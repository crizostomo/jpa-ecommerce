insert into product (id, version, name, price, creation_date, active, description) values (1, 0, 'Kindle', 800.0, date_sub(sysdate(), interval 1 day), 'YES', 'Check it out the new Kindle.');
insert into product (id, version, name, price, creation_date, active, description) values (3, 0, 'GoPro Camera Hero 7', 1400.0, date_sub(sysdate(), interval 1 day), 'YES', 'Check it out the new Camera.');
insert into product (id, version, name, price, creation_date, active, description) values (4, 0, 'Kindle III', 3500.0, date_sub(sysdate(), interval 1 day), 'YES', 'Check it out the new Kindle III.');
insert into product (id, version, name, price, creation_date, active, description) values (5, 0, 'Tech Mic', 50.0, date_sub(sysdate(), interval 1 day), 'NO', 'New tech mic');

insert into client (id, version, name, cpf) values (1, 0, 'Roronoa Zoro', '000');
insert into client (id, version, name, cpf) values (2, 0, 'Monkey D. Luffy', '333');

insert into client_detail (client_id, gender, birth_date) values (1, 'MALE', date_sub(sysdate(), interval 27 year));
insert into client_detail (client_id, gender, birth_date) values (2, 'MALE', date_sub(sysdate(), interval 30 year));

insert into `order` (id, client_id, version, creation_date, total, status) values (1, 1, 0, date_sub(sysdate(), interval 32 day), 2100.0, 'CANCELLED');
insert into `order` (id, client_id, version, creation_date, total, status) values (2, 1, 0, date_sub(sysdate(), interval 5 day), 500.0, 'WAITING');
insert into `order` (id, client_id, version, creation_date, total, status) values (3, 1, 0, date_sub(sysdate(), interval 4 day), 3500.0, 'PAID');
insert into `order` (id, client_id, version, creation_date, total, status) values (4, 2, 0, date_sub(sysdate(), interval 2 day), 500.0, 'PAID');
insert into `order` (id, client_id, version, creation_date, total, status) values (5, 1, 0, date_sub(sysdate(), interval 2 day), 800.0, 'PAID');
insert into `order` (id, client_id, version, creation_date, total, status) values (6, 2, 0, sysdate(), 800.0, 'WAITING');

insert into order_item (order_id, product_id, version, product_price, quantity) values (1, 1, 0, 500, 2);
insert into order_item (order_id, product_id, version, product_price, quantity) values (1, 3, 0, 1400, 1);
insert into order_item (order_id, product_id, version, product_price, quantity) values (2, 1, 0, 500, 1);
insert into order_item (order_id, product_id, version, product_price, quantity) values (3, 4, 0, 3500, 1);
insert into order_item (order_id, product_id, version, product_price, quantity) values (4, 1, 0, 500, 1);
insert into order_item (order_id, product_id, version, product_price, quantity) values (5, 1, 0, 800, 1);
insert into order_item (order_id, product_id, version, product_price, quantity) values (6, 1, 0, 800, 1);

insert into category (id, version, name) values (1, 0, 'Electronics Products');
insert into category (id, version, name) values (2, 0, 'Sci-Fi Books');
insert into category (id, version, name) values (3, 0, 'Sports');
insert into category (id, version, name) values (4, 0, 'Soccer');
insert into category (id, version, name) values (5, 0, 'Swimming');
insert into category (id, version, name) values (6, 0, 'Laptops');
insert into category (id, version, name) values (7, 0, 'Smartphones');
insert into category (id, version, name) values (8, 0, 'Cameras');

insert into product_category (product_id, category_id) values (1, 2);
insert into product_category (product_id, category_id) values (3, 8);
insert into product_category (product_id, category_id) values (4, 8);

insert into payment (order_id, version, status, card_number, payment_type, barcode) values (1, 0, 'RECEIVED', '123', 'card', null);
insert into payment (order_id, version, status, card_number, payment_type, barcode) values (2, 0, 'PROCESSING', '123', 'card', null);
insert into payment (order_id, version, status, card_number, payment_type, barcode, due_date) values (3, 0, 'RECEIVED', 'null', 'slip', '8910', date_sub(sysdate(), interval 2 day));
insert into payment (order_id, version, status, card_number, payment_type, barcode) values (4, 0, 'PROCESSING', '123', 'card', null);
insert into payment (order_id, version, status, card_number, payment_type, barcode, due_date) values (6, 0, 'PROCESSING', 'null', 'slip', '4567', date_add(sysdate(), interval 2 day));
--insert into card_payment (order_id, card_number) values (2, '123');

insert into invoice (order_id, version, xml, issuing_date) values (2, 0, '<xml />', sysdate());

insert into store_product (id, name, price, creation_date, description) values (101, 'Kindle', 800.0, date_sub(sysdate(), interval 1 day), 'Check it out the new Kindle.');
insert into store_product (id, name, price, creation_date, description) values (103, 'GoPro Camera Hero 7', 1400.0, date_sub(sysdate(), interval 1 day), 'Check it out the new Camera.');
insert into store_product (id, name, price, creation_date, description) values (104, 'Kindle III', 3500.0, date_sub(sysdate(), interval 1 day), 'Check it out the new Kindle III.');
insert into store_product (id, name, price, creation_date, description) values (105, 'Tech Mic', 50.0, date_sub(sysdate(), interval 1 day), 'New tech mic');

insert into product_ecm (prd_id, prd_name, prd_price, prd_creation_date, prd_description) values (201, 'Kindle', 800.0, date_sub(sysdate(), interval 1 day), 'Check it out the new Kindle.');
insert into product_ecm (prd_id, prd_name, prd_price, prd_creation_date, prd_description) values (203, 'GoPro Camera Hero 7', 1400.0, date_sub(sysdate(), interval 1 day), 'Check it out the new Camera.');
insert into product_ecm (prd_id, prd_name, prd_price, prd_creation_date, prd_description) values (204, 'Kindle III', 3500.0, date_sub(sysdate(), interval 1 day), 'Check it out the new Kindle III.');
insert into product_ecm (prd_id, prd_name, prd_price, prd_creation_date, prd_description) values (205, 'Tech Mic', 50.0, date_sub(sysdate(), interval 1 day), 'New tech mic');

insert into product_erp (id, name, price, description) values (301, 'Kindle', 800.0, 'Check it out the new Kindle.');
insert into product_erp (id, name, price, description) values (303, 'GoPro Camera Hero 7', 1400.0, 'Check it out the new Camera.');
insert into product_erp (id, name, price, description) values (304, 'Kindle III', 3500.0, 'Check it out the new Kindle III.');
insert into product_erp (id, name, price, description) values (305, 'Tech Mic', 50.0, 'New tech mic');

insert into category_ecm (cat_id, cat_name) values (201, 'Cat Electronics Products');
insert into category_ecm (cat_id, cat_name) values (202, 'Cat Sci-Fi Books');
insert into category_ecm (cat_id, cat_name) values (203, 'Cat Sports');
insert into category_ecm (cat_id, cat_name) values (204, 'Cat Soccer');
insert into category_ecm (cat_id, cat_name) values (205, 'Cat Swimming');
insert into category_ecm (cat_id, cat_name) values (206, 'Cat Laptops');
insert into category_ecm (cat_id, cat_name) values (207, 'Cat Smartphones');
insert into category_ecm (cat_id, cat_name) values (208, 'Cat Cameras');