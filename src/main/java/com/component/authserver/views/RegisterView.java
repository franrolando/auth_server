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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Route(value = RegisterView.REGISTER_USER_VIEW_ROUTE)
@AnonymousAllowed
@Slf4j
@Tag("register-user")
@JsModule("./views/register-user.js")
public class RegisterView extends LitTemplate implements HasUrlParameter<String> {

    @Id("usernameTextField")
    private TextField usernameTextField;
    @Id("passwordTextField")
    private PasswordField passwordTextField;
    @Id("signUpButton")
    private Button signUpButton;

    public static final String REGISTER_USER_VIEW_ROUTE = "register";
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
    private String redirectURL;

    public RegisterView(ClientRegistrationRepository clientRegistrationRepository, OAuthProviderRepository oAuthProviderRepository, HttpServletRequest request, OAuthLoginSuccessHandler oAuthLoginSuccessHandler, Configuration configuration, ILoginService iLoginService) {
        this.oAuthLoginSuccessHandler = oAuthLoginSuccessHandler;
        this.configuration = configuration;
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.oAuthProviderRepository = oAuthProviderRepository;
        this.iLoginService = iLoginService;
        init();
    }

    public void init() {
        signUpButton.addClickListener(e -> {

        });
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();
        Map<String, List<String>> parametersMap = queryParameters
                .getParameters();
        log.debug("LoginView query String {}", parametersMap);
        this.redirectURL = parametersMap.get("redirect_url") != null ? parametersMap.get("redirect_url").get(0) : null;
    }

    private void redirectErrorView(String errorMessage) {
        log.info("Redirecting to error view");
        Map<String, String> params = new HashMap<>();
        params.put("error", errorMessage);
        UI.getCurrent().getUI().ifPresent(e -> e.navigate(ErrorView.ERROR_VIEW_ROUTE, QueryParameters.simple(params)));
    }

    @Override
    public void onAttach(AttachEvent event) {
        if (this.redirectURL != null) {
            String domainName = this.redirectURL.replaceAll("http(s)?://|www\\.|/.*", "");
            if (domainName.equals(configuration.getApplicationDomain())) {
                log.debug("Url same as domain");
                this.oAuthLoginSuccessHandler.setRedirectUrl(this.redirectURL);
            } else {
                redirectErrorView("Redirect URL its different than domain initiated login request");
            }
        } else {
            redirectErrorView("Redirect URL must not be empty");
        }
    }
}
