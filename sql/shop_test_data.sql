-- ============================================
-- 毛孩驿站 - 商家测试数据
-- ============================================

-- 1. 插入商家用户（role=shop）
INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800001111', 'abc123', '宠物达人小王', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg', 'shop', 'wang@furbaby.com', NOW(), NOW());
SET @user1 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800002222', 'abc123', '喵星人守护者', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg', 'shop', 'cat@furbaby.com', NOW(), NOW());
SET @user2 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800003333', 'abc123', '旺旺大队长', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg', 'shop', 'dog@furbaby.com', NOW(), NOW());
SET @user3 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800004444', 'abc123', '毛茸茸之家', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg', 'shop', 'fur@furbaby.com', NOW(), NOW());
SET @user4 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800005555', 'abc123', '狗狗训练师老张', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg', 'shop', 'zhang@furbaby.com', NOW(), NOW());
SET @user5 = LAST_INSERT_ID();

INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13800006666', 'abc123', '宠物酒店管家', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg', 'shop', 'hotel@furbaby.com', NOW(), NOW());
SET @user6 = LAST_INSERT_ID();

-- 2. 插入商家店铺（status=approved 已审核通过）

-- 商家1: 宠爱有家宠物寄养中心（北京）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, status, create_time, update_time) VALUES
(@user1, '宠爱有家宠物寄养中心',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg',
 JSON_ARRAY(
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AF%84%E5%85%BB%E7%8E%AF%E5%A2%83.jpg',
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A0%E7%89%A9%E6%88%BF%E9%97%B4.jpg'
 ),
 4.8, 199.00,
 JSON_ARRAY('猫咪友好', '24小时看护', '超大活动空间', '专业营养餐'),
 '北京市朝阳区望京街道xxx号',
 '010-88880001',
 '宠爱有家是一家拥有10年宠物寄养经验的专业机构，提供舒适独立的宠物房间，每日定时遛弯和健康监测，让您的毛孩子享受五星级假期。店内配有专业兽医巡诊，确保每只宠物的健康与安全。',
 JSON_ARRAY('宠物寄养', '宠物美容', '健康体检', '行为训练'),
 '1. 请提前48小时预约\n2. 宠物需持有有效疫苗证明\n3. 建议自带宠物惯用粮\n4. 特殊饮食需求请提前告知\n5. 退房时间为离店日12:00前',
 'approved', NOW(), NOW());

-- 商家2: 喵不可言猫咪主题旅馆（上海）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, status, create_time, update_time) VALUES
(@user2, '喵不可言猫咪主题旅馆',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg',
 JSON_ARRAY(
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%8C%AB%E5%92%AA%E4%B8%93%E5%8C%BA.jpg',
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A0%E7%89%A9%E7%8E%A9%E5%85%B7.jpg'
 ),
 4.9, 299.00,
 JSON_ARRAY('纯猫咪寄养', '猫爬架乐园', '恒温猫房', '专业猫咪护理'),
 '上海市浦东新区陆家嘴街道xxx号',
 '021-68880002',
 '喵不可言是一家只接待猫咪的高端寄养旅馆，每只猫咪拥有独立跃层套房，配备猫爬架、玩具和全景落地窗。我们提供新鲜刺身级猫粮，并有专业猫咪行为顾问驻店，让每只猫咪享受尊贵假期。',
 JSON_ARRAY('猫咪寄养', '猫咪美容', '猫咪健康监测', '长毛猫专业护理'),
 '1. 仅接待猫咪，不接受其他宠物\n2. 猫咪需完成基础免疫\n3. 需自备猫咪常用粮以防肠胃不适\n4. 提供每日猫咪照片/视频反馈\n5. 长期寄养（7天以上）享9折优惠',
 'approved', NOW(), NOW());

-- 商家3: 旺旺乐园宠物度假村（杭州）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, status, create_time, update_time) VALUES
(@user3, '旺旺乐园宠物度假村',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg',
 JSON_ARRAY(
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%8B%97%E7%8B%97%E4%B9%90%E5%9B%AD.jpg',
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E6%88%B7%E5%A4%96%E6%B4%BB%E5%8A%A8.jpg'
 ),
 4.7, 159.00,
 JSON_ARRAY('大型犬友好', '户外草坪', '宠物游泳池', '接送服务'),
 '杭州市西湖区转塘街道xxx号',
 '0571-88880003',
 '旺旺乐园坐落在西湖景区旁，拥有2000平米户外草坪和宠物专用游泳池。我们提供寄养、训练一站式服务，让狗狗在大自然中尽情奔跑。每晚有篝火社交时间，让宠物们结交新朋友。',
 JSON_ARRAY('宠物寄养', '狗狗训练', '宠物游泳', '上门接送', '宠物摄影'),
 '1. 大型犬需佩戴口套入园\n2. 提供免费接送服务（10公里内）\n3. 训练课程需额外预约\n4. 游泳需主人签署安全协议\n5. 建议自带宠物玩具',
 'approved', NOW(), NOW());

-- 商家4: 毛茸茸宠物寄养小屋（成都）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, status, create_time, update_time) VALUES
(@user4, '毛茸茸宠物寄养小屋',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg',
 JSON_ARRAY(
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E6%B8%A9%E9%A6%A8%E5%AE%A0%E7%89%A9%E9%97%B4.jpg',
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A0%E7%89%A9%E5%AF%84%E5%85%BB.jpg'
 ),
 4.6, 129.00,
 JSON_ARRAY('家庭式寄养', '小宠友好', '温馨舒适', '超高性价比'),
 '成都市武侯区科华北路xxx号',
 '028-88880004',
 '毛茸茸宠物寄养小屋推崇家庭式寄养理念，让宠物像在自己家一样自在。每个房间都配有舒适的宠物床和恒温系统，每日提供新鲜手工宠物零食，用心呵护每只小毛球。适合中小型犬和猫咪。',
 JSON_ARRAY('宠物寄养', '手工零食', '日常护理', '宠物托管'),
 '1. 仅接待体重20kg以下的宠物\n2. 请提供宠物饮食偏好清单\n3. 寄养超过3天赠送免费洗澡\n4. 节假日加收20%服务费\n5. 提前退房不退还剩余费用',
 'approved', NOW(), NOW());

-- 商家5: 小狗当家寄养训练中心（深圳）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, status, create_time, update_time) VALUES
(@user5, '小狗当家寄养训练中心',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg',
 JSON_ARRAY(
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E8%AE%AD%E7%BB%83%E5%9C%BA%E5%9C%B0.jpg',
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A4%E5%86%85%E5%AF%84%E5%85%BB.jpg'
 ),
 4.9, 259.00,
 JSON_ARRAY('专业训练', '行为矫正', '寄养+训练', '军犬训导师'),
 '深圳市南山区科技园xxx号',
 '0755-88880005',
 '小狗当家由退役军犬训导师创办，将专业训练融入日常寄养。每只入住狗狗都会接受基础服从训练和行为评估，让您的爱犬在寄养期间不仅玩得开心，还能变得更听话。户外训练场占地3000平米。',
 JSON_ARRAY('宠物寄养', '服从训练', '行为矫正', '敏捷训练', '护卫训练'),
 '1. 训练课程需提前一周预约\n2. 攻击性较强的犬只需提前说明\n3. 寄养+训练套餐享8.5折\n4. 每周提供训练进度报告\n5. 首次入住的狗狗有2天适应期',
 'approved', NOW(), NOW());

-- 商家6: 爱宠屋24h宠物酒店（广州）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, status, create_time, update_time) VALUES
(@user6, '爱宠屋24h宠物酒店',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg',
 JSON_ARRAY(
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A0%E7%89%A9%E9%85%92%E5%BA%97%E5%A4%A7%E5%A0%82.jpg',
   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A0%E7%89%A9%E5%8C%BB%E7%96%97.jpg'
 ),
 4.5, 179.00,
 JSON_ARRAY('24小时营业', '宠物酒店', '医疗护理', '监控直播'),
 '广州市天河区珠江新城xxx号',
 '020-88880006',
 '爱宠屋是华南地区首家24小时宠物酒店，提供全天候寄养和医护服务。每个房间配备高清摄像头，主人可随时通过APP查看爱宠动态。店内配有宠物医院，突发状况可立即就医，让您寄养无忧。',
 JSON_ARRAY('宠物寄养', '宠物医疗', '24小时看护', '在线监控', '宠物SPA'),
 '1. 24小时随时办理入住/退房\n2. 医疗费用按实际项目收取\n3. 在线监控服务免费提供\n4. SPA服务需单独预约\n5. 办理会员卡享全年折扣',
 'approved', NOW(), NOW());

-- 3. 为商家6的店铺插入一些档期数据（未来7天）
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(6, CURDATE() + INTERVAL 0 DAY, 5, 179.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL 1 DAY, 3, 179.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL 2 DAY, 8, 179.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL 3 DAY, 6, 189.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL 4 DAY, 4, 189.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL 5 DAY, 7, 189.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL 6 DAY, 2, 199.00, NOW(), NOW());
