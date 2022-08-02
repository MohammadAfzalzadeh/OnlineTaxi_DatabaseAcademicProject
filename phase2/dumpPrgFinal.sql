-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: localhost    Database: db_databasePrgP2G9
-- ------------------------------------------------------
-- Server version	8.0.29-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount` (
  `ID` int NOT NULL,
  `Code` int NOT NULL,
  `Percent` decimal(4,2) NOT NULL,
  `Max` bigint DEFAULT NULL,
  PRIMARY KEY (`ID`,`Code`),
  KEY `Code` (`Code`),
  CONSTRAINT `Passenger_Discount_FK` FOREIGN KEY (`ID`) REFERENCES `passenger` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
INSERT INTO `discount` VALUES (1,56987,38.57,500000),(1,56988,23.65,500000),(2,56987,58.00,500000),(2,569874,58.00,500000),(5,56989,58.00,500000),(10,567852,58.00,500000);
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `driver` (
  `ID` int NOT NULL,
  `NationalNumber` char(10) COLLATE utf8mb4_general_ci NOT NULL,
  `FatherName` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `Addres` varchar(535) COLLATE utf8mb4_general_ci NOT NULL,
  `Birthday` date NOT NULL,
  `img` varchar(535) COLLATE utf8mb4_general_ci NOT NULL,
  `IdendifierID` int DEFAULT NULL,
  `Car_PNum1` tinyint NOT NULL,
  `Car_PChr` char(1) COLLATE utf8mb4_general_ci NOT NULL,
  `Car_PNum2` smallint NOT NULL,
  `Car_CNum` smallint NOT NULL,
  `Car_EndInsurance` date NOT NULL,
  `Car_Model` varchar(70) COLLATE utf8mb4_general_ci NOT NULL,
  `Car_Color` varchar(70) COLLATE utf8mb4_general_ci NOT NULL,
  `Certificate_Number` int NOT NULL,
  `Certificate_IssueDate` date NOT NULL,
  `Certificate_ValidityDuration` date NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NationalNumber_driver_Idx` (`NationalNumber`),
  UNIQUE KEY `Car_PNum1` (`Car_PNum1`,`Car_PChr`,`Car_PNum2`,`Car_CNum`),
  UNIQUE KEY `Certi_Num_idx` (`Certificate_Number`),
  KEY `IdentifyDriver` (`IdendifierID`),
  CONSTRAINT `ID_Person` FOREIGN KEY (`ID`) REFERENCES `person` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `IdentifyDriver` FOREIGN KEY (`IdendifierID`) REFERENCES `driver` (`ID`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES `driver` WRITE;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES (1,'1234561230','hosien','Mashhad Rahnamaie pelak7','2008-07-04','driversimgoigo.png',11,48,'ل',98,48,'2022-10-04','pride','green',9678,'2020-10-04','2025-10-09'),(3,'1234567890','Jadi','Mashhad Rahnamaie pelak1','2008-07-04','driversimggfhj84.png',NULL,23,'ل',568,12,'2022-10-04','206Pe','black',87545,'2020-10-04','2025-10-09'),(4,'1234567770','arash','Mashhad gasemCity pelak5','2008-07-04','driversimg\ro[g].png',9,96,'ت',789,11,'2022-10-04','206Pe','yellow',8575745,'2020-10-04','2025-10-09'),(5,'2856115125','akbar','Mashhad Rahnamaie pelak2','2008-07-04','driversimggfhj84.png',3,56,'م',568,12,'2022-10-04','206Pe','withe',9894,'2020-10-04','2025-10-09'),(7,'1234588880','payam','Mashhad Ahmadabad pelak6','2008-07-04','driversimgfigo.png',11,13,'ک',568,36,'2022-10-04','pars','black',232363,'2020-10-04','2025-10-09'),(9,'1234555890','asgar','Mashhad emamat pelak3','2008-07-04','driversimgkfhg.png',5,63,'و',568,32,'2022-10-04','pars','black',8552418,'2020-10-04','2025-10-09'),(11,'1236667890','ali','Mashhad omat pelak4','2008-07-04','driversimg\rfg.png',3,23,'ن',111,12,'2022-10-04','perado','black',574874,'2020-10-04','2025-10-09');
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passenger` (
  `ID` int NOT NULL,
  `IdendifierID` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Identify_passenger` (`IdendifierID`),
  CONSTRAINT `Identify_passenger` FOREIGN KEY (`IdendifierID`) REFERENCES `passenger` (`ID`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `Passenger_To_Person` FOREIGN KEY (`ID`) REFERENCES `person` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passenger`
--

LOCK TABLES `passenger` WRITE;
/*!40000 ALTER TABLE `passenger` DISABLE KEYS */;
INSERT INTO `passenger` VALUES (1,NULL),(10,NULL),(2,1),(5,2),(9,2),(6,5),(8,5);
/*!40000 ALTER TABLE `passenger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Phone` char(11) COLLATE utf8mb4_general_ci NOT NULL,
  `FullName` varchar(70) COLLATE utf8mb4_general_ci NOT NULL,
  `Gender` enum('Male','Female') COLLATE utf8mb4_general_ci DEFAULT NULL,
  `Pass` varchar(70) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `PhonePerson` (`Phone`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'09151003900','ali Ahmadi','Male','Passsgh'),(2,'09151003800','ali Ahmadian','Female','Passsgh'),(3,'09151003600','sara Ahmadian','Female','Passsgh54'),(4,'09151003350','ali Afzalzadeh','Male','Passsgh8541'),(5,'09151003980','mohammad Ahmadian','Male','Passsgh874'),(6,'09151001515','Tagi Ahmadian','Male','Passsgh874'),(7,'09151001616','Nagi Ahmadian','Male','Passsgh874'),(8,'09151001717','Goli Ahmadian','Male','Passsgh874'),(9,'09151001818','Asgar Ahmadian','Male','Passsgh874'),(10,'09151002020','Fatemeh Ahmadian','Female','Passsgh874'),(11,'09151002325','Matin Ahmadian','Female','Passsgh874');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savedLoc`
--

DROP TABLE IF EXISTS `savedLoc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `savedLoc` (
  `ID` int NOT NULL,
  `LocId` int NOT NULL,
  `NameOfLoc` varchar(70) COLLATE utf8mb4_general_ci NOT NULL,
  `Loc` point NOT NULL,
  `Address` varchar(170) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`ID`,`LocId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savedLoc`
--

LOCK TABLES `savedLoc` WRITE;
/*!40000 ALTER TABLE `savedLoc` DISABLE KEYS */;
INSERT INTO `savedLoc` VALUES (1,1,'خانه',_binary '\0\0\0\0\0\0\0D�U��H@\"\��t0@',NULL),(1,2,'شرکت',_binary '\0\0\0\0\0\0\0\��~j�dV@Zd;߇S@',NULL),(2,1,'خانه',_binary '\0\0\0\0\0\0\0F�\��\�HH@w��\Z/X@','Sazman Ab'),(5,1,'خانه',_binary '\0\0\0\0\0\0\0V-�X@\"\��t0@','بلوار کوثر'),(10,1,'شرکت',_binary '\0\0\0\0\0\0\0D�U��H@h�\�|?u1@',NULL),(10,3,'دانشگاه',_binary '\0\0\0\0\0\0\0\"\�*\�\�\�T@\npU:M@','دانشگاه فردوسی مشهد');
/*!40000 ALTER TABLE `savedLoc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS `trip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trip` (
  `TripId` bigint NOT NULL AUTO_INCREMENT,
  `PassId` int NOT NULL,
  `DriverId` int NOT NULL,
  `Amount` bigint NOT NULL,
  `PaymentMethod` enum('نقدی','اعتباری') COLLATE utf8mb4_general_ci NOT NULL,
  `DiscountCode` int DEFAULT NULL,
  `StartTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `EndTime` datetime DEFAULT NULL,
  `StartLoc` point DEFAULT NULL,
  `EndLoc` point DEFAULT NULL,
  `StartSavedLoc` int DEFAULT NULL,
  `EndSavedLoc` int DEFAULT NULL,
  `DriverRate` tinyint DEFAULT NULL,
  `PassRate` tinyint DEFAULT NULL,
  PRIMARY KEY (`TripId`),
  KEY `Pass_To_Trip_FK` (`PassId`),
  KEY `Driver_To_Trip_FK` (`DriverId`),
  KEY `Discount_Trip` (`DiscountCode`),
  CONSTRAINT `Discount_Trip` FOREIGN KEY (`DiscountCode`) REFERENCES `discount` (`Code`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `Driver_To_Trip_FK` FOREIGN KEY (`DriverId`) REFERENCES `driver` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `Pass_To_Trip_FK` FOREIGN KEY (`PassId`) REFERENCES `passenger` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip`
--

LOCK TABLES `trip` WRITE;
/*!40000 ALTER TABLE `trip` DISABLE KEYS */;
INSERT INTO `trip` VALUES (1,1,5,98000,'اعتباری',56987,'2022-01-23 12:45:56','2022-01-23 14:45:56',_binary '\0\0\0\0\0\0\0V-�X@\"\��t0@',NULL,NULL,2,5,5),(2,2,11,8900,'اعتباری',56987,'2022-01-23 13:09:57','2022-01-23 13:50:57',NULL,_binary '\0\0\0\0\0\0\0\��~j�dV@Zd;߇S@',1,NULL,1,3),(3,2,4,123000,'نقدی',NULL,'2021-01-23 15:36:19','2021-01-23 11:36:19',_binary '\0\0\0\0\0\0\0\��~j�dV@Zd;߇S@',NULL,NULL,1,7,8),(4,5,7,9700,'اعتباری',NULL,'2020-01-23 20:46:30','2020-01-23 21:46:36',_binary '\0\0\0\0\0\0\0\"\�*\�\�\�T@\npU:M@',NULL,1,NULL,5,6),(5,9,1,5000,'نقدی',569874,'2022-05-23 21:45:56',NULL,_binary '\0\0\0\0\0\0\0\"\�*\�\�\�T@\npU:M@',_binary '\0\0\0\0\0\0\0\"\�*\�\�\�T@\npU:M@',NULL,NULL,2,8),(6,10,9,59500,'اعتباری',567852,'2022-05-23 21:45:56','2022-10-23 15:39:19',NULL,NULL,1,3,1,8),(7,1,3,56000,'نقدی',NULL,'2022-05-23 21:45:56',NULL,NULL,_binary '\0\0\0\0\0\0\0V-�X@\"\��t0@',1,NULL,1,5);
/*!40000 ALTER TABLE `trip` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-27 21:53:40
