insert into permission(id, name) values(99, 'ROLE_ADMIN');

insert into groups(id, name) values(999, 'ADMIN');

insert into group_permission(id, groups_id, permission_id) values(9999, 999, 99);

insert into member(id, login_id, passwd, groups_id) values(99999, 'user', 'user1234', 999);
