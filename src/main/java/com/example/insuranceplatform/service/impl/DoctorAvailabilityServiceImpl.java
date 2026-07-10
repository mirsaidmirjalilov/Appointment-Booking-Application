package com.example.insuranceplatform.service.impl;

import com.example.insuranceplatform.entity.Doctor;
import com.example.insuranceplatform.entity.DoctorAvailability;
import com.example.insuranceplatform.exception.AvailabilityNotFoundException;
import com.example.insuranceplatform.mapper.DoctorAvailabilityMapper;
import com.example.insuranceplatform.payload.doctor_availability.AvailabilityRequest;
import com.example.insuranceplatform.payload.doctor_availability.AvailabilityResponse;
import com.example.insuranceplatform.repository.AppointmentRepository;
import com.example.insuranceplatform.repository.DoctorAvailabilityRepository;
import com.example.insuranceplatform.repository.DoctorRepository;
import com.example.insuranceplatform.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorAvailabilityMapper doctorAvailabilityMapper;
    private final AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public AvailabilityResponse setAvailability(Long doctorId, AvailabilityRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new UsernameNotFoundException("Doctor Id not found"));

        DoctorAvailability availability = doctorAvailabilityRepository
                .findByDoctorIdAndDayOfWeek(doctorId, request.dayOfWeek())
                .orElse(new DoctorAvailability());

        availability.setDoctor(doctor);
        availability.setDayOfWeek(request.dayOfWeek());
        availability.setStartTime(request.startTime());
        availability.setEndTime(request.endTime());
        availability.setSlotDurationMinutes(request.slotDurationMinutes());

        doctorAvailabilityRepository.save(availability);
        return doctorAvailabilityMapper.toResponse(availability);
    }

    @Override
    public List<AvailabilityResponse> getAvailability(Long doctorId) {
        return doctorAvailabilityRepository.findAll().stream().map(doctorAvailabilityMapper::toResponse).toList();
    }

    @Override
    public List<LocalTime> getAvailableSlots(Long doctorId, LocalDate date) {
        DoctorAvailability availability = doctorAvailabilityRepository
                .findByDoctorIdAndDayOfWeek(doctorId, date.getDayOfWeek())
                .orElseThrow(() -> new AvailabilityNotFoundException("No availability for this day"));

        List<LocalTime> allSlots = new ArrayList<>();
        LocalTime current = LocalTime.from(availability.getStartTime());
        while (current.isBefore(LocalTime.from(availability.getEndTime()))) {
            allSlots.add(current);
            current = current.plusMinutes(availability.getSlotDurationMinutes());
        }

        List<LocalTime> bookedSlots = appointmentRepository
                .findByDoctorIdAndAppointmentDateTimeBetween(
                        doctorId,
                        date.atStartOfDay(),
                        date.atTime(LocalTime.MAX)
                )
                .stream()
                .map(a -> a.getAppointmentDateTime().toLocalTime())
                .toList();

        allSlots.removeAll(bookedSlots);
        return allSlots;
    }

    @Override
    @Transactional
    public void deleteAvailability(Long doctorId) {
        DoctorAvailability doctorAvailability = doctorAvailabilityRepository.findByDoctorId(doctorId).orElseThrow(() -> new AvailabilityNotFoundException("Availability Id not found"));

        doctorAvailability.setIsActive(false);
        doctorAvailabilityRepository.save(doctorAvailability);
    }
}
