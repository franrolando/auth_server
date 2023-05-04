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

    @Override
    public void save(String username, String password) {
        Users user = Users.builder()
                .username(username)
                .password(password)
                .build();
        usersRepository.save(user);
    }

}
