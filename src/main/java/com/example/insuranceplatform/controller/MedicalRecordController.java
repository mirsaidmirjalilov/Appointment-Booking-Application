package com.example.insuranceplatform.controller;

import com.example.insuranceplatform.payload.BaseResponse;
import com.example.insuranceplatform.payload.medical_record.MedicalRecordRequest;
import com.example.insuranceplatform.payload.medical_record.MedicalRecordResponse;
import com.example.insuranceplatform.security.user_details.CustomUserDetails;
import com.example.insuranceplatform.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    @PostMapping("/{appointmentId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<BaseResponse<MedicalRecordResponse>> createRecord(@PathVariable Long appointmentId, @Valid @RequestBody MedicalRecordRequest request){
        MedicalRecordResponse record = medicalRecordService.createRecord(appointmentId, request);

        return ResponseEntity.status(201).body(BaseResponse.ok(record));
    }

    @GetMapping("/{appointmentId}")
    @PreAuthorize("hasAnyRole('DOCTOR','PATIENT')")
    public ResponseEntity<BaseResponse<MedicalRecordResponse>> getRecordByAppointmentId(
            @PathVariable Long appointmentId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        Long id = customUserDetails.user().getId();

        MedicalRecordResponse recordByAppointmentId = medicalRecordService.getRecordByAppointmentId(appointmentId, id);

        return ResponseEntity.status(200).body(BaseResponse.ok(recordByAppointmentId));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<BaseResponse<Page<MedicalRecordResponse>>> getRecordByPatientId(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PageableDefault Pageable pageable
    ){
        Long id = customUserDetails.user().getId();

        Page<MedicalRecordResponse> myRecords = medicalRecordService.getMyRecords(id, pageable);

        return ResponseEntity.status(200).body(BaseResponse.ok(myRecords));
    }
}
