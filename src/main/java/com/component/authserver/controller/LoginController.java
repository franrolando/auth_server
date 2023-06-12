package com.component.authserver.controller;

import com.component.authserver.service.ILoginService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private ILoginService iLoginService;

    public LoginController(@Qualifier("CustomFrontEndLoginService") ILoginService iLoginService){
        this.iLoginService = iLoginService;
    }

    @PostMapping("/signIn")
    public void signIn(String username, String password){
        iLoginService.signIn(username, password);
    }

    @PostMapping("/signUp")
    public void signUp(String username, String password){
        iLoginService.signUp(username, password);
    }

}
