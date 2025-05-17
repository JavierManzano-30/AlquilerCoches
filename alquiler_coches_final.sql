
-- BASE DE DATOS FINAL: alquiler_coches_final
DROP DATABASE IF EXISTS alquiler_coches_final;
CREATE DATABASE alquiler_coches_final;
USE alquiler_coches_final;

-- Tabla de usuarios (por si hay admins, se puede ampliar)
CREATE TABLE usuarios (
    email VARCHAR(100) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    rol ENUM('cliente', 'admin') DEFAULT 'cliente'
);

-- Tabla de clientes (relacionado a usuarios)
CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    FOREIGN KEY (email) REFERENCES usuarios(email)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Tabla de marcas
CREATE TABLE marca (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla de modelos
CREATE TABLE modelo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    id_marca INT NOT NULL,
    UNIQUE (id_marca, nombre),
    FOREIGN KEY (id_marca) REFERENCES marca(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Tabla de coches
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


-- Tabla de alquileres
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

-- Datos de prueba

-- Usuarios
INSERT INTO usuarios (email, password, rol) VALUES
('cliente1@example.com', '1234', 'cliente'),
('admin@example.com', 'adminpass', 'admin');

-- Clientes
INSERT INTO clientes (nombre, apellido, telefono, email) VALUES
('Juan', 'Pérez', '600123456', 'cliente1@example.com');

-- Marcas
INSERT INTO marca (nombre) VALUES ('Toyota'), ('Mazda'), ('Nissan');

-- Modelos
INSERT INTO modelo (nombre, id_marca) VALUES
('Supra', 1),
('RX-7', 2),
('Skyline', 3);

-- Coches
INSERT INTO coches (id_modelo, anio, precio_dia, disponible, caballos, cilindrada) VALUES
(1, 2020, 85.00, TRUE, 340, 3000),
(2, 1999, 70.00, TRUE, 276, 1300),
(3, 2002, 90.00, TRUE, 280, 2600);

-- Alquileres
INSERT INTO alquileres (id_cliente, id_coche, fecha_inicio, dias, total) VALUES
(1, 1, CURDATE(), 3, 255.00);
