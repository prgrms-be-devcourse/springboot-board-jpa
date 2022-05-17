INSERT INTO user(created_at, created_by, age, hobby, name, id)
VALUES (CURRENT_TIMESTAMP, '유민환', 26, '낚시', '유민환', 1);
INSERT INTO post(created_at, created_by, title, content, user_id, id)
VALUES (CURRENT_TIMESTAMP, '유민환', '제목입니다.', '내용입니다.', 1, 10)
