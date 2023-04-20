package com.component.authserver.entity;

import com.component.authserver.constants.Provider;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "oauth_provider")
@Data
public class OAuthProvider {

    @Id
    @Enumerated(value = EnumType.STRING)
    private Provider provider;
    private boolean enabled;

}
