package com.site.slowprint.carbon.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundInCarbonException extends CarbonException {
    private static final String MESSAGE = "탄소 기록을 위한 사용자를 찾을 수 없습니다.";

    public UserNotFoundInCarbonException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatusCode() { return HttpStatus.NOT_FOUND; }
}
