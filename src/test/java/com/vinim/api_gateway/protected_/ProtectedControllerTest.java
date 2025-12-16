package com.vinim.api_gateway.protected_;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.vinim.api_gateway.JwtTokenProvider;
import com.vinim.api_gateway.config.SecurityConfig;

@WebFluxTest(controllers = ProtectedController.class)
@ContextConfiguration(classes = {SecurityConfig.class, ProtectedController.class})
public class ProtectedControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void shouldDenyAccessWithoutAuthentication() {
        webTestClient.get()
                     .uri("/api/protected/ping")
                     .exchange()
                     .expectStatus().is4xxClientError();
    }

    @Test
    public void shouldAllowAccessWithValidToken() {
        String token = "mock.jwt.token";
        String userId = "user-123";

        when(jwtTokenProvider.getUserIdFromToken(token)).thenReturn(userId);

        webTestClient.get()
                     .uri("/api/protected/ping")
                     .header("Authorization", "Bearer " + token)
                     .exchange()
                     .expectStatus().isOk()
                     .expectBody()
                     .jsonPath("$.status").isEqualTo("ok")
                     .jsonPath("$.userId").isEqualTo(userId);

    }
}
