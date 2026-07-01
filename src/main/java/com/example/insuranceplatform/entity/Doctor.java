package com.example.insuranceplatform.entity;

import com.example.insuranceplatform.entity.auditable.AudiTableLong;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor extends AudiTableLong {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(nullable = false)
    private Double consultationFee;

    private Integer yearsOfExperience;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
