package com.component.authserver.strategy;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class DefaultImplementationStrategy implements UserEmailStrategy {
    @Override
    public String getUserEmail(Authentication authentication) {
        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        return defaultOAuth2User.getAttribute("email");
    }
}
