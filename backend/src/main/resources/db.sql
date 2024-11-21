CREATE TABLE departments (
    id BigInt PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(55) NOT NULL
);

CREATE TABLE employees (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
full_name VARCHAR(55) NOT NULL,
salary DECIMAL(15, 2),
gender BOOLEAN,
phone_number VARCHAR(10) NOT NULL,
birthDay DATE,
avatar VARCHAR(300) DEFAULT 'https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2F6596121.png?alt=media&token=866d2e85-7699-4d1c-9f45-aa60b172969c',
department_id BIGINT DEFAULT 0,
CONSTRAINT chk_phone_number CHECK (phone_number REGEXP '^[0-9]{10}$')
);


INSERT INTO employees (id, full_name, salary, gender, phone_number, birthDay, avatar, department_id) VALUES
(1, 'Hồ Trung', 10000000.00, TRUE, '0763514492', '2004-05-18', 'https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Fz5900740189484_9bf3aefe9a2c1c7c2cc129b4cdc680bd.jpg?alt=media&token=c85d74dd-b7b5-480f-9f4c-272f1a46259a', 1),
(2, 'Dạ Thảo', 12000000.00, FALSE, '0763514402', '2005-12-04', 'https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Flogo.jpg?alt=media&token=d9f9af08-4e6c-4a31-96bf-d12e8fe6486a', 7),
(3, 'Ko Pin Yi', 1000000000.00, TRUE, '0763514497', '1989-05-31', 'https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Fimages.jpg?alt=media&token=c7f6ccdb-6f26-46bc-89dd-0fa14e1ec136', 4),
(4, 'Fedor Gorst', 2000000000.00, TRUE, '0763514487', '2000-05-31', 'https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Fimages%20(1).jpg?alt=media&token=842f5852-3a7b-4865-b28c-a26d20f7c0dd', 4),
(5, 'Francisco Sánchez Ruíz', 1000000000.00, TRUE, '0763514387', '1991-12-29', 'https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Fbil-FranciscoSanchezRuiz-2-0502.jpg?alt=media&token=4d18d762-f31d-4433-b5a3-61804c978ccd', 4),
(6, 'Trương Mỹ Lan', 2000000000.00, FALSE, '0763514587', '1956-10-13', 'https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/sqc-academy%2Favataa.png?alt=media&token=751b2c78-709f-4631-9a46-81cbca824691', 6);


INSERT INTO departments (name) VALUES
('CEO'),
('Làm thinh'),
('Làm ông nội'),
('CƠ THỦ CHUYÊN NGHIỆP'),
('Ăn xin'),
('Admin Juventus (Đi tù)');




CREATE TABLE market_places (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100) NOT NULL,
address VARCHAR(100),
type_id bigint,
area double(20 ,0),
rent_price Decimal(15 , 2),
start_date Date,
CONSTRAINT fk_type FOREIGN KEY (type_id) REFERENCES type_market_places(id) ON DELETE CASCADE
);

CREATE TABLE type_market_places (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(45) NOT NULL
);


INSERT INTO type_market_places (name) VALUES
('Cho thuê'),
('Cho luôn'),
('Căn hộ'),
('Văn phòng'),
('Nhà ở');

INSERT INTO market_places (name, address, type_id, area, rent_price, start_date)
VALUES
    ('Quán cắt tóc Vũ', '123 Main St', 1, 5000, 10000000.50, '2023-01-15'),
    ('Green Mart', '44 Elm St', 2, 3000, 10000000, '2022-11-10'),
    ('Blue Mart', '156 Main St', 1, 3000, 10000000.75, '2022-11-10'),
    ('Trung Company', '15 Wale St', 4, 3000, 70000000.75, '2022-11-10'),
    ('Green Hotle', '111 Main St', 3, 3000, 45600000.75, '2022-11-10');










