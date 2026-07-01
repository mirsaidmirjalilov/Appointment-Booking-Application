package com.example.insuranceplatform.entity;

import com.example.insuranceplatform.entity.auditable.AudiTableLong;
import com.example.insuranceplatform.util.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends AudiTableLong {

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Pattern(regexp = "^9989[012345789]\\d{7}$")
    private String phone;

    @Column(nullable = false)
    private Boolean active =  false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
}
