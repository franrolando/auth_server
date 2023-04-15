package com.component.authserver.entity;

import com.component.authserver.constants.Provider;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "oauth_provider")
@Data
public class OAuthEntity {

    @Id
    @Enumerated(value = EnumType.STRING)
    private Provider provider;
    private String clientId;
    private String clientSecret;
    private String scopes;
    private String authUrl;
    private String redirectUrl;
    private Boolean enabled;

}
