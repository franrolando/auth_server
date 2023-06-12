package com.component.authserver.service;

import com.component.authserver.config.CustomLoginConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RedirectLoginService extends LoginService {
    public RedirectLoginService(IUserDetailsService iUserDetailsService, IUsersService iUsersService, CustomLoginConfiguration customLoginConfiguration, PasswordEncoder passwordEncoder) {
        super(iUserDetailsService, iUsersService, customLoginConfiguration, passwordEncoder);
    }

    @Override
    protected void userAuthenticationSuccessfully() {

    }
}
