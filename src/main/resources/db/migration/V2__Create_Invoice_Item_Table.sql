CREATE TABLE INVOICE_ITEM (
  id SERIAL NOT NULL PRIMARY KEY ,
  cost NUMERIC(15,2) NOT NULL,
  count INTEGER NOT NULL,
  amount NUMERIC(15,2) NOT NULL,
  name varchar(10) NOT NULL,
  order_id integer
);


