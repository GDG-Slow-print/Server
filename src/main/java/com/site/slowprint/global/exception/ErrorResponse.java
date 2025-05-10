package com.site.slowprint.global.exception;



import lombok.Builder;
import org.springframework.http.ResponseEntity;

@Builder
public record ErrorResponse(String code, String message) {
    public static ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.from(errorCode));
    }

    public static ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode, String message) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode, message));
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage() + "-detail message: " + message);
    }
}