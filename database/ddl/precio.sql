CREATE TABLE `precio` (
  `codigo_producto` varchar(100) NOT NULL,
  `organizacion_venta` varchar(100) DEFAULT NULL,
  `canal_distribucion` varchar(100) DEFAULT NULL,
  `condicion_importe` varchar(100) DEFAULT NULL,
  `condicion_unidad` varchar(100) DEFAULT NULL,
  `condicion_unidad_medida` varchar(100) DEFAULT NULL,
  `condicion_inicio_validez` varchar(100) DEFAULT NULL,
  `condicion_fin_validez` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`codigo_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;