/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : jy

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2018-05-02 08:29:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_data_warning_config
-- ----------------------------
DROP TABLE IF EXISTS `t_data_warning_config`;
CREATE TABLE `t_data_warning_config` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `equipment_id` bigint(10) DEFAULT '0',
  `min` decimal(10,0) DEFAULT '0',
  `max` decimal(10,0) DEFAULT '0',
  `method` varchar(4) DEFAULT '默认' COMMENT '页面通知/短信通知/默认 默认为两种方式',
  `status` varchar(2) DEFAULT '启用' COMMENT '启用/禁用',
  `content` varchar(255) DEFAULT '' COMMENT '报警内容',
  `to` bigint(10) DEFAULT '0' COMMENT '通知人',
  `last_exec` datetime DEFAULT '2018-01-01 00:00:00' COMMENT '最近一次执行时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据报警设置';

-- ----------------------------
-- Records of t_data_warning_config
-- ----------------------------

-- ----------------------------
-- Table structure for t_data_warning_info
-- ----------------------------
DROP TABLE IF EXISTS `t_data_warning_info`;
CREATE TABLE `t_data_warning_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `min` decimal(10,0) DEFAULT '0' COMMENT '配置时的最小值',
  `max` decimal(10,0) DEFAULT '0',
  `data` decimal(10,0) DEFAULT '0' COMMENT '数据',
  `exec_time` datetime DEFAULT NULL COMMENT '执行时间',
  `content` varchar(255) DEFAULT '' COMMENT '内容',
  `method` varchar(4) DEFAULT '默认' COMMENT '页面通知/短信通知/默认 默认为两种方式',
  `to` bigint(10) DEFAULT '0' COMMENT '通知人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='由于配置表可以更改所有添加了min ，max \r\n为匹配到的配置表中的数据。';

-- ----------------------------
-- Records of t_data_warning_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) DEFAULT '' COMMENT '部门名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dept
-- ----------------------------
INSERT INTO `t_dept` VALUES ('1', '总经理');
INSERT INTO `t_dept` VALUES ('2', '商务部');
INSERT INTO `t_dept` VALUES ('3', '销售部');

-- ----------------------------
-- Table structure for t_factory
-- ----------------------------
DROP TABLE IF EXISTS `t_factory`;
CREATE TABLE `t_factory` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `bh` varchar(32) DEFAULT '' COMMENT '编号',
  `name` varchar(64) DEFAULT '' COMMENT '工厂名称',
  `addr` varchar(64) DEFAULT '' COMMENT '地址',
  `phone` varchar(64) DEFAULT NULL,
  `owner` bigint(10) DEFAULT NULL COMMENT '负责人-关联用户表',
  `start_day` varchar(10) DEFAULT '' COMMENT '开始日期',
  `end_day` varchar(10) DEFAULT '' COMMENT '结束日期',
  `max` int(3) DEFAULT '0' COMMENT '最大用户数',
  `status` varchar(16) DEFAULT '' COMMENT '正常、到期、禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_factory
-- ----------------------------
INSERT INTO `t_factory` VALUES ('1', '121', '对赌帝都', '莲湖', '110', '31', '2018-03-17', '2020-12-30', '15', '正常');
INSERT INTO `t_factory` VALUES ('2', '102', '美凯龙', '路口11', '112', '24', '2018-03-28', '2020-03-31', '6', '正常');
INSERT INTO `t_factory` VALUES ('4', '1003', '疼训1', '深圳1', '199', '25', '2018-03-20', '2018-04-24', '30', '正常');

-- ----------------------------
-- Table structure for t_job
-- ----------------------------
DROP TABLE IF EXISTS `t_job`;
CREATE TABLE `t_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(32) DEFAULT '' COMMENT '任务名',
  `job_group` varchar(128) DEFAULT '' COMMENT ' 项目.组',
  `rest_url` varchar(128) DEFAULT '' COMMENT 'url地址',
  `deleted` varchar(1) DEFAULT '0' COMMENT '1删除 0 未删除',
  `cron` varchar(20) DEFAULT '' COMMENT 'cron表达式',
  `password` varchar(64) DEFAULT '' COMMENT '任务密码',
  `status` varchar(1) DEFAULT '1' COMMENT '1开启  2不开启',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_job
-- ----------------------------
INSERT INTO `t_job` VALUES ('5', 'ceshi', 'zhny', 'http://192.168.0.166:8090/task/task1', '0', '0 * * * * ?', '$2a$10$n6xMTCOXb61WPxyFZaCUq.VkirLO5faqE7gJuraz3ziUJVRDG01zG', '2');

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `method` varchar(128) DEFAULT '' COMMENT '执行方法',
  `param` varchar(255) DEFAULT '' COMMENT '参数json',
  `result` text,
  `ope_time` datetime DEFAULT NULL COMMENT '执行时间',
  `exp_time` bigint(20) DEFAULT '0' COMMENT '消耗时间',
  `operator` varchar(32) DEFAULT '' COMMENT '操作用户：根据token解析',
  `url` varchar(128) DEFAULT '' COMMENT '请求地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=268 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_maintain_warning_config
-- ----------------------------
DROP TABLE IF EXISTS `t_maintain_warning_config`;
CREATE TABLE `t_maintain_warning_config` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `equipment_id` bigint(10) DEFAULT '0',
  `method` varchar(4) DEFAULT '默认' COMMENT '页面通知/短信通知/默认 默认为两种方式',
  `content` varchar(255) DEFAULT '' COMMENT '通知内容',
  `to` bigint(10) DEFAULT '0' COMMENT '通知人',
  `status` varchar(2) DEFAULT '启用' COMMENT '启用/禁用',
  `last_exec` datetime DEFAULT '2018-01-01 00:00:00' COMMENT '最后执行时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_maintain_warning_config
-- ----------------------------

-- ----------------------------
-- Table structure for t_maintain_warning_info
-- ----------------------------
DROP TABLE IF EXISTS `t_maintain_warning_info`;
CREATE TABLE `t_maintain_warning_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_maintain_warning_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `url` varchar(60) DEFAULT '' COMMENT '页面地址，只有子菜单需要，父级目录不需要填写',
  `txt` varchar(60) DEFAULT '' COMMENT '名称',
  `status` int(1) DEFAULT '1' COMMENT '菜单状态：1开启、2关闭',
  `pid` bigint(10) DEFAULT '0' COMMENT '父级菜单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('2', '', '基础管理', '1', '0');
INSERT INTO `t_menu` VALUES ('3', '/user', '用户管理', '1', '2');
INSERT INTO `t_menu` VALUES ('4', '/role', '角色管理', '1', '2');
INSERT INTO `t_menu` VALUES ('5', '/right', '权限管理', '1', '2');
INSERT INTO `t_menu` VALUES ('6', '/menu', '菜单管理', '1', '2');
INSERT INTO `t_menu` VALUES ('7', '/log', '操作日志', '1', '2');
INSERT INTO `t_menu` VALUES ('8', '/job', '任务调度中心', '1', '2');
INSERT INTO `t_menu` VALUES ('9', '', '我的工作台', '1', '0');
INSERT INTO `t_menu` VALUES ('12', '/flow/todotasks', '待办任务', '1', '9');
INSERT INTO `t_menu` VALUES ('13', '', '商机管理', '1', '9');
INSERT INTO `t_menu` VALUES ('14', '/shangji/geren', '个人商机', '1', '9');
INSERT INTO `t_menu` VALUES ('15', '/shangji/gongshi', '已公示', '1', '9');
INSERT INTO `t_menu` VALUES ('16', '/login', '用户中心', '1', '0');
INSERT INTO `t_menu` VALUES ('17', '/factory', '工厂管理', '1', '2');
INSERT INTO `t_menu` VALUES ('18', '/equipment', '设备管理', '1', '2');
INSERT INTO `t_menu` VALUES ('19', '/collection', '数据采集', '1', '2');
INSERT INTO `t_menu` VALUES ('20', '/police', '报警设置', '1', '2');
INSERT INTO `t_menu` VALUES ('21', '/notice', '报警通知', '1', '2');
INSERT INTO `t_menu` VALUES ('22', '/chart', '图表展示', '1', '2');
INSERT INTO `t_menu` VALUES ('23', '/statement', '数据报表', '1', '2');
INSERT INTO `t_menu` VALUES ('24', '/video', '视频监控', '1', '2');
INSERT INTO `t_menu` VALUES ('25', '/password', '修改密码', '1', '16');

-- ----------------------------
-- Table structure for t_module
-- ----------------------------
DROP TABLE IF EXISTS `t_module`;
CREATE TABLE `t_module` (
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
-- Records of t_module
-- ----------------------------
INSERT INTO `t_module` VALUES ('1', '用户模块', null, null, '111', '1', '0');

-- ----------------------------
-- Table structure for t_registration_code
-- ----------------------------
DROP TABLE IF EXISTS `t_registration_code`;
CREATE TABLE `t_registration_code` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) DEFAULT '' COMMENT '注册码',
  `status` varchar(16) DEFAULT '未使用' COMMENT '注册码状态：已使用；未使用',
  `factory_id` bigint(10) DEFAULT NULL COMMENT '工厂',
  PRIMARY KEY (`id`),
  UNIQUE KEY `regis_code_u` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_registration_code
-- ----------------------------
INSERT INTO `t_registration_code` VALUES ('31', 'olvi1oq3c4', '已使用', '1');
INSERT INTO `t_registration_code` VALUES ('32', 'cjmv1c0bc2', '已使用', '1');
INSERT INTO `t_registration_code` VALUES ('33', 'd9ftl8srcg', '未使用', '1');
INSERT INTO `t_registration_code` VALUES ('34', '4h1r4r923g', '未使用', '1');
INSERT INTO `t_registration_code` VALUES ('35', 'vrppmeu8i1', '未使用', '1');
INSERT INTO `t_registration_code` VALUES ('36', 'e0idu3prsb', '未使用', '1');
INSERT INTO `t_registration_code` VALUES ('37', 'c0tiql64bc', '未使用', '1');
INSERT INTO `t_registration_code` VALUES ('38', '36mo2or66s', '未使用', '1');
INSERT INTO `t_registration_code` VALUES ('39', 'kc48e8hva7', '未使用', '1');
INSERT INTO `t_registration_code` VALUES ('40', 'rfrj87fmlf', '未使用', '1');
INSERT INTO `t_registration_code` VALUES ('81', '0tbne48e4n', '未使用', '1');
INSERT INTO `t_registration_code` VALUES ('82', '4dgaj4vja7', '未使用', '1');
INSERT INTO `t_registration_code` VALUES ('83', 'pn33gq7lff', '未使用', '1');
INSERT INTO `t_registration_code` VALUES ('84', 'qrnldo7lsf', '未使用', '1');
INSERT INTO `t_registration_code` VALUES ('85', '90ol3m3gkl', '未使用', '1');
INSERT INTO `t_registration_code` VALUES ('101', '9t3r0gbviq', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('102', 'bta97ss5ie', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('103', 'h722likv3n', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('104', 'j2pg5tv3md', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('105', 'dv7nkt86mt', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('106', 'aqhr30d079', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('107', 'fbiknp28r2', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('108', 'rvlr0v56lk', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('109', 'bgpgnvfbed', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('110', '4iflf20mq7', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('111', 'ap5cst947v', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('112', 'ja3ekdnd4v', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('113', 'an6b5hp1lb', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('114', 'hq6tvk5qmi', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('115', '8tgsit962v', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('116', 'kalg542ilm', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('117', 'e83b2hba5q', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('118', 'l2ml4m6r6o', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('119', 'et4q2lakjq', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('120', 'jl26j3tnft', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('121', 'srj6doug9p', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('122', 'i2hirkkt1i', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('123', 's5befj18q0', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('124', 'iooah38gii', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('125', 'jsvm8pgmpv', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('126', 'ja01822als', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('127', 'tkon9pe44v', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('128', 'rmtlfs1b0g', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('129', 'd1502f7jjd', '未使用', '4');
INSERT INTO `t_registration_code` VALUES ('130', '4gbina0d6t', '未使用', '4');

-- ----------------------------
-- Table structure for t_right
-- ----------------------------
DROP TABLE IF EXISTS `t_right`;
CREATE TABLE `t_right` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `right_name` varchar(64) DEFAULT '' COMMENT '权限名称 -使用访问控制',
  `module_id` bigint(20) DEFAULT NULL COMMENT '对应资源编号',
  `available_devices` varchar(3) DEFAULT '' COMMENT '可用设备 ***  1代表可用0不可用  web，安卓，ios',
  `url` varchar(128) DEFAULT '' COMMENT '返回客户端的操作',
  `right_txt` varchar(16) DEFAULT '' COMMENT '返回客户端操作的名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_right
-- ----------------------------
INSERT INTO `t_right` VALUES ('10', 'user_add', '3', '111', '/user', '新增');
INSERT INTO `t_right` VALUES ('11', 'user_deleted', '3', '111', '/user', '删除');
INSERT INTO `t_right` VALUES ('12', 'user_update', '3', '111', '/user', '修改');
INSERT INTO `t_right` VALUES ('13', 'user_info', '3', '111', '/user', '信息');
INSERT INTO `t_right` VALUES ('14', 'user_page', '3', '111', '/user/page/', '分页');
INSERT INTO `t_right` VALUES ('15', 'user_all', '3', '111', '/user', '查询');
INSERT INTO `t_right` VALUES ('16', 'role_add', '4', '111', '/role', '新增');
INSERT INTO `t_right` VALUES ('17', 'role_deleted', '4', '111', '/role', '删除');
INSERT INTO `t_right` VALUES ('18', 'role_update', '4', '111', '/role', '修改');
INSERT INTO `t_right` VALUES ('19', 'role_info', '4', '111', '/role', '信息');
INSERT INTO `t_right` VALUES ('20', 'role_page', '4', '111', '/role/page/', '分页');
INSERT INTO `t_right` VALUES ('21', 'role_all', '4', '111', '/role', '查询');
INSERT INTO `t_right` VALUES ('22', 'role_perms', '4', '111', '/role/perms/', '分配权限');
INSERT INTO `t_right` VALUES ('23', 'right_add', '5', '111', '/right', '新增');
INSERT INTO `t_right` VALUES ('24', 'right_deleted', '5', '111', '/right', '删除');
INSERT INTO `t_right` VALUES ('25', 'right_update', '5', '111', '/right', '修改');
INSERT INTO `t_right` VALUES ('26', 'right_info', '5', '111', '/right', '信息');
INSERT INTO `t_right` VALUES ('27', 'right_page', '5', '111', '/right/page/', '分页');
INSERT INTO `t_right` VALUES ('28', 'right_all', '5', '111', '/right', '查询');
INSERT INTO `t_right` VALUES ('29', 'roleRight_add', '100', '111', '/roleRight', '新增');
INSERT INTO `t_right` VALUES ('30', 'roleRight_deleted', '100', '111', '/roleRight', '删除');
INSERT INTO `t_right` VALUES ('31', 'roleRight_update', '100', '111', '/roleRight', '修改');
INSERT INTO `t_right` VALUES ('32', 'roleRight_info', '100', '111', '/roleRight', '信息');
INSERT INTO `t_right` VALUES ('33', 'roleRight_page', '100', '111', '/roleRight/page/', '分页');
INSERT INTO `t_right` VALUES ('34', 'roleRight_all', '100', '111', '/roleRight', '查询');
INSERT INTO `t_right` VALUES ('35', 'log_add', '7', '111', '/log', '新增');
INSERT INTO `t_right` VALUES ('36', 'log_deleted', '7', '111', '/log', '删除');
INSERT INTO `t_right` VALUES ('37', 'log_update', '7', '111', '/log', '修改');
INSERT INTO `t_right` VALUES ('38', 'log_info', '7', '111', '/log', '信息');
INSERT INTO `t_right` VALUES ('39', 'log_page', '7', '111', '/log/page/', '分页');
INSERT INTO `t_right` VALUES ('40', 'log_all', '7', '111', '/log', '查询');
INSERT INTO `t_right` VALUES ('41', 'user_updpassword', '3', '111', '/user/password', '修改密码');
INSERT INTO `t_right` VALUES ('42', 'job_add', '8', '111', '/job', '新增');
INSERT INTO `t_right` VALUES ('43', 'job_deleted', '8', '111', '/job', '删除');
INSERT INTO `t_right` VALUES ('44', 'job_update', '8', '111', '/job', '修改');
INSERT INTO `t_right` VALUES ('45', 'job_info', '8', '111', '/job', '信息');
INSERT INTO `t_right` VALUES ('46', 'job_page', '8', '111', '/job/page/', '分页');
INSERT INTO `t_right` VALUES ('47', 'job_all', '8', '111', '/job', '查询');
INSERT INTO `t_right` VALUES ('49', 'shangjiInfo_add', '7', '111', '/shangjiInfo', '新增');
INSERT INTO `t_right` VALUES ('50', 'shangjiInfo_deleted', '7', '111', '/shangjiInfo', '删除');
INSERT INTO `t_right` VALUES ('51', 'shangjiInfo_update', '7', '111', '/shangjiInfo', '修改');
INSERT INTO `t_right` VALUES ('52', 'shangjiInfo_info', '7', '111', '/shangjiInfo', '信息');
INSERT INTO `t_right` VALUES ('53', 'shangjiInfo_page', '7', '111', '/shangjiInfo/page/', '分页');
INSERT INTO `t_right` VALUES ('54', 'shangjiInfo_all', '7', '111', '/shangjiInfo', '查询');
INSERT INTO `t_right` VALUES ('55', 'flow_todotasks_page', '8', '111', '/flow/todotasks/page/', '分页');
INSERT INTO `t_right` VALUES ('56', 'processImage', '8', '111', '/flow/processImage', '流程图');
INSERT INTO `t_right` VALUES ('57', 'flow_claim', '8', '111', '/flow/claim', '签收');
INSERT INTO `t_right` VALUES ('58', 'flow_readform', '8', '111', '/flow/readform', '任务参数');
INSERT INTO `t_right` VALUES ('59', 'menu_page', '6', '111', '/menu/page/', '分页');
INSERT INTO `t_right` VALUES ('60', 'menu_add', '6', '111', '/menu', '新增');
INSERT INTO `t_right` VALUES ('61', 'menu_deleted', '6', '111', '/menu', '删除');
INSERT INTO `t_right` VALUES ('62', 'menu_update', '6', '111', '/menu', '修改');
INSERT INTO `t_right` VALUES ('63', 'menu_info', '6', '111', '/menu', '信息');
INSERT INTO `t_right` VALUES ('64', 'menu_all', '6', '111', '/menu', '查询');
INSERT INTO `t_right` VALUES ('65', 'factory_add', '17', '111', '/factory', '新增');
INSERT INTO `t_right` VALUES ('66', 'factory_deleted', '17', '111', '/factory', '删除');
INSERT INTO `t_right` VALUES ('67', 'factory_update', '17', '111', '/factory', '修改');
INSERT INTO `t_right` VALUES ('68', 'factory_info', '17', '111', '/factory', '信息');
INSERT INTO `t_right` VALUES ('69', 'factory_page', '17', '111', '/factory/page/', '分页');
INSERT INTO `t_right` VALUES ('70', 'factory_all', '17', '111', '/factory', '查询');
INSERT INTO `t_right` VALUES ('71', 'upload', '10', '111', '/upload', '上传');
INSERT INTO `t_right` VALUES ('72', 'registrationCode_add', '99', '111', '/registrationCode', '新增');
INSERT INTO `t_right` VALUES ('73', 'registrationCode_deleted', '99', '111', '/registrationCode', '删除');
INSERT INTO `t_right` VALUES ('74', 'registrationCode_update', '99', '111', '/registrationCode', '修改');
INSERT INTO `t_right` VALUES ('75', 'registrationCode_info', '99', '111', '/registrationCode', '信息');
INSERT INTO `t_right` VALUES ('76', 'registrationCode_page', '99', '111', '/registrationCode/page/', '分页');
INSERT INTO `t_right` VALUES ('77', 'registrationCode_all', '99', '111', '/registrationCode', '查询');
INSERT INTO `t_right` VALUES ('78', 'job_diaodu', '8', '111', '/job/pause', '调度');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) DEFAULT '' COMMENT '角色名',
  `intro` varchar(128) DEFAULT '' COMMENT '简介',
  `deleted` varchar(1) DEFAULT '0' COMMENT '删除状态  1：已删除  0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'ADMIN', '管理员', '0');
INSERT INTO `t_role` VALUES ('3', 'factory', '工厂用户', '0');
INSERT INTO `t_role` VALUES ('4', 'NORMAL', '普通员工1', '0');
INSERT INTO `t_role` VALUES ('5', 'company', '企业用户', '0');

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `role_id` bigint(10) DEFAULT '0',
  `menu_id` bigint(10) DEFAULT '0',
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=313 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES ('5', '17', '234');
INSERT INTO `t_role_menu` VALUES ('5', '4', '235');
INSERT INTO `t_role_menu` VALUES ('5', '3', '236');
INSERT INTO `t_role_menu` VALUES ('5', '5', '237');
INSERT INTO `t_role_menu` VALUES ('5', '18', '238');
INSERT INTO `t_role_menu` VALUES ('5', '19', '239');
INSERT INTO `t_role_menu` VALUES ('5', '20', '240');
INSERT INTO `t_role_menu` VALUES ('5', '21', '241');
INSERT INTO `t_role_menu` VALUES ('5', '22', '242');
INSERT INTO `t_role_menu` VALUES ('5', '23', '243');
INSERT INTO `t_role_menu` VALUES ('5', '24', '244');
INSERT INTO `t_role_menu` VALUES ('5', '16', '245');
INSERT INTO `t_role_menu` VALUES ('5', '2', '246');
INSERT INTO `t_role_menu` VALUES ('1', '2', '281');
INSERT INTO `t_role_menu` VALUES ('1', '3', '282');
INSERT INTO `t_role_menu` VALUES ('1', '4', '283');
INSERT INTO `t_role_menu` VALUES ('1', '5', '284');
INSERT INTO `t_role_menu` VALUES ('1', '6', '285');
INSERT INTO `t_role_menu` VALUES ('1', '7', '286');
INSERT INTO `t_role_menu` VALUES ('1', '8', '287');
INSERT INTO `t_role_menu` VALUES ('1', '9', '288');
INSERT INTO `t_role_menu` VALUES ('1', '12', '289');
INSERT INTO `t_role_menu` VALUES ('1', '16', '290');
INSERT INTO `t_role_menu` VALUES ('1', '17', '291');
INSERT INTO `t_role_menu` VALUES ('3', '2', '302');
INSERT INTO `t_role_menu` VALUES ('3', '3', '303');
INSERT INTO `t_role_menu` VALUES ('3', '17', '304');
INSERT INTO `t_role_menu` VALUES ('3', '18', '305');
INSERT INTO `t_role_menu` VALUES ('3', '19', '306');
INSERT INTO `t_role_menu` VALUES ('3', '20', '307');
INSERT INTO `t_role_menu` VALUES ('3', '21', '308');
INSERT INTO `t_role_menu` VALUES ('3', '22', '309');
INSERT INTO `t_role_menu` VALUES ('3', '23', '310');
INSERT INTO `t_role_menu` VALUES ('3', '24', '311');
INSERT INTO `t_role_menu` VALUES ('3', '16', '312');

-- ----------------------------
-- Table structure for t_role_right
-- ----------------------------
DROP TABLE IF EXISTS `t_role_right`;
CREATE TABLE `t_role_right` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `right_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2370 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_right
-- ----------------------------
INSERT INTO `t_role_right` VALUES ('1940', '10', '5');
INSERT INTO `t_role_right` VALUES ('1941', '11', '5');
INSERT INTO `t_role_right` VALUES ('1942', '12', '5');
INSERT INTO `t_role_right` VALUES ('1943', '13', '5');
INSERT INTO `t_role_right` VALUES ('1944', '14', '5');
INSERT INTO `t_role_right` VALUES ('1945', '15', '5');
INSERT INTO `t_role_right` VALUES ('1946', '19', '5');
INSERT INTO `t_role_right` VALUES ('1947', '20', '5');
INSERT INTO `t_role_right` VALUES ('1948', '18', '5');
INSERT INTO `t_role_right` VALUES ('1949', '17', '5');
INSERT INTO `t_role_right` VALUES ('1950', '41', '5');
INSERT INTO `t_role_right` VALUES ('1951', '21', '5');
INSERT INTO `t_role_right` VALUES ('1952', '22', '5');
INSERT INTO `t_role_right` VALUES ('1953', '23', '5');
INSERT INTO `t_role_right` VALUES ('1954', '25', '5');
INSERT INTO `t_role_right` VALUES ('1955', '26', '5');
INSERT INTO `t_role_right` VALUES ('1956', '61', '5');
INSERT INTO `t_role_right` VALUES ('1957', '60', '5');
INSERT INTO `t_role_right` VALUES ('1958', '24', '5');
INSERT INTO `t_role_right` VALUES ('1959', '62', '5');
INSERT INTO `t_role_right` VALUES ('1960', '59', '5');
INSERT INTO `t_role_right` VALUES ('1961', '28', '5');
INSERT INTO `t_role_right` VALUES ('1962', '27', '5');
INSERT INTO `t_role_right` VALUES ('1963', '63', '5');
INSERT INTO `t_role_right` VALUES ('1964', '16', '5');
INSERT INTO `t_role_right` VALUES ('1965', '64', '5');
INSERT INTO `t_role_right` VALUES ('1966', '36', '5');
INSERT INTO `t_role_right` VALUES ('1967', '37', '5');
INSERT INTO `t_role_right` VALUES ('1968', '38', '5');
INSERT INTO `t_role_right` VALUES ('1969', '35', '5');
INSERT INTO `t_role_right` VALUES ('1970', '39', '5');
INSERT INTO `t_role_right` VALUES ('1971', '40', '5');
INSERT INTO `t_role_right` VALUES ('1972', '49', '5');
INSERT INTO `t_role_right` VALUES ('1973', '50', '5');
INSERT INTO `t_role_right` VALUES ('1974', '51', '5');
INSERT INTO `t_role_right` VALUES ('1975', '43', '5');
INSERT INTO `t_role_right` VALUES ('1976', '42', '5');
INSERT INTO `t_role_right` VALUES ('1977', '54', '5');
INSERT INTO `t_role_right` VALUES ('1978', '53', '5');
INSERT INTO `t_role_right` VALUES ('1979', '52', '5');
INSERT INTO `t_role_right` VALUES ('1980', '44', '5');
INSERT INTO `t_role_right` VALUES ('1981', '45', '5');
INSERT INTO `t_role_right` VALUES ('1982', '46', '5');
INSERT INTO `t_role_right` VALUES ('1983', '47', '5');
INSERT INTO `t_role_right` VALUES ('1984', '55', '5');
INSERT INTO `t_role_right` VALUES ('1985', '56', '5');
INSERT INTO `t_role_right` VALUES ('1986', '66', '5');
INSERT INTO `t_role_right` VALUES ('1987', '65', '5');
INSERT INTO `t_role_right` VALUES ('1988', '71', '5');
INSERT INTO `t_role_right` VALUES ('1989', '57', '5');
INSERT INTO `t_role_right` VALUES ('1990', '67', '5');
INSERT INTO `t_role_right` VALUES ('1991', '58', '5');
INSERT INTO `t_role_right` VALUES ('1992', '69', '5');
INSERT INTO `t_role_right` VALUES ('1993', '70', '5');
INSERT INTO `t_role_right` VALUES ('1994', '78', '5');
INSERT INTO `t_role_right` VALUES ('1995', '68', '5');
INSERT INTO `t_role_right` VALUES ('1996', '72', '5');
INSERT INTO `t_role_right` VALUES ('1997', '76', '5');
INSERT INTO `t_role_right` VALUES ('1998', '32', '5');
INSERT INTO `t_role_right` VALUES ('1999', '31', '5');
INSERT INTO `t_role_right` VALUES ('2000', '75', '5');
INSERT INTO `t_role_right` VALUES ('2001', '30', '5');
INSERT INTO `t_role_right` VALUES ('2002', '74', '5');
INSERT INTO `t_role_right` VALUES ('2003', '29', '5');
INSERT INTO `t_role_right` VALUES ('2004', '77', '5');
INSERT INTO `t_role_right` VALUES ('2005', '73', '5');
INSERT INTO `t_role_right` VALUES ('2006', '33', '5');
INSERT INTO `t_role_right` VALUES ('2007', '34', '5');
INSERT INTO `t_role_right` VALUES ('2184', '10', '1');
INSERT INTO `t_role_right` VALUES ('2185', '41', '1');
INSERT INTO `t_role_right` VALUES ('2186', '15', '1');
INSERT INTO `t_role_right` VALUES ('2187', '14', '1');
INSERT INTO `t_role_right` VALUES ('2188', '13', '1');
INSERT INTO `t_role_right` VALUES ('2189', '11', '1');
INSERT INTO `t_role_right` VALUES ('2190', '12', '1');
INSERT INTO `t_role_right` VALUES ('2191', '16', '1');
INSERT INTO `t_role_right` VALUES ('2192', '22', '1');
INSERT INTO `t_role_right` VALUES ('2193', '21', '1');
INSERT INTO `t_role_right` VALUES ('2194', '19', '1');
INSERT INTO `t_role_right` VALUES ('2195', '18', '1');
INSERT INTO `t_role_right` VALUES ('2196', '17', '1');
INSERT INTO `t_role_right` VALUES ('2197', '20', '1');
INSERT INTO `t_role_right` VALUES ('2198', '28', '1');
INSERT INTO `t_role_right` VALUES ('2199', '27', '1');
INSERT INTO `t_role_right` VALUES ('2200', '25', '1');
INSERT INTO `t_role_right` VALUES ('2201', '26', '1');
INSERT INTO `t_role_right` VALUES ('2202', '23', '1');
INSERT INTO `t_role_right` VALUES ('2203', '24', '1');
INSERT INTO `t_role_right` VALUES ('2204', '64', '1');
INSERT INTO `t_role_right` VALUES ('2205', '59', '1');
INSERT INTO `t_role_right` VALUES ('2206', '60', '1');
INSERT INTO `t_role_right` VALUES ('2207', '61', '1');
INSERT INTO `t_role_right` VALUES ('2208', '62', '1');
INSERT INTO `t_role_right` VALUES ('2209', '63', '1');
INSERT INTO `t_role_right` VALUES ('2210', '52', '1');
INSERT INTO `t_role_right` VALUES ('2211', '51', '1');
INSERT INTO `t_role_right` VALUES ('2212', '50', '1');
INSERT INTO `t_role_right` VALUES ('2213', '49', '1');
INSERT INTO `t_role_right` VALUES ('2214', '53', '1');
INSERT INTO `t_role_right` VALUES ('2215', '54', '1');
INSERT INTO `t_role_right` VALUES ('2216', '35', '1');
INSERT INTO `t_role_right` VALUES ('2217', '36', '1');
INSERT INTO `t_role_right` VALUES ('2218', '40', '1');
INSERT INTO `t_role_right` VALUES ('2219', '37', '1');
INSERT INTO `t_role_right` VALUES ('2220', '38', '1');
INSERT INTO `t_role_right` VALUES ('2221', '39', '1');
INSERT INTO `t_role_right` VALUES ('2222', '56', '1');
INSERT INTO `t_role_right` VALUES ('2223', '57', '1');
INSERT INTO `t_role_right` VALUES ('2224', '58', '1');
INSERT INTO `t_role_right` VALUES ('2225', '78', '1');
INSERT INTO `t_role_right` VALUES ('2226', '55', '1');
INSERT INTO `t_role_right` VALUES ('2227', '42', '1');
INSERT INTO `t_role_right` VALUES ('2228', '44', '1');
INSERT INTO `t_role_right` VALUES ('2229', '43', '1');
INSERT INTO `t_role_right` VALUES ('2230', '45', '1');
INSERT INTO `t_role_right` VALUES ('2231', '46', '1');
INSERT INTO `t_role_right` VALUES ('2232', '47', '1');
INSERT INTO `t_role_right` VALUES ('2233', '71', '1');
INSERT INTO `t_role_right` VALUES ('2234', '69', '1');
INSERT INTO `t_role_right` VALUES ('2235', '68', '1');
INSERT INTO `t_role_right` VALUES ('2236', '67', '1');
INSERT INTO `t_role_right` VALUES ('2237', '66', '1');
INSERT INTO `t_role_right` VALUES ('2238', '65', '1');
INSERT INTO `t_role_right` VALUES ('2239', '70', '1');
INSERT INTO `t_role_right` VALUES ('2240', '72', '1');
INSERT INTO `t_role_right` VALUES ('2241', '77', '1');
INSERT INTO `t_role_right` VALUES ('2242', '76', '1');
INSERT INTO `t_role_right` VALUES ('2243', '75', '1');
INSERT INTO `t_role_right` VALUES ('2244', '74', '1');
INSERT INTO `t_role_right` VALUES ('2245', '73', '1');
INSERT INTO `t_role_right` VALUES ('2246', '29', '1');
INSERT INTO `t_role_right` VALUES ('2247', '34', '1');
INSERT INTO `t_role_right` VALUES ('2248', '33', '1');
INSERT INTO `t_role_right` VALUES ('2249', '31', '1');
INSERT INTO `t_role_right` VALUES ('2250', '30', '1');
INSERT INTO `t_role_right` VALUES ('2251', '32', '1');
INSERT INTO `t_role_right` VALUES ('2311', '10', '3');
INSERT INTO `t_role_right` VALUES ('2312', '41', '3');
INSERT INTO `t_role_right` VALUES ('2313', '15', '3');
INSERT INTO `t_role_right` VALUES ('2314', '14', '3');
INSERT INTO `t_role_right` VALUES ('2315', '13', '3');
INSERT INTO `t_role_right` VALUES ('2316', '11', '3');
INSERT INTO `t_role_right` VALUES ('2317', '12', '3');
INSERT INTO `t_role_right` VALUES ('2318', '16', '3');
INSERT INTO `t_role_right` VALUES ('2319', '22', '3');
INSERT INTO `t_role_right` VALUES ('2320', '21', '3');
INSERT INTO `t_role_right` VALUES ('2321', '19', '3');
INSERT INTO `t_role_right` VALUES ('2322', '18', '3');
INSERT INTO `t_role_right` VALUES ('2323', '17', '3');
INSERT INTO `t_role_right` VALUES ('2324', '20', '3');
INSERT INTO `t_role_right` VALUES ('2325', '28', '3');
INSERT INTO `t_role_right` VALUES ('2326', '27', '3');
INSERT INTO `t_role_right` VALUES ('2327', '25', '3');
INSERT INTO `t_role_right` VALUES ('2328', '26', '3');
INSERT INTO `t_role_right` VALUES ('2329', '23', '3');
INSERT INTO `t_role_right` VALUES ('2330', '24', '3');
INSERT INTO `t_role_right` VALUES ('2331', '64', '3');
INSERT INTO `t_role_right` VALUES ('2332', '62', '3');
INSERT INTO `t_role_right` VALUES ('2333', '59', '3');
INSERT INTO `t_role_right` VALUES ('2334', '60', '3');
INSERT INTO `t_role_right` VALUES ('2335', '61', '3');
INSERT INTO `t_role_right` VALUES ('2336', '63', '3');
INSERT INTO `t_role_right` VALUES ('2337', '51', '3');
INSERT INTO `t_role_right` VALUES ('2338', '49', '3');
INSERT INTO `t_role_right` VALUES ('2339', '53', '3');
INSERT INTO `t_role_right` VALUES ('2340', '54', '3');
INSERT INTO `t_role_right` VALUES ('2341', '52', '3');
INSERT INTO `t_role_right` VALUES ('2342', '50', '3');
INSERT INTO `t_role_right` VALUES ('2343', '36', '3');
INSERT INTO `t_role_right` VALUES ('2344', '39', '3');
INSERT INTO `t_role_right` VALUES ('2345', '35', '3');
INSERT INTO `t_role_right` VALUES ('2346', '37', '3');
INSERT INTO `t_role_right` VALUES ('2347', '38', '3');
INSERT INTO `t_role_right` VALUES ('2348', '40', '3');
INSERT INTO `t_role_right` VALUES ('2349', '55', '3');
INSERT INTO `t_role_right` VALUES ('2350', '56', '3');
INSERT INTO `t_role_right` VALUES ('2351', '57', '3');
INSERT INTO `t_role_right` VALUES ('2352', '58', '3');
INSERT INTO `t_role_right` VALUES ('2353', '47', '3');
INSERT INTO `t_role_right` VALUES ('2354', '46', '3');
INSERT INTO `t_role_right` VALUES ('2355', '42', '3');
INSERT INTO `t_role_right` VALUES ('2356', '43', '3');
INSERT INTO `t_role_right` VALUES ('2357', '44', '3');
INSERT INTO `t_role_right` VALUES ('2358', '45', '3');
INSERT INTO `t_role_right` VALUES ('2359', '68', '3');
INSERT INTO `t_role_right` VALUES ('2360', '67', '3');
INSERT INTO `t_role_right` VALUES ('2361', '66', '3');
INSERT INTO `t_role_right` VALUES ('2362', '65', '3');
INSERT INTO `t_role_right` VALUES ('2363', '69', '3');
INSERT INTO `t_role_right` VALUES ('2364', '30', '3');
INSERT INTO `t_role_right` VALUES ('2365', '29', '3');
INSERT INTO `t_role_right` VALUES ('2366', '31', '3');
INSERT INTO `t_role_right` VALUES ('2367', '34', '3');
INSERT INTO `t_role_right` VALUES ('2368', '32', '3');
INSERT INTO `t_role_right` VALUES ('2369', '33', '3');

-- ----------------------------
-- Table structure for t_shangji_info
-- ----------------------------
DROP TABLE IF EXISTS `t_shangji_info`;
CREATE TABLE `t_shangji_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `sbsj` varchar(20) DEFAULT '' COMMENT '申报时间',
  `yg` varchar(32) DEFAULT '' COMMENT '填报人',
  `xmbh` varchar(32) DEFAULT '' COMMENT '项目编号',
  `khqc` varchar(64) DEFAULT '' COMMENT '客户全称',
  `khlxr` varchar(16) DEFAULT '' COMMENT '客户联系人',
  `lxfs` varchar(64) DEFAULT '' COMMENT '联系方式',
  `xmmc` varchar(64) DEFAULT '' COMMENT '项目名称',
  `sjly` varchar(32) DEFAULT '' COMMENT '商机来源',
  `yshtje` varchar(12) DEFAULT '0' COMMENT '预算合同金额(元)',
  `shzt` int(1) DEFAULT '-1' COMMENT '审核状态 0不通过 1通过',
  `gszt` int(1) DEFAULT '0' COMMENT '公示状态 0未公示 1公示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_shangji_info
-- ----------------------------
INSERT INTO `t_shangji_info` VALUES ('41', '2018-03-13', 'dongwei', 'kl', '可哈', '吖吖', '110', '云2.0', '电话', '20000', '1', '1');
INSERT INTO `t_shangji_info` VALUES ('42', '2018-03-12', 'dongwei', '', '支付宝', '马云', '120', '余额宝', '电话', '3200', '0', '0');
INSERT INTO `t_shangji_info` VALUES ('43', '2018-03-13', 'dongwei', '', '粮库', '张三', '119', '智慧2', '电话', '300000', '-1', '0');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) NOT NULL DEFAULT '' COMMENT '用户名',
  `realname` varchar(32) DEFAULT '' COMMENT '真实姓名',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名：密码 MD5',
  `access_token` longtext,
  `status` varchar(1) DEFAULT '1' COMMENT '用户状态 1：正常 ；0: 锁定 ',
  `approved` varchar(4) DEFAULT '通过' COMMENT '核准状态: 未审核/通过/不通过',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_username_u` (`username`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '管理员', '$2a$10$Bq2oAVGl01G3o5wY00QQVO0sdW0TRlw4mRTtV6POCFAYGH92/60AS', '54c3f523-bf41-4472-a425-1d3c6aa57aee', '1', '通过');
INSERT INTO `t_user` VALUES ('24', 'jianghua', '姜华', '$2a$10$eElM1phQo5hb3/Xc.zmjs.2aWPhs8y1KbBK5hvTrGenwSTMQFfhjC', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqaWFuZ2h1YSIsImV4cCI6MTUyMTQzMzAyNCwiYXV0aHMiOiJbUk9MRV91c2VyX2FkZCwgUk9MRV91c2VyX2RlbGV0ZWQsIFJPTEVfdXNlcl91cGRhdGUsIFJPTEVfdXNlcl9pbmZvLCBST0xFX3VzZXJfcGFnZSwgUk9MRV91c2VyX2FsbCwgUk9MRV9yb2xlX2FkZCwgUk9MRV9yb2xlX2RlbGV0ZWQsIFJPTEVfcm9sZV91cGRhdGUsIFJPTEVfcm9sZV9pbmZvLCBST0xFX3JvbGVfcGFnZSwgUk9MRV9yb2xlX2FsbCwgUk9MRV9yb2xlX3Blcm1zLCBST0xFX3JpZ2h0X2FkZCwgUk9MRV9yaWdodF9kZWxldGVkLCBST0xFX3JpZ2h0X3VwZGF0ZSwgUk9MRV9yaWdodF9pbmZvLCBST0xFX3JpZ2h0X3BhZ2UsIFJPTEVfcmlnaHRfYWxsLCBST0xFX3JvbGVSaWdodF9hZGQsIFJPTEVfcm9sZVJpZ2h0X2RlbGV0ZWQsIFJPTEVfcm9sZVJpZ2h0X3VwZGF0ZSwgUk9MRV9yb2xlUmlnaHRfaW5mbywgUk9MRV9yb2xlUmlnaHRfcGFnZSwgUk9MRV9yb2xlUmlnaHRfYWxsLCBST0xFX2xvZ19hZGQsIFJPTEVfbG9nX2RlbGV0ZWQsIFJPTEVfbG9nX3VwZGF0ZSwgUk9MRV9sb2dfaW5mbywgUk9MRV9sb2dfcGFnZSwgUk9MRV9sb2dfYWxsLCBST0xFX3VzZXJfdXBkcGFzc3dvcmQsIFJPTEVfam9iX2FkZCwgUk9MRV9qb2JfZGVsZXRlZCwgUk9MRV9qb2JfdXBkYXRlLCBST0xFX2pvYl9pbmZvLCBST0xFX2pvYl9wYWdlLCBST0xFX2pvYl9hbGwsIFJPTEVfc2hhbmdqaUluZm9fYWRkLCBST0xFX3NoYW5namlJbmZvX2RlbGV0ZWQsIFJPTEVfc2hhbmdqaUluZm9fdXBkYXRlLCBST0xFX3NoYW5namlJbmZvX2luZm8sIFJPTEVfc2hhbmdqaUluZm9fcGFnZSwgUk9MRV9zaGFuZ2ppSW5mb19hbGwsIFJPTEVfZmxvd190b2RvdGFza3NfcGFnZSwgUk9MRV9wcm9jZXNzSW1hZ2UsIFJPTEVfZmxvd19jbGFpbSwgUk9MRV9mbG93X3JlYWRmb3JtXSJ9.7vxx1pmP7r2c91z7Ixcy81qyaVRc6QT34vK2fLhxIk1owpwBn-mbbhzcZgeDPfzNVJPpCJIGIWTjLMIWhiX4AQ', '1', '通过');
INSERT INTO `t_user` VALUES ('25', 'dongwei', '董魏', '$2a$10$BAnWfiOFLdODdP8qdyGg6OtbbFYTuBWKDuiWIfyumHPM5WR29ZmLW', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkb25nd2VpIiwiZXhwIjoxNTIwOTI4OTQxLCJhdXRocyI6IltST0xFX3VzZXJfYWRkLCBST0xFX3VzZXJfZGVsZXRlZCwgUk9MRV91c2VyX3VwZGF0ZSwgUk9MRV91c2VyX2luZm8sIFJPTEVfdXNlcl9wYWdlLCBST0xFX3VzZXJfYWxsLCBST0xFX3JvbGVfYWRkLCBST0xFX3JvbGVfZGVsZXRlZCwgUk9MRV9yb2xlX3VwZGF0ZSwgUk9MRV9yb2xlX2luZm8sIFJPTEVfcm9sZV9wYWdlLCBST0xFX3JvbGVfYWxsLCBST0xFX3JvbGVfcGVybXMsIFJPTEVfcmlnaHRfYWRkLCBST0xFX3JpZ2h0X2RlbGV0ZWQsIFJPTEVfcmlnaHRfdXBkYXRlLCBST0xFX3JpZ2h0X2luZm8sIFJPTEVfcmlnaHRfcGFnZSwgUk9MRV9yaWdodF9hbGwsIFJPTEVfcm9sZVJpZ2h0X2FkZCwgUk9MRV9yb2xlUmlnaHRfZGVsZXRlZCwgUk9MRV9yb2xlUmlnaHRfdXBkYXRlLCBST0xFX3JvbGVSaWdodF9pbmZvLCBST0xFX3JvbGVSaWdodF9wYWdlLCBST0xFX3JvbGVSaWdodF9hbGwsIFJPTEVfbG9nX2FkZCwgUk9MRV9sb2dfZGVsZXRlZCwgUk9MRV9sb2dfdXBkYXRlLCBST0xFX2xvZ19pbmZvLCBST0xFX2xvZ19wYWdlLCBST0xFX2xvZ19hbGwsIFJPTEVfdXNlcl91cGRwYXNzd29yZCwgUk9MRV9qb2JfYWRkLCBST0xFX2pvYl9kZWxldGVkLCBST0xFX2pvYl91cGRhdGUsIFJPTEVfam9iX2luZm8sIFJPTEVfam9iX3BhZ2UsIFJPTEVfam9iX2FsbCwgUk9MRV9zaGFuZ2ppSW5mb19hZGQsIFJPTEVfc2hhbmdqaUluZm9fZGVsZXRlZCwgUk9MRV9zaGFuZ2ppSW5mb191cGRhdGUsIFJPTEVfc2hhbmdqaUluZm9faW5mbywgUk9MRV9zaGFuZ2ppSW5mb19wYWdlLCBST0xFX3NoYW5namlJbmZvX2FsbCwgUk9MRV9mbG93X3RvZG90YXNrc19wYWdlLCBST0xFX3Byb2Nlc3NJbWFnZSwgUk9MRV9mbG93X2NsYWltLCBST0xFX2Zsb3dfcmVhZGZvcm1dIn0._CfhzrnV9QZRd_75oDRloJmb2sHHbncOJquQau8XSXY3_XqRfmXngtn8vLZqjzTKFrq0TUXSkx0NB0SgFg6GLw', '1', '通过');
INSERT INTO `t_user` VALUES ('30', 'juese', '角色测试', '$2a$10$zRpHebIOp21/CRsn2iKiWOMZWdYBetKsq4uFBYorS6dMl6qtsA31.', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWVzZSIsImV4cCI6MTUyMjA1MjU3MywiYXV0aHMiOiJbUk9MRV91c2VyX2FkZCwgUk9MRV91c2VyX2RlbGV0ZWQsIFJPTEVfdXNlcl91cGRhdGUsIFJPTEVfdXNlcl9pbmZvLCBST0xFX3VzZXJfcGFnZSwgUk9MRV91c2VyX2FsbCwgUk9MRV9yb2xlX2FkZCwgUk9MRV9yb2xlX2RlbGV0ZWQsIFJPTEVfcm9sZV91cGRhdGUsIFJPTEVfcm9sZV9pbmZvLCBST0xFX3JvbGVfcGFnZSwgUk9MRV9yb2xlX2FsbCwgUk9MRV9yb2xlX3Blcm1zLCBST0xFX3JpZ2h0X2FkZCwgUk9MRV9yaWdodF9kZWxldGVkLCBST0xFX3JpZ2h0X3VwZGF0ZSwgUk9MRV9yaWdodF9pbmZvLCBST0xFX3JpZ2h0X3BhZ2UsIFJPTEVfcmlnaHRfYWxsLCBST0xFX3JvbGVSaWdodF9hZGQsIFJPTEVfcm9sZVJpZ2h0X2RlbGV0ZWQsIFJPTEVfcm9sZVJpZ2h0X3VwZGF0ZSwgUk9MRV9yb2xlUmlnaHRfaW5mbywgUk9MRV9yb2xlUmlnaHRfcGFnZSwgUk9MRV9yb2xlUmlnaHRfYWxsLCBST0xFX2xvZ19hZGQsIFJPTEVfbG9nX2RlbGV0ZWQsIFJPTEVfbG9nX3VwZGF0ZSwgUk9MRV9sb2dfaW5mbywgUk9MRV9sb2dfcGFnZSwgUk9MRV9sb2dfYWxsLCBST0xFX3VzZXJfdXBkcGFzc3dvcmQsIFJPTEVfam9iX2FkZCwgUk9MRV9qb2JfZGVsZXRlZCwgUk9MRV9qb2JfdXBkYXRlLCBST0xFX2pvYl9pbmZvLCBST0xFX2pvYl9wYWdlLCBST0xFX2pvYl9hbGwsIFJPTEVfc2hhbmdqaUluZm9fYWRkLCBST0xFX3NoYW5namlJbmZvX2RlbGV0ZWQsIFJPTEVfc2hhbmdqaUluZm9fdXBkYXRlLCBST0xFX3NoYW5namlJbmZvX2luZm8sIFJPTEVfc2hhbmdqaUluZm9fcGFnZSwgUk9MRV9zaGFuZ2ppSW5mb19hbGwsIFJPTEVfZmxvd190b2RvdGFza3NfcGFnZSwgUk9MRV9wcm9jZXNzSW1hZ2UsIFJPTEVfZmxvd19jbGFpbSwgUk9MRV9mbG93X3JlYWRmb3JtLCBST0xFX21lbnVfcGFnZSwgUk9MRV9tZW51X2FkZCwgUk9MRV9tZW51X2RlbGV0ZWQsIFJPTEVfbWVudV91cGRhdGUsIFJPTEVfbWVudV9pbmZvLCBST0xFX21lbnVfYWxsXSJ9.bPoDjR-Cw176IyuUIy1nihEMzP-Qwv42nj_5xr1Svfas-7PdvPmt4uMURvdxSbbFeI3N_E11SHn3gr-BF1O82A', '1', '通过');
INSERT INTO `t_user` VALUES ('31', 'why', '王红艳', '$2a$10$frb6Qej0xJkG67OXVOheX.JvEVGPJk6i4df.T.Snd5sJEAjE2ePDW', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aHkiLCJleHAiOjE1MjMzMzMyNjAsImF1dGhzIjoiW1JPTEVfdXNlcl9hZGQsIFJPTEVfdXNlcl9kZWxldGVkLCBST0xFX3VzZXJfdXBkYXRlLCBST0xFX3VzZXJfaW5mbywgUk9MRV91c2VyX3BhZ2UsIFJPTEVfdXNlcl9hbGwsIFJPTEVfcm9sZV9hZGQsIFJPTEVfcm9sZV9kZWxldGVkLCBST0xFX3JvbGVfdXBkYXRlLCBST0xFX3JvbGVfaW5mbywgUk9MRV9yb2xlX3BhZ2UsIFJPTEVfcm9sZV9hbGwsIFJPTEVfcm9sZV9wZXJtcywgUk9MRV9yaWdodF9hZGQsIFJPTEVfcmlnaHRfZGVsZXRlZCwgUk9MRV9yaWdodF91cGRhdGUsIFJPTEVfcmlnaHRfaW5mbywgUk9MRV9yaWdodF9wYWdlLCBST0xFX3JpZ2h0X2FsbCwgUk9MRV9yb2xlUmlnaHRfYWRkLCBST0xFX3JvbGVSaWdodF9kZWxldGVkLCBST0xFX3JvbGVSaWdodF91cGRhdGUsIFJPTEVfcm9sZVJpZ2h0X2luZm8sIFJPTEVfcm9sZVJpZ2h0X3BhZ2UsIFJPTEVfcm9sZVJpZ2h0X2FsbCwgUk9MRV9sb2dfYWRkLCBST0xFX2xvZ19kZWxldGVkLCBST0xFX2xvZ191cGRhdGUsIFJPTEVfbG9nX2luZm8sIFJPTEVfbG9nX3BhZ2UsIFJPTEVfbG9nX2FsbCwgUk9MRV91c2VyX3VwZHBhc3N3b3JkLCBST0xFX2pvYl9hZGQsIFJPTEVfam9iX2RlbGV0ZWQsIFJPTEVfam9iX3VwZGF0ZSwgUk9MRV9qb2JfaW5mbywgUk9MRV9qb2JfcGFnZSwgUk9MRV9qb2JfYWxsLCBST0xFX3NoYW5namlJbmZvX2FkZCwgUk9MRV9zaGFuZ2ppSW5mb19kZWxldGVkLCBST0xFX3NoYW5namlJbmZvX3VwZGF0ZSwgUk9MRV9zaGFuZ2ppSW5mb19pbmZvLCBST0xFX3NoYW5namlJbmZvX3BhZ2UsIFJPTEVfc2hhbmdqaUluZm9fYWxsLCBST0xFX2Zsb3dfdG9kb3Rhc2tzX3BhZ2UsIFJPTEVfcHJvY2Vzc0ltYWdlLCBST0xFX2Zsb3dfY2xhaW0sIFJPTEVfZmxvd19yZWFkZm9ybSwgUk9MRV9tZW51X3BhZ2UsIFJPTEVfbWVudV9hZGQsIFJPTEVfbWVudV9kZWxldGVkLCBST0xFX21lbnVfdXBkYXRlLCBST0xFX21lbnVfaW5mbywgUk9MRV9tZW51X2FsbCwgUk9MRV9mYWN0b3J5X2FkZCwgUk9MRV9mYWN0b3J5X2RlbGV0ZWQsIFJPTEVfZmFjdG9yeV91cGRhdGUsIFJPTEVfZmFjdG9yeV9pbmZvLCBST0xFX2ZhY3RvcnlfcGFnZV0ifQ.rnc50W6LURq-yj97wVdzHfkamWl4ZXt8-V2-A0fzrpAdxt_zjsGRhnM-hINgXkhvShp6y_77Hu04nqzIbeuLGQ', '1', '通过');
INSERT INTO `t_user` VALUES ('36', 'wyf', '王一飞', '$2a$10$zTNzYitvNyu4xAvbiy4LNOWLIkq9.WKGeEfGgH7a2AAsQv3jf36oy', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3eWYiLCJleHAiOjE1MjMxNjczMzUsImF1dGhzIjoiW1JPTEVfdXNlcl9hZGQsIFJPTEVfdXNlcl9kZWxldGVkLCBST0xFX3VzZXJfdXBkYXRlLCBST0xFX3VzZXJfaW5mbywgUk9MRV91c2VyX3BhZ2UsIFJPTEVfdXNlcl9hbGwsIFJPTEVfcm9sZV9hZGQsIFJPTEVfcm9sZV9kZWxldGVkLCBST0xFX3JvbGVfdXBkYXRlLCBST0xFX3JvbGVfaW5mbywgUk9MRV9yb2xlX3BhZ2UsIFJPTEVfcm9sZV9hbGwsIFJPTEVfcm9sZV9wZXJtcywgUk9MRV9yaWdodF9hZGQsIFJPTEVfcmlnaHRfZGVsZXRlZCwgUk9MRV9yaWdodF91cGRhdGUsIFJPTEVfcmlnaHRfaW5mbywgUk9MRV9yaWdodF9wYWdlLCBST0xFX3JpZ2h0X2FsbCwgUk9MRV9yb2xlUmlnaHRfYWRkLCBST0xFX3JvbGVSaWdodF9kZWxldGVkLCBST0xFX3JvbGVSaWdodF91cGRhdGUsIFJPTEVfcm9sZVJpZ2h0X2luZm8sIFJPTEVfcm9sZVJpZ2h0X3BhZ2UsIFJPTEVfcm9sZVJpZ2h0X2FsbCwgUk9MRV9sb2dfYWRkLCBST0xFX2xvZ19kZWxldGVkLCBST0xFX2xvZ191cGRhdGUsIFJPTEVfbG9nX2luZm8sIFJPTEVfbG9nX3BhZ2UsIFJPTEVfbG9nX2FsbCwgUk9MRV91c2VyX3VwZHBhc3N3b3JkLCBST0xFX2pvYl9hZGQsIFJPTEVfam9iX2RlbGV0ZWQsIFJPTEVfam9iX3VwZGF0ZSwgUk9MRV9qb2JfaW5mbywgUk9MRV9qb2JfcGFnZSwgUk9MRV9qb2JfYWxsLCBST0xFX3NoYW5namlJbmZvX2FkZCwgUk9MRV9zaGFuZ2ppSW5mb19kZWxldGVkLCBST0xFX3NoYW5namlJbmZvX3VwZGF0ZSwgUk9MRV9zaGFuZ2ppSW5mb19pbmZvLCBST0xFX3NoYW5namlJbmZvX3BhZ2UsIFJPTEVfc2hhbmdqaUluZm9fYWxsLCBST0xFX2Zsb3dfdG9kb3Rhc2tzX3BhZ2UsIFJPTEVfcHJvY2Vzc0ltYWdlLCBST0xFX2Zsb3dfY2xhaW0sIFJPTEVfZmxvd19yZWFkZm9ybSwgUk9MRV9tZW51X3BhZ2UsIFJPTEVfbWVudV9hZGQsIFJPTEVfbWVudV9kZWxldGVkLCBST0xFX21lbnVfdXBkYXRlLCBST0xFX21lbnVfaW5mbywgUk9MRV9tZW51X2FsbCwgUk9MRV9mYWN0b3J5X2FkZCwgUk9MRV9mYWN0b3J5X2RlbGV0ZWQsIFJPTEVfZmFjdG9yeV91cGRhdGUsIFJPTEVfZmFjdG9yeV9pbmZvLCBST0xFX2ZhY3RvcnlfcGFnZSwgUk9MRV9mYWN0b3J5X2FsbCwgUk9MRV91cGxvYWQsIFJPTEVfcmVnaXN0cmF0aW9uQ29kZV9hZGQsIFJPTEVfcmVnaXN0cmF0aW9uQ29kZV9kZWxldGVkLCBST0xFX3JlZ2lzdHJhdGlvbkNvZGVfdXBkYXRlLCBST0xFX3JlZ2lzdHJhdGlvbkNvZGVfaW5mbywgUk9MRV9yZWdpc3RyYXRpb25Db2RlX3BhZ2UsIFJPTEVfcmVnaXN0cmF0aW9uQ29kZV9hbGwsIFJPTEVfam9iX2RpYW9kdV0ifQ.J3wElVrm05SmF6k_iavFIXrir--p3OgeLrvR9ERfEIY_aRFORo8JI3-C1KLkLC2vAj-ssIenDJBVf14iqDxljw', '1', '通过');
INSERT INTO `t_user` VALUES ('38', '测试', '测试一下下', '$2a$10$x4HLtD7oe0yBrUxoo9tAHeqmfZ3p0xh.L7orpiM90prx5K7jQYd56', null, '1', '通过');
INSERT INTO `t_user` VALUES ('44', 'ceshi', 'ww', '$2a$10$Avq7s/pp1eRkNkvWrd5K4uvQWfnxjSAimme1lz341MgcHU1thQArC', null, '1', '通过');
INSERT INTO `t_user` VALUES ('46', 'kkkk', 'mmasda', '$2a$10$gpi7e0k8nGSfOJlkofD31elwZ/rNjlTzGjQteVF8gGpEobPIaIQvC', null, '1', '通过');
INSERT INTO `t_user` VALUES ('59', 'new', '考虑', '$2a$10$y5EdRcY.y9WOdEiBpqsEnOoIafDWw/i21OGfuqdPlD5hkroHgRx/q', null, '1', '未审核');

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '用户名',
  `username` varchar(32) DEFAULT NULL,
  `realname` varchar(32) DEFAULT NULL,
  `factory_id` bigint(10) DEFAULT '0' COMMENT '工厂',
  `sex` varchar(2) DEFAULT '' COMMENT '性别 男/女',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `birth` varchar(10) DEFAULT '' COMMENT '生日 格式yyyy-MM-dd',
  `type` varchar(4) DEFAULT '企业用户' COMMENT '人员类型: 工厂用户/企业用户',
  `email` varchar(32) DEFAULT '' COMMENT '电子信箱',
  `dept` bigint(10) DEFAULT '0' COMMENT '部门,企业用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_info_phone_u` (`phone`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_info
-- ----------------------------
INSERT INTO `t_user_info` VALUES ('1', 'admin', '管理员', '0', '女', '18932224279', '1990-06-20', '企业用户', '407477841@qq.com', '0');
INSERT INTO `t_user_info` VALUES ('2', 'why', '王红艳', '0', '女', '18951638593', '2018-03-17', '企业用户', '1258186', '0');
INSERT INTO `t_user_info` VALUES ('9', 'jianghua', '姜华', '0', '', null, '', '企业用户', '', '0');
INSERT INTO `t_user_info` VALUES ('10', 'dongwei', '董魏', '0', '', null, '', '企业用户', '', '0');
INSERT INTO `t_user_info` VALUES ('11', 'juese', '角色测试', '0', '', null, '', '企业用户', '', '0');
INSERT INTO `t_user_info` VALUES ('13', 'wyf', '王一飞', '0', '男', null, '2018-04-22', '工厂用户', '', '0');
INSERT INTO `t_user_info` VALUES ('14', '测试', '测试一下下', '0', '', null, '', '企业用户', '', '0');
INSERT INTO `t_user_info` VALUES ('15', 'ceshi', 'ww', '0', '', null, '', '企业用户', '', '0');
INSERT INTO `t_user_info` VALUES ('16', 'kkkk', 'mmasda', '0', '', null, '', '企业用户', '', '0');

-- ----------------------------
-- Table structure for t_user_right_limit
-- ----------------------------
DROP TABLE IF EXISTS `t_user_right_limit`;
CREATE TABLE `t_user_right_limit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `right_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `limited` varchar(1) DEFAULT '0' COMMENT '1禁止 0允许',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='该表存储用户命令被禁止/允许的权限。';

-- ----------------------------
-- Records of t_user_right_limit
-- ----------------------------
INSERT INTO `t_user_right_limit` VALUES ('1', '1', '2', '1');
INSERT INTO `t_user_right_limit` VALUES ('2', '2', '2', '0');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', '1');
INSERT INTO `t_user_role` VALUES ('2', '1', '24');
INSERT INTO `t_user_role` VALUES ('3', '1', '25');
INSERT INTO `t_user_role` VALUES ('13', '3', '30');
INSERT INTO `t_user_role` VALUES ('14', '3', '31');
INSERT INTO `t_user_role` VALUES ('15', '4', '32');
INSERT INTO `t_user_role` VALUES ('16', '3', '33');
INSERT INTO `t_user_role` VALUES ('17', '4', '34');
INSERT INTO `t_user_role` VALUES ('18', '4', '35');
INSERT INTO `t_user_role` VALUES ('20', '3', '37');
INSERT INTO `t_user_role` VALUES ('23', '1', '39');
INSERT INTO `t_user_role` VALUES ('24', '3', '39');
INSERT INTO `t_user_role` VALUES ('26', '5', '45');
INSERT INTO `t_user_role` VALUES ('28', '4', '46');
INSERT INTO `t_user_role` VALUES ('32', '3', '53');
INSERT INTO `t_user_role` VALUES ('36', '1', '38');
INSERT INTO `t_user_role` VALUES ('37', '4', '38');
INSERT INTO `t_user_role` VALUES ('45', '4', '44');
INSERT INTO `t_user_role` VALUES ('53', '5', '36');
INSERT INTO `t_user_role` VALUES ('56', '3', '57');
INSERT INTO `t_user_role` VALUES ('57', '3', '59');
DROP TRIGGER IF EXISTS `deleteUser`;
DELIMITER ;;
CREATE TRIGGER `deleteUser` AFTER DELETE ON `t_user` FOR EACH ROW begin
delete from t_user_info where username=old.username;

end
;;
DELIMITER ;
