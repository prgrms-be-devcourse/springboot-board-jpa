INSERT INTO users(user_id, created_at, created_by, age, hobby, `name`) VALUES(1, NOW(), 'admin', 23, 'judo', 'kim seung eun');
INSERT INTO users(user_id, created_at, created_by, age, hobby, `name`) VALUES(2, NOW(), 'admin', 24, 'basketball', 'kim seung su');
INSERT INTO post(created_at, created_by, content, title, author) VALUES(now(), 'admin', '열심히 하자 화이팅', '데브코스 jpa 게시글', 1);