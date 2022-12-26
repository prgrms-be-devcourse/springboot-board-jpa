drop table if exists users;
drop table if exists posts;

create table users(
    id          bigint auto_increment,
    name        varchar(20),
    age         int,
    hobby       varchar(15),
    created_at  timestamp not null ,
    created_by  varchar(20) default null
)

create table users(
      id          bigint auto_increment,
      title        varchar(20),
      age         int,
      hobby       varchar(15),
      created_at  timestamp not null ,
      created_by  varchar(20)
)