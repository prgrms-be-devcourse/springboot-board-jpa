CREATE TABLE `board_mission`.`user`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(45) NOT NULL,
    `age`        VARCHAR(45) NOT NULL,
    `hobby`      VARCHAR(45) NOT NULL,
    `created_at` DATETIME    NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `board_mission`.`post`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `title`      VARCHAR(45) NOT NULL,
    `content`    TEXT        NOT NULL,
    `created_at` VARCHAR(45) NOT NULL,
    `created_by` INT         NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`created_by`) REFERENCES user (`id`)
);
