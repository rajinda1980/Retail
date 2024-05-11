DROP TABLE IF EXISTS `order-item`;
DROP TABLE IF EXISTS `orders`;

CREATE TABLE `order-service`.`orders` (
    did BIGINT AUTO_INCREMENT NOT NULL,
    order_id VARCHAR(255) NOT NULL,
    created_by VARCHAR(255) NULL,
    created_date TIMESTAMP NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (did)
);

CREATE TABLE `order-service`.`order-item` (
    did BIGINT AUTO_INCREMENT NOT NULL,
    item_id VARCHAR(255) NULL,
    qty INT NULL,
    price DECIMAL(10,2) NULL,
    order_did BIGINT NOT NULL,
    CONSTRAINT `pk_order-item` PRIMARY KEY (did),
    FOREIGN KEY (order_did) REFERENCES `orders`(did)
);
