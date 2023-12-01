CREATE DATABASE IF NOT EXISTS board;

USE board;

create table users
(
    user_id bigint primary key auto_increment,
    name varchar(20) not null ,
    age int not null,
    hobby varchar(20) not null ,
    created_at datetime not null ,
    updated_at datetime not null
);

create table posts
(
    post_id bigint primary key auto_increment,
    user_id bigint not null ,
    title varchar(30) not null,
    contents blob not null ,
    created_at datetime not null ,
    updated_at datetime not null,
    created_by varchar(20) not null,

    constraint post_user_fk FOREIGN key (user_id) references users(user_id)
);

CREATE DATABASE IF NOT EXISTS board_test;

USE board_test;

create table users
(
    user_id bigint primary key auto_increment,
    name varchar(20) not null ,
    age int not null,
    hobby varchar(20) not null ,
    created_at datetime not null ,
    updated_at datetime not null
);

create table posts
(
    post_id bigint primary key auto_increment,
    user_id bigint not null ,
    title varchar(30) not null,
    contents blob not null ,
    created_at datetime not null ,
    updated_at datetime not null,
    created_by varchar(20) not null,

    constraint post_user_fk FOREIGN key (user_id) references users(user_id)
);