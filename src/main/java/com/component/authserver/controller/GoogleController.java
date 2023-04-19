package com.component.authserver.controller;

import com.component.authserver.service.GoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/oauth2/google")
public class GoogleController {

    @Autowired
    private GoogleService googleService;

    @PostMapping
    public void redirect(@RequestBody String body, HttpServletRequest req, HttpServletResponse resp, @CookieValue(name= "g_csrf_token") String csrfTokenCookie){
        googleService.redirect(body, resp, csrfTokenCookie);
    }

}
