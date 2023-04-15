package com.component.authserver.service;

import com.component.authserver.constants.Provider;
import com.component.authserver.entity.OAuthEntity;
import com.component.authserver.repository.OAuthRepository;
import com.component.authserver.template.LoginTemplate;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class GoogleService {

    private OAuthRepository oAuthRepository;

    private LoginTemplate loginTemplate;

    public GoogleService(OAuthRepository oAuthRepository, LoginTemplate loginTemplate){
        this.oAuthRepository = oAuthRepository;
        this.loginTemplate = loginTemplate;
    }

    public void redirect(String body, HttpServletResponse httpServletResponse, String csrfTokenCookie) {
        if (csrfTokenCookie.isEmpty()){
            throw new RuntimeException("No CSRF token cookie");
        }
        String[] props = body.split("&");
        Optional<String> optCredential = Arrays.stream(props).filter(prop -> prop.startsWith("credential")).findFirst();
        Optional<String> optCsrfToken = Arrays.stream(props).filter(prop -> prop.startsWith("g_csrf_token")).findFirst();
        if (!optCsrfToken.isPresent()){
            throw new RuntimeException("No CSRF token in body");
        }
        if (optCsrfToken.isPresent() && optCredential.isPresent()){
            String csrfToken = optCsrfToken.get().substring(optCsrfToken.get().indexOf("=") + 1);
            if (!csrfToken.equals(csrfTokenCookie)){
                throw new RuntimeException("Failed to verify double submit cookie");
            }
            String credential = optCredential.get();
            String accessToken = credential.substring(credential.indexOf("=") + 1);
            Optional<OAuthEntity> optionalOAuthEntity = oAuthRepository.findByProvider(Provider.GOOGLE);

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                        .setAudience(Collections.singletonList("444454141593-g9r6bkk8rsdr29ufdrc67p7metirh4lp.apps.googleusercontent.com"))
                    .build();
            GoogleIdToken idToken = null;
            try {
                idToken = verifier.verify(accessToken);
                if (idToken != null) {
                    GoogleIdToken.Payload payload = idToken.getPayload();
                    String userId = payload.getSubject();

                    String email = payload.getEmail();
                    boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                    String name = (String) payload.get("name");
                    String pictureUrl = (String) payload.get("picture");
                    String locale = (String) payload.get("locale");
                    String familyName = (String) payload.get("family_name");
                    String givenName = (String) payload.get("given_name");
                    log.info("Payload {}", payload);
                    loginTemplate.redirect(httpServletResponse, optionalOAuthEntity.get().getRedirectUrl());
                } else {
                    System.out.println("Invalid ID token.");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
