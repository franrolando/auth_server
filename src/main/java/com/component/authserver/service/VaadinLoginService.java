package com.component.authserver.service;

import com.component.authserver.config.CustomLoginConfiguration;
import com.vaadin.flow.server.VaadinServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service(VaadinLoginService.NAME)
public class VaadinLoginService extends LoginService {

    public static final String NAME = "VaadinLoginService";

    public VaadinLoginService(IUserDetailsService iUserDetailsService, IUsersService iUsersService, CustomLoginConfiguration customLoginConfiguration, PasswordEncoder passwordEncoder) {
        super(iUserDetailsService, iUsersService, customLoginConfiguration, passwordEncoder);
    }

    @Override
    protected void userAuthenticationSuccessfully() {
        try {
            VaadinServletResponse.getCurrent().sendRedirect(this.customLoginConfiguration.getRedirectUrl());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
