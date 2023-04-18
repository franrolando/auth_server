package com.component.authserver.controller;

import com.component.authserver.service.FacebookService;
import com.component.authserver.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/oauth2/facebook")
public class FacebookController {

    @Autowired
    private FacebookService facebookService;

    @PostMapping
    public void redirect(@RequestParam String code, HttpServletResponse resp){
        facebookService.redirect(code,resp);
    }

    @DeleteMapping
    public void rpgdProcedure(){

    }
}
