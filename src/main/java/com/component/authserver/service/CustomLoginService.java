package com.component.authserver.service;

import com.component.authserver.config.CustomLoginConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomLoginService extends LoginService {
    public CustomLoginService(IUserDetailsService iUserDetailsService, IUsersService iUsersService, CustomLoginConfiguration customLoginConfiguration, PasswordEncoder passwordEncoder) {
        super(iUserDetailsService, iUsersService, customLoginConfiguration, passwordEncoder);
    }

    @Override
    protected void userAuthenticationSuccessfully() {

    }
}
