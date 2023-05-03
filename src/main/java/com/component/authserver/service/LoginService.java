package com.component.authserver.service;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService implements ILoginService {

    private IUserDetailsService iUserDetailsService;

    private IUsersService iUsersService;

    public LoginService(IUserDetailsService iUserDetailsService, IUsersService iUsersService){
        this.iUserDetailsService = iUserDetailsService;
        this.iUsersService= iUsersService;
    }

    @Override
    public boolean signIn(String username, String password) {
        boolean validSignIn = false;
        if (iUserDetailsService.findByEmail(username).isPresent()) {
            log.info("User already registered with OAuth");
            validSignIn = true;
        } else {

        }
        return validSignIn;
    }
}
