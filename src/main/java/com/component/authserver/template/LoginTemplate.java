package com.component.authserver.template;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginTemplate {

    public void redirect(HttpServletResponse httpServletResponse, String redirectUrl){
        httpServletResponse.setHeader("Location", redirectUrl);
        httpServletResponse.setStatus(HttpStatus.FOUND.value());
        log.info("Redirected successfully to {}", redirectUrl);
    }

}
