package com.example.insuranceplatform.repository;

import com.example.insuranceplatform.entity.MedicalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Long> {

    boolean existsMedicalRecordByAppointmentId(Long appointmentId);

    Optional<MedicalRecord> findByAppointmentIdAndPatientId(Long appointmentId, Long patientId);

    Page<MedicalRecord> findByPatientId(Long patientId, Pageable pageable);
}
