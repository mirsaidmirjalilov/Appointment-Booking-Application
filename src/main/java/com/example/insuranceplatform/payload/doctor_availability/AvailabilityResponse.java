package com.example.insuranceplatform.payload.doctor_availability;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AvailabilityResponse(
        Long id,
        @NotNull DayOfWeek dayOfWeek,
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime,
        @NotNull @Min(30) Integer slotDurationMinutes
) {
}
