package com.example.insuranceplatform.mapper;

import com.example.insuranceplatform.entity.DoctorAvailability;
import com.example.insuranceplatform.payload.doctor_availability.AvailabilityResponse;
import org.springframework.stereotype.Component;

@Component
public class DoctorAvailabilityMapper {
    public AvailabilityResponse toResponse(DoctorAvailability availability) {
        return new AvailabilityResponse(
                availability.getId(),
                availability.getDayOfWeek(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getSlotDurationMinutes()
        );
    }
}
