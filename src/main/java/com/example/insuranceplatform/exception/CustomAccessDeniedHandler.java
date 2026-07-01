package com.example.insuranceplatform.exception;

import com.example.insuranceplatform.payload.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String contextPath = request.getContextPath();
        String errorMessage = accessDeniedException.getMessage();
        int status = response.getStatus();
        ServletOutputStream outputStream = response.getOutputStream();
        objectMapper.writeValue(outputStream, ErrorResponse.error(errorMessage, status, contextPath));
    }
}
