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
| POST | `/user/logout` | 退出登录，Token 加入黑名单 | 是 |

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

- **商家列表**：支持 keyword 多字段模糊搜索（名称/标签/描述/地址）；支持 rating（评分降序）/ price_asc（价格升序）/ price_desc（价格降序）三种排序，未指定时默认按创建时间降序
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
pending ───────────┤                 │
    │              └─→ paid ─────────┤
    │                   │  ↑         │
    │                   │  │ reject  │
    │                   ↓  │         │
    └────────────→ boarding ┘         │
                        │            │
                        ↓            │
                    completed        │
                                     │
              paid/boarding ─→ refunding ──→ refunded
                                   (商家 approve)
```

| 状态 | 含义 | 可执行操作 |
|------|------|-----------|
| `pending` | 待支付 | 支付、取消 |
| `paid` | 已支付 | 取消、申请退款 |
| `boarding` | 寄养中 | 查看照片、申请退款 |
| `completed` | 已完成 | 评价 |
| `cancelled` | 已取消 | — |
| `refunding` | 退款中 | 商家确认/拒绝 |
| `refunded` | 已退款 | — |

### 实现要点

- **下单流程**：校验商家营业中 → 校验宠物属于当前用户 → 校验入住日期不早于今天 → 逐日检查档期可用性 → 按日累加价格计算总额 → 生成订单号 `ORD + 时间戳 + 4位随机数` → 保存订单 → 逐日扣减档期库存
- **订单号**：`ORD202606201430520012` 格式，保证唯一可追溯
- **列表查询**：按 userId 过滤，支持 `status` 可选筛选，关联查询商家名称和宠物名称（使用 HashMap 缓存避免重复查询）
- **取消订单**：校验 `pending`/`paid` 才可取消 → 设置取消时间和原因 → 恢复档期库存（available+1）
- **状态时间线**：根据订单各时间戳构建完整状态流转记录（pending→paid→boarding→...）

### 档期库存一致性保障

下单减库存、取消还库存、退款还库存均通过 `shopScheduleMapper.updateById()` 逐日操作，保证不会超卖。档期按天粒度管理，每日独立 available 计数。

### 订单状态自动流转（RabbitMQ 延迟消息）

订单状态转换不由用户手动触发，而是通过 **RabbitMQ 死信队列（DLX）+ per-message TTL** 实现精确延迟投递：

| 转换 | 消息类型 | TTL | 触发条件 |
|------|---------|-----|---------|
| `pending` → `cancelled` | `TIMEOUT_CANCEL` | 30 min | 超时未支付，自动取消并恢复档期 |
| `paid` → `boarding` | `START_BOARDING` | startDate - now | 到达寄养开始日期 |
| `boarding` → `completed` | `COMPLETE_BOARDING` | endDate+1day - now | 超过寄养结束日期 |

**实现文件：**
- `mq/OrderMessageSender.java` — 发送延迟消息（per-message TTL 写入 `expiration` 头）
- `mq/OrderMessageListener.java` — 消费延迟消息执行状态流转（含幂等校验）
- `scheduler/OrderStatusScheduler.java` — 已注释 `@Scheduled`，保留方法体作为 RabbitMQ 故障时的补漏方案

**原理：**

1. **消息发送**：订单创建/支付时将消息发送到 `order.delay.queue`（该队列无消费者），RabbitMQ 在消息 TTL 到期后自动将其转发到死信交换机 `order.dlx.exchange` → 路由到 `order.process.queue`
2. **精确触发**：每条消息独立 TTL，30 分钟超时、按寄养日期触发等不同延迟互不干扰，精确到毫秒级
3. **幂等校验**：消费者处理前先检查订单当前状态，仅当状态匹配时才执行流转。TIMEOUT_CANCEL 发出一条后即使 30 分钟内订单已支付，消息到达时因状态检查（非 pending → 跳过）也不会误取消
4. **手动 ACK**：消费成功后 `basicAck` 确认；业务异常时 `basicNack` 重新入队，配合持久化队列保证消息不丢失
5. **定时任务兜底**：`@Scheduled` 仅注释不删除，RabbitMQ 连接故障时可取消注释恢复每日全表扫描补漏

---

## 五、支付服务 (`/api/payment`)

### 接口列表

| 方法 | 路径 | 说明 | 需登录 |
|------|------|------|:---:|
| POST | `/payment/create` | 创建支付单 | 是 |
| GET | `/payment/status/{id}` | 查询支付状态 | — |
| POST | `/payment/refund` | 申请退款（宠主/商家均可发起） | 是 |
| PUT | `/payment/refund/{id}/process` | 商家处理退款（同意/拒绝） | 是（商家） |

### 实现要点

- **创建支付**：校验订单归属 + 状态为 `pending` → 生成支付单号 `PAY + 时间戳 + 4位随机数` → 根据支付方式生成模拟 `qrCode`（微信）或 `payUrl`（支付宝）→ 30 分钟过期 → 同步更新订单状态为 `paid`
- **退款申请**：校验订单为 `paid`/`boarding` 状态，且发起人必须为订单宠主或订单所属店铺的商家 → 创建 Refund 记录（状态 `pending`）→ 更新订单状态为 `refunding`
- **商家处理退款**：
  - `approve`（同意）：退款记录标记 `success`，订单状态置为 `refunded`，逐日恢复档期库存
  - `reject`（拒绝）：退款记录标记 `failed`，订单状态恢复为 `boarding`（继续寄养）
  - 仅订单对应店铺的商家有权操作
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
| POST | `/review/reply` | 商家回复评价 | 是（商家） |

### 实现要点

- **提交评价**：校验订单归属 + 状态为 `completed` → 检查是否已评价（唯一约束）→ 评分 1-5 → photos 序列化为 JSON → 保存评价 → **自动重算商家平均评分**（保留 1 位小数）
- **商家评价列表**：分页 + 关联用户昵称头像 + 计算该商家所有评价的平均分，返回专用 `ReviewPageVO`（含 `avgRating`），同时返回 `reply`/`replyTime` 字段
- **上传照片**：接收 `MultipartFile` → 生成模拟 OSS URL → 存入 `boarding_photo` 表
- **照片查询**：按 orderId 查询所有照片，按上传时间倒序
- **商家回复**：校验当前用户为评价所属店铺的店主 → 每条评价仅可回复一次 → 记录回复内容和时间，评价列表同步展示

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
          ├─→ 申请退款 (POST /payment/refund)  ← 宠主或商家均可发起
          │     └─→ [系统] 创建退款记录 → 订单状态: refunding
          └─→ 商家处理退款 (PUT /payment/refund/{id}/process)
                ├─→ approve: 退款成功，订单 refunded，恢复档期
                └─→ reject: 退款驳回，订单恢复 boarding
```

### 模块依赖关系

```
Controller 层:
  UserController    ← 独立
  ShopController    ← 依赖 JWT（商家身份验证）
  PetController     ← 依赖 JWT（用户归属）
  OrderController   ← 依赖 Shop + Pet + Schedule（下单校验链）
  PaymentController ← 依赖 Order + Shop（退款需校验商家身份）
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

9. **退款双通道**：宠主和商家均可发起退款申请，商家审批后自动恢复档期库存或被驳回继续寄养，形成完整退款闭环

10. **定时任务兜底**：每日凌晨自动扫描，将到达寄养日期的 paid 订单转为 boarding，将已过结束日期的 boarding 订单转为 completed，无需人工干预

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

---

## 后续演进：从单体到微服务

当前项目采用 Spring Boot 单体架构，所有模块在一个进程内运行。随着业务量增长和团队规模扩大，按以下三个阶段逐步演进。

### 演进路线图

```
Redis ────→ RabbitMQ ────→ 微服务拆分 + Gateway
(基础设施)    (异步消息)      (服务治理)
```

三个阶段按依赖关系依次推进，每一步都为下一步铺设基础设施，避免重复建设。

---

### 第一阶段：Redis 集成

**目标：** 在单体架构内引入缓存和分布式锁，为后续拆分打好基础。

| 能力 | 应用场景 | 解决的问题 |
|------|---------|-----------|
| 数据缓存 | 商家列表（热门排序）、商家详情、档期数据 | 高并发读场景下的 DB 瓶颈 |
| 分布式锁 | 下单扣减档期库存、支付回调幂等、退款审批防重 | 当前仅靠数据库行锁，多实例部署后会失效 |
| Token 黑名单 | 配合 JWT 实现主动登出/强制下线 | JWT 无状态特性导致无法主动失效 Token |

#### 已实现：数据缓存

**缓存策略：** Cache-Aside 模式 — 先查缓存，命中直接返回；未命中则查数据库，回填缓存后返回。更新时先写数据库，再删除对应缓存。

**实现文件：**
- `cache/CacheHelper.java` — 封装 getOrFetch / evict / evictPattern，处理 Redis 异常降级（Redis 不可用时自动回源数据库）
- `config/RedisConfig.java` — RedisTemplate 配置，使用 GenericJackson2JsonRedisSerializer 序列化，自动携带类型信息

**热点数据判断依据：**

并非所有接口都需要缓存，以下三个维度决定是否值得缓存：

| 维度 | 判断标准 | 说明 |
|------|---------|------|
| 访问频率 | 接口被高频调用，且多次查询结果短期内不变 | 商家列表是首页入口，每次搜索/翻页/切换排序都重新请求，PV 最高 |
| 数据稳定性 | 数据变更频率低，或对实时性要求不严格 | 商家名称/地址/电话/描述很少改动；评分变化频率也低 |
| 共享性 | 多条查询返回相同结果，不依赖当前用户身份 | 列表/详情/档期对所有访问者返回相同数据（公开接口），可最大化缓存复用率 |

据此筛选出三个缓存目标：

| 缓存目标 | 缓存键格式 | TTL | 判定理由 |
|---------|-----------|:---:|---------|
| 商家列表 | `shop:list:{keyword}:{sort}:{page}:{size}` | 5 min | 首页高频入口；不同排序/关键词组合各有缓存键；TTL 较短以兼顾分页参数多和排序新鲜度 |
| 商家详情 | `shop:detail:{id}` | 10 min | 店铺基础信息极少变更（名称、电话、地址等），可缓存较久 |
| 商家档期 | `shop:schedule:{shopId}:{start}:{end}` | 5 min | 预约流程高频访问；档期库存随下单实时变化，TTL 不宜过长避免展示已售罄档期 |

**TTL 取值原则：**

1. **5 分钟档（列表、档期）：** 数据受写操作影响较大 — 商家入驻/编辑会改变列表内容，下单/取消会改变档期可用量。5 分钟是"用户可接受的短暂滞后"和"缓存命中率"的平衡点。
2. **10 分钟档（详情）：** 店铺基础信息变更极低频，10 分钟过期能在不牺牲数据新鲜度的前提下大幅降低 DB 查询。
3. 不做长期缓存（30 分钟以上），因为当前数据量不大，长期缓存收益递减，且短 TTL 天然控制内存占用。

**缓存驱逐策略（先写库，后删缓存）：**

| 触发操作 | 驱逐的缓存 | 原因 |
|---------|-----------|------|
| 商家入驻 (`registerShop`) | `shop:list:*` | 新商家出现，所有列表缓存失效 |
| 更新店铺信息 (`updateMyShop`) | `shop:detail:{id}` + `shop:list:*` | 名称/地址/电话等字段变更影响详情和列表 |
| 切换营业状态 (`updateShopStatus`) | `shop:detail:{id}` + `shop:list:*` | 状态从 open↔closed 影响列表展示和详情页预约按钮 |
| 发布档期 (`publishSchedule`) | `shop:schedule:{shopId}:*` + `shop:list:*` | 档期价格变更影响该店所有档期缓存；价格变化影响列表排序 |
| 更新档期 (`updateSchedule`) | `shop:schedule:{shopId}:*` | 可用量变化影响该店所有档期缓存 |
| 创建订单 (`createOrder`) | `shop:schedule:{shopId}:*` + `shop:list:*` | 扣减档期库存影响可用量 |
| 取消订单 (`cancelOrder`) | `shop:schedule:{shopId}:*` | 恢复档期库存影响可用量 |
| 退款审批通过 (`processRefund approve`) | `shop:schedule:{shopId}:*` | 恢复档期库存影响可用量 |
| 提交评价 (`submitReview`) | `shop:detail:{id}` + `shop:list:*` | 评分重算影响详情展示和列表排序 |

**容错设计：** CacheHelper 所有 Redis 操作均以 try-catch 包裹，Redis 不可用时自动降级到数据库查询，不影响主流程。

#### 已实现：分布式锁

**实现文件：** `lock/DistributedLock.java`

**核心原理：**

```
加锁:  SET lock:shop:schedule:3 {UUID} NX PX 10000
          ↑                        ↑      ↑   ↑
      key=资源标识          随机凭证  仅当不存在 10秒后自动过期

解锁:  Lua 脚本原子校验 GET key == {UUID} → DEL key
                         ↑
               只删自己的锁，不误删别人的
```

| 机制 | 说明 | 解决的问题 |
|------|------|-----------|
| `SET NX PX` | Redis 原子指令，key 不存在时才写入并设置过期时间 | 互斥性：同一时刻仅一个线程持有锁 |
| UUID 凭证 | 每次加锁生成唯一随机值，解锁时校验 | 防误删：锁过期后其他线程已持有新锁，旧持有者无法释放别人的锁 |
| Lua 脚本解锁 | `GET + DEL` 在 Redis 服务端原子执行 | 校验和删除之间无时间窗口，避免并发问题 |
| 锁过期兜底 | 即使业务线程崩溃未解锁，TTL 到期自动释放 | 防死锁：不会因异常导致锁永久占用 |
| 锁内二次校验 | 获取锁后重新查询数据状态 | 防止锁外检查到锁内执行之间的状态变更（double-check） |

**加锁粒度设计：**

| 场景 | 锁键 | TTL | 超时 | 粒度依据 |
|------|------|:---:|:---:|------|
| 创建订单 | `lock:shop:schedule:{shopId}` | 10s | 5s | 按商家加锁：不同商家并行下单，同一商家串行化防止超卖 |
| 退款审批 | `lock:refund:{refundId}` | 5s | 3s | 按退款单加锁：同一退款单不可并发审批 |

锁粒度选择 `shop:schedule:{shopId}` 而非 `shop:schedule:{shopId}:{date}`（按天），权衡：

- 按商家加锁（当前方案）：同一商家所有预约串行，锁持有时间极短（几十毫秒），不会成为瓶颈；实现简单，锁键少
- 按天加锁：并发度更高，但下单跨越多个日期时需要获取多把锁，增加死锁风险和代码复杂度。当前业务量不需要此粒度

**实现示例（OrderServiceImpl.createOrder）：**

```java
String lockHolder = distributedLock.tryLock(
        "shop:schedule:" + dto.getShopId(),
        Duration.ofSeconds(10),   // 锁 TTL：防止死锁
        Duration.ofSeconds(5));   // 最大等待：提示用户重试

if (lockHolder == null) {
    throw new NoRegisterException("当前预约人数较多，请稍后重试");
}

try {
    // 临界区：查询档期 → 校验可用 → 创建订单 → 扣减库存 → 驱逐缓存
} finally {
    distributedLock.unlock("shop:schedule:" + dto.getShopId(), lockHolder);
}
```

**与 @Synchronized 的对比：**

| | `synchronized` | Redis 分布式锁 |
|------|:---:|:---:|
| 作用范围 | 单 JVM 内 | 跨 JVM（多实例、多服务） |
| 锁释放 | 代码块结束或异常自动释放 | TTL 自动过期 + finally 主动释放，双重保障 |
| 死锁风险 | 无（JVM 保证释放） | 有，通过 TTL 兜底解决 |
| 性能 | 极高（内存级） | 高（网络往返 ~1ms） |
| 适用场景 | 单实例开发/测试 | 多实例部署、微服务环境 |

**设计亮点：**

1. **UUID 防误删** — 解锁脚本校验 value 匹配后才 DEL，避免锁过期后 A 线程释放 B 线程持有的锁。这是 Redis 官方推荐的分布式锁正确实现方式，区别于网上常见的"直接 DEL key"的错误写法

2. **锁内二次校验（Double-Check）** — 以退款审批为例：锁外先检查 `refund.status == pending`，获取锁后再次查询确认状态未变。因为在等待锁期间，前一个持有者可能已经处理完毕，二次校验防止重复操作

3. **锁膨胀自动恢复** — 即使 `try` 块内抛出异常（如档期不足），`finally` 块保证锁一定释放，不会因业务异常导致锁泄漏

4. **锁粒度的业务隔离** — `lock:shop:schedule:3` 和 `lock:shop:schedule:5` 是两把独立的锁，商家 3 和商家 5 的预约完全并行，锁只阻止对同一商家的并发竞争

5. **用户友好降级** — 获取锁超时不抛系统异常，而是返回"预约人数较多，请稍后重试"，用户可以刷新重试，而非看到 500 错误

#### 已实现：Token 黑名单

**实现文件：** `security/TokenBlacklist.java`、`filter/LoginFilter.java`（已改造并启用）

**解决的问题：** JWT 是无状态的，签发后直到过期前始终有效，无法主动收回。Token 黑名单提供了一种撤销机制——将需要作废的 Token 存入 Redis，后续请求携带该 Token 时直接被拦截。

**黑名单判定标准（什么情况下将 Token 加入黑名单）：**

| 场景 | 触发方式 | 判定依据 |
|------|---------|---------|
| 用户主动登出 | `POST /user/logout` | 用户明确发起登出操作，当前 Token 立即失效 |
| 修改密码 | 业务层调用 `blacklist(token)` | 密码变更后旧 Token 应失效，防止被盗用后继续使用 |
| 管理员封禁/冻结用户 | 管理端调用 | 违规用户所有活跃 Token 加入黑名单，强制下线 |
| 检测到异常行为 | 风控系统调用（扩展预留） | 同一 Token 短时间内请求频率异常、IP 跳变等 |

> 不需要入黑名单：Token 自然过期的（JWT 自带 expiration 校验，无需黑名单处理）；Token 格式错误的（Filter 在签名校验阶段已拦截）。

**存储策略：**

```
Key:   blacklist:token:{sha256前32位}
Value: "1"
TTL:   Token剩余有效期（毫秒）

示例：
Token 在 2026-06-22 14:00 过期，用户在 2026-06-21 14:00 登出
→ TTL = 24小时，到期后 Redis 自动删除，不占持久内存
```

选用 SHA-256 哈希而非明文 Token 作为键，避免原始 JWT 暴露在 Redis 中。TTL 对齐 Token 自然过期时间，确保黑名单条目不会无限积累。

**请求拦截流程：**

```
请求到达 LoginFilter
  │
  ├─ 无 Authorization 头 → 放行（公开接口）
  │
  └─ 有 Authorization 头
       │
       ├─ 格式校验（Bearer xxx）
       ├─ JWT 签名 + 过期校验
       ├─ 黑名单查询 → 命中 → 401 "Token已失效"
       └─ 全部通过 → 放行
```

**LoginFilter 改动说明：**

此前 `@ServletComponentScan` 被注释掉，Filter 未生效，各 Controller 手动提取 Token。此次改造：

1. **启用全局 Filter** — 取消注释 `@ServletComponentScan`
2. **不强制认证** — 改为"有 Token 才校验，无 Token 放行"，兼容公开接口（商家列表、详情、档期等）
3. **修复原 Filter 的逻辑缺陷** — 原代码在放行登录/注册后没有 `return`，导致继续执行 Token 校验而报错
4. **集成黑名单检查** — 在签名校验之后、业务处理之前插入黑名单查询

**容错设计：**

- Redis 不可用时 `isBlacklisted()` 返回 `false`（放过），保证系统可用性优先于黑名单检查
- `blacklist()` 操作失败只记日志不抛异常，不影响登出响应

**集成端点：**

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/user/logout` | 退出登录，当前 Token 加入黑名单，返回 `{ success: true, message: "已退出登录" }` |

---

### 第二阶段：RabbitMQ 集成

**目标：** 用消息队列替换定时轮询，实现异步解耦和可靠延迟消息。

| 能力 | 应用场景 | 状态 |
|------|---------|:--:|
| 延迟消息 | 订单超时取消（pending 30min 未支付）、寄养到期自动完成 | 已实现 |
| 死信队列 (DLX) | DLX + per-message TTL 实现可变延迟，无插件依赖 | 已实现 |
| 事件广播 | 支付成功 → 异步创建通知消息 | 已实现 |

#### 已实现：延迟消息（DLX + TTL）

**实现文件：**
- `config/RabbitMQConfig.java` — 交换机、队列、绑定拓扑声明
- `mq/OrderMessage.java` — 消息体（type + orderId + shopId + eventTime）
- `mq/OrderMessageSender.java` — 发送延迟消息（per-message TTL）和事件消息
- `mq/OrderMessageListener.java` — 消费延迟消息执行状态流转 + 消费支付事件创建通知

**为什么不依赖 rabbitmq-delayed-message-exchange 插件：**

该插件需要在 RabbitMQ 服务端单独安装，部分云环境不支持。DLX + TTL 是 RabbitMQ 原生能力，无需额外插件，通用性更强。per-message TTL 可以在每条消息上设置不同的过期时间，满足"30 分钟超时取消"和"按寄养日期触发"两种不同延迟需求。

**消息流转架构：**

```
订单创建/支付                    延迟队列               死信交换机        处理队列            消费者
─────────────                  ────────              ──────────       ────────          ────────
                                                          
createOrder ──→ TIMEOUT_CANCEL ──→ order.delay.queue ──→ (TTL到期) ──→ order.dlx.exchange ──→ order.process.queue ──→ 取消订单
  (pending)        30min TTL        (无消费者)                                       │
                                                            	 	                  │
createPayment ─→ START_BOARDING ──→ order.delay.queue ──→ (TTL到期) ──→ order.dlx.exchange ──→ order.process.queue ──→ 开始寄养
  (paid)         startDate-now       (无消费者)                                       │        └→ 发送 COMPLETE_BOARDING
                                                                                      │
COMPLETE_BOARDING ─────────────────→ order.delay.queue ──→ (TTL到期) ──→ order.dlx.exchange ──→ order.process.queue ──→ 寄养完成
  (boarding)      endDate+1-now      (无消费者)
```

**三种延迟消息的状态流转链：**

| 消息类型 | 触发时机 | TTL | 消费动作 | 幂等校验 |
|---------|---------|-----|---------|---------|
| `TIMEOUT_CANCEL` | 订单创建后（pending） | 30 min | pending → cancelled，恢复档期 | 订单非 pending 则跳过 |
| `START_BOARDING` | 支付成功后（paid） | startDate - now | paid → boarding，发送 COMPLETE_BOARDING | 订单非 paid 则跳过 |
| `COMPLETE_BOARDING` | 寄养开始后（boarding） | endDate+1day - now | boarding → completed | 订单非 boarding 则跳过 |

**为什么一条消息链式触发下一条：**

`START_BOARDING` 消费成功后立即发送 `COMPLETE_BOARDING`，而不是在订单创建时就发两条。原因是：如果订单在 pending 状态就超时取消了，`COMPLETE_BOARDING` 消息就成了废消息。链式发送保证只有真正进入 boarding 的订单才会触发完成消息，减少无效消息。

**与定时任务的对比：**

| | `@Scheduled` 全表扫描 | RabbitMQ 延迟消息 |
|------|:---:|:---:|
| 触发方式 | 每日 0:00 扫描全部订单 | 每条订单精确到期触发 |
| 数据库负载 | 每次扫描 `WHERE status = ? AND date <= ?` | 仅按主键 `SELECTById` |
| 实时性 | 最多延迟 24 小时 | 精确到毫秒级 |
| 消息可靠性 | 无持久化，服务宕机漏执行 | 消息持久化到磁盘，重启不丢 |
| 多实例 | 需分布式锁防止重复执行 | MQ 自带竞争消费，天然防重 |
| 可观测性 | 仅日志 | RabbitMQ Management 面板可视化 |

定时任务未删除，仅注释了 `@Scheduled`，保留方法体作为 RabbitMQ 故障时的补漏手段。需要时取消注释即可恢复。

#### 已实现：支付成功事件广播

**流程：**

```
PaymentServiceImpl.createPayment()
    │
    └─→ orderMessageSender.sendOrderEvent(PAYMENT_SUCCESS)
            │
            ▼
    order.event.exchange (topic) ──→ order.event.queue ──→ OrderMessageListener
                                                              │
                                                              └─→ 创建 Notification
                                                                   userId = 订单用户
                                                                   title = "支付成功"
                                                                   content = "订单 ORDxxx 已支付成功"
```

支付成功后不再同步调用通知创建逻辑，而是发布事件。消费者异步处理，支付接口响应时间不受通知创建影响。后续拆分微服务时，通知服务可直接订阅 `order.event.exchange` 获取事件，无需修改支付模块代码。

**手动 ACK 保证消息不丢失：**

```java
@RabbitListener(queues = ORDER_PROCESS_QUEUE, ackMode = "MANUAL")
public void handleDelayedMessage(OrderMessage msg, Channel channel, long tag) {
    try {
        // 业务处理
        channel.basicAck(tag, false);   // 成功 → 确认消费
    } catch (Exception e) {
        channel.basicNack(tag, false, true); // 失败 → 重新入队重试
    }
}
```

#### 设计亮点

1. **DLX + TTL 无插件依赖** — 使用 RabbitMQ 原生死信队列实现延迟投递，不依赖 `rabbitmq-delayed-message-exchange` 插件，任何 RabbitMQ 环境开箱可用

2. **per-message TTL 灵活延迟** — 不同业务场景使用不同 TTL（30min 固定 vs 按寄养日期动态计算），无需为每种 TTL 创建独立队列，降低运维复杂度

3. **链式消息触发** — START_BOARDING 消费成功后才发送 COMPLETE_BOARDING，避免无效消息。若订单在 pending 阶段超时取消，后续消息链自动中断

4. **幂等消费** — 每条消息处理前先检查订单当前状态，仅当状态匹配时才执行流转。防止消息重复投递导致状态错乱，也允许定时任务补漏时二者不会冲突

5. **手动 ACK + 重新入队** — 业务异常时 `basicNack(tag, false, true)` 让消息重回队列，配合 RabbitMQ 的消息持久化（durable=true），保证进程崩溃也不丢消息

6. **定时任务保留兜底** — `@Scheduled` 仅注释不删除，RabbitMQ 故障时可立即恢复定时扫描补漏，避免单点依赖

7. **事件异步解耦** — 支付成功通过 TopicExchange 广播，消费者独立处理通知创建。支付接口响应不受通知逻辑阻塞，后续拆分微服务时直接复用事件通道

---

### 第三阶段：微服务拆分 + Spring Cloud Gateway

**目标：** 按业务边界拆分为独立微服务，引入网关统一入口和服务治理。

**拆分后的架构：**

```
Vue 3 前端
    │
    ▼
Spring Cloud Gateway (统一鉴权 / 限流 / 路由)
    │
    ├── user-service      (用户 + JWT 签发)
    ├── shop-service      (商家 + 档期管理)
    ├── order-service     (预约 + 订单状态机)
    ├── payment-service   (支付 + 退款)
    ├── review-service    (评价 + 寄养照片)
    └── notify-service    (通知消息)
```

**基础设施组件：**

| 组件 | 用途 | 说明 |
|------|------|------|
| Nacos | 注册中心 + 配置中心 | 替代 `application.yml` 分散配置，动态刷新 |
| Spring Cloud Gateway | API 网关 | 替代当前各 Controller 手动 `@RequestHeader` 取 Token，统一鉴权 |
| OpenFeign | 服务间同步调用 | 替代当前 Service 直接注入 Mapper 跨表查询 |
| Sentinel | 熔断降级 | 防止级联故障，保护核心链路 |

**拆分顺序（按依赖关系排列）：**

| 阶段 | 服务 | 关键动作 |
|:---:|------|---------|
| 1 | user-service | 独立认证鉴权，其他服务通过 Feign 验证 Token |
| 2 | shop-service | 商家 + 档期独立，暴露 Feign 接口供 order-service 调用 |
| 3 | order-service | 订单状态机独立，下单时通过 Feign 校验宠物/商家/档期 |
| 4 | payment-service | 支付/退款独立，通过 RabbitMQ 发送支付成功事件 |
| 5 | review-service | 评价 + 照片独立，评价后通过事件更新商家评分 |
| 6 | notify-service | 通知独立，监听 RabbitMQ 事件生成通知 |
| 7 | gateway | 最后上线，统一接管所有路由 |

当前代码已采用 **Service 接口 + Impl** 分离、**DTO/VO/Entity 三层模型**、**Result 统一响应格式**，与微服务架构天然对齐。拆分时核心工作是拷包 + 将直接注入的 Mapper 调用替换为 Feign 远程调用，Service 层业务逻辑可整体复用。

**数据拆分策略：**

初期各服务共享同一数据库实例（分库不分表），通过独立 Schema 或表前缀做逻辑隔离，降低数据迁移风险。后续再按实际负载逐步分库，配合 Canal + Binlog 做数据同步。分布式事务采用 Seata AT 模式，覆盖下单扣库存 + 创建订单这种跨服务的写操作。

---

### 演进原则

1. **先基础设施，后服务拆分** — Redis 和 RabbitMQ 是微服务的通信基础设施，先部署好，拆分时直接使用
2. **增量迁移，逐步验证** — 每次只拆分一个服务，上线观察后再推进下一个，保证系统可用性
3. **单体不做大改** — 当前单体代码结构已为拆分设计（接口分离、DTO/VO 隔离），拆分时不需要推翻重来
4. **监控先行** — 引入 Spring Boot Actuator + Prometheus + Grafana，对每个新上线的服务建立健康检查和核心指标监控
