-- ============================================
-- 毛孩驿站 - 疫苗 + 退款 测试数据
-- ============================================

-- ============================================
-- 第一部分：疫苗记录（12只宠物，每只2-4针）
-- ============================================

-- 猫: 团子(id=1) 金渐层 — 猫三联 + 狂犬
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(1, '猫三联（第一针）', '2025-01-15', NOW()),
(1, '猫三联（加强针）', '2025-02-15', NOW()),
(1, '狂犬疫苗',         '2025-03-15', NOW());

-- 狗: 布丁(id=2) 柯基 — 犬五联 + 狂犬 + 窝咳
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(2, '犬五联（第一针）', '2025-02-01', NOW()),
(2, '犬五联（加强针）', '2025-03-01', NOW()),
(2, '狂犬疫苗',         '2025-04-01', NOW()),
(2, '犬窝咳疫苗',       '2025-05-01', NOW());

-- 猫: 蓝宝(id=3) 英短蓝猫
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(3, '猫三联（第一针）', '2025-06-01', NOW()),
(3, '猫三联（加强针）', '2025-07-01', NOW()),
(3, '狂犬疫苗',         '2025-08-01', NOW());

-- 狗: 闪电(id=4) 边境牧羊犬
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(4, '犬五联（第一针）', '2024-06-10', NOW()),
(4, '犬五联（加强针）', '2024-07-10', NOW()),
(4, '狂犬疫苗',         '2024-08-10', NOW()),
(4, '犬窝咳疫苗',       '2024-09-10', NOW()),
(4, '犬钩端螺旋体疫苗', '2024-10-10', NOW());

-- 猫: 雪球(id=5) 布偶猫
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(5, '猫三联（第一针）', '2025-09-01', NOW()),
(5, '猫三联（加强针）', '2025-10-01', NOW()),
(5, '狂犬疫苗',         '2025-11-01', NOW());

-- 狗: 毛球(id=6) 金毛
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(6, '犬五联（第一针）', '2024-08-20', NOW()),
(6, '犬五联（加强针）', '2024-09-20', NOW()),
(6, '狂犬疫苗',         '2024-10-20', NOW()),
(6, '犬窝咳疫苗',       '2025-03-20', NOW());

-- 狗: 豆豆(id=7) 泰迪
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(7, '犬五联（第一针）', '2025-04-01', NOW()),
(7, '犬五联（加强针）', '2025-05-01', NOW()),
(7, '狂犬疫苗',         '2025-06-01', NOW());

-- 猫: 奶茶(id=8) 暹罗猫
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(8, '猫三联（第一针）', '2025-03-15', NOW()),
(8, '猫三联（加强针）', '2025-04-15', NOW()),
(8, '狂犬疫苗',         '2025-05-15', NOW());

-- 猫: 咪咪(id=9) 布偶猫
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(9, '猫三联（第一针）', '2025-07-20', NOW()),
(9, '猫三联（加强针）', '2025-08-20', NOW()),
(9, '狂犬疫苗',         '2025-09-20', NOW());

-- 狗: 旺财(id=10) 柴犬
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(10, '犬五联（第一针）', '2025-01-10', NOW()),
(10, '犬五联（加强针）', '2025-02-10', NOW()),
(10, '狂犬疫苗',         '2025-03-10', NOW()),
(10, '犬窝咳疫苗',       '2025-06-10', NOW());

-- 狗: 熊二(id=11) 哈士奇
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(11, '犬五联（第一针）', '2024-11-01', NOW()),
(11, '犬五联（加强针）', '2024-12-01', NOW()),
(11, '狂犬疫苗',         '2025-01-01', NOW()),
(11, '犬钩端螺旋体疫苗', '2025-02-01', NOW());

-- 猫: 三胖(id=12) 橘猫
INSERT INTO vaccine (pet_id, name, date, create_time) VALUES
(12, '猫三联（第一针）', '2024-04-10', NOW()),
(12, '猫三联（加强针）', '2024-05-10', NOW()),
(12, '狂犬疫苗',         '2024-06-10', NOW());

-- ============================================
-- 第二部分：退款记录 + 补充退款相关订单
-- ============================================

-- 补充: 为已取消订单14创建支付记录（先付款后退款场景），并更新订单
UPDATE orders SET pay_time='2026-06-10 10:00:00' WHERE id=14;
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY20260610014', 14, 8, 'wechat', 398.00, 'success', '2026-06-10 10:00:00', '2026-06-10 09:55:00', '2026-06-10 10:00:00');
SET @pay14 = LAST_INSERT_ID();

-- 订单14退款: 已退款（因行程变更）
INSERT INTO refund (order_id, payment_id, user_id, amount, reason, status, create_time, update_time) VALUES
(14, @pay14, 8, 398.00, '行程变更，全额退款', 'success', '2026-06-12 10:30:00', '2026-06-12 10:30:00');

-- 补充: 为已取消订单15创建支付记录
UPDATE orders SET pay_time='2026-06-15 08:00:00' WHERE id=15;
INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY20260615015', 15, 7, 'alipay', 258.00, 'success', '2026-06-15 08:00:00', '2026-06-15 07:55:00', '2026-06-15 08:00:00');
SET @pay15 = LAST_INSERT_ID();

-- 订单15退款: 已退款（宠物身体不适）
INSERT INTO refund (order_id, payment_id, user_id, amount, reason, status, create_time, update_time) VALUES
(15, @pay15, 7, 258.00, '宠物身体不适无法寄养，全额退款', 'success', '2026-06-16 15:00:00', '2026-06-16 15:00:00');

-- 补充一笔退款中的订单（体现 refunding 状态）
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260618016', 5, 3, 3, '2026-06-20', '2026-06-22', 'refunding', 477.00, '蓝宝生病需退款', '2026-06-17 20:00:00', '2026-06-17 18:00:00', '2026-06-19 09:00:00');
SET @order16 = LAST_INSERT_ID();

INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY20260617016', @order16, 5, 'alipay', 477.00, 'success', '2026-06-17 20:00:00', '2026-06-17 19:55:00', '2026-06-17 20:00:00');
SET @pay16 = LAST_INSERT_ID();

INSERT INTO refund (order_id, payment_id, user_id, amount, reason, status, create_time, update_time) VALUES
(@order16, @pay16, 5, 477.00, '宠物突然生病无法寄养，申请退款', 'pending', '2026-06-19 09:00:00', '2026-06-19 09:00:00');

-- 补充一笔已退款订单（体现 refunded 状态）
INSERT INTO orders (order_no, user_id, shop_id, pet_id, start_date, end_date, status, amount, remark, pay_time, create_time, update_time) VALUES
('ORD20260610017', 10, 6, 12, '2026-06-15', '2026-06-17', 'refunded', 537.00, '商家档期满无法接待', '2026-06-09 12:00:00', '2026-06-08 10:00:00', '2026-06-11 16:00:00');
SET @order17 = LAST_INSERT_ID();

INSERT INTO payment (payment_no, order_id, user_id, pay_method, amount, status, pay_time, create_time, update_time) VALUES
('PAY20260609017', @order17, 10, 'wechat', 537.00, 'success', '2026-06-09 12:00:00', '2026-06-09 11:55:00', '2026-06-09 12:00:00');
SET @pay17 = LAST_INSERT_ID();

INSERT INTO refund (order_id, payment_id, user_id, amount, reason, status, create_time, update_time) VALUES
(@order17, @pay17, 10, 537.00, '商家档期满无法接待，协商退款', 'success', '2026-06-10 09:00:00', '2026-06-11 16:00:00');

-- 订单17的评价
INSERT INTO review (order_id, shop_id, user_id, rating, content, photos, create_time) VALUES
(@order17, 6, 10, 3, '虽然没寄养成，但退款处理很及时，客服态度也不错。下次再试试。', NULL, '2026-06-12 14:00:00');

-- 补充通知
INSERT INTO notification (user_id, type, title, content, is_read, create_time) VALUES
(8,  'refund_done',  '退款到账',     '订单 ORD20260609014 退款 ¥398.00 已到账',             1, '2026-06-12 10:31:00'),
(7,  'refund_done',  '退款到账',     '订单 ORD20260615015 退款 ¥258.00 已到账',             1, '2026-06-16 15:01:00'),
(5,  'refund_apply', '退款申请已提交', '订单 ORD20260618016 退款申请已提交，处理中',           0, '2026-06-19 09:01:00'),
(10, 'refund_done',  '退款到账',     '订单 ORD20260610017 退款 ¥537.00 已到账',             1, '2026-06-11 16:01:00');
