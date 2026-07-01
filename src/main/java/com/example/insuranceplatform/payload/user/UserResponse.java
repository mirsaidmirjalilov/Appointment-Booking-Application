package com.example.insuranceplatform.payload.user;

import com.example.insuranceplatform.util.UserRole;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        UserRole role
) {
}
