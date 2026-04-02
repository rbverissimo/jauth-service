package com.coltran.jauth_service.infrastructure.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.coltran.jauth_service.infrastructure.security.CustomUserDetailsService;
import com.coltran.jauth_service.infrastructure.security.jwt.JwtAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({SecurityConfig.class})
public class SecurityConfigTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext applicationContext;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() throws Exception {
        Mockito.doAnswer(invocation -> {
            ServletRequest request = invocation.getArgument(0);
            ServletResponse response = invocation.getArgument(1);
            FilterChain chain = invocation.getArgument(2);
            chain.doFilter(request, response);
            return null;
        }).when(jwtAuthenticationFilter).doFilter(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should successfully load Security Bean into the Application Context")
    void givenSecurityConfig_whenContextLoads_thenBeansArePresent() {
        assertNotNull(applicationContext.getBean(AuthenticationManager.class), "AuthenticationManager bean should be present");
        assertNotNull(applicationContext.getBean(PasswordEncoder.class), "PasswordEncoder bean should be present");
        assertNotNull(applicationContext.getBean(AuthenticationProvider.class), "AuthenticationProvider bean should be present");

    }
}
