package com.example.insuranceplatform.service.impl;

import com.example.insuranceplatform.entity.Appointment;
import com.example.insuranceplatform.entity.Doctor;
import com.example.insuranceplatform.entity.User;
import com.example.insuranceplatform.exception.AppointmentNotFoundException;
import com.example.insuranceplatform.mapper.AppointmentMapper;
import com.example.insuranceplatform.payload.appointment.AppointmentRequest;
import com.example.insuranceplatform.payload.appointment.AppointmentResponse;
import com.example.insuranceplatform.repository.AppointmentRepository;
import com.example.insuranceplatform.repository.DoctorRepository;
import com.example.insuranceplatform.repository.UserRepository;
import com.example.insuranceplatform.service.AppointmentService;
import com.example.insuranceplatform.util.AppointmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    @Transactional
    public AppointmentResponse bookAppointment(AppointmentRequest request, Long patientId) {
        boolean exists = appointmentRepository
                .existsByDoctorIdAndAppointmentDateTime(request.doctorId(), request.appointmentDateTime());
        if (exists) throw new RuntimeException("Slot already booked");
        User user = userRepository.findById(patientId).orElseThrow(() -> new UsernameNotFoundException("Patient not found"));
        Doctor doctor = doctorRepository.findById(request.doctorId()).orElseThrow(() -> new UsernameNotFoundException("Doctor not found"));

        Appointment appointment = appointmentMapper.toEntity(request, user, doctor);

        appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentResponse(appointment);
    }

    @Override
    @Transactional
    public AppointmentResponse confirmAppointment(Long appointmentId) {
        return changeAppointmentStatus(appointmentId, AppointmentStatus.CONFIRMED);
    }

    @Override
    @Transactional
    public AppointmentResponse cancelAppointment(Long appointmentId) {
        return changeAppointmentStatus(appointmentId, AppointmentStatus.CANCELLED);
    }

    @Override
    @Transactional
    public AppointmentResponse completeAppointment(Long appointmentId) {
        return changeAppointmentStatus(appointmentId, AppointmentStatus.COMPLETED);
    }

    @Override
    @Transactional
    public AppointmentResponse markNoShow(Long appointmentId) {
        return changeAppointmentStatus(appointmentId, AppointmentStatus.NO_SHOW);
    }

    @Override
    public Page<AppointmentResponse> getMyAppointments(Long userId, Pageable pageable) {
        return appointmentRepository.findByUserId(userId, pageable).map(appointmentMapper::toAppointmentResponse);
    }

    private AppointmentResponse changeAppointmentStatus(Long appointmentId, AppointmentStatus confirmed) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

        appointment.setStatus(confirmed);
        appointmentRepository.save(appointment);
        return appointmentMapper.toAppointmentResponse(appointment);
    }
}
