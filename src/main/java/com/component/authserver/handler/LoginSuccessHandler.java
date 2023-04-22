package com.component.authserver.handler;

import com.vaadin.flow.spring.security.VaadinSavedRequestAwareAuthenticationSuccessHandler;
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
public class LoginSuccessHandler extends VaadinSavedRequestAwareAuthenticationSuccessHandler {

    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Login successful to provider {}", authentication);
        log.debug("Login redirecting to {}", redirectUrl);
        setDefaultTargetUrl(this.redirectUrl);
        super.onAuthenticationSuccess(request,response,authentication);
        log.debug("Redirected");
    }

}
