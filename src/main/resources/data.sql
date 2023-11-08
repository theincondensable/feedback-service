INSERT INTO ROLE (id, role) VALUES(1, 'ADMIN');
INSERT INTO ROLE (id, role) VALUES(2, 'MANAGER');
INSERT INTO ROLE (id, role) VALUES(3, 'CUSTOMER');
INSERT INTO ROLE (id, role) VALUES(4, 'BIKER');

INSERT INTO BIKER (name, phone_number, age) VALUES('simple biker', '09009000000', 30);

INSERT INTO delivery (biker_id, customer_id) VALUES(1, 1);
INSERT INTO delivery (biker_id, customer_id) VALUES(1, 1);
INSERT INTO delivery (biker_id, customer_id) VALUES(1, 1);