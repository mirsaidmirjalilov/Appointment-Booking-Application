package com.example.insuranceplatform.repository;

import com.example.insuranceplatform.entity.DoctorAvailability;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Optional;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability,Long> {

    Optional<DoctorAvailability> findByDoctorIdAndDayOfWeek(Long doctorId, @NotNull DayOfWeek dayOfWeek);

    Optional<DoctorAvailability> findByDoctorId(Long doctorId);
}
