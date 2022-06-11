-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db_tareas
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_tareas
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_tareas` DEFAULT CHARACTER SET utf8 ;
USE `db_tareas` ;

-- -----------------------------------------------------
-- Table `db_tareas`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_tareas`.`usuario` (
                                                     `id_usuario` INT NOT NULL AUTO_INCREMENT,
                                                     `nombre_usuario` VARCHAR(50) NULL,
                                                     PRIMARY KEY (`id_usuario`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_tareas`.`tarea`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_tareas`.`tarea` (
                                                   `id_tarea` INT NOT NULL AUTO_INCREMENT,
                                                   `descripcion_tarea` VARCHAR(60) NULL,
                                                   `fecha_creada` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                                   `fecha_completada` TIMESTAMP NULL DEFAULT NULL,
                                                   `tarea_completada` TINYINT NULL DEFAULT 0,
                                                   `usuario_id` INT NULL DEFAULT NULL,
                                                   PRIMARY KEY (`id_tarea`),
                                                   INDEX `fk_tarea_usuario_idx` (`usuario_id` ASC) VISIBLE,
                                                   CONSTRAINT `fk_tarea_usuario`
                                                       FOREIGN KEY (`usuario_id`)
                                                           REFERENCES `db_tareas`.`usuario` (`id_usuario`)
                                                           ON DELETE NO ACTION
                                                           ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;