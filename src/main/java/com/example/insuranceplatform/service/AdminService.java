package com.example.insuranceplatform.service;

import com.example.insuranceplatform.payload.appointment.AppointmentResponse;
import com.example.insuranceplatform.payload.medical_record.MedicalRecordResponse;
import com.example.insuranceplatform.payload.user.UserResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    Page<UserResponse> getAllUsers(Pageable pageable);

    void deactivateUser(@NotNull Long userId);

    void activateUser(@NotNull Long userId);

    void deactivateDoctor(@NotNull Long doctorId);

    void activateDoctor(@NotNull Long doctorId);

    Page<AppointmentResponse> getAllAppointments(Pageable pageable);

    Page<MedicalRecordResponse> getAllMedicalRecords(Pageable pageable);
}
