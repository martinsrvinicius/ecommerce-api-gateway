package com.vinim.api_gateway.auth;

public record ValidateTokenResponse(String userId, boolean valid) {

}
