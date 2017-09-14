/*
Navicat MySQL Data Transfer

Source Server         : FinatioalManager
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : financialmanager

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2017-09-13 17:14:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `city`
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `cityID` char(36) NOT NULL COMMENT '城市ID',
  `city` varchar(50) NOT NULL COMMENT '城市',
  PRIMARY KEY (`cityID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES ('2d709c68-87da-11e7-b0f8-708bcd7b61ba', '成都');
INSERT INTO `city` VALUES ('d8a616e6-87da-11e7-b0f8-708bcd7b61ba', '上海');
INSERT INTO `city` VALUES ('dc2d387e-87da-11e7-b0f8-708bcd7b61ba', '重庆');

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` char(36) NOT NULL COMMENT '部门ID',
  `city` char(36) NOT NULL COMMENT '城市',
  `department` varchar(50) NOT NULL COMMENT '部门',
  PRIMARY KEY (`id`),
  KEY `fk_city_department` (`city`),
  CONSTRAINT `fk_city_department` FOREIGN KEY (`city`) REFERENCES `city` (`cityID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('01599550-87de-11e7-b0f8-708bcd7b61ba', '2d709c68-87da-11e7-b0f8-708bcd7b61ba', '车贷一部');
INSERT INTO `department` VALUES ('b86b45ba-87de-11e7-b0f8-708bcd7b61ba', 'dc2d387e-87da-11e7-b0f8-708bcd7b61ba', '房贷一部');
INSERT INTO `department` VALUES ('c0d2cdbd-87de-11e7-b0f8-708bcd7b61ba', 'dc2d387e-87da-11e7-b0f8-708bcd7b61ba', '房贷二部');
INSERT INTO `department` VALUES ('c4b92431-87de-11e7-b0f8-708bcd7b61ba', 'dc2d387e-87da-11e7-b0f8-708bcd7b61ba', '房贷三部');
INSERT INTO `department` VALUES ('cba0a8d7-87de-11e7-b0f8-708bcd7b61ba', 'd8a616e6-87da-11e7-b0f8-708bcd7b61ba', '房贷一部');
INSERT INTO `department` VALUES ('ceec0a07-87de-11e7-b0f8-708bcd7b61ba', 'd8a616e6-87da-11e7-b0f8-708bcd7b61ba', '房贷二部');
INSERT INTO `department` VALUES ('e8a049e3-87dc-11e7-b0f8-708bcd7b61ba', '2d709c68-87da-11e7-b0f8-708bcd7b61ba', '技术部');
INSERT INTO `department` VALUES ('f0a8c045-87dd-11e7-b0f8-708bcd7b61ba', '2d709c68-87da-11e7-b0f8-708bcd7b61ba', '财务部');

-- ----------------------------
-- Table structure for `equipment`
-- ----------------------------
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

-- ----------------------------
-- Records of equipment
-- ----------------------------
INSERT INTO `equipment` VALUES ('1', 'type1', 'name1', 'brand1', 'purchas1', 'depart1', '2017-09-07', 'supplier1', 'state1', '111', 'message1', 'other1', '2d709c68-87da-11e7-b0f8-708bcd7b61ba');
INSERT INTO `equipment` VALUES ('2', '22', '222', '2222', '22222', '222222', '0000-00-00', '22222222', '22222222', '2222', '222222222222', '2222222222', 'd8a616e6-87da-11e7-b0f8-708bcd7b61ba');

-- ----------------------------
-- Table structure for `eq_borrow_log`
-- ----------------------------
DROP TABLE IF EXISTS `eq_borrow_log`;
CREATE TABLE `eq_borrow_log` (
  `borrow_id` char(36) NOT NULL COMMENT '设备借调ID',
  `eq_id` char(36) NOT NULL COMMENT '设备ID',
  `state` char(36) NOT NULL COMMENT '状态',
  `use_by` char(36) NOT NULL COMMENT '使用人',
  `do_time` date NOT NULL COMMENT '操作时间',
  `use_by_depart` char(36) NOT NULL COMMENT '使用部门',
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

-- ----------------------------
-- Records of eq_borrow_log
-- ----------------------------

-- ----------------------------
-- Table structure for `eq_fix_log`
-- ----------------------------
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

-- ----------------------------
-- Records of eq_fix_log
-- ----------------------------

-- ----------------------------
-- Table structure for `eq_name`
-- ----------------------------
DROP TABLE IF EXISTS `eq_name`;
CREATE TABLE `eq_name` (
  `eq_name_id` char(36) NOT NULL COMMENT '设备名称ID',
  `eq_name` varchar(50) NOT NULL COMMENT '设备名称',
  `eq_type_id` char(36) NOT NULL COMMENT '设备类型',
  PRIMARY KEY (`eq_name_id`),
  KEY `eqName_eqType` (`eq_type_id`),
  CONSTRAINT `eqName_eqType` FOREIGN KEY (`eq_type_id`) REFERENCES `eq_type` (`eq_type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eq_name
-- ----------------------------
INSERT INTO `eq_name` VALUES ('52bfbe2f-87b3-11e7-b0f8-708bcd7b61ba', '台式电脑', 'fb8ca8f8-86e7-11e7-afa6-708bcd7b61ba');
INSERT INTO `eq_name` VALUES ('69df5c8f-87b3-11e7-b0f8-708bcd7b61ba', '打印机', 'fb8ca8f8-86e7-11e7-afa6-708bcd7b61ba');
INSERT INTO `eq_name` VALUES ('74234779-87b3-11e7-b0f8-708bcd7b61ba', '路由器', 'f832f659-86e7-11e7-afa6-708bcd7b61ba');
INSERT INTO `eq_name` VALUES ('78abeb6d-87b3-11e7-b0f8-708bcd7b61ba', '交换机', 'f832f659-86e7-11e7-afa6-708bcd7b61ba');
INSERT INTO `eq_name` VALUES ('7c8651a2-87b3-11e7-b0f8-708bcd7b61ba', '服务器', 'f832f659-86e7-11e7-afa6-708bcd7b61ba');
INSERT INTO `eq_name` VALUES ('837525e6-87b3-11e7-b0f8-708bcd7b61ba', '内存条', 'f292d64a-86e7-11e7-afa6-708bcd7b61ba');
INSERT INTO `eq_name` VALUES ('898f1aab-87b3-11e7-b0f8-708bcd7b61ba', '硬盘', 'f292d64a-86e7-11e7-afa6-708bcd7b61ba');
INSERT INTO `eq_name` VALUES ('e9947697-87b3-11e7-b0f8-708bcd7b61ba', '笔记本电脑', 'fb8ca8f8-86e7-11e7-afa6-708bcd7b61ba');

-- ----------------------------
-- Table structure for `eq_starff`
-- ----------------------------
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

-- ----------------------------
-- Records of eq_starff
-- ----------------------------

-- ----------------------------
-- Table structure for `eq_state`
-- ----------------------------
DROP TABLE IF EXISTS `eq_state`;
CREATE TABLE `eq_state` (
  `eq_state_id` char(36) NOT NULL COMMENT '设备状态ID',
  `state` char(10) NOT NULL COMMENT '状态',
  PRIMARY KEY (`eq_state_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eq_state
-- ----------------------------

-- ----------------------------
-- Table structure for `eq_type`
-- ----------------------------
DROP TABLE IF EXISTS `eq_type`;
CREATE TABLE `eq_type` (
  `eq_type_id` char(36) NOT NULL COMMENT '设备类型ID',
  `eq_type_name` varchar(50) NOT NULL COMMENT '设备类型',
  `eq_type_other_id` char(36) NOT NULL COMMENT '设备别名ID',
  PRIMARY KEY (`eq_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eq_type
-- ----------------------------
INSERT INTO `eq_type` VALUES ('f292d64a-86e7-11e7-afa6-708bcd7b61ba', '配件', '01');
INSERT INTO `eq_type` VALUES ('f832f659-86e7-11e7-afa6-708bcd7b61ba', '机房设备', '02');
INSERT INTO `eq_type` VALUES ('fb8ca8f8-86e7-11e7-afa6-708bcd7b61ba', '办公设备', '03');

-- ----------------------------
-- Table structure for `res`
-- ----------------------------
DROP TABLE IF EXISTS `res`;
CREATE TABLE `res` (
  `res_id` char(36) NOT NULL,
  `res_url` varchar(2048) NOT NULL,
  `res_description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of res
-- ----------------------------
INSERT INTO `res` VALUES ('1664ccf8-7d91-11e7-8499-708bcd7b61ba', '/user.jsp', '这是普通用户和管理员才能够访问的页面');
INSERT INTO `res` VALUES ('2b4718af-7d91-11e7-8499-708bcd7b61ba', '/admin.jsp', '这是管理员才能够访问的页面');

-- ----------------------------
-- Table structure for `res_role`
-- ----------------------------
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

-- ----------------------------
-- Records of res_role
-- ----------------------------
INSERT INTO `res_role` VALUES ('0d3842b8-7d92-11e7-8499-708bcd7b61ba', '1664ccf8-7d91-11e7-8499-708bcd7b61ba', 'd0a93949-7ce2-11e7-9bb7-708bcd7b61ba');
INSERT INTO `res_role` VALUES ('1c08f2a2-7d92-11e7-8499-708bcd7b61ba', '2b4718af-7d91-11e7-8499-708bcd7b61ba', 'd0a93949-7ce2-11e7-9bb7-708bcd7b61ba');
INSERT INTO `res_role` VALUES ('ec3434ed-7d91-11e7-8499-708bcd7b61ba', '1664ccf8-7d91-11e7-8499-708bcd7b61ba', '967bafbc-7cda-11e7-9bb7-708bcd7b61ba');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `rid` char(36) NOT NULL,
  `rname` varchar(40) NOT NULL COMMENT '角色名称',
  `rdescription` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('967bafbc-7cda-11e7-9bb7-708bcd7b61ba', 'ROLE_USER', '普通登录账户');
INSERT INTO `role` VALUES ('d0a93949-7ce2-11e7-9bb7-708bcd7b61ba', 'ROLE_ADMIN', '管理员账户');

-- ----------------------------
-- Table structure for `staff`
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `id` char(36) NOT NULL COMMENT '员工ID',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `department` char(36) NOT NULL COMMENT '部门',
  `position` varchar(50) NOT NULL COMMENT '岗位',
  `tel` char(11) NOT NULL COMMENT '联系电话',
  `entry_time` date NOT NULL COMMENT '入职时间',
  `custom_message` varchar(1024) DEFAULT NULL COMMENT '自定义信息',
  `city` char(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES ('1', 'name1', 'dep1', 'po1', 'tel1', '2017-09-11', null, '1');
INSERT INTO `staff` VALUES ('2', 'name2', 'dep2', 'po2', 'tel2', '2017-09-03', null, '2');

-- ----------------------------
-- Table structure for `supplier`
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `id` char(36) NOT NULL COMMENT '供应商ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `adtitude` char(30) NOT NULL COMMENT '资质',
  `address` varchar(300) NOT NULL COMMENT '地址',
  `contact_name` char(20) NOT NULL COMMENT '联系人',
  `tel` char(11) NOT NULL COMMENT '联系电话',
  `business` varchar(300) NOT NULL COMMENT '主营业务',
  `contract_time` date NOT NULL COMMENT '签约时间',
  `custom_message` varchar(1024) DEFAULT NULL COMMENT '自定义信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES ('e7fa854a-88ad-11e7-8254-708bcd7b61ba', '盐市口联想直营中心', '已上市', '成都市锦江区大业路6号上普财富中心16F03室公司', '李四', '13822223333', '联想电脑全系列产品', '2017-05-09', null);

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(40) NOT NULL COMMENT '用户名',
  `password` varchar(30) NOT NULL COMMENT '密码',
  `city` char(36) NOT NULL COMMENT '城市',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('Admin', '123456', '');
INSERT INTO `user` VALUES ('MoonFollow', '123456', '2d709c68-87da-11e7-b0f8-708bcd7b61ba');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
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

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('09d079b7-7ce3-11e7-9bb7-708bcd7b61ba', 'Admin', 'd0a93949-7ce2-11e7-9bb7-708bcd7b61ba');
INSERT INTO `user_role` VALUES ('3417eeaf-7ce2-11e7-9bb7-708bcd7b61ba', 'MoonFollow', '967bafbc-7cda-11e7-9bb7-708bcd7b61ba');
