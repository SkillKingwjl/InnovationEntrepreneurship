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
-- Table structure for enterprisedetil
-- ----------------------------
DROP TABLE IF EXISTS `enterprisedetil`;
CREATE TABLE `enterprisedetil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `sex` tinyint(1) NOT NULL,
  `enterpriseName` varchar(255) NOT NULL,
  `post` varchar(255) NOT NULL,
  `education` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `weiChart` varchar(255) NOT NULL,
  `isShowWei` tinyint(1) NOT NULL,
  `phoneNum` int(11) NOT NULL,
  `isShowPhoneNum` tinyint(1) NOT NULL,
  `photo` varchar(255) NOT NULL,
  `
personalProfile` text,
  `workExperience` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for studentdetil
-- ----------------------------
DROP TABLE IF EXISTS `studentdetil`;
CREATE TABLE `studentdetil` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `sex` tinyint(1) NOT NULL,
  `studentID` varchar(255) NOT NULL,
  `
department` varchar(255) NOT NULL,
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
-- Table structure for teacherdetil
-- ----------------------------
DROP TABLE IF EXISTS `teacherdetil`;
CREATE TABLE `teacherdetil` (
  `id` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `sex` tinyint(1) NOT NULL,
  `jobNum` varchar(255) NOT NULL,
  `department` varchar(255) NOT NULL,
  `professionalTitle` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `weiChart` varchar(255) NOT NULL,
  `isShowWei` tinyint(1) NOT NULL,
  `phoneNum` int(11) NOT NULL,
  `isShowPhoneNum` tinyint(1) NOT NULL,
  `photo` varchar(255) NOT NULL,
  `personalProfile` text,
  `researchDirection` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) NOT NULL,
  `userPass` varchar(255) NOT NULL,
  `flag` tinyint(1) NOT NULL COMMENT '9代表管理员,1代表普通学生,2代表项目负责人,3代表教师,4代表企业',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
