package com.vinim.api_gateway.auth;

import com.vinim.api_gateway.JwtTokenProvider;
import com.vinim.api_gateway.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = AuthController.class)
@ContextConfiguration(classes = {SecurityConfig.class, AuthController.class})
public class AuthControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void shouldReturnJwtOnValidLogin() {
        LoginRequest request =
                new LoginRequest("vinim@email.com", "strongpassword");

        when(jwtTokenProvider.generateToken(request.email(), "user"))
                .thenReturn("eyJ.mock.token");

        webTestClient.post()
                .uri("/api/auth/login")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.token").isEqualTo("eyJ.mock.token");
    }
}
