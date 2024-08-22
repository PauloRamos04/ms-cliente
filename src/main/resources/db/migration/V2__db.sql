CREATE TABLE health_units (
                              id SERIAL PRIMARY KEY,
                              name_unit VARCHAR(100) NOT NULL,
                              unit_number VARCHAR(50),
                              cnpj VARCHAR(18),
                              password VARCHAR(20),
                              email VARCHAR(100),
                              UNIQUE (cnpj),
                              UNIQUE (unit_number),
                              UNIQUE (email)
);
