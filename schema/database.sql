CREATE DATABASE if not exists KotlinSaper;
USE KotlinSaper;

CREATE TABLE App_Users (
    user_id int not null primary key auto_increment,
    username varchar(128) not null,
    user_password varchar(256) not null,
    email varchar(128) not null,
    password_token varchar(256)
);

CREATE TABLE User_Roles (
    user_id int not null,
    roles varchar(128) not null,
    constraint foreign key(user_id) references App_Users(user_id)
);

CREATE TABLE Spots (
    spot_id int not null primary key auto_increment,
    board_id int not null,
    x int not null,
    y int not null,
    isMined boolean not null default 0,
    isChecked boolean not null default 0,
    isFlagged boolean not null default 0,
    mines_around int not null
);

CREATE TABLE `Configuration` (
    configuration_id int not null primary key auto_increment,
    height int not null,
    width int not null,
    mines int not null
);

CREATE TABLE Boards (
    board_id int not null primary key auto_increment,
    user_id int not null,
    configuration_id int not null,
    constraint foreign key(user_id) references App_Users(user_id),
    constraint foreign key(configuration_id) references `Configuration`(configuration_id)
);
