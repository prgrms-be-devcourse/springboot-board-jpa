DROP TABLE IF EXISTS users;
DROP TABLE if EXISTS posts;

CREATE TABLE users(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(20),
    age         INT,
    hobby       VARCHAR(15),
    created_at  TIMESTAMP NOT NULL ,
    created_by  VARCHAR(20) DEFAULT NULL
);

CREATE TABLE posts(
      id          BIGINT AUTO_INCREMENT PRIMARY KEY ,
      title       VARCHAR(20),
      content     VARCHAR(255),
      created_at  TIMESTAMP NOT NULL,
      created_by  BIGINT
);

INSERT INTO users(name, age, hobby, created_at) VALUES("BS_KIM", 25, "MUSIC", CURRENT_TIMESTAMP);

INSERT INTO posts(title, content, created_at, created_by) VALUES("test_title", "test_content", CURRENT_TIMESTAMP, 1);