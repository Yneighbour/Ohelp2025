# o-f 管理后台 UI 改造说明（参考 11111111111111111 静态演示项目）

> 目标：将主前端项目 o-f 的视觉与交互规范，统一到测试项目（11111111111111111）的“紫色主题 + 阴影层级 + 轻动画 + 高可读性”风格。
> 
> 范围：仅描述 UI/UX 规范与落地改造建议；不讨论“路由权限/业务权限”。

---

## 1. 参考源（对标对象）

测试项目目录：`11111111111111111/`

- 设计基调：紫色主题、渐变、阴影系统、卡片化布局、清晰层级
- 关键文件：
  - 设计 tokens：`11111111111111111/styles/variables.css`
  - 公共结构：`11111111111111111/styles/common.css`
  - 管理后台布局：`11111111111111111/styles/admin.css`

---

## 2. o-f 当前基础（可复用资产）

o-f 已具备与测试项目一致的基础 tokens/动画系统，可直接作为改造底座：

- 设计 tokens：`o-f/src/styles/themes/variables.css`
- 公共样式：`o-f/src/styles/common.css`
- 动画/工具类：`o-f/src/styles/index.css`
- 基础组件：
  - `o-f/src/components/base/BaseButton.vue`
  - `o-f/src/components/base/BaseCard.vue`
  - `o-f/src/components/base/BaseInput.vue`
  - `o-f/src/components/base/BaseTable.vue`

> 结论：本次 UI 改造重点不是“从零搭框架”，而是“统一页面结构/视觉语言/交互反馈”，并补齐各模块页面的细节一致性。

---

## 3. 视觉风格总则（必须遵守）

### 3.1 主题色与渐变

- 主色：`--primary-color: #7C3AED`
- 主渐变：`--primary-gradient: linear-gradient(135deg, #7C3AED 0%, #A78BFA 100%)`
- 文本高亮：标题/Logo 使用渐变文字（与测试项目一致）

### 3.2 阴影与层级

- 卡片：使用 `--shadow-card` 或 `--shadow-md`（轻层级）
- 悬浮：hover 时提升到 `--shadow-lg` 并 `translateY(-2px)`
- 强调：关键按钮 hover 使用 `--shadow-purple`

### 3.3 圆角与间距

- 卡片圆角：`--border-radius-lg`（12px）
- 按钮圆角：`--border-radius-md`（8px）或更柔和（12px）
- 页面主间距：桌面端 `--spacing-lg`（24px），模块内部尽量用 16/24 体系

### 3.4 字体与可读性

- 基础字号：16px
- 页面标题：20~24px
- 信息密度：管理后台允许略紧凑，但仍保持 14~16px 为主
- 表格：表头 14px + 字重 600，内容 14~16px

---

## 4. 布局规范（与测试项目 admin.css 对齐）

### 4.1 结构分层

推荐统一为四层：

1) TopNav（固定）
2) Sidebar（固定）
3) Content（滚动）
4) Overlay（Modal/Toast/Loading）

### 4.2 顶部导航（TopNav）

- 固定高度：`--header-height: 64px`
- 背景：白底 + 轻阴影 + 下边框
- Logo：渐变文字 + 700 字重
- 右侧用户区：hover 背景使用 `--nav-hover-bg`

落地点：`o-f/src/styles/common.css` 中 `.top-nav` 及其子元素。

### 4.3 侧边栏（Sidebar）

两种可选方案（选其一，禁止混用）：

**方案 A（当前 o-f 方案）：白底侧边栏（已实现）**
- 白底 + 分组标题 + hover/active 背景浅紫
- 适合“企业后台”清爽风

**方案 B（对齐测试项目 admin.css）：深色渐变侧边栏**
- 背景：深蓝紫渐变 `linear-gradient(180deg, #1e1b4b 0%, #312e81 100%)`
- 文字：白/半透明白
- active：左侧高亮条或背景紫色透明层

建议：若追求与测试项目一致度更高，采用方案 B，并将颜色抽成变量，例如：

- `--sidebar-bg: linear-gradient(180deg, #1e1b4b 0%, #312e81 100%)`
- `--sidebar-text: rgba(255,255,255,0.8)`
- `--sidebar-active-bg: rgba(124, 58, 237, 0.3)`

### 4.4 主内容区（Content）

- 内容默认 padding：24px
- 页面头（PageHeader）+ 内容卡片（BaseCard）为标准结构
- 表格/图表等内容均放在卡片内

落地点：各模块 `*Module.vue` 的页面头部结构。

---

## 5. 组件规范（页面一致性的核心）

### 5.1 按钮（BaseButton）

- 主按钮：渐变背景 + hover 提升 + active 缩放
- 次按钮：白底边框 + hover 变浅紫背景
- 危险按钮：红色系（可用渐变或纯色，保持一致）
- 最小点击目标：桌面端 40px，移动端 44px

对齐现状：`BaseButton.vue` 已基本满足。

### 5.2 卡片（BaseCard）

- Header 区背景：`--background-secondary`
- Body 区：24px padding（移动端 16px）
- Hover 卡片：启用 `hover` 属性，统一使用 `translateY(-2px)` + `--shadow-lg`

对齐现状：`BaseCard.vue` 已基本满足。

### 5.3 表单（输入框、标签、校验）

- 输入框 focus：
  - 1px 主色描边或轻主色阴影
  - 不使用突兀的深色外发光
- 错误提示：红色（`--error-color`）+ 12~14px
- 表单布局：
  - 桌面端优先双列（grid）
  - 移动端单列

建议落地点：
- `o-f/src/components/base/BaseInput.vue`（focus/错误态）
- `o-f/src/components/form/FormValidator.vue`（一致性文案/样式）

### 5.4 表格（BaseTable + 页面表格外壳）

对齐测试项目的“管理页表格”体验：

- 表头背景：`--table-header-bg`
- 行 hover：背景浅紫 `--nav-hover-bg`
- 操作列：统一按钮尺寸（sm）与间距
- 状态标签：使用 badge（圆角 9999 或 md），颜色来自 tokens

建议：在各模块页面中统一表格上方的“搜索筛选区”结构：

- 左侧：搜索框
- 中间：筛选下拉（可选）
- 右侧：新增按钮/刷新按钮

（参考测试项目 admin 页面结构）

---

## 6. 页面级规范（o-f 各模块统一模板）

### 6.1 页面头（PageHeader）统一模板

建议每个模块页面上方结构一致：

- 标题（h1）
- 描述（p）
- 右侧操作区：主按钮（新增）+ 次按钮（刷新）

参考现有：`o-f/src/modules/user/UserModule.vue` 里的 header 区。

### 6.2 登录页（Login）

参考测试项目“居中卡片 + 干净背景”的思路：

- 居中卡片
- 明确标题 + 轻说明
- 错误消息就地展示 + toast

落地点：`o-f/src/modules/auth/Login.vue`

---

## 7. 交互与动效规范

### 7.1 统一动效节奏

- 快：`--transition-fast: 0.15s ease`
- 常规：`--transition-base: 0.3s ease`
- 慢：`--transition-slow: 0.5s ease`

规则：
- hover 只做轻微位移或阴影变化
- active 只做轻微缩放（0.98）
- 页面切换用淡入/滑动，不要夸张

### 7.2 反馈策略

- 成功：toast（success）
- 失败：toast（error）+ 表单内错误文案
- 危险操作：二次确认（Modal/confirm）

---

## 8. 响应式与可访问性

### 8.1 断点建议

- `<= 768px`：侧边栏折叠或隐藏；内容区 padding 降为 16px
- 表格：支持横向滚动，避免强行压缩到不可读

### 8.2 可访问性

- 对比度：关键文本 ≥ 4.5:1
- 触摸目标：移动端 ≥ 44px
- 键盘：可聚焦元素有可见 focus 样式

---

## 9. 实施清单（按优先级）

### P0（必须完成，统一骨架）

- 统一所有模块页面的 PageHeader 模板
- 统一“搜索筛选区 + 表格 + 操作列按钮”布局
- 统一状态类 badge（role/status/health/priority）

### P1（增强体验）

- 侧边栏升级为深色渐变（如选择方案 B）
- 表格行 hover、表头背景、空状态插画/文案
- Modal/Toast 动效与层级统一

### P2（加分项）

- Dashboard 的卡片栅格在移动端更自然
- 表格列排序/筛选 UI 统一
- Loading 覆盖策略更细粒度（局部 loading 优先）

---

## 10. 验收标准（可检查）

- 任意模块页面：标题/描述/操作区位置一致
- 任意表格页面：搜索筛选区结构一致，按钮风格一致
- 任意状态展示：badge 颜色与含义一致
- hover/active 动效：幅度统一，不突兀
- 移动端：无明显溢出/遮挡，表格可滚动
