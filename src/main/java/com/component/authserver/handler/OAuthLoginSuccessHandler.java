package com.component.authserver.handler;

import com.component.authserver.constants.Provider;
import com.component.authserver.entity.OAuthProvider;
import com.component.authserver.entity.UserDetails;
import com.component.authserver.entity.UserDetailsId;
import com.component.authserver.repository.OAuthProviderRepository;
import com.component.authserver.repository.UserDetailsRepository;
import com.component.authserver.strategy.UserEmailStrategyImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.vaadin.flow.spring.security.VaadinSavedRequestAwareAuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Setter
@Slf4j
@Component
public class OAuthLoginSuccessHandler extends VaadinSavedRequestAwareAuthenticationSuccessHandler {

    private String redirectUrl;
    private OAuth2AuthorizedClientService authorizedClientService;
    private UserDetailsRepository userDetailsRepository;
    private OAuthProviderRepository oAuthProviderRepository;
    private UserEmailStrategyImpl userEmailStrategy;

    public OAuthLoginSuccessHandler(OAuth2AuthorizedClientService authorizedClientService, UserDetailsRepository userDetailsRepository, OAuthProviderRepository oAuthProviderRepository, UserEmailStrategyImpl userEmailStrategy){
        this.authorizedClientService = authorizedClientService;
        this.userDetailsRepository = userDetailsRepository;
        this.oAuthProviderRepository = oAuthProviderRepository;
        this.userEmailStrategy = userEmailStrategy;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("Login will redirect to {}", redirectUrl);
        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        setDefaultTargetUrl(this.redirectUrl);
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
                .email(userEmailStrategy.getUserEmail(authentication))
                .lastLogin(LocalDateTime.now())
                .provider(oauthProvider)
                .build();
        Optional<UserDetails> existingUser = userDetailsRepository.findByEmail(userDetails.getEmail());
        if (!existingUser.isPresent()){
            userDetailsRepository.save(userDetails);
        } else {
            log.info("User already logged with {} provider", existingUser.get().getProvider().getProvider());
        }
        super.onAuthenticationSuccess(request,response,authentication);
        log.debug("Redirected");
    }

}
