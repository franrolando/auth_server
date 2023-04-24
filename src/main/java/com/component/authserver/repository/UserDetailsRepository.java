package com.component.authserver.repository;

import com.component.authserver.entity.UserDetails;
import com.component.authserver.entity.UserDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, UserDetailsId> {
}
