create database  if not exists `tkeel-alarm` Character Set UTF8;


-- `tkeel-alarm`.tkeel_alarm_mail_config definition

CREATE TABLE if not exists `tkeel-alarm`.`tkeel_alarm_mail_config` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `smtp_address` varchar(100) NOT NULL,
  `port` varchar(10) NOT NULL,
  `ssl` int NOT NULL DEFAULT '0' COMMENT '0:不启用\r\n1:启用\r\n默认不启用',
  `smtp_user_name` varchar(20) NOT NULL,
  `smtp_pass_word` varchar(30) NOT NULL,
  `from_address` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- `tkeel-alarm`.tkeel_alarm_rule definition

CREATE TABLE if not exists `tkeel-alarm`.`tkeel_alarm_rule` (
  `rule_id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(20) NOT NULL,
  `rule_name` varchar(50) NOT NULL,
  `alarm_type` int NOT NULL COMMENT '0：普通告警\r\n1：持续告警',
  `alarm_rule_type` int NOT NULL COMMENT '0：阈值告警\r\n1：系统告警',
  `alarm_level` int NOT NULL COMMENT '级别：1、2、3、4\r\n1级最高，4级最低',
  `alarm_source_object` int NOT NULL COMMENT '0：平台\r\n1：设备',
  `temp_id` varchar(50) DEFAULT NULL,
  `temp_name` varchar(50) DEFAULT NULL,
  `device_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '当告警源对象为设备时生效',
  `device_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '当告警源对象为设备时生效',
  `telemetry_id` varchar(100) DEFAULT NULL,
  `condition` varchar(10) NOT NULL COMMENT 'or,and',
  `prom_ql` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'promQL表达式',
  `rule_desc` varchar(200) NOT NULL COMMENT '当对象是设备时，提取遥测名称+阈值放入描述\r\n当对象是平台时，直接名称用逗号隔开存入即可',
  `notice_id` varchar(100) DEFAULT NULL COMMENT '规则对应的通知ID，多个逗号隔开',
  `enable` int NOT NULL DEFAULT '0' COMMENT '是否启用\r\n0：停用\r\n1：启用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` int NOT NULL DEFAULT '0',
  `temp_status` int NOT NULL DEFAULT '0' COMMENT '0：未变更，1：删除，2：修改',
  `device_status` int NOT NULL DEFAULT '0' COMMENT '0:未变更，1：删除，2：修改',
  `telemetry_status` int NOT NULL DEFAULT '0' COMMENT '0：未变更，1：删除，2：修改',
  PRIMARY KEY (`rule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10149 DEFAULT CHARSET=utf8mb3;


-- `tkeel-alarm`.tkeel_alarm_record definition

CREATE TABLE if not exists `tkeel-alarm`.`tkeel_alarm_record` (
  `alarm_id` bigint NOT NULL AUTO_INCREMENT,
  `rule_id` varchar(100) NOT NULL,
  `tenant_id` varchar(20) NOT NULL,
  `alarm_name` varchar(50) NOT NULL,
  `alarm_level` int NOT NULL,
  `alarm_strategy` int NOT NULL,
  `alarm_source` varchar(20) NOT NULL,
  `alarm_type` int NOT NULL,
  `object_id` varchar(50) DEFAULT NULL,
  `telemetry_id` varchar(20) DEFAULT NULL,
  `start_time` bigint NOT NULL,
  `hand_time` bigint DEFAULT NULL,
  `alarm_status` int NOT NULL,
  `alarm_value` varchar(20) NOT NULL COMMENT '告警上报值',
  `alarm_desc` varchar(100) DEFAULT NULL,
  `hand_opinions` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处理意见',
  `deleted` int NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`alarm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=60000 DEFAULT CHARSET=utf8mb3;


-- `tkeel-alarm`.tkeel_alarm_rule_desc definition

CREATE TABLE  if not exists `tkeel-alarm`.`tkeel_alarm_rule_desc` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `rule_id` bigint NOT NULL,
  `tenant_id` varchar(20) NOT NULL,
  `alarm_source_object` int NOT NULL COMMENT '0：平台，1：设备',
  `telemetry_type` int DEFAULT NULL,
  `telemetry_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `telemetry_name` varchar(20) DEFAULT NULL,
  `time` int DEFAULT NULL,
  `polymerize` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '聚合,avg（平均值）；max（最大值）；min（最小值）',
  `operator` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '运算符',
  `label` varchar(10) DEFAULT NULL COMMENT '当遥测类型是，枚举或者bool时生效',
  `value` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `plat_rule_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `deleted` int NOT NULL DEFAULT '0',
  `telemetry_status` int NOT NULL DEFAULT '0' COMMENT '0：未变更，1：删除，2：修改',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=959 DEFAULT CHARSET=utf8mb3;

-- `tkeel-alarm`.tkeel_alarm_rule_platform definition

CREATE TABLE  if not exists `tkeel-alarm`.`tkeel_alarm_rule_platform` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `alarm_desc` varchar(100) NOT NULL COMMENT '告警描述',
  `prom_ql` varchar(200) NOT NULL COMMENT 'promQL表达式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb3;


-- `tkeel-alarm`.tkeel_alarm_notice definition

CREATE TABLE  if not exists `tkeel-alarm`.`tkeel_alarm_notice` (
  `notice_id` bigint NOT NULL AUTO_INCREMENT,
  `tenant_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `group_name` varchar(30) NOT NULL,
  `notice_desc` varchar(100) NOT NULL,
  `email_address` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '邮件地址，逗号隔开',
  `deleted` int NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb3;