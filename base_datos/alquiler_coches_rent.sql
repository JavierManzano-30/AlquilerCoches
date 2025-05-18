
-- BLOQUE 1: Crear base de datos y tablas
DROP DATABASE IF EXISTS alquiler_coches_rent;
CREATE DATABASE alquiler_coches_rent;
USE alquiler_coches_rent;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    dni VARCHAR(15) NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE marca (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE modelo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    id_marca INT NOT NULL,
    UNIQUE (id_marca, nombre),
    FOREIGN KEY (id_marca) REFERENCES marca(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE coches (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_modelo INT NOT NULL,
    anio YEAR NOT NULL,
    precio_dia DECIMAL(8,2) NOT NULL,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    caballos INT NOT NULL,
    cilindrada INT NOT NULL,
    transmision ENUM('manual', 'automatico', 'semiautomatico') NOT NULL DEFAULT 'manual',
    FOREIGN KEY (id_modelo) REFERENCES modelo(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE alquileres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_coche INT NOT NULL,
    fecha_inicio DATE NOT NULL,
    dias INT NOT NULL CHECK (dias > 0),
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (id_coche) REFERENCES coches(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- BLOQUE 2: Insertar usuarios y clientes
INSERT INTO usuarios (username, password, rol) VALUES
('cliente1@example.com', '1234', 'cliente'),
('admin@example.com', 'adminpass', 'admin');

INSERT INTO clientes (nombre, apellido, telefono, email, dni, password) VALUES
('Juan', 'Pérez', '600123456', 'cliente1@example.com', '12345678Z', '1234');

-- BLOQUE 3: Insertar marcas y modelos
INSERT INTO marca (nombre) VALUES 
('Toyota'), ('Mazda'), ('Nissan'), ('Honda');

INSERT INTO modelo (nombre, id_marca) VALUES
('Supra', 1),
('RX-7', 2),
('Skyline', 3),
('NSX', 4);

-- BLOQUE 4: Insertar coches
INSERT INTO coches (id_modelo, anio, precio_dia, disponible, caballos, cilindrada, transmision) VALUES
(1, 1998, 85.00, TRUE, 340, 3000, 'manual'),
(2, 1999, 70.00, TRUE, 276, 3000, 'manual'),
(3, 2002, 90.00, TRUE, 280, 2600, 'automatico'),
(4, 2001, 75.00, TRUE, 300, 2600, 'automatico');

-- BLOQUE 5: Insertar alquileres
INSERT INTO alquileres (id_cliente, id_coche, fecha_inicio, dias, total) VALUES
(1, 1, CURDATE(), 3, 255.00);
