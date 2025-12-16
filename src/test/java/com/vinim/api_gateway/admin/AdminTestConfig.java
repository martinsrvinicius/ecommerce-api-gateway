package com.vinim.api_gateway.admin;

import com.vinim.api_gateway.JwtTokenProvider;
import com.vinim.api_gateway.security.JwtReactiveAuthenticationManager;
import com.vinim.api_gateway.security.JwtServerAuthenticationConverter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class AdminTestConfig {

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return mock(JwtTokenProvider.class);
    }

    @Bean
    public ReactiveAuthenticationManager jwtAuthenticationManager(JwtTokenProvider jwtTokenProvider) {
        return new JwtReactiveAuthenticationManager(jwtTokenProvider);
    }

    @Bean
    public AuthenticationWebFilter jwtAuthenticationWebFilter(ReactiveAuthenticationManager jwtAuthenticationManager) {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(jwtAuthenticationManager);
        filter.setServerAuthenticationConverter(new JwtServerAuthenticationConverter());
        filter.setRequiresAuthenticationMatcher(
                ServerWebExchangeMatchers.pathMatchers("/api/**"));
        return filter;
    }
}
