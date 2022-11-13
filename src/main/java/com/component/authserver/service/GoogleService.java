package com.component.authserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class GoogleService implements IOAuthService {
    public void redirect(String code, HttpServletResponse resp) {
    }
}
