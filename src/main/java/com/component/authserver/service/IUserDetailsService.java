package com.component.authserver.service;

import com.component.authserver.entity.UserDetails;

import java.util.Optional;

public interface IUserDetailsService {

    Optional<UserDetails> findByEmail(String email);

}
