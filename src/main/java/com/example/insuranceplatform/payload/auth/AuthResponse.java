package com.example.insuranceplatform.payload.auth;

import com.example.insuranceplatform.util.UserRole;

public record AuthResponse(
        String token,
        UserRole role
) {
}
