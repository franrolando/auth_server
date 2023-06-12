package com.component.authserver.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
public class CustomRuntimeExceptionDTO {

    private HttpStatus httpStatus;
    private String message;
    private String path;
    private LocalDateTime time;
}
