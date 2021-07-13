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
    `RoleList` VARCHAR(30),
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
    `IsExtender` TINYINT(1),
    PRIMARY KEY (`UserID`),
    CONSTRAINT `fk_role_id_user`
        FOREIGN KEY (`Role`)
        REFERENCES `schedulemddb`.`role` (`RoleID`),
    CONSTRAINT `fk_hospital_id_user`
        FOREIGN KEY (`Hospital`)
        REFERENCES `schedulemddb`.`hospital` (`HospitalID`)
);
-- -----------------------------------------------------
-- Table `schedulemddb`.`notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `schedulemddb`.`notification` (
    `NotificationID` INT(3) NOT NULL AUTO_INCREMENT,
    `Note` VARCHAR(100) NOT NULL,
    `User` INT(4) NOT NULL,
    PRIMARY KEY (`NotificationID`),
    CONSTRAINT `fk_user_id_noticiation`
        FOREIGN KEY (`User`)
        REFERENCES `schedulemddb`.`user` (`UserID`)
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
    `User` INT(4) NOT NULL,
    `StartTime` DATETIME NOT NULL,
    `EndTime` DATETIME NOT NULL,
    `IsWeekend` TINYINT(1),
    `IsHoliday` TINYINT(1),
    `NumberInBlock` INT(40),
    `DayOfWeek` INT(1), 
    `Role` INT(2),
    PRIMARY KEY (`ShiftID`),
    CONSTRAINT `fk_schedule_id_shift`
        FOREIGN KEY (`Schedule`)
        REFERENCES `schedulemddb`.`schedule` (`ScheduleID`),
    CONSTRAINT `fk_role_id_shift`
        FOREIGN KEY (`Role`)
        REFERENCES `schedulemddb`.`role` (`RoleID`),
    CONSTRAINT `fk_user_id_shift`
        FOREIGN KEY (`User`)
        REFERENCES `schedulemddb`.`user` (`UserID`)
);


-- -----------------------------------------------------
-- This section is used to insert test data into the
-- database, this will be removed/changes when we have
-- real production data instead.
-- -----------------------------------------------------

INSERT INTO `hospital` VALUES (1, 'test', 'test type','ATS');

INSERT INTO `role` VALUES (1, 'system admin');
INSERT INTO `role` VALUES (2, 'access');
INSERT INTO `role` VALUES (3, 'trauma');
INSERT INTO `role` VALUES (4, 'senior');
INSERT INTO `role` VALUES (5, 'extender');

INSERT INTO `user_reset_token` (`UserID`, `Token`) VALUES (1, '');
INSERT INTO `user_reset_token` (`UserID`, `Token`) VALUES (2, '');
INSERT INTO `user_reset_token` (`UserID`, `Token`) VALUES (3, '');
INSERT INTO `user_reset_token` (`UserID`, `Token`) VALUES (4, '');

INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (1, 1, 'Admin', 'Admin', 'admin@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'Alex', 'Zecevic', 'alexz@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'Tom', 'Skiff', 'toms@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'Ethan', 'Paul', 'ethanpaul20@outlook.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'test1first', 'test1last', 'test1@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'test2first', 'test2last', 'test2@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'test3first', 'test3last', 'test3@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'test4first', 'test4last', 'test4@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'test5first', 'test5last', 'test5@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'test6first', 'test6last', 'test6@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'test7first', 'test7last', 'test7@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'test8first', 'test8last', 'test8@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'test9first', 'test9last', 'test9@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'test9first', 'test9last', 'test9@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'test10first', 'test10last', 'test10@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'test11first', 'test11last', 'test11@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'test12first', 'test12last', 'test12@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'test13first', 'test13last', 'test13@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'test13first', 'test13last', 'test13@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'test14first', 'test14last', 'test14@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'test15first', 'test15last', 'test15@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'test16first', 'test16last', 'test16@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'test17first', 'test17last', 'test17@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'test18first', 'test18last', 'test18@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'test19first', 'test19last', 'test19@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'test20first', 'test20last', 'test20@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'test21first', 'test21last', 'test21@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'test22first', 'test22last', 'test22@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'test23first', 'test23last', 'test23@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'test24first', 'test24last', 'test24@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'test25first', 'test25last', 'test25@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (5, 1, 'Extender', 'Extender', 'Extender@gmail.com', 'shit', true,true);