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
-- Table structure for table `usersubs`
--

DROP TABLE IF EXISTS `usersubs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usersubs` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `plan_id` int NOT NULL,
  `payment_method` varchar(50) NOT NULL,
  `payment_info` varchar(50) NOT NULL,
  `transaction_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`transaction_id`),
  KEY `user_id` (`user_id`),
  KEY `plan_id` (`plan_id`),
  CONSTRAINT `usersubs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `bumbleusers` (`User_id`) ON DELETE CASCADE,
  CONSTRAINT `usersubs_ibfk_2` FOREIGN KEY (`plan_id`) REFERENCES `subsfeat` (`plan_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersubs`
--

LOCK TABLES `usersubs` WRITE;
/*!40000 ALTER TABLE `usersubs` DISABLE KEYS */;
INSERT INTO `usersubs` VALUES (1,1,2,'Gcash','1234-5678-9012','2025-02-28 08:24:21'),(2,1,1,'Gcash','09683266665','2025-02-28 08:37:08'),(3,1,4,'Gcash','09683266665','2025-02-28 08:38:11'),(4,10,8,'Gcash','0969051178','2025-02-28 08:40:47'),(5,10,3,'Gcash','0978592648512','2025-02-28 09:03:58'),(6,19,5,'Gcash','0987532159865','2025-02-28 09:17:45'),(7,10,2,'Gcash','0978562845847','2025-03-03 07:29:17'),(8,10,3,'Gcash','097856284596','2025-03-03 07:44:08'),(9,10,6,'Gcash','09785264851','2025-03-03 11:03:28'),(10,10,4,'Gcash','09785264851','2025-03-03 11:31:18'),(11,10,5,'Gcash','09785236415','2025-03-03 11:39:53');
/*!40000 ALTER TABLE `usersubs` ENABLE KEYS */;
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
