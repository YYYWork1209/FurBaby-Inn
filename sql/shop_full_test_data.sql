-- ============================================
-- 毛孩驿站 - 商家相关全量测试数据
-- 依赖链: pet → orders → payment → review / boarding_photo / notification
-- ============================================

-- ============================================
-- 第一部分：宠物档案（7位宠主，约12只宠物）
-- ============================================

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(1,  '团子',   'cat',  '金渐层',   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E9%87%91%E6%B8%90%E5%B1%82.jpg', 'male',   24, 4.5,  '已绝育，定期驱虫，性格温顺', NOW(), NOW());
SET @pet1 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(1,  '布丁',   'dog',  '柯基',     'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E6%9F%AF%E5%9F%BA.jpg',   'female', 18, 10.2, '疫苗齐全，活泼好动，喜欢社交', NOW(), NOW());
SET @pet2 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(5,  '蓝宝',   'cat',  '英短蓝猫', 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E8%8B%B1%E7%9F%AD.jpg',   'female', 12, 3.8,  '已绝育，胆小猫，需安静环境', NOW(), NOW());
SET @pet3 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(6,  '闪电',   'dog',  '边境牧羊犬','https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E8%BE%B9%E7%89%A7.jpg',   'male',   36, 18.5, '聪明好动，需大量运动，会基础指令', NOW(), NOW());
SET @pet4 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(6,  '雪球',   'cat',  '布偶猫',   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%B8%83%E5%81%B6%E7%8C%AB.jpg', 'female', 8,  2.8,  '粘人爱撒娇，需每日梳毛', NOW(), NOW());
SET @pet5 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(7,  '毛球',   'dog',  '金毛',     'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E9%87%91%E6%AF%9B.jpg',     'male',   30, 28.0, '温和友善，髋关节需关注，饮食控制中', NOW(), NOW());
SET @pet6 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(8,  '豆豆',   'dog',  '泰迪',     'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E6%B3%B0%E8%BF%AA.jpg',     'female', 14, 4.2,  '已绝育，怕冷，需要穿衣服', NOW(), NOW());
SET @pet7 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(8,  '奶茶',   'cat',  '暹罗猫',   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E6%9A%B9%E7%BD%97.jpg',     'male',   20, 5.0,  '话多粘人，已绝育，对海鲜过敏', NOW(), NOW());
SET @pet8 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(9,  '咪咪',   'cat',  '布偶猫',   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%B8%83%E5%81%B6%E7%8C%AB.jpg', 'female', 10, 3.2,  '性格温顺，长毛需定期护理', NOW(), NOW());
SET @pet9 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(9,  '旺财',   'dog',  '柴犬',     'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E6%9F%B4%E7%8A%AC.jpg',     'male',   16, 9.5,  '独立性强，已绝育，挑食', NOW(), NOW());
SET @pet10 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(10, '熊二',   'dog',  '哈士奇',   'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%93%88%E5%A3%AB%E5%A5%87.jpg', 'male',   22, 22.0, '精力旺盛，拆家能手，需大量运动', NOW(), NOW());
SET @pet11 = LAST_INSERT_ID();

INSERT INTO pet (owner_id, name, species, breed, avatar, gender, age, weight, health_notes, create_time, update_time) VALUES
(10, '三胖',   'cat',  '橘猫',     'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E6%A9%98%E7%8C%AB.jpg',     'male',   36, 7.5,  '超重，需控制饮食，性格懒散', NOW(), NOW());
SET @pet12 = LAST_INSERT_ID();

-- ============================================
-- 第二部分：订单（多状态、多商铺）
-- ============================================

-- 已完成订单（便于后面写评价）
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260601001', 1,  1, @pet1,  '2026-06-01', '2026-06-03', 'completed', 597.00, '团子第一次寄养体验', '2026-05-31 14:30:00', '2026-05-30 10:00:00', '2026-06-03 18:00:00');
SET @order1 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260605002', 6,  2, @pet4,  '2026-06-05', '2026-06-08', 'completed', 1196.00, '闪电参加训练营', '2026-06-04 09:00:00', '2026-06-03 19:00:00', '2026-06-08 12:00:00');
SET @order2 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260610003', 7,  3, @pet6,  '2026-06-10', '2026-06-12', 'completed', 477.00, NULL, '2026-06-09 20:00:00', '2026-06-09 08:00:00', '2026-06-12 15:00:00');
SET @order3 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260612004', 5,  6, @pet3,  '2026-06-12', '2026-06-15', 'completed', 716.00, '蓝宝胆子小请多关照', '2026-06-11 11:00:00', '2026-06-10 15:00:00', '2026-06-15 10:00:00');
SET @order4 = LAST_INSERT_ID();

-- 寄养中
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260616005', 8,  4, @pet7,  '2026-06-16', '2026-06-22', 'boarding', 903.00, '豆豆怕冷，请保持室温', '2026-06-15 16:00:00', '2026-06-14 10:00:00', '2026-06-16 09:00:00');
SET @order5 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260618006', 9,  5, @pet10, '2026-06-18', '2026-06-25', 'boarding', 2072.00, '寄养+服从训练套餐', '2026-06-17 12:00:00', '2026-06-16 08:00:00', '2026-06-18 08:00:00');
SET @order6 = LAST_INSERT_ID();

-- 已支付（待开始）
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260620007', 1,  2, @pet2,  '2026-06-22', '2026-06-24', 'paid', 897.00, '布丁喜欢社交', '2026-06-19 10:00:00', '2026-06-18 14:00:00', '2026-06-19 10:00:00');
SET @order7 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260619008', 10, 3, @pet11, '2026-06-23', '2026-06-25', 'paid', 477.00, NULL, '2026-06-19 08:00:00', '2026-06-18 20:00:00', '2026-06-19 08:00:00');
SET @order8 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260620009', 6,  1, @pet5,  '2026-06-24', '2026-06-26', 'paid', 597.00, '雪球需要每日梳毛', '2026-06-19 18:00:00', '2026-06-19 16:00:00', '2026-06-19 18:00:00');
SET @order9 = LAST_INSERT_ID();

-- 待支付
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, create_time, update_time) VALUES
('ORD20260620010', 7,  6, @pet6,  '2026-06-25', '2026-06-27', 'pending', 537.00, '毛球需要控制饮食', '2026-06-19 12:00:00', '2026-06-19 12:00:00');
SET @order10 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, create_time, update_time) VALUES
('ORD20260620011', 9,  4, @pet9,  '2026-06-26', '2026-06-28', 'pending', 387.00, NULL, '2026-06-20 08:00:00', '2026-06-20 08:00:00');
SET @order11 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, create_time, update_time) VALUES
('ORD20260620012', 5,  2, @pet3,  '2026-06-27', '2026-06-29', 'pending', 897.00, '蓝宝再次寄养，老顾客', '2026-06-20 09:00:00', '2026-06-20 09:00:00');
SET @order12 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, create_time, update_time) VALUES
('ORD20260620013', 10, 5, @pet12, '2026-06-25', '2026-06-26', 'pending', 259.00, '三胖超重请按食谱喂养', '2026-06-20 10:00:00', '2026-06-20 10:00:00');
SET @order13 = LAST_INSERT_ID();

-- 已取消
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, cancel_time, cancel_reason, create_time, update_time) VALUES
('ORD20260609014', 8,  1, @pet7,  '2026-06-14', '2026-06-16', 'cancelled', 398.00, NULL, '2026-06-12 10:00:00', '行程变更，取消预订', '2026-06-08 15:00:00', '2026-06-12 10:00:00');
SET @order14 = LAST_INSERT_ID();

INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, cancel_time, cancel_reason, create_time, update_time) VALUES
('ORD20260615015', 7,  4, @pet6,  '2026-06-18', '2026-06-20', 'cancelled', 258.00, NULL, '2026-06-16 14:00:00', '宠物身体不适，需要就医', '2026-06-14 10:00:00', '2026-06-16 14:00:00');
SET @order15 = LAST_INSERT_ID();

-- ============================================
-- 第三部分：支付单（对应已支付/寄养中/已完成的订单）
-- ============================================

INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026053101', @order1,  1,  'wechat', 597.00,  'success', '2026-05-31 14:30:00', '2026-05-31 14:25:00', '2026-05-31 14:30:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026060402', @order2,  6,  'alipay', 1196.00, 'success', '2026-06-04 09:00:00', '2026-06-04 08:55:00', '2026-06-04 09:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026060903', @order3,  7,  'wechat', 477.00,  'success', '2026-06-09 20:00:00', '2026-06-09 19:50:00', '2026-06-09 20:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026061104', @order4,  5,  'wechat', 716.00,  'success', '2026-06-11 11:00:00', '2026-06-11 10:55:00', '2026-06-11 11:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026061505', @order5,  8,  'alipay', 903.00,  'success', '2026-06-15 16:00:00', '2026-06-15 15:50:00', '2026-06-15 16:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026061706', @order6,  9,  'wechat', 2072.00, 'success', '2026-06-17 12:00:00', '2026-06-17 11:45:00', '2026-06-17 12:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026061907', @order7,  1,  'wechat', 897.00,  'success', '2026-06-19 10:00:00', '2026-06-19 09:55:00', '2026-06-19 10:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026061908', @order8,  10, 'alipay', 477.00,  'success', '2026-06-19 08:00:00', '2026-06-19 07:55:00', '2026-06-19 08:00:00');
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY2026061909', @order9,  6,  'wechat', 597.00,  'success', '2026-06-19 18:00:00', '2026-06-19 17:50:00', '2026-06-19 18:00:00');

-- ============================================
-- 第四部分：评价（已完成订单的评价 + 几条直接评价）
-- ============================================

INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order1, 1, 1, 5, '环境很好，团子玩得很开心，每天都能收到照片反馈，下次还来！',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AF%84%E5%85%BB%E7%8E%AF%E5%A2%83.jpg'),
 '2026-06-04 10:00:00');
INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order2, 2, 6, 5, '闪电参加了训练营，回来真的乖了很多！专业度满分，强烈推荐给养边牧的朋友。',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E8%AE%AD%E7%BB%83%E5%9C%BA%E5%9C%B0.jpg',
           'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%8B%97%E7%8B%97%E4%B9%90%E5%9B%AD.jpg'),
 '2026-06-09 14:00:00');
INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order3, 3, 7, 4, '旺旺乐园场地很大，毛球跑得很尽兴。就是接送服务稍微迟到了半小时，总体不错。',
 NULL,
 '2026-06-13 11:00:00');
INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order4, 6, 5, 5, '蓝宝胆子很小但是店员照顾得很细心，监控功能太方便了，随时能看到它！',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A0%E7%89%A9%E9%85%92%E5%BA%97%E5%A4%A7%E5%A0%82.jpg'),
 '2026-06-16 09:00:00');

-- 补充评价（其他店铺的额外评价，让每家店都有评价）
INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order5, 4, 8, 4, '毛茸茸小屋虽然不大但很温馨，豆豆穿上小衣服特别可爱。价格也很实惠！',
 NULL,
 '2026-06-18 20:00:00');
INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order6, 5, 9, 5, '旺财参加寄养+训练套餐效果显著！之前挑食的毛病改善了很多，张教练很专业。',
 JSON_ARRAY('https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E6%88%B7%E5%A4%96%E6%B4%BB%E5%8A%A8.jpg'),
 '2026-06-19 10:00:00');

-- ============================================
-- 第五部分：寄养照片（寄养中/已完成订单的照片回传）
-- ============================================

INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order1, 11, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AF%84%E5%85%BB%E7%8E%AF%E5%A2%83.jpg', '团子在新环境里探索', '2026-06-01 15:00:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order1, 11, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%B2%BE%E8%8B%B1%E5%96%B5.jpg', '团子晒太阳', '2026-06-02 10:30:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order2, 12, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E8%AE%AD%E7%BB%83%E5%9C%BA%E5%9C%B0.jpg', '闪电在训练场', '2026-06-05 16:00:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order2, 12, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E7%8B%97%E7%8B%97%E4%B9%90%E5%9B%AD.jpg', '闪电和同伴玩耍', '2026-06-07 09:00:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order3, 13, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E6%88%B7%E5%A4%96%E6%B4%BB%E5%8A%A8.jpg', '毛球在大草坪奔跑', '2026-06-11 14:00:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order4, 16, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E5%AE%A0%E7%89%A9%E9%85%92%E5%BA%97%E5%A4%A7%E5%A0%82.jpg', '蓝宝的豪华套房', '2026-06-13 11:00:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order5, 14, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E6%B8%A9%E9%A6%A8%E5%AE%A0%E7%89%A9%E9%97%B4.jpg', '豆豆专属小窝', '2026-06-17 10:00:00');
INSERT INTO boarding_photo (order_id, user_id, url, description, upload_time) VALUES
(@order6, 15, 'https://furbaby-inn.oss-cn-hangzhou.aliyuncs.com/customer/%E8%AE%AD%E7%BB%83%E5%9C%BA%E5%9C%B0.jpg', '旺财训练中', '2026-06-19 08:00:00');

-- ============================================
-- 第六部分：通知消息（系统/订单相关）
-- ============================================

-- 商家收到的通知
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(11, 'order_new',     '新订单提醒',           '宠爱有家收到一笔新预约（团子，6月1-3日）', 1, '2026-05-30 10:05:00'),
(11, 'payment_done',  '支付到账',             '订单 ORD20260601001 已支付 ¥597.00',       1, '2026-05-31 14:31:00'),
(11, 'review_new',    '新评价提醒',           '用户YYY给您留下了一条5星评价',               0, '2026-06-04 10:01:00'),
(12, 'order_new',     '新订单提醒',           '喵不可言收到一笔新预约（闪电，6月5-8日）',   1, '2026-06-03 19:05:00'),
(12, 'review_new',    '新评价提醒',           '用户边牧给您留下了一条5星评价',               0, '2026-06-09 14:01:00'),
(13, 'order_new',     '新订单提醒',           '旺旺乐园收到一笔新预约（毛球，6月10-12日）',  1, '2026-06-09 08:05:00'),
(14, 'order_new',     '新订单提醒',           '毛茸茸小屋收到一笔新预约（豆豆，6月16-22日）', 1, '2026-06-14 10:05:00'),
(15, 'order_new',     '新订单提醒',           '小狗当家收到一笔新预约（旺财，6月18-25日）',   1, '2026-06-16 08:05:00'),
(16, 'review_new',    '新评价提醒',           '用户金渐层给您留下了一条5星评价',              0, '2026-06-16 09:01:00');

-- 顾客收到的通知
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(1,  'payment_done',  '支付成功',             '订单 ORD20260601001 支付成功，¥597.00',       1, '2026-05-31 14:32:00'),
(1,  'boarding_start','寄养开始',             '团子已入住宠爱有家，将定时为您推送照片',        1, '2026-06-01 09:30:00'),
(1,  'boarding_end',  '寄养结束',             '团子的寄养服务已结束，欢迎评价本次体验！',       1, '2026-06-03 18:01:00'),
(6,  'payment_done',  '支付成功',             '订单 ORD20260605002 支付成功，¥1196.00',      1, '2026-06-04 09:01:00'),
(6,  'photo_new',     '新照片提醒',           '闪电的寄养照片已更新（2张）',                   1, '2026-06-07 09:05:00'),
(7,  'order_pending', '待支付提醒',           '订单 ORD20260610003 待支付，请在24小时内完成',   1, '2026-06-09 08:01:00'),
(8,  'boarding_start','寄养开始',             '豆豆已入住毛茸茸小屋',                          1, '2026-06-16 09:30:00'),
(5,  'boarding_end',  '寄养结束',             '蓝宝的寄养服务已结束，欢迎评价本次体验！',        1, '2026-06-15 10:01:00'),
(9,  'payment_done',  '支付成功',             '订单 ORD20260618006 支付成功，¥2072.00',      1, '2026-06-17 12:01:00'),
(10, 'order_pending', '待支付提醒',           '订单 ORD20260620009 待支付，请在24小时内完成',   0, '2026-06-20 08:01:00');
