package com.vinim.api_gateway;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenProviderTest {
    
    @Test
    void shouldGenerateValidToken() {
        JwtTokenProvider provider = new JwtTokenProvider("test-secret-key-that-is-long-enough-for-jwt"); 

        String token = provider.generate("test-user", "ROLE_USER");

        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    void shouldValidateCorrectToken() {
        JwtTokenProvider provider = new JwtTokenProvider("test-secret-key-that-is-long-enough-for-jwt");

        String token = provider.generate("test-user", "ROLE_USER");
        String userId = provider.getUserIdFromToken(token);

        assertEquals("test-user", userId);
    }
}
