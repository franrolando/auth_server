package com.component.authserver.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "OAuth_entity")
@Data
public class OAuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String provider;
    private String clientId;
    private String clientSecret;
    private String scopes;
    private String authUrl;
    private String redirectUrl;
    private String serverRedirectUrl;

}
