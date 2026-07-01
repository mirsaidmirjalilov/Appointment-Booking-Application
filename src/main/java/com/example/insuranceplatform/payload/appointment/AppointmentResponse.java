package com.example.insuranceplatform.payload.appointment;

import com.example.insuranceplatform.util.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        Long doctorId,
        Long patientId,
        LocalDateTime appointmentDateTime,
        AppointmentStatus status,
        String reasonForVisit
) {
}
