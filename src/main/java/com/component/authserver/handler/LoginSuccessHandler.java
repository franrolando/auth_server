package com.component.authserver.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Setter
@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("Login redirecting to {}", redirectUrl);
        response.sendRedirect(this.redirectUrl);
        log.debug("Redirected");
    }

}
