CREATE TABLE `categoria` (
  `id_categoria` varchar(100) NOT NULL,
  `nombre_corto` varchar(100) NOT NULL,
  `nombre_largo` text,
  `descripcion_gral` text,
  `descripcion_tec` text,
  `descripcion_com` text,
  `imagen_chica` blob,
  `imagen_grande` blob,
  `id_categoria_sup` varchar(100) DEFAULT NULL,
  `orden` int(11) DEFAULT NULL,
  `imagen_chica_nombre` varchar(200) DEFAULT NULL,
  `imagen_grande_nombre` varchar(200) DEFAULT NULL,
  `entered_date_time` datetime DEFAULT NULL,
  `entered_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Categorías de productos';