INSERT INTO `roles` (`name`)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO `users` (`login`, `firstname`, `lastname`, `password`, `email`)
values ('user', 'user', 'user', '$2a$12$bpRpItMZi0aryo/Sd9BFW.0tPNrlZQsbBbrTKgvcIV9k7KdOle8nS', 'user@gmail.com');

INSERT INTO `users` (`login`, `firstname`, `lastname`, `password`, `email`)
values ('admin', 'admin', 'admin', '$2a$12$qk2MbdsdCOvUvaJkSXdKv.M9f87XCLlL15x/79uIpWOe27ABAammO', 'admin@gmail.com');

INSERT INTO `users_roles` (`user_id`, `role_id`)
values (1, 1);

INSERT INTO `users_roles` (`user_id`, `role_id`)
values (2, 1);

INSERT INTO `users_roles` (`user_id`, `role_id`)
values (2, 2);