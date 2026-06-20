# 毛孩驿站 (Furbaby Inn) 后端服务

> 宠物寄养服务平台后端 — Spring Boot + MyBatis-Plus 单体架构

## 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 框架 | Spring Boot | 3.5.15 |
| ORM | MyBatis-Plus | 3.5.16 |
| 安全 | JWT (jjwt) | 0.12.6 |
| 文档 | SpringDoc OpenAPI + Knife4j | — |
| 数据库 | MySQL | — |
| 构建 | Maven | — |

## 项目结构

```
src/main/java/com/furbaby/furbaby/
├── config/              # 配置（Security、SpringDoc、WebMvc）
├── controller/          # 控制器层（7 个模块）
├── dto/                 # 请求体 DTO
├── entity/              # 数据库实体 + Result 响应体
├── enums/               # 枚举类
├── exception/           # 全局异常处理
├── filter/              # 过滤器
├── inteceptor/          # 拦截器
├── mapper/              # MyBatis-Plus Mapper
├── service/             # 服务接口 + 实现
├── utils/               # 工具类（JWT）
└── vo/                  # 响应体 VO
```

## 公共基础设施

### 统一响应格式 `Result<T>`

```json
{ "code": 200, "message": "成功", "data": { ... } }
```

- `code`: Integer，`200` 成功 / `500` 失败
- 静态方法 `Result.success(data)` / `Result.error(message)` 快速构建

### JWT 认证

- 用户登录后签发 JWT Token，存储在 `Authorization: Bearer <token>` 请求头
- `JWTUtils.getUserIdFromToken(token)` 解析出 userId
- 所有需登录接口在 Controller 层手工提取 token → 传入 Service 层
- 各 Service 根据 userId 做归属校验，防止越权操作

### 全局异常处理

| 异常类 | HTTP 状态码 | 场景 |
|--------|:----------:|------|
| `UnAuthorizedException` | 401 | Token 无效/过期 |
| `NoRegisterException` | 401 | 资源不存在或无权操作 |
| `PhoneOrPasswordException` | 401 | 手机号或密码错误 |
| `Exception` | 500 | 未知系统异常 |

### 手动分页

由于本地 Maven 缓存 MyBatis-Plus 版本（3.5.10.1）不含 `PaginationInnerInterceptor`，统一使用手动分页：

```java
long total = this.count(wrapper);
int offset = (page - 1) * size;
wrapper.last("LIMIT " + offset + "," + size);
```

返回 `PageResult<T>`：`{ total, pages, records }`。

### JSON 字段转换

数据库中 `tags`、`services`、`photos` 等字段以 JSON 字符串存储，查询时通过 Jackson `ObjectMapper` 反序列化为 `List<String>` 返回前端。

---

## 一、用户服务 (`/api/user`)

### 接口列表

| 方法 | 路径 | 说明 | 需登录 |
|------|------|------|:---:|
| POST | `/user/login` | 用户登录 | — |
| POST | `/user/register` | 用户注册 | — |
| GET | `/user/info` | 获取当前用户信息 | 是 |
| PUT | `/user/info` | 更新当前用户信息 | 是 |
| GET | `/user/info/{id}` | 获取指定用户公开信息 | — |

### 实现要点

- **注册**：BCrypt 加密密码存储，校验手机号唯一性，支持 `owner`（宠主）和 `shop`（商家）两种角色
- **登录**：校验手机号+密码 → 生成 JWT Token（24h 有效期）→ 返回 token + 用户信息
- **获取当前用户**：从 `Authorization` 头解析 token → 提取 userId → 查询完整信息
- **更新用户**：仅更新 DTO 中非 null 字段（nickname / avatar / email），防止误覆盖
- **获取指定用户**：返回公开信息（id / nickname / avatar），不暴露手机号等隐私

---

## 二、商家服务 (`/api/shop`)

### 接口列表

| 方法 | 路径 | 说明 | 需登录 |
|------|------|------|:---:|
| GET | `/shop/list` | 商家列表（搜索+排序+分页） | — |
| GET | `/shop/detail/{id}` | 商家详情 | — |
| GET | `/shop/schedule/{shopId}` | 商家档期 | — |
| POST | `/shop/schedule` | 发布档期 | 是（商家） |
| PUT | `/shop/schedule/{date}` | 扣减档期库存 | 是（商家） |
| POST | `/shop/register` | 商家入驻 | 是 |
| GET | `/shop/my` | 获取当前商家店铺信息 | 是（商家） |
| PUT | `/shop/my` | 更新店铺信息 | 是（商家） |
| PUT | `/shop/my/status` | 切换营业状态 | 是（商家） |
| GET | `/shop/my/dashboard` | 商家后台统计 | 是（商家） |
| GET | `/shop/my/orders` | 商家订单列表 | 是（商家） |

### 实现要点

- **商家列表**：支持 keyword 多字段模糊搜索（名称/标签/描述/地址）；支持 rating / price / 默认排序
- **档期发布**：JWT 反查商家身份（shop.userId == userId），支持新增/更新已有日期，批量处理
- **档期扣减**：通过 delta 增量更新（下单 delta=-1，退单 delta=+1），保证库存一致性
- **商家入驻**：一个用户只能注册一个商家；tags/services 以 `List<String>` 入参，写入时序列化为 JSON
- **营业状态**：商家可自主切换 `open`（营业中）/ `closed`（休息中），下单前校验店铺必须为营业中
- **仪表盘统计**：实时查询今日订单数、今日营收、待处理订单、寄养中数量、累计评价数、平均评分
- **商家订单列表**：按店铺 ID 过滤订单，支持状态筛选 + 分页，关联宠物名称展示

---

## 三、宠物服务 (`/api/pet`)

### 接口列表

| 方法 | 路径 | 说明 | 需登录 |
|------|------|------|:---:|
| GET | `/pet/list` | 我的宠物列表 | 是 |
| POST | `/pet/create` | 创建宠物档案 | 是 |
| GET | `/pet/detail/{id}` | 宠物详情（含疫苗记录） | — |
| PUT | `/pet/update/{id}` | 更新宠物信息 | 是（主人） |
| DELETE | `/pet/delete/{id}` | 删除宠物档案 | 是（主人） |

### 实现要点

- **归属校验**：增/改/删操作均验证 `pet.ownerId == userId`，防止越权
- **性别枚举转换**：Entity 存 `PetGender` 枚举，VO 返回 `String`（male/female）
- **疫苗关联**：详情接口自动关联查询 `vaccine` 表返回疫苗列表
- **级联删除**：删除宠物时同步删除关联的所有疫苗记录

---

## 四、预约服务 (`/api/order`)

### 接口列表

| 方法 | 路径 | 说明 | 需登录 |
|------|------|------|:---:|
| POST | `/order/create` | 创建预约 | 是 |
| GET | `/order/list` | 我的订单列表 | 是 |
| GET | `/order/detail/{id}` | 订单详情 | — |
| PUT | `/order/cancel/{id}` | 取消预约 | 是（本人） |
| GET | `/order/status/{id}` | 查询订单状态时间线 | — |

### 订单状态流转

```
         ┌─→ cancelled ←──┐
pending ─┤                 │
    │    └─→ paid ─→ refunding ─→ refunded
    │           │
    └─→ boarding ─→ completed
```

| 状态 | 含义 | 可执行操作 |
|------|------|-----------|
| `pending` | 待支付 | 支付、取消 |
| `paid` | 已支付 | 取消、申请退款 |
| `boarding` | 寄养中 | 查看照片、申请退款 |
| `completed` | 已完成 | 评价 |
| `cancelled` | 已取消 | — |
| `refunding` | 退款中 | — |
| `refunded` | 已退款 | — |

### 实现要点

- **下单流程**：校验商家营业中 → 校验宠物属于当前用户 → 校验入住日期不早于今天 → 逐日检查档期可用性 → 按日累加价格计算总额 → 生成订单号 `ORD + 时间戳 + 4位随机数` → 保存订单 → 逐日扣减档期库存
- **订单号**：`ORD202606201430520012` 格式，保证唯一可追溯
- **列表查询**：按 userId 过滤，支持 `status` 可选筛选，关联查询商家名称和宠物名称（使用 HashMap 缓存避免重复查询）
- **取消订单**：校验 `pending`/`paid` 才可取消 → 设置取消时间和原因 → 恢复档期库存（available+1）
- **状态时间线**：根据订单各时间戳构建完整状态流转记录（pending→paid→boarding→...）

### 档期库存一致性保障

下单减库存、取消还库存、退款还库存均通过 `shopScheduleMapper.updateById()` 逐日操作，保证不会超卖。档期按天粒度管理，每日独立 available 计数。

### 订单状态自动流转（定时任务）

以下两个状态转换不由用户手动触发，而是通过 **Spring `@Scheduled` 定时任务**在每日 0:00 自动扫描执行：

| 转换 | 触发条件 | Cron |
|------|----------|------|
| `paid` → `boarding` | `start_date <= 今天` 且当前状态为 paid | `0 0 0 * * ?` |
| `boarding` → `completed` | `end_date < 今天` 且当前状态为 boarding | `0 0 0 * * ?` |

**实现文件**: `scheduler/OrderStatusScheduler.java`

**原理（企业常用方式）**：

1. **`@EnableScheduling`** 在主类开启 Spring 定时任务支持
2. **`@Scheduled(cron = "0 0 0 * * ?")`** 定义每日 0:00 执行，Spring 在 `TaskScheduler` 线程池中触发
3. **扫描逻辑**：用 MyBatis-Plus `Wrappers.lambdaQuery()` 条件查询匹配的订单，逐笔更新状态并附加日志
4. **幂等性**：通过 `eq(Order::getStatus, 预期状态)` 在 WHERE 条件中锁定当前状态，即便任务重复执行也不会误改
5. **分布式扩展**：多实例部署时需加分布式锁（如 Redis SETNX）避免重复执行，或升级为 XXL-JOB / Elastic-Job 等分布式调度框架统一管理

---

## 五、支付服务 (`/api/payment`)

### 接口列表

| 方法 | 路径 | 说明 | 需登录 |
|------|------|------|:---:|
| POST | `/payment/create` | 创建支付单 | 是 |
| GET | `/payment/status/{id}` | 查询支付状态 | — |
| POST | `/payment/refund` | 申请退款 | 是 |

### 实现要点

- **创建支付**：校验订单归属 + 状态为 `pending` → 生成支付单号 `PAY + 时间戳 + 4位随机数` → 根据支付方式生成模拟 `qrCode`（微信）或 `payUrl`（支付宝）→ 30 分钟过期
- **退款申请**：校验订单为 `paid`/`boarding` 状态 → 创建 Refund 记录（状态 `pending`）→ 更新订单状态为 `refunding`
- **支付状态**：纯查询，返回 `pending / success / failed / closed` 四种状态

---

## 六、评价服务 (`/api/review`)

### 接口列表

| 方法 | 路径 | 说明 | 需登录 |
|------|------|------|:---:|
| POST | `/review/submit` | 提交评价 | 是 |
| GET | `/review/list/{shopId}` | 商家评价列表（含平均分） | — |
| GET | `/review/my-reviews` | 我的评价 | 是 |
| POST | `/review/upload-photo` | 上传寄养照片 | 是 |
| GET | `/review/photos/{orderId}` | 获取寄养照片 | — |

### 实现要点

- **提交评价**：校验订单归属 + 状态为 `completed` → 检查是否已评价（唯一约束）→ 评分 1-5 → photos 序列化为 JSON → 保存评价 → **自动重算商家平均评分**（保留 1 位小数）
- **商家评价列表**：分页 + 关联用户昵称头像 + 计算该商家所有评价的平均分，返回专用 `ReviewPageVO`（含 `avgRating`）
- **上传照片**：接收 `MultipartFile` → 生成模拟 OSS URL → 存入 `boarding_photo` 表
- **照片查询**：按 orderId 查询所有照片，按上传时间倒序

### 商家评分自动更新

每次提交评价后触发 `recalcShopRating()`：查询该商家所有评价 → 计算平均分 → 取 1 位小数 → 更新 `shop.rating`。商户未收到评价时默认 5.0 分。

---

## 七、通知服务 (`/api/notify`)

### 接口列表

| 方法 | 路径 | 说明 | 需登录 |
|------|------|------|:---:|
| GET | `/notify/list` | 消息列表（分页） | 是 |
| PUT | `/notify/read/{id}` | 标记已读 | 是 |
| GET | `/notify/unread-count` | 未读数量 | 是 |

### 实现要点

- **消息列表**：按 userId 过滤，按创建时间倒序，分页返回
- **标记已读**：双重校验（id + userId），防止操作他人通知
- **未读计数**：`SELECT COUNT(*) WHERE user_id = ? AND is_read = 0`

---

## 核心业务流程图

### 完整寄养预约流程

```
用户注册/登录
    │
    ├─→ 创建宠物档案 (POST /pet/create)
    │
    ├─→ 浏览商家列表 (GET /shop/list)
    │     └─→ 查看商家详情 (GET /shop/detail/{id})
    │           └─→ 查看商家档期 (GET /shop/schedule/{shopId})
    │
    ├─→ 创建预约订单 (POST /order/create)
    │     └─→ [系统] 校验宠物归属 + 逐日校验档期可用性 → 计算金额 → 扣减库存
    │
    ├─→ 创建支付单 (POST /payment/create)
    │     └─→ [系统] 生成支付单号 + 二维码/链接
    │
    ├─→ (模拟支付成功后) 订单状态: pending → paid
    │
    ├─→ 寄养期间
    │     ├─→ 上传寄养照片 (POST /review/upload-photo)
    │     └─→ 查看寄养照片 (GET /review/photos/{orderId})
    │
    ├─→ 订单完成 (completed)
    │     └─→ 提交评价 (POST /review/submit)
    │           └─→ [系统] 自动重算商家评分
    │
    └─→ 取消/退款路径
          ├─→ 取消预约 (PUT /order/cancel/{id})
          │     └─→ [系统] 恢复档期库存
          └─→ 申请退款 (POST /payment/refund)
                └─→ [系统] 创建退款记录 → 订单状态: refunding
```

### 模块依赖关系

```
Controller 层:
  UserController    ← 独立
  ShopController    ← 依赖 JWT（商家身份验证）
  PetController     ← 依赖 JWT（用户归属）
  OrderController   ← 依赖 Shop + Pet + Schedule（下单校验链）
  PaymentController ← 依赖 Order（订单状态驱动）
  ReviewController  ← 依赖 Order + Shop + User（评价关联）
  NotifyController  ← 依赖 JWT（用户归属）

数据流向:
  order 表 ←→ shop_schedule 表（库存联动）
  order 表 ←→ payment 表 ←→ refund 表（支付链路）
  order 表 ←→ review 表 ←→ shop 表（评价→评分回流）
  order 表 ←→ boarding_photo 表（寄养照片）
  pet 表 ←→ vaccine 表（级联删除）
```

---

## 设计亮点

1. **档期库存实时联动**：下单扣减、取消恢复、退款恢复，三次库存操作形成闭环，保证数据一致性

2. **细粒度归属校验**：所有写操作均验证"当前用户 == 资源所有者"，防止横向越权（A 用户操作 B 用户的宠物/订单/评价）

3. **评分自动回流**：评价提交后自动重算商家平均评分，取 1 位小数，写入 `shop` 表实现读写分离

4. **JSON 字段透明转换**：`tags`、`services`、`photos` 等列表字段在 DB 存 JSON 字符串，Service 层通过 ObjectMapper 手动序列化/反序列化，前端无感知

5. **状态机驱动**：订单 7 种状态严格按流转图执行，每个操作前校验当前状态是否允许，防止非法状态转换；商家营业状态可自主切换，下单前校验

6. **手动分页兼容**：绕过 MyBatis-Plus 版本差异，统一使用 `LIMIT offset,size` 原生 SQL 分页，避免插件依赖

7. **订单号/支付单号**：`ORD/PAY + yyyyMMddHHmmss + 4位随机数`，支持高并发不重复，可读性强

8. **评价唯一性**：一个订单只能评价一次，防止刷评

---

## 注意事项

- 所有时间字段使用 `LocalDateTime` / `LocalDate`，数据库对应 `datetime` / `date` 类型
- `Order` 实体对应表名 `orders`（MySQL 关键字转义）
- `Notification.isRead` 字段在 Entity 中是 `isRead`，VO 中对应 `read`（符合前端命名习惯）
- 上传照片使用模拟 OSS URL，生产环境需对接真实 OSS 服务
- 支付服务为模拟实现，生产需对接微信/支付宝真实支付回调
- Shop 表仅保留 `biz_status` 营业状态字段（open/closed），审核流程暂未启用，后期可扩展

---

## 测试数据

执行顺序：
```sql
source sql/furbaby_inn_init.sql;   -- 建表
source sql/demo_accounts.sql;       -- 导入测试数据
```

### 体验账号

| 角色 | 手机号 | 密码 | 昵称 |
|------|--------|------|------|
| 宠主（小明） | 13800138001 | 123456 | 小明 |
| 商家（老张） | 13800138002 | 123456 | 老张宠物店 |

### 数据规模

| 表 | 数量 | 说明 |
|----|:---:|------|
| user | 16 | 10 宠主(owner) + 6 商家(shop) |
| shop | 6 | 均营业中(biz_status=open)，各 14 天档期 |
| pet | 12 | 分属不同宠主 |
| vaccine | 42 | 每只宠物 2-5 针 |
| orders | 17 | 覆盖全部 7 种状态 |
| payment | 13 | 含取消/退款关联支付 |
| refund | 4 | 含成功/处理中 |
| review | 7 | 各商家 1-2 条评价 |
| boarding_photo | 8 | 商家拍摄上传 |
| notification | 27 | 商家通知 10 条 + 宠主通知 17 条 |

> **角色分离原则**：订单/支付/评价的 user_id 全部为宠主角色，商家角色只拥有店铺、上传照片和系统通知。
