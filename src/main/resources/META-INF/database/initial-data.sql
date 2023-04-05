insert into product (id, name, price, description) values (1, 'Kindle', 499.0, 'Check it out the new Kindle.');
insert into product (id, name, price, description) values (3, 'Kindle II', 699.0, 'Check it out the new Kindle II.');

insert into client (id, name) values (1, 'Roronoa Zoro.');
insert into client (id, name) values (2, 'Monkey D. Luffy.');

insert into order (id, client_id, order_date, total, status) values (1, 1, sysdate(), 100.0, 'WAITING');

insert into order_item (id, order_id, product_id, product_price, quantity) values (1, 1, 1, 5.0, 2);
