-- =====================================================================
-- V1: Initial RBAC Schema (Users, Roles, Privileges)
-- =====================================================================

CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(50) UNIQUE                    NOT NULL,
    email         VARCHAR(100) UNIQUE                   NOT NULL,
    password_hash VARCHAR(255)                          NOT NULL,
    enabled       BOOLEAN                  DEFAULT TRUE NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) UNIQUE NOT NULL, -- e.g., SYSTEM_ADMIN, USER_MANAGER, STANDARD_USER
    description VARCHAR(255)
);

CREATE TABLE privileges
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) UNIQUE NOT NULL, -- e.g., USER_CREATE, USER_READ, USER_DELETE, SYSTEM_CONFIG
    description VARCHAR(255)
);

-- Join table for Users and Roles (Many-to-Many)
CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- Join table for Roles and Privileges (Many-to-Many)
CREATE TABLE role_privileges
(
    role_id      BIGINT NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
    privilege_id BIGINT NOT NULL REFERENCES privileges (id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, privilege_id)
);

-- Seed Initial System Administrator Role and Core Privileges
INSERT INTO roles (name, description)
VALUES ('SYSTEM_ADMIN', 'Full system access and user onboarding privileges'),
       ('STANDARD_USER', 'Standard operational user privileges');

INSERT INTO privileges (name, description)
VALUES ('USER_ONBOARD', 'Permission to register and onboard new users'),
       ('USER_READ', 'Permission to view user profile details'),
       ('SYSTEM_CONFIG', 'Permission to alter system-level configurations');

-- Assign all privileges to SYSTEM_ADMIN
INSERT INTO role_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM roles r,
     privileges p
WHERE r.name = 'SYSTEM_ADMIN';