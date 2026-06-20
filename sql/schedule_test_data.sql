-- ============================================
-- 毛孩驿站 - 商家档期测试数据（6家店铺，未来14天）
-- ============================================

-- 先清空已有档期
DELETE FROM shop_schedule;

-- 商家1: 宠爱有家宠物寄养中心（北京）shop_id=1, 日价199
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(1, CURDATE() + INTERVAL  0 DAY, 5, 199.00, NOW(), NOW()),
(1, CURDATE() + INTERVAL  1 DAY, 3, 199.00, NOW(), NOW()),
(1, CURDATE() + INTERVAL  2 DAY, 8, 199.00, NOW(), NOW()),
(1, CURDATE() + INTERVAL  3 DAY, 6, 199.00, NOW(), NOW()),
(1, CURDATE() + INTERVAL  4 DAY, 4, 199.00, NOW(), NOW()),
(1, CURDATE() + INTERVAL  5 DAY, 7, 199.00, NOW(), NOW()),
(1, CURDATE() + INTERVAL  6 DAY, 2, 199.00, NOW(), NOW()),
(1, CURDATE() + INTERVAL  7 DAY, 0, 199.00, NOW(), NOW()),
(1, CURDATE() + INTERVAL  8 DAY, 5, 199.00, NOW(), NOW()),
(1, CURDATE() + INTERVAL  9 DAY, 6, 219.00, NOW(), NOW()),
(1, CURDATE() + INTERVAL 10 DAY, 8, 219.00, NOW(), NOW()),
(1, CURDATE() + INTERVAL 11 DAY, 9, 219.00, NOW(), NOW()),
(1, CURDATE() + INTERVAL 12 DAY, 3, 219.00, NOW(), NOW()),
(1, CURDATE() + INTERVAL 13 DAY, 1, 219.00, NOW(), NOW());

-- 商家2: 喵不可言猫咪主题旅馆（上海）shop_id=2, 日价299
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(2, CURDATE() + INTERVAL  0 DAY, 4, 299.00, NOW(), NOW()),
(2, CURDATE() + INTERVAL  1 DAY, 2, 299.00, NOW(), NOW()),
(2, CURDATE() + INTERVAL  2 DAY, 6, 299.00, NOW(), NOW()),
(2, CURDATE() + INTERVAL  3 DAY, 4, 299.00, NOW(), NOW()),
(2, CURDATE() + INTERVAL  4 DAY, 3, 299.00, NOW(), NOW()),
(2, CURDATE() + INTERVAL  5 DAY, 5, 349.00, NOW(), NOW()),
(2, CURDATE() + INTERVAL  6 DAY, 1, 349.00, NOW(), NOW()),
(2, CURDATE() + INTERVAL  7 DAY, 0, 349.00, NOW(), NOW()),
(2, CURDATE() + INTERVAL  8 DAY, 4, 299.00, NOW(), NOW()),
(2, CURDATE() + INTERVAL  9 DAY, 6, 299.00, NOW(), NOW()),
(2, CURDATE() + INTERVAL 10 DAY, 8, 299.00, NOW(), NOW()),
(2, CURDATE() + INTERVAL 11 DAY, 5, 349.00, NOW(), NOW()),
(2, CURDATE() + INTERVAL 12 DAY, 2, 349.00, NOW(), NOW()),
(2, CURDATE() + INTERVAL 13 DAY, 1, 349.00, NOW(), NOW());

-- 商家3: 旺旺乐园宠物度假村（杭州）shop_id=3, 日价159
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(3, CURDATE() + INTERVAL  0 DAY, 10, 159.00, NOW(), NOW()),
(3, CURDATE() + INTERVAL  1 DAY, 8, 159.00, NOW(), NOW()),
(3, CURDATE() + INTERVAL  2 DAY, 12, 159.00, NOW(), NOW()),
(3, CURDATE() + INTERVAL  3 DAY, 9, 159.00, NOW(), NOW()),
(3, CURDATE() + INTERVAL  4 DAY, 7, 159.00, NOW(), NOW()),
(3, CURDATE() + INTERVAL  5 DAY, 11, 179.00, NOW(), NOW()),
(3, CURDATE() + INTERVAL  6 DAY, 5, 179.00, NOW(), NOW()),
(3, CURDATE() + INTERVAL  7 DAY, 3, 179.00, NOW(), NOW()),
(3, CURDATE() + INTERVAL  8 DAY, 10, 159.00, NOW(), NOW()),
(3, CURDATE() + INTERVAL  9 DAY, 12, 159.00, NOW(), NOW()),
(3, CURDATE() + INTERVAL 10 DAY, 8, 159.00, NOW(), NOW()),
(3, CURDATE() + INTERVAL 11 DAY, 6, 179.00, NOW(), NOW()),
(3, CURDATE() + INTERVAL 12 DAY, 4, 179.00, NOW(), NOW()),
(3, CURDATE() + INTERVAL 13 DAY, 2, 179.00, NOW(), NOW());

-- 商家4: 毛茸茸宠物寄养小屋（成都）shop_id=4, 日价129
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(4, CURDATE() + INTERVAL  0 DAY, 3, 129.00, NOW(), NOW()),
(4, CURDATE() + INTERVAL  1 DAY, 2, 129.00, NOW(), NOW()),
(4, CURDATE() + INTERVAL  2 DAY, 5, 129.00, NOW(), NOW()),
(4, CURDATE() + INTERVAL  3 DAY, 3, 129.00, NOW(), NOW()),
(4, CURDATE() + INTERVAL  4 DAY, 4, 129.00, NOW(), NOW()),
(4, CURDATE() + INTERVAL  5 DAY, 2, 149.00, NOW(), NOW()),
(4, CURDATE() + INTERVAL  6 DAY, 1, 149.00, NOW(), NOW()),
(4, CURDATE() + INTERVAL  7 DAY, 0, 149.00, NOW(), NOW()),
(4, CURDATE() + INTERVAL  8 DAY, 4, 129.00, NOW(), NOW()),
(4, CURDATE() + INTERVAL  9 DAY, 3, 129.00, NOW(), NOW()),
(4, CURDATE() + INTERVAL 10 DAY, 5, 129.00, NOW(), NOW()),
(4, CURDATE() + INTERVAL 11 DAY, 2, 149.00, NOW(), NOW()),
(4, CURDATE() + INTERVAL 12 DAY, 1, 149.00, NOW(), NOW()),
(4, CURDATE() + INTERVAL 13 DAY, 3, 129.00, NOW(), NOW());

-- 商家5: 小狗当家寄养训练中心（深圳）shop_id=5, 日价259
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(5, CURDATE() + INTERVAL  0 DAY, 6, 259.00, NOW(), NOW()),
(5, CURDATE() + INTERVAL  1 DAY, 4, 259.00, NOW(), NOW()),
(5, CURDATE() + INTERVAL  2 DAY, 8, 259.00, NOW(), NOW()),
(5, CURDATE() + INTERVAL  3 DAY, 5, 259.00, NOW(), NOW()),
(5, CURDATE() + INTERVAL  4 DAY, 7, 259.00, NOW(), NOW()),
(5, CURDATE() + INTERVAL  5 DAY, 3, 289.00, NOW(), NOW()),
(5, CURDATE() + INTERVAL  6 DAY, 2, 289.00, NOW(), NOW()),
(5, CURDATE() + INTERVAL  7 DAY, 0, 289.00, NOW(), NOW()),
(5, CURDATE() + INTERVAL  8 DAY, 6, 259.00, NOW(), NOW()),
(5, CURDATE() + INTERVAL  9 DAY, 8, 259.00, NOW(), NOW()),
(5, CURDATE() + INTERVAL 10 DAY, 5, 259.00, NOW(), NOW()),
(5, CURDATE() + INTERVAL 11 DAY, 4, 289.00, NOW(), NOW()),
(5, CURDATE() + INTERVAL 12 DAY, 3, 289.00, NOW(), NOW()),
(5, CURDATE() + INTERVAL 13 DAY, 1, 289.00, NOW(), NOW());

-- 商家6: 爱宠屋24h宠物酒店（广州）shop_id=6, 日价179
INSERT INTO shop_schedule (shop_id, date, available, price, create_time, update_time) VALUES
(6, CURDATE() + INTERVAL  0 DAY, 8, 179.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL  1 DAY, 6, 179.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL  2 DAY, 10, 179.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL  3 DAY, 7, 179.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL  4 DAY, 9, 179.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL  5 DAY, 4, 199.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL  6 DAY, 3, 199.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL  7 DAY, 2, 199.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL  8 DAY, 8, 179.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL  9 DAY, 10, 179.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL 10 DAY, 6, 179.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL 11 DAY, 5, 199.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL 12 DAY, 3, 199.00, NOW(), NOW()),
(6, CURDATE() + INTERVAL 13 DAY, 1, 199.00, NOW(), NOW());
