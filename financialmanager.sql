-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: financialmanager
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES gbk */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `cityID` char(36) NOT NULL COMMENT '鍩庡競ID',
  `city` varchar(50) NOT NULL COMMENT '鍩庡競',
  PRIMARY KEY (`cityID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES ('2d709c68-87da-11e7-b0f8-708bcd7b61ba','成都'),('d8a616e6-87da-11e7-b0f8-708bcd7b61ba','上海'),('dc2d387e-87da-11e7-b0f8-708bcd7b61ba','重庆');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `id` char(36) NOT NULL COMMENT '閮ㄩ棬ID',
  `city` char(36) NOT NULL COMMENT '鍩庡競',
  `department` varchar(50) NOT NULL COMMENT '閮ㄩ棬',
  PRIMARY KEY (`id`),
  KEY `fk_city_department` (`city`),
  CONSTRAINT `fk_city_department` FOREIGN KEY (`city`) REFERENCES `city` (`cityID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES ('01599550-87de-11e7-b0f8-708bcd7b61ba','2d709c68-87da-11e7-b0f8-708bcd7b61ba','车贷一部'),('b86b45ba-87de-11e7-b0f8-708bcd7b61ba','dc2d387e-87da-11e7-b0f8-708bcd7b61ba','房贷一部'),('c0d2cdbd-87de-11e7-b0f8-708bcd7b61ba','dc2d387e-87da-11e7-b0f8-708bcd7b61ba','房贷二部'),('c4b92431-87de-11e7-b0f8-708bcd7b61ba','dc2d387e-87da-11e7-b0f8-708bcd7b61ba','房贷三部'),('cba0a8d7-87de-11e7-b0f8-708bcd7b61ba','d8a616e6-87da-11e7-b0f8-708bcd7b61ba','房贷一部'),('ceec0a07-87de-11e7-b0f8-708bcd7b61ba','d8a616e6-87da-11e7-b0f8-708bcd7b61ba','房贷二部'),('e8a049e3-87dc-11e7-b0f8-708bcd7b61ba','2d709c68-87da-11e7-b0f8-708bcd7b61ba','技术部'),('f0a8c045-87dd-11e7-b0f8-708bcd7b61ba','2d709c68-87da-11e7-b0f8-708bcd7b61ba','财务部');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eq_borrow_log`
--

DROP TABLE IF EXISTS `eq_borrow_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eq_borrow_log` (
  `borrow_id` char(36) NOT NULL COMMENT '璁惧鍊熻皟ID',
  `eq_id` char(36) NOT NULL COMMENT '璁惧ID',
  `state` char(36) NOT NULL COMMENT '鐘舵€?,
  `use_by` char(36) NOT NULL COMMENT '浣跨敤浜?,
  `do_time` date NOT NULL COMMENT '鎿嶄綔鏃堕棿',
  `use_by_depart` char(36) NOT NULL COMMENT '浣跨敤閮ㄩ棬',
  PRIMARY KEY (`borrow_id`),
  KEY `eqState_eqBorrowLog` (`state`),
  KEY `eqBorrowLog_equipment` (`eq_id`),
  KEY `user_eqBorrowLog` (`use_by`),
  KEY `department_eqBorrowLog` (`use_by_depart`),
  CONSTRAINT `department_eqBorrowLog` FOREIGN KEY (`use_by_depart`) REFERENCES `department` (`id`),
  CONSTRAINT `eqBorrowLog_equipment` FOREIGN KEY (`eq_id`) REFERENCES `equipment` (`eq_id`),
  CONSTRAINT `eqState_eqBorrowLog` FOREIGN KEY (`state`) REFERENCES `eq_state` (`eq_state_id`),
  CONSTRAINT `user_eqBorrowLog` FOREIGN KEY (`use_by`) REFERENCES `staff` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eq_borrow_log`
--

LOCK TABLES `eq_borrow_log` WRITE;
/*!40000 ALTER TABLE `eq_borrow_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eq_borrow_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eq_fix_log`
--

DROP TABLE IF EXISTS `eq_fix_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eq_fix_log` (
  `id` char(36) NOT NULL,
  `fixTime` date NOT NULL,
  `fixType` varchar(50) NOT NULL,
  `fixDetail` varchar(300) NOT NULL,
  `eqId` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `eqFixLog_equipment` (`eqId`),
  CONSTRAINT `eqFixLog_equipment` FOREIGN KEY (`eqId`) REFERENCES `equipment` (`eq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eq_fix_log`
--

LOCK TABLES `eq_fix_log` WRITE;
/*!40000 ALTER TABLE `eq_fix_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `eq_fix_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eq_name`
--

DROP TABLE IF EXISTS `eq_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eq_name` (
  `eq_name_id` char(36) NOT NULL COMMENT '璁惧鍚嶇ОID',
  `eq_name` varchar(50) NOT NULL COMMENT '璁惧鍚嶇О',
  `eq_type_id` char(36) NOT NULL COMMENT '璁惧绫诲瀷',
  PRIMARY KEY (`eq_name_id`),
  KEY `eqName_eqType` (`eq_type_id`),
  CONSTRAINT `eqName_eqType` FOREIGN KEY (`eq_type_id`) REFERENCES `eq_type` (`eq_type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eq_name`
--

LOCK TABLES `eq_name` WRITE;
/*!40000 ALTER TABLE `eq_name` DISABLE KEYS */;
INSERT INTO `eq_name` VALUES ('52bfbe2f-87b3-11e7-b0f8-708bcd7b61ba','台式电脑','fb8ca8f8-86e7-11e7-afa6-708bcd7b61ba'),('69df5c8f-87b3-11e7-b0f8-708bcd7b61ba','打印机','fb8ca8f8-86e7-11e7-afa6-708bcd7b61ba'),('74234779-87b3-11e7-b0f8-708bcd7b61ba','路由器','f832f659-86e7-11e7-afa6-708bcd7b61ba'),('78abeb6d-87b3-11e7-b0f8-708bcd7b61ba','交换机','f832f659-86e7-11e7-afa6-708bcd7b61ba'),('7c8651a2-87b3-11e7-b0f8-708bcd7b61ba','服务器','f832f659-86e7-11e7-afa6-708bcd7b61ba'),('837525e6-87b3-11e7-b0f8-708bcd7b61ba','内存条','f292d64a-86e7-11e7-afa6-708bcd7b61ba'),('898f1aab-87b3-11e7-b0f8-708bcd7b61ba','硬盘','f292d64a-86e7-11e7-afa6-708bcd7b61ba'),('e9947697-87b3-11e7-b0f8-708bcd7b61ba','笔记本电脑','fb8ca8f8-86e7-11e7-afa6-708bcd7b61ba');
/*!40000 ALTER TABLE `eq_name` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eq_starff`
--

DROP TABLE IF EXISTS `eq_starff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eq_starff` (
  `staff_eq_id` char(36) NOT NULL COMMENT '鍛樺伐璁惧ID',
  `eq_id` char(36) NOT NULL COMMENT '璁惧ID',
  `staff_id` char(36) NOT NULL COMMENT '鍛樺伐ID',
  PRIMARY KEY (`staff_eq_id`),
  KEY `eqStaff_staff` (`staff_id`),
  KEY `eqStaff_equipment` (`eq_id`),
  CONSTRAINT `eqStaff_equipment` FOREIGN KEY (`eq_id`) REFERENCES `equipment` (`eq_id`),
  CONSTRAINT `eqStaff_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eq_starff`
--

LOCK TABLES `eq_starff` WRITE;
/*!40000 ALTER TABLE `eq_starff` DISABLE KEYS */;
/*!40000 ALTER TABLE `eq_starff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eq_state`
--

DROP TABLE IF EXISTS `eq_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eq_state` (
  `eq_state_id` char(36) NOT NULL COMMENT '璁惧鐘舵€両D',
  `state` char(10) NOT NULL COMMENT '鐘舵€?,
  PRIMARY KEY (`eq_state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eq_state`
--

LOCK TABLES `eq_state` WRITE;
/*!40000 ALTER TABLE `eq_state` DISABLE KEYS */;
INSERT INTO `eq_state` VALUES ('d5a39eff-9acf-11e7-94cf-1cb72c2f3c53','闲置'),('e3d81c22-9acf-11e7-94cf-1cb72c2f3c53','借出');
/*!40000 ALTER TABLE `eq_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eq_type`
--

DROP TABLE IF EXISTS `eq_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eq_type` (
  `eq_type_id` char(36) NOT NULL COMMENT '璁惧绫诲瀷ID',
  `eq_type_name` varchar(50) NOT NULL COMMENT '璁惧绫诲瀷',
  `eq_type_other_id` char(36) NOT NULL COMMENT '璁惧鍒悕ID',
  PRIMARY KEY (`eq_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eq_type`
--

LOCK TABLES `eq_type` WRITE;
/*!40000 ALTER TABLE `eq_type` DISABLE KEYS */;
INSERT INTO `eq_type` VALUES ('f292d64a-86e7-11e7-afa6-708bcd7b61ba','配件','01'),('f832f659-86e7-11e7-afa6-708bcd7b61ba','机房设备','02'),('fb8ca8f8-86e7-11e7-afa6-708bcd7b61ba','办公设备','03');
/*!40000 ALTER TABLE `eq_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipment` (
  `eq_id` char(36) NOT NULL COMMENT '璁惧ID',
  `eq_type` varchar(50) NOT NULL COMMENT '璁惧绫诲瀷',
  `eq_name` varchar(50) NOT NULL COMMENT '璁惧鍚嶇О',
  `brand_name` varchar(50) NOT NULL COMMENT '鍝佺墝鍚嶇О',
  `purchas_depart` varchar(50) NOT NULL COMMENT '閲囪喘閮ㄩ棬',
  `belong_depart` varchar(50) NOT NULL COMMENT '褰掑睘閮ㄩ棬',
  `purchas_date` date NOT NULL COMMENT '閲囪喘鏃堕棿',
  `supplier` char(36) NOT NULL COMMENT '渚涘簲鍟?,
  `eq_state` char(36) NOT NULL COMMENT '璁惧鐘舵€?,
  `purchas_price` double NOT NULL COMMENT '閲囪喘浠锋牸',
  `custom_message` varchar(1024) DEFAULT NULL COMMENT '鑷畾涔変俊鎭?,
  `eq_other_id` char(36) NOT NULL COMMENT '璁惧鍒悕ID',
  `city` char(36) NOT NULL COMMENT '鍩庡競ID',
  `buyCity` char(36) NOT NULL COMMENT '璐拱鍩庡競',
  PRIMARY KEY (`eq_id`),
  KEY `purchasDepartment_equipment` (`purchas_depart`),
  KEY `belongDepartment_equipment` (`belong_depart`),
  KEY `eqState_equipment` (`eq_state`),
  KEY `supplier_equipment` (`supplier`),
  KEY `eqName_equipment` (`eq_name`),
  KEY `eqType_equipment` (`eq_type`),
  KEY `city_eq_fk` (`city`),
  CONSTRAINT `city_eq_fk` FOREIGN KEY (`city`) REFERENCES `city` (`cityID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment`
--

LOCK TABLES `equipment` WRITE;
/*!40000 ALTER TABLE `equipment` DISABLE KEYS */;
INSERT INTO `equipment` VALUES ('1ac44f18-9bb8-11e7-94cf-1cb72c2f3c53','办公设备','笔记本电脑','华硕笔记本W550JK','e8a049e3-87dc-11e7-b0f8-708bcd7b61ba','e8a049e3-87dc-11e7-b0f8-708bcd7b61ba','2017-09-30','华硕','d5a39eff-9acf-11e7-94cf-1cb72c2f3c53',4299,'','0002','2d709c68-87da-11e7-b0f8-708bcd7b61ba','2d709c68-87da-11e7-b0f8-708bcd7b61ba'),('1d3b0f72-9bb9-11e7-94cf-1cb72c2f3c53','配件','硬盘','HyperX fury发烧级笔记本固态硬盘','e8a049e3-87dc-11e7-b0f8-708bcd7b61ba','e8a049e3-87dc-11e7-b0f8-708bcd7b61ba','2017-09-30','金士顿','d5a39eff-9acf-11e7-94cf-1cb72c2f3c53',755,'','0004','2d709c68-87da-11e7-b0f8-708bcd7b61ba','2d709c68-87da-11e7-b0f8-708bcd7b61ba'),('339d9c85-9bb9-11e7-94cf-1cb72c2f3c53','机房设备','服务器','阿里云服务器','e8a049e3-87dc-11e7-b0f8-708bcd7b61ba','e8a049e3-87dc-11e7-b0f8-708bcd7b61ba','2017-09-30','华硕','d5a39eff-9acf-11e7-94cf-1cb72c2f3c53',65,'','0005','2d709c68-87da-11e7-b0f8-708bcd7b61ba','2d709c68-87da-11e7-b0f8-708bcd7b61ba'),('5e059009-9b90-11e7-94cf-1cb72c2f3c53','配件','内存条','骇客神条','e8a049e3-87dc-11e7-b0f8-708bcd7b61ba','e8a049e3-87dc-11e7-b0f8-708bcd7b61ba','2017-09-28','金士顿','d5a39eff-9acf-11e7-94cf-1cb72c2f3c53',298,'','0006','2d709c68-87da-11e7-b0f8-708bcd7b61ba','2d709c68-87da-11e7-b0f8-708bcd7b61ba'),('dff9c764-9bb8-11e7-94cf-1cb72c2f3c53','配件','硬盘','HyperX fury发烧级笔记本固态硬盘','e8a049e3-87dc-11e7-b0f8-708bcd7b61ba','e8a049e3-87dc-11e7-b0f8-708bcd7b61ba','2017-09-30','金士顿','d5a39eff-9acf-11e7-94cf-1cb72c2f3c53',755,'','0003','2d709c68-87da-11e7-b0f8-708bcd7b61ba','2d709c68-87da-11e7-b0f8-708bcd7b61ba');
/*!40000 ALTER TABLE `equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `res`
--

DROP TABLE IF EXISTS `res`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `res` (
  `res_id` char(36) NOT NULL,
  `res_url` varchar(2048) NOT NULL,
  `res_description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `res`
--

LOCK TABLES `res` WRITE;
/*!40000 ALTER TABLE `res` DISABLE KEYS */;
INSERT INTO `res` VALUES ('1664ccf8-7d91-11e7-8499-708bcd7b61ba','/user.jsp','这是普通用户和管理员才能够访问的页面'),('2299841d-9918-11e7-94cf-1cb72c2f3c53','/user/testmain','主界面'),('2b4718af-7d91-11e7-8499-708bcd7b61ba','/admin.jsp','这是管理员才能够访问的页面');
/*!40000 ALTER TABLE `res` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `res_role`
--

DROP TABLE IF EXISTS `res_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `res_role` (
  `res_r_id` char(36) NOT NULL,
  `res_id` char(36) NOT NULL,
  `r_id` char(36) NOT NULL,
  PRIMARY KEY (`res_r_id`),
  KEY `res_role_foreikey_res_id` (`res_id`),
  KEY `res_role_foreikey_r_id` (`r_id`),
  CONSTRAINT `res_role_foreikey_res_id` FOREIGN KEY (`res_id`) REFERENCES `res` (`res_id`),
  CONSTRAINT `res_role_foreikey_r_id` FOREIGN KEY (`r_id`) REFERENCES `role` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `res_role`
--

LOCK TABLES `res_role` WRITE;
/*!40000 ALTER TABLE `res_role` DISABLE KEYS */;
INSERT INTO `res_role` VALUES ('0d3842b8-7d92-11e7-8499-708bcd7b61ba','1664ccf8-7d91-11e7-8499-708bcd7b61ba','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('1c08f2a2-7d92-11e7-8499-708bcd7b61ba','2b4718af-7d91-11e7-8499-708bcd7b61ba','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('4574debb-9918-11e7-94cf-1cb72c2f3c53','2299841d-9918-11e7-94cf-1cb72c2f3c53','967bafbc-7cda-11e7-9bb7-708bcd7b61ba'),('7b974d03-9918-11e7-94cf-1cb72c2f3c53','2299841d-9918-11e7-94cf-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('ec3434ed-7d91-11e7-8499-708bcd7b61ba','1664ccf8-7d91-11e7-8499-708bcd7b61ba','967bafbc-7cda-11e7-9bb7-708bcd7b61ba');
/*!40000 ALTER TABLE `res_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `rid` char(36) NOT NULL,
  `rname` varchar(40) NOT NULL COMMENT '瑙掕壊鍚嶇О',
  `rdescription` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('967bafbc-7cda-11e7-9bb7-708bcd7b61ba','ROLE_USER','普通登录账户'),('d0a93949-7ce2-11e7-9bb7-708bcd7b61ba','ROLE_ADMIN','管理员账户');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `id` char(36) NOT NULL COMMENT '鍛樺伐ID',
  `name` varchar(30) NOT NULL COMMENT '濮撳悕',
  `department` char(36) NOT NULL COMMENT '閮ㄩ棬',
  `position` varchar(50) NOT NULL COMMENT '宀椾綅',
  `tel` char(11) NOT NULL COMMENT '鑱旂郴鐢佃瘽',
  `entry_time` date NOT NULL COMMENT '鍏ヨ亴鏃堕棿',
  `custom_message` varchar(1024) DEFAULT NULL COMMENT '鑷畾涔変俊鎭?,
  `city` char(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES ('54ed41d8-98f1-11e7-94cf-1cb72c2f3c53','小雷','c0d2cdbd-87de-11e7-b0f8-708bcd7b61ba','Java开发工程师','18382949078','2017-09-21','','dc2d387e-87da-11e7-b0f8-708bcd7b61ba'),('66002c6c-98f4-11e7-94cf-1cb72c2f3c53','Joy','e8a049e3-87dc-11e7-b0f8-708bcd7b61ba','安卓开发高级工程师','18280469370','2017-09-19','性别=女;','2d709c68-87da-11e7-b0f8-708bcd7b61ba');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supplier` (
  `id` char(36) NOT NULL COMMENT '渚涘簲鍟咺D',
  `name` varchar(50) NOT NULL COMMENT '鍚嶇О',
  `adtitude` char(30) NOT NULL COMMENT '璧勮川',
  `address` varchar(300) NOT NULL COMMENT '鍦板潃',
  `contact_name` char(20) NOT NULL COMMENT '鑱旂郴浜?,
  `tel` char(11) NOT NULL COMMENT '鑱旂郴鐢佃瘽',
  `business` varchar(300) NOT NULL COMMENT '涓昏惀涓氬姟',
  `contract_time` date NOT NULL COMMENT '绛剧害鏃堕棿',
  `custom_message` varchar(1024) DEFAULT NULL COMMENT '鑷畾涔変俊鎭?,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES ('041ca1ee-9bb8-11e7-94cf-1cb72c2f3c53','华硕','上市公司','北京市武城区','华天','19625846536','专业高性能笔记本','2017-04-29',''),('5fd908ce-986b-11e7-94cf-1cb72c2f3c53','小米','上市','北京市','雷军','18382949075','卖手机','2017-09-14',''),('7564f680-9b51-11e7-94cf-1cb72c2f3c53','金士顿','上市','北京市内城','HyperX','18382909576','存储介质','2015-06-12',''),('e7fa854a-88ad-11e7-8254-708bcd7b61ba','盐市口联想直营中心','已上市','成都市锦江区大业路6号上普财富中心16F03室公司','李四','13822223333','联想电脑全系列产品','2017-05-09',NULL);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(40) NOT NULL COMMENT '鐢ㄦ埛鍚?,
  `password` varchar(30) NOT NULL COMMENT '瀵嗙爜',
  `city` char(36) NOT NULL COMMENT '鍩庡競',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('Admin','123456',''),('MoonFollow','123456','2d709c68-87da-11e7-b0f8-708bcd7b61ba');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `urid` char(36) NOT NULL,
  `username` char(36) NOT NULL,
  `rid` char(36) NOT NULL,
  PRIMARY KEY (`urid`),
  KEY `user_role_foreikey_username` (`username`),
  KEY `user_role_foreikey_rid` (`rid`),
  CONSTRAINT `user_role_foreikey_rid` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`),
  CONSTRAINT `user_role_foreikey_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES ('09d079b7-7ce3-11e7-9bb7-708bcd7b61ba','Admin','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('3417eeaf-7ce2-11e7-9bb7-708bcd7b61ba','MoonFollow','967bafbc-7cda-11e7-9bb7-708bcd7b61ba');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-17 23:11:45
