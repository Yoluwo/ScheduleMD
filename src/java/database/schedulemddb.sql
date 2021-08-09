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
    `IsHidden` boolean,
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
    `IsUsed` TINYINT(1) NOT NULL,
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
INSERT INTO `hospital` VALUES (1, 'Foothills Medical Center', 'type','ATS');
INSERT INTO `hospital` VALUES (2, 'Peter Lougheed Hospital', 'type','AS');

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
    VALUES (2, 1, 'Tom', 'Skiff', 'Thomas.Skiffington@edu.sait.ca', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'Ethan', 'Paul', 'ethanpaul20@outlook.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'Mahira', 'Ramos', 'Mahira@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'Manisha', 'Bean', 'Manisha@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'Zane', 'Fischer', 'Zane@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'Cristian', 'Walsh', 'Cristian@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'Timur', 'Proctor', 'Timur@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'Nelson', 'Ruiz', 'Nelson@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'Dorian', 'Ali', 'Dorian@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'Laiba', 'Esquivel', 'Laiba@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'Joey', 'Lawson', 'Joey@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'Craig', 'Donovan', 'Craig@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'Reanne', 'Walls', 'Reanne@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'Fiona', 'Hudson', 'Fiona@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'Janice', 'Ward', 'Janice@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'Maison', 'Newton', 'Maison@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'Saira', 'Espinosa', 'Saira@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'Melvin', 'Vargas', 'Melvin@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'Cheryl', 'Wallace', 'Cheryl@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'Rubie', 'Wilder', 'Rubie@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'Ellis', 'King', 'Ellis@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'Eric', 'Lloyd', 'Eric@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'Renee', 'Handley', 'Renee@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'Taylor', 'Snider', 'Taylor@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'Aurora', 'Whitaker', 'Aurora@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'Krishan', 'Mcgill', 'Krishan@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'Remy', 'Dolan', 'Remy@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 2, 'Josie', 'Weber', 'Josie@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 2, 'Alexander', 'Nguyen', 'Alexander@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 2, 'Evie', 'Coffey', 'Evie@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 2, 'Hannah', 'Logan', 'Hannah@gmail.com', 'password', true,false);

INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 2, 'Roman', 'Olson', 'Roman@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 2, 'Hettie', 'Chung', 'Hettie@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 2, 'Derren', 'Lozano', 'Derren@gmail.com', 'password', true,false);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 2, 'Simeon', 'Ballard', 'Simeon@gmail.com', 'password', true,false);


INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (2, 1, 'Extender', 'Extender', 'ExtenderAccess@gmail.com', 'dunno', true,true);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (3, 1, 'Extender', 'Extender', 'ExtenderTrauama@gmail.com', 'dunno', true,true);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (4, 1, 'Extender', 'Extender', 'ExtenderSenior@gmail.com', 'dunno', true,true);
INSERT INTO `user` (`Role`, `Hospital`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`, `IsExtender`)
    VALUES (5, 1, 'Extender', 'Extender', 'Extender@gmail.com', 'dunno', true,true);




