package com.component.authserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "OAuth_entity")
public class OAuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String applicationName;
    private String clientId;
    private String clientSecret;
    private String scopes;
    private String authUrl;
    private String redirectUrl;

}
