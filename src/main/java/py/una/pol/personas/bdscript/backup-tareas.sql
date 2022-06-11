-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: db_tareas
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `tarea`
--

DROP TABLE IF EXISTS `tarea`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarea` (
  `id_tarea` int NOT NULL AUTO_INCREMENT,
  `descripcion_tarea` varchar(60) DEFAULT NULL,
  `fecha_creada` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `fecha_completada` timestamp NULL DEFAULT NULL,
  `tarea_completada` tinyint DEFAULT '0',
  `usuario_id` int DEFAULT NULL,
  PRIMARY KEY (`id_tarea`),
  KEY `fk_tarea_usuario_idx` (`usuario_id`),
  CONSTRAINT `fk_tarea_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarea`
--

LOCK TABLES `tarea` WRITE;
/*!40000 ALTER TABLE `tarea` DISABLE KEYS */;
INSERT INTO `tarea` VALUES (14,'5','2022-06-10 23:42:45','2022-06-10 23:51:44',1,4),(15,'5','2022-06-10 23:42:46','2022-06-11 19:26:43',1,4),(16,'5333 tarea','2022-06-10 23:43:49',NULL,0,4),(17,'5333 tarea','2022-06-10 23:43:50','2022-06-12 01:59:48',1,4),(18,'Tarea 1xxx','2022-06-11 02:51:57','2022-08-11 05:59:48',1,4),(20,'Tarea 1xxx','2022-06-11 02:52:01','2022-06-11 05:59:48',1,NULL),(21,'TAREA GENERADA POR TEST - ALEATORIO 27','2022-06-11 16:05:03','2022-06-11 16:21:11',1,NULL),(22,'TAREA ACTU','2022-06-11 16:21:11','2022-06-11 16:22:10',1,NULL),(24,'TAREA GENERADA POR TEST - ALEATORIO 88','2022-06-11 16:23:31','2022-06-11 16:32:25',1,3),(25,'TAREA GENERADA POR TEST - ALEATORIO 59','2022-06-11 16:32:25','2022-06-11 16:33:29',1,NULL),(26,'TAREA GENERADA POR TEST - ALEATORIO 99','2022-06-11 16:33:29','2022-06-11 16:34:06',1,NULL),(27,'TAREA GENERADA POR TEST - ALEATORIO 37','2022-06-11 16:34:06','2022-06-11 17:34:09',1,NULL),(28,'TAREA GENERADA POR TEST - ALEATORIO 85','2022-06-11 17:34:09',NULL,0,NULL),(29,'123321','2022-06-11 19:10:24',NULL,0,NULL),(30,'prueba1234','2022-06-11 19:11:55',NULL,0,NULL),(31,'nueva tarea','2022-06-11 19:13:38',NULL,0,NULL),(32,'descripcion123','2022-06-11 19:17:14',NULL,0,NULL);
/*!40000 ALTER TABLE `tarea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,''),(2,'user 1'),(3,'user 1'),(4,'user 2'),(5,'user 2'),(6,'Juan Pablo5'),(7,'Juan Pablo'),(8,'Juan Pablo');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'db_tareas'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-11 15:41:23
