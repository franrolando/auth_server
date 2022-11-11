package com.component.authserver.service;

import com.component.authserver.response.GithubResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class OAuthService {

    public void redirect(String code, HttpServletResponse httpServletResponse){
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://github.com/login/oauth/access_token?client_id=693f3da9f92cd15aa2b8&client_secret=286c4918012eb81a1fed26ce0502a1d49c94e597&code="+ code;
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("client_id", "{clientId}")
                .queryParam("client_secret", "{clientSecret}")
                .queryParam("code", "{code}")
                .encode()
                .toUriString();
        Map<String, String> params = new HashMap<>();
        params.put("clientId", "asd");
        params.put("clientSecret", "asd");
        params.put("code", code);

        ResponseEntity<GithubResponse> response = restTemplate.exchange(urlTemplate, HttpMethod.GET, entity, GithubResponse.class, params);
        log.info("{}",response.getBody());
        httpServletResponse.setHeader("Location", "https://www.google.com");
        httpServletResponse.setStatus(HttpStatus.FOUND.value());
    }

}
