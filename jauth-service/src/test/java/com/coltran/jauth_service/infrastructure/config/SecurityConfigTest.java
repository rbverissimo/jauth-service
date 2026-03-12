package com.coltran.jauth_service.infrastructure.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.coltran.jauth_service.infrastructure.security.jwt.JwtAuthenticationFilter;
import com.coltran.jauth_service.infrastructure.security.jwt.JwtTokenProvider;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({SecurityConfig.class, JwtAuthenticationFilter.class})
public class SecurityConfigTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Should allow unauthenticated access to permitted endpoints")
    void permittedEnpoints_ShouldBeAccessibleWithoutAuthentication() throws Exception {

        mockMvc.perform(get("/api/auth/login")).andExpect(status().isNotFound());
    }
}
