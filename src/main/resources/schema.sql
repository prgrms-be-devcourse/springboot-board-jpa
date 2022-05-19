create sequence hibernate_sequence start with 1 increment by 1;

create table post (
    id bigint not null,
    created_at timestamp,
    content clob not null,
    title varchar(255) not null,
    user_id bigint not null,
    primary key (id)
);

create table user (
    id bigint not null,
    created_at timestamp,
    age integer not null,
    hobby varchar(255),
    name varchar(15) not null,
    primary key (id)
);

alter table post add constraint post_fk_user_id foreign key (user_id) references user;
