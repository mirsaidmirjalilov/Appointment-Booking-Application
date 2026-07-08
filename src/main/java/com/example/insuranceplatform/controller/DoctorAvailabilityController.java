package com.example.insuranceplatform.controller;

import com.example.insuranceplatform.payload.BaseResponse;
import com.example.insuranceplatform.payload.doctor_availability.AvailabilityRequest;
import com.example.insuranceplatform.payload.doctor_availability.AvailabilityResponse;
import com.example.insuranceplatform.service.DoctorAvailabilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController("/api/v1/availabilities")
@RequiredArgsConstructor
public class DoctorAvailabilityController {
    private final DoctorAvailabilityService doctorAvailabilityService;

    @PostMapping("/{doctorId}")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    public ResponseEntity<BaseResponse<AvailabilityResponse>> createAvailability(@PathVariable Long doctorId, @Valid @RequestBody AvailabilityRequest request){
        AvailabilityResponse availabilityResponse = doctorAvailabilityService.setAvailability(doctorId, request);

        return ResponseEntity.status(201).body(BaseResponse.ok(availabilityResponse));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<BaseResponse<List<AvailabilityResponse>>> getAvailability(@PathVariable Long doctorId){
        List<AvailabilityResponse> availability = doctorAvailabilityService.getAvailability(doctorId);

        return ResponseEntity.status(200).body(BaseResponse.ok(availability));
    }

    @GetMapping("/{doctorId}/slots")
    public ResponseEntity<BaseResponse<List<LocalTime>>>  getAvailabilitySlots(@PathVariable Long doctorId, @RequestParam LocalDate date){
        List<LocalTime> availableSlots = doctorAvailabilityService.getAvailableSlots(doctorId, date);

        return ResponseEntity.status(200).body(BaseResponse.ok(availableSlots));
    }

    @DeleteMapping("/{availabilityId}")
    @PreAuthorize("hasAnyRole('DOCTOR','ADMIN')")
    public ResponseEntity<BaseResponse> deleteAvailability(@PathVariable Long availabilityId){
        doctorAvailabilityService.deleteAvailability(availabilityId);

        return ResponseEntity.status(200).body(BaseResponse.ok());
    }
}
