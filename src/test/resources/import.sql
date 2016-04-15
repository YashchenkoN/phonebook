create table users (id BIGINT IDENTITY primary key, name varchar(100) not null, login varchar(100) not null unique, password varchar(100) not null);
insert into users (name, login, password) values ('name', 'login', '1111');
insert into users (name, login, password) values ('name', 'read_by_login', '1111');
insert into users (name, login, password) values ('name', 'delete', '1111');