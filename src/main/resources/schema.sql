CREATE TABLE IF NOT EXISTS `sitoo_test_assignment`.`product` (
    `product_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `title` VARCHAR ( 32 ) NOT NULL ,
    `sku` VARCHAR ( 32 ) NOT NULL ,
    `description` VARCHAR ( 1024 ) NULL ,
    `price` DECIMAL ( 12 , 2 ) NOT NULL DEFAULT 0.00 ,
    `created` DATETIME NOT NULL ,
    `last_updated` DATETIME NULL ,
    PRIMARY KEY ( `product_id` ),
    UNIQUE INDEX ( `sku` ASC ),
    INDEX ( `created` ),
    INDEX ( `last_updated` )
);

CREATE TABLE IF NOT EXISTS `sitoo_test_assignment` . `product_barcode` (
    `product_id` INT UNSIGNED NOT NULL ,
    `barcode` VARCHAR ( 32 ) NOT NULL ,
    PRIMARY KEY ( `product_id` , `barcode` ),
    UNIQUE INDEX ( `barcode` )
);
CREATE TABLE IF NOT EXISTS `sitoo_test_assignment` . `product_attribute` (
    `product_id` INT UNSIGNED NOT NULL ,
    `name` VARCHAR ( 16 ) NOT NULL ,
    `value` VARCHAR ( 32 ) NOT NULL ,
    PRIMARY KEY ( `product_id` , `name` )
);