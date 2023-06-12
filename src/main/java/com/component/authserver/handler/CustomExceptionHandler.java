package com.component.authserver.handler;

import com.component.authserver.dto.CustomRuntimeExceptionDTO;
import com.component.authserver.exception.CustomRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<CustomRuntimeExceptionDTO> handleCustomRuntimeException(CustomRuntimeException exception, HttpServletRequest httpServletRequest) {
        CustomRuntimeExceptionDTO exceptionDTO = CustomRuntimeExceptionDTO.builder()
                .httpStatus(exception.getHttpStatus())
                .message(exception.getMessage())
                .path(httpServletRequest.getServletPath())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(exceptionDTO, exception.getHttpStatus());
    }
}
