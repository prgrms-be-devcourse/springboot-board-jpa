DROP TABLE IF EXISTS post CASCADE;
DROP TABLE IF EXISTS user CASCADE;

create table user
(
    id  bigint auto_increment   primary key,
    created_at timestamp    default CURRENT_TIMESTAMP,
    created_by varchar(255) null,
    age        int          null,
    hobby      varchar(35)  null,
    username    varchar(20) not null unique
);

create table post
(
    id  bigint auto_increment   primary key,
    created_at timestamp    default CURRENT_TIMESTAMP,
    created_by varchar(255) null,
    content    longtext     null,
    title      varchar(255) null,
    user_id    bigint       null,
    constraint FK_user_id
    foreign key (user_id) references user (id)
);