package com.component.authserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@org.springframework.context.annotation.Configuration
@ConfigurationProperties(prefix = "login")
@Getter
@Setter
public class Configuration {

    private String applicationDomain;

}
