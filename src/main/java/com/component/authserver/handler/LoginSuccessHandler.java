package com.component.authserver.handler;

import com.component.authserver.config.Configuration;
import com.vaadin.flow.component.UI;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;


@Setter
@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private String redirectUrl;
    private Optional<UI> ui;
    private Configuration configuration;

    public LoginSuccessHandler(Configuration configuration){
        this.configuration = configuration;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Login redirecting to {}", redirectUrl);
        try {
            URL redirectUrl = new URL(this.redirectUrl);
            if (redirectUrl.getHost().equals(configuration.getApplicationDomain())){
                response.sendRedirect(this.redirectUrl);
                log.info("Redirected");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
