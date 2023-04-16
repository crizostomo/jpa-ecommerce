drop table if exists testing;

alter table category drop foreign key fk_category_category_father;
alter table client_contact drop foreign key fk_client_contact_client;
alter table client_detail drop foreign key fk_client_detail_client;
alter table stock drop foreign key fk_stock_product;
alter table order_item drop foreign key fk_order_item_order;
alter table order_item drop foreign key fk_order_item_product;
alter table invoice drop foreign key fk_invoice_order;
alter table payment drop foreign key fk_payment_order;
alter table `order` drop foreign key fk_order_client;
alter table product_attribute drop foreign key fk_product_attribute_product;
alter table product_category drop foreign key fk_product_category_category;
alter table product_category drop foreign key fk_product_category_product;
alter table product_tag drop foreign key fk_product_tag_product;
drop table if exists category;
drop table if exists client;
drop table if exists client_contact;
drop table if exists client_detail;
drop table if exists stock;
drop table if exists order_item;
drop table if exists invoice;
drop table if exists payment;
drop table if exists `order`;
drop table if exists product;
drop table if exists product_attribute;
drop table if exists product_category;
drop table if exists product_tag;