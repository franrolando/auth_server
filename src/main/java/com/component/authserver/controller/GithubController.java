package com.component.authserver.controller;

import com.component.authserver.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class GithubController implements IOAuthController {

    @Autowired
    private OAuthService oAuthService;

    @GetMapping("github/redirect")
    public void redirect(@RequestParam String  code, HttpServletResponse resp){
        oAuthService.redirect(code,resp);
    }
}
