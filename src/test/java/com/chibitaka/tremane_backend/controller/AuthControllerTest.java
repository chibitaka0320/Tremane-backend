package com.chibitaka.tremane_backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.chibitaka.tremane_backend.common.security.SecurityConfig;
import com.chibitaka.tremane_backend.dto.SignUpDto;
import com.chibitaka.tremane_backend.form.SignUpForm;
import com.chibitaka.tremane_backend.service.AuthService;
import com.chibitaka.tremane_backend.service.SignInService;
import com.chibitaka.tremane_backend.service.SignUpService;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SignUpService signUpService;

    @MockitoBean
    private SignInService signInService;

    @MockitoBean
    private AuthService authService;

    @Test
    @DisplayName("新規登録_正常系")
    void testSignUp_success() throws Exception {
        SignUpDto mockDto = new SignUpDto();
        mockDto.setAccessToken("accessToken");
        mockDto.setRefreshToken("refreshToken");
        when(signUpService.signUp(any(SignUpForm.class))).thenReturn(mockDto);

        String requestBody = """
                {
                    "email": "test@example.com",
                    "password": "password123"
                }
                """;

        mockMvc.perform(post("/auth/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.refreshToken").value("refreshToken"));
    }
}
