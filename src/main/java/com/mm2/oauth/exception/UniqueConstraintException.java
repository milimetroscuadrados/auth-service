package com.mm2.oauth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "Duplicated key")
public class UniqueConstraintException extends RuntimeException {

    public UniqueConstraintException(String var1) {
        super(var1);
    }
}
