package com.component.authserver.response;

import lombok.Data;

@Data
public class GithubResponse {

    private String access_token;
    private String scope;
    private String token_type;

}
