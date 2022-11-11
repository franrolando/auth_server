package com.component.authserver.repository;

import com.component.authserver.entity.OAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthRepository extends JpaRepository<OAuthEntity, Long> {

    Optional<OAuthEntity> findByApplicationName(String applicationName);
}
