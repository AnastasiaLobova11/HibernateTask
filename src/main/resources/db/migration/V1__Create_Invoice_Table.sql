CREATE TABLE INVOICE
(
    order_id      SERIAL        NOT NULL PRIMARY KEY ,
    date_of_create timestamp      NOT NULL,
    date_of_change timestamp,
    client_id      INTEGER        NOT NULL,
    amount         NUMERIC(15, 2) NOT NULL,
    status         varchar(10)    NOT NULL

);


