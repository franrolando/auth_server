package com.component.authserver.entity;

import com.component.authserver.constants.Provider;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
