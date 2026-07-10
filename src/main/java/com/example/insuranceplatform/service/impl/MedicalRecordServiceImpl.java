package com.example.insuranceplatform.service.impl;

import com.example.insuranceplatform.entity.Appointment;
import com.example.insuranceplatform.entity.MedicalRecord;
import com.example.insuranceplatform.exception.AppointmentNotFoundException;
import com.example.insuranceplatform.mapper.MedicalRecordMapper;
import com.example.insuranceplatform.payload.medical_record.MedicalRecordRequest;
import com.example.insuranceplatform.payload.medical_record.MedicalRecordResponse;
import com.example.insuranceplatform.repository.AppointmentRepository;
import com.example.insuranceplatform.repository.MedicalRecordRepository;
import com.example.insuranceplatform.service.MedicalRecordService;
import com.example.insuranceplatform.util.AppointmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    @Transactional
    public MedicalRecordResponse createRecord(Long appointmentId, MedicalRecordRequest request) {
        boolean exists = medicalRecordRepository.existsMedicalRecordByAppointmentId(appointmentId);

        if (exists) throw new RuntimeException("Appointment with id: " + appointmentId + " already exists");

        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException("Appointment with id: " + appointmentId + " does not exist"));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED)
            throw new RuntimeException("Appointment with id: " + appointmentId + " has not been completed");

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .doctor(appointment.getDoctor())
                .patient(appointment.getPatient())
                .appointment(appointment)
                .diagnosis(request.diagnosis())
                .followUpDate(request.followUpDate())
                .recommendations(request.recommendations())
                .prescription(request.prescription())
                .build();
        medicalRecordRepository.save(medicalRecord);

        return medicalRecordMapper.toMedicalRecordResponse(medicalRecord);
    }

    @Override
    public MedicalRecordResponse getRecordByAppointmentId(Long appointmentId, Long requesterId) {
        MedicalRecord medicalRecord = medicalRecordRepository.findByAppointmentIdAndPatientId(appointmentId, requesterId).orElseThrow(() -> new AppointmentNotFoundException("Appointment with id: " + appointmentId + " does not exist"));

        boolean isPatient = medicalRecord.getPatient().getId().equals(requesterId);
        boolean isDoctor = medicalRecord.getDoctor().getUser().getId().equals(requesterId);

        if (!isPatient && !isDoctor) throw new AccessDeniedException("Access denied");

        return medicalRecordMapper.toMedicalRecordResponse(medicalRecord);
    }

    @Override
    public Page<MedicalRecordResponse> getMyRecords(Long patientId, Pageable pageable) {
        return medicalRecordRepository.findByPatientId(patientId, pageable).map(medicalRecordMapper::toMedicalRecordResponse);
    }
}
