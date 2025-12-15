
package com.vinim.api_gateway.auth;

import com.vinim.api_gateway.JwtTokenProvider;
import com.vinim.api_gateway.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = AuthController.class)
@ContextConfiguration(classes = {SecurityConfig.class, AuthController.class})
public class AuthControllerValidateTest {

    @Autowired
    private WebTestClient webTestClient;
    
    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void shouldValidateTokenAndReturnUserId() {
        String token = "mock.jwt.token";
        String userId = "user-123";

        when(jwtTokenProvider.getUserIdFromToken(token)).thenReturn(userId);

        webTestClient.get()
                .uri("/api/auth/validate")
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.userId").isEqualTo(userId)
                .jsonPath("$.valid").isEqualTo(true);
    }
}
