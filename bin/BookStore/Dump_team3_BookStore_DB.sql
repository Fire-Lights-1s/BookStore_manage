CREATE DATABASE  IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mydb`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `book_purchase`
--

DROP TABLE IF EXISTS `book_purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_purchase` (
  `no` varchar(12) NOT NULL,
  `oderQuantity` int NOT NULL,
  `oderDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `publisherID` varchar(10) DEFAULT NULL,
  `bookID` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `fk_publisherOder_book_ID_idx` (`bookID`) /*!80000 INVISIBLE */,
  KEY `fk_publisherOder_publisher_ID_idx` (`publisherID`),
  CONSTRAINT `fk_publisher_bookStock_ID` FOREIGN KEY (`bookID`) REFERENCES `bookstock` (`ID`),
  CONSTRAINT `fk_publisherOder_publisher_ID` FOREIGN KEY (`publisherID`) REFERENCES `publisher` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_purchase`
--

LOCK TABLES `book_purchase` WRITE;
/*!40000 ALTER TABLE `book_purchase` DISABLE KEYS */;
INSERT INTO `book_purchase` VALUES ('211208195858',2,'2021-12-08 19:58:57','1018126734','9788934944195'),('211208195945',6,'2021-12-08 19:59:44','1108165046','9788931021998'),('211208200349',10,'2021-12-08 20:03:49','1018126734','9788934988816'),('211208200420',5,'2021-12-08 20:04:20','1058163672','9788936457075'),('211208200437',20,'2021-12-08 20:04:37','1058719009','9791168120846'),('211208200457',5,'2021-12-08 20:04:56','1058163672','9788936457075'),('211208201306',2,'2021-12-08 20:13:05','1058163672','9788936457075'),('211208201335',5,'2021-12-08 20:13:34','1058719009','9791168120846'),('211209104026',15,'2021-12-09 10:40:29','2088124906','9788954683678'),('211209122556',10,'2021-12-09 12:25:58','1108165046','9788931020090');
/*!40000 ALTER TABLE `book_purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_sale`
--

DROP TABLE IF EXISTS `book_sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_sale` (
  `no` varchar(12) NOT NULL,
  `customerID` varchar(10) DEFAULT NULL,
  `bookID` varchar(45) DEFAULT NULL COMMENT 'ID를 받고 나중에 화면에 출력할 때 조인을 사용해서 제목을 보여줌 ',
  `saleDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `saleMethod` varchar(45) NOT NULL,
  `address` varchar(45) DEFAULT NULL,
  `saleQuantity` int DEFAULT NULL,
  PRIMARY KEY (`no`),
  KEY `fk_customer_ID_idx` (`customerID`),
  KEY `fk_bookStock_ID_idx` (`bookID`),
  CONSTRAINT `fk_customerOder_bookStock_ID` FOREIGN KEY (`bookID`) REFERENCES `bookstock` (`ID`),
  CONSTRAINT `fk_customerOder_customer_ID` FOREIGN KEY (`customerID`) REFERENCES `customer` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_sale`
--

LOCK TABLES `book_sale` WRITE;
/*!40000 ALTER TABLE `book_sale` DISABLE KEYS */;
INSERT INTO `book_sale` VALUES ('211208200152','tjdah','9788936455705','2021-12-08 20:01:51','카드결제',NULL,2),('211208200205','tjdah','9788967359720','2021-12-08 20:02:04','계좌이체',NULL,1),('211208200627','ajehr','9788936457075','2021-12-08 20:06:27','카드결제',NULL,1),('211208200637','ajehr','9788934988816','2021-12-08 20:06:37','카드결제',NULL,1),('211208200640','ajehr','9788934988823','2021-12-08 20:06:39','카드결제',NULL,1),('211208200713','tjdah','9788931021998','2021-12-08 20:07:13','계좌이체',NULL,1),('211208200750','sample','9788954683456','2021-12-08 20:07:49','현금결제',NULL,1),('211208204954','yss00001','9788931010152','2021-12-08 20:49:53','현금결제',NULL,1),('211208205040','ajehr','9791168120846','2021-12-08 20:50:39','계좌이체',NULL,3),('211208205119','gangnam','9788954683456','2021-12-08 20:51:18','계좌이체',NULL,1),('211208205123','gangnam','9788934944195','2021-12-08 20:51:23','계좌이체',NULL,2),('211208210848','tjdah','9788936457075','2021-12-08 21:08:47','카드결제',NULL,4),('211208210911','tjdah','9788931020090','2021-12-08 21:09:11','카드결제',NULL,1),('211209103927','tjdah','9788931010831','2021-12-09 10:39:30','현금결제',NULL,1),('211209122408','tjdah','9788931010152','2021-12-09 12:24:10','현금결제',NULL,2);
/*!40000 ALTER TABLE `book_sale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookstock`
--

DROP TABLE IF EXISTS `bookstock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookstock` (
  `ID` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(45) NOT NULL,
  `publisherID` varchar(45) DEFAULT NULL,
  `price` int NOT NULL,
  `stock` int DEFAULT NULL COMMENT '재고',
  PRIMARY KEY (`ID`),
  KEY `fk_publisher_ID_idx` (`publisherID`),
  CONSTRAINT `fk_publisher_ID` FOREIGN KEY (`publisherID`) REFERENCES `publisher` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookstock`
--

LOCK TABLES `bookstock` WRITE;
/*!40000 ALTER TABLE `bookstock` DISABLE KEYS */;
INSERT INTO `bookstock` VALUES ('9788931010152','산월기','1108165046',11800,2),('9788931010831','멋진 신세계: 에디터스 컬렉션','1108165046',11500,21),('9788931020090','손자병법: 쉽고 바르게 읽는 고전','1108165046',14000,10),('9788931021998','17일','1108165046',15000,5),('9788931022049','거래소','1108165046',11500,4),('9788934944195','BTS와 철학하기','1018126734',15800,52),('9788934980070','그래라 그래','1018126734',13000,5),('9788934988816','오월의 청춘 1','1018126734',16500,9),('9788934988823','오월의 청춘 2','1018126734',16500,31),('9788934990208','신부 이태석','1018126734',16800,22),('9788936448059','쉿! 안개초등학교 1','1058163672',12000,6),('9788936455705','문수의 비밀','1058163672',13000,5),('9788936457075','나인','1058163672',14000,11),('9788936463564','기억의 연금술','1058163672',20000,8),('9788954683258','바퀴벌레','2088124906',12500,11),('9788954683326','바닷마을 다이어리 5','2088124906',8500,2),('9788954683456','얀 쿠브레 디저트와 플레이팅','2088124906',55000,4),('9788954683678','이런 얘기 하지 말까?','2088124906',13000,15),('9788967359720','냉장고 인류','2088124906',17000,3),('9791168120846','즉시 기분을 바꿔드립니다','1058719009',14000,18),('9791185014661','콩 고양이','1018126734',10000,5),('9791185014814','플럼 다이어리','1018126734',15500,13),('9791191859119','재','2088124906',14000,21);
/*!40000 ALTER TABLE `bookstock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `ID` varchar(10) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `name` varchar(15) NOT NULL,
  `address` varchar(45) NOT NULL,
  `phoneNumber` varchar(15) DEFAULT NULL,
  `gender` varchar(5) DEFAULT NULL,
  `age` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('45zeong','45zeong11','사오정','경기도 연천군','01045454444','여성',43),('ajehr','ajehr','박진수','광주광역시 광산구','01052525252','남성',29),('CE1001',NULL,'우병섭','부산광역시','01033355686','남성',23),('CE1003','CE1003','이성규','부산광역시','01033667788','남성',23),('gangnam','gangnam','이강남','서울특별시 강남구','01052541313','여성',18),('gdhong','gdhong','홍길동','대전광역시 서구','01034973761','남성',33),('memo','memo12','이메모','강원도 원주시','01044992213','여성',15),('sample','sample','김샘플','서울특별시 강서구','01055553333','남성',33),('tjdah','tjdah','전성모','제주특별자치도 제주시','01012345678','남성',23),('yss00001','yss00001','이순신','부산광역시 남구','01039282242','남성',25);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publisher`
--

DROP TABLE IF EXISTS `publisher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publisher` (
  `ID` varchar(10) NOT NULL,
  `name` varchar(45) NOT NULL,
  `phoneNumber` varchar(15) NOT NULL,
  `publisherAddress` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publisher`
--

LOCK TABLES `publisher` WRITE;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
INSERT INTO `publisher` VALUES ('1018126734','김영사','0319553100','경기도 파주시 문발로 197 파주출판단지 (주)김영사'),('1058163672','창비','0319553333','경기도 파주시 회동길 184'),('1058719009','위즈덤하우스','0221795600','서울 마포구 양화로 19, (합정동, 합정 오피스 빌딩) 16~17층'),('1108165046','문예출판사','023935681','서울 마포구 월드컵북로 6길 30 신원빌딩 401호 '),('2088124906','문학동네','0319558888','경기도 파주시 회동길 210');
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'mydb'
--

--
-- Dumping routines for database 'mydb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-12 18:57:33
