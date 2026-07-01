package com.example.insuranceplatform.payload.appointment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AppointmentRequest(
        @Min(1) Long doctorId,
        @NotNull LocalDateTime appointmentDateTime,
        @NotNull String reasonForVisit,
        @NotNull String notes
) {
}
