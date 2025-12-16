package com.vinim.api_gateway.protected_;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

@RestController
public class ProtectedController {

    @GetMapping("/api/protected/ping")
    @ResponseStatus(HttpStatus.OK)
    public ProtectedResponse ping(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
            return new ProtectedResponse("ok", userId);
    }

    public record ProtectedResponse(String status, String userId) {}
}
