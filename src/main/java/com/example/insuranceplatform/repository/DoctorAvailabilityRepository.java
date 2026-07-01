package com.example.insuranceplatform.repository;

import com.example.insuranceplatform.entity.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability,Long> {
}
