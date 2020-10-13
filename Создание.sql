-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema timetable_infosystem
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema timetable_infosystem
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `timetable_infosystem`; 
CREATE SCHEMA IF NOT EXISTS `timetable_infosystem` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `timetable_infosystem` ;

-- -----------------------------------------------------
-- Table `timetable_infosystem`.`Преподаватели`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `timetable_infosystem`.`Преподаватели` (
  `Почта` VARCHAR(45) NOT NULL,
  `Серия паспорта` VARCHAR(4)  NOT NULL,
  `Номер паспорта` VARCHAR(6) NOT NULL,
  `Имя` VARCHAR(45) NOT NULL,
  `Отчество` VARCHAR(45) NOT NULL,
  `Фамилия` VARCHAR(45) NOT NULL,
  `Права на супервайзинг` TINYINT NOT NULL,
  `Пароль` CHAR(8) NOT NULL,
  PRIMARY KEY (`Почта`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `timetable_infosystem`.`Кабинеты`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `timetable_infosystem`.`Кабинеты` (
  `Корпус` VARCHAR(1) NOT NULL,
  `Номер` VARCHAR(5) NOT NULL,
  `Почта ответственного` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Корпус`, `Номер`),
  INDEX `fk_Кабинеты_Преподаватели1_idx` (`Почта ответственного` ASC),
  CONSTRAINT `fk_Кабинеты_Преподаватели1`
    FOREIGN KEY (`Почта ответственного`)
    REFERENCES `timetable_infosystem`.`Преподаватели` (`Почта`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `timetable_infosystem`.`Занятия`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `timetable_infosystem`.`Занятия` (
  `Идентификатор` INT NOT NULL AUTO_INCREMENT,
  `Дата` DATE NOT NULL,
  `Время` TIME NOT NULL,
  `Название дисциплины` VARCHAR(45) NOT NULL,
  `Корпус кабинета` VARCHAR(1) NOT NULL,
  `Номер кабинета` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`Идентификатор`),
  INDEX `fk_Занятия_Кабинеты1_idx` (`Корпус кабинета` ASC, `Номер кабинета` ASC),
  CONSTRAINT `fk_Занятия_Кабинеты1`
    FOREIGN KEY (`Корпус кабинета` , `Номер кабинета`)
    REFERENCES `timetable_infosystem`.`Кабинеты` (`Корпус` , `Номер`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `timetable_infosystem`.`Учащиеся`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `timetable_infosystem`.`Учащиеся` (
  `Почта` VARCHAR(45) NOT NULL,
  `Серия паспорта` VARCHAR(4) NOT NULL,
  `Номер паспорта` VARCHAR(6) NOT NULL,
  `Имя` VARCHAR(45) NOT NULL,
  `Отчество` VARCHAR(45) NOT NULL,
  `Фамилия` VARCHAR(45) NOT NULL,
  `Почта куратора` VARCHAR(45) NOT NULL,
  `Пароль` CHAR(8) NOT NULL,
  PRIMARY KEY (`Почта`),
  INDEX `fk_Учащиеся_Преподаватели_idx` (`Почта куратора` ASC),
  CONSTRAINT `fk_Учащиеся_Преподаватели`
    FOREIGN KEY (`Почта куратора`)
    REFERENCES `timetable_infosystem`.`Преподаватели` (`Почта`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `timetable_infosystem`.`Преподаватели на занятиях`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `timetable_infosystem`.`Преподаватели на занятиях` (
  `Почта преподавателя` VARCHAR(45) NOT NULL,
  `Идентификатор занятия` INT NOT NULL,
  `Статус преподавателя на занятии` VARCHAR(9) NOT NULL,
  INDEX `fk_Преподаватели_has_Занятия_Занят_idx` (`Идентификатор занятия` ASC),
  INDEX `fk_Преподаватели_has_Занятия_Препо_idx` (`Почта преподавателя` ASC),
  PRIMARY KEY (`Идентификатор занятия`, `Почта преподавателя`),
  CONSTRAINT `fk_Преподаватели_has_Занятия_Препод1`
    FOREIGN KEY (`Почта преподавателя`)
    REFERENCES `timetable_infosystem`.`Преподаватели` (`Почта`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Преподаватели_has_Занятия_Заняти1`
    FOREIGN KEY (`Идентификатор занятия`)
    REFERENCES `timetable_infosystem`.`Занятия` (`Идентификатор`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `timetable_infosystem`.`Учащиеся на занятиях`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `timetable_infosystem`.`Учащиеся на занятиях` (
  `Почта учащегося` VARCHAR(45) NOT NULL,
  `Идентификатор занятия` INT NOT NULL,
  `Приоритет` VARCHAR(7) NOT NULL,
  INDEX `fk_Учащиеся_has_Занятия_Занятия1_idx` (`Идентификатор занятия` ASC),
  INDEX `fk_Учащиеся_has_Занятия_Учащиеся1_idx` (`Почта учащегося` ASC),
  PRIMARY KEY (`Почта учащегося`, `Идентификатор занятия`),
  CONSTRAINT `fk_Учащиеся_has_Занятия_Учащиеся1`
    FOREIGN KEY (`Почта учащегося`)
    REFERENCES `timetable_infosystem`.`Учащиеся` (`Почта`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Учащиеся_has_Занятия_Занятия1`
    FOREIGN KEY (`Идентификатор занятия`)
    REFERENCES `timetable_infosystem`.`Занятия` (`Идентификатор`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
