package com.example.insuranceplatform.controller;

import com.example.insuranceplatform.payload.BaseResponse;
import com.example.insuranceplatform.payload.review.ReviewRequest;
import com.example.insuranceplatform.payload.review.ReviewResponse;
import com.example.insuranceplatform.security.user_details.CustomUserDetails;
import com.example.insuranceplatform.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<BaseResponse<ReviewResponse>> createReview(
            @Valid @RequestBody ReviewRequest reviewRequest,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long id = customUserDetails.user().getId();

        ReviewResponse review = reviewService.createReview(reviewRequest, id);

        return ResponseEntity.status(201).body(BaseResponse.ok(review));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<BaseResponse<Page<ReviewResponse>>> getReviewByDoctorId(
            @PathVariable Long doctorId,
            @PageableDefault Pageable pageable
    ) {
        Page<ReviewResponse> doctorReviews = reviewService.getDoctorReviews(doctorId, pageable);

        return ResponseEntity.status(200).body(BaseResponse.ok(doctorReviews));
    }
}
