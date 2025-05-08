CREATE SCHEMA IF NOT EXISTS common;

CREATE TABLE IF NOT EXISTS common.users(
    id bigserial PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    is_active BOOL NOT NULL
);

CREATE TABLE IF NOT EXISTS common.subscriptions(
    id bigserial PRIMARY KEY,
    media_service VARCHAR(255) NOT NULL,
    enabled_at TIMESTAMP NOT NULL,
    disabled_at TIMESTAMP,
    is_active BOOL NOT NULL,
    user_id int8 NOT NULL,
    FOREIGN KEY(user_id) REFERENCES common.users(id)
);
