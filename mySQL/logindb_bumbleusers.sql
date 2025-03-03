-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: logindb
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bumbleusers`
--

DROP TABLE IF EXISTS `bumbleusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bumbleusers` (
  `User_id` int NOT NULL AUTO_INCREMENT,
  `Fname` varchar(20) NOT NULL,
  `Birthday` date NOT NULL,
  `Age` int DEFAULT NULL,
  `Password_hash` varchar(255) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Phone_no` varchar(15) NOT NULL,
  PRIMARY KEY (`User_id`),
  UNIQUE KEY `Fname` (`Fname`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bumbleusers`
--

LOCK TABLES `bumbleusers` WRITE;
/*!40000 ALTER TABLE `bumbleusers` DISABLE KEYS */;
INSERT INTO `bumbleusers` VALUES (1,'Audrey','2005-05-23',19,'89d6f2878f320b612a335dd0ff8eec11d340460256b581023bc587964d08d8c5','lanceaudrey05@gmail.com','09123456789'),(2,'Charles','2002-07-12',22,'8a85951b623b9fe274b689d24049fe661ff7f1ca9130e6584ec37b64e4b3c59d','ch4rlesweh@gmail.com','09187654321'),(3,'Ali','2001-04-04',23,'89bf9215569c720220c140f70993453f3a330993d4df08fa39e66826d54693fb','alimango0@gmail.com','09234567890'),(4,'Mark','2001-02-25',23,'02f1cfde6cf29affb644c95a813e8b664724d3647f01c0456c1b84b4ff8e9d58','krammm3@gmail.com','09345678901'),(5,'Diya','2003-05-27',21,'2acdffc33c28cea9450c8fc7d5c005cc3cb0abf259aaffeed3ae3dd81692d03e','diyanlang6@gmail.com','09456789012'),(10,'admin','2005-02-18',19,'d82494f05d6917ba02f7aaa29689ccb444bb73f20380876cb05d1f37537b7892','amin00@gmail.com','09232323238'),(11,'gee','2004-02-15',20,'479f8899407a784f0ed6759dd5eed7ce522f2dd70e573e543effe1fdeac6631d','gee77@gmail.com','09784536952'),(12,'kcccc','2006-08-24',19,'13a0c83ca9b627c1ed7bebd32fefe26fededac895102c406d3809774a6610712','kc89@gmail.com','09856247985'),(15,'reya4','2005-01-29',19,'b5a4ec869015095060b1171791334513f741177c4011e2c5c36e3e37a5ff8e5f','reya14@yahoo.com','09784526845'),(16,'lila','2004-03-25',20,'3c8b8ed3401c4b9b261a52277e6a18cb44e13bdbb13f8e0ddf5dcfa29035340d','lila44@gmail.com','09785264852'),(17,'zen','2003-11-01',21,'5901f6ea346664b56b1448ac993e16a6066e5f191ed8f9d7c5e3dc309808af29','zeeEn10@gmail.com','09584126845'),(18,'sethings','2005-11-23',19,'ca15f903e35fa395266e1195a764eba55161a09485a0c0a8c67cd5d15ab27ea3','sethingss@yahoo.com','09785262478'),(19,'Sofia','2005-02-17',20,'298ab18a6be37da3410cee1132a2c2c2e55995356d2549af3f46398f2280babc','sofiathe2nd@gmail.com','09584625789'),(22,'clark','2003-04-18',21,'f0906e415f6a1b2016b4dbdf53b06f3c3e8e4159ad981e578e5dd3a0b985c122','clarkpam@yahoo.com','09785624858');
/*!40000 ALTER TABLE `bumbleusers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-03 20:11:35
