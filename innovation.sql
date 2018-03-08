/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50637
Source Host           : localhost:3306
Source Database       : innovation

Target Server Type    : MYSQL
Target Server Version : 50637
File Encoding         : 65001

Date: 2018-02-11 17:38:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for studentdetil
-- ----------------------------
DROP TABLE IF EXISTS `userdetil`;
CREATE TABLE `studentdetil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `sex` tinyint(1) NOT NULL,
  `studentID` varchar(255) NOT NULL,
  `department` varchar(255) NOT NULL,
  `major` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `weichart` varchar(255) NOT NULL,
  `isShowWei` tinyint(1) NOT NULL,
  `phoneNum` int(11) NOT NULL,
  `isShowPhoneNum` tinyint(1) NOT NULL,
  `photo` varchar(255) NOT NULL,
  `specialty` text,
  `competitionExperience` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `flag` tinyint(1) NOT NULL COMMENT '9代表管理员,1代表普通学生,2代表项目负责人,3代表教师,4代表企业',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-------------------------
--项目表
-----------------------
DROP TABLE IF EXISTS `projetdetail`;
CREATE TABLE `projetdetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) not null DEFAULT 0 COMMENT'创建人id',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT'项目名称',
  `name` varchar(255) NOT NULl DEFAULT '' comment '创建人姓名',
  `type` int(1) NOT NULL DEFAULT  0 COMMENT '0:互联网+ 1:挑战杯 2:创青春 3:微软创青春',
  `processType` INT(1) NOT NULL DEFAULT 0 COMMENT '0:校级 1：省级 2：国家级',
  `introdution`VARCHAR(1000) not null DEFAULT '' COMMENT '创意简介',
  `leder` VARCHAR(255) not null DEFAULT '' COMMENT '负责人',
  `teacher` VARCHAR(255) not NULL DEFAULT '' COMMENT '导师',
  `findType` INT(1) NOT NULL DEFAULT 0 COMMENT '0:队员 1：项目负责人 2：创新创业导师',
  `findNum` INT(2) NOT NULL  DEFAULT  1 COMMENT '寻找人数',
  `findIntrodution` VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '寻找说明',
  `createTime` TIMESTAMP  not null DEFAULT  CURRENT_TIMESTAMP comment'创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
----------------------------
--消息表
--------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL DEFAULT 0 ,
  `ownID` int(11) NOT NULL DEFAULT 0,
  `projectID` int(11) NOT NULl DEFAULT 0,
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` INT(1) NOT NULL DEFAULT 0 COMMENT '0:申请 1:邀请',
  `status` INT(1) NOT NULL DEFAULT 1 COMMENT '0：通过 1:审核中 2;拒绝',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
-------------------------
--项目状态
------------------------
DROP TABLE IF EXISTS `ownproject`;
CREATE TABLE `ownproject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL DEFAULT 0 ,
  `ownID` int(11) NOT NULL DEFAULT 0,
  `projectID` int(11) NOT NULl DEFAULT 0,
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) not null DEFAULT 0 COMMENT '0:申请 1;邀请 ',
  `flag` int(1) not null DEFAULT 1 comment '0：通过 1:审核中 2;拒绝',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
