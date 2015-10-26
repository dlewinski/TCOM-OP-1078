ALTER TABLE `centro_almacen` 
ADD COLUMN `entered_date_time` DATETIME NULL COMMENT '' AFTER `almacen_deno`,
ADD COLUMN `entered_user_id` INT NULL COMMENT '' AFTER `entered_date_time`;