# INSERTING ROLES INTO DB
INSERT INTO ROLE (id, role)
VALUES (1, 'ADMIN');
INSERT INTO ROLE (id, role)
VALUES (2, 'MANAGER');
INSERT INTO ROLE (id, role)
VALUES (3, 'CUSTOMER');
INSERT INTO ROLE (id, role)
VALUES (4, 'BIKER');

# INSERTING ADMIN, MANAGER, AND A SIMPLE CUSTOMER INTO DB
# Note that the password field is the encrypted of "P@ssw0rd"
# ADMIN or let's say the SYSTEM
INSERT INTO CUSTOMER (city, country, street, zipcode, email, firstname, lastname, password, phone_number, created_on,
                      last_updated_on)
VALUES ('Tehran', 'Iran', 'Azadi', '123123123', 'admin@gmail.com', 'John', 'Doe',
        '$e0801$2u8TUqRLMOrXqk4XciUUEQBbj38mZsyg5D2KK+LrFRUroParRBqbOeGIdArLOLLQyPidM5eqCTOGr8MZ89vlNw==$JGtdnHOaTDOU39n2nj/C+t8ORS8Of3m90iL9swakWBs=',
        '09001000000',
        CURRENT_TIMESTAMP, null);

# MANAGER
INSERT INTO CUSTOMER (city, country, street, zipcode, email, firstname, lastname, password, phone_number, created_on,
                      last_updated_on)
VALUES ('Scranton', 'U.S', 'Slough Avenue', '185057427', 'manager@gmail.com', 'Micheal', 'Scott',
        '$e0801$2u8TUqRLMOrXqk4XciUUEQBbj38mZsyg5D2KK+LrFRUroParRBqbOeGIdArLOLLQyPidM5eqCTOGr8MZ89vlNw==$JGtdnHOaTDOU39n2nj/C+t8ORS8Of3m90iL9swakWBs=',
        '09002000000',
        CURRENT_TIMESTAMP, null);

# CUSTOMER
INSERT INTO CUSTOMER (city, country, street, zipcode, email, firstname, lastname, password, phone_number, created_on,
                      last_updated_on)
VALUES ('Tehran', 'Iran', 'Shoosh', '192819827', 'customer@gmail.com', 'Sarah', 'Dean',
        '$e0801$2u8TUqRLMOrXqk4XciUUEQBbj38mZsyg5D2KK+LrFRUroParRBqbOeGIdArLOLLQyPidM5eqCTOGr8MZ89vlNw==$JGtdnHOaTDOU39n2nj/C+t8ORS8Of3m90iL9swakWBs=',
        '09003000000',
        CURRENT_TIMESTAMP, null);

# CUSTOMER
INSERT INTO CUSTOMER (city, country, street, zipcode, email, firstname, lastname, password, phone_number, created_on,
                      last_updated_on)
VALUES ('Tehran', 'Iran', 'Hakimieh', '817361726', 'customer2@gmail.com', 'Mohammad', 'Mohammadi',
        '$e0801$2u8TUqRLMOrXqk4XciUUEQBbj38mZsyg5D2KK+LrFRUroParRBqbOeGIdArLOLLQyPidM5eqCTOGr8MZ89vlNw==$JGtdnHOaTDOU39n2nj/C+t8ORS8Of3m90iL9swakWBs=',
        '09003000001',
        CURRENT_TIMESTAMP, null);

# INSERTING Customer_Roles
insert into customer_roles (customer_id, roles_id) VALUES (1, 1);
insert into customer_roles (customer_id, roles_id) VALUES (2, 2);
insert into customer_roles (customer_id, roles_id) VALUES (3, 3);
insert into customer_roles (customer_id, roles_id) VALUES (4, 3);

# INSERTING SOME BIKERS INTO DB
INSERT INTO BIKER (name, phone_number, age)
VALUES ('simple biker', '09009000000', 30);
INSERT INTO BIKER (name, phone_number, age)
VALUES ('simple biker 2', '09009000001', 20);
INSERT INTO BIKER (name, phone_number, age)
VALUES ('simple biker 3', '09009000002', 40);

# INSERTING SOME DELIVERIES INTO DB
INSERT INTO delivery (biker_id, customer_id, created_on)
VALUES (1, 3, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, customer_id, created_on)
VALUES (1, 3, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, customer_id, created_on)
VALUES (1, 3, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, customer_id, created_on)
VALUES (1, 4, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, customer_id, created_on)
VALUES (1, 4, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, customer_id, created_on)
VALUES (2, 3, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, customer_id, created_on)
VALUES (2, 3, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, customer_id, created_on)
VALUES (2, 3, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, customer_id, created_on)
VALUES (2, 3, CURRENT_TIMESTAMP);
INSERT INTO DELIVERY (biker_id, customer_id, created_on)
VALUES (2, 3, CURRENT_TIMESTAMP)