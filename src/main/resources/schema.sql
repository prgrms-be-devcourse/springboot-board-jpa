CREATE TABLE post (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    content VARCHAR(1024),
    created_at TIMESTAMP,
    user BIGINT
);

CREATE TABLE user (
    id BIGINT PRIMARY KEY,
    name VARCHAR(64),
    age INT,
    hobby VARCHAR(64),
    create_at TIMESTAMP
);

