package com.example.insuranceplatform.controller;

import com.example.insuranceplatform.payload.BaseResponse;
import com.example.insuranceplatform.payload.appointment.AppointmentResponse;
import com.example.insuranceplatform.payload.doctor.DoctorRequest;
import com.example.insuranceplatform.payload.doctor.DoctorResponse;
import com.example.insuranceplatform.payload.medical_record.MedicalRecordResponse;
import com.example.insuranceplatform.payload.user.UserResponse;
import com.example.insuranceplatform.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<BaseResponse<Page<UserResponse>>> getUsers(@PageableDefault Pageable pageable) {
        Page<UserResponse> allUsers = adminService.getAllUsers(pageable);

        return ResponseEntity.status(200).body(BaseResponse.ok(allUsers));
    }

    @PatchMapping("/users/{userId}/deactivate")
    public ResponseEntity<BaseResponse<Page<UserResponse>>> deactivateUser(@PathVariable Long userId){
        adminService.deactivateUser(userId);

        return ResponseEntity.status(200).body(BaseResponse.ok());
    }

    @PatchMapping("/users/{userId}/activate")
    public ResponseEntity<BaseResponse<Page<UserResponse>>> activateUser(@PathVariable Long userId){
        adminService.activateUser(userId);

        return ResponseEntity.status(200).body(BaseResponse.ok());
    }

    @PatchMapping("/doctors/{doctorId}/activate")
    public ResponseEntity<BaseResponse<Page<UserResponse>>> activateDoctor(@PathVariable Long doctorId){
        adminService.activateDoctor(doctorId);

        return ResponseEntity.status(200).body(BaseResponse.ok());
    }

    @PatchMapping("/doctors/{doctorId}/deactivate")
    public ResponseEntity<BaseResponse<Page<UserResponse>>> deactivateDoctor(@PathVariable Long doctorId){
        adminService.deactivateDoctor(doctorId);

        return ResponseEntity.status(200).body(BaseResponse.ok());
    }

    @GetMapping("/appointments")
    public ResponseEntity<BaseResponse<Page<AppointmentResponse>>> getAppointments(@PageableDefault Pageable pageable) {
        Page<AppointmentResponse> allAppointments = adminService.getAllAppointments(pageable);

        return ResponseEntity.status(200).body(BaseResponse.ok(allAppointments));
    }

    @GetMapping("/medical-records")
    public ResponseEntity<BaseResponse<Page<MedicalRecordResponse>>> getMedicalRecords(@PageableDefault Pageable pageable) {
        Page<MedicalRecordResponse> medicalRecordResponses = adminService.getAllMedicalRecords(pageable);

        return ResponseEntity.status(200).body(BaseResponse.ok(medicalRecordResponses));
    }

    @PostMapping("/doctors/{userId}")
    public ResponseEntity<BaseResponse<DoctorResponse>> createDoctor(@PathVariable Long userId, @Valid @RequestBody DoctorRequest doctorRequest){
        DoctorResponse doctor = adminService.createDoctor(userId, doctorRequest);

        return ResponseEntity.status(201).body(BaseResponse.ok(doctor));
    }
}
