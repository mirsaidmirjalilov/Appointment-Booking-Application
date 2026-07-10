package com.example.insuranceplatform.repository;

import com.example.insuranceplatform.entity.MedicalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Long> {

    boolean existsMedicalRecordByAppointmentId(Long appointmentId);

    @Query("SELECT m FROM MedicalRecord m " +
            "WHERE m.appointment.id = :appointmentId " +
            "AND (m.patient.id = :requesterId OR m.doctor.user.id = :requesterId)")
    Optional<MedicalRecord> findByAppointmentIdAndRequester(
            @Param("appointmentId") Long appointmentId,
            @Param("requesterId") Long requesterId
    );

    Page<MedicalRecord> findByPatientId(Long patientId, Pageable pageable);
}
