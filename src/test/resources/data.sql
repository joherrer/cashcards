TRUNCATE TABLE cash_cards, users RESTART IDENTITY CASCADE;

INSERT INTO users(id, username, password, role) VALUES (1, 'sarah1', '$2y$10$4FSutKJ6DIfu4D1WYK2l.ub41oUuFHvPyE6UoaKD.SW.DTNEwrdxC', 'AUTHORISED');
INSERT INTO users(id, username, password, role) VALUES (2, 'hank3', '$2y$10$yfL84UyOxES.0C9vDzYuD.e8iOiwYEkUTfw2hl/M.h6b6Wq/E9Jmu', 'UNAUTHORISED');
INSERT INTO users(id, username, password, role) VALUES (3, 'kumar2', '$2y$10$T9lqb8jb0SoW86oFFX59SOZFFFi4uDx5KiWs0KHpwObDF8qfAb9He', 'AUTHORISED');

INSERT INTO cash_cards(id, amount, owner) VALUES (99, 123.45, 'sarah1');
INSERT INTO cash_cards(id, amount, owner) VALUES (100, 1.00, 'sarah1');
INSERT INTO cash_cards(id, amount, owner) VALUES (101, 150.00, 'sarah1');
INSERT INTO cash_cards(id, amount, owner) VALUES (102, 200.00, 'kumar2');

ALTER SEQUENCE users_id_seq RESTART WITH 4;
ALTER SEQUENCE cash_cards_id_seq RESTART WITH 103;
