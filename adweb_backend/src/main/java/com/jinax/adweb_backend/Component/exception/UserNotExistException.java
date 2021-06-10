package com.jinax.adweb_backend.Component.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author : chara
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UserNotExistException extends RuntimeException{
    String message;

    public UserNotExistException(String message) {
        super(message);
        this.message = message;
    }
}
