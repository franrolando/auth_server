package com.component.authserver.service;

import com.component.authserver.entity.Users;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class LoginService implements ILoginService {

    private IUserDetailsService iUserDetailsService;

    private IUsersService iUsersService;

    private PasswordEncoder passwordEncoder;




    public LoginService(IUserDetailsService iUserDetailsService, IUsersService iUsersService){
        this.iUserDetailsService = iUserDetailsService;
        this.iUsersService= iUsersService;
    }

    @Override
    public void signIn(String username, String password) {
        if (iUserDetailsService.findByEmail(username).isPresent()) {
            log.info("User already registered with OAuth");
        } else {
            Optional<Users> optUser = iUsersService.findByUsernameAndPassword(username, password);
            if (optUser.isPresent()) {
                if (passwordEncoder.matches(password, optUser.get().getPassword())) {

                }
            }
        }
    }

    @Override
    public void signUp(String username, String password) {
        if (iUserDetailsService.findByEmail(username).isPresent()) {
            throw new RuntimeException("Email already registered with an OAuth provider");
        } else {
            iUsersService.save(username, password);
        }
    }


}
