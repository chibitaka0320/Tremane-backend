package com.chibitaka.tremane_backend.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import com.chibitaka.tremane_backend.common.error.ApiResponseException;
import com.chibitaka.tremane_backend.entity.EmailVerificationCodeEntity;
import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.form.SignUpForm;
import com.chibitaka.tremane_backend.repository.EmailVerificationCodeRepository;
import com.chibitaka.tremane_backend.repository.UserRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** 認証関連Service */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository; // ユーザーRepository
    private final EmailVerificationCodeRepository emailVerificationCodeRepository; // 確認コードRepository
    private final ModelMapper modelMapper; // ModelMapper
    private final EmailService emailService; // メール送信Service
    private final MessageSource messageSource; // メッセージソース
    private final RestClient restClient; // RestClient
    private final GoogleCredentials firebaseCredentials; // Firebaseサービスアカウント認証情報

    private final SecureRandom secureRandom = new SecureRandom();

    /** Identity Toolkit REST API（Admin SDKにVERIFY_AND_CHANGE_EMAILの生成メソッドが無いため直接呼び出す） */
    private static final String SEND_OOB_CODE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode";
    private static final String IDENTITY_TOOLKIT_SCOPE = "https://www.googleapis.com/auth/identitytoolkit";

    /** メールアドレス確認コード（OTP）関連の設定値 */
    private static final int VERIFICATION_CODE_TTL_MINUTES = 10;
    private static final int VERIFICATION_CODE_RESEND_COOLDOWN_SECONDS = 60;
    private static final int VERIFICATION_CODE_MAX_ATTEMPT_COUNT = 5;

    /** ユーザー新規登録 */
    public void signUp(SignUpForm form) {
        UserEntity user = modelMapper.map(form, UserEntity.class);
        userRepository.insert(user);
    }

    /** 認証トークン再発行 */
    public String issueReauthToken(String uid) throws FirebaseAuthException {
        // TODO: エラーハンドリングについて検討
        return FirebaseAuth.getInstance().createCustomToken(uid);
    }

    /** メールアドレス確認コード（OTP）送信 */
    public void sendVerificationEmail(String uid) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        String email = userRecord.getEmail();

        // 直近の発行から一定時間内は再送を拒否する（連打防止）
        EmailVerificationCodeEntity existing = emailVerificationCodeRepository.findByUserId(uid);
        if (existing != null && existing.getCreatedAt()
                .isAfter(LocalDateTime.now().minusSeconds(VERIFICATION_CODE_RESEND_COOLDOWN_SECONDS))) {
            throw new ApiResponseException(429, "429", "しばらく時間を置いてから再送してください");
        }

        String code = String.format("%06d", secureRandom.nextInt(1_000_000));
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(VERIFICATION_CODE_TTL_MINUTES);
        emailVerificationCodeRepository.upsert(new EmailVerificationCodeEntity(uid, code, expiresAt, 0, null));

        String subject = messageSource.getMessage("email.verification.subject", null, Locale.JAPAN);
        String body = messageSource.getMessage("email.verification.body", new Object[] { code }, Locale.JAPAN);

        emailService.sendPlainTextEmail(email, subject, body);
    }

    /** メールアドレス確認コード（OTP）検証 */
    public void verifyEmailCode(String uid, String code) throws FirebaseAuthException {
        EmailVerificationCodeEntity entity = emailVerificationCodeRepository.findByUserId(uid);
        if (entity == null) {
            throw new ApiResponseException(400, "400", "コードが見つかりません。再送してください");
        }
        if (entity.getExpiresAt().isBefore(LocalDateTime.now())) {
            emailVerificationCodeRepository.deleteByUserId(uid);
            throw new ApiResponseException(400, "400", "コードの有効期限が切れています。再送してください");
        }
        if (entity.getAttemptCount() >= VERIFICATION_CODE_MAX_ATTEMPT_COUNT) {
            throw new ApiResponseException(400, "400", "試行回数の上限に達しました。再送してください");
        }
        if (!entity.getCode().equals(code)) {
            emailVerificationCodeRepository.incrementAttemptCount(uid);
            throw new ApiResponseException(400, "400", "コードが正しくありません");
        }

        emailVerificationCodeRepository.deleteByUserId(uid);
        FirebaseAuth.getInstance().updateUser(new UserRecord.UpdateRequest(uid).setEmailVerified(true));
    }

    /** パスワード再設定メール送信 */
    public void sendPasswordResetEmail(String email) throws FirebaseAuthException {
        String link;
        try {
            link = FirebaseAuth.getInstance().generatePasswordResetLink(email);
        } catch (FirebaseAuthException e) {
            // 未登録メールアドレスの場合は何もせず正常終了扱いにする（メールアドレスの存在有無を推測されないようにするため）
            if (e.getAuthErrorCode() == AuthErrorCode.USER_NOT_FOUND) {
                return;
            }
            throw e;
        }

        // メール列挙保護が有効な場合、未登録メールアドレスに対しては例外を投げずlinkがnullで返る
        if (link == null) {
            log.warn("パスワード再設定リンクが生成されなかったため送信をスキップ: email={}", email);
            return;
        }

        String subject = messageSource.getMessage("email.password_reset.subject", null, Locale.JAPAN);
        String body = messageSource.getMessage("email.password_reset.body", new Object[] { link }, Locale.JAPAN);

        emailService.sendPlainTextEmail(email, subject, body);
    }

    /** メールアドレス変更確認メール送信 */
    public void sendChangeEmailVerification(String uid, String newEmail) throws Exception {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        String currentEmail = userRecord.getEmail();

        GoogleCredentials credentials = firebaseCredentials.createScoped(IDENTITY_TOOLKIT_SCOPE);
        String accessToken = credentials.refreshAccessToken().getTokenValue();

        Map<String, Object> payload = Map.of(
                "requestType", "VERIFY_AND_CHANGE_EMAIL",
                "email", currentEmail,
                "newEmail", newEmail,
                "returnOobLink", true);

        Map<?, ?> response = restClient.post()
                .uri(SEND_OOB_CODE_URL)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .body(Map.class);

        Object link = response != null ? response.get("oobLink") : null;
        if (link == null) {
            log.warn("メールアドレス変更確認リンクが生成されなかったため送信をスキップ: uid={}, newEmail={}, response={}",
                    uid, newEmail, response);
            return;
        }

        String subject = messageSource.getMessage("email.change_email.subject", null, Locale.JAPAN);
        String body = messageSource.getMessage("email.change_email.body", new Object[] { link }, Locale.JAPAN);

        emailService.sendPlainTextEmail(newEmail, subject, body);
    }
}
