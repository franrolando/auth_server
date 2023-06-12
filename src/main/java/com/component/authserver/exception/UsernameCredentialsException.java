package com.component.authserver.exception;

import org.springframework.http.HttpStatus;

public class UsernameCredentialsException extends CustomRuntimeException {

    public UsernameCredentialsException() {
        super("Username not found", HttpStatus.UNAUTHORIZED);
    }
}
