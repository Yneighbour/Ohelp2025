// 后台菜单配置：仅用于信息架构与导航差异化（不做权限控制）

export const adminMenu = [
  {
    title: '概览',
    items: [
      { label: '系统概览', to: '/admin/dashboard' }
    ]
  },
  {
    title: '系统管理',
    items: [
      { label: '认证管理', to: '/auth' },
      { label: '用户管理', to: '/user' },
      { label: '老人信息', to: '/elder' },
      { label: '工作人员', to: '/worker' },
      { label: '文件管理', to: '/file' }
    ]
  },
  {
    title: '业务管理',
    items: [
      { label: '紧急求助', to: '/emergency' },
      { label: '服务订单', to: '/serviceorder' },
      { label: '健康管理', to: '/health' },
      { label: '活动管理', to: '/activity' }
    ]
  }
]

export const workerMenu = [
  {
    title: '工作台',
    items: [
      { label: '任务中心', to: '/worker/tasks' },
      { label: '处理记录', to: '/worker/records' }
    ]
  },
  {
    title: '业务入口',
    items: [
      { label: '紧急求助', to: '/emergency' },
      { label: '服务订单', to: '/serviceorder' }
    ]
  }
]

export function getMenuByRole(role) {
  if (role === 'ADMIN') return adminMenu
  if (role === 'WORKER') return workerMenu
  return []
}
