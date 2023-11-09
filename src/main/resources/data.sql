# START OF INSERTING CUSTOMERS
# INSERTING ROLES INTO DB
INSERT INTO ROLE (id, role)
VALUES (1, 'ADMIN');
INSERT INTO ROLE (id, role)
VALUES (2, 'MANAGER');
INSERT INTO ROLE (id, role)
VALUES (3, 'CUSTOMER');
INSERT INTO ROLE (id, role)
VALUES (4, 'BIKER');
# END OF INSERTING ROLES

# START OF INSERTING CUSTOMERS
# INSERTING ADMIN, MANAGER, AND A SIMPLE CUSTOMER INTO DB
# Note that the password field is the encrypted of "P@ssw0rd"
# ADMIN or let's say the SYSTEM
INSERT INTO USER (city, country, street, zipcode, email, firstname, lastname, password, phone_number, created_on,
                      last_updated_on)
VALUES ('Tehran', 'Iran', 'Azadi', '123123123', 'admin@gmail.com', 'John', 'Doe',
        '$e0801$2u8TUqRLMOrXqk4XciUUEQBbj38mZsyg5D2KK+LrFRUroParRBqbOeGIdArLOLLQyPidM5eqCTOGr8MZ89vlNw==$JGtdnHOaTDOU39n2nj/C+t8ORS8Of3m90iL9swakWBs=',
        '09001000000',
        CURRENT_TIMESTAMP, null);

# MANAGER
INSERT INTO USER (city, country, street, zipcode, email, firstname, lastname, password, phone_number, created_on,
                      last_updated_on)
VALUES ('Scranton', 'U.S', 'Slough Avenue', '185057427', 'manager@gmail.com', 'Micheal', 'Scott',
        '$e0801$2u8TUqRLMOrXqk4XciUUEQBbj38mZsyg5D2KK+LrFRUroParRBqbOeGIdArLOLLQyPidM5eqCTOGr8MZ89vlNw==$JGtdnHOaTDOU39n2nj/C+t8ORS8Of3m90iL9swakWBs=',
        '09002000000',
        CURRENT_TIMESTAMP, null);

# CUSTOMER
INSERT INTO USER (city, country, street, zipcode, email, firstname, lastname, password, phone_number, created_on,
                      last_updated_on)
VALUES ('Tehran', 'Iran', 'Shoosh', '192819827', 'customer@gmail.com', 'Sarah', 'Dean',
        '$e0801$2u8TUqRLMOrXqk4XciUUEQBbj38mZsyg5D2KK+LrFRUroParRBqbOeGIdArLOLLQyPidM5eqCTOGr8MZ89vlNw==$JGtdnHOaTDOU39n2nj/C+t8ORS8Of3m90iL9swakWBs=',
        '09003000000',
        CURRENT_TIMESTAMP, null);

# CUSTOMER
INSERT INTO USER (city, country, street, zipcode, email, firstname, lastname, password, phone_number, created_on,
                      last_updated_on)
VALUES ('Tehran', 'Iran', 'Hakimieh', '817361726', 'customer2@gmail.com', 'Mohammad', 'Mohammadi',
        '$e0801$2u8TUqRLMOrXqk4XciUUEQBbj38mZsyg5D2KK+LrFRUroParRBqbOeGIdArLOLLQyPidM5eqCTOGr8MZ89vlNw==$JGtdnHOaTDOU39n2nj/C+t8ORS8Of3m90iL9swakWBs=',
        '09003000001',
        CURRENT_TIMESTAMP, null);

# BIKER
INSERT INTO USER (city, country, street, zipcode, email, firstname, lastname, password, phone_number, created_on,
                      last_updated_on)
VALUES ('Tehran', 'Iran', 'Monirieh', '918376183', 'biker1@gmail.com', 'Ali', 'Saeedi',
        '$e0801$2u8TUqRLMOrXqk4XciUUEQBbj38mZsyg5D2KK+LrFRUroParRBqbOeGIdArLOLLQyPidM5eqCTOGr8MZ89vlNw==$JGtdnHOaTDOU39n2nj/C+t8ORS8Of3m90iL9swakWBs=',
        '09009000001',
        CURRENT_TIMESTAMP, null);
# BIKER
INSERT INTO USER (city, country, street, zipcode, email, firstname, lastname, password, phone_number, created_on,
                      last_updated_on)
VALUES ('Tehran', 'Iran', 'Monirieh', '918376185', 'biker2@gmail.com', 'Pejman', 'Asghari',
        '$e0801$2u8TUqRLMOrXqk4XciUUEQBbj38mZsyg5D2KK+LrFRUroParRBqbOeGIdArLOLLQyPidM5eqCTOGr8MZ89vlNw==$JGtdnHOaTDOU39n2nj/C+t8ORS8Of3m90iL9swakWBs=',
        '09009000002',
        CURRENT_TIMESTAMP, null);
# BIKER
INSERT INTO USER (city, country, street, zipcode, email, firstname, lastname, password, phone_number, created_on,
                      last_updated_on)
VALUES ('Tehran', 'Iran', 'Monirieh', '918376184', 'biker3@gmail.com', 'Saeed', 'Javadi',
        '$e0801$2u8TUqRLMOrXqk4XciUUEQBbj38mZsyg5D2KK+LrFRUroParRBqbOeGIdArLOLLQyPidM5eqCTOGr8MZ89vlNw==$JGtdnHOaTDOU39n2nj/C+t8ORS8Of3m90iL9swakWBs=',
        '09009000003',
        CURRENT_TIMESTAMP, null);
# END OF INSERTING CUSTOMERS

# START OF INSERTING CUSTOMER_ROLES
# INSERTING Customer_Roles
insert into user_roles (user_id, roles_id) VALUES (1, 1);
insert into user_roles (user_id, roles_id) VALUES (2, 2);
insert into user_roles (user_id, roles_id) VALUES (3, 3);
insert into user_roles (user_id, roles_id) VALUES (4, 3);
insert into user_roles (user_id, roles_id) VALUES (5, 4);
insert into user_roles (user_id, roles_id) VALUES (6, 4);
insert into user_roles (user_id, roles_id) VALUES (7, 4);
# END OF INSERTING CUSTOMER_ROLES

# START OF INSERTING BIKERS
# INSERTING SOME BIKERS INTO DB
INSERT INTO BIKER (created_on, last_updated_on, user_id)
VALUES (CURRENT_TIMESTAMP, null, 5);
INSERT INTO BIKER (created_on, last_updated_on, user_id)
VALUES (CURRENT_TIMESTAMP, null, 6);
INSERT INTO BIKER (created_on, last_updated_on, user_id)
VALUES (CURRENT_TIMESTAMP, null, 7);
# END OF INSERTING BIKERS

# START OF INSERTING DELIVERIES
# INSERTING SOME DELIVERIES INTO DB
INSERT INTO delivery (biker_id, deliveree_id, created_on)
VALUES (1, 3, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, deliveree_id, created_on)
VALUES (1, 3, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, deliveree_id, created_on)
VALUES (1, 3, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, deliveree_id, created_on)
VALUES (1, 4, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, deliveree_id, created_on)
VALUES (1, 4, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, deliveree_id, created_on)
VALUES (2, 3, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, deliveree_id, created_on)
VALUES (2, 3, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, deliveree_id, created_on)
VALUES (2, 3, CURRENT_TIMESTAMP);
INSERT INTO delivery (biker_id, deliveree_id, created_on)
VALUES (2, 3, CURRENT_TIMESTAMP);
INSERT INTO DELIVERY (biker_id, deliveree_id, created_on)
VALUES (2, 3, CURRENT_TIMESTAMP)
# END OF INSERTING DELIVERIES