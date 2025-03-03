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
-- Table structure for table `usermessages`
--

DROP TABLE IF EXISTS `usermessages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usermessages` (
  `message_ID` int NOT NULL AUTO_INCREMENT,
  `matches_ID` int NOT NULL,
  `sender_ID` int NOT NULL,
  `messages_text` text NOT NULL,
  `message_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`message_ID`),
  KEY `matches_ID` (`matches_ID`),
  KEY `sender_ID` (`sender_ID`),
  CONSTRAINT `usermessages_ibfk_1` FOREIGN KEY (`matches_ID`) REFERENCES `userpreference` (`matches_ID`) ON DELETE CASCADE,
  CONSTRAINT `usermessages_ibfk_2` FOREIGN KEY (`sender_ID`) REFERENCES `bumbleusers` (`User_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usermessages`
--

LOCK TABLES `usermessages` WRITE;
/*!40000 ALTER TABLE `usermessages` DISABLE KEYS */;
INSERT INTO `usermessages` VALUES (1,1,1,'Test Message','2025-02-27 06:40:05'),(2,1,1,'Hey','2025-02-27 06:46:57'),(3,1,1,'What\'s up?','2025-02-27 06:47:06'),(5,4,10,'Heyy','2025-02-27 07:55:45'),(6,7,16,'Hey','2025-02-27 09:54:24'),(11,27,10,'Hey','2025-02-27 13:36:54'),(12,16,10,'Heyy','2025-02-27 13:39:44'),(14,16,10,'What\'s up?','2025-02-27 13:39:55'),(16,16,10,'Hey! How are you?','2025-02-27 13:44:04'),(18,16,16,'I\'m good haha','2025-02-27 13:44:27'),(20,16,16,'I\'m good haha','2025-02-27 13:44:37'),(22,16,10,'Hey','2025-02-27 13:45:13'),(24,16,10,'HEyy','2025-02-27 13:47:17'),(26,16,16,'Whats upP','2025-02-27 13:47:41'),(28,16,10,'whats wrong ba','2025-02-27 15:38:05'),(30,16,10,'ayaw gumana huhu','2025-02-27 15:52:25'),(32,16,16,'still not showing?','2025-02-27 15:53:07'),(34,30,10,'is it working na ba?','2025-02-27 15:56:32'),(36,30,10,'tangina not showing again','2025-02-27 15:56:44'),(38,30,10,'is it working here?','2025-02-27 16:00:04'),(39,31,4,'is it working here?','2025-02-27 16:00:04'),(40,16,10,'showing here?','2025-02-27 16:00:16'),(41,31,4,'showing here?','2025-02-27 16:00:16'),(42,30,10,'what the heck?','2025-02-27 16:00:27'),(43,31,4,'what the heck?','2025-02-27 16:00:27'),(44,16,16,'it is but not showing mine?','2025-02-27 16:00:51'),(46,30,10,'Hey?','2025-02-27 16:17:11'),(47,31,4,'Hey?','2025-02-27 16:17:11'),(48,16,10,'Yow what\'s up?','2025-02-27 16:17:25'),(49,31,4,'Yow what\'s up?','2025-02-27 16:17:25'),(50,30,10,'hi again','2025-02-27 16:20:59'),(51,30,10,'can u see the message?','2025-02-27 16:22:29'),(52,16,10,'can u see it?','2025-02-27 16:22:45'),(53,16,16,'yes','2025-02-27 16:23:16'),(54,16,10,'yurouim yrtrmet','2025-02-28 05:05:00'),(55,16,10,'AHSAHAHSHAS KRU VSLEIBS;OBVDJS ;RITBS1LGJ2BSG33334567890','2025-02-28 05:05:10'),(56,32,19,'Hey!','2025-02-28 09:10:53'),(57,32,19,'Why are you here?','2025-02-28 09:11:07'),(58,33,1,'Hey I did not receive your message?','2025-02-28 09:14:13'),(59,33,19,'Can u see mine now?','2025-02-28 09:15:22'),(60,33,1,'There!','2025-02-28 09:15:48'),(61,33,19,'Yes, just got it!','2025-02-28 09:16:17'),(62,27,10,'Hii','2025-03-03 07:29:01'),(63,30,10,'Hey!','2025-03-03 08:04:00'),(64,27,10,'heyy','2025-03-03 09:15:31'),(65,27,10,'glad to see you here too!','2025-03-03 09:15:42'),(66,30,10,'sap','2025-03-03 11:03:06'),(67,35,10,'heyy','2025-03-03 11:03:53'),(68,35,10,'zupp','2025-03-03 11:31:02'),(69,36,10,'whats upp','2025-03-03 11:31:45'),(70,36,10,'can you see the message?','2025-03-03 11:39:38'),(71,37,10,'New account, hey!','2025-03-03 11:40:12');
/*!40000 ALTER TABLE `usermessages` ENABLE KEYS */;
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
