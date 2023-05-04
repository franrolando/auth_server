package com.component.authserver.service;

public interface ILoginService {

    void signIn(String username, String password);
    void signUp(String username, String password);
}
