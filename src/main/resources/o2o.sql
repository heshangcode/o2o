/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : o2o

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 23/04/2019 14:02:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_area
-- ----------------------------
DROP TABLE IF EXISTS `tb_area`;
CREATE TABLE `tb_area`  (
  `area_id` int(2) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `priority` int(2) NOT NULL DEFAULT 0 COMMENT '权重，优先级',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `last_edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`area_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_area
-- ----------------------------
INSERT INTO `tb_area` VALUES (2, '东苑', 1, '2018-09-16 17:10:41', '2018-09-16 17:10:41');
INSERT INTO `tb_area` VALUES (3, '西苑', 2, NULL, NULL);
INSERT INTO `tb_area` VALUES (4, '朝阳区', 3, NULL, NULL);
INSERT INTO `tb_area` VALUES (5, '南关区', 4, NULL, NULL);
INSERT INTO `tb_area` VALUES (6, '经开区', 5, NULL, NULL);
INSERT INTO `tb_area` VALUES (7, '绿园区', 6, NULL, NULL);

-- ----------------------------
-- Table structure for tb_head_line
-- ----------------------------
DROP TABLE IF EXISTS `tb_head_line`;
CREATE TABLE `tb_head_line`  (
  `line_id` int(100) NOT NULL AUTO_INCREMENT,
  `line_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `line_link` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `line_img` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `priority` int(2) NULL DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `last_edit_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`line_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_head_line
-- ----------------------------
INSERT INTO `tb_head_line` VALUES (11, '1', 'http://115.28.159.6/myo2o/frontend/shopdetail?shopId=20', '/upload/images/item/headtitle/2017061320315746624.jpg', 1, 1, '2017-06-13 20:31:57', '2017-06-13 20:31:57');
INSERT INTO `tb_head_line` VALUES (12, '2', 'http://115.28.159.6/myo2o/frontend/shopdetail?shopId=20', '/upload/images/item/headtitle/2017061320371786788.jpg', 2, 1, '2017-06-13 20:37:17', '2017-06-13 20:37:17');
INSERT INTO `tb_head_line` VALUES (14, '3', 'http://115.28.159.6/myo2o/frontend/shopdetail?shopId=20', '/upload/images/item/headtitle/2017061320393452772.jpg', 3, 1, '2017-06-13 20:39:34', '2017-06-13 20:39:34');
INSERT INTO `tb_head_line` VALUES (15, '4', 'http://115.28.159.6/myo2o/frontend/shopdetail?shopId=20', '/upload/images/item/headtitle/2017061320400198256.jpg', 4, 1, '2017-06-13 20:40:01', '2017-06-13 20:40:01');

-- ----------------------------
-- Table structure for tb_local_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_local_auth`;
CREATE TABLE `tb_local_auth`  (
  `local_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `username` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `last_edit_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`local_auth_id`) USING BTREE,
  UNIQUE INDEX `uk_local_profile`(`username`) USING BTREE,
  INDEX `fk_localauth_profile`(`user_id`) USING BTREE,
  CONSTRAINT `fk_localauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_person_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_person_info`;
CREATE TABLE `tb_person_info`  (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_person_info
-- ----------------------------
INSERT INTO `tb_person_info` VALUES (1, '1', '1', '和尚');
INSERT INTO `tb_person_info` VALUES (2, 'admin', 'admin', 'admin');

-- ----------------------------
-- Table structure for tb_product
-- ----------------------------
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product`  (
  `product_id` int(100) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `product_desc` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img_addr` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `normal_price` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `promotion_price` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `last_edit_time` datetime(0) NULL DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT 0,
  `product_category_id` int(11) NULL DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`product_id`) USING BTREE,
  INDEX `fk_product_procate`(`product_category_id`) USING BTREE,
  INDEX `fk_product_shop`(`shop_id`) USING BTREE,
  CONSTRAINT `fk_product_procate` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`product_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_product
-- ----------------------------
INSERT INTO `tb_product` VALUES (15, '脏脏包', '网红面包', '\\upload\\item\\shop\\1\\2018111515265759843.jpg', '50', '30', 10, '2018-11-15 15:26:58', '2018-11-15 15:26:58', 1, 11, 1);
INSERT INTO `tb_product` VALUES (17, '猫屎咖啡', '1', '\\upload\\item\\shop\\1\\2018111515293567629.jpg', '60', '50', 30, '2018-11-15 15:29:36', '2018-11-15 15:29:36', 1, 9, 1);
INSERT INTO `tb_product` VALUES (18, '丝袜奶茶', '奶茶', '\\upload\\item\\shop\\1\\2018111515305386476.jpg', '50', '10', 60, '2018-11-15 15:30:54', '2018-11-15 16:16:03', 1, 10, 1);
INSERT INTO `tb_product` VALUES (19, '肥牛', '肥牛', '\\upload\\item\\shop\\7\\2018111515460998439.jpg', '50', '30', 30, '2018-11-15 15:46:09', '2018-11-15 15:46:09', 1, 12, 7);
INSERT INTO `tb_product` VALUES (20, '可乐', '22', '\\upload\\item\\shop\\6\\2018111515485819830.jpg', '50', '30', 30, '2018-11-15 15:48:58', '2018-11-15 15:48:58', 1, 17, 6);
INSERT INTO `tb_product` VALUES (21, '长城', '55', '\\upload\\item\\shop\\6\\2018111515505645448.jpg', '900', '700', 60, '2018-11-15 15:50:56', '2018-11-15 15:50:56', 1, 17, 6);
INSERT INTO `tb_product` VALUES (23, '大刀肉', '1', '\\upload\\item\\shop\\7\\2018111516223458052.jpg', '90', '50', 60, '2018-11-15 16:22:35', '2018-11-15 16:22:35', 1, 12, 7);
INSERT INTO `tb_product` VALUES (24, '千层肚', '1', '\\upload\\item\\shop\\7\\2018111516232190563.jpg', '60', '50', 60, '2018-11-15 16:23:22', '2018-11-15 16:23:22', 1, 16, 7);
INSERT INTO `tb_product` VALUES (25, '撒尿牛丸', '1', '\\upload\\item\\shop\\7\\2018111516240465360.jpg', '50', '30', 90, '2018-11-15 16:24:04', '2018-11-15 16:24:04', 1, 12, 7);

-- ----------------------------
-- Table structure for tb_product_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_category`;
CREATE TABLE `tb_product_category`  (
  `product_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_category_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `priority` int(2) NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`product_category_id`) USING BTREE,
  INDEX `fk_procate_shop`(`shop_id`) USING BTREE,
  CONSTRAINT `fk_procate_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_product_category
-- ----------------------------
INSERT INTO `tb_product_category` VALUES (9, '咖啡', 20, NULL, 1);
INSERT INTO `tb_product_category` VALUES (10, '奶茶', 50, NULL, 1);
INSERT INTO `tb_product_category` VALUES (11, '糕点', 60, NULL, 1);
INSERT INTO `tb_product_category` VALUES (12, '肉类', 20, NULL, 7);
INSERT INTO `tb_product_category` VALUES (13, '丸类', 30, NULL, 7);
INSERT INTO `tb_product_category` VALUES (14, '素菜', 60, NULL, 7);
INSERT INTO `tb_product_category` VALUES (15, '饮料', 50, NULL, 7);
INSERT INTO `tb_product_category` VALUES (16, '海鲜', 100, NULL, 7);
INSERT INTO `tb_product_category` VALUES (17, '酒水', 50, NULL, 6);
INSERT INTO `tb_product_category` VALUES (18, '零食', 30, NULL, 6);
INSERT INTO `tb_product_category` VALUES (19, '唱歌', 60, NULL, 6);

-- ----------------------------
-- Table structure for tb_product_img
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_img`;
CREATE TABLE `tb_product_img`  (
  `product_img_id` int(20) NOT NULL AUTO_INCREMENT,
  `img_addr` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `img_desc` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `priority` int(2) NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `product_id` int(20) NULL DEFAULT NULL,
  PRIMARY KEY (`product_img_id`) USING BTREE,
  INDEX `fk_proimg_product`(`product_id`) USING BTREE,
  CONSTRAINT `fk_proimg_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_product_img
-- ----------------------------
INSERT INTO `tb_product_img` VALUES (20, '\\upload\\item\\shop\\1\\2018111515293598646.jpg', NULL, NULL, '2018-11-15 15:29:36', 17);
INSERT INTO `tb_product_img` VALUES (21, '\\upload\\item\\shop\\1\\2018111515305397773.jpg', NULL, NULL, '2018-11-15 15:30:54', 18);
INSERT INTO `tb_product_img` VALUES (22, '\\upload\\item\\shop\\7\\2018111515460992055.jpg', NULL, NULL, '2018-11-15 15:46:10', 19);
INSERT INTO `tb_product_img` VALUES (23, '\\upload\\item\\shop\\6\\2018111515485816627.jpg', NULL, NULL, '2018-11-15 15:48:58', 20);
INSERT INTO `tb_product_img` VALUES (24, '\\upload\\item\\shop\\6\\2018111515505631340.jpg', NULL, NULL, '2018-11-15 15:50:56', 21);
INSERT INTO `tb_product_img` VALUES (26, '\\upload\\item\\shop\\7\\2018111516223495963.jpg', NULL, NULL, '2018-11-15 16:22:35', 23);
INSERT INTO `tb_product_img` VALUES (27, '\\upload\\item\\shop\\7\\2018111516232155019.jpg', NULL, NULL, '2018-11-15 16:23:22', 24);
INSERT INTO `tb_product_img` VALUES (28, '\\upload\\item\\shop\\7\\2018111516240476591.jpg', NULL, NULL, '2018-11-15 16:24:04', 25);

-- ----------------------------
-- Table structure for tb_shop
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop`  (
  `shop_id` int(10) NOT NULL AUTO_INCREMENT,
  `owner_id` int(10) NOT NULL COMMENT '店铺创建人',
  `area_id` int(5) NULL DEFAULT NULL,
  `shop_category_id` int(11) NULL DEFAULT NULL,
  `shop_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shop_desc` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_addr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_img` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `priority` int(3) NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `last_edit_time` datetime(0) NULL DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT 0,
  `advice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`shop_id`) USING BTREE,
  INDEX `fk_shop_area`(`area_id`) USING BTREE,
  INDEX `fk_shop_profile`(`owner_id`) USING BTREE,
  INDEX `fk_shop_shopcate`(`shop_category_id`) USING BTREE,
  CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_shop_shopcate` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_shop
-- ----------------------------
INSERT INTO `tb_shop` VALUES (1, 1, 3, 1, 'BreadTalk', '咖啡', '东北', '88888888', '\\upload\\item\\shop\\1\\2018111515260464076.jpg', NULL, '2018-09-18 08:58:53', '2018-11-15 15:26:04', 1, '审核中');
INSERT INTO `tb_shop` VALUES (6, 1, 4, 25, '华庭KTV', '1', '东北', '66666666', '\\upload\\item\\shop\\6\\2018111516122928724.jpg', NULL, '2018-11-15 15:43:00', '2018-11-15 16:12:29', 1, NULL);
INSERT INTO `tb_shop` VALUES (7, 1, 4, 20, '望江门', '1', '东北', '11111111', '\\upload\\item\\shop\\7\\2018111516124778907.jpg', NULL, '2018-11-15 15:44:05', '2018-11-15 16:12:48', 1, NULL);

-- ----------------------------
-- Table structure for tb_shop_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_category`;
CREATE TABLE `tb_shop_category`  (
  `shop_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_category_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `shop_category_desc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `shop_category_img` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `last_edit_time` datetime(0) NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`shop_category_id`) USING BTREE,
  INDEX `fk_shop_category_self`(`parent_id`) USING BTREE,
  CONSTRAINT `fk_shop_category_self` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_shop_category
-- ----------------------------
INSERT INTO `tb_shop_category` VALUES (1, '咖啡奶茶', '咖啡奶茶', 'test', 1, NULL, NULL, 1);
INSERT INTO `tb_shop_category` VALUES (2, '咖啡', '测试类别', 'test3', 0, NULL, NULL, 1);
INSERT INTO `tb_shop_category` VALUES (10, '二手市场', '二手商品交易', '/upload/images/item/shopcategory/2017061223272255687.png', 100, '2017-06-04 20:10:58', '2017-06-12 23:27:22', NULL);
INSERT INTO `tb_shop_category` VALUES (11, '美容美发', '美容美发', '/upload/images/item/shopcategory/2017061223273314635.png', 99, '2017-06-04 20:12:57', '2017-06-12 23:27:33', NULL);
INSERT INTO `tb_shop_category` VALUES (12, '美食饮品', '美食饮品', '/upload/images/item/shopcategory/2017061223274213433.png', 98, '2017-06-04 20:15:21', '2017-06-12 23:27:42', NULL);
INSERT INTO `tb_shop_category` VALUES (13, '休闲娱乐', '休闲娱乐', '/upload/images/item/shopcategory/2017061223275121460.png', 97, '2017-06-04 20:19:29', '2017-06-12 23:27:51', NULL);
INSERT INTO `tb_shop_category` VALUES (14, '旧车', '旧车', '/upload/images/item/shopcategory/2017060420315183203.png', 80, '2017-06-04 20:31:51', '2017-06-04 20:31:51', 10);
INSERT INTO `tb_shop_category` VALUES (15, '二手书籍', '二手书籍', '/upload/images/item/shopcategory/2017060420322333745.png', 79, '2017-06-04 20:32:23', '2017-06-04 20:32:23', 10);
INSERT INTO `tb_shop_category` VALUES (17, '护理', '护理', '/upload/images/item/shopcategory/2017060420372391702.png', 76, '2017-06-04 20:37:23', '2017-06-04 20:37:23', 11);
INSERT INTO `tb_shop_category` VALUES (18, '理发', '理发', '/upload/images/item/shopcategory/2017060420374775350.png', 74, '2017-06-04 20:37:47', '2017-06-04 20:37:47', 11);
INSERT INTO `tb_shop_category` VALUES (20, '大排档', '大排档', '/upload/images/item/shopcategory/2017060420460491494.png', 59, '2017-06-04 20:46:04', '2017-06-04 20:46:04', 12);
INSERT INTO `tb_shop_category` VALUES (22, '奶茶店', '奶茶店', '/upload/images/item/shopcategory/2017060420464594520.png', 58, '2017-06-04 20:46:45', '2017-06-04 20:46:45', 12);
INSERT INTO `tb_shop_category` VALUES (24, '密室逃生', '密室逃生', '/upload/images/item/shopcategory/2017060420500783376.png', 56, '2017-06-04 20:50:07', '2017-06-04 21:45:53', 13);
INSERT INTO `tb_shop_category` VALUES (25, 'KTV', 'KTV', '/upload/images/item/shopcategory/2017060420505834244.png', 57, '2017-06-04 20:50:58', '2017-06-04 20:51:14', 13);
INSERT INTO `tb_shop_category` VALUES (27, '培训教育', '培训教育', '/upload/images/item/shopcategory/2017061223280082147.png', 96, '2017-06-04 21:51:36', '2017-06-12 23:28:00', NULL);
INSERT INTO `tb_shop_category` VALUES (28, '租赁市场', '租赁市场', '/upload/images/item/shopcategory/2017061223281361578.png', 95, '2017-06-04 21:53:52', '2017-06-12 23:28:13', NULL);
INSERT INTO `tb_shop_category` VALUES (29, '程序设计', '程序设计', '/upload/images/item/shopcategory/2017060421593496807.png', 50, '2017-06-04 21:59:34', '2017-06-04 21:59:34', 27);
INSERT INTO `tb_shop_category` VALUES (30, '声乐舞蹈', '声乐舞蹈', '/upload/images/item/shopcategory/2017060421595843693.png', 49, '2017-06-04 21:59:58', '2017-06-04 21:59:58', 27);
INSERT INTO `tb_shop_category` VALUES (31, '演出道具', '演出道具', '/upload/images/item/shopcategory/2017060422114076152.png', 45, '2017-06-04 22:11:40', '2017-06-04 22:11:40', 28);
INSERT INTO `tb_shop_category` VALUES (32, '交通工具', '交通工具', '/upload/images/item/shopcategory/2017060422121144586.png', 44, '2017-06-04 22:12:11', '2017-06-04 22:12:11', 28);

-- ----------------------------
-- Table structure for tb_wechat_auth
-- ----------------------------
DROP TABLE IF EXISTS `tb_wechat_auth`;
CREATE TABLE `tb_wechat_auth`  (
  `wechat_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `open_id` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`wechat_auth_id`) USING BTREE,
  UNIQUE INDEX `open_id`(`open_id`) USING BTREE,
  INDEX `fk_wechatauth_profile`(`user_id`) USING BTREE,
  CONSTRAINT `fk_wechatauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
