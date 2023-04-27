package com.component.authserver.views;

import com.component.authserver.component.ButtonAnchor;
import com.component.authserver.config.Configuration;
import com.component.authserver.entity.OAuthProvider;
import com.component.authserver.handler.LoginSuccessHandler;
import com.component.authserver.repository.OAuthProviderRepository;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Route(value = LoginView.LOGIN_VIEW_ROUTE)
@AnonymousAllowed
@Slf4j
@Tag("login-view")
@JsModule("./login/login-view.js")
public class LoginView extends LitTemplate implements HasUrlParameter<String> {

    @Id("usernameTextField")
    private TextField usernameTextField;
    @Id("passwordTextField")
    private PasswordField passwordTextField;
    @Id("forgotPasswordButton")
    private Button forgotPasswordButton;
    @Id("loginButton")
    private Button loginButton;
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

    private LoginSuccessHandler loginSuccessHandler;
    private Configuration configuration;
    private String redirectURL;

    public LoginView(ClientRegistrationRepository clientRegistrationRepository, OAuthProviderRepository oAuthProviderRepository, HttpServletRequest request, LoginSuccessHandler loginSuccessHandler, Configuration configuration) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.configuration = configuration;
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.oAuthProviderRepository = oAuthProviderRepository;
        init();
    }

    public void init() {
        forgotPasswordButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
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
                    String message = "";
                    switch (oAuthProvider.getProvider()) {
                        case GOOGLE -> {
                            src = "/META-INF/resources/brandguidelines/google/google_signin_buttons/web/2x/btn_google_signin_light_normal_web@2x.png";
                        }
                        case FACEBOOK -> {
                            src = "";
                            message = MessageFormat.format("Login with {0}", oAuthProvider.getProvider().getName());

                        }
                        case GITHUB -> {
                            src = "";
                            message = MessageFormat.format("Login with {0}", oAuthProvider.getProvider().getName());
                        }
                    }
                    ButtonAnchor buttonAnchor = new ButtonAnchor(src, message, OAUTH_URL + registration.getRegistrationId());
                    oauthDiv.add(buttonAnchor);
                }
            }
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
                this.loginSuccessHandler.setRedirectUrl(this.redirectURL);
                oauthButtons(clientRegistrationRepository, oAuthProviderRepository);
            } else {
                redirectErrorView("Redirect URL its different than domain initiated login request");
            }
        } else {
            redirectErrorView("Redirect URL must not be empty");
        }
    }
}
