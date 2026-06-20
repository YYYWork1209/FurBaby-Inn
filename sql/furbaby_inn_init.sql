-- ============================================================
-- 毛孩驿站 (Furbaby Inn) 数据库初始化脚本
-- 版本: 1.0.0
-- 数据库: MySQL 8.0+
-- 字符集: utf8mb4
-- ============================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS furbaby_inn
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE furbaby_inn;

-- ============================================================
-- 1. 用户服务 (user-service)
-- ============================================================

-- 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '用户ID',
    `phone`         VARCHAR(20)     NOT NULL                 COMMENT '手机号',
    `password`      VARCHAR(255)    NOT NULL                 COMMENT '密码(BCrypt加密)',
    `nickname`      VARCHAR(50)     NOT NULL                 COMMENT '昵称',
    `avatar`        VARCHAR(500)    DEFAULT NULL             COMMENT '头像URL',
    `role`          ENUM('owner','shop') NOT NULL            COMMENT '角色: owner=宠主, shop=商家',
    `email`         VARCHAR(100)    DEFAULT NULL             COMMENT '邮箱',
    `create_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ============================================================
-- 2. 商家服务 (shop-service)
-- ============================================================

-- 商家表
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '商家ID',
    `user_id`       BIGINT          NOT NULL                 COMMENT '商户用户ID',
    `name`          VARCHAR(100)    NOT NULL                 COMMENT '商家名称',
    `avatar`        VARCHAR(500)    DEFAULT NULL             COMMENT '头像URL',
    `photos`        JSON            DEFAULT NULL             COMMENT '店铺照片URL列表',
    `rating`        DECIMAL(2,1)    DEFAULT 5.0              COMMENT '评分(1.0-5.0)',
    `price`         DECIMAL(10,2)   NOT NULL                 COMMENT '日单价',
    `tags`          JSON            DEFAULT NULL             COMMENT '标签列表',
    `address`       VARCHAR(255)    NOT NULL                 COMMENT '地址',
    `phone`         VARCHAR(20)     NOT NULL                 COMMENT '联系电话',
    `description`   TEXT            DEFAULT NULL             COMMENT '商家描述',
    `services`      JSON            DEFAULT NULL             COMMENT '服务项目列表',
    `notice`        TEXT            DEFAULT NULL             COMMENT '入住须知',
    `status`        ENUM('pending','approved','rejected','disabled') DEFAULT 'pending' COMMENT '审核状态',
    `biz_status`    VARCHAR(20)     NOT NULL DEFAULT 'open'         COMMENT '营业状态: open=营业中, closed=休息中',
    `create_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`),
    KEY `idx_rating` (`rating`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家表';

-- 商家档期表
DROP TABLE IF EXISTS `shop_schedule`;
CREATE TABLE `shop_schedule` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '档期ID',
    `shop_id`       BIGINT          NOT NULL                 COMMENT '商家ID',
    `date`          DATE            NOT NULL                 COMMENT '日期',
    `available`     INT             NOT NULL DEFAULT 0       COMMENT '剩余名额',
    `price`         DECIMAL(10,2)   NOT NULL                 COMMENT '当日价格',
    `create_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_shop_date` (`shop_id`, `date`),
    KEY `idx_date` (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家档期表';

-- ============================================================
-- 3. 宠物服务 (pet-service)
-- ============================================================

-- 宠物档案表
DROP TABLE IF EXISTS `pet`;
CREATE TABLE `pet` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '宠物ID',
    `owner_id`      BIGINT          NOT NULL                 COMMENT '宠主用户ID',
    `name`          VARCHAR(50)     NOT NULL                 COMMENT '宠物名称',
    `species`       VARCHAR(20)     NOT NULL                 COMMENT '物种(dog/cat等)',
    `breed`         VARCHAR(50)     DEFAULT NULL             COMMENT '品种',
    `avatar`        VARCHAR(500)    DEFAULT NULL             COMMENT '头像URL',
    `gender`        ENUM('male','female') NOT NULL           COMMENT '性别',
    `age`           INT             DEFAULT NULL             COMMENT '年龄(月)',
    `weight`        DECIMAL(5,1)    DEFAULT NULL             COMMENT '体重(kg)',
    `health_notes`  TEXT            DEFAULT NULL             COMMENT '健康备注',
    `create_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_owner_id` (`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='宠物档案表';

-- 疫苗记录表
DROP TABLE IF EXISTS `vaccine`;
CREATE TABLE `vaccine` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '疫苗ID',
    `pet_id`        BIGINT          NOT NULL                 COMMENT '宠物ID',
    `name`          VARCHAR(100)    NOT NULL                 COMMENT '疫苗名称',
    `date`          DATE            NOT NULL                 COMMENT '接种日期',
    `create_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_pet_id` (`pet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='疫苗记录表';

-- ============================================================
-- 4. 预约服务 (order-service)
-- ============================================================

-- 订单表
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '订单ID',
    `order_no`      VARCHAR(32)     NOT NULL                 COMMENT '订单编号',
    `user_id`       BIGINT          NOT NULL                 COMMENT '宠主用户ID',
    `shop_id`       BIGINT          NOT NULL                 COMMENT '商家ID',
    `pet_id`        BIGINT          NOT NULL                 COMMENT '宠物ID',
    `start_date`    DATE            NOT NULL                 COMMENT '寄养开始日期',
    `end_date`      DATE            NOT NULL                 COMMENT '寄养结束日期',
    `status`        ENUM('pending','paid','boarding','completed','cancelled','refunding','refunded')
                                    NOT NULL DEFAULT 'pending' COMMENT '订单状态',
    `amount`        DECIMAL(10,2)   NOT NULL                 COMMENT '总金额',
    `remark`        VARCHAR(500)    DEFAULT NULL             COMMENT '备注',
    `pay_time`      DATETIME        DEFAULT NULL             COMMENT '支付时间',
    `cancel_time`   DATETIME        DEFAULT NULL             COMMENT '取消时间',
    `cancel_reason` VARCHAR(500)    DEFAULT NULL             COMMENT '取消原因',
    `create_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_shop_id` (`shop_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- ============================================================
-- 5. 支付服务 (payment-service)
-- ============================================================

-- 支付单表
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '支付ID',
    `payment_no`    VARCHAR(32)     NOT NULL                 COMMENT '支付单号',
    `order_id`      BIGINT          NOT NULL                 COMMENT '订单ID',
    `user_id`       BIGINT          NOT NULL                 COMMENT '用户ID',
    `pay_method`    ENUM('wechat','alipay') NOT NULL         COMMENT '支付方式',
    `amount`        DECIMAL(10,2)   NOT NULL                 COMMENT '支付金额',
    `status`        ENUM('pending','success','failed','closed')
                                    NOT NULL DEFAULT 'pending' COMMENT '支付状态',
    `qr_code`       VARCHAR(500)    DEFAULT NULL             COMMENT '二维码',
    `pay_url`       VARCHAR(500)    DEFAULT NULL             COMMENT '支付链接',
    `expire_time`   DATETIME        DEFAULT NULL             COMMENT '过期时间',
    `pay_time`      DATETIME        DEFAULT NULL             COMMENT '支付成功时间',
    `create_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_payment_no` (`payment_no`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付单表';

-- 退款表
DROP TABLE IF EXISTS `refund`;
CREATE TABLE `refund` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '退款ID',
    `order_id`      BIGINT          NOT NULL                 COMMENT '订单ID',
    `payment_id`    BIGINT          NOT NULL                 COMMENT '支付单ID',
    `user_id`       BIGINT          NOT NULL                 COMMENT '用户ID',
    `amount`        DECIMAL(10,2)   NOT NULL                 COMMENT '退款金额',
    `reason`        VARCHAR(500)    DEFAULT NULL             COMMENT '退款原因',
    `status`        ENUM('pending','success','failed')
                                    NOT NULL DEFAULT 'pending' COMMENT '退款状态',
    `create_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退款表';

-- ============================================================
-- 6. 评价服务 (review-service)
-- ============================================================

-- 评价表
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '评价ID',
    `order_id`      BIGINT          NOT NULL                 COMMENT '订单ID',
    `shop_id`       BIGINT          NOT NULL                 COMMENT '商家ID',
    `user_id`       BIGINT          NOT NULL                 COMMENT '用户ID',
    `rating`        TINYINT         NOT NULL                 COMMENT '评分(1-5)',
    `content`       TEXT            DEFAULT NULL             COMMENT '评价内容',
    `photos`        JSON            DEFAULT NULL             COMMENT '照片URL列表',
    `create_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_order_id` (`order_id`),
    KEY `idx_shop_id` (`shop_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评价表';

-- 寄养照片表
DROP TABLE IF EXISTS `boarding_photo`;
CREATE TABLE `boarding_photo` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '照片ID',
    `order_id`      BIGINT          NOT NULL                 COMMENT '订单ID',
    `user_id`       BIGINT          NOT NULL                 COMMENT '上传用户ID',
    `url`           VARCHAR(500)    NOT NULL                 COMMENT '照片URL',
    `description`   VARCHAR(200)    DEFAULT NULL             COMMENT '照片描述',
    `upload_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    PRIMARY KEY (`id`),
    KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='寄养照片表';

-- ============================================================
-- 7. 通知服务 (notify-service)
-- ============================================================

-- 通知消息表
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '通知ID',
    `user_id`       BIGINT          NOT NULL                 COMMENT '用户ID',
    `type`          VARCHAR(50)     NOT NULL                 COMMENT '通知类型',
    `title`         VARCHAR(200)    NOT NULL                 COMMENT '通知标题',
    `content`       TEXT            DEFAULT NULL             COMMENT '通知内容',
    `is_read`       TINYINT(1)      NOT NULL DEFAULT 0       COMMENT '是否已读',
    `create_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id_read` (`user_id`, `is_read`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知消息表';
