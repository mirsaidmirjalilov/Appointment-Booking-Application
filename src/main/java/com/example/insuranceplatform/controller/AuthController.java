package com.example.insuranceplatform.controller;

import com.example.insuranceplatform.payload.BaseResponse;
import com.example.insuranceplatform.payload.auth.AuthResponse;
import com.example.insuranceplatform.payload.auth.LoginRequest;
import com.example.insuranceplatform.payload.auth.RegisterRequest;
import com.example.insuranceplatform.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse register = authService.register(registerRequest);

        return ResponseEntity.status(201).body(BaseResponse.ok(register));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse login = authService.login(loginRequest);

        return ResponseEntity.status(200).body(BaseResponse.ok(login));
    }
}
