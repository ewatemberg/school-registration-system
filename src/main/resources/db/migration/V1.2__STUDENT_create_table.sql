CREATE TABLE student
(
    id         bigint auto_increment primary key,
    first_name varchar(50)  default null,
    last_name  varchar(50)  default null,
    email      varchar(191) default null
);