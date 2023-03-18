CREATE SCHEMA IF NOT EXISTS notification_service;

CREATE TABLE IF NOT EXISTS notification_service.account (
    id varchar(32) PRIMARY KEY,
    account_name varchar(500),
    created_on timestamptz DEFAULT NOW(),
    updated_on timestamptz
    );

CREATE TABLE IF NOT EXISTS notification_service.notification(
    id varchar(32) PRIMARY KEY,
    payload jsonb,
    delivery_date timestamptz,
    is_ready boolean,
    was_sent boolean,
    account_id varchar(32),
    created_on timestamptz DEFAULT NOW(),

    CONSTRAINT FK_account_id FOREIGN KEY(account_id)
    REFERENCES notification_service.account(id)
    );

CREATE INDEX idx_delivery_date ON notification_service.notification(delivery_date);