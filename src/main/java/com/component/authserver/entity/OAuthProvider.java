package com.component.authserver.entity;

import com.component.authserver.constants.Provider;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "oauth_provider")
@Data
public class OAuthProvider implements Serializable {

    @Enumerated(EnumType.ORDINAL)
    @Id
    private Provider providerId;
    @Enumerated(value = EnumType.STRING)
    private Provider provider;
    private boolean enabled;

    @OneToMany(mappedBy = "provider")
    private Collection<UserDetails> users;

}
