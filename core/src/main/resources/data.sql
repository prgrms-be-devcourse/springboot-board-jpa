insert into permission(id, name) values(99, 'ROLE_ADMIN');
insert into permission(id, name) values(98, 'ROLE_USER');

insert into groups(id, name) values(999, 'ADMIN_GROUP');
insert into groups(id, name) values(998, 'USER_GROUP');

insert into group_permission(id, groups_id, permission_id) values(9999, 999, 99);

-- password : admin123
insert into member(id, login_id, passwd, groups_id) values(99999, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999);

--- users for inactive
insert into member(id, login_id, passwd, groups_id, login_at) values(9999999, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2023-12-04 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(999991, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(999992, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(999993, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(999994, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(999995, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(999996, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(999997, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(999998, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(999999, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(9999910, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(9999911, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(9999912, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(9999913, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(9999914, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(9999915, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(9999916, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(9999917, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(9999918, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
insert into member(id, login_id, passwd, groups_id, login_at) values(9999919, 'user', '$2a$10$/enTGRjB6noB9NCd8g5kGuLchiTsZsqcUyXkUn4yglUPZ4WZ9MvrK', 999, '2019-07-01 23:59:59');
