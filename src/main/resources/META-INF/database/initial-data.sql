insert into product (id, name, price, creation_date, description) values (1, 'Kindle', 500.0, date_sub(sysdate(), interval 1 day), 'Check it out the new Kindle.');
insert into product (id, name, price, creation_date, description) values (3, 'Kindle II', 700.0, date_sub(sysdate(), interval 1 day), 'Check it out the new Kindle II.');

insert into client (id, name, cpf) values (1, 'Roronoa Zoro', '000');
insert into client (id, name, cpf) values (2, 'Monkey D. Luffy', '333');

insert into client_detail (client_id, gender, birth_date) values (1, 'MALE', date_sub(sysdate(), interval 27 year));
insert into client_detail (client_id, gender, birth_date) values (2, 'MALE', date_sub(sysdate(), interval 30 year));

insert into `order` (id, client_id, creation_date, total, status) values (1, 1, sysdate(), 1700.0, 'WAITING');
insert into `order` (id, client_id, creation_date, total, status) values (2, 1, sysdate(), 500.0, 'WAITING');

insert into order_item (order_id, product_id, product_price, quantity) values (1, 1, 500, 2);
insert into order_item (order_id, product_id, product_price, quantity) values (1, 3, 700, 1);
insert into order_item (order_id, product_id, product_price, quantity) values (2, 1, 500, 1);

insert into category (id, name) values (1, 'Electronics Products');
insert into category (id, name) values (2, 'Sci-Fi Books');

insert into product_category (product_id, category_id) values (1, 2);

insert into payment (order_id, status, card_number, payment_type) values (2, 'PROCESSING', '123', 'card');
--insert into card_payment (order_id, card_number) values (2, '123');