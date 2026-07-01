package com.example.insuranceplatform.payload.doctor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DoctorRequest(
        @NotNull String specialization,
        @NotNull String licenseNumber,
        @NotNull String bio,
        @NotNull Double consultationFee,
        @NotNull @Min(1) Integer yearsOfExperience
) {
}
