package com.example.insuranceplatform.service.impl;

import com.example.insuranceplatform.entity.Doctor;
import com.example.insuranceplatform.entity.User;
import com.example.insuranceplatform.mapper.DoctorMapper;
import com.example.insuranceplatform.payload.doctor.DoctorRequest;
import com.example.insuranceplatform.payload.doctor.DoctorResponse;
import com.example.insuranceplatform.repository.DoctorRepository;
import com.example.insuranceplatform.repository.UserRepository;
import com.example.insuranceplatform.service.DoctorService;
import com.example.insuranceplatform.util.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public Page<DoctorResponse> getAllDoctors(Pageable pageable) {
        return doctorRepository.findAllByIsActiveTrue(pageable).map(doctorMapper::toDoctorResponse);
    }

    @Override
    @Cacheable(value = "doctorById", key = "#id")
    public DoctorResponse getDoctorById(Long id) {
        return doctorRepository.findById(id).map(doctorMapper::toDoctorResponse).orElseThrow(() -> new UsernameNotFoundException("doctor not found"));
    }

    @Override
    @Transactional
    public DoctorResponse createDoctorProfile(DoctorRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (user.getRole() != UserRole.DOCTOR) {
            throw new RuntimeException("user is not a doctor");
        }

        Doctor doctor = doctorMapper.toDoctorEntity(user,request);

        doctorRepository.save(doctor);
        return doctorMapper.toDoctorResponse(doctor);
    }

    @Override
    @Transactional
    @CacheEvict(value = "doctorById", key = "#doctorId")
    public DoctorResponse updateDoctorProfile(Long doctorId, DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new UsernameNotFoundException("doctor not found"));

        doctor.setBio(request.bio());
        doctor.setSpecialization(request.specialization());
        doctor.setLicenseNumber(request.licenseNumber());
        doctor.setConsultationFee(request.consultationFee());
        doctor.setYearsOfExperience(request.yearsOfExperience());

        doctorRepository.save(doctor);
        return doctorMapper.toDoctorResponse(doctor);
    }
}
