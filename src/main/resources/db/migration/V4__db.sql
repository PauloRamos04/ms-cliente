-- V4__db.sql

-- Drop the existing tables if they exist (in case they are not dropped previously)
DROP TABLE IF EXISTS login_history CASCADE;
DROP TABLE IF EXISTS patient CASCADE;
DROP TABLE IF EXISTS health_professional CASCADE; -- Renomeando para singular
DROP TABLE IF EXISTS address CASCADE; -- Renomeando para singular
DROP TABLE IF EXISTS child CASCADE; -- Renomeando para singular
DROP TABLE IF EXISTS deficiency CASCADE; -- Renomeando para singular
DROP TABLE IF EXISTS sus_document CASCADE; -- Renomeando para singular
DROP TABLE IF EXISTS health_unit CASCADE; -- Renomeando para singular

-- Create the health_professional table
CREATE TABLE health_professional (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(50) NOT NULL,
                                     password VARCHAR(20) NOT NULL,
                                     cpf VARCHAR(11) NOT NULL UNIQUE,
                                     email VARCHAR(100) NOT NULL UNIQUE,
                                     health_unit_number VARCHAR(50) NOT NULL,
                                     phone VARCHAR(15),
                                     address VARCHAR(255) NOT NULL,
                                     birth_date DATE NOT NULL,
                                     gender SMALLINT NOT NULL, -- Alterado para SMALLINT
                                     professional_type SMALLINT NOT NULL, -- Alterado para SMALLINT
                                     crm VARCHAR(20),
                                     coren VARCHAR(20)
);

-- Create the patient table
CREATE TABLE patient (
                         id SERIAL PRIMARY KEY,
                         first_name VARCHAR(50) NOT NULL,
                         last_name VARCHAR(50) NOT NULL,
                         cpf VARCHAR(11) NOT NULL UNIQUE,
                         email VARCHAR(100) UNIQUE,
                         phone VARCHAR(15),
                         address VARCHAR(255) NOT NULL,
                         gender SMALLINT NOT NULL, -- Alterado para SMALLINT
                         birth_date DATE NOT NULL,
                         deficiency VARCHAR(10),
                         photo VARCHAR(255),
                         password VARCHAR(20) NOT NULL
);

-- Create the login_history table
CREATE TABLE login_history (
                               id SERIAL PRIMARY KEY,
                               login_time TIMESTAMP NOT NULL,
                               health_professional_id INTEGER NOT NULL REFERENCES health_professional(id)
);

-- Create the address table
CREATE TABLE address (
                         id SERIAL PRIMARY KEY,
                         street VARCHAR(100) NOT NULL,
                         city VARCHAR(50) NOT NULL,
                         state VARCHAR(50) NOT NULL,
                         zip_code VARCHAR(10) NOT NULL,
                         patient_id INTEGER NOT NULL REFERENCES patient(id)
);

-- Create the child table
CREATE TABLE child (
                       id SERIAL PRIMARY KEY,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       birth_date DATE NOT NULL,
                       gender SMALLINT NOT NULL, -- Alterado para SMALLINT
                       parent_id INTEGER NOT NULL REFERENCES patient(id)
);

-- Create the sus_document table
CREATE TABLE sus_document (
                              id SERIAL PRIMARY KEY,
                              document_type VARCHAR(50) NOT NULL,
                              document_number VARCHAR(50) NOT NULL UNIQUE,
                              issue_date DATE NOT NULL,
                              expiration_date DATE,
                              patient_id INTEGER NOT NULL REFERENCES patient(id)
);

-- Create the health_unit table
CREATE TABLE health_unit (
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
