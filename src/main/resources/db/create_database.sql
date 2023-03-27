CREATE SCHEMA IF NOT EXISTS notification_service;

CREATE TABLE IF NOT EXISTS notification_service.account (
    id varchar(32) PRIMARY KEY,
    account_name varchar(500),
    opt_in boolean DEFAULT false,
    created_on timestamptz DEFAULT NOW(),
    updated_on timestamptz
    );

CREATE TABLE IF NOT EXISTS notification_service.notification(
    id varchar(32) PRIMARY KEY,
    notification_type varchar(100),
    notification_message varchar(1000),
    delivery_date timestamptz,
    is_ready boolean DEFAULT false,
    was_sent boolean DEFAULT false,
    created_on timestamptz DEFAULT NOW(),
    account_id varchar(32),

    CONSTRAINT FK_account_id FOREIGN KEY(account_id)
    REFERENCES notification_service.account(id)
    );

CREATE INDEX idx_delivery_date ON notification_service.notification(delivery_date);

INSERT INTO notification_service.account (id, account_name, created_on, opt_in)
VALUES ('2NJ14ffd3iToJlzpcgqEf6D31ff','Bernardo', now(), true);

INSERT INTO notification_service.account (id, account_name, created_on, opt_in)
VALUES ('2NJ14d4P9F33AgkN4FlSV4q9sgD','Daniel', now(), true);

INSERT INTO notification_service.account (id, account_name, created_on, opt_in)
VALUES ('2NJ14bt7YN8YnpUrTbXqgwsnwrk','Nilson', now(), true);

INSERT INTO notification_service.account (id, account_name, created_on, opt_in)
VALUES ('2NJ14dXPE6kb4jRhjDQB1IwJD47','Diego', now(), true);

INSERT INTO notification_service.account (id, account_name, created_on, opt_in)
VALUES ('2NJ14aovE6y0z5a23MnefFEkJrA','Marcus', now(), true);


INSERT INTO notification_service.notification (id, notification_type, notification_message, created_on, delivery_date, account_id)
VALUES ('2NJ14deSs8iAIpEwGtA6iHewUT0', 'PUSH', 'Hello World!', now(), now(), '2NJ14ffd3iToJlzpcgqEf6D31ff');

INSERT INTO notification_service.notification (id, notification_type, notification_message, created_on, delivery_date, account_id)
VALUES ('2NJ14ZpfFqmFWCFU4GqhuXLQ3qD', 'SMS', 'Hello World2!', now(), now(), '2NJ14ffd3iToJlzpcgqEf6D31ff');

INSERT INTO notification_service.notification (id, notification_type, notification_message, created_on, delivery_date, account_id)
VALUES ('2NJ14gEiCyrla4Dl3HkVDqbQQ0d', 'SMS', 'Hello World2!', now(), now() - INTERVAL '1 DAY', '2NJ14ffd3iToJlzpcgqEf6D31ff');

INSERT INTO notification_service.notification (id, notification_type, notification_message, created_on, delivery_date, account_id)
VALUES ('2NJ12zMqxla2r0BFkt6xWsh8eE1', 'SMS', 'Hello World3!', now(), now() + INTERVAL '1 DAY', '2NJ14ffd3iToJlzpcgqEf6D31ff');
