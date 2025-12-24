# OHelp 前端（多页面）

这是一个基于 Vite + Vue 3 的多页面（MPA）示例脚手架，供智慧养老服务管理系统前端使用。

快速开始

```bash
cd o-f
npm install
npm run dev
```

构建

```bash
npm run build
npm run serve
```

结构说明

- `index.html` - 主页入口
- `dashboard.html` - 仪表盘入口
- `elderly.html` - 老人档案入口
- `requests.html` - 服务请求入口
- `src/pages` - 各页面的 Vue 组件
- `src/components` - 公共组件（头部、底部）
- `vite.config.js` - 多页面入口配置

后续建议

- 将 UI 框架（如 Element Plus / Ant Design Vue）集成
- 添加统一 API 封装与环境变量
- 根据后端 Spring Boot 接口补全页面功能
