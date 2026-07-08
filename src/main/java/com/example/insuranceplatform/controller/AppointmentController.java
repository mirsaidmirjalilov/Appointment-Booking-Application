package com.example.insuranceplatform.controller;

import com.example.insuranceplatform.payload.BaseResponse;
import com.example.insuranceplatform.payload.appointment.AppointmentRequest;
import com.example.insuranceplatform.payload.appointment.AppointmentResponse;
import com.example.insuranceplatform.security.user_details.CustomUserDetails;
import com.example.insuranceplatform.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<BaseResponse<AppointmentResponse>> createAppointment(
            @Valid @RequestBody AppointmentRequest appointmentRequest,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long id = customUserDetails.user().getId();
        AppointmentResponse appointmentResponse = appointmentService.bookAppointment(appointmentRequest, id);

        return ResponseEntity.status(201).body(BaseResponse.ok(appointmentResponse));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    public ResponseEntity<BaseResponse<Page<AppointmentResponse>>> getAppointment(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PageableDefault Pageable pageable
    ) {
        Long id = customUserDetails.user().getId();
        Page<AppointmentResponse> myAppointments = appointmentService.getMyAppointments(id,pageable);

        return ResponseEntity.status(200).body(BaseResponse.ok(myAppointments));
    }

    @PatchMapping("/{appointmentId}/confirm")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<BaseResponse<AppointmentResponse>> confirmAppointment(@PathVariable Long appointmentId) {
        AppointmentResponse appointmentResponse = appointmentService.confirmAppointment(appointmentId);

        return ResponseEntity.status(200).body(BaseResponse.ok(appointmentResponse));
    }

    @PatchMapping("/{appointmentId}/cancel")
    public ResponseEntity<BaseResponse<AppointmentResponse>> cancelAppointment(@PathVariable Long appointmentId) {
        AppointmentResponse appointmentResponse = appointmentService.cancelAppointment(appointmentId);

        return ResponseEntity.status(200).body(BaseResponse.ok(appointmentResponse));
    }

    @PatchMapping("/{appointmentId}/no-show")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<BaseResponse<AppointmentResponse>> noShowAppointment(@PathVariable Long appointmentId) {
        AppointmentResponse appointmentResponse = appointmentService.markNoShow(appointmentId);

        return ResponseEntity.status(200).body(BaseResponse.ok(appointmentResponse));
    }

    @PatchMapping("/{appointmentId}/completed")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<BaseResponse<AppointmentResponse>> completedAppointment(@PathVariable Long appointmentId) {
        AppointmentResponse appointmentResponse = appointmentService.completeAppointment(appointmentId);

        return ResponseEntity.status(200).body(BaseResponse.ok(appointmentResponse));
    }
}
