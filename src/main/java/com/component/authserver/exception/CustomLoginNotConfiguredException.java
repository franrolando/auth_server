package com.component.authserver.exception;

import org.springframework.http.HttpStatus;

public class CustomLoginNotConfiguredException extends CustomRuntimeException {
    public CustomLoginNotConfiguredException() {
        super("Custom login not configured", HttpStatus.BAD_REQUEST);
    }
}
