package com.chibitaka.tremane_backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.entity.RefreshTokenEntity;
import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.vo.EmailVo;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RefreshTokenRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    @DisplayName("トークン情報登録")
    void testInsert() {
        UserEntity user = new UserEntity();
        user.setEmail(new EmailVo("refreshuser@example.com"));
        user.setPassword("hashedPassword");
        int userResult = userRepository.insert(user);
        assertEquals(1, userResult);
        Long userId = user.getUserId();
        assertNotNull(userId);

        String refreshToken = "sample-refresh-token";
        LocalDateTime expiry = LocalDateTime.now().plusDays(7);
        RefreshTokenEntity entity = new RefreshTokenEntity(userId, refreshToken, expiry);
        int result = refreshTokenRepository.insert(entity);
        assertEquals(1, result);
    }
}
