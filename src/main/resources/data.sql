-- Users
INSERT INTO users (username, password) VALUES ('user1', 'pass1');
INSERT INTO users (username, password) VALUES ('user2', 'pass2');
INSERT INTO users (username, password) VALUES ('user3', 'pass3');

-- Categories
INSERT INTO category (name) VALUES ('Electronics');
INSERT INTO category (name) VALUES ('Books');
INSERT INTO category (name) VALUES ('Home Goods');

-- Products
INSERT INTO product (name, price) VALUES ('Laptop', 1200.00);
INSERT INTO product (name, price) VALUES ('Smartphone', 800.00);
INSERT INTO product (name, price) VALUES ('Headphones', 150.00);
INSERT INTO product (name, price) VALUES ('Science Fiction Novel', 15.00);
INSERT INTO product (name, price) VALUES ('History of Rome', 25.00);
INSERT INTO product (name, price) VALUES ('Coffee Maker', 50.00);
INSERT INTO product (name, price) VALUES ('Desk Chair', 180.00);

-- Product-Category Mappings (N:N)
INSERT INTO product_category (product_id, category_id) VALUES (1, 1); -- Laptop -> Electronics
INSERT INTO product_category (product_id, category_id) VALUES (2, 1); -- Smartphone -> Electronics
INSERT INTO product_category (product_id, category_id) VALUES (3, 1); -- Headphones -> Electronics
INSERT INTO product_category (product_id, category_id) VALUES (4, 2); -- Sci-Fi Novel -> Books
INSERT INTO product_category (product_id, category_id) VALUES (5, 2); -- History Book -> Books
INSERT INTO product_category (product_id, category_id) VALUES (6, 3); -- Coffee Maker -> Home Goods
INSERT INTO product_category (product_id, category_id) VALUES (7, 3); -- Desk Chair -> Home Goods

-- Orders for user1
INSERT INTO orders (user_id, order_date) VALUES (1, NOW());
INSERT INTO order_item (order_id, product_id, quantity) VALUES (1, 1, 1); -- 1 Laptop
INSERT INTO order_item (order_id, product_id, quantity) VALUES (1, 3, 2); -- 2 Headphones

-- Orders for user2
INSERT INTO orders (user_id, order_date) VALUES (2, NOW());
INSERT INTO order_item (order_id, product_id, quantity) VALUES (2, 4, 5); -- 5 Sci-Fi Novels
INSERT INTO order_item (order_id, product_id, quantity) VALUES (2, 5, 1); -- 1 History Book

