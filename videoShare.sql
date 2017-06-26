
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `videoshare_user`
-- ----------------------------
DROP TABLE IF EXISTS `videoshare_user`;
CREATE TABLE `videoshare_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码，MD5加密',
  `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
  `role` int(4) NOT NULL COMMENT '角色0-管理员,1-普通用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mmall_user`
-- ----------------------------
BEGIN;
INSERT INTO `videoshare_user`
VALUES
  ('1', 'admin', '427338237BD929443EC5D48E24FD2B1A', '13800138000', '1', now(), now()),
  ('13', 'geely', '08E9A6EA287E70E7E3F7C982BF7923AC', '13800138000', '0', now(), now()),
  ('17', 'rosen', '095AC193FE2212EEC7A93E8FEFF11902', '13800138000', '0', now(), now()),
  ('21', 'soonerbetter', 'DE6D76FE7C40D5A1A8F04213F2BEFBEE', '13800138000', '0', now(), now());
COMMIT;

-- ----------------------------
--  Table structure for `videoshare_video`
-- ----------------------------
DROP TABLE IF EXISTS `videoshare_video`;
CREATE TABLE `videoshare_video` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '视频id',
  `title` varchar(100) NOT NULL COMMENT '视频标题',
  `detail` varchar(50) DEFAULT NULL COMMENT '视频描述信息',
  `video_image_address` VARCHAR(200) DEFAULT NULL COMMENT '视频缩略图地址',
  `video_address` VARCHAR(200) DEFAULT NULL COMMENT '视频点播地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_name_unique` (`title`) USING BTREE,
  FULLTEXT ( title , detail ) WITH PARSER NGRAM
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
SET GLOBAL innodb_ft_aux_table="videoshare/videoshare_video";

SET FOREIGN_KEY_CHECKS = 1;



##中文全文检索学习
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255),
  body TEXT,
  FULLTEXT ( title , body ) WITH PARSER NGRAM
)  ENGINE=INNODB CHARACTER SET UTF8;

INSERT INTO posts(title,body) VALUES('MySQL全文搜索','MySQL提供了具有许多好的功能的内置全文搜索'),('MySQL教程','学习MySQL快速，简单和有趣');

SET GLOBAL innodb_ft_aux_table="videoshare/posts";
SELECT * FROM information_schema.innodb_ft_index_cache ORDER BY doc_id , position;
SELECT * FROM posts WHERE MATCH (title , body) AGAINST ('mysql' IN natural language MODE);