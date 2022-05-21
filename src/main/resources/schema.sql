CREATE TABLE post (
    id BIGINT,
    title VARCHAR(255),
    content VARCHAR(1023),
    created_at TIMESTAMP,
    user BIGINT
);

CREATE TABLE user (
    id BIGINT,
    name VARCHAR(64),
    age INT,
    hobby VARCHAR(64),
    create_at TIMESTAMP
);

