package com.example.insuranceplatform.payload.medical_record;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MedicalRecordResponse(
        Long id,
        String diagnosis,
        String prescription,
        String recommendations,
        LocalDateTime followUpDate,
        Long appointmentId,
        String doctorName,
        String patientName,
        LocalDateTime createdAt
) {
}
