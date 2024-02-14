DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS rentals;
DROP TABLE IF EXISTS rental_car;

CREATE TABLE cars (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    make VARCHAR(30) NOT NULL,
    model VARCHAR(30) NOT NULL,
    production_year INT NOT NULL,
    status VARCHAR(30) NOT NULL,
    price_per_day DOUBLE NOT NULL
);

CREATE TABLE clients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    tel INT NOT NULL
);

CREATE TABLE employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    tel INT NOT NULL
);

CREATE TABLE rentals (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    rental_date DATETIME NOT NULL,
    return_date DATETIME NULL,
    rental_for INT NOT NULL,
    client_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

CREATE TABLE rental_car (
    car_id BIGINT,
    rental_id BIGINT,
    PRIMARY KEY (car_id, rental_id),
    FOREIGN KEY (car_id) REFERENCES cars(id),
    FOREIGN KEY (rental_id) REFERENCES rentals(id)
);
































