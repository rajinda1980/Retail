DROP TABLE IF EXISTS `inventory-service`.`item`;

CREATE TABLE `inventory-service`.`item` (
    did BIGINT AUTO_INCREMENT NOT NULL,
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NULL,
    created_date TIMESTAMP NULL,
    updated_date TIMESTAMP NULL,
    qty INT NULL,
    rol INT NULL,
    CONSTRAINT pk_inventory PRIMARY KEY (did)
);