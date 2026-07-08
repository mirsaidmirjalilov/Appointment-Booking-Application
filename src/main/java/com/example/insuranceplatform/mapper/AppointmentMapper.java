package com.example.insuranceplatform.mapper;

import com.example.insuranceplatform.entity.Appointment;
import com.example.insuranceplatform.entity.Doctor;
import com.example.insuranceplatform.entity.User;
import com.example.insuranceplatform.payload.appointment.AppointmentRequest;
import com.example.insuranceplatform.payload.appointment.AppointmentResponse;
import com.example.insuranceplatform.repository.DoctorRepository;
import com.example.insuranceplatform.repository.UserRepository;
import com.example.insuranceplatform.util.AppointmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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

    public Appointment toEntity(AppointmentRequest request, User user, Doctor doctor) {
        return Appointment.builder()
                .status(AppointmentStatus.PENDING)
                .appointmentDateTime(request.appointmentDateTime())
                .notes(request.notes())
                .patient(user)
                .doctor(doctor)
                .reasonForVisit(request.reasonForVisit())
                .build();
    }
}
