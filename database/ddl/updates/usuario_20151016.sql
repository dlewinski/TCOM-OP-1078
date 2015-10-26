ALTER TABLE `usuario` 
ADD COLUMN `habilitado` VARCHAR(250) NULL COMMENT '' AFTER `nro_vendedor_sap`;

ALTER TABLE `usuario` 
ADD UNIQUE INDEX `email_UNIQUE` (`email` ASC)  COMMENT '';