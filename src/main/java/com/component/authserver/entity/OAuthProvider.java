package com.component.authserver.entity;

import com.component.authserver.constants.Provider;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "oauth_provider")
@Data
public class OAuthProvider {

    @Id
    @Enumerated(value = EnumType.STRING)
    private Provider provider;
    private boolean enabled;

}
