-- account
CREATE SEQUENCE account_seq;
CREATE TABLE account
(
    id              BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('account_seq'::regclass),
    external_id     VARCHAR(255) UNIQUE,
    name            VARCHAR(255)                NOT NULL,
    icon_url        VARCHAR(255)                NOT NULL,
    last_login_date TIMESTAMP WITHOUT TIME ZONE,
    service_account BOOLEAN                     NOT NULL,
    idp_linked      BOOLEAN                     NOT NULL,

    email           VARCHAR(255) UNIQUE,
    phone           VARCHAR(255),
    mobile          VARCHAR(255),

    first_name      VARCHAR(255)                NOT NULL,
    last_name       VARCHAR(255)                NOT NULL,

    last_sync       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated         TIMESTAMP WITHOUT TIME ZONE
);

-- event
CREATE SEQUENCE event_seq;
CREATE TABLE event
(
    id         BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('event_seq'::regclass),
    owner_id   BIGINT                      NOT NULL REFERENCES account (id),

    start      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    finish     TIMESTAMP WITHOUT TIME ZONE NOT NULL,

    title      VARCHAR(255)                NOT NULL,
    short_text VARCHAR(255)                NOT NULL,
    long_text  TEXT                        NOT NULL,
    image_url  VARCHAR(255)                NOT NULL,
    icon_url   VARCHAR(255)                NOT NULL,

    published  BOOLEAN                     NOT NULL,

    tags       TEXT                        NOT NULL,

    created    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated    TIMESTAMP WITHOUT TIME ZONE
);