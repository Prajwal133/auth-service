ALTER TABLE passenger
    ADD CONSTRAINT uc_passenger_email UNIQUE (email);