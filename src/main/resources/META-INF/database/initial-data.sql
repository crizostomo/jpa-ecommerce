insert into product (id, name, price, creation_date, description) values (1, 'Kindle', 800.0, date_sub(sysdate(), interval 1 day), 'Check it out the new Kindle.');
insert into product (id, name, price, creation_date, description) values (3, 'GoPro Camera Hero 7', 1400.0, date_sub(sysdate(), interval 1 day), 'Check it out the new Camera.');
insert into product (id, name, price, creation_date, description) values (4, 'Kindle III', 3500.0, date_sub(sysdate(), interval 1 day), 'Check it out the new Kindle III.');
insert into product (id, name, price, creation_date, description) values (5, 'Tech Mic', 50.0, date_sub(sysdate(), interval 1 day), 'New tech mic');

insert into client (id, name, cpf) values (1, 'Roronoa Zoro', '000');
insert into client (id, name, cpf) values (2, 'Monkey D. Luffy', '333');

insert into client_detail (client_id, gender, birth_date) values (1, 'MALE', date_sub(sysdate(), interval 27 year));
insert into client_detail (client_id, gender, birth_date) values (2, 'MALE', date_sub(sysdate(), interval 30 year));

insert into `order` (id, client_id, creation_date, total, status) values (1, 1, date_sub(sysdate(), interval 32 day), 2100.0, 'CANCELLED');
insert into `order` (id, client_id, creation_date, total, status) values (2, 1, date_sub(sysdate(), interval 5 day), 500.0, 'WAITING');
insert into `order` (id, client_id, creation_date, total, status) values (3, 1, date_sub(sysdate(), interval 4 day), 3500.0, 'PAID');
insert into `order` (id, client_id, creation_date, total, status) values (4, 2, date_sub(sysdate(), interval 2 day), 500.0, 'PAID');
insert into `order` (id, client_id, creation_date, total, status) values (5, 1, date_sub(sysdate(), interval 2 day), 800.0, 'PAID');
insert into `order` (id, client_id, creation_date, total, status) values (6, 2, sysdate(), 800.0, 'WAITING');

insert into order_item (order_id, product_id, product_price, quantity) values (1, 1, 500, 2);
insert into order_item (order_id, product_id, product_price, quantity) values (1, 3, 1400, 1);
insert into order_item (order_id, product_id, product_price, quantity) values (2, 1, 500, 1);
insert into order_item (order_id, product_id, product_price, quantity) values (3, 4, 3500, 1);
insert into order_item (order_id, product_id, product_price, quantity) values (4, 1, 500, 1);
insert into order_item (order_id, product_id, product_price, quantity) values (5, 1, 800, 1);
insert into order_item (order_id, product_id, product_price, quantity) values (6, 1, 800, 1);

insert into category (name) values ('Electronics Products');
insert into category (name) values ('Sci-Fi Books');
insert into category (name) values ('Sports');
insert into category (name) values ('Soccer');
insert into category (name) values ('Swimming');
insert into category (name) values ('Laptops');
insert into category (name) values ('Smartphones');
insert into category (name) values ('Cameras');

insert into product_category (product_id, category_id) values (1, 2);
insert into product_category (product_id, category_id) values (3, 8);
insert into product_category (product_id, category_id) values (4, 8);

insert into payment (order_id, status, card_number, payment_type, barcode) values (1, 'RECEIVED', '123', 'card', null);
insert into payment (order_id, status, card_number, payment_type, barcode) values (2, 'PROCESSING', '123', 'card', null);
insert into payment (order_id, status, card_number, payment_type, barcode, due_date) values (3, 'RECEIVED', 'null', 'slip', '8910', date_sub(sysdate(), interval 2 day));
insert into payment (order_id, status, card_number, payment_type, barcode) values (4, 'PROCESSING', '123', 'card', null);
insert into payment (order_id, status, card_number, payment_type, barcode, due_date) values (6, 'PROCESSING', 'null', 'slip', '4567', date_add(sysdate(), interval 2 day));
--insert into card_payment (order_id, card_number) values (2, '123');

insert into invoice (order_id, xml, issuing_date) values (2, '<xml />', sysdate());