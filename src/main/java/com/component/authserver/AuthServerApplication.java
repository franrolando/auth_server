package com.component.authserver;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@Theme(value = "mytodo")
public class AuthServerApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

}
