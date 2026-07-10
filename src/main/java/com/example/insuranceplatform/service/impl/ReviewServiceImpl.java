package com.example.insuranceplatform.service.impl;

import com.example.insuranceplatform.entity.Appointment;
import com.example.insuranceplatform.entity.Review;
import com.example.insuranceplatform.exception.AppointmentNotFoundException;
import com.example.insuranceplatform.mapper.ReviewMapper;
import com.example.insuranceplatform.payload.review.ReviewRequest;
import com.example.insuranceplatform.payload.review.ReviewResponse;
import com.example.insuranceplatform.repository.AppointmentRepository;
import com.example.insuranceplatform.repository.ReviewRepository;
import com.example.insuranceplatform.service.ReviewService;
import com.example.insuranceplatform.util.AppointmentStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final AppointmentRepository appointmentRepository;

    @Override
    @Transactional
    public ReviewResponse createReview(ReviewRequest request, Long patientId) {
        Appointment appointment = appointmentRepository.findById(request.appointmentId()).orElseThrow(() -> new AppointmentNotFoundException("appointment not found"));

        if (!appointment.getPatient().getId().equals(patientId)) {
            throw new RuntimeException("appointment does not belong to patient");
        }

        if (appointment.getStatus() != AppointmentStatus.COMPLETED)
            throw new RuntimeException("Appointment is not completed");

        if (reviewRepository.existsByAppointmentId(request.appointmentId()))
            throw new RuntimeException("Review already exists for this appointment");

        Review review = Review.builder()
                .appointment(appointment)
                .doctor(appointment.getDoctor())
                .patient(appointment.getPatient())
                .comment(request.comment())
                .rating(request.rating())
                .build();
        reviewRepository.save(review);

        return reviewMapper.toReviewResponse(review);
    }

    @Override
    public Page<ReviewResponse> getDoctorReviews(Long doctorId, Pageable pageable) {
        return reviewRepository.findReviewByDoctorId(doctorId, pageable).map(reviewMapper::toReviewResponse);
    }
}
