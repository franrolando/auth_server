package com.component.authserver.views;

import com.component.authserver.component.ButtonAnchor;
import com.component.authserver.config.Configuration;
import com.component.authserver.entity.OAuthProvider;
import com.component.authserver.handler.OAuthLoginSuccessHandler;
import com.component.authserver.repository.OAuthProviderRepository;
import com.component.authserver.service.ILoginService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Route(value = LoginView.LOGIN_VIEW_ROUTE)
@AnonymousAllowed
@Slf4j
@Tag("login-view")
@JsModule("./views/login-view.js")
public class LoginView extends LitTemplate {

    @Id("usernameTextField")
    private TextField usernameTextField;
    @Id("passwordTextField")
    private PasswordField passwordTextField;
    @Id("forgotPasswordButton")
    private Button forgotPasswordButton;
    @Id("signInButton")
    private Button signInButton;
    @Id("signUpButton")
    private Button signUpButton;
    @Id("oauthDiv")
    private Div oauthDiv;

    public static final String LOGIN_VIEW_ROUTE = "login";
    private static final String OAUTH_URL = "/oauth2/authorization/";
    private static String authorizationRequestBaseUri
            = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls
            = new HashMap<>();

    private ClientRegistrationRepository clientRegistrationRepository;
    private OAuthProviderRepository oAuthProviderRepository;
    private ILoginService iLoginService;

    private OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
    private Configuration configuration;

    public LoginView(ClientRegistrationRepository clientRegistrationRepository, OAuthProviderRepository oAuthProviderRepository, HttpServletRequest request, OAuthLoginSuccessHandler oAuthLoginSuccessHandler, Configuration configuration, ILoginService iLoginService) {
        this.oAuthLoginSuccessHandler = oAuthLoginSuccessHandler;
        this.configuration = configuration;
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.oAuthProviderRepository = oAuthProviderRepository;
        this.iLoginService = iLoginService;
        init();
    }

    public void init() {
        forgotPasswordButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        signInButton.addClickListener(e->{
            try {
                iLoginService.signIn(usernameTextField.getValue(), passwordTextField.getValue());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        signUpButton.addClickListener(e -> {
            Map<String, String> params = new HashMap<>();
            UI.getCurrent().navigate(RegisterView.class, QueryParameters.simple(params));
        });
        oauthButtons(this.clientRegistrationRepository, this.oAuthProviderRepository);
    }

    private void oauthButtons(ClientRegistrationRepository clientRegistrationRepository, OAuthProviderRepository oAuthProviderRepository) {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        List<OAuthProvider> oAuthProviderList = oAuthProviderRepository.findAll();
        log.info("There are {} OAuth providers configured", oAuthProviderList.size());
        clientRegistrations.forEach(registration -> {
            Optional<OAuthProvider> optOAuthProvider = oAuthProviderList.stream().filter(oAuthProvider -> oAuthProvider.getProvider().getName().equalsIgnoreCase(registration.getRegistrationId())).findFirst();
            if (optOAuthProvider.isPresent()) {
                OAuthProvider oAuthProvider = optOAuthProvider.get();
                if (oAuthProvider.isEnabled()) {
                    String src = "";
                    String text = "";
                    String filename = "";
                    String backgroundColor = "";
                    String textColor = "";
                    switch (oAuthProvider.getProvider()) {
                        case GOOGLE -> {
                            backgroundColor = "#EFEFEF";
                            textColor = "black";
                            filename = "icons8-google.svg";
                            text = MessageFormat.format("Sign in with {0}", oAuthProvider.getProvider().getName());
                        }
                        case FACEBOOK -> {
                            backgroundColor = "#16528C";
                            textColor = "white";
                            filename = "icons8-facebook.svg";
                            text = MessageFormat.format("Sign in with {0}", oAuthProvider.getProvider().getName());
                        }
                        case GITHUB -> {
                            backgroundColor = "#424141";
                            textColor = "white";
                            filename = "icons8-github.svg";
                            text = MessageFormat.format("Sign in with {0}", oAuthProvider.getProvider().getName());
                        }
                    }
                    src = "/META-INF/resources/login/icons/" + filename;
                    ButtonAnchor buttonAnchor = new ButtonAnchor(src, filename, text, OAUTH_URL + registration.getRegistrationId(), backgroundColor, textColor);
                    oauthDiv.add(buttonAnchor);
                }
            }
        });
    }

    private void redirectErrorView(String errorMessage) {
        log.info("Redirecting to error view");
        Map<String, String> params = new HashMap<>();
        params.put("error", errorMessage);
        UI.getCurrent().getUI().ifPresent(e -> e.navigate(ErrorView.ERROR_VIEW_ROUTE, QueryParameters.simple(params)));
    }

}
