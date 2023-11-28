CREATE TABLE IF NOT EXISTS member
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(30) NOT NULL,
    age        INT         NOT NULL,
    hobby      VARCHAR(255),
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS post
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(60) NOT NULL,
    content    TEXT        NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    member_id  BIGINT,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255)
);
