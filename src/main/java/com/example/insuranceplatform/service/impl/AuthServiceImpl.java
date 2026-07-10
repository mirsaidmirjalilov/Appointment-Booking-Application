package com.example.insuranceplatform.service.impl;

import com.example.insuranceplatform.entity.User;
import com.example.insuranceplatform.payload.auth.AuthResponse;
import com.example.insuranceplatform.payload.auth.LoginRequest;
import com.example.insuranceplatform.payload.auth.RegisterRequest;
import com.example.insuranceplatform.repository.UserRepository;
import com.example.insuranceplatform.security.JwtTokenUtil;
import com.example.insuranceplatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {
        String email = loginRequest.email();
        String password = loginRequest.password();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = jwtTokenUtil.generateToken(email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new AuthResponse(token, user.getRole());
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .phone(registerRequest.phoneNumber())
                .role(registerRequest.role())
                .active(true)
                .lastName(registerRequest.lastName())
                .firstName(registerRequest.firstName())
                .build();
        userRepository.save(user);

        return new AuthResponse(user.getEmail(), user.getRole());
    }
}
