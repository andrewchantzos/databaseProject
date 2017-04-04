SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`PHARMACY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`PHARMACIES` ;

CREATE TABLE IF NOT EXISTS `mydb`.`PHARMACIES` (
  `PHARMACY_ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NOT NULL,
  `TOWN` VARCHAR(45) NOT NULL,
  `STREET` VARCHAR(45) NOT NULL,
  `STR_NUM` INT NULL,
  `POSTAL_CODE` VARCHAR(45) NOT NULL,
  `PHONE` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`PHARMACY_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`DOCTOR`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`DOCTORS` ;

CREATE TABLE IF NOT EXISTS `mydb`.`DOCTORS` (
  `DOCTOR_ID` INT NOT NULL AUTO_INCREMENT,
  `FIRSTNAME` VARCHAR(45) NOT NULL,
  `LASTNAME` VARCHAR(45) NOT NULL,
  `SPECIALITY` VARCHAR(45) NOT NULL,
  `EXPERIENCE_YEARS` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`DOCTOR_ID`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`PATIENT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`PATIENTS` ;

CREATE TABLE IF NOT EXISTS `mydb`.`PATIENTS` (
  `PATIENT_ID` INT NOT NULL AUTO_INCREMENT,
  `DOCTOR_DOCTOR_ID` INT  NULL,
  `TOWN` VARCHAR(45)  NOT NULL,
  `STREET` VARCHAR(45)  NULL,
  `STR_NUM` INT NULL,
  `POSTAL_CODE` VARCHAR(10) NULL,
  `PHONE` VARCHAR(15)  NULL,
  `FIRSTNAME` VARCHAR(45) NOT NULL,
  `LASTNAME` VARCHAR(45) NOT  NULL,
  `AGE` INT NULL,
  PRIMARY KEY (`PATIENT_ID`),
  INDEX `fk_PATIENT_DOCTOR_idx` (`DOCTOR_DOCTOR_ID` ASC),
    FOREIGN KEY (`DOCTOR_DOCTOR_ID`)
    REFERENCES `mydb`.`DOCTORS` (`DOCTOR_ID`)
    ON DELETE SET NULL
    ON UPDATE CASCADE )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`COMPANY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`COMPANIES` ;

CREATE TABLE IF NOT EXISTS `mydb`.`COMPANIES` (
  `COMPANY_ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NOT NULL,
  `PHONE_NUMBER` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`COMPANY_ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`DRUG`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`DRUGS` ;

CREATE TABLE IF NOT EXISTS `mydb`.`DRUGS` (
  `DRUG_ID` INT NOT NULL AUTO_INCREMENT,
  `COMPANY_COMPANY_ID` INT NOT NULL,
  `NAME` VARCHAR(45) NOT NULL,
  `FORMULA` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`DRUG_ID`, `COMPANY_COMPANY_ID`),
  INDEX `fk_DRUG_COMPANY1_idx` (`COMPANY_COMPANY_ID` ASC),
  CONSTRAINT `fk_DRUG_COMPANY1`
    FOREIGN KEY (`COMPANY_COMPANY_ID`)
    REFERENCES `mydb`.`COMPANIES` (`COMPANY_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`CONTRACT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`CONTRACTS` ;

CREATE TABLE IF NOT EXISTS `mydb`.`CONTRACTS` (
  `PHARMACY_ID` INT NOT NULL,
  `COMPANY_ID` INT NOT NULL,
  `START_DATE` DATE NOT NULL,
  `END_DATE` DATE NOT NULL,
  `SUPERVISOR` VARCHAR(45) NOT NULL,
  `TEXT` LONGTEXT NOT NULL,
  PRIMARY KEY (`PHARMACY_ID`, `COMPANY_ID`),
  INDEX `fk_CONTRACT_COMPANY1_idx` (`COMPANY_ID` ASC),
  CONSTRAINT `fk_CONTRACT_PHARMACY1`
    FOREIGN KEY (`PHARMACY_ID`)
    REFERENCES `mydb`.`PHARMACIES` (`PHARMACY_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_CONTRACT_COMPANY1`
    FOREIGN KEY (`COMPANY_ID`)
    REFERENCES `mydb`.`COMPANIES` (`COMPANY_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PRESCRIPTION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`PRESCRIPTIONS` ;

CREATE TABLE IF NOT EXISTS `mydb`.`PRESCRIPTIONS` (
  `DRUG_ID` INT NOT NULL AUTO_INCREMENT,
  /*`DRUG_COMPANY_ID` INT NOT NULL,*/
  `PATIENT_ID` INT NOT NULL,
  `DOCTOR_ID` INT NOT NULL,
  `DATE` DATE NOT NULL,
  `QUANTITY` INT NOT NULL,
  /*PRIMARY KEY (`DRUG_ID`, `DRUG_COMPANY_ID`, `PATIENT_ID`, `DOCTOR_ID`),*/
  PRIMARY KEY (`DRUG_ID`,  `PATIENT_ID`, `DOCTOR_ID`),
  INDEX `fk_PRESCRIPTION_PATIENT1_idx` (`PATIENT_ID` ASC),
  INDEX `fk_PRESCRIPTION_DOCTOR1_idx` (`DOCTOR_ID` ASC),
  CONSTRAINT `fk_PRESCRIPTION_DRUG1`
    /*FOREIGN KEY (`DRUG_ID` , `DRUG_COMPANY_ID`) */
    FOREIGN KEY (`DRUG_ID`)
    /*REFERENCES `mydb`.`DRUGS` (`DRUG_ID` , `COMPANY_COMPANY_ID`) */
    REFERENCES `mydb`.`DRUGS` (`DRUG_ID`) 
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_PRESCRIPTION_PATIENT1`
    FOREIGN KEY (`PATIENT_ID`)
    REFERENCES `mydb`.`PATIENTS` (`PATIENT_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_PRESCRIPTION_DOCTOR1`
    FOREIGN KEY (`DOCTOR_ID`)
    REFERENCES `mydb`.`DOCTORS` (`DOCTOR_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`SELL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`SELLS` ;

CREATE TABLE IF NOT EXISTS `mydb`.`SELLS` (
  `PHARMACY_ID` INT NOT NULL,
  `DRUG_ID` INT NOT NULL,
  `PRICE` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`PHARMACY_ID`, `DRUG_ID`),
  INDEX `fk_SELL_DRUG1_idx` (`DRUG_ID` ASC),
  CONSTRAINT `fk_SELL_PHARMACY1`
    FOREIGN KEY (`PHARMACY_ID`)
    REFERENCES `mydb`.`PHARMACIES` (`PHARMACY_ID`)
    ON DELETE CASCADE
	ON UPDATE  CASCADE,
  CONSTRAINT `fk_SELL_DRUG1`
    FOREIGN KEY (`DRUG_ID`)
    REFERENCES `mydb`.`DRUGS` (`DRUG_ID`)
    ON DELETE CASCADE
    ON UPDATE  CASCADE)
ENGINE = InnoDB;


DROP TABLE IF EXISTS `mydb`.`doctors_audit` ;

CREATE TABLE `mydb`.`doctors_audit` (
   id INT AUTO_INCREMENT PRIMARY KEY,
   old_name VARCHAR(50) NOT NULL,
   changedate TIMESTAMP NOT NULL,
   action VARCHAR(50) NOT NULL
);
DROP TABLE IF EXISTS `mydb`.`pharmacies_audit` ;

CREATE TABLE `mydb`.`pharmacies_audit` (
   id  INT AUTO_INCREMENT PRIMARY KEY,
   modified_id INT  NULL,
   old_name VARCHAR(45)  NULL,
   street VARCHAR(45) NULL,
   changedate TIMESTAMP ,
   action VARCHAR(50) DEFAULT NULL,
   postal_code VARCHAR(45) NULL,
   phone VARCHAR(15) NULL
);

DROP View IF EXISTS `mydb`.`ElderPatients` ;

-- View of patients with age above average
-- cannot be updated (contains AVG)
CREATE VIEW ElderPatients AS
SELECT P.*
FROM Patients as P
WHERE age > (SELECT AVG(age) FROM Patients);


DROP View IF EXISTS `mydb`.`NoAgePatients` ;

-- Updatable View, show patient
-- info without doctors and personal stuff
CREATE VIEW NoAgePatients AS
SELECT P.patient_id, P.firstname, P.lastname, P.town 
FROM Patients as P ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
