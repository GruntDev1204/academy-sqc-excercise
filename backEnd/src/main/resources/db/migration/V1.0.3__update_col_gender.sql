UPDATE employees
SET gender = CASE
                 WHEN gender = TRUE THEN 'Male'
                 WHEN gender = FALSE THEN 'Female'
                 ELSE gender
    END;


ALTER TABLE employees
    MODIFY COLUMN gender VARCHAR(10) NULL ,
    ADD CONSTRAINT check_gender CHECK (gender IN ('Male', 'Female'));
