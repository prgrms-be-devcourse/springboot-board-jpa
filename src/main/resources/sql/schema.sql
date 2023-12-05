CREATE TABLE IF NOT EXISTS member (
    member_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(25) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(15) NOT NULL,
    age INT NOT NULL,
    hobby VARCHAR(255) NOT NULL,
    is_deleted BIT(1) NOT NULL DEFAULT FALSE,
    last_updated_password TIMESTAMP(6),
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(25)
);

CREATE TABLE IF NOT EXISTS post (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    content VARCHAR(255) NOT NULL,
    view INT DEFAULT 0,
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(25),
    member_id INT UNSIGNED NOT NULL
);

CREATE TABLE IF NOT EXISTS role (
    role_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    role_type VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS member_role (
    member_role_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    member_id INT UNSIGNED,
    role_id INT UNSIGNED
);

CREATE TABLE IF NOT EXISTS comment (
     id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
     content VARCHAR(255) NOT NULL,
     member_id INT,
     post_id INT,
     parent_id INT,
     created_at TIMESTAMP(6),
     updated_at TIMESTAMP(6),
     updated_by VARCHAR(25)
);

CREATE TABLE IF NOT EXISTS email_auth (
    auth_key VARCHAR(255) NOT NULL,
    email VARCHAR(25) NOT NULL,
    purpose VARCHAR(15) NOT NULL,
    PRIMARY KEY (auth_key)
);
