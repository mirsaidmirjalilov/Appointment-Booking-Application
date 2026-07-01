package com.example.insuranceplatform.payload.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @Email @NotNull String email,
        @NotNull String password
) {
}
