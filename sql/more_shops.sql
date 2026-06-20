-- ============================================
-- 毛孩驿站 - 补充商家数据（6家新店 + 档期）
-- ============================================

-- 商家用户
INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13890007777', 'abc123', '爪印小管家', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg', 'shop', 'paw@furbaby.com', NOW(), NOW());
SET @u7 = LAST_INSERT_ID();
INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13890008888', 'abc123', '喵呜森林园长', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg', 'shop', 'mew@furbaby.com', NOW(), NOW());
SET @u8 = LAST_INSERT_ID();
INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13890009999', 'abc123', '汪汪队长', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg', 'shop', 'woof@furbaby.com', NOW(), NOW());
SET @u9 = LAST_INSERT_ID();
INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13890010000', 'abc123', '宠星人管家', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg', 'shop', 'star@furbaby.com', NOW(), NOW());
SET @u10 = LAST_INSERT_ID();
INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13890011111', 'abc123', '小尾巴屋主', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg', 'shop', 'tail@furbaby.com', NOW(), NOW());
SET @u11 = LAST_INSERT_ID();
INSERT INTO user (phone, password, nickname, avatar, role, email, create_time, update_time) VALUES
('13890012222', 'abc123', '毛孩子大家长', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg', 'shop', 'fur@furbaby.com', NOW(), NOW());
SET @u12 = LAST_INSERT_ID();

-- ============================================
-- 商家店铺
-- ============================================

-- 商家7: 爪印宠物托儿所（南京）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, status, create_time, update_time) VALUES
(@u7, '爪印宠物托儿所',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AF%84%E5%85%BB%E7%8E%AF%E5%A2%83.jpg', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A4%E5%86%85%E5%AF%84%E5%85%BB.jpg'),
 4.8, 169.00,
 JSON_ARRAY('日托服务', '小宠专属', '接送服务', '环境温馨'),
 '南京市鼓楼区湖南路xxx号',
 '025-66660007',
 '爪印宠物托儿所专注于宠物日托和短期寄养，位于市中心交通便利。店内采用家庭式布局，每只宠物都有自己的专属活动空间。提供早晚接送服务，方便上班族。',
 JSON_ARRAY('宠物日托', '短期寄养', '早晚接送', '宠物零食'),
 '1. 日托时间为8:00-18:00\n2. 寄养需提前一天预约\n3. 接送服务5公里内免费\n4. 首次入托享体验价',
 'approved', NOW(), NOW());
SET @s7 = LAST_INSERT_ID();

-- 商家8: 喵呜森林猫咪旅馆（厦门）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, status, create_time, update_time) VALUES
(@u8, '喵呜森林猫咪旅馆',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%8C%AB%E5%92%AA%E4%B8%93%E5%8C%BA.jpg', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A0%E7%89%A9%E7%8E%A9%E5%85%B7.jpg'),
 4.9, 269.00,
 JSON_ARRAY('纯猫寄养', '森林主题', '海景猫房', '猫咪摄影'),
 '厦门市思明区环岛路xxx号',
 '0592-66660008',
 '喵呜森林坐落在厦门环岛路旁，面朝大海，是猫咪的度假天堂。每间猫房都有落地窗可以看海看鸟，室内布满猫爬架和绿植，营造森林般的自然环境。定期举办猫咪写真拍摄。',
 JSON_ARRAY('猫咪寄养', '猫咪写真', '长毛猫护理', '猫咪社交'),
 '1. 仅接待猫咪\n2. 建议提前两周预约\n3. 提供每日视频直播\n4. 长期寄养享8折',
 'approved', NOW(), NOW());
SET @s8 = LAST_INSERT_ID();

-- 商家9: 汪汪大队宠物基地（武汉）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, status, create_time, update_time) VALUES
(@u9, '汪汪大队宠物基地',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%8B%97%E7%8B%97%E4%B9%90%E5%9B%AD.jpg', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E8%AE%AD%E7%BB%83%E5%9C%BA%E5%9C%B0.jpg'),
 4.7, 149.00,
 JSON_ARRAY('大型犬友好', '超大草坪', '宠物游泳池', '狗狗社交'),
 '武汉市洪山区光谷大道xxx号',
 '027-66660009',
 '汪汪大队是武汉最大的狗狗寄养基地，拥有5000平米户外活动空间和宠物专用游泳池。每周举办狗狗社交日，让毛孩子们尽情奔跑撒欢。设有专业训犬课程可选。',
 JSON_ARRAY('宠物寄养', '狗狗训练', '宠物游泳', '宠物摄影', '狗狗社交活动'),
 '1. 大型犬需完成基础训练\n2. 发情期母犬暂不接待\n3. 游泳池需预约使用\n4. 训练课额外收费',
 'approved', NOW(), NOW());
SET @s9 = LAST_INSERT_ID();

-- 商家10: 宠星人宠物公馆（苏州）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, status, create_time, update_time) VALUES
(@u10, '宠星人宠物公馆',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A0%E7%89%A9%E9%85%92%E5%BA%97%E5%A4%A7%E5%A0%82.jpg', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A0%E7%89%A9%E5%8C%BB%E7%96%97.jpg'),
 4.6, 239.00,
 JSON_ARRAY('园林式寄养', 'VIP套房', '宠物SPA', '医疗驻店'),
 '苏州市姑苏区观前街xxx号',
 '0512-66660010',
 '宠星人宠物公馆将苏州园林风格融入宠物寄养空间，每个套房都有独立小院和假山流水景观。配备宠物SPA中心和驻店兽医，提供高端尊享服务。所有洗护用品均为进口天然配方。',
 JSON_ARRAY('高端寄养', '宠物SPA', '专业美容', '医疗巡诊', '接送服务'),
 '1. VIP套房需提前3天预约\n2. 提供全城接送（收费）\n3. SPA和美容需单独预约\n4. 节假日加收30%服务费',
 'approved', NOW(), NOW());
SET @s10 = LAST_INSERT_ID();

-- 商家11: 小尾巴宠物度假屋（重庆）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, status, create_time, update_time) VALUES
(@u11, '小尾巴宠物度假屋',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E6%B8%A9%E9%A6%A8%E5%AE%A0%E7%89%A9%E9%97%B4.jpg', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A0%E7%89%A9%E5%AF%84%E5%85%BB.jpg'),
 4.5, 139.00,
 JSON_ARRAY('山景寄养', '超高性价比', '猫狗分区', '温馨舒适'),
 '重庆市渝北区新牌坊xxx号',
 '023-66660011',
 '小尾巴宠物度假屋位于山城重庆，坐拥绝佳山景视野。坚持平价高端路线，用实惠的价格提供优质的寄养服务。猫狗严格分区，互不打扰。店内收养了3只流浪猫作为"店长"，深受顾客喜爱。',
 JSON_ARRAY('宠物寄养', '平价优选', '宠物托管', '手工零食'),
 '1. 猫狗分区管理\n2. 长期寄养（15天以上）享85折\n3. 欢迎自带宠物用品\n4. 提供每日照片反馈',
 'approved', NOW(), NOW());
SET @s11 = LAST_INSERT_ID();

-- 商家12: 毛孩子乐园（西安）
INSERT INTO shop (user_id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice, status, create_time, update_time) VALUES
(@u12, '毛孩子乐园',
 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A0%E7%89%A9%E5%AF%84%E5%85%BB.jpg', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E6%88%B7%E5%A4%96%E6%B4%BB%E5%8A%A8.jpg'),
 4.7, 159.00,
 JSON_ARRAY('古城特色', '宠物乐园', '异宠友好', '亲子互动'),
 '西安市雁塔区大雁塔街道xxx号',
 '029-66660012',
 '毛孩子乐园是西北地区最大的综合性宠物寄养乐园，毗邻大雁塔，交通便利。除了猫狗寄养外，还接待兔子、龙猫、仓鼠等小宠异宠。设有宠物主人专属休息区，可随时来园探视互动。',
 JSON_ARRAY('宠物寄养', '异宠寄养', '亲子探视', '宠物用品售卖', '营养咨询'),
 '1. 异宠需自带笼具和饲料\n2. 探视时间每天10:00-17:00\n3. 提供长短期寄养方案\n4. 会员享专属折扣',
 'approved', NOW(), NOW());
SET @s12 = LAST_INSERT_ID();

-- ============================================
-- 6家新店各14天档期
-- ============================================

-- 商家7: 爪印宠物托儿所（南京）shop_id=@s7, 日价169
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(@s7, CURDATE() + INTERVAL  0 DAY, 6, 169.00, NOW(), NOW()),
(@s7, CURDATE() + INTERVAL  1 DAY, 4, 169.00, NOW(), NOW()),
(@s7, CURDATE() + INTERVAL  2 DAY, 8, 169.00, NOW(), NOW()),
(@s7, CURDATE() + INTERVAL  3 DAY, 5, 169.00, NOW(), NOW()),
(@s7, CURDATE() + INTERVAL  4 DAY, 3, 169.00, NOW(), NOW()),
(@s7, CURDATE() + INTERVAL  5 DAY, 7, 189.00, NOW(), NOW()),
(@s7, CURDATE() + INTERVAL  6 DAY, 2, 189.00, NOW(), NOW()),
(@s7, CURDATE() + INTERVAL  7 DAY, 0, 189.00, NOW(), NOW()),
(@s7, CURDATE() + INTERVAL  8 DAY, 6, 169.00, NOW(), NOW()),
(@s7, CURDATE() + INTERVAL  9 DAY, 8, 169.00, NOW(), NOW()),
(@s7, CURDATE() + INTERVAL 10 DAY, 5, 169.00, NOW(), NOW()),
(@s7, CURDATE() + INTERVAL 11 DAY, 3, 189.00, NOW(), NOW()),
(@s7, CURDATE() + INTERVAL 12 DAY, 1, 189.00, NOW(), NOW()),
(@s7, CURDATE() + INTERVAL 13 DAY, 4, 169.00, NOW(), NOW());

-- 商家8: 喵呜森林猫咪旅馆（厦门）shop_id=@s8, 日价269
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(@s8, CURDATE() + INTERVAL  0 DAY, 3, 269.00, NOW(), NOW()),
(@s8, CURDATE() + INTERVAL  1 DAY, 2, 269.00, NOW(), NOW()),
(@s8, CURDATE() + INTERVAL  2 DAY, 5, 269.00, NOW(), NOW()),
(@s8, CURDATE() + INTERVAL  3 DAY, 3, 269.00, NOW(), NOW()),
(@s8, CURDATE() + INTERVAL  4 DAY, 4, 269.00, NOW(), NOW()),
(@s8, CURDATE() + INTERVAL  5 DAY, 2, 299.00, NOW(), NOW()),
(@s8, CURDATE() + INTERVAL  6 DAY, 1, 299.00, NOW(), NOW()),
(@s8, CURDATE() + INTERVAL  7 DAY, 0, 299.00, NOW(), NOW()),
(@s8, CURDATE() + INTERVAL  8 DAY, 4, 269.00, NOW(), NOW()),
(@s8, CURDATE() + INTERVAL  9 DAY, 3, 269.00, NOW(), NOW()),
(@s8, CURDATE() + INTERVAL 10 DAY, 5, 269.00, NOW(), NOW()),
(@s8, CURDATE() + INTERVAL 11 DAY, 2, 299.00, NOW(), NOW()),
(@s8, CURDATE() + INTERVAL 12 DAY, 1, 299.00, NOW(), NOW()),
(@s8, CURDATE() + INTERVAL 13 DAY, 3, 269.00, NOW(), NOW());

-- 商家9: 汪汪大队宠物基地（武汉）shop_id=@s9, 日价149
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(@s9, CURDATE() + INTERVAL  0 DAY, 12, 149.00, NOW(), NOW()),
(@s9, CURDATE() + INTERVAL  1 DAY, 9,  149.00, NOW(), NOW()),
(@s9, CURDATE() + INTERVAL  2 DAY, 15, 149.00, NOW(), NOW()),
(@s9, CURDATE() + INTERVAL  3 DAY, 10, 149.00, NOW(), NOW()),
(@s9, CURDATE() + INTERVAL  4 DAY, 8,  149.00, NOW(), NOW()),
(@s9, CURDATE() + INTERVAL  5 DAY, 13, 169.00, NOW(), NOW()),
(@s9, CURDATE() + INTERVAL  6 DAY, 6,  169.00, NOW(), NOW()),
(@s9, CURDATE() + INTERVAL  7 DAY, 3,  169.00, NOW(), NOW()),
(@s9, CURDATE() + INTERVAL  8 DAY, 11, 149.00, NOW(), NOW()),
(@s9, CURDATE() + INTERVAL  9 DAY, 14, 149.00, NOW(), NOW()),
(@s9, CURDATE() + INTERVAL 10 DAY, 9,  149.00, NOW(), NOW()),
(@s9, CURDATE() + INTERVAL 11 DAY, 7,  169.00, NOW(), NOW()),
(@s9, CURDATE() + INTERVAL 12 DAY, 5,  169.00, NOW(), NOW()),
(@s9, CURDATE() + INTERVAL 13 DAY, 2,  169.00, NOW(), NOW());

-- 商家10: 宠星人宠物公馆（苏州）shop_id=@s10, 日价239
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(@s10, CURDATE() + INTERVAL  0 DAY, 3, 239.00, NOW(), NOW()),
(@s10, CURDATE() + INTERVAL  1 DAY, 2, 239.00, NOW(), NOW()),
(@s10, CURDATE() + INTERVAL  2 DAY, 4, 239.00, NOW(), NOW()),
(@s10, CURDATE() + INTERVAL  3 DAY, 2, 239.00, NOW(), NOW()),
(@s10, CURDATE() + INTERVAL  4 DAY, 3, 239.00, NOW(), NOW()),
(@s10, CURDATE() + INTERVAL  5 DAY, 1, 269.00, NOW(), NOW()),
(@s10, CURDATE() + INTERVAL  6 DAY, 0, 269.00, NOW(), NOW()),
(@s10, CURDATE() + INTERVAL  7 DAY, 2, 269.00, NOW(), NOW()),
(@s10, CURDATE() + INTERVAL  8 DAY, 3, 239.00, NOW(), NOW()),
(@s10, CURDATE() + INTERVAL  9 DAY, 4, 239.00, NOW(), NOW()),
(@s10, CURDATE() + INTERVAL 10 DAY, 2, 239.00, NOW(), NOW()),
(@s10, CURDATE() + INTERVAL 11 DAY, 1, 269.00, NOW(), NOW()),
(@s10, CURDATE() + INTERVAL 12 DAY, 0, 269.00, NOW(), NOW()),
(@s10, CURDATE() + INTERVAL 13 DAY, 3, 239.00, NOW(), NOW());

-- 商家11: 小尾巴宠物度假屋（重庆）shop_id=@s11, 日价139
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(@s11, CURDATE() + INTERVAL  0 DAY, 4, 139.00, NOW(), NOW()),
(@s11, CURDATE() + INTERVAL  1 DAY, 3, 139.00, NOW(), NOW()),
(@s11, CURDATE() + INTERVAL  2 DAY, 6, 139.00, NOW(), NOW()),
(@s11, CURDATE() + INTERVAL  3 DAY, 4, 139.00, NOW(), NOW()),
(@s11, CURDATE() + INTERVAL  4 DAY, 5, 139.00, NOW(), NOW()),
(@s11, CURDATE() + INTERVAL  5 DAY, 3, 159.00, NOW(), NOW()),
(@s11, CURDATE() + INTERVAL  6 DAY, 2, 159.00, NOW(), NOW()),
(@s11, CURDATE() + INTERVAL  7 DAY, 1, 159.00, NOW(), NOW()),
(@s11, CURDATE() + INTERVAL  8 DAY, 5, 139.00, NOW(), NOW()),
(@s11, CURDATE() + INTERVAL  9 DAY, 6, 139.00, NOW(), NOW()),
(@s11, CURDATE() + INTERVAL 10 DAY, 4, 139.00, NOW(), NOW()),
(@s11, CURDATE() + INTERVAL 11 DAY, 2, 159.00, NOW(), NOW()),
(@s11, CURDATE() + INTERVAL 12 DAY, 1, 159.00, NOW(), NOW()),
(@s11, CURDATE() + INTERVAL 13 DAY, 3, 139.00, NOW(), NOW());

-- 商家12: 毛孩子乐园（西安）shop_id=@s12, 日价159
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(@s12, CURDATE() + INTERVAL  0 DAY, 8,  159.00, NOW(), NOW()),
(@s12, CURDATE() + INTERVAL  1 DAY, 6,  159.00, NOW(), NOW()),
(@s12, CURDATE() + INTERVAL  2 DAY, 10, 159.00, NOW(), NOW()),
(@s12, CURDATE() + INTERVAL  3 DAY, 7,  159.00, NOW(), NOW()),
(@s12, CURDATE() + INTERVAL  4 DAY, 9,  159.00, NOW(), NOW()),
(@s12, CURDATE() + INTERVAL  5 DAY, 5,  179.00, NOW(), NOW()),
(@s12, CURDATE() + INTERVAL  6 DAY, 4,  179.00, NOW(), NOW()),
(@s12, CURDATE() + INTERVAL  7 DAY, 2,  179.00, NOW(), NOW()),
(@s12, CURDATE() + INTERVAL  8 DAY, 8,  159.00, NOW(), NOW()),
(@s12, CURDATE() + INTERVAL  9 DAY, 10, 159.00, NOW(), NOW()),
(@s12, CURDATE() + INTERVAL 10 DAY, 6,  159.00, NOW(), NOW()),
(@s12, CURDATE() + INTERVAL 11 DAY, 4,  179.00, NOW(), NOW()),
(@s12, CURDATE() + INTERVAL 12 DAY, 3,  179.00, NOW(), NOW()),
(@s12, CURDATE() + INTERVAL 13 DAY, 1,  179.00, NOW(), NOW());
