package com.vinim.api_gateway.admin;

import com.vinim.api_gateway.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = AdminController.class)
@ContextConfiguration(classes = {SecurityConfig.class, AdminController.class})
public class AdminControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @WithMockUser(username = "user-123", roles = {"USER"})
    public void shouldForbidAccessToAdminWithoutAdminRole() {
        webTestClient.get()
                .uri("/api/admin/ping")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @WithMockUser(username = "admin-1", roles = {"ADMIN"})
    public void shouldAllowAccessToAdminWithAdminRole() {
        webTestClient.get()
                .uri("/api/admin/ping")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("admin-ok");
    }

    @Test
    public void shouldDenyAccessToAdminWithoutToken() {
        webTestClient.get()
                .uri("/api/admin/ping")
                .exchange()
                .expectStatus().is4xxClientError();
    }
}
