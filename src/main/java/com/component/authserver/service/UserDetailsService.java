package com.component.authserver.service;

import com.component.authserver.entity.UserDetails;
import com.component.authserver.repository.UserDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsService implements IUserDetailsService {

    private UserDetailsRepository userDetailsRepository;

    public UserDetailsService(UserDetailsRepository userDetailsRepository){
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public Optional<UserDetails> findByEmail(String email) {
        return userDetailsRepository.findByEmail(email);
    }

}
