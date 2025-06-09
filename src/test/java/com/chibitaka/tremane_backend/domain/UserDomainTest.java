package com.chibitaka.tremane_backend.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.repository.UserRepository;
import com.chibitaka.tremane_backend.vo.EmailVo;

@ExtendWith(MockitoExtension.class)
public class UserDomainTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDomain userDomain;

    @Test
    @DisplayName("ユーザー存在チェック_未存在")
    void testExists_nonExists() {
        EmailVo email = new EmailVo("test@example.com");

        when(userRepository.findByEmail(any(EmailVo.class))).thenReturn(null);

        boolean exists = userDomain.exists(email);

        assertFalse(exists);
    }

    @Test
    @DisplayName("ユーザー存在チェック_存在")
    void testExists_exists() {
        EmailVo email = new EmailVo("test@example.com");
        UserEntity user = new UserEntity();

        when(userRepository.findByEmail(any(EmailVo.class))).thenReturn(user);

        boolean exists = userDomain.exists(email);

        assertTrue(exists);
    }
}
