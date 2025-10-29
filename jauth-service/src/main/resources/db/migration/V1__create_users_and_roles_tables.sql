CREATE EXTENSION IF NOT EXISTS pg_idkit;

CREATE TABLE roles (
    id CHAR(26) PRIMARY KEY DEFAULT idkit_ulid_generate(),
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id CHAR(26) PRIMARY KEY DEFAULT idkit_ulid_generate(),
    email VARCHAR(100) NOT NULL UNIQUE,
    public_name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    verified_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    deleted_at TIMESTAMPTZ
);

CREATE TABLE users_roles (
    user_id CHAR(26) NOT NULL,
    role_id CHAR(26) NOT NULL,
    PRIMARY KEY(user_id, role_id),
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(role_id) REFERENCES roles(id)
);