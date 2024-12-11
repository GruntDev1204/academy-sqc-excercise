CREATE TABLE market_places
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(40)           NULL,
    address       VARCHAR(255)          NULL,
    area          DOUBLE                NULL,
    avatar        VARCHAR(500)          NULL,
    rent_pice     DECIMAL(25, 2)        NULL,
    rent_date     date                  NULL,
    `description` VARCHAR(500)          NULL,
    type_id       BIGINT                NULL,
    CONSTRAINT pk_market_places PRIMARY KEY (id)
);

ALTER TABLE market_places
    ADD CONSTRAINT FK_MARKET_PLACES_ON_TYPE FOREIGN KEY (type_id) REFERENCES type_market_places (id);