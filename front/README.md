# 🐾 毛孩驿站 (Furbaby Inn)

> 宠物寄养服务平台 — 给毛孩子一个温暖的家

毛孩驿站是一个面向宠物主的寄养服务平台，支持寄养商家检索、宠物档案管理、档期预约、订单支付、寄养期间照片回传及服务评价等完整流程。

## 技术栈

| 类别 | 技术 |
|------|------|
| 框架 | Vue 3 (Composition API) |
| 构建工具 | Vite |
| 路由 | Vue Router 4 |
| 状态管理 | Pinia |
| HTTP 客户端 | Axios |
| 样式方案 | 纯 CSS (CSS Variables) |
| 字体 | Noto Serif SC / Noto Sans SC |

## 项目结构

```
furbaby-inn-front/
├── index.html
├── package.json
├── vite.config.js
├── README.md
├── public/
│   └── favicon.svg
└── src/
    ├── main.js                     # 应用入口
    ├── App.vue                     # 根组件
    ├── assets/
    │   └── styles/
    │       ├── variables.css       # CSS 变量 (颜色/间距/圆角/阴影)
    │       ├── reset.css           # 样式重置
    │       ├── typography.css      # 字体排版系统
    │       └── global.css          # 全局样式 + 动画
    ├── api/                        # API 接口层
    │   ├── request.js              # Axios 实例 + 拦截器
    │   ├── user.js                 # 用户服务
    │   ├── shop.js                 # 商家服务
    │   ├── merchant.js             # 商家管理端统计
    │   ├── pet.js                  # 宠物服务
    │   ├── order.js                # 预约服务
    │   ├── payment.js              # 支付服务
    │   ├── review.js               # 评价服务
    │   └── notify.js               # 通知服务
    ├── stores/                     # Pinia 状态管理
    │   └── user.js                 # 用户状态 (登录/信息)
    ├── router/
    │   └── index.js                # 路由配置 + 导航守卫
    ├── components/
    │   ├── layout/
    │   │   ├── AppHeader.vue       # 顶部导航栏
    │   │   └── AppFooter.vue       # 页脚
    │   ├── common/
    │   │   ├── ShopCard.vue        # 商家卡片
    │   │   ├── PetCard.vue         # 宠物卡片
    │   │   ├── OrderCard.vue       # 订单卡片
    │   │   ├── StarRating.vue      # 星级评分
    │   │   ├── PhotoGrid.vue       # 照片网格
    │   │   └── LoadingSpinner.vue  # 加载动画
    │   └── form/
    │       ├── FormInput.vue       # 表单输入框
    │       └── FormButton.vue      # 按钮
    └── views/                      # 页面视图
        ├── HomeView.vue            # 首页
        ├── LoginView.vue           # 登录
        ├── RegisterView.vue        # 注册
        ├── ShopListView.vue        # 商家列表
        ├── ShopDetailView.vue      # 商家详情
        ├── PetListView.vue         # 宠物列表
        ├── PetCreateView.vue       # 添加宠物
        ├── PetDetailView.vue       # 宠物详情
        ├── BookingView.vue         # 创建预约
        ├── OrderListView.vue       # 订单列表
        ├── OrderDetailView.vue     # 订单详情
        ├── PaymentView.vue         # 支付页
        ├── PhotoGalleryView.vue    # 寄养照片
        ├── ReviewView.vue          # 服务评价
        └── merchant/               # 商家管理端
            ├── DashboardView.vue       # 后台首页
            ├── OrderManagementView.vue # 订单管理
            ├── ReviewManagementView.vue # 评价管理
            └── ShopSettingsView.vue    # 店铺设置
```

## 快速开始

```bash
# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 构建生产版本
npm run build

# 预览生产构建
npm run preview
```

开发服务器默认运行在 `http://localhost:5173`。

## 后端微服务架构

前端通过 API 网关统一请求后端服务，架构图：

```
前端 (Vue 3) → 网关服务 (Gateway) → ├── 用户服务 (user-service)
                                      ├── 商家服务 (shop-service)
                                      ├── 宠物服务 (pet-service)
                                      ├── 预约服务 (order-service)
                                      ├── 支付服务 (payment-service)
                                      ├── 评价服务 (review-service)
                                      └── 通知服务 (notify-service)
```

网关地址通过 Vite 代理配置，开发环境默认代理到 `http://localhost:8080`。

## 页面路由

| 路径 | 页面 | 说明 | 需要登录 |
|------|------|------|---------|
| `/` | 首页 | 搜索入口 + 推荐商家 | 否 |
| `/login` | 登录 | 用户登录 | 否 |
| `/register` | 注册 | 用户注册 | 否 |
| `/shops` | 商家列表 | 搜索/筛选商家 | 否 |
| `/shops/:id` | 商家详情 | 商家详情 + 评价 | 否 |
| `/pets` | 宠物列表 | 我的宠物档案 | 是 |
| `/pets/create` | 添加宠物 | 创建宠物档案 | 是 |
| `/pets/:id` | 宠物详情 | 宠物详情/删除 | 是 |
| `/booking/:shopId` | 创建预约 | 选择宠物和日期 | 是 |
| `/orders` | 订单列表 | 我的订单 | 是 |
| `/orders/:id` | 订单详情 | 订单详情 + 操作 | 是 |
| `/payment/:orderId` | 支付 | 支付确认 | 是 |
| `/photos/:orderId` | 寄养照片 | 查看寄养期间照片 | 是 |
| `/review/:orderId` | 服务评价 | 提交评价 | 是 |
| `/merchant` | 商家后台 | Dashboard 统计 + 快捷入口 | 是(商家) |
| `/merchant/orders` | 订单管理 | 管理店铺订单 | 是(商家) |
| `/merchant/reviews` | 评价管理 | 查看和回复评价 | 是(商家) |
| `/merchant/settings` | 店铺设置 | 营业状态 + 信息编辑 | 是(商家) |

> 商家路由同时受 `requiresAuth` 和 `role: 'shop'` 双重保护，宠物主角色无法访问。

---

# API 接口文档

> 所有接口统一前缀 `/api`，通过网关转发到对应微服务。
> 网关统一响应格式: `{ code: 200, message: "success", data: {...} }`
>
> 前端 Axios 拦截器会自动解包 `data` 字段，以下返回类型均指 `data` 的内容。

## 1. 用户服务 user-service (`/api/user`)

| 方法 | 路径 | 说明 | 请求体 | 返回类型 |
|------|------|------|--------|---------|
| POST | `/user/login` | 用户登录 | `{ phone: string, password: string }` | `{ token: string, userInfo: { id: number, nickname: string, avatar: string, role: 'owner' \| 'shop', phone: string } }` |
| POST | `/user/register` | 用户注册 | `{ phone: string, password: string, nickname: string, role: 'owner' \| 'shop' }` | `{ id: number, nickname: string, phone: string, role: string }` |
| GET | `/user/info` | 获取当前用户信息 | — | `{ id: number, nickname: string, avatar: string, role: string, phone: string, email: string \| null, createTime: string }` |
| PUT | `/user/info` | 更新用户信息 | `{ nickname?: string, avatar?: string, email?: string }` | `{ id: number, nickname: string, avatar: string, role: string, phone: string, email: string }` |
| GET | `/user/info/:userId` | 获取指定用户信息 | — | `{ id: number, nickname: string, avatar: string }` |

## 2. 商家服务 shop-service (`/api/shop`)

| 方法 | 路径 | 说明 | 请求体/参数 | 返回类型 |
|------|------|------|--------|---------|
| GET | `/shop/list` | 商家列表 | `?keyword=&page=&size=&sort=` | `{ total: number, pages: number, records: Shop[] }` |
| GET | `/shop/detail/:id` | 商家详情 | — | `ShopDetail` |
| GET | `/shop/schedule/:shopId` | 商家档期 | `?startDate=&endDate=` | `{ shopId: number, schedules: Schedule[] }` |
| POST | `/shop/schedule` | 发布档期(商家) | `{ dates: Schedule[] }` | `{ success: boolean, count: number }` |
| PUT | `/shop/schedule/:date` | 扣减档期库存 | `{ date: string, delta: number }` | `{ success: boolean, available: number }` |
| POST | `/shop/register` | 商家入驻 | `{ name, phone, address, description, tags, services }` | `{ shopId: number, status: 'pending' }` |
| GET | `/shop/my` | 获取当前商家店铺信息 | — | `MerchantShop` |
| PUT | `/shop/my` | 更新店铺信息 | `{ name?, phone?, address?, description?, tags?, services?, notice? }` | `MerchantShop` |
| PUT | `/shop/my/status` | 切换营业状态 | `{ status: 'open' \| 'closed' }` | `{ success: boolean, status: string }` |
| GET | `/shop/my/dashboard` | 商家后台统计 | — | `DashboardStats` |
| GET | `/shop/my/orders` | 商家订单列表 | `?page=&size=&status=` | `{ total: number, pages: number, records: OrderItem[] }` |

**Shop 对象**: `{ id, name, avatar, rating, price, tags, address, distance, status: 'open' | 'closed' }`  
**ShopDetail 对象**: `{ id, name, avatar, photos, rating, price, tags, address, phone, description, services, notice }`  
**MerchantShop 对象**: `{ shopId: number, name: string, avatar: string, phone: string, address: string, description: string, tags: string[], services: string[], notice: string, rating: number, price: number, status: 'open' | 'closed' }`  
**Schedule 对象**: `{ date: string, available: number, price: number }`  
**DashboardStats 对象**: `{ todayOrders: number, todayRevenue: number, pendingOrders: number, boardingCount: number, totalReviews: number, avgRating: number }`

## 3. 宠物服务 pet-service (`/api/pet`)

| 方法 | 路径 | 说明 | 请求体 | 返回类型 |
|------|------|------|--------|---------|
| GET | `/pet/list` | 我的宠物列表 | — | `Pet[]` |
| POST | `/pet/create` | 创建宠物档案 | `PetCreateParams` | `PetDetail` |
| GET | `/pet/detail/:id` | 宠物详情 | — | `PetDetail` |
| PUT | `/pet/update/:id` | 更新宠物信息 | `PetUpdateParams` | `PetDetail` |
| DELETE | `/pet/delete/:id` | 删除宠物档案 | — | `{ success: boolean }` |

**Pet 对象**: `{ id, name, species, breed, avatar, age, gender, weight }`  
**PetDetail 对象**: `{ ...Pet, healthNotes, vaccines: Vaccine[], createTime }`  
**Vaccine 对象**: `{ name: string, date: string }`  
**PetCreateParams**: `{ name: string, species: string, breed: string, avatar?: string, gender: 'male' | 'female', age: number, weight: number, healthNotes?: string }`

## 4. 预约服务 order-service (`/api/order`)

| 方法 | 路径 | 说明 | 请求体 | 返回类型 |
|------|------|------|--------|---------|
| POST | `/order/create` | 创建预约 | `{ shopId, petId, startDate, endDate, remark? }` | `{ orderId, orderNo, status, amount, createTime }` |
| GET | `/order/list` | 我的订单列表 | `?status=&page=&size=` | `{ total, pages, records: OrderItem[] }` |
| GET | `/order/detail/:id` | 订单详情 | — | `OrderDetail` |
| PUT | `/order/cancel/:id` | 取消预约 | `{ reason?: string }` | `{ success: boolean, status: 'cancelled' }` |
| GET | `/order/status/:id` | 查询订单状态 | — | `{ orderId, status, timeline: StatusStep[] }` |

**OrderItem 对象**: `{ orderId, orderNo, shopName, petName, startDate, endDate, status, amount, createTime }`  
**OrderDetail 对象**: `{ ...OrderItem, shopId, petId, remark, payTime, cancelTime }`  
**StatusStep 对象**: `{ status: string, time: string }`

### 订单状态流转

```
pending → paid → boarding → completed
   ↓         ↓        ↓
cancelled  refunding → refunded
```

| 状态 | 含义 | 可执行操作 |
|------|------|-----------|
| `pending` | 待支付 | 支付、取消 |
| `paid` | 已支付 | 取消、申请退款 |
| `boarding` | 寄养中 | 查看照片、申请退款 |
| `completed` | 已完成 | 评价 |
| `cancelled` | 已取消 | — |
| `refunding` | 退款中 | 商家确认退款 |
| `refunded` | 已退款 | — |

## 5. 支付服务 payment-service (`/api/payment`)

| 方法 | 路径 | 说明 | 请求体 | 返回类型 |
|------|------|------|--------|---------|
| POST | `/payment/create` | 创建支付单 | `{ orderId: number, payMethod: 'wechat' \| 'alipay' }` | `{ paymentId, payNo, amount, qrCode?, payUrl?, expireTime }` |
| GET | `/payment/status/:paymentId` | 查询支付状态 | — | `{ paymentId, status: 'pending' \| 'success' \| 'failed' \| 'closed', payTime? }` |
| POST | `/payment/refund` | 申请退款 | `{ orderId: number, reason?: string }` | `{ refundId: number, status: string, amount: number }` |

## 6. 评价服务 review-service (`/api/review`)

| 方法 | 路径 | 说明 | 请求体 | 返回类型 |
|------|------|------|--------|---------|
| POST | `/review/submit` | 提交评价 | `{ orderId, rating, content, photos? }` | `{ reviewId, rating, content, createTime }` |
| GET | `/review/list/:shopId` | 商家评价列表 | `?page=&size=` | `{ total, avgRating, records: ReviewItem[] }` |
| GET | `/review/my-reviews` | 我的评价 | `?page=&size=` | `{ total, records: ReviewItem[] }` |
| POST | `/review/upload-photo` | 上传寄养照片 | `FormData { orderId, file }` | `{ photoId: number, url: string }` |
| GET | `/review/photos/:orderId` | 获取寄养照片 | — | `BoardingPhoto[]` |
| POST | `/review/reply` | 商家回复评价 | `{ reviewId: number, content: string }` | `{ reviewId: number, reply: string, replyTime: string }` |

**ReviewItem 对象**: `{ reviewId, userId, nickname, avatar, rating, content, photos, createTime, reply?: string, replyTime?: string }`  
**BoardingPhoto 对象**: `{ photoId, url, uploadTime, description? }`

## 7. 通知服务 notify-service (`/api/notify`)

| 方法 | 路径 | 说明 | 请求体 | 返回类型 |
|------|------|------|--------|---------|
| GET | `/notify/list` | 消息列表 | `?page=&size=` | `{ total, records: NotifyItem[] }` |
| PUT | `/notify/read/:id` | 标记已读 | — | `{ success: boolean }` |
| GET | `/notify/unread-count` | 未读数量 | — | `{ count: number }` |

**NotifyItem 对象**: `{ id, type, title, content, read: boolean, createTime }`

---

## Vite 配置说明

`vite.config.js` 中配置了开发代理，将 `/api` 请求转发到网关服务：

```js
server: {
  proxy: {
    '/api': {
      target: 'http://localhost:8080',  // 网关服务地址
      changeOrigin: true,
    },
  },
},
```

## 设计系统

设计主题 **"温暖的庇护所"** (Warm Sanctuary)：

- **主色**: 暖琥珀 `#E8894B` — 温暖、可靠、亲和
- **背景**: 奶油色 `#FFF8F0` — 柔和、舒适
- **文字**: 深咖色 `#3D2B1F` — 沉稳、可读性
- **点缀**: 鼠尾草绿 `#8BA888` — 自然、宁静
- **品牌元素**: 爪印图案贯穿全局，圆润的卡片和按钮

CSS 变量定义在 `src/assets/styles/variables.css`，通过 `global.css` 全局引入。

---

## License

MIT
