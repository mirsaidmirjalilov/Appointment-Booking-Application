package com.example.insuranceplatform.controller;

import com.example.insuranceplatform.payload.BaseResponse;
import com.example.insuranceplatform.payload.doctor.DoctorRequest;
import com.example.insuranceplatform.payload.doctor.DoctorResponse;
import com.example.insuranceplatform.security.user_details.CustomUserDetails;
import com.example.insuranceplatform.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<BaseResponse<DoctorResponse>> createDoctorProfile(
            @Valid @RequestBody DoctorRequest doctorRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long userId = ((CustomUserDetails) userDetails).user().getId();

        DoctorResponse doctorProfile = doctorService.createDoctorProfile(doctorRequest, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.ok(doctorProfile));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Page<DoctorResponse>>> getAllDoctors(@PageableDefault Pageable pageable) {
        Page<DoctorResponse> allDoctors = doctorService.getAllDoctors(pageable);

        return ResponseEntity.status(200).body(BaseResponse.ok(allDoctors));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<BaseResponse<DoctorResponse>> getDoctorById(@PathVariable Long doctorId) {
        DoctorResponse doctorResponse = doctorService.getDoctorById(doctorId);

        return ResponseEntity.status(200).body(BaseResponse.ok(doctorResponse));
    }

    @PutMapping("/{doctorId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<BaseResponse<DoctorResponse>> updateDoctorProfile(@PathVariable Long doctorId, @Valid @RequestBody DoctorRequest doctorRequest) {
        DoctorResponse doctorResponse = doctorService.updateDoctorProfile(doctorId, doctorRequest);

        return ResponseEntity.status(200).body(BaseResponse.ok(doctorResponse));
    }
}
