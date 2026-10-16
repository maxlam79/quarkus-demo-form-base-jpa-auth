CREATE TABLE users
(
    id       BIGSERIAL    NOT NULL PRIMARY KEY,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    UNIQUE (username)
);

CREATE TABLE roles
(
    id        BIGSERIAL    NOT NULL PRIMARY KEY,
    role_name varchar(255) NOT NULL,
    role_desc varchar(255),
    UNIQUE (role_name)
);

CREATE TABLE users_roles_map
(
    id      BIGSERIAL NOT NULL PRIMARY KEY,
    user_id BIGINT    NOT NULL REFERENCES users (id) on update cascade on delete cascade,
    role_id BIGINT    NOT NULL REFERENCES roles (id) on update cascade on delete cascade,
    UNIQUE (user_id, role_id)
);
