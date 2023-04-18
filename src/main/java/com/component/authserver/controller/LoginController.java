package com.component.authserver.controller;

import com.component.authserver.service.GithubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

@RestController("/login/oauth2/code")
@Slf4j
public class LoginController {


    @Autowired
    private GithubService githubService;

    @PostMapping("/github")
    public void googleLogin(Model model, OAuth2AuthenticationToken authentication, HttpServletResponse resp){
        githubService.redirect(model,authentication, resp);
    }

    @GetMapping
    public String loginSuccess(Model model, OAuth2AuthenticationToken authentication){
        log.info("AAAAA");
        return "loginSuccess";
    }

}
