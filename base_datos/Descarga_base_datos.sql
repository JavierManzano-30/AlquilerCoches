-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.40 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para alquiler_coches_rent
CREATE DATABASE IF NOT EXISTS `alquiler_coches_rent` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `alquiler_coches_rent`;

-- Volcando estructura para tabla alquiler_coches_rent.alquileres
CREATE TABLE IF NOT EXISTS `alquileres` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `id_coche` int NOT NULL,
  `fecha_inicio` date NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `fecha_fin` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_cliente` (`id_cliente`),
  KEY `id_coche` (`id_coche`),
  CONSTRAINT `alquileres_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `alquileres_ibfk_2` FOREIGN KEY (`id_coche`) REFERENCES `coches` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla alquiler_coches_rent.alquileres: ~0 rows (aproximadamente)
DELETE FROM `alquileres`;

-- Volcando estructura para tabla alquiler_coches_rent.clientes
CREATE TABLE IF NOT EXISTS `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `dni` varchar(15) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla alquiler_coches_rent.clientes: ~4 rows (aproximadamente)
DELETE FROM `clientes`;
INSERT INTO `clientes` (`id`, `nombre`, `apellido`, `telefono`, `email`, `dni`, `password`) VALUES
	(1, 'Juan', 'Pérez', '600123456', 'cliente1@example.com', '12345678Z', '1234'),
	(2, 'Javier', 'Manzano', '640959215', 'javier@gmail.com', '30279574M', '123456'),
	(3, 'Victor', 'Ridao', '652660274', 'victor@gmail.com', '30263103W', '123456'),
	(4, 'Antonio', 'Ortega', '666666666', 'antonio@gmail.com', '30266794J', '123456');

-- Volcando estructura para tabla alquiler_coches_rent.coches
CREATE TABLE IF NOT EXISTS `coches` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_modelo` int NOT NULL,
  `anio` year NOT NULL,
  `precio_dia` decimal(8,2) NOT NULL,
  `disponible` tinyint(1) NOT NULL DEFAULT '1',
  `caballos` int NOT NULL,
  `cilindrada` int NOT NULL,
  `transmision` enum('manual','automatico','semiautomatico') NOT NULL DEFAULT 'manual',
  PRIMARY KEY (`id`),
  KEY `id_modelo` (`id_modelo`),
  CONSTRAINT `coches_ibfk_1` FOREIGN KEY (`id_modelo`) REFERENCES `modelo` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla alquiler_coches_rent.coches: ~4 rows (aproximadamente)
DELETE FROM `coches`;
INSERT INTO `coches` (`id`, `id_modelo`, `anio`, `precio_dia`, `disponible`, `caballos`, `cilindrada`, `transmision`) VALUES
	(1, 1, '1998', 85.00, 1, 340, 3000, 'manual'),
	(2, 2, '1999', 70.00, 1, 276, 3000, 'manual'),
	(3, 3, '2002', 90.00, 1, 280, 2600, 'automatico'),
	(4, 4, '2001', 75.00, 1, 300, 2600, 'automatico');

-- Volcando estructura para tabla alquiler_coches_rent.marca
CREATE TABLE IF NOT EXISTS `marca` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla alquiler_coches_rent.marca: ~4 rows (aproximadamente)
DELETE FROM `marca`;
INSERT INTO `marca` (`id`, `nombre`) VALUES
	(4, 'Honda'),
	(2, 'Mazda'),
	(3, 'Nissan'),
	(1, 'Toyota');

-- Volcando estructura para tabla alquiler_coches_rent.modelo
CREATE TABLE IF NOT EXISTS `modelo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `id_marca` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_marca` (`id_marca`,`nombre`),
  CONSTRAINT `modelo_ibfk_1` FOREIGN KEY (`id_marca`) REFERENCES `marca` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla alquiler_coches_rent.modelo: ~4 rows (aproximadamente)
DELETE FROM `modelo`;
INSERT INTO `modelo` (`id`, `nombre`, `id_marca`) VALUES
	(1, 'Supra', 1),
	(2, 'RX-7', 2),
	(3, 'Skyline', 3),
	(4, 'NSX', 4);

-- Volcando estructura para tabla alquiler_coches_rent.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `rol` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla alquiler_coches_rent.usuarios: ~5 rows (aproximadamente)
DELETE FROM `usuarios`;
INSERT INTO `usuarios` (`id`, `username`, `password`, `rol`) VALUES
	(1, 'cliente1@example.com', '1234', 'cliente'),
	(2, 'admin@example.com', 'adminpass', 'admin'),
	(3, 'javier@gmail.com', '123456', 'cliente'),
	(4, 'victor@gmail.com', '123456', 'cliente'),
	(5, 'antonio@gmail.com', '123456', 'cliente');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
