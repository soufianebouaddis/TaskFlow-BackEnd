CREATE TABLE t_role
(
    id        BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL
);
INSERT INTO t_role (role_name) VALUES ('DEVELOPER');
INSERT INTO t_role (role_name) VALUES ('MANAGER');
INSERT INTO t_role (role_name) VALUES ('USER');
CREATE TABLE t_user
(
    id         UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    email      VARCHAR(255) UNIQUE,
    password   VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    update_at  TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    role_id    BIGINT,
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES t_role (id)
);
CREATE TABLE t_manager
(
    id UUID PRIMARY KEY,
    CONSTRAINT fk_manager_user FOREIGN KEY (id) REFERENCES t_user (id) ON DELETE CASCADE
);
CREATE TABLE t_developer
(
    id             UUID PRIMARY KEY,
    developer_type VARCHAR(50) NOT NULL,
    manager_id     UUID,
    CONSTRAINT fk_developer_user FOREIGN KEY (id) REFERENCES t_user (id) ON DELETE CASCADE,
    CONSTRAINT fk_developer_manager FOREIGN KEY (manager_id) REFERENCES t_manager (id)
);
CREATE TABLE t_task
(
    id           UUID PRIMARY KEY         DEFAULT gen_random_uuid(),
    task_libelle VARCHAR(500),
    task_state   VARCHAR(50),
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    update_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    developer_id UUID,
    CONSTRAINT fk_task_developer FOREIGN KEY (developer_id) REFERENCES t_developer (id)
);