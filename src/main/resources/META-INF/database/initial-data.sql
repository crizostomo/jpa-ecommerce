insert into product (id, name, price, creation_date, description) values (1, 'Kindle', 499.0, date_sub(sysdate(), interval 1 day), 'Check it out the new Kindle.');
insert into product (id, name, price, creation_date, description) values (3, 'Kindle II', 699.0, date_sub(sysdate(), interval 1 day), 'Check it out the new Kindle II.');

insert into client (id, name, cpf) values (1, 'Roronoa Zoro.', '000');
insert into client (id, name, cpf) values (2, 'Monkey D. Luffy.', '333');

insert into client_detail (client_id, gender, birth_date) values (1, 'Roronoa Zoro.', 'MALE', date_sub(sysdate(), interval 27 year));
insert into client_detail (client_id, gender, birth_date) values (2, 'Monkey D. Luffy.', 'MALE', date_sub(sysdate(), interval 30 year));

insert into order (id, client_id, creation_date, total, status) values (1, 1, sysdate(), 998.0, 'WAITING');
insert into order (id, client_id, creation_date, total, status) values (2, 1, sysdate(), 499.0, 'WAITING');

insert into order_item (order_id, product_id, product_price, quantity) values (1, 1, 499, 2);
insert into order_item (order_id, product_id, product_price, quantity) values (2, 1, 499, 1);

insert into category (id, name) values (1, 'Electronics Products');

insert into payment (order_id, status, card_number, payment_type) values (2, 'PROCESSING', '123', 'card');
--insert into card_payment (order_id, card_number) values (2, '123');