package com.example.insuranceplatform.entity.auditable;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class AudiTable {
    @Column(name = "created_at",nullable = false,updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "created_by",nullable = false,updatable = false)
    @CreatedBy
    private String createdBy;

    @Column(name = "updated_by",nullable = false)
    @LastModifiedBy
    private String updatedBy;
}
