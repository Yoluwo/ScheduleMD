-- -----------------------------------------------------
-- The MySQL script used for setting up the database
-- that will be used throughout the project.
-- Author: Alex Zecevic
-- -----------------------------------------------------

-- -----------------------------------------------------
-- This section is used to create the tables that are
-- needed for the database.
-- -----------------------------------------------------

DROP SCHEMA IF EXISTS `schedulemddb`;
CREATE SCHEMA IF NOT EXISTS `schedulemddb` DEFAULT CHARACTER SET latin1;
USE `schedulemddb`;

-- -----------------------------------------------------
-- Table `schedulemddb`.`hospital`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulemddb`.`hospital` (
    `HospitalID` INT(3) NOT NULL,
    `HospitalName` VARCHAR(30) NOT NULL,
    `HospitalType` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`HospitalID`)
);

-- -----------------------------------------------------
-- Table `schedulemddb`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulemddb`.`role` (
  `RoleID` INT(2) NOT NULL,
  `RoleName` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`RoleID`)
);

-- -----------------------------------------------------
-- Table `schedulemddb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulemddb`.`user` (
    `UserID` INT(4) NOT NULL AUTO_INCREMENT,
    `Role` INT(2) NOT NULL,
    `Hospital` INT(3) NOT NULL,
    `FirstName` VARCHAR(20) NOT NULL,
    `LastName` VARCHAR(20) NOT NULL,
    `Email` VARCHAR(40) NOT NULL,
    `Password` VARCHAR(20) NOT NULL,
    `IsActive` TINYINT(1) NOT NULL,
    PRIMARY KEY (`UserID`),
    CONSTRAINT `fk_role_id_user`
        FOREIGN KEY (`Role`)
        REFERENCES `schedulemddb`.`role` (`RoleID`),
    CONSTRAINT `fk_hospital_id_user`
        FOREIGN KEY (`Hospital`)
        REFERENCES `schedulemddb`.`hospital` (`HospitalID`)
);

-- -----------------------------------------------------
-- Table `schedulemddb`.`user_reset_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulemddb`.`user_reset_token` (
    `UserID` INT(4) NOT NULL,
    `Token` VARCHAR(20) NOT NULL,
    `Expiration_date` DATETIME,
    `is_active` BOOLEAN,
    PRIMARY KEY (`UserID`)
);

-- -----------------------------------------------------
-- Table `schedulemddb`.`timeoff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulemddb`.`timeoff` (
    `TimeOffID` INT(6) NOT NULL AUTO_INCREMENT,
    `User` INT(4) NOT NULL,
    `RequestedDate` DATE NOT NULL,
    `StartDate` DATE NOT NULL,
    `EndDate` DATE NOT NULL,
    `IsApproved` TINYINT(1) NOT NULL,
    PRIMARY KEY (`TimeOffID`),
    CONSTRAINT `fk_user_id_timeoff`
        FOREIGN KEY (`User`)
        REFERENCES `schedulemddb`.`user` (`UserID`)
);

-- -----------------------------------------------------
-- Table `schedulemddb`.`schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulemddb`.`schedule` (
    `ScheduleID` INT(6) NOT NULL AUTO_INCREMENT,
    `Hospital` INT(3) NOT NULL,
    `StartDate` DATE NOT NULL,
    `EndDate` DATE NOT NULL,
    PRIMARY KEY (`ScheduleID`),
    CONSTRAINT `fk_hospital_id_schedule`
        FOREIGN KEY (`Hospital`)
        REFERENCES `schedulemddb`.`hospital` (`HospitalID`)
); 

-- -----------------------------------------------------
-- Table `schedulemddb`.`shift`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulemddb`.`shift` (
    `ShiftID` INT(6) NOT NULL AUTO_INCREMENT,
    `Schedule` INT(6) NOT NULL,
    `StartTime` DATETIME NOT NULL,
    `EndTime` DATETIME NOT NULL,
    PRIMARY KEY (`ShiftID`),
    CONSTRAINT `fk_schedule_id_shift`
        FOREIGN KEY (`Schedule`)
        REFERENCES `schedulemddb`.`schedule` (`ScheduleID`)
);

-- -----------------------------------------------------
-- Table `schedulemddb`.`personalSchedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulemddb`.`personalSchedule` (
    `PersonalSchduleID` INT(6) NOT NULL AUTO_INCREMENT,
    `User` INT(4) NOT NULL,
    `Shift` INT(6) NOT NULL,
    PRIMARY KEY (`personalSchduleID`),
    CONSTRAINT `fk_user_id_personal_schedule`
        FOREIGN KEY (`User`)
        REFERENCES `schedulemddb`.`user` (`UserID`),
    CONSTRAINT `fk_shift_id_personal_schedule`
        FOREIGN KEY (`Shift`)
        REFERENCES `schedulemddb`.`shift` (`ShiftID`)
);

-- -----------------------------------------------------
-- This section is used to insert test data into the
-- database, this will be removed/changes when we have
-- real production data instead.
-- -----------------------------------------------------

INSERT INTO `hospital` VALUES (1, 'test', 'test type');

INSERT INTO `role` VALUES (0, 'system admin');
INSERT INTO `role` VALUES (1, 'regular user');

INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`)
    VALUES (0, 1, 'Admin', 'Admin', 'admin@gmail.com', 'password', true);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`)
    VALUES (1, 1, 'Alex', 'Zecevic', 'alexz@gmail.com', 'password', true);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`)
    VALUES (1, 1, 'Tom', 'Skiff', 'toms@gmail.com', 'password', true);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`)
    VALUES (1, 1, 'Ethan', 'Paul', 'ethanp@gmail.com', 'password', true);

INSERT INTO `user_reset_token` (`UserID`, `Token`) VALUES (1, '');
INSERT INTO `user_reset_token` (`UserID`, `Token`) VALUES (2, '');
INSERT INTO `user_reset_token` (`UserID`, `Token`) VALUES (3, '');
INSERT INTO `user_reset_token` (`UserID`, `Token`) VALUES (4, '');