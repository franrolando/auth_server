package com.component.authserver.handler;

import com.vaadin.flow.spring.security.VaadinSavedRequestAwareAuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Setter
@Slf4j
@Component
public class LoginSuccessHandler extends VaadinSavedRequestAwareAuthenticationSuccessHandler {

    private String redirectUrl;
    private OAuth2AuthorizedClientService authorizedClientService;

    public LoginSuccessHandler(OAuth2AuthorizedClientService authorizedClientService){
        this.authorizedClientService = authorizedClientService;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("Login will redirect to {}", redirectUrl);
        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.debug("OAuth2 User {}", oAuth2User);
        setDefaultTargetUrl(this.redirectUrl);
        super.onAuthenticationSuccess(request,response,authentication);
        log.debug("Redirected");
    }

}
