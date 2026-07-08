package com.example.insuranceplatform.mapper;

import com.example.insuranceplatform.entity.Review;
import com.example.insuranceplatform.payload.review.ReviewResponse;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewResponse  toReviewResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getDoctor().getId(),
                review.getPatient().getFirstName(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}
