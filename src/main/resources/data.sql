INSERT INTO users (id, created_at, deleted_at, updated_at, age, hobby, name)
SELECT 1, '2023-11-23 10:45:12', null, '2023-11-23 10:45:13', 59, 'platypus', 'ogu'
WHERE NOT EXISTS (SELECT * FROM users WHERE id = 1);