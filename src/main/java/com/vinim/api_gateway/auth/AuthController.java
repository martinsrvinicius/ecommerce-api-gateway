package com.vinim.api_gateway.auth;

import com.vinim.api_gateway.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest request) {
        // TODO: aqui no futuro vamos chamar User Service / DB para validar credenciais

        if (request.email() == null || !request.email().contains("@") ||
            request.password() == null || request.password().isBlank()) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        // Por enquanto, userId é o próprio email
        String token = jwtTokenProvider.generateToken(request.email(), "user");
        return new LoginResponse(token);
    }

    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public ValidateTokenResponse validateToken(@RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
        
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        String token = authorizationHeader.substring("Bearer ".length());
        String userId = jwtTokenProvider.getUserIdFromToken(token);

        return new ValidateTokenResponse(userId, true);   
    }
}