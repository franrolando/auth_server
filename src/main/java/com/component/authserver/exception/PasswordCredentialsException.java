package com.component.authserver.exception;

import org.springframework.http.HttpStatus;

public class PasswordCredentialsException extends CustomRuntimeException {

    public PasswordCredentialsException() {
        super("Password does not match", HttpStatus.UNAUTHORIZED);
    }
}
