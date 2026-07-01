package com.example.insuranceplatform.service.impl;

import com.example.insuranceplatform.mapper.AppointmentMapper;
import com.example.insuranceplatform.mapper.MedicalRecordMapper;
import com.example.insuranceplatform.mapper.UserMapper;
import com.example.insuranceplatform.payload.appointment.AppointmentResponse;
import com.example.insuranceplatform.payload.medical_record.MedicalRecordResponse;
import com.example.insuranceplatform.payload.user.UserResponse;
import com.example.insuranceplatform.repository.AppointmentRepository;
import com.example.insuranceplatform.repository.DoctorRepository;
import com.example.insuranceplatform.repository.MedicalRecordRepository;
import com.example.insuranceplatform.repository.UserRepository;
import com.example.insuranceplatform.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserMapper userMapper;
    private final AppointmentMapper appointmentMapper;
    private final MedicalRecordMapper medicalRecordMapper;
    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::userToUserResponse);
    }

    @Override
    @Transactional
    public void deactivateUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found")).setActive(false);
    }

    @Override
    @Transactional
    public void activateUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found")).setActive(true);
    }

    @Override
    @Transactional
    public void deactivateDoctor(Long doctorId) {
        doctorRepository.findById(doctorId).orElseThrow(() -> new UsernameNotFoundException("Doctor not found")).setIsActive(false);
    }

    @Override
    @Transactional
    public void activateDoctor(Long doctorId) {
        doctorRepository.findById(doctorId).orElseThrow(() -> new UsernameNotFoundException("Doctor not found")).setIsActive(true);
    }

    @Override
    public Page<AppointmentResponse> getAllAppointments(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(appointmentMapper::toAppointmentResponse);
    }

    @Override
    public Page<MedicalRecordResponse> getAllMedicalRecords(Pageable pageable) {
        return medicalRecordRepository.findAll(pageable).map(medicalRecordMapper::toMedicalRecordResponse);
    }
}
