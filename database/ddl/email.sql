CREATE TABLE `email` (
  `id_email` varchar(100) NOT NULL,
  `smtp_host` varchar(100) DEFAULT NULL,
  `smtp_port` varchar(100) DEFAULT NULL,
  `smtp_auth` varchar(100) DEFAULT NULL,
  `smtp_starttls` varchar(100) DEFAULT NULL,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `method` varchar(50) DEFAULT NULL COMMENT 'SSL or TLS',
  `from` varchar(100) DEFAULT NULL,
  `subject` varchar(200) DEFAULT NULL,
  `bcc` varchar(200) DEFAULT NULL,
  `cc` varchar(100) DEFAULT NULL,
  `template` varchar(50) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id_email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;