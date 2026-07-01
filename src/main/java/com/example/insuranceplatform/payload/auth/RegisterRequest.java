package com.example.insuranceplatform.payload.auth;

import com.example.insuranceplatform.util.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterRequest(
        @Email @NotNull String email,
        @NotNull String password,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull @Pattern(regexp = "^9989[012345789]\\d{7}$") String phoneNumber,
        @NotNull UserRole role
) {
}
