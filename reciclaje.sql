-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-08-2021 a las 07:11:43
-- Versión del servidor: 10.4.14-MariaDB
-- Versión de PHP: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `reciclaje`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `material`
--

CREATE TABLE `material` (
  `id` int(11) NOT NULL,
  `tipo` varchar(60) NOT NULL,
  `precio_x_kilo` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `material`
--

INSERT INTO `material` (`id`, `tipo`, `precio_x_kilo`) VALUES
(1, 'papel', 0.22),
(2, 'plastico', 0.6),
(3, 'vidrio', 0.5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reciclado`
--

CREATE TABLE `reciclado` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `material_id` int(11) NOT NULL,
  `fecha_reciclaje` date NOT NULL,
  `peso_kilogramo` float NOT NULL,
  `precio_total` float NOT NULL,
  `estado` int(1) NOT NULL COMMENT '1: activo\r\n2: inactivo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `reciclado`
--

INSERT INTO `reciclado` (`id`, `usuario_id`, `material_id`, `fecha_reciclaje`, `peso_kilogramo`, `precio_total`, `estado`) VALUES
(1, 1, 2, '2021-08-24', 30, 201, 1),
(2, 1, 2, '2021-08-25', 0.6, 30, 1),
(3, 1, 2, '2021-08-25', 0.6, 30, 1),
(4, 2, 1, '2021-08-25', 0.22, 11, 1),
(5, 2, 1, '2021-08-25', 0.22, 132, 1),
(6, 2, 3, '2021-08-25', 55, 27.5, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id` int(11) NOT NULL,
  `rol` varchar(55) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id`, `rol`) VALUES
(1, 'Admin'),
(2, 'Reciclador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `rol` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `correo` varchar(60) NOT NULL,
  `contrasena` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `rol`, `nombre`, `apellido`, `telefono`, `correo`, `contrasena`) VALUES
(1, 2, 'bryan', 'leon', '2197387', 'ssa', '$2y$10$IivOaIoD5M1Eb3bceWNGN.zdPBVuN.OOBzXkRK7nyQGQt4NOy5uLK'),
(2, 2, 'Adrian', 'Leon', '2197387', 's', '$2y$10$.Ihe/5GK0SYOqt8lZm3VlO1aegJ2ZHwhmGQPLUd3SXU9lSQEg5b2O'),
(3, 2, 'bryan', 'leon', '2197387', 'b', '$2y$10$NoVkD.W56sDPACPN2IEHgOccG8msOBCzsenVLhwrgoZchvh2oV4YO');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `material`
--
ALTER TABLE `material`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `reciclado`
--
ALTER TABLE `reciclado`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario_id` (`usuario_id`,`material_id`),
  ADD KEY `material_id` (`material_id`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `rol` (`rol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `material`
--
ALTER TABLE `material`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `reciclado`
--
ALTER TABLE `reciclado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `reciclado`
--
ALTER TABLE `reciclado`
  ADD CONSTRAINT `reciclado_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `reciclado_ibfk_2` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`rol`) REFERENCES `rol` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
