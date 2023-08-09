/*
 Navicat Premium Data Transfer

 Source Server         : my-vir-machine-mysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 192.168.83.128:3306
 Source Schema         : swms_tenant

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 13/07/2023 08:37:30
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tenant
-- ----------------------------
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant`
(
    `id`                bigint(20) NOT NULL,
    `create_time`       bigint(20) NOT NULL DEFAULT 0 COMMENT 'Creation time',
    `create_user`       varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT '' COMMENT 'Create user',
    `driver_class_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据库驱动',
    `name`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '租户名称',
    `password`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据库密码',
    `tenant_id`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'md5加密租户名称',
    `update_time`       bigint(20) NOT NULL DEFAULT 0 COMMENT 'Update time',
    `update_user`       varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL DEFAULT '' COMMENT 'Update user',
    `url`               varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据库url',
    `username`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '数据库账号',
    `email`             varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
    `mobile`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '手机号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_name`(`name`) USING BTREE,
    UNIQUE INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant
-- ----------------------------
INSERT INTO `tenant`
VALUES (1, 0, '', 'com.mysql.cj.jdbc.Driver', '123', 'root', 'test0', 0, '',
        'jdbc:mysql://192.168.83.128:3306/test?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true',
        'root', '', '');
INSERT INTO `tenant`
VALUES (2, 0, '', 'com.mysql.cj.jdbc.Driver', '234', 'root', 'test1', 0, '',
        'jdbc:mysql://192.168.83.128:3306/test2?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true',
        'root', '', '');

SET
FOREIGN_KEY_CHECKS = 1;
