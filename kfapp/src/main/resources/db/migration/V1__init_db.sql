CREATE TABLE `users`
(
    `id`        bigint      NOT NULL AUTO_INCREMENT,
    `firstname` varchar(30) NOT NULL,
    `lastname`  varchar(30) NOT NULL,
    `login`     varchar(30) NOT NULL,
    `password`  varchar(80) DEFAULT NULL,
    `email`     varchar(50) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

create table `roles`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `name` varchar(100) NOT NULL,
    PRIMARY KEY (`id`)
);

create table `users_roles`
(
    `user_id` bigint NOT NULL,
    `role_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    FOREIGN KEY (`user_id`) references `users` (`id`),
    FOREIGN KEY (`role_id`) references `roles` (`id`)
)