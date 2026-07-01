package com.example.insuranceplatform.mapper;

import com.example.insuranceplatform.entity.Appointment;
import com.example.insuranceplatform.payload.appointment.AppointmentResponse;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {
    public AppointmentResponse toAppointmentResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getDoctor().getId(),
                appointment.getPatient().getId(),
                appointment.getAppointmentDateTime(),
                appointment.getStatus(),
                appointment.getReasonForVisit()
        );
    }
}
