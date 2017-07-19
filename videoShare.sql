/*
Navicat MySQL Data Transfer

Source Server         : xieguotao
Source Server Version : 50716
Source Host           : 192.168.1.107:3306
Source Database       : videoshare

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-07-19 11:53:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `body` text,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `title` (`title`,`body`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for videoshare_history
-- ----------------------------
DROP TABLE IF EXISTS `videoshare_history`;
CREATE TABLE `videoshare_history` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(50) DEFAULT '',
  `title` varchar(255) DEFAULT NULL,
  `video_address` varchar(255) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `current_video_time` bigint(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `phoneNumber` (`phone_number`),
  KEY `title` (`title`),
  KEY `video_address` (`video_address`),
  KEY `detail` (`detail`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for videoshare_user
-- ----------------------------
DROP TABLE IF EXISTS `videoshare_user`;
CREATE TABLE `videoshare_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码，MD5加密',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `role` int(4) NOT NULL COMMENT '角色0-管理员,1-普通用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_unique` (`phone`) USING BTREE,
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for videoshare_video
-- ----------------------------
DROP TABLE IF EXISTS `videoshare_video`;
CREATE TABLE `videoshare_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '视频id',
  `title` varchar(100) NOT NULL COMMENT '视频标题',
  `detail` varchar(50) DEFAULT NULL COMMENT '视频描述信息',
  `video_image_address` varchar(200) DEFAULT NULL COMMENT '视频缩略图地址',
  `video_address` varchar(200) DEFAULT NULL COMMENT '视频点播地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_name_unique` (`title`) USING BTREE,
  KEY `video_address` (`video_address`),
  KEY `detail` (`detail`),
  FULLTEXT KEY `title` (`title`,`detail`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB AUTO_INCREMENT=332 DEFAULT CHARSET=utf8;
