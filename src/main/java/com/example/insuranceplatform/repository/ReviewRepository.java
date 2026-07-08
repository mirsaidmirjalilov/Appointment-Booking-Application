package com.example.insuranceplatform.repository;

import com.example.insuranceplatform.entity.Review;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findReviewByDoctorId(Long doctorId, Pageable pageable);

    boolean existsByAppointmentId(@NotNull Long aLong);
}
