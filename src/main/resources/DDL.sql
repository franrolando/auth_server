drop table if exists oauth.user_details;
drop table if exists oauth.oauth_provider;
drop schema if exists oauth;

create schema if not exists oauth;

create table if not exists oauth.oauth_provider(
    provider_id smallint not null,
    provider text not null,
    enabled boolean not null,
    constraint pk_oauth_provider_id primary key (provider_id)
);

create table if not exists oauth.user_details(
    fk_provider_id smallint not null,
    user_id text not null,
    uuid UUID not null unique,
    user_attributes json not null,
    email text not null unique,
    last_login timestamp not null,
    constraint pk_user_details primary key (user_id),
    constraint fk_provider_id foreign key (fk_provider_id) references oauth.oauth_provider(provider_id)
);

