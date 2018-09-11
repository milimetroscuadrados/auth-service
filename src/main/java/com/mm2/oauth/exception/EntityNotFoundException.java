package com.mm2.oauth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "Entity not found")
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String var1) {
        super(var1);
    }
}
