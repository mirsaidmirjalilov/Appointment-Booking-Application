package com.example.insuranceplatform.payload.doctor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DoctorResponse(
        Long id,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String specialization,
        @NotNull String licenseNumber,
        @NotNull String bio,
        @NotNull Double consultationFee,
        @NotNull @Min(1) Integer yearsOfExperience,
        @NotNull @Min(1) Integer rating,
        Boolean isActive
) {
}
