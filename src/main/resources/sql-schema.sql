drop schema imsproject;

CREATE SCHEMA IF NOT EXISTS `imsproject`;

USE `imsproject` ;

CREATE TABLE IF NOT EXISTS `customers` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR (40) DEFAULT NULL,
    `surname` VARCHAR (40) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `items` (
	`item_id` INT NOT NULL AUTO_INCREMENT,
	`item_name` VARCHAR (40) DEFAULT NULL,
	`item_price` DOUBLE DEFAULT NULL,
	PRIMARY KEY (`item_id`)
);

CREATE TABLE IF NOT EXISTS `orders` (
	`order_id` INT NOT NULL AUTO_INCREMENT,
	`order_placed` DATE DEFAULT NULL,
    `total_price` DOUBLE DEFAULT NULL,
	`fk_customer_id` INT NOT NULL,
	PRIMARY KEY (`order_id`),
	CONSTRAINT `fk_customer_id` FOREIGN KEY (`fk_customer_id`) REFERENCES `customers` (`id`)
);

CREATE TABLE IF NOT EXISTS `order_items` (
	`order_item_id` INT NOT NULL AUTO_INCREMENT,
	`total_price` DOUBLE DEFAULT NULL,
	`fk_order_id` INT NOT NULL,
	`fk_item_id` INT NOT NULL,
	PRIMARY KEY (`order_item_id`),
	CONSTRAINT `fk_order_id` FOREIGN KEY (`fk_order_id`) REFERENCES `orders` (`order_id`),
	CONSTRAINT `fk_item_id` FOREIGN KEY (`fk_item_id`) REFERENCES `items` (`item_id`)
);