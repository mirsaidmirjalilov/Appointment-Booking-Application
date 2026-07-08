package com.example.insuranceplatform.service;

import com.example.insuranceplatform.payload.medical_record.MedicalRecordRequest;
import com.example.insuranceplatform.payload.medical_record.MedicalRecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicalRecordService {
    MedicalRecordResponse createRecord(Long appointmentId, MedicalRecordRequest request);
    MedicalRecordResponse getRecordByAppointmentId(Long appointmentId, Long requesterId);
    Page<MedicalRecordResponse> getMyRecords(Long patientId, Pageable pageable);
}
