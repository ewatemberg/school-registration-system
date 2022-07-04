CREATE TABLE `student`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `first_name` varchar(50)  DEFAULT NULL,
    `last_name`  varchar(50)  DEFAULT NULL,
    `email`      varchar(191) DEFAULT NULL,
    PRIMARY KEY (`id`)
);