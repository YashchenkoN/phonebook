# phonebook

Application need MySQL DB.
Connect to localhost server:
mysql -u root -p
and enter next commands

create database phonebook;
use phonebook;
create table users (id bigint primary key auto_increment, name varchar(100) not null, login varchar(100) not null, password varchar(100) not null);
