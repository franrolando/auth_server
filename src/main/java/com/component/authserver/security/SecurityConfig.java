package com.component.authserver.security;

import com.component.authserver.handler.OAuthLoginSuccessHandler;
import com.component.authserver.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends VaadinWebSecurity {

    private OAuthLoginSuccessHandler oAuthLoginSuccessHandler;

    public SecurityConfig(OAuthLoginSuccessHandler oAuthLoginSuccessHandler){
        this.oAuthLoginSuccessHandler = oAuthLoginSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringRequestMatchers("/login/**");
        http.authorizeHttpRequests().requestMatchers("/oauth2/authorization/**","/login/**", "/VAADIN/**", "/META-INF/**").permitAll();
        http.oauth2Login().loginPage("/" + LoginView.LOGIN_VIEW_ROUTE).successHandler(oAuthLoginSuccessHandler);
        super.configure(http);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
