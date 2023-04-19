create schema if not exists oauth;

create table if not exists oauth.oauth_provider(
    provider text not null primary key,
    client_id text not null,
    client_secret text not null,
    scopes text not null,
    auth_url text not null,
    redirect_url text not null,
    error_page_redirect_url text not null,
    enabled boolean not null
);