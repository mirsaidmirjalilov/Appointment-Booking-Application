package com.example.insuranceplatform.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private String errorMessage;
    private Integer errorCode;
    private String errorPath;
    private final LocalDateTime  timestamp = LocalDateTime.now();
    private final Boolean success = false;

    public static ErrorResponse error(String errorMessage, Integer errorCode, String errorPath) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.errorMessage = errorMessage;
        errorResponse.errorCode = errorCode;
        errorResponse.errorPath = errorPath;
        return errorResponse;
    }
}
