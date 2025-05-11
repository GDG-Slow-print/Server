package com.site.slowprint.user.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsEmailException extends UserException {
    private static final String MESSAGE = "이미 존재하는 이메일입니다.";

    public AlreadyExistsEmailException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
