/* users */
insert into USR_USERS (username, password, enabled) values('user', '$2a$04$.50lAnVItLwL0hIVUv2s/e2Ty1rGIXBKZ12AWozjpekF8fABTumcK',1 );
insert into USR_USERS (username, password, enabled) values('admin', '$2a$04$.50lAnVItLwL0hIVUv2s/e2Ty1rGIXBKZ12AWozjpekF8fABTumcK', 1);

/* roles */
insert into USR_ROLES (user_id, name) values(1, 'ROLE_USER');
insert into USR_ROLES (user_id, name) values(2, 'ROLE_USER');
insert into USR_ROLES (user_id, name) values(2, 'ROLE_ADMIN');
insert into USR_ROLES (user_id, name) values(2, 'ROLE_REPORT');
