package com.component.authserver.controller;

import com.component.authserver.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/oauth2/github")
public class GithubController {

    @Autowired
    private GithubService githubService;

    @PostMapping
    public void redirect(Model model, OAuth2AuthenticationToken authentication, HttpServletResponse resp){
        githubService.redirect(model, authentication, resp);
    }

    @GetMapping
    public void redirect2(Model model, OAuth2AuthenticationToken authentication, HttpServletResponse resp){
        githubService.redirect(model, authentication, resp);
    }

}
