CREATE TABLE `centro_almacen` (
  `id_centro_almacen` int(11) NOT NULL,
  `organizacion_venta` varchar(300) DEFAULT NULL,
  `organizacion_venta_deno` varchar(300) DEFAULT NULL,
  `centro` varchar(300) DEFAULT NULL,
  `nombre` varchar(300) DEFAULT NULL,
  `almacen` varchar(300) DEFAULT NULL,
  `almacen_deno` varchar(300) DEFAULT NULL,
  `entered_date_time` datetime DEFAULT NULL,
  `entered_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_centro_almacen`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;