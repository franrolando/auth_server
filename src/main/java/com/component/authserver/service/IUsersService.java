package com.component.authserver.service;

import com.component.authserver.entity.Users;

import java.util.Optional;

public interface IUsersService {

    Optional<Users> findByUsernameAndPassword(String username, String password);

    void save(String username, String password);
}
