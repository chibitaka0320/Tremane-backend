package com.chibitaka.tremane_backend.service;

import java.util.Locale;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.form.SignUpForm;
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
    private final ModelMapper modelMapper; // ModelMapper
    private final EmailService emailService; // メール送信Service
    private final MessageSource messageSource; // メッセージソース
    private final RestClient restClient; // RestClient
    private final GoogleCredentials firebaseCredentials; // Firebaseサービスアカウント認証情報

    /** Identity Toolkit REST API（Admin SDKにVERIFY_AND_CHANGE_EMAILの生成メソッドが無いため直接呼び出す） */
    private static final String SEND_OOB_CODE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode";
    private static final String IDENTITY_TOOLKIT_SCOPE = "https://www.googleapis.com/auth/identitytoolkit";

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

    /** メールアドレス確認メール送信 */
    public void sendVerificationEmail(String uid) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        String email = userRecord.getEmail();

        String link = FirebaseAuth.getInstance().generateEmailVerificationLink(email);

        String subject = messageSource.getMessage("email.verification.subject", null, Locale.JAPAN);
        String body = messageSource.getMessage("email.verification.body", new Object[] { link }, Locale.JAPAN);

        emailService.sendPlainTextEmail(email, subject, body);
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
