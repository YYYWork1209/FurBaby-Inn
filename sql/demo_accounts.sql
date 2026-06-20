-- ============================================
-- 毛孩驿站 - 体验账号及完整测试数据
-- 执行顺序: 先执行 furbaby_inn_init.sql 建表，再执行本文件
-- 角色分离: owner=宠主(下单寄养), shop=商家(经营店铺)
-- ============================================

-- ============================================
-- 第一部分：宠主用户（role=owner，共10人）
-- ============================================

-- 体验账号：宠物主小明
INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138001', '123456', '小明', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/default-avatar.jpg', 'owner', 'xiaoming@furbaby.com', NOW(), NOW());
SET @u1 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138003', 'abc123', '金渐层铲屎官', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/avatar-02.jpg', 'owner', 'owner2@furbaby.com', NOW(), NOW());
SET @u2 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138004', 'abc123', '英短爱好者', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/avatar-03.jpg', 'owner', 'owner3@furbaby.com', NOW(), NOW());
SET @u3 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138005', 'abc123', '边牧家长', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/avatar-04.jpg', 'owner', 'owner4@furbaby.com', NOW(), NOW());
SET @u4 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138006', 'abc123', '金毛麻麻', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/avatar-05.jpg', 'owner', 'owner5@furbaby.com', NOW(), NOW());
SET @u5 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138007', 'abc123', '泰迪小主', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/avatar-06.jpg', 'owner', 'owner6@furbaby.com', NOW(), NOW());
SET @u6 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138008', 'abc123', '暹罗控', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/avatar-07.jpg', 'owner', 'owner7@furbaby.com', NOW(), NOW());
SET @u7 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138009', 'abc123', '布偶猫奴', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/avatar-08.jpg', 'owner', 'owner8@furbaby.com', NOW(), NOW());
SET @u8 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138010', 'abc123', '柴犬阿强', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/avatar-09.jpg', 'owner', 'owner9@furbaby.com', NOW(), NOW());
SET @u9 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138011', 'abc123', '哈士奇勇士', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/avatar-10.jpg', 'owner', 'owner10@furbaby.com', NOW(), NOW());
SET @u10 = LAST_INSERT_ID();

-- ============================================
-- 第二部分：商家用户（role=shop，共6人）
-- ============================================

-- 体验账号：商家老张
INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138002', '123456', '老张宠物店', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-avatar.jpg', 'shop', 'laozhang@furbaby.com', NOW(), NOW());
SET @s1 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138102', 'abc123', '喵星人守护者', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-avatar-02.jpg', 'shop', 'cat@furbaby.com', NOW(), NOW());
SET @s2 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138103', 'abc123', '旺旺大队长', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-avatar-03.jpg', 'shop', 'dog@furbaby.com', NOW(), NOW());
SET @s3 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138104', 'abc123', '毛茸茸之家', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-avatar-04.jpg', 'shop', 'fur@furbaby.com', NOW(), NOW());
SET @s4 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138105', 'abc123', '狗狗训练师老张', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-avatar-05.jpg', 'shop', 'zhang@furbaby.com', NOW(), NOW());
SET @s5 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800138106', 'abc123', '宠物酒店管家', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-avatar-06.jpg', 'shop', 'hotel@furbaby.com', NOW(), NOW());
SET @s6 = LAST_INSERT_ID();

-- ============================================
-- 第三部分：商家店铺（6家）
-- ============================================

-- 商家1: 老张宠物寄养中心（北京）— 体验账号的店铺
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, biz_status, status, create_time, update_time) VALUES
(@s1, '老张宠物寄养中心',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-01.jpg',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-01-a.jpg', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-01-b.jpg'),
 4.7, 158.00,
 JSON_ARRAY('猫咪友好', '狗狗友好', '24小时看护', '接送服务', '监控直播'),
 '北京市海淀区中关村街道xxx号',
 '010-66668888',
 '老张宠物寄养中心位于海淀区核心地段，拥有800平米室内外活动空间。老张本人养宠20年，对猫狗习性了如指掌。店内配备24小时监控和恒温系统，每日定时遛弯和健康检查，让您的毛孩子享受家庭般的温暖。',
 JSON_ARRAY('宠物寄养', '宠物美容', '健康体检', '上门接送', '在线监控'),
 '1. 请提前24小时预约\n2. 宠物需持有有效疫苗证明\n3. 接送服务5公里内免费\n4. 退房时间为离店日14:00前',
 'open', 'approved', NOW(), NOW());
SET @shop1 = LAST_INSERT_ID();

-- 商家2: 喵不可言猫咪主题旅馆（上海）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, biz_status, status, create_time, update_time) VALUES
(@s2, '喵不可言猫咪主题旅馆',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-02.jpg',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-02-a.jpg', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-02-b.jpg'),
 4.9, 299.00,
 JSON_ARRAY('纯猫咪寄养', '猫爬架乐园', '恒温猫房', '专业猫咪护理'),
 '上海市浦东新区陆家嘴街道xxx号',
 '021-68880002',
 '喵不可言是一家只接待猫咪的高端寄养旅馆，每只猫咪拥有独立跃层套房，配备猫爬架、玩具和全景落地窗。专业猫咪行为顾问驻店，让每只猫咪享受尊贵假期。',
 JSON_ARRAY('猫咪寄养', '猫咪美容', '猫咪健康监测', '长毛猫专业护理'),
 '1. 仅接待猫咪\n2. 猫咪需完成基础免疫\n3. 长期寄养（7天以上）享9折',
 'open', 'approved', NOW(), NOW());
SET @shop2 = LAST_INSERT_ID();

-- 商家3: 旺旺乐园宠物度假村（杭州）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, biz_status, status, create_time, update_time) VALUES
(@s3, '旺旺乐园宠物度假村',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-03.jpg',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-03-a.jpg', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-03-b.jpg'),
 4.7, 159.00,
 JSON_ARRAY('大型犬友好', '户外草坪', '宠物游泳池', '接送服务'),
 '杭州市西湖区转塘街道xxx号',
 '0571-88880003',
 '旺旺乐园坐落在西湖景区旁，拥有2000平米户外草坪和宠物专用游泳池。寄养、训练一站式服务，让狗狗在大自然中尽情奔跑。',
 JSON_ARRAY('宠物寄养', '狗狗训练', '宠物游泳', '上门接送', '宠物摄影'),
 '1. 大型犬需佩戴口套入园\n2. 提供免费接送服务（10公里内）\n3. 训练课程需额外预约',
 'open', 'approved', NOW(), NOW());
SET @shop3 = LAST_INSERT_ID();

-- 商家4: 毛茸茸宠物寄养小屋（成都）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, biz_status, status, create_time, update_time) VALUES
(@s4, '毛茸茸宠物寄养小屋',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-04.jpg',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-04-a.jpg', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-04-b.jpg'),
 4.6, 129.00,
 JSON_ARRAY('家庭式寄养', '小宠友好', '温馨舒适', '超高性价比'),
 '成都市武侯区科华北路xxx号',
 '028-88880004',
 '毛茸茸推崇家庭式寄养理念，让宠物像在自己家一样自在。每个房间配有舒适的宠物床和恒温系统，每日提供新鲜手工宠物零食。适合中小型犬和猫咪。',
 JSON_ARRAY('宠物寄养', '手工零食', '日常护理', '宠物托管'),
 '1. 仅接待体重20kg以下的宠物\n2. 寄养超过3天赠送免费洗澡\n3. 节假日加收20%服务费',
 'open', 'approved', NOW(), NOW());
SET @shop4 = LAST_INSERT_ID();

-- 商家5: 小狗当家寄养训练中心（深圳）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, biz_status, status, create_time, update_time) VALUES
(@s5, '小狗当家寄养训练中心',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-05.jpg',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-05-a.jpg', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-05-b.jpg'),
 4.9, 259.00,
 JSON_ARRAY('专业训练', '行为矫正', '寄养+训练', '军犬训导师'),
 '深圳市南山区科技园xxx号',
 '0755-88880005',
 '小狗当家由退役军犬训导师创办，将专业训练融入日常寄养。每只入住狗狗都会接受基础服从训练和行为评估，户外训练场占地3000平米。',
 JSON_ARRAY('宠物寄养', '服从训练', '行为矫正', '敏捷训练', '护卫训练'),
 '1. 训练课程需提前一周预约\n2. 寄养+训练套餐享8.5折\n3. 首次入住有2天适应期',
 'open', 'approved', NOW(), NOW());
SET @shop5 = LAST_INSERT_ID();

-- 商家6: 爱宠屋24h宠物酒店（广州）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, biz_status, status, create_time, update_time) VALUES
(@s6, '爱宠屋24h宠物酒店',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-06.jpg',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-06-a.jpg', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shop-06-b.jpg'),
 4.5, 179.00,
 JSON_ARRAY('24小时营业', '宠物酒店', '医疗护理', '监控直播'),
 '广州市天河区珠江新城xxx号',
 '020-88880006',
 '爱宠屋是华南地区首家24小时宠物酒店，提供全天候寄养和医护服务。每个房间配备高清摄像头，主人可随时查看爱宠动态。店内配有宠物医院，突发状况可立即就医。',
 JSON_ARRAY('宠物寄养', '宠物医疗', '24小时看护', '在线监控', '宠物SPA'),
 '1. 24小时随时办理入住/退房\n2. 医疗费用按实际项目收取\n3. 在线监控服务免费提供',
 'open', 'approved', NOW(), NOW());
SET @shop6 = LAST_INSERT_ID();

-- ============================================
-- 第四部分：商家档期（每家店未来14天）
-- ============================================

-- 商家1: 老张宠物寄养中心，日价158（周末178）
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(@shop1, CURDATE() + INTERVAL  0 DAY, 5, 158.00, NOW(), NOW()),
(@shop1, CURDATE() + INTERVAL  1 DAY, 3, 158.00, NOW(), NOW()),
(@shop1, CURDATE() + INTERVAL  2 DAY, 6, 158.00, NOW(), NOW()),
(@shop1, CURDATE() + INTERVAL  3 DAY, 4, 158.00, NOW(), NOW()),
(@shop1, CURDATE() + INTERVAL  4 DAY, 7, 158.00, NOW(), NOW()),
(@shop1, CURDATE() + INTERVAL  5 DAY, 2, 178.00, NOW(), NOW()),
(@shop1, CURDATE() + INTERVAL  6 DAY, 1, 178.00, NOW(), NOW()),
(@shop1, CURDATE() + INTERVAL  7 DAY, 5, 158.00, NOW(), NOW()),
(@shop1, CURDATE() + INTERVAL  8 DAY, 6, 158.00, NOW(), NOW()),
(@shop1, CURDATE() + INTERVAL  9 DAY, 4, 158.00, NOW(), NOW()),
(@shop1, CURDATE() + INTERVAL 10 DAY, 8, 158.00, NOW(), NOW()),
(@shop1, CURDATE() + INTERVAL 11 DAY, 3, 178.00, NOW(), NOW()),
(@shop1, CURDATE() + INTERVAL 12 DAY, 2, 178.00, NOW(), NOW()),
(@shop1, CURDATE() + INTERVAL 13 DAY, 5, 158.00, NOW(), NOW());

-- 商家2: 喵不可言，日价299（周末349）
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(@shop2, CURDATE() + INTERVAL  0 DAY, 4, 299.00, NOW(), NOW()),
(@shop2, CURDATE() + INTERVAL  1 DAY, 2, 299.00, NOW(), NOW()),
(@shop2, CURDATE() + INTERVAL  2 DAY, 6, 299.00, NOW(), NOW()),
(@shop2, CURDATE() + INTERVAL  3 DAY, 4, 299.00, NOW(), NOW()),
(@shop2, CURDATE() + INTERVAL  4 DAY, 3, 299.00, NOW(), NOW()),
(@shop2, CURDATE() + INTERVAL  5 DAY, 5, 349.00, NOW(), NOW()),
(@shop2, CURDATE() + INTERVAL  6 DAY, 1, 349.00, NOW(), NOW()),
(@shop2, CURDATE() + INTERVAL  7 DAY, 4, 299.00, NOW(), NOW()),
(@shop2, CURDATE() + INTERVAL  8 DAY, 6, 299.00, NOW(), NOW()),
(@shop2, CURDATE() + INTERVAL  9 DAY, 8, 299.00, NOW(), NOW()),
(@shop2, CURDATE() + INTERVAL 10 DAY, 5, 349.00, NOW(), NOW()),
(@shop2, CURDATE() + INTERVAL 11 DAY, 2, 349.00, NOW(), NOW()),
(@shop2, CURDATE() + INTERVAL 12 DAY, 1, 349.00, NOW(), NOW()),
(@shop2, CURDATE() + INTERVAL 13 DAY, 3, 299.00, NOW(), NOW());

-- 商家3: 旺旺乐园，日价159（周末179）
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(@shop3, CURDATE() + INTERVAL  0 DAY, 10, 159.00, NOW(), NOW()),
(@shop3, CURDATE() + INTERVAL  1 DAY, 8,  159.00, NOW(), NOW()),
(@shop3, CURDATE() + INTERVAL  2 DAY, 12, 159.00, NOW(), NOW()),
(@shop3, CURDATE() + INTERVAL  3 DAY, 9,  159.00, NOW(), NOW()),
(@shop3, CURDATE() + INTERVAL  4 DAY, 7,  159.00, NOW(), NOW()),
(@shop3, CURDATE() + INTERVAL  5 DAY, 11, 179.00, NOW(), NOW()),
(@shop3, CURDATE() + INTERVAL  6 DAY, 5,  179.00, NOW(), NOW()),
(@shop3, CURDATE() + INTERVAL  7 DAY, 3,  179.00, NOW(), NOW()),
(@shop3, CURDATE() + INTERVAL  8 DAY, 10, 159.00, NOW(), NOW()),
(@shop3, CURDATE() + INTERVAL  9 DAY, 12, 159.00, NOW(), NOW()),
(@shop3, CURDATE() + INTERVAL 10 DAY, 8,  159.00, NOW(), NOW()),
(@shop3, CURDATE() + INTERVAL 11 DAY, 6,  179.00, NOW(), NOW()),
(@shop3, CURDATE() + INTERVAL 12 DAY, 4,  179.00, NOW(), NOW()),
(@shop3, CURDATE() + INTERVAL 13 DAY, 2,  179.00, NOW(), NOW());

-- 商家4: 毛茸茸小屋，日价129（周末149）
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(@shop4, CURDATE() + INTERVAL  0 DAY, 3, 129.00, NOW(), NOW()),
(@shop4, CURDATE() + INTERVAL  1 DAY, 2, 129.00, NOW(), NOW()),
(@shop4, CURDATE() + INTERVAL  2 DAY, 5, 129.00, NOW(), NOW()),
(@shop4, CURDATE() + INTERVAL  3 DAY, 3, 129.00, NOW(), NOW()),
(@shop4, CURDATE() + INTERVAL  4 DAY, 4, 129.00, NOW(), NOW()),
(@shop4, CURDATE() + INTERVAL  5 DAY, 2, 149.00, NOW(), NOW()),
(@shop4, CURDATE() + INTERVAL  6 DAY, 1, 149.00, NOW(), NOW()),
(@shop4, CURDATE() + INTERVAL  7 DAY, 4, 129.00, NOW(), NOW()),
(@shop4, CURDATE() + INTERVAL  8 DAY, 3, 129.00, NOW(), NOW()),
(@shop4, CURDATE() + INTERVAL  9 DAY, 5, 129.00, NOW(), NOW()),
(@shop4, CURDATE() + INTERVAL 10 DAY, 2, 149.00, NOW(), NOW()),
(@shop4, CURDATE() + INTERVAL 11 DAY, 1, 149.00, NOW(), NOW()),
(@shop4, CURDATE() + INTERVAL 12 DAY, 3, 129.00, NOW(), NOW()),
(@shop4, CURDATE() + INTERVAL 13 DAY, 4, 129.00, NOW(), NOW());

-- 商家5: 小狗当家，日价259（周末289）
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(@shop5, CURDATE() + INTERVAL  0 DAY, 6, 259.00, NOW(), NOW()),
(@shop5, CURDATE() + INTERVAL  1 DAY, 4, 259.00, NOW(), NOW()),
(@shop5, CURDATE() + INTERVAL  2 DAY, 8, 259.00, NOW(), NOW()),
(@shop5, CURDATE() + INTERVAL  3 DAY, 5, 259.00, NOW(), NOW()),
(@shop5, CURDATE() + INTERVAL  4 DAY, 7, 259.00, NOW(), NOW()),
(@shop5, CURDATE() + INTERVAL  5 DAY, 3, 289.00, NOW(), NOW()),
(@shop5, CURDATE() + INTERVAL  6 DAY, 2, 289.00, NOW(), NOW()),
(@shop5, CURDATE() + INTERVAL  7 DAY, 6, 259.00, NOW(), NOW()),
(@shop5, CURDATE() + INTERVAL  8 DAY, 8, 259.00, NOW(), NOW()),
(@shop5, CURDATE() + INTERVAL  9 DAY, 5, 259.00, NOW(), NOW()),
(@shop5, CURDATE() + INTERVAL 10 DAY, 4, 289.00, NOW(), NOW()),
(@shop5, CURDATE() + INTERVAL 11 DAY, 3, 289.00, NOW(), NOW()),
(@shop5, CURDATE() + INTERVAL 12 DAY, 1, 289.00, NOW(), NOW()),
(@shop5, CURDATE() + INTERVAL 13 DAY, 6, 259.00, NOW(), NOW());

-- 商家6: 爱宠屋，日价179（周末199）
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(@shop6, CURDATE() + INTERVAL  0 DAY, 8,  179.00, NOW(), NOW()),
(@shop6, CURDATE() + INTERVAL  1 DAY, 6,  179.00, NOW(), NOW()),
(@shop6, CURDATE() + INTERVAL  2 DAY, 10, 179.00, NOW(), NOW()),
(@shop6, CURDATE() + INTERVAL  3 DAY, 7,  179.00, NOW(), NOW()),
(@shop6, CURDATE() + INTERVAL  4 DAY, 9,  179.00, NOW(), NOW()),
(@shop6, CURDATE() + INTERVAL  5 DAY, 4,  199.00, NOW(), NOW()),
(@shop6, CURDATE() + INTERVAL  6 DAY, 3,  199.00, NOW(), NOW()),
(@shop6, CURDATE() + INTERVAL  7 DAY, 2,  199.00, NOW(), NOW()),
(@shop6, CURDATE() + INTERVAL  8 DAY, 8,  179.00, NOW(), NOW()),
(@shop6, CURDATE() + INTERVAL  9 DAY, 10, 179.00, NOW(), NOW()),
(@shop6, CURDATE() + INTERVAL 10 DAY, 6,  179.00, NOW(), NOW()),
(@shop6, CURDATE() + INTERVAL 11 DAY, 5,  199.00, NOW(), NOW()),
(@shop6, CURDATE() + INTERVAL 12 DAY, 3,  199.00, NOW(), NOW()),
(@shop6, CURDATE() + INTERVAL 13 DAY, 1,  199.00, NOW(), NOW());

-- ============================================
-- 第五部分：宠物档案（10位宠主，12只宠物）
-- ============================================

-- 小明的宠物（体验账号 @u1）
INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@u1, '团子', 'cat', '金渐层', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/golden-shaded.jpg', 'male', 24, 4.5, '已绝育，定期驱虫，性格温顺', NOW(), NOW());
SET @pet1 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@u1, '布丁', 'dog', '柯基', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/corgi.jpg', 'female', 18, 10.2, '疫苗齐全，活泼好动，喜欢社交', NOW(), NOW());
SET @pet2 = LAST_INSERT_ID();

-- 其他宠主的宠物
INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@u3, '蓝宝', 'cat', '英短蓝猫', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/british-shorthair.jpg', 'female', 12, 3.8, '已绝育，胆小猫，需安静环境', NOW(), NOW());
SET @pet3 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@u4, '闪电', 'dog', '边境牧羊犬', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/border-collie.jpg', 'male', 36, 18.5, '聪明好动，需大量运动，会基础指令', NOW(), NOW());
SET @pet4 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@u4, '雪球', 'cat', '布偶猫', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/ragdoll.jpg', 'female', 8, 2.8, '粘人爱撒娇，需每日梳毛', NOW(), NOW());
SET @pet5 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@u5, '毛球', 'dog', '金毛', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/golden-retriever.jpg', 'male', 30, 28.0, '温和友善，髋关节需关注，饮食控制中', NOW(), NOW());
SET @pet6 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@u6, '豆豆', 'dog', '泰迪', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/toy-poodle.jpg', 'female', 14, 4.2, '已绝育，怕冷，需要穿衣服', NOW(), NOW());
SET @pet7 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@u6, '奶茶', 'cat', '暹罗猫', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/siamese.jpg', 'male', 20, 5.0, '话多粘人，已绝育，对海鲜过敏', NOW(), NOW());
SET @pet8 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@u8, '咪咪', 'cat', '布偶猫', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/ragdoll-02.jpg', 'female', 10, 3.2, '性格温顺，长毛需定期护理', NOW(), NOW());
SET @pet9 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@u9, '旺财', 'dog', '柴犬', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/shiba-inu.jpg', 'male', 16, 9.5, '独立性强，已绝育，挑食', NOW(), NOW());
SET @pet10 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@u10, '熊二', 'dog', '哈士奇', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/husky.jpg', 'male', 22, 22.0, '精力旺盛，拆家能手，需大量运动', NOW(), NOW());
SET @pet11 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(@u10, '三胖', 'cat', '橘猫', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/orange-cat.jpg', 'male', 36, 7.5, '超重，需控制饮食，性格懒散', NOW(), NOW());
SET @pet12 = LAST_INSERT_ID();

-- ============================================
-- 第六部分：疫苗记录
-- ============================================

-- 团子（猫）
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(@pet1, '猫三联（第一针）', '2025-01-15', NOW()),
(@pet1, '猫三联（加强针）', '2025-02-15', NOW()),
(@pet1, '狂犬疫苗',         '2025-03-15', NOW());

-- 布丁（狗）
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(@pet2, '犬五联（第一针）', '2025-02-01', NOW()),
(@pet2, '犬五联（加强针）', '2025-03-01', NOW()),
(@pet2, '狂犬疫苗',         '2025-04-01', NOW()),
(@pet2, '犬窝咳疫苗',       '2025-05-01', NOW());

-- 蓝宝（猫）
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(@pet3, '猫三联（第一针）', '2025-06-01', NOW()),
(@pet3, '猫三联（加强针）', '2025-07-01', NOW()),
(@pet3, '狂犬疫苗',         '2025-08-01', NOW());

-- 闪电（狗）
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(@pet4, '犬五联（第一针）', '2024-06-10', NOW()),
(@pet4, '犬五联（加强针）', '2024-07-10', NOW()),
(@pet4, '狂犬疫苗',         '2024-08-10', NOW()),
(@pet4, '犬窝咳疫苗',       '2024-09-10', NOW()),
(@pet4, '犬钩端螺旋体疫苗', '2024-10-10', NOW());

-- 雪球（猫）
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(@pet5, '猫三联（第一针）', '2025-09-01', NOW()),
(@pet5, '猫三联（加强针）', '2025-10-01', NOW()),
(@pet5, '狂犬疫苗',         '2025-11-01', NOW());

-- 毛球（狗）
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(@pet6, '犬五联（第一针）', '2024-08-20', NOW()),
(@pet6, '犬五联（加强针）', '2024-09-20', NOW()),
(@pet6, '狂犬疫苗',         '2024-10-20', NOW()),
(@pet6, '犬窝咳疫苗',       '2025-03-20', NOW());

-- 豆豆（狗）
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(@pet7, '犬五联（第一针）', '2025-04-01', NOW()),
(@pet7, '犬五联（加强针）', '2025-05-01', NOW()),
(@pet7, '狂犬疫苗',         '2025-06-01', NOW());

-- 奶茶（猫）
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(@pet8, '猫三联（第一针）', '2025-03-15', NOW()),
(@pet8, '猫三联（加强针）', '2025-04-15', NOW()),
(@pet8, '狂犬疫苗',         '2025-05-15', NOW());

-- 咪咪（猫）
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(@pet9, '猫三联（第一针）', '2025-07-20', NOW()),
(@pet9, '猫三联（加强针）', '2025-08-20', NOW()),
(@pet9, '狂犬疫苗',         '2025-09-20', NOW());

-- 旺财（狗）
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(@pet10, '犬五联（第一针）', '2025-01-10', NOW()),
(@pet10, '犬五联（加强针）', '2025-02-10', NOW()),
(@pet10, '狂犬疫苗',         '2025-03-10', NOW()),
(@pet10, '犬窝咳疫苗',       '2025-06-10', NOW());

-- 熊二（狗）
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(@pet11, '犬五联（第一针）', '2024-11-01', NOW()),
(@pet11, '犬五联（加强针）', '2024-12-01', NOW()),
(@pet11, '狂犬疫苗',         '2025-01-01', NOW()),
(@pet11, '犬钩端螺旋体疫苗', '2025-02-01', NOW());

-- 三胖（猫）
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(@pet12, '猫三联（第一针）', '2024-04-10', NOW()),
(@pet12, '猫三联（加强针）', '2024-05-10', NOW()),
(@pet12, '狂犬疫苗',         '2024-06-10', NOW());

-- ============================================
-- 第七部分：订单（各状态，均为宠主角色下单）
-- ============================================

-- 已完成订单
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260601001', @u1, @shop1, @pet1, '2026-06-01', '2026-06-03', 'completed', 474.00, '团子第一次寄养体验', '2026-05-31 14:30:00', '2026-05-30 10:00:00', '2026-06-03 18:00:00');
SET @order1 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260605002', @u4, @shop2, @pet4, '2026-06-05', '2026-06-08', 'completed', 1196.00, '闪电参加训练营', '2026-06-04 09:00:00', '2026-06-03 19:00:00', '2026-06-08 12:00:00');
SET @order2 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260610003', @u5, @shop3, @pet6, '2026-06-10', '2026-06-12', 'completed', 477.00, NULL, '2026-06-09 20:00:00', '2026-06-09 08:00:00', '2026-06-12 15:00:00');
SET @order3 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260612004', @u3, @shop6, @pet3, '2026-06-12', '2026-06-15', 'completed', 537.00, '蓝宝胆子小请多关照', '2026-06-11 11:00:00', '2026-06-10 15:00:00', '2026-06-15 10:00:00');
SET @order4 = LAST_INSERT_ID();

-- 寄养中
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260616005', @u6, @shop4, @pet7, '2026-06-16', '2026-06-22', 'boarding', 903.00, '豆豆怕冷，请保持室温', '2026-06-15 16:00:00', '2026-06-14 10:00:00', '2026-06-16 09:00:00');
SET @order5 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260618006', @u9, @shop5, @pet10, '2026-06-18', '2026-06-25', 'boarding', 2072.00, '寄养+服从训练套餐', '2026-06-17 12:00:00', '2026-06-16 08:00:00', '2026-06-18 08:00:00');
SET @order6 = LAST_INSERT_ID();

-- 已支付（待开始）
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260620007', @u1, @shop2, @pet2, '2026-06-22', '2026-06-24', 'paid', 897.00, '布丁喜欢社交', '2026-06-19 10:00:00', '2026-06-18 14:00:00', '2026-06-19 10:00:00');
SET @order7 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260619008', @u10, @shop3, @pet11, '2026-06-23', '2026-06-25', 'paid', 477.00, NULL, '2026-06-19 08:00:00', '2026-06-18 20:00:00', '2026-06-19 08:00:00');
SET @order8 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260620009', @u4, @shop1, @pet5, '2026-06-24', '2026-06-26', 'paid', 474.00, '雪球需要每日梳毛', '2026-06-19 18:00:00', '2026-06-19 16:00:00', '2026-06-19 18:00:00');
SET @order9 = LAST_INSERT_ID();

-- 待支付
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, create_time, update_time) VALUES
('ORD20260620010', @u5, @shop6, @pet6, '2026-06-25', '2026-06-27', 'pending', 537.00, '毛球需要控制饮食', '2026-06-19 12:00:00', '2026-06-19 12:00:00');
SET @order10 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, create_time, update_time) VALUES
('ORD20260620011', @u8, @shop4, @pet9, '2026-06-26', '2026-06-28', 'pending', 387.00, NULL, '2026-06-20 08:00:00', '2026-06-20 08:00:00');
SET @order11 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, create_time, update_time) VALUES
('ORD20260620012', @u3, @shop2, @pet3, '2026-06-27', '2026-06-29', 'pending', 897.00, '蓝宝再次寄养，老顾客', '2026-06-20 09:00:00', '2026-06-20 09:00:00');
SET @order12 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, create_time, update_time) VALUES
('ORD20260620013', @u10, @shop5, @pet12, '2026-06-25', '2026-06-26', 'pending', 259.00, '三胖超重请按食谱喂养', '2026-06-20 10:00:00', '2026-06-20 10:00:00');
SET @order13 = LAST_INSERT_ID();

-- 已取消
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, cancel_time, cancel_reason, create_time, update_time) VALUES
('ORD20260609014', @u6, @shop1, @pet7, '2026-06-14', '2026-06-16', 'cancelled', 316.00, NULL, '2026-06-12 10:00:00', '行程变更，取消预订', '2026-06-08 15:00:00', '2026-06-12 10:00:00');
SET @order14 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, cancel_time, cancel_reason, create_time, update_time) VALUES
('ORD20260615015', @u5, @shop4, @pet6, '2026-06-18', '2026-06-20', 'cancelled', 258.00, NULL, '2026-06-16 14:00:00', '宠物身体不适，需要就医', '2026-06-14 10:00:00', '2026-06-16 14:00:00');
SET @order15 = LAST_INSERT_ID();

-- 退款中
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260618016', @u3, @shop3, @pet3, '2026-06-20', '2026-06-22', 'refunding', 477.00, '蓝宝生病需退款', '2026-06-17 20:00:00', '2026-06-17 18:00:00', '2026-06-19 09:00:00');
SET @order16 = LAST_INSERT_ID();

-- 已退款
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260610017', @u10, @shop6, @pet12, '2026-06-15', '2026-06-17', 'refunded', 537.00, '商家档期满无法接待', '2026-06-09 12:00:00', '2026-06-08 10:00:00', '2026-06-11 16:00:00');
SET @order17 = LAST_INSERT_ID();

-- ============================================
-- 第八部分：支付单
-- ============================================

INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026053101', @order1, @u1,  'wechat', 474.00,  'success', '2026-05-31 14:30:00', '2026-05-31 14:25:00', '2026-05-31 14:30:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026060402', @order2, @u4,  'alipay', 1196.00, 'success', '2026-06-04 09:00:00', '2026-06-04 08:55:00', '2026-06-04 09:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026060903', @order3, @u5,  'wechat', 477.00,  'success', '2026-06-09 20:00:00', '2026-06-09 19:50:00', '2026-06-09 20:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026061104', @order4, @u3,  'wechat', 537.00,  'success', '2026-06-11 11:00:00', '2026-06-11 10:55:00', '2026-06-11 11:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026061505', @order5, @u6,  'alipay', 903.00,  'success', '2026-06-15 16:00:00', '2026-06-15 15:50:00', '2026-06-15 16:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026061706', @order6, @u9,  'wechat', 2072.00, 'success', '2026-06-17 12:00:00', '2026-06-17 11:45:00', '2026-06-17 12:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026061907', @order7, @u1,  'wechat', 897.00,  'success', '2026-06-19 10:00:00', '2026-06-19 09:55:00', '2026-06-19 10:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026061908', @order8, @u10, 'alipay', 477.00,  'success', '2026-06-19 08:00:00', '2026-06-19 07:55:00', '2026-06-19 08:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026061909', @order9, @u4,  'wechat', 474.00,  'success', '2026-06-19 18:00:00', '2026-06-19 17:50:00', '2026-06-19 18:00:00');

-- 取消订单的支付记录
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY20260610014', @order14, @u6, 'wechat', 316.00, 'success', '2026-06-10 10:00:00', '2026-06-10 09:55:00', '2026-06-10 10:00:00');
SET @pay14 = LAST_INSERT_ID();

INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY20260615015', @order15, @u5, 'alipay', 258.00, 'success', '2026-06-15 08:00:00', '2026-06-15 07:55:00', '2026-06-15 08:00:00');
SET @pay15 = LAST_INSERT_ID();

INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY20260617016', @order16, @u3, 'alipay', 477.00, 'success', '2026-06-17 20:00:00', '2026-06-17 19:55:00', '2026-06-17 20:00:00');
SET @pay16 = LAST_INSERT_ID();

INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY20260609017', @order17, @u10, 'wechat', 537.00, 'success', '2026-06-09 12:00:00', '2026-06-09 11:55:00', '2026-06-09 12:00:00');
SET @pay17 = LAST_INSERT_ID();

-- ============================================
-- 第九部分：退款记录
-- ============================================

INSERT INTO refund (order_id, payment_id, user_id, amount, reason, status, create_time, update_time) VALUES
(@order14, @pay14, @u6,  316.00, '行程变更，全额退款', 'success', '2026-06-12 10:30:00', '2026-06-12 10:30:00');
INSERT INTO refund (order_id, payment_id, user_id, amount, reason, status, create_time, update_time) VALUES
(@order15, @pay15, @u5,  258.00, '宠物身体不适无法寄养，全额退款', 'success', '2026-06-16 15:00:00', '2026-06-16 15:00:00');
INSERT INTO refund (order_id, payment_id, user_id, amount, reason, status, create_time, update_time) VALUES
(@order16, @pay16, @u3,  477.00, '宠物突然生病无法寄养，申请退款', 'pending', '2026-06-19 09:00:00', '2026-06-19 09:00:00');
INSERT INTO refund (order_id, payment_id, user_id, amount, reason, status, create_time, update_time) VALUES
(@order17, @pay17, @u10, 537.00, '商家档期满无法接待，协商退款', 'success', '2026-06-10 09:00:00', '2026-06-11 16:00:00');

-- ============================================
-- 第十部分：评价
-- ============================================

INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order1, @shop1, @u1, 5, '环境很好，团子玩得很开心，每天都能收到照片反馈，下次还来！',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/review-photo-01.jpg'),
 '2026-06-04 10:00:00');
INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order2, @shop2, @u4, 5, '闪电参加了训练营，回来真的乖了很多！专业度满分，强烈推荐给养边牧的朋友。',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/review-photo-02.jpg', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/review-photo-03.jpg'),
 '2026-06-09 14:00:00');
INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order3, @shop3, @u5, 4, '旺旺乐园场地很大，毛球跑得很尽兴。就是接送服务稍微迟到了半小时，总体不错。',
 NULL,
 '2026-06-13 11:00:00');
INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order4, @shop6, @u3, 5, '蓝宝胆子很小但是店员照顾得很细心，监控功能太方便了，随时能看到它！',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/review-photo-04.jpg'),
 '2026-06-16 09:00:00');
INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order5, @shop4, @u6, 4, '毛茸茸小屋虽然不大但很温馨，豆豆穿上小衣服特别可爱。价格也很实惠！',
 NULL,
 '2026-06-18 20:00:00');
INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order6, @shop5, @u9, 5, '旺财参加寄养+训练套餐效果显著！之前挑食的毛病改善了很多，张教练很专业。',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/review-photo-05.jpg'),
 '2026-06-19 10:00:00');
INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order17, @shop6, @u10, 3, '虽然没寄养成，但退款处理很及时，客服态度也不错。下次再试试。', NULL, '2026-06-12 14:00:00');

-- ============================================
-- 第十一部分：评价完成后更新商家评分
-- ============================================

UPDATE shop SET rating = (SELECT ROUND(AVG(rating), 1) FROM review WHERE shop_id = @shop1) WHERE id = @shop1;
UPDATE shop SET rating = (SELECT ROUND(AVG(rating), 1) FROM review WHERE shop_id = @shop2) WHERE id = @shop2;
UPDATE shop SET rating = (SELECT ROUND(AVG(rating), 1) FROM review WHERE shop_id = @shop3) WHERE id = @shop3;
UPDATE shop SET rating = (SELECT ROUND(AVG(rating), 1) FROM review WHERE shop_id = @shop4) WHERE id = @shop4;
UPDATE shop SET rating = (SELECT ROUND(AVG(rating), 1) FROM review WHERE shop_id = @shop5) WHERE id = @shop5;
UPDATE shop SET rating = (SELECT ROUND(AVG(rating), 1) FROM review WHERE shop_id = @shop6) WHERE id = @shop6;

-- ============================================
-- 第十二部分：寄养照片
-- ============================================

INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order1, @s1, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/boarding-01.jpg', '团子在新环境里探索', '2026-06-01 15:00:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order1, @s1, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/boarding-02.jpg', '团子晒太阳', '2026-06-02 10:30:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order2, @s2, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/boarding-03.jpg', '闪电在训练场', '2026-06-05 16:00:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order2, @s2, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/boarding-04.jpg', '闪电和同伴玩耍', '2026-06-07 09:00:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order3, @s3, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/boarding-05.jpg', '毛球在大草坪奔跑', '2026-06-11 14:00:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order4, @s6, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/boarding-06.jpg', '蓝宝的豪华套房', '2026-06-13 11:00:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order5, @s4, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/boarding-07.jpg', '豆豆专属小窝', '2026-06-17 10:00:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order6, @s5, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/boarding-08.jpg', '旺财训练中', '2026-06-19 08:00:00');

-- ============================================
-- 第十三部分：通知消息
-- ============================================

-- 商家收到的通知（由系统发送，user_id 为商家用户ID）
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@s1, 'system_welcome', '欢迎入驻毛孩驿站', '恭喜您成功开通商家"老张宠物寄养中心"，请完善店铺信息并发布档期！', 0, NOW());
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@s1, 'order_new',     '新订单提醒',       '您的店铺收到一笔新预约（团子，6月1-3日）', 1, '2026-05-30 10:05:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@s1, 'payment_done',  '支付到账',         '订单 ORD20260601001 已支付 ¥474.00',       1, '2026-05-31 14:31:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@s1, 'review_new',    '新评价提醒',       '用户小明给您留下了一条5星评价',               0, '2026-06-04 10:01:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@s2, 'order_new',     '新订单提醒',       '喵不可言收到一笔新预约（闪电，6月5-8日）',   1, '2026-06-03 19:05:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@s2, 'review_new',    '新评价提醒',       '用户边牧家长给您留下了一条5星评价',           0, '2026-06-09 14:01:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@s3, 'order_new',     '新订单提醒',       '旺旺乐园收到一笔新预约（毛球，6月10-12日）', 1, '2026-06-09 08:05:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@s4, 'order_new',     '新订单提醒',       '毛茸茸小屋收到一笔新预约（豆豆，6月16-22日）',1, '2026-06-14 10:05:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@s5, 'order_new',     '新订单提醒',       '小狗当家收到一笔新预约（旺财，6月18-25日）', 1, '2026-06-16 08:05:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@s6, 'review_new',    '新评价提醒',       '用户英短爱好者给您留下了一条5星评价',         0, '2026-06-16 09:01:00');

-- 宠主收到的通知（user_id 为宠主用户ID）
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u1, 'system_welcome', '欢迎加入毛孩驿站', '欢迎来到毛孩驿站！您可以浏览商家、为宠物预约寄养服务。', 0, NOW());
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u1, 'payment_done',   '支付成功',         '订单 ORD20260601001 支付成功，¥474.00',              1, '2026-05-31 14:32:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u1, 'boarding_start', '寄养开始',         '团子已入住老张宠物寄养中心，将定时为您推送照片',         1, '2026-06-01 09:30:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u1, 'boarding_end',   '寄养结束',         '团子的寄养服务已结束，欢迎评价本次体验！',              1, '2026-06-03 18:01:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u4, 'payment_done',   '支付成功',         '订单 ORD20260605002 支付成功，¥1196.00',             1, '2026-06-04 09:01:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u4, 'photo_new',      '新照片提醒',       '闪电的寄养照片已更新（2张）',                          1, '2026-06-07 09:05:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u5, 'order_pending',  '待支付提醒',       '订单 ORD20260610003 待支付，请在24小时内完成',          1, '2026-06-09 08:01:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u6, 'boarding_start', '寄养开始',         '豆豆已入住毛茸茸宠物寄养小屋',                         1, '2026-06-16 09:30:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u3, 'boarding_end',   '寄养结束',         '蓝宝的寄养服务已结束，欢迎评价本次体验！',              1, '2026-06-15 10:01:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u9, 'payment_done',   '支付成功',         '订单 ORD20260618006 支付成功，¥2072.00',             1, '2026-06-17 12:01:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u10,'order_pending',  '待支付提醒',       '订单 ORD20260620013 待支付，请在24小时内完成',          0, '2026-06-20 08:01:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u6, 'refund_done',    '退款到账',         '订单 ORD20260609014 退款 ¥316.00 已到账',              1, '2026-06-12 10:31:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u5, 'refund_done',    '退款到账',         '订单 ORD20260615015 退款 ¥258.00 已到账',              1, '2026-06-16 15:01:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u3, 'refund_apply',   '退款申请已提交',   '订单 ORD20260618016 退款申请已提交，处理中',             0, '2026-06-19 09:01:00');
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(@u10,'refund_done',    '退款到账',         '订单 ORD20260610017 退款 ¥537.00 已到账',              1, '2026-06-11 16:01:00');
