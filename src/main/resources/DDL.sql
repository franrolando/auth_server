create schema if not exists oauth;

create table if not exists oauth.oauth_provider(
    provider text not null primary key,
    enabled boolean not null
);