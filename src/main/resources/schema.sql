CREATE TABLE IF NOT EXISTS member
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(30) NOT NULL,
    login_id        varchar(20) NOT NULL,
    password        varchar(80) NOT NULL,
    role            varchar(30) NOT NULL,
    last_login_date TIMESTAMP,
    is_deleted      BOOLEAN DEFAULT FALSE,
    created_at      TIMESTAMP,
    created_by      VARCHAR(255),
    updated_at      TIMESTAMP,
    updated_by      VARCHAR(255)
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
