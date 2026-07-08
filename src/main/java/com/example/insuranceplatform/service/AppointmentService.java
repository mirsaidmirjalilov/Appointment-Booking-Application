package com.example.insuranceplatform.service;

import com.example.insuranceplatform.payload.appointment.AppointmentRequest;
import com.example.insuranceplatform.payload.appointment.AppointmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {
    AppointmentResponse bookAppointment(AppointmentRequest request, Long patientId);
    AppointmentResponse confirmAppointment(Long appointmentId);
    AppointmentResponse cancelAppointment(Long appointmentId);
    AppointmentResponse completeAppointment(Long appointmentId);
    AppointmentResponse markNoShow(Long appointmentId);
    Page<AppointmentResponse> getMyAppointments(Long userId, Pageable pageable);
}
