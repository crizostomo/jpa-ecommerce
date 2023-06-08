create table testing (id integer not null auto_increment, primary key (id)) engine=InnoDB;

create table store_product (id integer not null auto_increment, name varchar(100), description longtext, price decimal(19, 2), creation_date datetime(6), last_update_date datetime(6), photo longblob, primary key (id)) engine=InnoDB;

create table product_ecm (prd_id integer not null auto_increment, prd_name varchar(100), prd_description longtext, prd_price decimal(19, 2), prd_creation_date datetime(6), prd_last_update_date datetime(6), prd_photo longblob, primary key (prd_id)) engine=InnoDB;

create table product_erp (id integer not null auto_increment, name varchar(100), description longtext, price decimal(19, 2), primary key (id)) engine=InnoDB;

create table category_ecm (cat_id integer not null auto_increment, cat_name varchar(100), cat_father_category_id integer, primary key (cat_id)) engine=InnoDB;

create function invoice_above_average(`value` double) returns boolean reads sql data return `value` > (select avg(total) from `order`);

create procedure search_product_name(in product_id int, out product_name varchar(255)) begin select name into product_name from product where id = product_id; end

create procedure bought_above_average(in ano integer) begin select cli.* from client cli join `order` ord on ord.client_id = cli.id where ord.status = 'PAID' and year(ord.creation_date) = ano group by ord.client_id having sum(ord.total) >= (select avg(total_per_client.sum_total) from (select sum(ord2.total) sum_total from `order` ord2 where ord.status = 'PAID' and year(ord2.creation_date) = ano group by ord2.client_id) as total_per_client); end

create procedure adjust_product_price(in product_id int, in percentage_adjust double, out adjusted_price double) begin declare product_price double; select price into product_price from product where id = product_id; set adjusted_price = product_price + (product_price * percentage_adjust); update product set price = adjusted_price where id = product_id; end

--create table category (id integer not null auto_increment, name varchar(100) not null, father_category_id integer, primary key (id)) engine=InnoDB;
--create table client (id integer not null auto_increment, cpf varchar(14) not null, name varchar(100) not null, primary key (id)) engine=InnoDB;
--create table client_contact (client_id integer not null, description varchar(255), type varchar(255) not null, primary key (client_id, type)) engine=InnoDB;
--create table client_detail (birth_date date, gender varchar(30) not null, client_id integer not null, primary key (client_id)) engine=InnoDB;
--create table stock (id integer not null auto_increment, quantity integer, product_id integer not null, primary key (id)) engine=InnoDB;
--create table order_item (order_id integer not null, product_id integer not null, product_price decimal(19,2) not null, quantity integer not null, primary key (order_id, product_id)) engine=InnoDB;
--create table invoice (order_id integer not null, issuing_date datetime(6) not null, xml longblob not null, primary key (order_id)) engine=InnoDB;
--create table payment (payment_type varchar(31) not null, order_id integer not null, status varchar(30) not null, card_number varchar(50), barcode varchar(100), primary key (order_id)) engine=InnoDB;
--create table `order` (id integer not null auto_increment, ending_date datetime(6), creation_date datetime(6) not null, last_update_date datetime(6), neighborhood varchar(50), zipCode varchar(9), city varchar(50), complement varchar(50), state varchar(50), street varchar(100), number varchar(10), status varchar(30) not null, total decimal(19,2) not null, client_id integer not null, primary key (id)) engine=InnoDB;
--create table product (id integer not null auto_increment, creation_date datetime(6) not null, last_update_date datetime(6), description longtext, photo longblob, name varchar(100) not null, price decimal(19,2), primary key (id)) engine=InnoDB;
--create table product_attribute (product_id integer not null, name varchar(100) not null, value varchar(255)) engine=InnoDB;
--create table product_category (product_id integer not null, category_id integer not null) engine=InnoDB;
--create table product_tag (product_id integer not null, tag varchar(50) not null) engine=InnoDB;
--
--alter table category add constraint unq_name unique (name);
--create index idx_name on client (name);
--alter table client add constraint unq_cpf unique (cpf);
--alter table stock add constraint UK_g72w2sa50w9a647f0eyhogus5 unique (product_id);
--create index idx_name on product (name);
--alter table product add constraint unq_name unique (name);
--alter table category add constraint fk_category_category_father foreign key (father_category_id) references category (id);
--alter table client_contact add constraint fk_client_contact_client foreign key (client_id) references client (id);
--alter table client_detail add constraint fk_client_detail_client foreign key (client_id) references client (id);
--alter table stock add constraint fk_stock_product foreign key (product_id) references product (id);
--alter table order_item add constraint fk_order_item_order foreign key (order_id) references `order` (id);
--alter table order_item add constraint fk_order_item_product foreign key (product_id) references product (id);
--alter table invoice add constraint fk_invoice_order foreign key (order_id) references `order` (id);
--alter table payment add constraint fk_payment_order foreign key (order_id) references `order` (id);
--alter table `order` add constraint fk_order_client foreign key (client_id) references client (id);
--alter table product_attribute add constraint fk_product_attribute_product foreign key (product_id) references product (id);
--alter table product_category add constraint fk_product_category_category foreign key (category_id) references category (id);
--alter table product_category add constraint fk_product_category_product foreign key (product_id) references product (id);
--alter table product_tag add constraint fk_product_tag_product foreign key (product_id) references product (id);