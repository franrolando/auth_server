package com.component.authserver.controller;

import com.component.authserver.service.GoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/google")
public class GoogleController implements IOAuthController{

    @Autowired
    private GoogleService googleService;

    @GetMapping("/redirect")
    public void redirect(@RequestParam String code, HttpServletResponse resp){
        googleService.redirect(code,resp);
    }

}
