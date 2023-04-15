package com.component.authserver.controller;

import com.component.authserver.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/oauth/v2.0/github")
public class GithubController {

    @Autowired
    private GithubService githubService;

    @GetMapping("/redirect")
    public void redirect(@RequestParam String code, HttpServletResponse resp){
        githubService.redirect(code,resp);
    }
}
