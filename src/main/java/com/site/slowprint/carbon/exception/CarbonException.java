package com.site.slowprint.carbon.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CarbonException extends RuntimeException {

    public CarbonException(String message) {
        super(message);
    }
    public abstract HttpStatus getStatusCode();
}
