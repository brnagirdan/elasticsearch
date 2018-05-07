package com.berna.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username){
        super("could not found user '" + username + "'.");
    }
}
