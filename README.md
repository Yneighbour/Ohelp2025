# Ohelp2025
基于Spring Boot的智慧养老服务管理系统的设计与实现

## 后端功能模块

### 1. 用户管理模块 (`UserController`)

- 用户登录、注册、登出
- 密码重置功能
- 用户信息管理（查询、修改、删除）
- 会话管理

### 2. 老人管理模块 (`LaorenController`)

- 老人账户管理（注册、登录）
- 老人基本信息管理（姓名、性别、年龄、照片、联系方式、地址等）
- 密码重置功能

### 3. 服务管理模块

- **服务类型管理** (`FuwuleixingController`)：管理服务分类护工
- **服务项目管理** (`FuwuxiangmuController`)：管理具体服务项目（名称、类型、价格、数量、详情等）
- **服务购买管理** (`FuwugoumaiController`)：处理服务购买订单

### 4. 活动管理模块

- **活动分类管理** (`HuodongfenleiController`)：管理活动分类
- **活动信息管理** (`HuodongxinxiController`)：管理活动详情（名称、类别、地点、时间、费用、详情等）

### 5. 健康与医疗模块

- **每日健康** (`MeirijiankangController`)：管理健康资讯内容
- **既往病史** (`JiwangbingshiController`)：记录老人病史信息
- **积分增加** (`JifenzengjiaController`)：管理老人积分变动

### 6. 社交与紧急模块

- **亲人管理** (`QinshuController`)：管理老人亲属联系信息
- **老工管理** (`LaogongController`)：管理工作人员信息
- **紧急求助** (`JinjiqiuzhuController`)：处理老人紧急求助信息



### 7. 护工管理模块

- **创建照料计划** (`ZhaoliaoController`) ：(每日送餐，洗浴)
- **服务评价** (`PingjiaController`)：老人或家属对服务的评价

### 8. 护工管理模块

- 家属探望登记(TanwangController)

