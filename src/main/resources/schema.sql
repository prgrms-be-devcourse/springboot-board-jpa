drop table if exists users;
drop table if exists posts;

create table users (
   id bigint not null auto_increment,
   crated_at datetime,
   created_by bigint not null,
   age integer not null,
   hobby varchar(30) not null,
   name varchar(30) not null,
   primary key (id)
);

create table posts (
   id bigint not null auto_increment,
   crated_at datetime,
   created_by bigint not null,
   content longtext,
   title varchar(30) not null,
   user_id bigint,
   primary key (id)
);

INSERT INTO users(crated_at, created_by, age, hobby, name)
VALUES (CURRENT_TIMESTAMP, 1, 28, '음악듣기', '김태희');

INSERT INTO posts(crated_at, created_by, content, title, user_id)
VALUES (CURRENT_TIMESTAMP, 1, '제목1', '내용1', 1);