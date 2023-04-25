package com.component.authserver.strategy;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.sql.Array;
import java.util.*;

@Slf4j
public class GitHubUserEmail implements UserEmailStrategy {

    private OAuth2AuthorizedClientService authorizedClientService;

    public GitHubUserEmail(OAuth2AuthorizedClientService oAuth2AuthorizedClientService){
        this.authorizedClientService = oAuth2AuthorizedClientService;
    }

    @Override
    public String getUserEmail(Authentication authentication) {
        String userEmail = "";
        OAuth2AuthenticationToken auth = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        auth.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();
        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());
            HttpEntity entity = new HttpEntity("", headers);
            ResponseEntity<String> response = restTemplate
                    .exchange(userInfoEndpointUri + "/emails", HttpMethod.GET, entity, String.class);
            String userAttributes = response.getBody();
            log.info("User attributes {}", userAttributes);
            JsonArray userEmails = JsonParser.parseString(userAttributes).getAsJsonArray();
            Iterator<JsonElement> it = userEmails.iterator();
            Boolean isPrincipal = false;
            while (it.hasNext() && !isPrincipal) {
                JsonElement email = it.next();
                isPrincipal = email.getAsJsonObject().get("primary").getAsBoolean();
                userEmail = email.getAsJsonObject().get("email").getAsString();
            }
        }
        return userEmail;
    }
}
