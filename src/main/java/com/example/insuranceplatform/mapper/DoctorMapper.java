package com.example.insuranceplatform.mapper;

import com.example.insuranceplatform.entity.Doctor;
import com.example.insuranceplatform.entity.User;
import com.example.insuranceplatform.payload.doctor.DoctorRequest;
import com.example.insuranceplatform.payload.doctor.DoctorResponse;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {
    public DoctorResponse toDoctorResponse(Doctor doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getUser().getFirstName(),
                doctor.getUser().getLastName(),
                doctor.getSpecialization(),
                doctor.getLicenseNumber(),
                doctor.getBio(),
                doctor.getConsultationFee(),
                doctor.getYearsOfExperience(),
                5,
                doctor.getIsActive()
        );
    }

    public Doctor toDoctorEntity(User user, DoctorRequest request) {
        return Doctor.builder()
                .user(user)
                .bio(request.bio())
                .specialization(request.specialization())
                .licenseNumber(request.licenseNumber())
                .yearsOfExperience(request.yearsOfExperience())
                .isActive(true)
                .consultationFee(request.consultationFee())
                .build();
    }
}
