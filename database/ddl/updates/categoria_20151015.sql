ALTER TABLE `categoria` 
ADD COLUMN `entered_date_time` DATETIME NULL COMMENT '' AFTER `imagen_grande_nombre`,
ADD COLUMN `entered_user_id` INT NULL COMMENT '' AFTER `entered_date_time`;