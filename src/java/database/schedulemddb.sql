DROP SCHEMA IF EXISTS `schedulemddb`;
CREATE SCHEMA IF NOT EXISTS `schedulemddb` DEFAULT CHARACTER SET latin1;
USE `schedulemddb`;

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
    `RoleID` INT(2) NOT NULL,
    `FirstName` VARCHAR(20) NOT NULL,
    `LastName` VARCHAR(20) NOT NULL,
    `Email` VARCHAR(40) NOT NULL,
    `Password` VARCHAR(20) NOT NULL,
    `IsActive` TINYINT(1) NOT NULL,
    PRIMARY KEY (`UserID`),
    CONSTRAINT `fk_role_id`
        FOREIGN KEY (`RoleID`)
        REFERENCES `schedulemddb`.`role` (RoleID)
);

INSERT INTO `role` VALUES (0, 'system admin');
INSERT INTO `role` VALUES (1, 'regular user');

INSERT INTO `user` (`RoleID`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`)
    VALUES (0, 'Admin', 'Admin', 'admin@gmail.com', 'password', true);
INSERT INTO `user` (`RoleID`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`)
    VALUES (1, 'Alex', 'Zecevic', 'alexz@gmail.com', 'password', true);
INSERT INTO `user` (`RoleID`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`)
    VALUES (1, 'Tom', 'Skiff', 'toms@gmail.com', 'password', true);
INSERT INTO `user` (`RoleID`, `FirstName`,`LastName`,`Email`, `Password`, `IsActive`)
    VALUES (1, 'Ethan', 'Paul', 'ethanp@gmail.com', 'password', true);