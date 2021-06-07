/*
 Navicat Premium Data Transfer

 Source Server         : mysql-connection
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : message_system

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 07/06/2021 17:14:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account_table
-- ----------------------------
DROP TABLE IF EXISTS `account_table`;
CREATE TABLE `account_table`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'uuid',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `authority` int(255) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '账号密码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account_table
-- ----------------------------
INSERT INTO `account_table` VALUES (1, 'root', 'root', 2);
INSERT INTO `account_table` VALUES (19, '123', '123', 1);
INSERT INTO `account_table` VALUES (37, 'aaa', 'aasss', 1);
INSERT INTO `account_table` VALUES (39, '456', '123', 1);

SET FOREIGN_KEY_CHECKS = 1;
