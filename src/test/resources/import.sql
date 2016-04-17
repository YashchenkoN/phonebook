create table users (id BIGINT IDENTITY primary key, name varchar(100) not null, login varchar(100) not null unique, password varchar(100) not null);
insert into users (id, name, login, password) values (0, 'name', 'login', '1111');
insert into users (id, name, login, password) values (1, 'name', 'read_by_login', '1111');
insert into users (id, name, login, password) values (2, 'name', 'delete', '1111');