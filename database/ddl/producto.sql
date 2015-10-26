CREATE TABLE `producto` (
  `codigo` varchar(100) NOT NULL,
  `id_categoria` varchar(100) DEFAULT NULL,
  `descripcion` varchar(300) DEFAULT NULL,
  `unidad_medida` varchar(100) DEFAULT NULL,
  `unidad_peso` varchar(100) DEFAULT NULL,
  `grupo` varchar(100) DEFAULT NULL,
  `peso_bruto` varchar(100) DEFAULT NULL,
  `sector` varchar(100) DEFAULT NULL,
  `numero_europeo` varchar(100) DEFAULT NULL,
  `entered_date_time` datetime DEFAULT NULL,
  `entered_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_id_categoria_idx` (`id_categoria`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;