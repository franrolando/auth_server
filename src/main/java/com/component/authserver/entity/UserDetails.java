package com.component.authserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.UniqueElements;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_details")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails implements Serializable {

    @EmbeddedId
    private UserDetailsId id;
    private UUID uuid;
    @JdbcTypeCode(SqlTypes.JSON)
    private String userAttributes;
    @NotNull
    private String email;
    private LocalDateTime lastLogin;

    @ManyToOne
    @JoinColumn(name = "fk_provider_id")
    @MapsId("fkProviderId")
    private OAuthProvider provider;

}
