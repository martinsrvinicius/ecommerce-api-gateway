package com.vinim.api_gateway.admin;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/api/admin/ping")
    public Map<String, String> pingAdmin() {
        return Map.of("status", "admin-ok");
    }
}
