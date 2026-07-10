package com.example.insuranceplatform.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public record BaseResponse<T>(
        T data,
        Boolean success
) {
    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(data, true);
    }

    public static <T> BaseResponse<T> ok() {
        return new BaseResponse<>(null,  true);
    }
}
