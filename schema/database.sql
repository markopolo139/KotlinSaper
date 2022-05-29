DROP DATABASE KotlinSaper;
CREATE DATABASE KotlinSaper;
USE KotlinSaper;

CREATE TABLE app_users (
    user_id int not null primary key auto_increment,
    username varchar(128) not null,
    user_password varchar(256) not null,
    email varchar(128) not null unique,
    password_token varchar(256) unique
);

CREATE TABLE user_roles (
    user_id int not null,
    roles varchar(128) not null,
    constraint foreign key(user_id) references app_users(user_id)
);

CREATE TABLE boards (
    board_id int not null primary key auto_increment,
    user_id int not null,
    constraint foreign key(user_id) references app_users(user_id)
);

CREATE TABLE spots (
    spot_id int not null primary key auto_increment,
    board_id int not null,
    x int not null,
    y int not null,
    mined boolean not null default 0,
    checked boolean not null default 0,
    flagged boolean not null default 0,
    mines_around int not null,
    constraint foreign key(board_id) references boards(board_id)
);

CREATE TABLE board_configuration (
    configuration_id int not null primary key auto_increment,
    board_id int not null,
    name varchar(250) default 'custom_config',
    constraint foreign key(board_id) references boards(board_id)
);

CREATE TABLE configuration_entry (
    configuration_id int not null,
    entry_name varchar(128) not null,
    `value` varchar(128) not null,
    constraint foreign key(configuration_id) references board_configuration(configuration_id)
);

INSERT INTO app_users VALUES(1, "guest", "$2a$12$0OTUMsGYKjMRWKYq6ecHpObjEj9/9utZm6kPRGzx1yasJpSo3PO3y", "email", null);
INSERT INTO user_roles VALUES(1, "ANONYMOUS");

INSERT INTO app_users VALUES(2, "admin", "$2a$12$6WGDczQT9Ii93OuKd4M8SOq8ckAKC5il2t/SvhTLuY1je9jyxB4Zq", "admin@onet.pl", null);
INSERT INTO user_roles VALUES(2, "ADMIN");
