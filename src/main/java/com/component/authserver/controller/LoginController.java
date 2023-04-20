package com.component.authserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class LoginController {

    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model, OAuth2AuthenticationToken authentication, HttpServletResponse resp){
        log.debug("OAuth login {} success");
        resp.setHeader("Location", "https://www.google.com.ar");
        resp.setStatus(HttpStatus.FOUND.value());
        return "loginSuccess";
    }

}
