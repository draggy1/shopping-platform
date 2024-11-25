CREATE TABLE IF NOT EXISTS products
(
    id     UUID PRIMARY KEY,
    amount INT            NOT NULL,
    price  NUMERIC(12, 2) NOT NULL
);

INSERT INTO products (id, amount, price)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 100, 10.00),
       ('660f8411-e29c-42e5-b717-556655441111', 200, 20.50),
       ('770e8422-e29d-43f6-c718-666655442222', 300, 30.75);