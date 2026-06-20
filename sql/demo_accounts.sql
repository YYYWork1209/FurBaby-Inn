-- ============================================
-- 毛孩驿站 - 体验账号及关联数据
-- ============================================

-- ============================================
-- 第一部分：用户账号
-- ============================================

-- 宠物主体验账号
INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138001', '123456', '小明', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/default-avatar.jpg', 'owner', 'xiaoming@furbaby.com', NOW(), NOW());
SET @owner_uid = LAST_INSERT_ID();

-- 商家体验账号
INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138002', '123456', '老张宠物店', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/default-avatar.jpg', 'shop', 'laozhang@furbaby.com', NOW(), NOW());
SET @shop_uid = LAST_INSERT_ID();

-- ============================================
-- 第二部分：商家店铺
-- ============================================

INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, status, create_time, update_time) VALUES
(@shop_uid, '老张宠物寄养中心',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-avatar.jpg',
 JSON_ARRAY(
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-photo-1.jpg',
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-photo-2.jpg',
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-photo-3.jpg'
 ),
 4.7, 158.00,
 JSON_ARRAY('猫咪友好', '狗狗友好', '24小时看护', '接送服务', '监控直播'),
 '北京市海淀区中关村街道xxx号',
 '010-66668888',
 '老张宠物寄养中心位于海淀区核心地段，拥有800平米室内外活动空间。老张本人养宠20年，对猫狗习性了如指掌。店内配备24小时监控和恒温系统，每日定时遛弯和健康检查，让您的毛孩子享受家庭般的温暖。提供早晚接送服务，方便上班族。',
 JSON_ARRAY('宠物寄养', '宠物美容', '健康体检', '上门接送', '在线监控'),
 '1. 请提前24小时预约\n2. 宠物需持有有效疫苗证明\n3. 建议自带宠物惯用粮\n4. 接送服务5公里内免费\n5. 退房时间为离店日14:00前',
 'approved', NOW(), NOW());
SET @shop_id = LAST_INSERT_ID();

-- ============================================
-- 第三部分：商家档期（未来14天）
-- ============================================

INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(@shop_id, CURDATE() + INTERVAL  0 DAY, 5, 158.00, NOW(), NOW()),
(@shop_id, CURDATE() + INTERVAL  1 DAY, 3, 158.00, NOW(), NOW()),
(@shop_id, CURDATE() + INTERVAL  2 DAY, 6, 158.00, NOW(), NOW()),
(@shop_id, CURDATE() + INTERVAL  3 DAY, 4, 158.00, NOW(), NOW()),
(@shop_id, CURDATE() + INTERVAL  4 DAY, 7, 158.00, NOW(), NOW()),
(@shop_id, CURDATE() + INTERVAL  5 DAY, 2, 178.00, NOW(), NOW()),
(@shop_id, CURDATE() + INTERVAL  6 DAY, 1, 178.00, NOW(), NOW()),
(@shop_id, CURDATE() + INTERVAL  7 DAY, 5, 158.00, NOW(), NOW()),
(@shop_id, CURDATE() + INTERVAL  8 DAY, 6, 158.00, NOW(), NOW()),
(@shop_id, CURDATE() + INTERVAL  9 DAY, 4, 158.00, NOW(), NOW()),
(@shop_id, CURDATE() + INTERVAL 10 DAY, 8, 158.00, NOW(), NOW()),
(@shop_id, CURDATE() + INTERVAL 11 DAY, 3, 178.00, NOW(), NOW()),
(@shop_id, CURDATE() + INTERVAL 12 DAY, 2, 178.00, NOW(), NOW()),
(@shop_id, CURDATE() + INTERVAL 13 DAY, 5, 158.00, NOW(), NOW());

-- ============================================
-- 第四部分：宠物主名下宠物
-- ============================================

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@owner_uid, '团子', 'cat', '金渐层', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/golden-shaded.jpg', 'male', 24, 4.5, '已绝育，定期驱虫，性格温顺粘人', NOW(), NOW());
SET @pet1 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@owner_uid, '布丁', 'dog', '柯基', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/corgi.jpg', 'female', 18, 10.2, '疫苗齐全，活泼好动，喜欢社交，对鸡肉过敏', NOW(), NOW());
SET @pet2 = LAST_INSERT_ID();

-- ============================================
-- 第五部分：给商家用户也添加一只宠物（演示用）
-- ============================================

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@shop_uid, '旺财', 'dog', '金毛', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/golden-retriever.jpg', 'male', 30, 28.0, '温和友善，已绝育，髋关节需定期检查', NOW(), NOW());
SET @pet3 = LAST_INSERT_ID();

-- ============================================
-- 第六部分：示例通知消息
-- ============================================

-- 商家的通知
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@shop_uid, 'system_welcome', '欢迎入驻毛孩驿站', '恭喜您成功开通商家，请完善店铺信息并发布档期，开始接单吧！', 0, NOW());
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@shop_uid, 'shop_approved', '店铺审核通过', '您的店铺"老张宠物寄养中心"已通过审核，现在可以正常接单了。', 0, NOW());

-- 宠物主的通知
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@owner_uid, 'system_welcome', '欢迎加入毛孩驿站', '欢迎来到毛孩驿站！您可以浏览商家、为宠物预约寄养服务，祝您使用愉快。', 0, NOW());
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@owner_uid, 'pet_reminder', '完善宠物档案', '您已添加宠物"团子"和"布丁"，记得补充疫苗记录哦～', 0, NOW());
