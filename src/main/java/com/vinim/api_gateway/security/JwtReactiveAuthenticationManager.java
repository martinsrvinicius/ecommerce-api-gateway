package com.vinim.api_gateway.security;

import com.vinim.api_gateway.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.util.List;

public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtReactiveAuthenticationManager(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = (String) authentication.getCredentials();

        try {
            String userId = jwtTokenProvider.getUserIdFromToken(token);
            String role = (String) authentication.getCredentials();
            
            // Converte role única (ex: "USER" ou "ADMIN") para GrantedAuthority
            String authority = role.startsWith("Role_") ? role : "ROLE_" + role;

            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority));

            Authentication auth = new UsernamePasswordAuthenticationToken(userId, token, authorities);
            return Mono.just(auth);
        } catch (Exception e) {
            return Mono.empty();
        } 
    }
}
