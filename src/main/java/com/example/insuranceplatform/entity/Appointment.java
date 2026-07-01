package com.example.insuranceplatform.entity;

import com.example.insuranceplatform.entity.auditable.AudiTableLong;
import com.example.insuranceplatform.util.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment extends AudiTableLong {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    @Column(nullable = false)
    @Builder.Default
    private Integer durationMinutes = 30;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AppointmentStatus status = AppointmentStatus.PENDING;

    @Column(nullable = false)
    private String reasonForVisit;

    // Optional notes from patient at booking time
    @Column(columnDefinition = "TEXT")
    private String notes;
}
