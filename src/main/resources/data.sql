
INSERT INTO member (name, age, hobby, created_at, created_by, updated_at, updated_by)
VALUES ('John Doe', 30, 'Reading', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
       ('Jane Smith', 25, 'Hiking', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
       ('Alice Johnson', 35, 'Photography', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');

INSERT INTO post (title, content, member_id, created_at, created_by, updated_at, updated_by)
VALUES ('First Post', 'This is the content of the first post.', 1, CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
       ('Second Post', 'This is the content of the second post.', 1, CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
       ('Third Post', 'This is the content of the third post.', 2, CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');
