CREATE EXTENSION IF NOT EXISTS "pg_ulid";

CREATE TABLE roles (
    id ULID PRIMARY KEY DEFAULT ulid(),
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
    id ULID PRIMARY KEY DEFAULT ulid(),
    email VARCHAR(100) NOT NULL UNIQUE,
    public_name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    verified_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ,
    deleted_at TIMESTAMPTZ
);

CREATE TABLE users_roles (
    user_id ULID NOT NULL,
    role_id ULID NOT NULL,
    PRIMARY KEY(user_id, role_id),
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(role_id) REFERENCES roles(id)
);