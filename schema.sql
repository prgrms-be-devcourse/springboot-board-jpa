create table posts
(
    id         bigint      	  	not null auto_increment primary key,
    title      varchar(255)   	not null,
    content    tinytext    	  	not null,
    created_at datetime(6)		not null,
    created_by varchar (255),
    updated_at datetime(6)		not null,
    updated_by varchar(255)
);

create table users
(
    id         bigint       	not null auto_increment primary key,
    age        integer      	not null,
    hobby      varchar(255),
    name       varchar(255) 	not null,
    created_at datetime(6)		not null,
    created_by varchar (255),
    updated_at datetime(6)		not null,
    updated_by varchar(255)
);
