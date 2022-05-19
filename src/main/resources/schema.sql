create table post
(
    id         bigint auto_increment
        primary key,
    created_at timestamp    null,
    created_by varchar(255) null,
    content    longtext     null,
    title      varchar(255) null,
    user_id    bigint       null,
    constraint FK72mt33dhhs48hf9gcqrq4fxte
        foreign key (user_id) references user (id)
);

create table user
(
    id         bigint auto_increment
        primary key,
    created_at timestamp    null,
    created_by varchar(255) null,
    age        int          null,
    hobby      varchar(35)  null,
    name       varchar(20)  null
);

insert into user(username, age, hobby) values("guest", 7, "guest");