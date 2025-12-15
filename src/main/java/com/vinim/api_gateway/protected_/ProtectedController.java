package com.vinim.api_gateway.protected_;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
public class ProtectedController {

    @GetMapping("/api/protected/ping")
    @ResponseStatus(HttpStatus.OK)
    public ProtectedResponse ping() {
        return new ProtectedResponse("ok");
    }

    public record ProtectedResponse(String status) {}
}
