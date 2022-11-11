package com.component.authserver.repository;

import com.component.authserver.entity.OAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthRepository extends JpaRepository<OAuthEntity, Long> {
}
