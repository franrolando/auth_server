insert
into
    oauth_entity(application_name,
                 client_id,
                 client_secret,
                 scopes,
                 auth_url,
                 redirect_url) VALUEs('Github',
                                      'GITHUB ID',
                                      'GITHUB SECRET',
                                      'read:user',
                                      'https://github.com/login/oauth/authorize',
                                      'http://localhost:8081/oauth/v2.0/github/redirect'
                                          'http://localhost:8081/error'
                                     );
