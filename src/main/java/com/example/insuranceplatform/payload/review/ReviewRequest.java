package com.example.insuranceplatform.payload.review;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest(
        @NotNull Long appointmentId,
        @NotNull @Min(1) Integer rating,
        @NotNull String comment
) {
}
