package com.component.authserver.controller;

import com.component.authserver.service.GoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/oauth/v2.0/google")
public class GoogleController {

    @Autowired
    private GoogleService googleService;

    @PostMapping("/redirect")
    public void redirect(@RequestBody String body, HttpServletRequest req, HttpServletResponse resp, @CookieValue(name= "g_csrf_token") String csrfTokenCookie){
        googleService.redirect(body, resp, csrfTokenCookie);
    }

}
