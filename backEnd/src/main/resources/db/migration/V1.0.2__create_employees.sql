CREATE TABLE employees
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    salary        DECIMAL(15, 2)        NULL,
    full_name     VARCHAR(50)           NULL,
    gender        BIT(1)                NULL,
    birthday      date                  NULL,
    phone_number  VARCHAR(20)           NULL,
    avatar        VARCHAR(500)          NULL,
    department_id BIGINT                NULL,
    CONSTRAINT pk_employees PRIMARY KEY (id)
);

ALTER TABLE employees
    ADD CONSTRAINT FK_EMPLOYEES_ON_DEPARTMENT FOREIGN KEY (department_id) REFERENCES departments (id);