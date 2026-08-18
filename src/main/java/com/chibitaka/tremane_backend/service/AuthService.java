package com.chibitaka.tremane_backend.service;

import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.form.SignUpForm;
import com.chibitaka.tremane_backend.repository.UserRepository;
import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import lombok.RequiredArgsConstructor;

/** 認証関連Service */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository; // ユーザーRepository
    private final ModelMapper modelMapper; // ModelMapper
    private final EmailService emailService; // メール送信Service
    private final MessageSource messageSource; // メッセージソース

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

        String subject = messageSource.getMessage("email.password_reset.subject", null, Locale.JAPAN);
        String body = messageSource.getMessage("email.password_reset.body", new Object[] { link }, Locale.JAPAN);

        emailService.sendPlainTextEmail(email, subject, body);
    }
}
