package com.component.authserver.service;

import javax.servlet.http.HttpServletResponse;

public interface IOAuthService {

    void redirect(String code, HttpServletResponse resp);

}
