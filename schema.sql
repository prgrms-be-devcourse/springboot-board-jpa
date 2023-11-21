create table posts
(
    id         bigint       not null auto_increment primary key,
    created_at datetime(6) not null,
    updated_at datetime(6) not null,
    created_by varchar(255) not null,
    title      varchar(255) not null,
    updated_by varchar(255) not null,
    content    tinytext     not null
);

create table users
(
    id         bigint       not null auto_increment primary key,
    created_at datetime(6) not null,
    updated_at datetime(6) not null,
    age        integer      not null,
    created_by varchar(255) not null,
    hobby      varchar(255),
    name       varchar(255) not null,
    updated_by varchar(255) not null
);
