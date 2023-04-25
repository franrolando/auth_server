package com.component.authserver.strategy;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

public interface UserEmailStrategy {

    String getUserEmail(Authentication authentication);

}
