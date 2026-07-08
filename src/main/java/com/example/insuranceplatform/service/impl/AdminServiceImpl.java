package com.example.insuranceplatform.service.impl;

import com.example.insuranceplatform.entity.Doctor;
import com.example.insuranceplatform.entity.User;
import com.example.insuranceplatform.mapper.AppointmentMapper;
import com.example.insuranceplatform.mapper.DoctorMapper;
import com.example.insuranceplatform.mapper.MedicalRecordMapper;
import com.example.insuranceplatform.mapper.UserMapper;
import com.example.insuranceplatform.payload.appointment.AppointmentResponse;
import com.example.insuranceplatform.payload.doctor.DoctorRequest;
import com.example.insuranceplatform.payload.doctor.DoctorResponse;
import com.example.insuranceplatform.payload.medical_record.MedicalRecordResponse;
import com.example.insuranceplatform.payload.user.UserResponse;
import com.example.insuranceplatform.repository.AppointmentRepository;
import com.example.insuranceplatform.repository.DoctorRepository;
import com.example.insuranceplatform.repository.MedicalRecordRepository;
import com.example.insuranceplatform.repository.UserRepository;
import com.example.insuranceplatform.service.AdminService;
import com.example.insuranceplatform.util.UserRole;
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
    private final DoctorMapper doctorMapper;

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::userToUserResponse);
    }

    @Override
    @Transactional
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void activateUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setActive(true);
        userRepository.save(user);}

    @Override
    @Transactional
    public void deactivateDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new UsernameNotFoundException("Doctor not found"));

        doctor.setIsActive(false);
        doctorRepository.save(doctor);
    }

    @Override
    @Transactional
    public void activateDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new UsernameNotFoundException("Doctor not found"));

        doctor.setIsActive(true);
        doctorRepository.save(doctor); }

    @Override
    public Page<AppointmentResponse> getAllAppointments(Pageable pageable) {
        return appointmentRepository.findAll(pageable).map(appointmentMapper::toAppointmentResponse);
    }

    @Override
    public Page<MedicalRecordResponse> getAllMedicalRecords(Pageable pageable) {
        return medicalRecordRepository.findAll(pageable).map(medicalRecordMapper::toMedicalRecordResponse);
    }

    @Override
    public DoctorResponse createDoctor(Long userId, DoctorRequest doctorRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getRole() != UserRole.DOCTOR) {
            throw new RuntimeException("Doctor is not allowed to be created");
        }

        Doctor doctor = Doctor.builder()
                .user(user)
                .bio(doctorRequest.bio())
                .isActive(true)
                .consultationFee(doctorRequest.consultationFee())
                .licenseNumber(String.valueOf(doctorRequest.licenseNumber()))
                .specialization(String.valueOf(doctorRequest.specialization()))
                .yearsOfExperience(doctorRequest.yearsOfExperience())
                .build();
        doctorRepository.save(doctor);

        return doctorMapper.toDoctorResponse(doctor);
    }
}
