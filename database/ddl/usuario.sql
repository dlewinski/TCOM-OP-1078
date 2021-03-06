CREATE TABLE `usuario` (
  `usuario_id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(250) NOT NULL,
  `clave` varchar(250) NOT NULL,
  `nombre` varchar(250) DEFAULT NULL,
  `apellido` varchar(250) DEFAULT NULL,
  `entered_date_time` datetime NOT NULL,
  `entered_user_id` int(11) NOT NULL,
  `salt` char(60) NOT NULL,
  `perfil_id` int(11) DEFAULT NULL,
  `telefono` varchar(250) DEFAULT NULL,
  `email` varchar(250) DEFAULT NULL,
  `nro_vendedor_sap` varchar(250) DEFAULT NULL,
  `habilitado` varchar(250) DEFAULT NULL,
  `code` varchar(250) DEFAULT NULL,
  `reset_password` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`usuario_id`),
  UNIQUE KEY `nombre_usuario_UNIQUE` (`nombre_usuario`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_usuario_usuario1_idx` (`entered_user_id`),
  KEY `fk_usuario_perfil_idx` (`perfil_id`),
  CONSTRAINT `fk_usuario_perfil` FOREIGN KEY (`perfil_id`) REFERENCES `perfil` (`perfil_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_usuario1` FOREIGN KEY (`entered_user_id`) REFERENCES `usuario` (`usuario_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;