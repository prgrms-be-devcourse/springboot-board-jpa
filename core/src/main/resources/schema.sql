-- Drop tables if they exist
drop table if exists post;
drop table if exists member;
drop table if exists groups;
drop table if exists permission;
drop table if exists group_permission;

-- Create the post table
create table post (
    id bigint auto_increment primary key,
    title varchar(60),
    content text,
    is_deleted boolean,
    member_id bigint,
    created_at timestamp,
    created_by varchar(50),
    updated_at timestamp,
    updated_by varchar(50)
);

-- Create the member table
create table member (
    id bigint auto_increment primary key,
    login_id varchar(50),
    passwd varchar(255),
    name varchar(50),
    age int,
    hobby varchar(50),
    login_at timestamp,
    status varchar(50),
    groups_id bigint,
    created_at timestamp,
    created_by varchar(50),
    updated_at timestamp,
    updated_by varchar(50)
);

-- Create the groups table
create table groups (
    id bigint auto_increment primary key,
    name varchar(50),
    created_at timestamp,
    created_by varchar(50),
    updated_at timestamp,
    updated_by varchar(50)
);

-- Create the permission table
create table permission (
    id bigint auto_increment primary key,
    name varchar(50),
    created_at timestamp,
    created_by varchar(50),
    updated_at timestamp,
    updated_by varchar(50)
);

-- Create the group_permission table
create table group_permission (
    id bigint auto_increment primary key,
    groups_id bigint,
    permission_id bigint,
    created_at timestamp,
    created_by varchar(50),
    updated_at timestamp,
    updated_by varchar(50)
);
