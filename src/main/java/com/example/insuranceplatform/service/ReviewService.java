package com.example.insuranceplatform.service;

import com.example.insuranceplatform.payload.review.ReviewRequest;
import com.example.insuranceplatform.payload.review.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest request, Long patientId);
    Page<ReviewResponse> getDoctorReviews(Long doctorId, Pageable pageable);
}
