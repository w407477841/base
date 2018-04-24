/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.22
Source Server Version : 50528
Source Host           : 192.168.0.22:3306
Source Database       : quality-tracing

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2017-12-25 14:05:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(32) DEFAULT '' COMMENT '任务名',
  `job_group` varchar(128) DEFAULT '' COMMENT ' 项目.组',
  `rest_url` varchar(128) DEFAULT '' COMMENT 'url地址',
  `deleted` varchar(1) DEFAULT '0' COMMENT '1删除 0 未删除',
  `cron` varchar(20) DEFAULT '' COMMENT 'cron表达式',
  `password` varchar(64) DEFAULT '' COMMENT '任务密码',
  `status` varchar(1) DEFAULT '1' COMMENT '1开启  2不开启',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of job
-- ----------------------------

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `method` varchar(128) DEFAULT '' COMMENT '执行方法',
  `param` varchar(255) DEFAULT '' COMMENT '参数json',
  `result` varchar(255) DEFAULT NULL,
  `ope_time` datetime DEFAULT NULL COMMENT '执行时间',
  `exp_time` bigint(20) DEFAULT '0' COMMENT '消耗时间',
  `operator` varchar(32) DEFAULT '' COMMENT '操作用户：根据token解析',
  `url` varchar(128) DEFAULT '' COMMENT '请求地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT '' COMMENT '资源名称',
  `icon_web` varchar(128) DEFAULT '',
  `icon_mob` varchar(128) DEFAULT '',
  `available_devices` varchar(3) DEFAULT '111' COMMENT '可用设备 ***  1代表可用0不可用  web，安卓，ios',
  `level` varchar(6) DEFAULT '' COMMENT '排序 升序',
  `parent_id` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module
-- ----------------------------
INSERT INTO `module` VALUES ('1', '用户模块', null, null, '111', '1', '0');

-- ----------------------------
-- Table structure for right
-- ----------------------------
DROP TABLE IF EXISTS `right`;
CREATE TABLE `right` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `right_name` varchar(64) DEFAULT '' COMMENT '权限名称 -使用访问控制',
  `module_id` bigint(20) DEFAULT NULL COMMENT '对应资源编号',
  `available_devices` varchar(3) DEFAULT '' COMMENT '可用设备 ***  1代表可用0不可用  web，安卓，ios',
  `url` varchar(128) DEFAULT '' COMMENT '返回客户端的操作',
  `right_txt` varchar(16) DEFAULT '' COMMENT '返回客户端操作的名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of right
-- ----------------------------
INSERT INTO `right` VALUES ('10', 'user_add', '1', '111', '/user', '新增');
INSERT INTO `right` VALUES ('11', 'user_deleted', '1', '111', '/user', '删除');
INSERT INTO `right` VALUES ('12', 'user_update', '1', '111', '/user', '修改');
INSERT INTO `right` VALUES ('13', 'user_info', '1', '111', '/user', '信息');
INSERT INTO `right` VALUES ('14', 'user_page', '1', '111', '/user/page/', '分页');
INSERT INTO `right` VALUES ('15', 'user_all', '1', '111', '/user', '查询');
INSERT INTO `right` VALUES ('16', 'role_add', '2', '111', '/role', '新增');
INSERT INTO `right` VALUES ('17', 'role_deleted', '2', '111', '/role', '删除');
INSERT INTO `right` VALUES ('18', 'role_update', '2', '111', '/role', '修改');
INSERT INTO `right` VALUES ('19', 'role_info', '2', '111', '/role', '信息');
INSERT INTO `right` VALUES ('20', 'role_page', '2', '111', '/role/page/', '分页');
INSERT INTO `right` VALUES ('21', 'role_all', '2', '111', '/role', '查询');
INSERT INTO `right` VALUES ('22', 'role_perms', '2', '111', '/role/perms/', '分配权限');
INSERT INTO `right` VALUES ('23', 'right_add', '3', '111', '/right', '新增');
INSERT INTO `right` VALUES ('24', 'right_deleted', '3', '111', '/right', '删除');
INSERT INTO `right` VALUES ('25', 'right_update', '3', '111', '/right', '修改');
INSERT INTO `right` VALUES ('26', 'right_info', '3', '111', '/right', '信息');
INSERT INTO `right` VALUES ('27', 'right_page', '3', '111', '/right/page/', '分页');
INSERT INTO `right` VALUES ('28', 'right_all', '3', '111', '/right', '查询');
INSERT INTO `right` VALUES ('29', 'roleRight_add', '4', '111', '/roleRight', '新增');
INSERT INTO `right` VALUES ('30', 'roleRight_deleted', '4', '111', '/roleRight', '删除');
INSERT INTO `right` VALUES ('31', 'roleRight_update', '4', '111', '/roleRight', '修改');
INSERT INTO `right` VALUES ('32', 'roleRight_info', '4', '111', '/roleRight', '信息');
INSERT INTO `right` VALUES ('33', 'roleRight_page', '4', '111', '/roleRight/page/', '分页');
INSERT INTO `right` VALUES ('34', 'roleRight_all', '4', '111', '/roleRight', '查询');
INSERT INTO `right` VALUES ('35', 'log_add', '5', '111', '/log', '新增');
INSERT INTO `right` VALUES ('36', 'log_deleted', '5', '111', '/log', '删除');
INSERT INTO `right` VALUES ('37', 'log_update', '5', '111', '/log', '修改');
INSERT INTO `right` VALUES ('38', 'log_info', '5', '111', '/log', '信息');
INSERT INTO `right` VALUES ('39', 'log_page', '5', '111', '/log/page/', '分页');
INSERT INTO `right` VALUES ('40', 'log_all', '5', '111', '/log', '查询');
INSERT INTO `right` VALUES ('41', 'user_updpassword', '1', '111', '/user/password', '修改密码');
INSERT INTO `right` VALUES ('42', 'job_add', '6', '111', '/job', '新增');
INSERT INTO `right` VALUES ('43', 'job_deleted', '6', '111', '/job', '删除');
INSERT INTO `right` VALUES ('44', 'job_update', '6', '111', '/job', '修改');
INSERT INTO `right` VALUES ('45', 'job_info', '6', '111', '/job', '信息');
INSERT INTO `right` VALUES ('46', 'job_page', '6', '111', '/job/page/', '分页');
INSERT INTO `right` VALUES ('47', 'job_all', '6', '111', '/job', '查询');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) DEFAULT '' COMMENT '角色名',
  `intro` varchar(128) DEFAULT '' COMMENT '简介',
  `deleted` varchar(1) DEFAULT '0' COMMENT '删除状态  1：已删除  0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'ADMIN', '管理员', '0');

-- ----------------------------
-- Table structure for role_right
-- ----------------------------
DROP TABLE IF EXISTS `role_right`;
CREATE TABLE `role_right` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `right_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_right
-- ----------------------------
INSERT INTO `role_right` VALUES ('28', '10', '1');
INSERT INTO `role_right` VALUES ('29', '11', '1');
INSERT INTO `role_right` VALUES ('30', '12', '1');
INSERT INTO `role_right` VALUES ('31', '13', '1');
INSERT INTO `role_right` VALUES ('32', '14', '1');
INSERT INTO `role_right` VALUES ('33', '15', '1');
INSERT INTO `role_right` VALUES ('34', '16', '1');
INSERT INTO `role_right` VALUES ('35', '17', '1');
INSERT INTO `role_right` VALUES ('36', '18', '1');
INSERT INTO `role_right` VALUES ('37', '19', '1');
INSERT INTO `role_right` VALUES ('38', '20', '1');
INSERT INTO `role_right` VALUES ('39', '21', '1');
INSERT INTO `role_right` VALUES ('40', '22', '1');
INSERT INTO `role_right` VALUES ('41', '23', '1');
INSERT INTO `role_right` VALUES ('42', '24', '1');
INSERT INTO `role_right` VALUES ('43', '25', '1');
INSERT INTO `role_right` VALUES ('44', '26', '1');
INSERT INTO `role_right` VALUES ('45', '27', '1');
INSERT INTO `role_right` VALUES ('46', '28', '1');
INSERT INTO `role_right` VALUES ('47', '29', '1');
INSERT INTO `role_right` VALUES ('48', '30', '1');
INSERT INTO `role_right` VALUES ('49', '31', '1');
INSERT INTO `role_right` VALUES ('50', '32', '1');
INSERT INTO `role_right` VALUES ('51', '33', '1');
INSERT INTO `role_right` VALUES ('52', '34', '1');
INSERT INTO `role_right` VALUES ('53', '35', '1');
INSERT INTO `role_right` VALUES ('54', '36', '1');
INSERT INTO `role_right` VALUES ('55', '37', '1');
INSERT INTO `role_right` VALUES ('56', '38', '1');
INSERT INTO `role_right` VALUES ('57', '39', '1');
INSERT INTO `role_right` VALUES ('58', '40', '1');
INSERT INTO `role_right` VALUES ('67', '41', '1');
INSERT INTO `role_right` VALUES ('68', '42', '1');
INSERT INTO `role_right` VALUES ('69', '43', '1');
INSERT INTO `role_right` VALUES ('70', '44', '1');
INSERT INTO `role_right` VALUES ('71', '45', '1');
INSERT INTO `role_right` VALUES ('72', '46', '1');
INSERT INTO `role_right` VALUES ('73', '47', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL DEFAULT '' COMMENT '用户名',
  `realname` varchar(32) DEFAULT '' COMMENT '真实姓名',
  `password` varchar(64) DEFAULT '' COMMENT '用户名：密码 MD5',
  `access_token` longtext,
  `status` varchar(1) DEFAULT '1' COMMENT '用户状态 1：正常 ；0: 锁定',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '管理员', '$2a$10$tzcdD3jPxWqR3dPJrhsHo.nJhJgsFLlGJP47ZnDkdHh8xUEy/dzVq', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUxMzgyMjU0OCwiYXV0aHMiOiJbUk9MRV91c2VyX2FkZCwgUk9MRV91c2VyX2RlbGV0ZWQsIFJPTEVfdXNlcl91cGRhdGUsIFJPTEVfdXNlcl9pbmZvLCBST0xFX3VzZXJfcGFnZSwgUk9MRV91c2VyX2FsbCwgUk9MRV9yb2xlX2FkZCwgUk9MRV9yb2xlX2RlbGV0ZWQsIFJPTEVfcm9sZV91cGRhdGUsIFJPTEVfcm9sZV9pbmZvLCBST0xFX3JvbGVfcGFnZSwgUk9MRV9yb2xlX2FsbCwgUk9MRV9yb2xlX3Blcm1zLCBST0xFX3JpZ2h0X2FkZCwgUk9MRV9yaWdodF9kZWxldGVkLCBST0xFX3JpZ2h0X3VwZGF0ZSwgUk9MRV9yaWdodF9pbmZvLCBST0xFX3JpZ2h0X3BhZ2UsIFJPTEVfcmlnaHRfYWxsLCBST0xFX3JvbGVSaWdodF9hZGQsIFJPTEVfcm9sZVJpZ2h0X2RlbGV0ZWQsIFJPTEVfcm9sZVJpZ2h0X3VwZGF0ZSwgUk9MRV9yb2xlUmlnaHRfaW5mbywgUk9MRV9yb2xlUmlnaHRfcGFnZSwgUk9MRV9yb2xlUmlnaHRfYWxsLCBST0xFX2xvZ19hZGQsIFJPTEVfbG9nX2RlbGV0ZWQsIFJPTEVfbG9nX3VwZGF0ZSwgUk9MRV9sb2dfaW5mbywgUk9MRV9sb2dfcGFnZSwgUk9MRV9sb2dfYWxsLCBST0xFX3VzZXJfdXBkcGFzc3dvcmQsIFJPTEVfam9iX2FkZCwgUk9MRV9qb2JfZGVsZXRlZCwgUk9MRV9qb2JfdXBkYXRlLCBST0xFX2pvYl9pbmZvLCBST0xFX2pvYl9wYWdlLCBST0xFX2pvYl9hbGxdIn0.n43AQLnunjppJnz0htQqJcyJNiwTXl1wBv62k2jUOUyoPCwEC1kMWNdMlnv39xTf6pEEmicPXseCT94qXeVgGA', '1');

-- ----------------------------
-- Table structure for user_right_limit
-- ----------------------------
DROP TABLE IF EXISTS `user_right_limit`;
CREATE TABLE `user_right_limit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `right_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `limited` varchar(1) DEFAULT '0' COMMENT '1禁止 0允许',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='该表存储用户命令被禁止/允许的权限。';

-- ----------------------------
-- Records of user_right_limit
-- ----------------------------
INSERT INTO `user_right_limit` VALUES ('1', '1', '2', '1');
INSERT INTO `user_right_limit` VALUES ('2', '2', '2', '0');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
