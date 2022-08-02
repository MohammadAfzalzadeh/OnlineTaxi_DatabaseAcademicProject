-- for create database
DROP
DATABASE IF EXISTS `db_databasePrgP2G9`;
CREATE
DATABASE `db_databasePrgP2G9` DEFAULT CHARACTER SET utf8mb4 COLLATE
utf8mb4_general_ci;
-- end

-- create Table for Person
CREATE TABLE `db_databasePrgP2G9`.`person`
(
    `ID`       INT         NOT NULL AUTO_INCREMENT,
    `Phone`    CHAR(11)    NOT NULL,
    `FullName` VARCHAR(70) NOT NULL,
    `Gender`   ENUM('Male','Female') NULL,
    `Pass`     VARCHAR(70) NOT NULL,
    PRIMARY KEY (`ID`),
    UNIQUE `PhonePerson` (`Phone`(11))
) ENGINE = InnoDB;
-- end


-- create Table Passenger
CREATE TABLE `db_databasePrgP2G9`.`passenger`
(
    `ID` INT NOT NULL,
    PRIMARY KEY (`ID`)
) ENGINE = InnoDB;
ALTER TABLE `db_databasePrgP2G9`.`passenger`
    ADD CONSTRAINT `Passenger_To_Person` FOREIGN KEY (`ID`) REFERENCES `person` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT; -- FOREIGN KEY to Person Table

ALTER TABLE `db_databasePrgP2G9`.`passenger`
    ADD `IdendifierID` INT NULL AFTER `ID`; -- for relation identify
ALTER TABLE `db_databasePrgP2G9`.`passenger`
    ADD CONSTRAINT `Identify_passenger` FOREIGN KEY (`IdendifierID`) REFERENCES `passenger` (`ID`) ON DELETE SET NULL ON UPDATE RESTRICT;
-- for relation identify

-- end

-- create Table Driver
CREATE TABLE `db_databasePrgP2G9`.`driver`
(
    `ID`             INT          NOT NULL,
    `NationalNumber` CHAR(10)     NOT NULL,
    `FatherName`     VARCHAR(50)  NOT NULL,
    `Addres`         VARCHAR(535) NOT NULL,
    `Birthday`       DATE         NOT NULL,
    `img`            VARCHAR(535) NOT NULL,
    PRIMARY KEY (`ID`),
    UNIQUE `NationalNumber_driver_Idx` (`NationalNumber`(10))
) ENGINE = InnoDB;

ALTER TABLE `db_databasePrgP2G9`.`driver`
    ADD CONSTRAINT `ID_Person` FOREIGN KEY (`ID`) REFERENCES `person` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT;
-- FOREIGN KEY Driver to Person
-- ADD Car
ALTER TABLE `db_databasePrgP2G9`.`driver`
    ADD `Car_PNum1` TINYINT NOT NULL AFTER `img`, ADD `Car_PChr` CHAR(1) NOT NULL AFTER `Car_PNum1`, ADD `Car_PNum2` SMALLINT NOT NULL AFTER `Car_PChr`, ADD `Car_CNum` SMALLINT NOT NULL AFTER `Car_PNum2`, ADD `Car_EndInsurance` DATE NOT NULL AFTER `Car_CNum`, ADD `Car_Model` VARCHAR(70) NOT NULL AFTER `Car_EndInsurance`, ADD `Car_Color` VARCHAR(70) NOT NULL AFTER `Car_Model`;
ALTER TABLE `db_databasePrgP2G9`.`driver`
    ADD UNIQUE (`Car_PNum1`, `Car_PChr`, `Car_PNum2`, `Car_CNum`);
-- end Of Add Car

-- ADD Certificate
ALTER TABLE `db_databasePrgP2G9`.`driver`
    ADD `Certificate_Number` INT NOT NULL AFTER `Car_Color`, ADD `Certificate_IssueDate` DATE NOT NULL AFTER `Certificate_Number`, ADD `Certificate_ValidityDuration` DATE NOT NULL AFTER `Certificate_IssueDate`, ADD UNIQUE `Certi_Num_idx` (`Certificate_Number`);
-- End Cer

-- ADD Relation Identify Driver
ALTER TABLE `db_databasePrgP2G9`.`driver`
    ADD `IdendifierID` INT NULL AFTER `img`;
ALTER TABLE `db_databasePrgP2G9`.`driver`
    ADD CONSTRAINT `IdentifyDriver` FOREIGN KEY (`IdendifierID`) REFERENCES `driver` (`ID`) ON DELETE SET NULL ON UPDATE RESTRICT;
-- end Relation Identify Driver

-- end of Driver


-- CREATE TABLE discount
CREATE TABLE `db_databasePrgP2G9`.`discount`
(
    `ID`      INT           NOT NULL,
    `Code`    INT           NOT NULL,
    `Percent` DECIMAL(4, 2) NOT NULL,
    `Max`     BIGINT NULL DEFAULT NULL,
    PRIMARY KEY (ID, Code)
) ENGINE = InnoDB;
ALTER TABLE `db_databasePrgP2G9`.`discount`
    ADD CONSTRAINT `Passenger_Discount_FK` FOREIGN KEY (`ID`) REFERENCES `passenger` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT; -- foreign Key To Passenger

ALTER TABLE `db_databasePrgP2G9`.`discount`
    ADD INDEX(`Code`);
-- index for Foreign Key From Trip
-- end Of Discount

-- CREATE TABLE SavedLocation
CREATE TABLE `db_databasePrgP2G9`.`savedLoc`
(
    `ID`        INT         NOT NULL,
    `LocId`     INT         NOT NULL,
    `NameOfLoc` VARCHAR(70) NOT NULL,
    `Loc`       POINT       NOT NULL,
    `Address`   VARCHAR(170) NULL DEFAULT NULL,
    PRIMARY KEY (`ID`, `LocId`)
) ENGINE = InnoDB;
ALTER TABLE `db_databasePrgP2G9`.`savedloc`
    ADD CONSTRAINT `Passenger_ImpLoc_FK` FOREIGN KEY (`ID`) REFERENCES `passenger` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT; -- foreign Key To Passenger

ALTER TABLE `db_databasePrgP2G9`.`savedloc`
    ADD INDEX(`LocId`);
-- index for Foreign Key From Trip
-- end Of SavedLocation


-- Add Tb Trip Is Relation
CREATE TABLE `db_databasePrgP2G9`.`trip`
(
    `TripId`        BIGINT   NOT NULL AUTO_INCREMENT,
    `PassId`        INT      NOT NULL,
    `DriverId`      INT      NOT NULL,
    `Amount`        BIGINT   NOT NULL,
    `PaymentMethod` ENUM('نقدی','اعتباری') NOT NULL,
    `DiscountCode`  INT NULL,
    `StartTime`     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `EndTime`       DATETIME NULL DEFAULT NULL,
    `StartLoc`      POINT NULL DEFAULT NULL,
    `EndLoc`        POINT NULL DEFAULT NULL,
    `StartSavedLoc` INT NULL DEFAULT NULL,
    `EndSavedLoc`   INT NULL DEFAULT NULL,
    `DriverRate`    TINYINT NULL DEFAULT NULL,
    `PassRate`      TINYINT NULL DEFAULT NULL,
    PRIMARY KEY (`TripId`)
) ENGINE = InnoDB;
ALTER TABLE `db_databasePrgP2G9`.`trip`
    ADD CONSTRAINT `Pass_To_Trip_FK` FOREIGN KEY (`PassId`) REFERENCES `passenger` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `db_databasePrgP2G9`.`trip`
    ADD CONSTRAINT `Driver_To_Trip_FK` FOREIGN KEY (`DriverId`) REFERENCES `driver` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `db_databasePrgP2G9`.`trip`
    ADD CONSTRAINT `StartSavedLoc_Trip` FOREIGN KEY (`StartSavedLoc`) REFERENCES `savedloc` (`LocId`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `db_databasePrgP2G9`.`trip`
    ADD CONSTRAINT `EndSavedLoc_Trip` FOREIGN KEY (`EndSavedLoc`) REFERENCES `savedloc` (`LocId`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `db_databasePrgP2G9`.`trip`
    ADD CONSTRAINT `Discount_Trip` FOREIGN KEY (`DiscountCode`) REFERENCES `discount` (`Code`) ON DELETE RESTRICT ON UPDATE RESTRICT;
-- end of Trip

-- insert
-- persons
insert into person
values (NULL, '09151003900', 'ali Ahmadi', 'Male', 'Passsgh');
insert into person
values (NULL, '09151003800', 'ali Ahmadian', 'Female', 'Passsgh');

insert into person
values (NULL, '09151003600', 'sara Ahmadian', 'Female', 'Passsgh54');
insert into person
values (NULL, '09151003350', 'ali Afzalzadeh', 'Male', 'Passsgh8541');
insert into person
values (NULL, '09151003980', 'mohammad Ahmadian', 'Male', 'Passsgh874');

insert into person
values (NULL, '09151001515', 'Tagi Ahmadian', 'Male', 'Passsgh874');
insert into person
values (NULL, '09151001616', 'Nagi Ahmadian', 'Male', 'Passsgh874');
insert into person
values (NULL, '09151001717', 'Goli Ahmadian', 'Male', 'Passsgh874');
insert into person
values (NULL, '09151001818', 'Asgar Ahmadian', 'Male', 'Passsgh874');
insert into person
values (NULL, '09151002020', 'Fatemeh Ahmadian', 'Female', 'Passsgh874');
insert into person
values (NULL, '09151002325', 'Matin Ahmadian', 'Female', 'Passsgh874');


-- passengers
insert into passenger
values (1, NULL);
insert into passenger
values (2, 1);
insert into passenger
values (5, 2);
insert into passenger
values (6, 5);
insert into passenger
values (8, 5);
insert into passenger
values (9, 2);
insert into passenger
values (10, NULL);

-- driver
insert into driver
values (3, '1234567890', 'Jadi', 'Mashhad Rahnamaie pelak1', '2008-7-04', '\drivers\img\gfhj84.png', NULL, 23, 'ل', 568,
        12, '2022-10-04', '206Pe', 'black', 87545, '2020-10-04', '2025-10-09');

insert into driver
values (5, '2856115125', 'akbar', 'Mashhad Rahnamaie pelak2', '2008-7-04', '\drivers\img\gfhj84.png', 3, 56, 'م', 568,
        12, '2022-10-04', '206Pe', 'withe', 9894, '2020-10-04', '2025-10-09');
insert into driver
values (9, '1234555890', 'asgar', 'Mashhad emamat pelak3', '2008-7-04', '\drivers\img\kfhg.png', 5, 63, 'و', 568, 32,
        '2022-10-04', 'pars', 'black', 8552418, '2020-10-04', '2025-10-09');
insert into driver
values (11, '1236667890', 'ali', 'Mashhad omat pelak4', '2008-7-04', '\drivers\img\rfg.png', 3, 23, 'ن', 111, 12,
        '2022-10-04', 'perado', 'black', 574874, '2020-10-04', '2025-10-09');
insert into driver
values (4, '1234567770', 'arash', 'Mashhad gasemCity pelak5', '2008-7-04', '\drivers\img\ro[g].png', 9, 96, 'ت', 789,
        11, '2022-10-04', '206Pe', 'yellow', 8575745, '2020-10-04', '2025-10-09');
insert into driver
values (7, '1234588880', 'payam', 'Mashhad Ahmadabad pelak6', '2008-7-04', '\drivers\img\figo.png', 11, 13, 'ک', 568,
        36, '2022-10-04', 'pars', 'black', 232363, '2020-10-04', '2025-10-09');
insert into driver
values (1, '1234561230', 'hosien', 'Mashhad Rahnamaie pelak7', '2008-7-04', '\drivers\img\oigo.png', 11, 48, 'ل', 98,
        48, '2022-10-04', 'pride', 'green', 9678, '2020-10-04', '2025-10-09');


-- discount
insert into discount
values (1, 56987, 38.569, 500000);
insert into discount
values (1, 56988, 23.65, 500000);
insert into discount
values (2, 56987, 58, 500000);
insert into discount
values (5, 56989, 58, 500000);
insert into discount
values (2, 569874, 58, 500000);
insert into discount
values (10, 567852, 58, 500000);

--saved Loc
insert into savedLoc
values (1, 1, 'خانه', POINT(48.19976, 16.45572), NULL);

insert into savedLoc
values (1, 2, 'شرکت', POINT(89.574, 78.123), NULL);
insert into savedLoc
values (2, 1, 'خانه', POINT(48.569, 96.456), 'Sazman Ab');
insert into savedLoc
values (5, 1, 'خانه', POINT(98.159, 16.45572), 'بلوار کوثر');
insert into savedLoc
values (10, 1, 'شرکت', POINT(48.19976, 17.458), NULL);
insert into savedLoc
values (10, 3, 'دانشگاه', POINT(83.19976, 58.45572), 'دانشگاه فردوسی مشهد');

--trip
insert into trip
values (NULL, 1, 3, 56000, 'نقدی', NULL, '2022-05-23 21:45:56', Null,
        NULL, POINT(98.159, 16.45572), 1, NULL, 1, 5);

insert into trip
values (NULL, 1, 5, 98000, 'اعتباری', 56987, '2022-01-23 12:45:56', '2022-01-23 14:45:56',
        POINT(98.159, 16.45572), NULL, Null, 2, 5, 5);
insert into trip
values (NULL, 2, 11, 8900, 'اعتباری', 56987, '2022-01-23 13:09:57', '2022-01-23 13:50:57',
        NULL, POINT(89.574, 78.123), 1, NULL, 1, 3);
insert into trip
values (NULL, 2, 4, 123000, 'نقدی', NULL, '2021-01-23 15:36:19', '2021-01-23 11:36:19',
        POINT(89.574, 78.123), NULL, Null, 1, 7, 8);
insert into trip
values (NULL, 5, 7, 9700, 'اعتباری', NULL, '2020-01-23 20:46:30', '2020-01-23 21:46:36',
        POINT(83.19976, 58.45572), NULL, 1, NULL, 5, 6);

insert into trip
values (NULL, 9, 1, 5000, 'نقدی', 569874, '2022-05-23 21:45:56', NULL,
        POINT(83.19976, 58.45572), POINT(83.19976, 58.45572), Null, NULL, 2, 8);

insert into trip
values (NULL, 10, 9, 59500, 'اعتباری', 567852, '2022-05-23 21:45:56', '2022-10-23 15:39:19',
        NULL, NULL, 1, 3, 1, 8);

