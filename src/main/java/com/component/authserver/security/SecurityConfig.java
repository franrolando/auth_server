package com.component.authserver.security;

import com.component.authserver.handler.LoginSuccessHandler;
import com.component.authserver.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends VaadinWebSecurity {

    private LoginSuccessHandler loginSuccessHandler;

    public SecurityConfig(LoginSuccessHandler loginSuccessHandler){
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers("/oauth2/authorization/**", "/VAADIN/**").permitAll();
        http.oauth2Login().loginPage("/" + LoginView.LOGIN_VIEW_ROUTE).successHandler(loginSuccessHandler);
        super.configure(http);
    }

}
