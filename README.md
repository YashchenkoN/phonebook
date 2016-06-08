# Phonebook

My small project

## Features:
- authorization/registration
- phone book viewing with filtering by name, surname and phone number
- adding, editing and deleting of phone numbers
- each user have own phone book
- anonymous user cannot see phone book pages
- number validation for Ukraine
- dynamic behaviour


## Technologies
- Maven
- Spring Boot
- HTML/CSS
- JS/jQuery
- MySQL
- Hibernate

## Deployment
Application needs MySQL DB. Before using the application you need to create tables by following the commands below.
Do connecting to MySQL server and enter next statements:
``` mysql
mysql -u root -p
and enter next commands

create database phonebook;
use phonebook;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `phone_book_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `home_phone` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `mobile_phone` varchar(255) NOT NULL,
  `patronymic` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k25vwf99ddsw18lxukbewypdy` (`mobile_phone`),
  KEY `FK_ebh8qhgpxej7tpecohji4cvl2` (`user_id`),
  CONSTRAINT `FK_ebh8qhgpxej7tpecohji4cvl2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
```

The application can use two different data bases: MySQL database and XML file database <br/>
for mysql db pass JVM argument
```
-Dspring.profiles.active=mysql
```
default profile - mysql
for xml db pass JVM argument
```
-Dlardi.conf=/path/to/file.properties
```

To running application, please, enter:
```
mvn spring-boot:run
```
