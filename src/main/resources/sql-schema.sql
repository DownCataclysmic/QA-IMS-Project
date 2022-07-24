DROP SCHEMA IF EXISTS imsproject;

CREATE SCHEMA IF NOT EXISTS `imsproject`;

USE `imsproject` ;

CREATE TABLE IF NOT EXISTS customer
(
    customer_id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(40) DEFAULT NULL,
    surname VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (customer_id)
);

CREATE TABLE IF NOT EXISTS items
(
    item_id INT NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(40) NOT NULL,
    unit_price DOUBLE NOT NULL,
    PRIMARY KEY (item_id)
);

CREATE TABLE IF NOT EXISTS orders
(
    order_id INT NOT NULL AUTO_INCREMENT,
    order_price DOUBLE NOT NULL,
    quantity INT DEFAULT NULL,
    fk_customer_id INT NOT NULL,
    PRIMARY KEY (order_id),
    FOREIGN KEY (fk_customer_id) REFERENCES customer (customer_id)
);

CREATE TABLE IF NOT EXISTS order_items
(
    fk_order_id INT NOT NULL,
    fk_item_id INT NOT NULL,
    quantity INT DEFAULT NULL,
    FOREIGN KEY (fk_order_id) REFERENCES orders (order_id),
    FOREIGN KEY (fk_item_id) REFERENCES items (item_id)
);
