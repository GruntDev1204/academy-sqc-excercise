CREATE TABLE employees
(
    id            BIGINT PRIMARY KEY auto_increment,
    full_name     VARCHAR(50) NOT NULL,
    phone_number  VARCHAR(20),
    salary        DECIMAL(15, 2),
    department_id BIGINT,
    birthday      DATE,
    gender        VARCHAR(10),
    avatar        VARCHAR(500) DEFAULT NULL
);

ALTER TABLE employees
    ADD CONSTRAINT FK_EMPLOYEES_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES departments (id);