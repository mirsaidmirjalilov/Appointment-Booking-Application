package com.example.insuranceplatform.payload.medical_record;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MedicalRecordRequest(
        @NotNull String diagnosis,
        @NotNull String prescription,
        @NotNull String recommendations,
        LocalDateTime followUpDate
) {
}
