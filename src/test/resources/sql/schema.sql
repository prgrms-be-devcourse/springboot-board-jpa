CREATE TABLE member (
    member_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    age TINYINT NOT NULL,
    hobby VARCHAR(255) NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    last_updated_password TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    updated_by VARCHAR(255)
);

CREATE TABLE post (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    view INT DEFAULT 0,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    member_id INT NOT NULL
);

CREATE TABLE role (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_type VARCHAR(255) NOT NULL
);

CREATE TABLE member_role (
    member_role_id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT,
    role_id INT
);

CREATE TABLE comment (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(255) NOT NULL,
    member_id INT,
    post_id INT,
    parent_id INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    updated_by VARCHAR(255)
);

CREATE TABLE email_auth (
    auth_key VARCHAR(255) NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    purpose VARCHAR(255) NOT NULL
);
