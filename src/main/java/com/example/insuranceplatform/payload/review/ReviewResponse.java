package com.example.insuranceplatform.payload.review;

import java.time.LocalDateTime;

public record ReviewResponse(
    Long id,
    Long doctorId,
    String patientName,
    Integer rating,
    String comment,
    LocalDateTime createdAt
) {
}
