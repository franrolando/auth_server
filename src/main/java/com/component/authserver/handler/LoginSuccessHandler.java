package com.component.authserver.handler;

import com.component.authserver.constants.Provider;
import com.component.authserver.entity.OAuthProvider;
import com.component.authserver.entity.UserDetails;
import com.component.authserver.entity.UserDetailsId;
import com.component.authserver.repository.OAuthProviderRepository;
import com.component.authserver.repository.UserDetailsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.vaadin.flow.spring.security.VaadinSavedRequestAwareAuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.compress.archivers.zip.PKWareExtraHeader;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Setter
@Slf4j
@Component
public class LoginSuccessHandler extends VaadinSavedRequestAwareAuthenticationSuccessHandler {

    private String redirectUrl;
    private OAuth2AuthorizedClientService authorizedClientService;

    private UserDetailsRepository userDetailsRepository;
    private OAuthProviderRepository oAuthProviderRepository;

    public LoginSuccessHandler(OAuth2AuthorizedClientService authorizedClientService, UserDetailsRepository userDetailsRepository, OAuthProviderRepository oAuthProviderRepository){
        this.authorizedClientService = authorizedClientService;
        this.userDetailsRepository = userDetailsRepository;
        this.oAuthProviderRepository = oAuthProviderRepository;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("Login will redirect to {}", redirectUrl);
        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.debug("OAuth2 User {}", oAuth2User);
        setDefaultTargetUrl(this.redirectUrl);
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        auth.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        log.info("Authorized client {}", client);
        OAuthProvider oauthProvider = oAuthProviderRepository.findByProvider(Provider.valueOf(auth.getAuthorizedClientRegistrationId().toUpperCase())).get();
        UserDetailsId userDetailsId = UserDetailsId.builder()
                .userId(oAuth2User.getName())
                .fkProviderId(oauthProvider.getProviderId())
                .build();
        ObjectMapper objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        UserDetails userDetails = UserDetails.builder()
                .id(userDetailsId)
                .uuid(UUID.randomUUID())
                .userAttributes(objectMapper.writeValueAsString(oAuth2User.getAttributes()))
                .lastLogin(LocalDateTime.now())
                .provider(oauthProvider)
                .build();
        userDetailsRepository.save(userDetails);
        super.onAuthenticationSuccess(request,response,authentication);
        log.debug("Redirected");
    }

}
