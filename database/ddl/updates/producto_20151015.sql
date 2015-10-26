ALTER TABLE `producto` 
ADD COLUMN `entered_date_time` DATETIME NULL COMMENT '' AFTER `numero_europeo`,
ADD COLUMN `entered_user_id` INT NULL COMMENT '' AFTER `entered_date_time`;