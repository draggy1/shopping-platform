CREATE TABLE IF NOT EXISTS products
(
    id     UUID PRIMARY KEY,
    amount INT            NOT NULL,
    price  NUMERIC(12, 2) NOT NULL
);

INSERT INTO products (id, amount, price)
VALUES ('86b4efdd-5b10-4bfc-a5d8-83f59f8cd745', 1, 10.00),
       ('efa591fe-1805-46d6-8cbc-5ba0567ba834', 2, 1.0),
       ('90f850e9-496c-429d-9d70-36bb1951d678', 10, 100.0);
