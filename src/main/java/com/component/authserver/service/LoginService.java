package com.component.authserver.service;

import com.component.authserver.config.Configuration;
import com.component.authserver.entity.Users;
import com.vaadin.flow.server.VaadinServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class LoginService implements ILoginService {

    private IUserDetailsService iUserDetailsService;

    private IUsersService iUsersService;

    private PasswordEncoder passwordEncoder;
    private Configuration configuration;
    public LoginService(IUserDetailsService iUserDetailsService, IUsersService iUsersService, Configuration configuration) {
        this.iUserDetailsService = iUserDetailsService;
        this.iUsersService = iUsersService;
        this.configuration = configuration;
    }

    @Override
    public void signIn(String username, String password) throws IOException {
        if (iUserDetailsService.findByEmail(username).isPresent()) {
            log.info("User already registered with OAuth");
            redirectToUrl();
        } else {
            Users user = iUsersService.findByUsernameAndPassword(username, password).orElseThrow(RuntimeException::new);
            if (passwordEncoder.matches(password, user.getPassword())) {
                redirectToUrl();
            } else {
                throw new RuntimeException("Password does not match");
            }
        }
    }

    private void redirectToUrl() throws IOException {
        VaadinServletResponse.getCurrent().sendRedirect(this.configuration.getRedirectUrl());
    }

    @Override
    public void signUp(String username, String password) throws IOException {
        if (iUserDetailsService.findByEmail(username).isPresent()) {
            throw new RuntimeException("Email already registered with an OAuth provider");
        } else {
            iUsersService.save(username, password);
        }
    }


}
