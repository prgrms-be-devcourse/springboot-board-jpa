CREATE TABLE member IF NOT EXISTS (
    member_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(25) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(15) NOT NULL,
    age INT NOT NULL,
    hobby VARCHAR(255) NOT NULL,
    is_deleted BIT(1) NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(25)
);

CREATE TABLE post IF NOT EXISTS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    content VARCHAR(255) NOT NULL,
    view INT DEFAULT 0,
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6),
    updated_by VARCHAR(25),
    member_id INT UNSIGNED NOT NULL
);

CREATE TABLE role IF NOT EXISTS (
    role_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    role_type VARCHAR(10) NOT NULL
);

CREATE TABLE member_role IF NOT EXISTS (
    member_id INT UNSIGNED,
    role_id INT UNSIGNED,
    PRIMARY KEY (member_id, role_id)
);

CREATE TABLE comment IF NOT EXISTS (
     id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
     content VARCHAR(255) NOT NULL,
     member_id INT,
     post_id INT,
     parent_id INT,
     created_at TIMESTAMP(6),
     updated_at TIMESTAMP(6),
     updated_by VARCHAR(25)
);

CREATE TABLE email_auth IF NOT EXISTS (
    auth_key VARCHAR(255) NOT NULL,
    email VARCHAR(25) NOT NULL,
    purpose VARCHAR(10) NOT NULL,
    PRIMARY KEY (auth_key)
);
