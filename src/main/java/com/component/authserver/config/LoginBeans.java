package com.component.authserver.config;

import com.component.authserver.service.CustomLoginService;
import com.component.authserver.service.ILoginService;
import com.component.authserver.service.IUserDetailsService;
import com.component.authserver.service.IUsersService;
import com.component.authserver.service.RedirectLoginService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class LoginBeans {

    private CustomLoginConfiguration customLoginConfiguration;

    public LoginBeans(CustomLoginConfiguration customLoginConfiguration) {
        this.customLoginConfiguration = customLoginConfiguration;
    }

    @Bean(name = "CustomFrontEndLoginService")
    public ILoginService loginService(IUserDetailsService iUserDetailsService, IUsersService iUsersService, CustomLoginConfiguration customLoginConfiguration, PasswordEncoder passwordEncoder) {
        ILoginService iLoginService = null;
        if (this.customLoginConfiguration.isEnableRedirect()) {
            iLoginService = new RedirectLoginService(iUserDetailsService, iUsersService, customLoginConfiguration, passwordEncoder);
        } else {
            iLoginService = new CustomLoginService(iUserDetailsService, iUsersService, customLoginConfiguration, passwordEncoder);
        }
        return iLoginService;
    }

}
