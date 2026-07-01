package com.example.insuranceplatform.mapper;

import com.example.insuranceplatform.entity.MedicalRecord;
import com.example.insuranceplatform.payload.medical_record.MedicalRecordResponse;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMapper {
    public MedicalRecordResponse toMedicalRecordResponse(MedicalRecord medicalRecord) {
        return new MedicalRecordResponse(
                medicalRecord.getId(),
                medicalRecord.getDiagnosis(),
                medicalRecord.getPrescription(),
                medicalRecord.getRecommendations(),
                medicalRecord.getFollowUpDate(),
                medicalRecord.getAppointment().getId(),
                medicalRecord.getDoctor().getUser().getFirstName(),
                medicalRecord.getPatient().getFirstName(),
                medicalRecord.getCreatedAt()
        );
    }
}
