package com.site.slowprint.user.controller;

import com.site.slowprint.global.exception.ErrorResponse;
import com.site.slowprint.user.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionController {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {

        String defaultMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .get();

        return ErrorResponse.builder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST))
                .message(defaultMessage)
                .build();
    }

    @ResponseBody
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> userException(UserException e) {
        HttpStatus statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(statusCode)
                .body(body);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}