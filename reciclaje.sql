-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-09-2021 a las 19:21:56
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
-- Estructura de tabla para la tabla `peticion`
--

CREATE TABLE `peticion` (
  `id_peticion` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `total_recibir` double NOT NULL,
  `fecha_peticion` datetime NOT NULL,
  `estado_peticion` int(11) NOT NULL COMMENT '1: activo, 0 : inactivo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `peticion`
--

INSERT INTO `peticion` (`id_peticion`, `id_usuario`, `total_recibir`, `fecha_peticion`, `estado_peticion`) VALUES
(11, 4, 0.5, '2021-09-08 10:53:52', 0),
(12, 4, 1.8, '2021-09-08 10:54:25', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reciclado`
--

CREATE TABLE `reciclado` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `material_id` int(11) NOT NULL,
  `id_recicladora` int(11) NOT NULL,
  `id_peticion` int(11) DEFAULT NULL,
  `descripcion_reciclaje` varchar(100) NOT NULL,
  `fecha_reciclaje` datetime NOT NULL,
  `peso_kilogramo` float NOT NULL,
  `precio_total` float NOT NULL,
  `estado` int(1) NOT NULL COMMENT '0: finalizado,1: activo, 2:espera\r\n'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `reciclado`
--

INSERT INTO `reciclado` (`id`, `usuario_id`, `material_id`, `id_recicladora`, `id_peticion`, `descripcion_reciclaje`, `fecha_reciclaje`, `peso_kilogramo`, `precio_total`, `estado`) VALUES
(19, 4, 3, 2, 11, 'Vidrio', '2021-09-08 10:53:38', 1, 0.5, 0),
(20, 4, 2, 2, 12, 'Plstico', '2021-09-08 10:54:20', 3, 1.8, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recicladora_sucursal`
--

CREATE TABLE `recicladora_sucursal` (
  `id_recicladora` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `coord_latitud` double NOT NULL,
  `coord_longitud` double NOT NULL,
  `estado_direccion` int(11) NOT NULL COMMENT '0: inactiva , 1: activa'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `recicladora_sucursal`
--

INSERT INTO `recicladora_sucursal` (`id_recicladora`, `id_usuario`, `coord_latitud`, `coord_longitud`, `estado_direccion`) VALUES
(1, 5, -2.1338375, -79.9411406, 1),
(2, 5, -2.1708307, -79.892172, 1),
(3, 5, -2.1810855, -79.89869829999999, 1),
(4, 5, -2.1810855, -79.89869829999999, 0),
(5, 5, -2.1409374, -79.9094386, 1),
(6, 5, -2.1952778, -79.88083329999999, 0),
(7, 11, -2.1388437, -79.9079418, 0),
(8, 11, -2.1693477000000003, -79.8985397, 1),
(9, 11, -2.1709275999999997, -79.90025589999999, 0),
(10, 11, -2.1431673, -79.8794521, 0);

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
  `rol` int(11) NOT NULL COMMENT '1: Admin , 2: Normal\r\n',
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
(3, 2, 'bryan', 'leon', '2197387', 'b', '$2y$10$NoVkD.W56sDPACPN2IEHgOccG8msOBCzsenVLhwrgoZchvh2oV4YO'),
(4, 2, 'Oscar', 'Chele', '0990822993', 'oscar@gmail.com', '$2y$10$gozOKhYwZOmUv3OOgXlaje6FT10EG2l7arEMM62OPQXlGKL/Q1RR2'),
(5, 1, 'Kevin', 'Velasquez', '0985632145', 'kevin@gmail.com', '$2y$10$WiU6zUfBonAfJUY8ckW9Uum9C3iZpiFjf4MIQk26b3JUdYi9foHcO'),
(11, 1, 'Sebastián', 'Coello', '0512312121', 'sebastian@gmail.com', '$2y$10$uX2utCEZEjWWKShuUfGN4.yXF6M.Kkfb6yH5AIBZ.dBxPAPfHZbte');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `material`
--
ALTER TABLE `material`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `peticion`
--
ALTER TABLE `peticion`
  ADD PRIMARY KEY (`id_peticion`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `reciclado`
--
ALTER TABLE `reciclado`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario_id` (`usuario_id`,`material_id`),
  ADD KEY `material_id` (`material_id`),
  ADD KEY `id_recicladora` (`id_recicladora`),
  ADD KEY `id_recicladora_2` (`id_recicladora`),
  ADD KEY `id_peticion` (`id_peticion`);

--
-- Indices de la tabla `recicladora_sucursal`
--
ALTER TABLE `recicladora_sucursal`
  ADD PRIMARY KEY (`id_recicladora`),
  ADD KEY `id_usuario` (`id_usuario`);

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
-- AUTO_INCREMENT de la tabla `peticion`
--
ALTER TABLE `peticion`
  MODIFY `id_peticion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `reciclado`
--
ALTER TABLE `reciclado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `recicladora_sucursal`
--
ALTER TABLE `recicladora_sucursal`
  MODIFY `id_recicladora` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `peticion`
--
ALTER TABLE `peticion`
  ADD CONSTRAINT `peticion_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `reciclado`
--
ALTER TABLE `reciclado`
  ADD CONSTRAINT `reciclado_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `reciclado_ibfk_2` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `reciclado_ibfk_3` FOREIGN KEY (`id_recicladora`) REFERENCES `recicladora_sucursal` (`id_recicladora`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reciclado_ibfk_4` FOREIGN KEY (`id_peticion`) REFERENCES `peticion` (`id_peticion`);

--
-- Filtros para la tabla `recicladora_sucursal`
--
ALTER TABLE `recicladora_sucursal`
  ADD CONSTRAINT `recicladora_sucursal_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`rol`) REFERENCES `rol` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
