-- V3__db.sql

-- Update the gender column values in health_professionals
UPDATE health_professionals SET gender = '0' WHERE gender = 'masculino';
UPDATE health_professionals SET gender = '1' WHERE gender = 'feminino';
UPDATE health_professionals SET gender = '2' WHERE gender = 'outro';

-- Alter the gender column type to smallint in health_professionals
ALTER TABLE health_professionals
    ALTER COLUMN gender SET DATA TYPE smallint USING gender::smallint;

-- Update the gender column values in patients
UPDATE patients SET gender = '0' WHERE gender = 'masculino';
UPDATE patients SET gender = '1' WHERE gender = 'feminino';
UPDATE patients SET gender = '2' WHERE gender = 'outro';

-- Alter the gender column type to smallint in patients
ALTER TABLE patients
    ALTER COLUMN gender SET DATA TYPE smallint USING gender::smallint;

-- Update the gender column values in children
UPDATE children SET gender = '0' WHERE gender = 'masculino';
UPDATE children SET gender = '1' WHERE gender = 'feminino';
UPDATE children SET gender = '2' WHERE gender = 'outro';

-- Alter the gender column type to smallint in children
ALTER TABLE children
    ALTER COLUMN gender SET DATA TYPE smallint USING gender::smallint;
