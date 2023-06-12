package com.component.authserver.aspect;

import com.component.authserver.config.CustomLoginConfiguration;
import com.component.authserver.exception.CustomLoginNotConfiguredException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CustomLoginAspect {

    private CustomLoginConfiguration customLoginConfiguration;

    public CustomLoginAspect(CustomLoginConfiguration customLoginConfiguration) {
        this.customLoginConfiguration = customLoginConfiguration;
    }

    @Before("execution(* com.component.authserver.controller.LoginController.*(..))")
    public void logBeforeMethodCall() {
        if (!customLoginConfiguration.isCustomLogin()) {
            log.info("Cannot access to controller due custom login is not enabled");
            throw new CustomLoginNotConfiguredException();
        }
    }
}
