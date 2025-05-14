-- ========================================
-- CREACIÓN DE LA BASE DE DATOS
-- ========================================
DROP DATABASE IF EXISTS alquiler_coches_db;
CREATE DATABASE alquiler_coches_db;
USE alquiler_coches_db;

-- ========================================
-- TABLA: usuarios
-- ========================================
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL DEFAULT 'cliente'
);

-- ========================================
-- TABLA: clientes
-- ========================================
CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    FOREIGN KEY (email) REFERENCES usuarios(username)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ========================================
-- TABLA: coches (marca y modelo como texto)
-- ========================================
CREATE TABLE coches (
    id INT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    anio YEAR NOT NULL,
    precio DECIMAL(8,2) NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    caballos INT NOT NULL,
    cilindrada INT NOT NULL
);

-- ========================================
-- TABLA: alquileres (con fecha_fin incluida)
-- ========================================
CREATE TABLE alquileres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_coche INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (id_coche) REFERENCES coches(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- ========================================
-- DATOS DE PRUEBA
-- ========================================

-- Usuario de prueba
INSERT INTO usuarios (username, password, rol) VALUES
('juan.perez@example.com', '1234', 'cliente');

-- Cliente asociado
INSERT INTO clientes (nombre, apellido, email, telefono, password) VALUES
('Juan', 'Pérez', 'juan.perez@example.com', '600123456', '1234');

-- Coches de prueba
INSERT INTO coches (marca, modelo, anio, precio, disponible, caballos, cilindrada) VALUES
('Toyota', 'Corolla', 2020, 40.00, TRUE, 132, 1800),
('Mazda', 'RX-7', 1999, 70.00, TRUE, 276, 1300),
('Nissan', 'Skyline', 2002, 85.00, TRUE, 280, 2600);

-- Alquiler de prueba
INSERT INTO alquileres (id_cliente, id_coche, fecha_inicio, fecha_fin, total) VALUES
(1, 1, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 5 DAY), 200.00);
