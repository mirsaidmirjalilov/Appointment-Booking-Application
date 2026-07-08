package com.example.insuranceplatform.service;

import com.example.insuranceplatform.payload.doctor.DoctorRequest;
import com.example.insuranceplatform.payload.doctor.DoctorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
    Page<DoctorResponse> getAllDoctors(Pageable pageable);
    DoctorResponse getDoctorById(Long id);
    DoctorResponse createDoctorProfile(DoctorRequest request, Long userId);
    DoctorResponse updateDoctorProfile(Long doctorId, DoctorRequest request);
}
