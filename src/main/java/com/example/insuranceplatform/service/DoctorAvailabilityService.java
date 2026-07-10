package com.example.insuranceplatform.service;

import com.example.insuranceplatform.payload.doctor_availability.AvailabilityRequest;
import com.example.insuranceplatform.payload.doctor_availability.AvailabilityResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DoctorAvailabilityService {
    AvailabilityResponse setAvailability(Long doctorId, AvailabilityRequest request);
    List<AvailabilityResponse> getAvailabilityForDoctor(Long doctorId);
    List<LocalTime> getAvailableSlots(Long doctorId, LocalDate date);
    void deleteAvailability(Long doctorId);
}
