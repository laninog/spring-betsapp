insert into MGR_MATCHES(id, local, rel_local, visitor, rel_visitor, rel_draw, open, close) values(1, 'España', 2, 'Mexico', 5, 3, {ts '2018-03-04 11:47:00.00'}, {ts '2018-03-05 20:47:52.00'});
insert into MGR_MATCHES(id, local, rel_local, visitor, rel_visitor, rel_draw, open, close) values(2, 'Corea', 2, 'Polonia', 4, 5, {ts '2018-03-04 11:47:00.00'}, {ts '2018-03-05 21:47:52.00'});
insert into MGR_MATCHES(id, local, rel_local, visitor, rel_visitor, rel_draw, open, close) values(3, 'Brazil', 6, 'Argentina', 4, 2, {ts '2018-03-04 11:47:00.00'}, {ts '2018-03-05 19:47:52.00'});
insert into MGR_MATCHES(id, local, rel_local, visitor, rel_visitor, rel_draw, open, close) values(4, 'Camerun', 2, 'USA', 6, 4, {ts '2018-03-04 11:47:00.00'}, {ts '2018-03-05 17:47:52.00'});

/* users */
insert into USR_USERS (username, password, enabled) values('user', '$2a$04$.50lAnVItLwL0hIVUv2s/e2Ty1rGIXBKZ12AWozjpekF8fABTumcK',1 );
insert into USR_USERS (username, password, enabled) values('admin', '$2a$04$.50lAnVItLwL0hIVUv2s/e2Ty1rGIXBKZ12AWozjpekF8fABTumcK', 1);

/* roles */
insert into USR_ROLES (user_id, name) values(1, 'ROLE_USER');
insert into USR_ROLES (user_id, name) values(2, 'ROLE_USER');
insert into USR_ROLES (user_id, name) values(2, 'ROLE_ADMIN');
insert into USR_ROLES (user_id, name) values(2, 'ROLE_REPORT');
