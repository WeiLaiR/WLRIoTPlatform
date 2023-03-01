CREATE DATABASE iot_platform_db;
USE iot_platform_db;

DROP TABLE IF EXISTS login;

CREATE TABLE `login` (
    `uid` BIGINT NOT NULL COMMENT '用户id',
    `email` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL UNIQUE COMMENT '邮箱',
    `password` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '密码',
    `status` INT NULL DEFAULT 0 COMMENT '用户状态',
    `create_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '信息创建时间',
    `update_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '信息更改时间',
    `is_delete` BIT NULL DEFAULT FALSE COMMENT '逻辑删除',
    PRIMARY KEY (`uid`) USING BTREE,
    INDEX e_s (uid, status) USING BTREE,
    INDEX ss(status, email)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO login (uid, email, password, status) VALUES
     (1677658882950001,'admin@admin.com','0d08db925f7d3332305970bfb4cba6df7aa1c36570f979f0d5b862db70a33237',3);


DROP TABLE IF EXISTS user;

CREATE TABLE `user` (
    `uid` BIGINT NOT NULL COMMENT '用户id',
    `sex` BIT NULL DEFAULT NULL COMMENT '性别',
    `age` SMALLINT NULL DEFAULT NULL COMMENT '年龄',
    `email` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL UNIQUE COMMENT '邮箱',
    `phone_number` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '电话号',
    `uname`  VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
    `brief_introduction` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
    `remarks` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
    `create_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '信息创建时间',
    `update_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '信息更改时间',
    `is_delete` BIT NULL DEFAULT FALSE COMMENT '逻辑删除',
    PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO user (uid, sex, age, email, phone_number, uname, brief_introduction, remarks) VALUES
                     (1677658882950001, true, 23, 'admin@admin.com', '12345678901', '管理员', '我是管理员', '我是管理员!!!');


DROP TABLE IF EXISTS user_setting;

CREATE TABLE `user_setting` (
    `uid` BIGINT NOT NULL COMMENT '用户id',
    `send_email` BIT NULL DEFAULT FALSE COMMENT '是否发送邮件',
    `keep_alive` INT NULL DEFAULT 3 COMMENT 'token存活时长',
    `create_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '信息创建时间',
    `update_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '信息更改时间',
    `is_delete` BIT NULL DEFAULT FALSE COMMENT '逻辑删除',
    PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO user_setting(uid, send_email, keep_alive) VALUES
                            ('1677658882950001', false, 3);


DROP TABLE IF EXISTS user_remind;

CREATE TABLE `user_remind` (
    `remind_id` BIGINT NOT NULL COMMENT '信息id',
    `uid` BIGINT NOT NULL COMMENT '用户id',
    `message` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消息体',
    `is_read` BIT NULL DEFAULT FALSE COMMENT '已读',
    `create_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '信息创建时间',
    PRIMARY KEY (`remind_id`) USING BTREE,
    INDEX (uid, is_read) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



DROP TABLE IF EXISTS device_info;

CREATE TABLE `device_info` (
    `device_id` BIGINT NOT NULL COMMENT '设备id',
    `uid` BIGINT NOT NULL COMMENT '用户id',
    `device_name` VARCHAR(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备名',
    `device_token` VARCHAR(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL UNIQUE COMMENT '设备Token',
    `protocol` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL NULL COMMENT '协议',
    `version` DOUBLE DEFAULT 1.0 NULL COMMENT '版本',
    `description` VARCHAR(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL NULL COMMENT '描述',
    `create_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '信息创建时间',
    `update_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '信息更改时间',
    `is_delete` BIT NULL DEFAULT FALSE COMMENT '逻辑删除',
    PRIMARY KEY (`device_id`) USING BTREE,
    INDEX (uid) USING BTREE,
    INDEX (device_name) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;





DROP TABLE IF EXISTS device_cfg;

CREATE TABLE `device_cfg` (
    `device_cfg_id` BIGINT NOT NULL COMMENT '配置id',
    `device_id` BIGINT NOT NULL COMMENT '设备id',
    `type_name` VARCHAR(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型名',
    `type_nick_name` VARCHAR(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型昵称',
    `is_number` BIT NOT NULL DEFAULT FALSE COMMENT '是否为数字',
    `create_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '信息创建时间',
    `update_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '信息更改时间',
    `is_delete` BIT NULL DEFAULT FALSE COMMENT '逻辑删除',
    PRIMARY KEY (`device_cfg_id`) USING BTREE,
    INDEX (device_id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



DROP TABLE IF EXISTS device_data;

CREATE TABLE `device_data` (
    `device_data_id` BIGINT NOT NULL COMMENT '数据id',
    `device_cfg_id` BIGINT NOT NULL COMMENT '配置id',
    `data` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字符串型数据',
    `equipment_no` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL COMMENT '设备编号',
    `protocol` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL NULL COMMENT '协议',
    `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '信息创建时间',
    PRIMARY KEY (`device_data_id`) USING BTREE,
    INDEX (device_cfg_id, create_time) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS device_data_number;

CREATE TABLE `device_data_number` (
    `device_data_id` BIGINT NOT NULL COMMENT '数据id',
    `device_cfg_id` BIGINT NOT NULL COMMENT '配置id',
    `data` DOUBLE NOT NULL COMMENT '数字类型数据',
    `equipment_no` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL COMMENT '设备编号',
    `protocol` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL NULL COMMENT '协议',
    `create_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '信息创建时间',
    PRIMARY KEY (`device_data_id`) USING BTREE,
    INDEX (device_cfg_id, create_time) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS device_rule;

CREATE TABLE `device_rule` (
    `device_rule_id` BIGINT NOT NULL COMMENT '规则id',
    `device_cfg_id` BIGINT NOT NULL COMMENT '配置id',
    `is_meet` BIT NOT NULL DEFAULT FALSE COMMENT '是否满足该要求',
    `value` DOUBLE NOT NULL COMMENT '值',
    `rule_name` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL COMMENT '规则名',
    `symbol` VARCHAR(5) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL COMMENT '符号',
    `create_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '信息创建时间',
    `update_time` DATETIME  NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '信息更改时间',
    `is_delete` BIT NULL DEFAULT FALSE COMMENT '逻辑删除',
    PRIMARY KEY (`device_rule_id`) USING BTREE,
    INDEX (device_cfg_id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



