package com.component.authserver.repository;

import com.component.authserver.constants.Provider;
import com.component.authserver.entity.OAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthRepository extends JpaRepository<OAuthEntity, Long> {

    Optional<OAuthEntity> findByProvider(Provider provider);
}
