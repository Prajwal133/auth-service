CREATE TABLE booking
(
    id             BIGINT NOT NULL,
    created_at     datetime(6)  NOT NULL,
    updated_at     datetime(6)  NOT NULL,
    passenger_id   BIGINT NULL,
    driver_id      BIGINT NULL,
    booking_status VARCHAR(255) NULL,
    start_time     datetime(6)  NULL,
    end_time       datetime(6)  NULL,
    total_distance BIGINT NULL,
    CONSTRAINT pk_booking PRIMARY KEY (id)
);

CREATE TABLE driver
(
    id             BIGINT       NOT NULL,
    created_at     datetime(6)  NOT NULL,
    updated_at     datetime(6)  NOT NULL,
    name           VARCHAR(255) NULL,
    license_number VARCHAR(255) NOT NULL,
    phone_number   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_driver PRIMARY KEY (id)
);

CREATE TABLE passenger
(
    id           BIGINT       NOT NULL,
    created_at   datetime(6)  NOT NULL,
    updated_at   datetime(6)  NOT NULL,
    name         VARCHAR(255) NULL,
    email        VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    CONSTRAINT pk_passenger PRIMARY KEY (id)
);

CREATE TABLE passenger_bookings
(
    passenger_id BIGINT NOT NULL,
    bookings_id  BIGINT NOT NULL
);

CREATE TABLE revchanges
(
    rev        BIGINT NOT NULL,
    entityname VARCHAR(255) NULL
);

CREATE TABLE revinfo
(
    rev      BIGINT NOT NULL,
    revtstmp BIGINT NULL,
    CONSTRAINT pk_revinfo PRIMARY KEY (rev)
);

ALTER TABLE driver
    ADD CONSTRAINT uc_driver_licensenumber UNIQUE (license_number);

ALTER TABLE driver
    ADD CONSTRAINT uc_driver_phonenumber UNIQUE (phone_number);

ALTER TABLE passenger_bookings
    ADD CONSTRAINT uc_passenger_bookings_bookings UNIQUE (bookings_id);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES driver (id);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_PASSENGER FOREIGN KEY (passenger_id) REFERENCES passenger (id);

ALTER TABLE passenger_bookings
    ADD CONSTRAINT fk_pasboo_on_booking FOREIGN KEY (bookings_id) REFERENCES booking (id);

ALTER TABLE passenger_bookings
    ADD CONSTRAINT fk_pasboo_on_passenger FOREIGN KEY (passenger_id) REFERENCES passenger (id);

ALTER TABLE revchanges
    ADD CONSTRAINT fk_revchanges_on_default_tracking_modified_entities_changelog FOREIGN KEY (rev) REFERENCES revinfo (rev);