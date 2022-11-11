insert
into
    oauth_entity(application_name,
                 client_id,
                 client_secret,
                 scopes,
                 auth_url,
                 redirect_url) VALUEs('Github',
                                      '693f3da9f92cd15aa2b8',
                                      '286c4918012eb81a1fed26ce0502a1d49c94e597',
                                      'read:user',
                                      'https://github.com/login/oauth/authorize',
                                      'http://localhost:8081/oauth/v2.0/github/redirect'
                                          'http://localhost:8081/error'
                                     );
