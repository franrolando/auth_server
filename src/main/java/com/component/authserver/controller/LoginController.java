package com.component.authserver.controller;

import com.component.authserver.service.ILoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private ILoginService loginService;

    @PostMapping("/signIn")
    public void signIn(String username, String password){
        loginService.signIn(username, password);
    }

    @PostMapping("/signUp")
    public void signUp(String username, String password){
        loginService.signUp(username, password);
    }

}
