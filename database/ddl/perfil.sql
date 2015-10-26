CREATE TABLE `perfil` (
  `perfil_id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(250) NOT NULL,
  `entered_date_time` datetime NOT NULL,
  `entered_user_id` int(11) NOT NULL,
  PRIMARY KEY (`perfil_id`),
  KEY `fk_perfil_usuario_idx` (`entered_user_id`),
  CONSTRAINT `fk_perfil_usuario` FOREIGN KEY (`entered_user_id`) REFERENCES `usuario` (`usuario_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;