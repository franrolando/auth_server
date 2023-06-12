package com.component.authserver.views;

import com.component.authserver.config.CustomLoginConfiguration;
import com.component.authserver.handler.OAuthLoginSuccessHandler;
import com.component.authserver.repository.OAuthProviderRepository;
import com.component.authserver.service.ILoginService;
import com.component.authserver.service.VaadinLoginService;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.util.HashMap;
import java.util.Map;

@Route(value = RegisterView.REGISTER_USER_VIEW_ROUTE)
@AnonymousAllowed
@Slf4j
@Tag("register-user")
@JsModule("./views/register-user.js")
public class RegisterView extends LitTemplate {

    @Id("usernameTextField")
    private TextField usernameTextField;
    @Id("passwordTextField")
    private PasswordField passwordTextField;
    @Id("nameTextField")
    private TextField nameTextField;
    @Id("surnameTextField")
    private TextField surnameTextField;
    @Id("countryTextField")
    private TextField countryTextField;
    @Id("stateTextField")
    private TextField stateTextField;
    @Id("cityTextField")
    private TextField cityTextField;
    @Id("signUpButton")
    private Button signUpButton;

    public static final String REGISTER_USER_VIEW_ROUTE = "register";

    private ILoginService iLoginService;

    private CustomLoginConfiguration customLoginConfiguration;

    public RegisterView(CustomLoginConfiguration customLoginConfiguration, @Qualifier(VaadinLoginService.NAME) ILoginService iLoginService) {
        this.customLoginConfiguration = customLoginConfiguration;
        this.iLoginService = iLoginService;
        init();
    }

    public void init() {
        signUpButton.addClickListener(e -> {

        });
    }

}
