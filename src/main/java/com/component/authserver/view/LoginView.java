package com.component.authserver.view;

import com.component.authserver.config.Configuration;
import com.component.authserver.entity.OAuthProvider;
import com.component.authserver.handler.LoginSuccessHandler;
import com.component.authserver.repository.OAuthProviderRepository;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("oauth-login")
@AnonymousAllowed
@Slf4j
public class LoginView extends VerticalLayout implements HasUrlParameter<String> {
   /**
    * URL that Spring uses to connect to Google services
    */
   private static final String OAUTH_URL = "/oauth2/authorization/google";
    private static String authorizationRequestBaseUri
            = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls
            = new HashMap<>();

    private ClientRegistrationRepository clientRegistrationRepository;
    private OAuthProviderRepository oAuthProviderRepository;

    private LoginSuccessHandler loginSuccessHandler;
    private Configuration configuration;
   public LoginView(ClientRegistrationRepository clientRegistrationRepository, OAuthProviderRepository oAuthProviderRepository, HttpServletRequest request, LoginSuccessHandler loginSuccessHandler, Configuration configuration) {
       this.loginSuccessHandler = loginSuccessHandler;
       this.configuration = configuration;
       this.clientRegistrationRepository = clientRegistrationRepository;
       this.oAuthProviderRepository = oAuthProviderRepository;
       Iterable<ClientRegistration> clientRegistrations = null;
       ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
               .as(Iterable.class);
       if (type != ResolvableType.NONE &&
               ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
           clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
       }
       List<OAuthProvider> oAuthProviderList = oAuthProviderRepository.findAll();
       clientRegistrations.forEach(registration -> {
           OAuthProvider oAuthProvider1 = oAuthProviderList.stream().filter(oAuthProvider -> oAuthProvider.getProvider().getName().equalsIgnoreCase(registration.getRegistrationId())).findFirst().get();
           if (oAuthProvider1.isEnabled()){
               oauth2AuthenticationUrls.put(registration.getClientName(),
                       authorizationRequestBaseUri + "/" + registration.getRegistrationId());
               Anchor loginLink = new Anchor(OAUTH_URL, MessageFormat.format("Login with {0}", oAuthProvider1.getProvider().getName()));
               add(loginLink);
               loginLink.getElement().setAttribute("router-ignore", true);
           }
       });
       setAlignItems(FlexComponent.Alignment.CENTER);
   }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();
        Map<String, List<String>> parametersMap = queryParameters
                .getParameters();
        log.info("Query String {}", parametersMap);
        if (parametersMap.get("redirect_url") != null){
            try {
                URL redirectUrl = new URL(parametersMap.get("redirect_url").get(0));
                if (redirectUrl.getHost().equals(configuration.getApplicationDomain())){
                    this.loginSuccessHandler.setRedirectUrl(redirectUrl.toString());
                } else {
                    getUI().ifPresent(ui -> ui.navigate("errorView"));
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
