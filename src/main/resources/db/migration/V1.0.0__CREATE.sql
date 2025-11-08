CREATE TABLE t_user
(
    id       BIGSERIAL    NOT NULL PRIMARY KEY,
    username varchar(255) NOT NULL,
    password text NOT NULL,
    UNIQUE (username)
);

CREATE TABLE t_role
(
    id        BIGSERIAL    NOT NULL PRIMARY KEY,
    role_name varchar(255) NOT NULL,
    role_desc varchar(255),
    UNIQUE (role_name)
);

CREATE TABLE t_user_role_map
(
    id      BIGSERIAL NOT NULL PRIMARY KEY,
    user_id BIGINT    NOT NULL REFERENCES t_user (id) on update cascade on delete cascade,
    role_id BIGINT    NOT NULL REFERENCES t_role (id) on update cascade on delete cascade,
    UNIQUE (user_id, role_id)
);
