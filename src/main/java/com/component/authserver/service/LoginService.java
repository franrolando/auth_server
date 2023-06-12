package com.component.authserver.service;

import com.component.authserver.config.CustomLoginConfiguration;
import com.component.authserver.entity.Users;
import com.component.authserver.exception.PasswordCredentialsException;
import com.component.authserver.exception.UsernameCredentialsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public abstract class LoginService implements ILoginService {

    private IUserDetailsService iUserDetailsService;
    private IUsersService iUsersService;
    private PasswordEncoder passwordEncoder;
    protected CustomLoginConfiguration customLoginConfiguration;

    public LoginService(IUserDetailsService iUserDetailsService, IUsersService iUsersService, CustomLoginConfiguration customLoginConfiguration, PasswordEncoder passwordEncoder) {
        this.iUserDetailsService = iUserDetailsService;
        this.iUsersService = iUsersService;
        this.customLoginConfiguration = customLoginConfiguration;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signIn(String username, String password) {
        if (iUserDetailsService.findByEmail(username).isPresent()) {
            log.info("User already registered with OAuth");
            userAuthenticationSuccessfully();
        } else {
            Users user = iUsersService.findByUsernameAndPassword(username, password).orElseThrow(UsernameCredentialsException::new);
            if (passwordEncoder.matches(password, user.getPassword())) {
                userAuthenticationSuccessfully();
            } else {
                throw new PasswordCredentialsException();
            }
        }
    }

    protected abstract void userAuthenticationSuccessfully();

    @Override
    public void signUp(String username, String password) {
        if (iUserDetailsService.findByEmail(username).isPresent()) {
            throw new RuntimeException("Email already registered with an OAuth provider");
        } else {
            iUsersService.save(username, password);
        }
    }


}
