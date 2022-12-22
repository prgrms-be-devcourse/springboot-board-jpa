drop table if exists posts cascade;
drop table if exists users cascade;


CREATE TABLE users (
    user_id bigint NOT NULL AUTO_INCREMENT,
    created_at timestamp NULL DEFAULT NULL,
    created_by varchar(255) DEFAULT NULL,
    age int NOT NULL,
    hobby varchar(255) DEFAULT NULL,
    name varchar(20) NOT NULL,
    PRIMARY KEY (user_id)
);


CREATE TABLE posts (
     post_id bigint NOT NULL AUTO_INCREMENT,
     created_at timestamp NULL DEFAULT NULL,
     created_by varchar(255) DEFAULT NULL,
     content longtext,
     title varchar(255) NOT NULL,
     user_user_id bigint DEFAULT NULL,
     PRIMARY KEY (post_id)
);
