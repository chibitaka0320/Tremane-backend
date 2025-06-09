package com.chibitaka.tremane_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.chibitaka.tremane_backend.common.error.ApiResponseException;
import com.chibitaka.tremane_backend.common.util.JwtUtil;
import com.chibitaka.tremane_backend.domain.UserDomain;
import com.chibitaka.tremane_backend.dto.SignUpDto;
import com.chibitaka.tremane_backend.entity.RefreshTokenEntity;
import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.form.SignUpForm;
import com.chibitaka.tremane_backend.repository.RefreshTokenRepository;
import com.chibitaka.tremane_backend.repository.UserRepository;
import com.chibitaka.tremane_backend.vo.EmailVo;

@ExtendWith(MockitoExtension.class)
public class SignUpServiceTest {

    @Mock
    private UserDomain userDomain;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private SignUpService signUpService;

    @Test
    @DisplayName("新規登録_正常系")
    void testSignUp_success() {
        SignUpForm form = new SignUpForm();
        form.setEmail("test@example.com");
        form.setPassword("password");

        when(userDomain.exists(any(EmailVo.class))).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        doAnswer(invocation -> {
            UserEntity user = invocation.getArgument(0);
            user.setUserId(1L);
            return 1;
        }).when(userRepository).insert(any(UserEntity.class));
        when(jwtUtil.createAccessToken(anyLong())).thenReturn("accessToken");
        when(jwtUtil.createRefreshToken()).thenReturn("refreshToken");

        SignUpDto dto = signUpService.signUp(form);

        assertEquals("accessToken", dto.getAccessToken());
        assertEquals("refreshToken", dto.getRefreshToken());
        verify(userRepository).insert(any(UserEntity.class));
        verify(refreshTokenRepository).insert(any(RefreshTokenEntity.class));
    }

    @Test
    @DisplayName("新規登録_異常系")
    void testSignUp_conflict() {
        SignUpForm form = new SignUpForm();
        form.setEmail("test@example.com");
        form.setPassword("password");

        when(userDomain.exists(any(EmailVo.class))).thenReturn(true);
        when(messageSource.getMessage(eq("10002E"), any(), any(Locale.class))).thenReturn("メールアドレスはすでに存在しています");

        ApiResponseException exception = assertThrows(
                ApiResponseException.class,
                () -> {
                    signUpService.signUp(form);
                });
        assertEquals("メールアドレスはすでに存在しています", exception.getMessage());
        assertEquals(409, exception.getResponseCode());
    }
}
