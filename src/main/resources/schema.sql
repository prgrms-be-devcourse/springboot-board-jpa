USE board;

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