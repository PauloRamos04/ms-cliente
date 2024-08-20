-- V1__db.sql

-- Drop the existing tables if they exist
DROP TABLE IF EXISTS login_history CASCADE;
DROP TABLE IF EXISTS patients CASCADE;
DROP TABLE IF EXISTS health_professionals CASCADE;
DROP TABLE IF EXISTS addresses CASCADE;
DROP TABLE IF EXISTS children CASCADE;
DROP TABLE IF EXISTS deficiencies CASCADE;
DROP TABLE IF EXISTS sus_documents CASCADE;

-- Create the health_professionals table
CREATE TABLE health_professionals (
                                      id SERIAL PRIMARY KEY,
                                      name VARCHAR(50) NOT NULL,
                                      password VARCHAR(20) NOT NULL,
                                      cpf VARCHAR(11) NOT NULL UNIQUE,
                                      email VARCHAR(100) NOT NULL UNIQUE,
                                      health_unit_number VARCHAR(50) NOT NULL,
                                      phone VARCHAR(15),
                                      address VARCHAR(255) NOT NULL,
                                      birth_date DATE NOT NULL,
                                      gender VARCHAR(6) NOT NULL,
                                      professional_type VARCHAR(255) NOT NULL,
                                      crm VARCHAR(20),
                                      coren VARCHAR(20)
);

-- Create the patients table
CREATE TABLE patients (
                          id SERIAL PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          cpf VARCHAR(11) NOT NULL UNIQUE,
                          email VARCHAR(100) UNIQUE,
                          phone VARCHAR(15),
                          address VARCHAR(255) NOT NULL,
                          gender VARCHAR(6) NOT NULL,
                          birth_date DATE NOT NULL,
                          deficiency VARCHAR(10),
                          photo VARCHAR(255),
                          password VARCHAR(20) NOT NULL
);

-- Create the login_history table
CREATE TABLE login_history (
                               id SERIAL PRIMARY KEY,
                               login_time TIMESTAMP NOT NULL,
                               health_professional_id INTEGER NOT NULL REFERENCES health_professionals(id)
);

-- Create the addresses table
CREATE TABLE addresses (
                           id SERIAL PRIMARY KEY,
                           street VARCHAR(100) NOT NULL,
                           city VARCHAR(50) NOT NULL,
                           state VARCHAR(50) NOT NULL,
                           zip_code VARCHAR(10) NOT NULL,
                           patient_id INTEGER NOT NULL REFERENCES patients(id)
);

-- Create the children table
CREATE TABLE children (
                          id SERIAL PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          birth_date DATE NOT NULL,
                          gender VARCHAR(6) NOT NULL,
                          parent_id INTEGER NOT NULL REFERENCES patients(id)
);

-- Create the sus_documents table
CREATE TABLE sus_documents (
                               id SERIAL PRIMARY KEY,
                               document_type VARCHAR(50) NOT NULL,
                               document_number VARCHAR(50) NOT NULL UNIQUE,
                               issue_date DATE NOT NULL,
                               expiration_date DATE,
                               patient_id INTEGER NOT NULL REFERENCES patients(id)
);
