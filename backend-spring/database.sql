drop database shopapp;
CREATE DATABASE shopapp;
use shopapp;

CREATE TABLE users(
    id INT PRIMARY KEY auto_increment,
    fullname VARCHAR(100) NOT NULL,
    phone_number VARCHAR(10) NOT NULL,
    address VARCHAR(255) DEFAULT '',
    password VARCHAR(255) NOT NULL,
    created_at datetime,
    updated_at datetime,
    is_active tinyint(1) DEFAULT 1,
    date_of_birth date,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0,
    role_id INT NOT NULL
);

CREATE TABLE tokens(
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL unique,
    token_type VARCHAR(255) NOT NULL,
    expiration_date datetime,
    revoked tinyint(1) NOT NULL,
    expired tinyint(1) NOT NULL,
    user_id INT,
    foreign key(user_id) references users(id)
);

CREATE TABLE social_accounts(
    id INT AUTO_INCREMENT PRIMARY KEY,
    provider VARCHAR(255) NOT NULL comment 'facebook or google',
    provider_id VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL comment 'email of user',
    name VARCHAR(255) NOT NULL comment 'name of user',
    user_id INT,
    foreign key(user_id) references users(id)
);


CREATE TABLE categories(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL DEFAULT '' comment 'name of category'
);

CREATE TABLE products(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL comment 'name of product',
    price float NOT NULL check(price >= 0),
    thumbnail VARCHAR(255) DEFAULT '',
    description LONGTEXT,
    created_at datetime,
    updated_at datetime,
    category_id INT,
    foreign key(category_id) references categories(id)
);


CREATE TABLE product_images(
    id INT PRIMARY key AUTO_INCREMENT,
    product_id INT,
    foreign KEY (product_id) references products (id),
    constraint fk_product_images_product_id foreign key (product_id) references products (id) on delete cascade,
    image_url VARCHAR(300) 
);

CREATE TABLE orders(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    foreign key(user_id) references users(id),
    fullname VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(20) NOT NULL,
    email VARCHAR(255) DEFAULT '',
    address VARCHAR(255) NOT NULL,
    note VARCHAR(255) DEFAULT '',
    order_date datetime DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20),
    total_money float check(total_money >= 0)
);

alter TABLE orders add column shipping_method VARCHAR(255);
alter TABLE orders add column shipping_address VARCHAR(255);
alter TABLE orders add column shipping_date date;
alter TABLE orders add column tracking_number VARCHAR(255);
alter TABLE orders add column payment_method VARCHAR(255);
alter TABLE orders add column active tinyint(1);
alter TABLE orders modify column status enum('pending', 'processing', 'shipped', 'delivered', 'cancelled') comment 'status of order';

CREATE TABLE order_details(
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    foreign key(order_id) references orders(id),
    product_id INT,
    foreign key(product_id) references products(id),
    price float NOT NULL check(price >= 0),
    number_of_product INT NOT NULL check(number_of_product > 0),
    total_money float check(total_money >= 0),
    color VARCHAR(255) DEFAULT ''
);