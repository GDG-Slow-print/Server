package com.site.slowprint.user.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserException {
    private static final String MESSAGE = "사용자를 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

}
