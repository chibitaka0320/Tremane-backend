package com.chibitaka.tremane_backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.chibitaka.tremane_backend.entity.UserEntity;
import com.chibitaka.tremane_backend.vo.EmailVo;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("ユーザー登録・ID検索・メール検索")
    void testInsertAndFind() {
        // ユーザー登録
        UserEntity user = new UserEntity();
        user.setEmail(new EmailVo("test@example.com"));
        user.setPassword("hashedPassword");
        int result = userRepository.insert(user);
        assertEquals(1, result);

        Long userId = user.getUserId();
        assertNotNull(userId);

        // ID検索
        UserEntity findById = userRepository.findById(userId);
        assertNotNull(findById);
        assertEquals("test@example.com", findById.getEmail().getValue());

        // メール検索
        UserEntity findByEmail = userRepository.findByEmail(new EmailVo("test@example.com"));
        assertNotNull(findByEmail);
        assertEquals(userId, findByEmail.getUserId());
    }
}
