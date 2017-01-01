-- MySQL Script generated by MySQL Workbench
-- Sat Nov 19 22:59:22 2016
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema portiq
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema portiq
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `portiq` DEFAULT CHARACTER SET utf8 ;
USE `portiq` ;

-- -----------------------------------------------------
-- Table `portiq`.`BillOfLaden`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `portiq`.`BillOfLaden` (
  `idRecord` INT(11) NOT NULL AUTO_INCREMENT,
  `CompanyID` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL,
  `CompanyName` VARCHAR(45) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Bill of Lading #` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  PRIMARY KEY (`idRecord`),
  UNIQUE INDEX `CompanyID_UNIQUE` (`CompanyID` ASC),
  UNIQUE INDEX `idRecord_UNIQUE` (`idRecord` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `portiq`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `portiq`.`Users` (
  `idUser` INT(10) UNSIGNED NOT NULL,
  `email` VARCHAR(45) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `password` VARCHAR(45) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE INDEX `idUser_UNIQUE` (`idUser` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `portiq`.`data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `portiq`.`data` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `Vessel Name` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Operation Type Name` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Cargo Type` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Quantity` INT(11) NULL DEFAULT NULL,
  `Shipping Line` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Container #` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Bill of Lading #` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Container Size Type` INT(11) NULL DEFAULT NULL,
  `Load Port` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Unload Port` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Local or IPI` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Inland Point` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Agent Voyage Number` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Arriving Terminal` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Estimated Vessel Arrival` DATE NULL DEFAULT NULL,
  `Cargo Cutoff` DATE NULL DEFAULT NULL,
  `Estimated Ship Departure` DATE NULL DEFAULT NULL,
  `Expected Availability` DATE NULL DEFAULT NULL,
  `Mode of Entry` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `Mode of Exit` VARCHAR(25) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `ID_UNIQUE` (`ID` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 33938
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;