package com.component.authserver.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    private LocalDateTime lastLogin;

    @ManyToOne
    @JoinColumn(name = "fk_provider_id")
    @MapsId("fkProviderId")
    private OAuthProvider provider;

}
