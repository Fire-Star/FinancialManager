/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.6.17 : Database - financialmanager
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`financialmanager` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `financialmanager`;

/*Table structure for table `city` */

DROP TABLE IF EXISTS `city`;

CREATE TABLE `city` (
  `cityID` char(36) NOT NULL COMMENT '城市ID',
  `city` varchar(50) NOT NULL COMMENT '城市',
  `cityOtherID` char(2) NOT NULL COMMENT '城市的补充ID',
  PRIMARY KEY (`cityID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `city` */

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `id` char(36) NOT NULL COMMENT '部门ID',
  `city` char(36) NOT NULL COMMENT '城市',
  `department` varchar(50) NOT NULL COMMENT '部门',
  PRIMARY KEY (`id`),
  KEY `fk_city_department` (`city`),
  CONSTRAINT `fk_city_department` FOREIGN KEY (`city`) REFERENCES `city` (`cityID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `department` */

/*Table structure for table `eq_borrow_log` */

DROP TABLE IF EXISTS `eq_borrow_log`;

CREATE TABLE `eq_borrow_log` (
  `borrow_id` char(36) NOT NULL COMMENT '设备借调ID',
  `eq_id` char(36) DEFAULT NULL COMMENT '设备ID',
  `state` char(36) DEFAULT NULL COMMENT '状态',
  `use_by` char(36) DEFAULT NULL COMMENT '使用人',
  `do_time` date DEFAULT NULL COMMENT '操作时间',
  `use_by_depart` char(36) DEFAULT NULL COMMENT '使用部门',
  `detail` char(255) DEFAULT NULL COMMENT '详细',
  PRIMARY KEY (`borrow_id`),
  KEY `eqState_eqBorrowLog` (`state`),
  KEY `eqBorrowLog_equipment` (`eq_id`),
  KEY `user_eqBorrowLog` (`use_by`),
  KEY `department_eqBorrowLog` (`use_by_depart`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `eq_borrow_log` */

/*Table structure for table `eq_fix_log` */

DROP TABLE IF EXISTS `eq_fix_log`;

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

/*Data for the table `eq_fix_log` */

/*Table structure for table `eq_name` */

DROP TABLE IF EXISTS `eq_name`;

CREATE TABLE `eq_name` (
  `eq_name_id` char(36) NOT NULL COMMENT '设备名称ID',
  `eq_name` varchar(50) NOT NULL COMMENT '设备名称',
  `eq_type_id` char(36) NOT NULL COMMENT '设备类型',
  PRIMARY KEY (`eq_name_id`),
  KEY `eqName_eqType` (`eq_type_id`),
  CONSTRAINT `eqName_eqType` FOREIGN KEY (`eq_type_id`) REFERENCES `eq_type` (`eq_type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `eq_name` */

/*Table structure for table `eq_starff` */

DROP TABLE IF EXISTS `eq_starff`;

CREATE TABLE `eq_starff` (
  `staff_eq_id` char(36) NOT NULL COMMENT '员工设备ID',
  `eq_id` char(36) NOT NULL COMMENT '设备ID',
  `staff_id` char(36) NOT NULL COMMENT '员工ID',
  PRIMARY KEY (`staff_eq_id`),
  KEY `eqStaff_staff` (`staff_id`),
  KEY `eqStaff_equipment` (`eq_id`),
  CONSTRAINT `eqStaff_equipment` FOREIGN KEY (`eq_id`) REFERENCES `equipment` (`eq_id`),
  CONSTRAINT `eqStaff_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `eq_starff` */

/*Table structure for table `eq_state` */

DROP TABLE IF EXISTS `eq_state`;

CREATE TABLE `eq_state` (
  `eq_state_id` char(36) NOT NULL COMMENT '设备状态ID',
  `state` char(10) NOT NULL COMMENT '状态',
  PRIMARY KEY (`eq_state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `eq_state` */

insert  into `eq_state`(`eq_state_id`,`state`) values ('56b7c7a1-9dce-11e7-94cf-1cb72c2f3c53','报废'),('d5a39eff-9acf-11e7-94cf-1cb72c2f3c53','闲置'),('e3d81c22-9acf-11e7-94cf-1cb72c2f3c53','使用');

/*Table structure for table `eq_type` */

DROP TABLE IF EXISTS `eq_type`;

CREATE TABLE `eq_type` (
  `eq_type_id` char(36) NOT NULL COMMENT '设备类型ID',
  `eq_type_name` varchar(50) NOT NULL COMMENT '设备类型',
  `eq_type_other_id` char(36) NOT NULL COMMENT '设备别名ID',
  PRIMARY KEY (`eq_type_id`),
  UNIQUE KEY `eq_type_name` (`eq_type_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `eq_type` */

/*Table structure for table `equipment` */

DROP TABLE IF EXISTS `equipment`;

CREATE TABLE `equipment` (
  `eq_id` char(36) NOT NULL COMMENT '设备ID',
  `eq_type` varchar(50) NOT NULL COMMENT '设备类型',
  `eq_name` varchar(50) NOT NULL COMMENT '设备名称',
  `brand_name` varchar(50) NOT NULL COMMENT '品牌名称',
  `purchas_depart` varchar(50) NOT NULL COMMENT '采购部门',
  `belong_depart` varchar(50) NOT NULL COMMENT '归属部门',
  `purchas_date` date NOT NULL COMMENT '采购时间',
  `supplier` char(36) NOT NULL COMMENT '供应商',
  `eq_state` char(36) NOT NULL COMMENT '设备状态',
  `purchas_price` double NOT NULL COMMENT '采购价格',
  `custom_message` varchar(1024) DEFAULT NULL COMMENT '自定义信息',
  `eq_other_id` char(36) NOT NULL COMMENT '设备别名ID',
  `city` char(36) NOT NULL COMMENT '城市ID',
  `buyCity` char(36) NOT NULL COMMENT '购买城市',
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

/*Data for the table `equipment` */

/*Table structure for table `maxvalue` */

DROP TABLE IF EXISTS `maxvalue`;

CREATE TABLE `maxvalue` (
  `key` char(36) NOT NULL COMMENT 'key',
  `value` varchar(1024) NOT NULL COMMENT 'value',
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `maxvalue` */

/*Table structure for table `res` */

DROP TABLE IF EXISTS `res`;

CREATE TABLE `res` (
  `res_id` char(36) NOT NULL,
  `res_url` varchar(2048) NOT NULL,
  `res_description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `res` */

insert  into `res`(`res_id`,`res_url`,`res_description`) values ('1237f174-ae37-11e7-a820-1cb72c2f3c53','/department/add','只有普通用户和管理员能够访问的页面'),('1664ccf8-7d91-11e7-8499-708bcd7b61ba','/user.jsp','这是普通用户和管理员才能够访问的页面'),('222e939d-ae37-11e7-a820-1cb72c2f3c53','/addEquipmentType','只有管理员能够访问的页面'),('2299841d-9918-11e7-94cf-1cb72c2f3c53','/user/index','主界面'),('2b4718af-7d91-11e7-8499-708bcd7b61ba','/admin.jsp','这是管理员才能够访问的页面'),('3433fbb9-a42e-11e7-9463-1cb72c2f3c53','/equipment/fieldManagerPage','这是管理员才能访问的页面'),('418646a1-ae37-11e7-a820-1cb72c2f3c53','/addSingleEquipmentName','只有管理员能够访问的页面'),('6d03b934-a5b4-11e7-9463-1cb72c2f3c53','/equipment/upload','用户管理员才可以做批量上传导入'),('7d167fbc-ae37-11e7-a820-1cb72c2f3c53','/equipment/add','只有普通用户和管理员能够访问的页面'),('84c67ba4-a42e-11e7-9463-1cb72c2f3c53','/department/systemFieldManager','这是管理员才能访问的页面'),('92251d08-ae37-11e7-a820-1cb72c2f3c53','/staff/add','只有普通用户和管理员能够访问的页面'),('a6a4c45b-ae37-11e7-a820-1cb72c2f3c53','/supplier/add','只有普通用户和管理员能够访问的页面'),('ae52e885-a42e-11e7-9463-1cb72c2f3c53','/user/user/query','这是管理员才能访问的页面'),('c5c3406a-ae37-11e7-a820-1cb72c2f3c53','/staff/upload','只有管理员能够访问的页面'),('d6ae9901-ae37-11e7-a820-1cb72c2f3c53','/supplier/upload','只有管理员能够访问的页面'),('e7427251-ae30-11e7-a820-1cb72c2f3c53','/equipment/addPage','只有普通用户和管理员能够访问的页面'),('f274a6a7-ae30-11e7-a820-1cb72c2f3c53','/staff/addPage','只有普通用户和管理员能够访问的页面'),('fa3e289b-ae30-11e7-a820-1cb72c2f3c53','/supplier/addPage','只有普通用户和管理员能够访问的页面'),('fcbd0638-ae36-11e7-a820-1cb72c2f3c53','/city/add','只有普通用户和管理员能够访问的页面');

/*Table structure for table `res_role` */

DROP TABLE IF EXISTS `res_role`;

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

/*Data for the table `res_role` */

insert  into `res_role`(`res_r_id`,`res_id`,`r_id`) values ('00cb24ba-ae37-11e7-a820-1cb72c2f3c53','fcbd0638-ae36-11e7-a820-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('021f83c3-a42f-11e7-9463-1cb72c2f3c53','ae52e885-a42e-11e7-9463-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('0d3842b8-7d92-11e7-8499-708bcd7b61ba','1664ccf8-7d91-11e7-8499-708bcd7b61ba','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('1599e6ef-ae37-11e7-a820-1cb72c2f3c53','1237f174-ae37-11e7-a820-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('1c08f2a2-7d92-11e7-8499-708bcd7b61ba','2b4718af-7d91-11e7-8499-708bcd7b61ba','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('3125909c-ae37-11e7-a820-1cb72c2f3c53','222e939d-ae37-11e7-a820-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('43d49b00-a5b5-11e7-9463-1cb72c2f3c53','6d03b934-a5b4-11e7-9463-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('4574debb-9918-11e7-94cf-1cb72c2f3c53','2299841d-9918-11e7-94cf-1cb72c2f3c53','967bafbc-7cda-11e7-9bb7-708bcd7b61ba'),('4a02881b-ae37-11e7-a820-1cb72c2f3c53','418646a1-ae37-11e7-a820-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('7b974d03-9918-11e7-94cf-1cb72c2f3c53','2299841d-9918-11e7-94cf-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('80ce1bc6-ae37-11e7-a820-1cb72c2f3c53','7d167fbc-ae37-11e7-a820-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('85f41553-ae37-11e7-a820-1cb72c2f3c53','7d167fbc-ae37-11e7-a820-1cb72c2f3c53','967bafbc-7cda-11e7-9bb7-708bcd7b61ba'),('8b0d21f4-ae31-11e7-a820-1cb72c2f3c53','e7427251-ae30-11e7-a820-1cb72c2f3c53','967bafbc-7cda-11e7-9bb7-708bcd7b61ba'),('94f160da-ae37-11e7-a820-1cb72c2f3c53','92251d08-ae37-11e7-a820-1cb72c2f3c53','967bafbc-7cda-11e7-9bb7-708bcd7b61ba'),('9757dc33-ae31-11e7-a820-1cb72c2f3c53','e7427251-ae30-11e7-a820-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('9a417d8a-ae37-11e7-a820-1cb72c2f3c53','92251d08-ae37-11e7-a820-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('a17ade8e-ae31-11e7-a820-1cb72c2f3c53','f274a6a7-ae30-11e7-a820-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('a80a3655-ae31-11e7-a820-1cb72c2f3c53','f274a6a7-ae30-11e7-a820-1cb72c2f3c53','967bafbc-7cda-11e7-9bb7-708bcd7b61ba'),('a8b086c1-ae37-11e7-a820-1cb72c2f3c53','a6a4c45b-ae37-11e7-a820-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('ade68b24-ae37-11e7-a820-1cb72c2f3c53','a6a4c45b-ae37-11e7-a820-1cb72c2f3c53','967bafbc-7cda-11e7-9bb7-708bcd7b61ba'),('b229e3b8-ae31-11e7-a820-1cb72c2f3c53','fa3e289b-ae30-11e7-a820-1cb72c2f3c53','967bafbc-7cda-11e7-9bb7-708bcd7b61ba'),('b83e17fe-ae31-11e7-a820-1cb72c2f3c53','fa3e289b-ae30-11e7-a820-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('c83189a6-ae37-11e7-a820-1cb72c2f3c53','c5c3406a-ae37-11e7-a820-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('d8c72335-ae37-11e7-a820-1cb72c2f3c53','d6ae9901-ae37-11e7-a820-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('ec3434ed-7d91-11e7-8499-708bcd7b61ba','1664ccf8-7d91-11e7-8499-708bcd7b61ba','967bafbc-7cda-11e7-9bb7-708bcd7b61ba'),('f23db5cd-ae34-11e7-a820-1cb72c2f3c53','2299841d-9918-11e7-94cf-1cb72c2f3c53','aa66ea09-ae2f-11e7-a820-1cb72c2f3c53'),('f3d8959f-a42e-11e7-9463-1cb72c2f3c53','3433fbb9-a42e-11e7-9463-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba'),('fd83357e-a42e-11e7-9463-1cb72c2f3c53','84c67ba4-a42e-11e7-9463-1cb72c2f3c53','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `rid` char(36) NOT NULL,
  `rname` varchar(40) NOT NULL COMMENT '角色名称',
  `rdescription` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`rid`,`rname`,`rdescription`) values ('967bafbc-7cda-11e7-9bb7-708bcd7b61ba','ROLE_USER','普通登录账户'),('aa66ea09-ae2f-11e7-a820-1cb72c2f3c53','ROLE_ONLY_SHOW','在网页中只有浏览权限'),('d0a93949-7ce2-11e7-9bb7-708bcd7b61ba','ROLE_ADMIN','管理员账户');

/*Table structure for table `staff` */

DROP TABLE IF EXISTS `staff`;

CREATE TABLE `staff` (
  `id` char(36) NOT NULL COMMENT '员工ID',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `department` char(36) NOT NULL COMMENT '部门',
  `position` varchar(50) NOT NULL COMMENT '岗位',
  `tel` char(255) NOT NULL COMMENT '联系电话',
  `entry_time` date NOT NULL COMMENT '入职时间',
  `custom_message` varchar(1024) DEFAULT NULL COMMENT '自定义信息',
  `city` char(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `staff` */

/*Table structure for table `supplier` */

DROP TABLE IF EXISTS `supplier`;

CREATE TABLE `supplier` (
  `id` char(36) NOT NULL COMMENT '供应商ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `adtitude` char(30) NOT NULL COMMENT '资质',
  `address` varchar(300) NOT NULL COMMENT '地址',
  `contact_name` char(20) NOT NULL COMMENT '联系人',
  `tel` char(255) NOT NULL COMMENT '联系电话',
  `business` varchar(300) NOT NULL COMMENT '主营业务',
  `contract_time` date DEFAULT NULL COMMENT '签约时间',
  `custom_message` varchar(1024) DEFAULT NULL COMMENT '自定义信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `supplier` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `username` varchar(40) NOT NULL COMMENT '用户名',
  `password` varchar(30) NOT NULL COMMENT '密码',
  `city` char(36) NOT NULL COMMENT '城市',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`username`,`password`,`city`) values ('Admin','123456','');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

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

/*Data for the table `user_role` */

insert  into `user_role`(`urid`,`username`,`rid`) values ('09d079b7-7ce3-11e7-9bb7-708bcd7b61ba','Admin','d0a93949-7ce2-11e7-9bb7-708bcd7b61ba');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
