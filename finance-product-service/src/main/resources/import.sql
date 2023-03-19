insert into USERS(username, password, role, refresh_token) values('Swapnil', '$2a$12$.T9auUh7ryfqVyWWwU7T4OOPsUv1HvClWu3H/pIA9bKUkdhbLoD4y', 'USER', null);
insert into USERS(username, password, role, refresh_token) values('Admin', '$2a$12$0iK1bF0JpPfAgXUO5WXNPe2oH7NN6V44vj.WRQ3dpZib.L2ZGQTrq', 'ADMIN', null);

insert into PRODUCTS(id, name, description, image_url, charge) VALUES((VALUES NEXT VALUE FOR PRODUCT_SEQ), 'Debit Card', 'Normal Debit Card', 'images/Blue-Credit-Card.png', 100);
insert into PRODUCTS(id, name, description, image_url, charge) VALUES((VALUES NEXT VALUE FOR PRODUCT_SEQ), 'Debit Card', 'Express Debit Card', 'images/consumer-platinum-credit-card_1280x720.png', 150);
insert into PRODUCTS(id, name, description, image_url, charge) VALUES((VALUES NEXT VALUE FOR PRODUCT_SEQ), 'Debit Card', 'Platinum Debit Card', 'images/debitblue.png', 350);
insert into PRODUCTS(id, name, description, image_url, charge) VALUES((VALUES NEXT VALUE FOR PRODUCT_SEQ), 'Credit Card', 'Normal Credit Card', 'images/Blue-Credit-Card-PNG-Clipart.png', 150);
insert into PRODUCTS(id, name, description, image_url, charge) VALUES((VALUES NEXT VALUE FOR PRODUCT_SEQ), 'Credit Card', 'Express Credit Card', 'images/Credit-Card-PNG-Pic.png', 200);
insert into PRODUCTS(id, name, description, image_url, charge) VALUES((VALUES NEXT VALUE FOR PRODUCT_SEQ), 'Credit Card', 'Platinum Credit Card', 'images/Credit-Card-PNG-HD-Image.png', 420);


insert into check_book(id, pages, rate_per_page) values((VALUES NEXT VALUE FOR CHKBK_SEQ), 20, 20);
insert into check_book(id, pages, rate_per_page) values((VALUES NEXT VALUE FOR CHKBK_SEQ), 50, 30);
insert into check_book(id, pages, rate_per_page) values((VALUES NEXT VALUE FOR CHKBK_SEQ), 100, 40);
insert into check_book(id, pages, rate_per_page) values((VALUES NEXT VALUE FOR CHKBK_SEQ), 200, 50);

insert into CURRENCY(currency) values ('INR')
insert into CURRENCY(currency) values ('USD')
insert into CURRENCY(currency) values ('EUR')
insert into CURRENCY(currency) values ('MUR')