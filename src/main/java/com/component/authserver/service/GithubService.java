package com.component.authserver.service;

import com.component.authserver.entity.OAuthEntity;
import com.component.authserver.repository.OAuthRepository;
import com.component.authserver.response.GithubResponse;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class GithubService implements IOAuthService {

    private OAuthRepository oAuthRepository;

    public GithubService(OAuthRepository oAuthRepository){
        this.oAuthRepository = oAuthRepository;
    }

    public void redirect(String code, HttpServletResponse httpServletResponse){
        Optional<OAuthEntity> optionalOAuthEntity = oAuthRepository.findByProvider("Github");
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        String clientId = optionalOAuthEntity.get().getClientId();
        String clientSecret = optionalOAuthEntity.get().getClientSecret();
        String url = "https://github.com/login/oauth/access_token?client_id="+ clientId +"&client_secret="+ clientSecret +"&code="+ code;
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("client_id", "{clientId}")
                .queryParam("client_secret", "{clientSecret}")
                .queryParam("code", "{code}")
                .encode()
                .toUriString();
        Map<String, String> params = new HashMap<>();
        params.put("clientId", clientId);
        params.put("clientSecret", clientSecret);
        params.put("code", code);

        ResponseEntity<GithubResponse> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, GithubResponse.class, params);

        headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/vnd.github+json");
        headers.setBearerAuth(response.getBody().getAccess_token());
        entity = new HttpEntity<>(headers);
        url = "https://api.github.com/user";
        urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .encode()
                .toUriString();
        params = new HashMap<>();
        ResponseEntity<String> userData = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, String.class, params);
        log.info("User data {}", JsonParser.parseString(userData.getBody()));

        httpServletResponse.setHeader("Location", optionalOAuthEntity.get().getRedirectUrl());
        httpServletResponse.setStatus(HttpStatus.FOUND.value());

    }

}
