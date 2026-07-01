package com.example.insuranceplatform.service;

import com.example.insuranceplatform.payload.auth.AuthResponse;
import com.example.insuranceplatform.payload.auth.LoginRequest;
import com.example.insuranceplatform.payload.auth.RegisterRequest;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {
    AuthResponse login(@RequestBody LoginRequest loginRequest);

    AuthResponse register(@RequestBody RegisterRequest registerRequest);
}
