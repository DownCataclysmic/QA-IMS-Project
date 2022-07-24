DROP SCHEMA IF EXISTS imsproject;

CREATE SCHEMA IF NOT EXISTS `imsproject`;

USE `imsproject` ;

CREATE TABLE IF NOT EXISTS customers
(
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(40) DEFAULT NULL,
    surname VARCHAR(40) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS items
(
    item_id INT NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(40) NOT NULL,
    item_price DOUBLE NOT NULL,
    PRIMARY KEY (item_id)
);

CREATE TABLE IF NOT EXISTS orders
(
    orders_id INT NOT NULL AUTO_INCREMENT,
    orders_total_price DOUBLE NOT NULL,
    fk_customers_id INT NOT NULL,
    PRIMARY KEY (orders_id),
    FOREIGN KEY (fk_customers_id) REFERENCES customers (id)
);

CREATE TABLE IF NOT EXISTS order_items
(
    fk_orders_id INT NOT NULL,
    fk_items_id INT NOT NULL,
    FOREIGN KEY (fk_orders_id) REFERENCES orders (orders_id),
    FOREIGN KEY (fk_items_id) REFERENCES items (item_id)
);

	);