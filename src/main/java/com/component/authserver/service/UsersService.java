package com.component.authserver.service;

import com.component.authserver.entity.Users;
import com.component.authserver.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService implements IUsersService {

    private UsersRepository usersRepository;


    @Override
    public Optional<Users> findByUsernameAndPassword(String username, String password) {
        return Optional.empty();
    }
}
