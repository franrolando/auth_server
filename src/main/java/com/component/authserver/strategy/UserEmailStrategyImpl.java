package com.component.authserver.strategy;

import com.component.authserver.constants.Provider;
import com.component.authserver.entity.OAuthProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class UserEmailStrategyImpl implements UserEmailStrategy {

    private UserEmailStrategy userEmailStrategy;

    private OAuth2AuthorizedClientService authorizedClientService;

    public UserEmailStrategyImpl(OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
        this.authorizedClientService = oAuth2AuthorizedClientService;
    }

    @Override
    public String getUserEmail(Authentication authentication) {
        setUserEmailStrategy(authentication);
        return userEmailStrategy.getUserEmail(authentication);
    }

    private void setUserEmailStrategy(Authentication authentication){
        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
        switch (Provider.valueOf(auth.getAuthorizedClientRegistrationId().toUpperCase())) {
            case GITHUB -> this.userEmailStrategy = new GitHubUserEmail(authorizedClientService);
            default -> this.userEmailStrategy = new DefaultImplementationStrategy();
        }
    }

}
