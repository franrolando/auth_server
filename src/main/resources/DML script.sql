insert
into oauth_entity(provider,
                  client_id,
                  client_secret,
                  scopes,
                  auth_url,
                  redirect_url,
                  error_page_redirect_url)
VALUEs ('Github',
        'client_id',
        'client_secret',
        'read:user',
        'https://github.com/login/oauth/authorize',
        'http://localhost:8081/oauth/v2.0/github/redirect',
        'http://localhost:8081/error');