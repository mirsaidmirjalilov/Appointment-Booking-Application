package com.example.insuranceplatform.payload.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest(
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull @Pattern(regexp = "^9989[012345789]\\d{7}$") String phoneNumber
) {
}
