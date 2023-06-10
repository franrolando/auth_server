package com.component.authserver.service;

import java.io.IOException;

public interface ILoginService {

    void signIn(String username, String password) throws IOException;
    void signUp(String username, String password) throws IOException;
}
