CREATE TABLE `course`
(
    `id`               bigint  NOT NULL AUTO_INCREMENT,
    `name`             varchar(100) DEFAULT NOT NULL,
    `maximum_capacity` TINYINT NOT NULL,
    PRIMARY KEY (`id`)
);