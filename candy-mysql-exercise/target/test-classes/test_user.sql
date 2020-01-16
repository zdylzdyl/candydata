/*
 测试代码用的单表 test_user
 Navicat Premium Data Transfer

 Source Server         : 本地Mysql
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 14/11/2019 16:27:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for test_user
-- ----------------------------
DROP TABLE IF EXISTS `test_user`;
CREATE TABLE `test_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 80 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test_user
-- ----------------------------
INSERT INTO `test_user` VALUES (1, '张三', '18875839756');
INSERT INTO `test_user` VALUES (2, '李四', '18814339877');
INSERT INTO `test_user` VALUES (3, '老王', '19987357566');
INSERT INTO `test_user` VALUES (4, '甄姬', '19986869688');
INSERT INTO `test_user` VALUES (22, '东施效颦', '13686228624');
INSERT INTO `test_user` VALUES (23, '甄姬', '12787859634');
INSERT INTO `test_user` VALUES (24, '无锡', '19985597987');
INSERT INTO `test_user` VALUES (25, '甄姬', '19986814464');
INSERT INTO `test_user` VALUES (26, '小乔', '19979799427');
INSERT INTO `test_user` VALUES (27, '小乔', '19979797965');
INSERT INTO `test_user` VALUES (28, '甄姬', '19986819754');
INSERT INTO `test_user` VALUES (29, '小乔', '19979732502');
INSERT INTO `test_user` VALUES (30, '小乔', '19979755798');
INSERT INTO `test_user` VALUES (31, '小乔', '19979762996');
INSERT INTO `test_user` VALUES (32, '小乔', '19979781246');
INSERT INTO `test_user` VALUES (33, '小乔', '19979766522');
INSERT INTO `test_user` VALUES (34, '小乔', '19979721005');
INSERT INTO `test_user` VALUES (35, '小乔', '19979756122');
INSERT INTO `test_user` VALUES (36, '测试人', '13602087204');
INSERT INTO `test_user` VALUES (37, '测试人', '13602051062');
INSERT INTO `test_user` VALUES (38, '测试人', '13602054319');
INSERT INTO `test_user` VALUES (39, '测试人', '13602055755');
INSERT INTO `test_user` VALUES (40, '小乔', '19979731789');
INSERT INTO `test_user` VALUES (41, '小乔', '19979729620');
INSERT INTO `test_user` VALUES (44, '测试人', '13602052025');
INSERT INTO `test_user` VALUES (45, '小乔', '19979734815');
INSERT INTO `test_user` VALUES (46, '测试人', '13602058446');
INSERT INTO `test_user` VALUES (48, '大乔', '12735794097');
INSERT INTO `test_user` VALUES (49, '大乔', '12735793643');
INSERT INTO `test_user` VALUES (50, '快速插入测试员0', '13686236756');
INSERT INTO `test_user` VALUES (51, '快速插入测试员1', '13686278533');
INSERT INTO `test_user` VALUES (52, '快速插入测试员2', '13686253499');
INSERT INTO `test_user` VALUES (53, '快速插入测试员3', '13686227742');
INSERT INTO `test_user` VALUES (54, '快速插入测试员4', '13686237883');
INSERT INTO `test_user` VALUES (55, '快速插入测试员5', '13686215051');
INSERT INTO `test_user` VALUES (56, '快速插入测试员6', '13686279128');
INSERT INTO `test_user` VALUES (57, '快速插入测试员7', '13686224399');
INSERT INTO `test_user` VALUES (58, '快速插入测试员8', '13686264257');
INSERT INTO `test_user` VALUES (59, '快速插入测试员9', '13686241401');
INSERT INTO `test_user` VALUES (60, '快速插入测试员10', '13686242684');
INSERT INTO `test_user` VALUES (61, '快速插入测试员11', '13686277262');
INSERT INTO `test_user` VALUES (62, '快速插入测试员12', '13686213921');
INSERT INTO `test_user` VALUES (63, '快速插入测试员13', '13686243502');
INSERT INTO `test_user` VALUES (64, '快速插入测试员14', '13686236858');
INSERT INTO `test_user` VALUES (65, '快速插入测试员15', '13686278687');
INSERT INTO `test_user` VALUES (66, '快速插入测试员16', '13686296046');
INSERT INTO `test_user` VALUES (67, '快速插入测试员17', '13686298773');
INSERT INTO `test_user` VALUES (68, '快速插入测试员18', '13686264540');
INSERT INTO `test_user` VALUES (69, '快速插入测试员19', '13686250488');
INSERT INTO `test_user` VALUES (70, '快速插入测试员20', '13686219188');
INSERT INTO `test_user` VALUES (71, '快速插入测试员21', '13686280670');
INSERT INTO `test_user` VALUES (72, '快速插入测试员22', '13686283609');
INSERT INTO `test_user` VALUES (73, '快速插入测试员23', '13686286253');
INSERT INTO `test_user` VALUES (74, '快速插入测试员24', '13686280562');
INSERT INTO `test_user` VALUES (75, '快速插入测试员25', '13686241236');
INSERT INTO `test_user` VALUES (76, '快速插入测试员26', '13686262575');
INSERT INTO `test_user` VALUES (77, '快速插入测试员27', '13686299368');
INSERT INTO `test_user` VALUES (78, '快速插入测试员28', '13686221232');
INSERT INTO `test_user` VALUES (79, '快速插入测试员29', '13686286123');

SET FOREIGN_KEY_CHECKS = 1;
