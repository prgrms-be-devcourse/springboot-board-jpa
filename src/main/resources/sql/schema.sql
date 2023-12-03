DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS member_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS email_auth;


CREATE TABLE member (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    hobby VARCHAR(255) NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255)
);

CREATE TABLE post (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    view INT DEFAULT 0,
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(255),
    member_id INT NOT NULL
);

CREATE TABLE role (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_type VARCHAR(255) NOT NULL
);

CREATE TABLE member_role (
    member_id INT,
    role_id INT,
    PRIMARY KEY (member_id, role_id)
);

CREATE TABLE comment (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     content VARCHAR(255) NOT NULL,
     is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
     member_id INT,
     post_id INT,
     parent_id INT,
     created_at TIMESTAMP(6),
     updated_at TIMESTAMP(6),
     updated_by VARCHAR(255)
);

CREATE TABLE email_auth (
    email_key VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    PRIMARY KEY (email_key)
);
