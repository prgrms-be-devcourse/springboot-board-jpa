INSERT INTO role (role_type)
values ('ADMIN'),
       ('USER');


INSERT INTO member (email, password, name, age, hobby, created_at, updated_at)
VALUES
       ('admin123@naver.com', '$2a$10$rBzmYPWWdAkUwXjBu/nEXuQ2zB49NhnfnI4XG.y2t7z.VpVw2kS0i', 'admin', 25, '배드민턴 치기', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('user123@naver.com', '$2a$10$bSA3A3pSDKW0kXHAEZBFdeyLU21aHW5DwlhmjboEEySsMR6/iBptS', 'user', 22, '축구하기', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO member_role (member_id, role_id)
values (1, 1),
       (1, 2),
       (2, 2)
