/**
 * 后台管理页面 - 智慧养老系统
 * 包含10个管理子模块页面（左侧导航栏布局）
 */

// 加载管理页面样式
function loadAdminStyles() {
  if (!document.querySelector('link[href="styles/admin.css"]')) {
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = 'styles/admin.css';
    document.head.appendChild(link);
  }
}

// 当前激活的菜单
let currentAdminPage = 'admin-user-list';

// 生成左侧导航栏HTML
function renderAdminSidebar(activePage) {
  currentAdminPage = activePage;
  return `
    <div class="admin-sidebar">
      <div class="admin-sidebar-header">
        <div class="admin-sidebar-logo">🏠</div>
        <div>
          <div class="admin-sidebar-title">智慧养老</div>
          <div class="admin-sidebar-subtitle">后台管理系统</div>
        </div>
      </div>
      <nav class="admin-nav">
        <div class="admin-nav-group">
          <div class="admin-nav-group-title">👥 用户管理</div>
          <div class="admin-nav-item ${activePage === 'admin-user-list' ? 'active' : ''}" onclick="router.navigate('/admin-user-list')">
            <span class="icon">📋</span>
            <span class="text">用户列表</span>
          </div>
          <div class="admin-nav-item ${activePage === 'admin-role-manage' ? 'active' : ''}" onclick="router.navigate('/admin-role-manage')">
            <span class="icon">🎭</span>
            <span class="text">角色管理</span>
          </div>
          <div class="admin-nav-item ${activePage === 'admin-permission' ? 'active' : ''}" onclick="router.navigate('/admin-permission')">
            <span class="icon">🔐</span>
            <span class="text">权限设置</span>
          </div>
        </div>
        <div class="admin-nav-group">
          <div class="admin-nav-group-title">👴 老人管理</div>
          <div class="admin-nav-item ${activePage === 'admin-elder-list' ? 'active' : ''}" onclick="router.navigate('/admin-elder-list')">
            <span class="icon">📁</span>
            <span class="text">老人档案</span>
          </div>
          <div class="admin-nav-item ${activePage === 'admin-health-record' ? 'active' : ''}" onclick="router.navigate('/admin-health-record')">
            <span class="icon">💊</span>
            <span class="text">健康记录</span>
          </div>
          <div class="admin-nav-item ${activePage === 'admin-family-bindng' ? 'active' : ''}" onclick="router.navigate('/admin-family-bindng')">
            <span class="icon">👨‍👩‍👧</span>
            <span class="text">家属绑定</span>
          </div>
        </div>
        <div class="admin-nav-group">
          <div class="admin-nav-group-title">🛎️ 服务管理</div>
          <div class="admin-nav-item ${activePage === 'admin-service-list' ? 'active' : ''}" onclick="router.navigate('/admin-service-list')">
            <span class="icon">📦</span>
            <span class="text">服务项目</span>
          </div>
          <div class="admin-nav-item ${activePage === 'admin-service-order' ? 'active' : ''}" onclick="router.navigate('/admin-service-order')">
            <span class="icon">📝</span>
            <span class="text">服务预约</span>
          </div>
        </div>
        <div class="admin-nav-group">
          <div class="admin-nav-group-title">🎉 活动管理</div>
          <div class="admin-nav-item ${activePage === 'admin-activity-list' ? 'active' : ''}" onclick="router.navigate('/admin-activity-list')">
            <span class="icon">🎯</span>
            <span class="text">活动列表</span>
          </div>
          <div class="admin-nav-item ${activePage === 'admin-enrollment' ? 'active' : ''}" onclick="router.navigate('/admin-enrollment')">
            <span class="icon">✅</span>
            <span class="text">报名管理</span>
          </div>
        </div>
      </nav>
      <button class="admin-back-btn" onclick="router.navigate('/profile')">
        ← 返回前台
      </button>
    </div>
  `;
}

// 生成顶部栏HTML
function renderAdminTopbar(title) {
  return `
    <div class="admin-topbar">
      <h1>${title}</h1>
      <div class="admin-topbar-actions">
        <span style="color: var(--text-secondary);">管理员</span>
      </div>
    </div>
  `;
}

// ==================== 用户管理模块 ====================

// 1. 用户列表页面
function renderAdminUserListPage() {
  loadAdminStyles();
  const html = `
    <div class="admin-layout">
      ${renderAdminSidebar('admin-user-list')}
      <div class="admin-main">
        ${renderAdminTopbar('用户列表')}
        <div class="admin-content">
          <div class="admin-toolbar">
            <div class="admin-search-row">
              <input type="text" class="admin-search-input" id="userSearch" placeholder="搜索用户名/手机号">
              <button class="admin-search-btn" onclick="handleAdminSearch('用户', document.getElementById('userSearch').value)">搜索</button>
            </div>
            <div class="admin-filter-row">
              <select class="admin-filter-select" onchange="handleStatusFilter('用户', this.value)">
                <option value="all">全部状态</option>
                <option value="active">正常</option>
                <option value="disabled">禁用</option>
              </select>
              <select class="admin-filter-select" onchange="handleCategoryFilter('角色', this.value)">
                <option value="all">全部角色</option>
                <option value="admin">管理员</option>
                <option value="operator">操作员</option>
                <option value="user">普通用户</option>
              </select>
              <button class="admin-add-btn" onclick="handleAdminAdd('user')">+ 添加用户</button>
            </div>
          </div>
          <div class="admin-table-container">
            <table class="admin-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>用户名</th>
                  <th>手机号</th>
                  <th>角色</th>
                  <th>状态</th>
                  <th>注册时间</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                ${adminUsersData.map(user => `
                  <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.phone}</td>
                    <td><span class="category-tag">${user.roleText}</span></td>
                    <td><span class="status-tag ${user.status}">${user.statusText}</span></td>
                    <td>${user.createTime}</td>
                    <td>
                      <div class="admin-actions">
                        <button class="admin-action-btn edit" onclick="handleAdminEdit('user', ${user.id}, '${user.username}')">编辑</button>
                        <button class="admin-action-btn toggle" onclick="handleAdminToggleStatus('user', ${user.id}, '${user.status}', '${user.username}')">${user.status === 'active' ? '禁用' : '启用'}</button>
                        <button class="admin-action-btn delete" onclick="handleAdminDelete('user', ${user.id}, '${user.username}')">删除</button>
                      </div>
                    </td>
                  </tr>
                `).join('')}
              </tbody>
            </table>
          </div>
          <div class="admin-pagination">
            <span>共 ${adminUsersData.length} 条记录</span>
            <span>第 1/1 页</span>
          </div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}

// 2. 角色管理页面
function renderAdminRoleManagePage() {
  loadAdminStyles();
  const html = `
    <div class="admin-layout">
      ${renderAdminSidebar('admin-role-manage')}
      <div class="admin-main">
        ${renderAdminTopbar('角色管理')}
        <div class="admin-content">
          <div class="admin-toolbar">
            <div class="admin-filter-row">
              <button class="admin-add-btn" onclick="handleAdminAdd('role')">+ 添加角色</button>
            </div>
          </div>
          <div class="admin-table-container">
            <table class="admin-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>角色名称</th>
                  <th>角色编码</th>
                  <th>用户数</th>
                  <th>权限范围</th>
                  <th>状态</th>
                  <th>创建时间</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                ${adminRolesData.map(role => `
                  <tr>
                    <td>${role.id}</td>
                    <td>${role.name}</td>
                    <td>${role.code}</td>
                    <td>${role.userCount}</td>
                    <td style="max-width:200px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" title="${role.permissions}">${role.permissions}</td>
                    <td><span class="status-tag ${role.status}">${role.statusText}</span></td>
                    <td>${role.createTime}</td>
                    <td>
                      <div class="admin-actions">
                        <button class="admin-action-btn view" onclick="handleConfigPermission(${role.id}, '${role.name}')">配置权限</button>
                        <button class="admin-action-btn edit" onclick="handleAdminEdit('role', ${role.id}, '${role.name}')">编辑</button>
                        <button class="admin-action-btn delete" onclick="handleAdminDelete('role', ${role.id}, '${role.name}')">删除</button>
                      </div>
                    </td>
                  </tr>
                `).join('')}
              </tbody>
            </table>
          </div>
          <div class="admin-pagination">
            <span>共 ${adminRolesData.length} 条记录</span>
          </div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}

// 3. 权限设置页面
function renderAdminPermissionPage() {
  loadAdminStyles();
  const html = `
    <div class="admin-layout">
      ${renderAdminSidebar('admin-permission')}
      <div class="admin-main">
        ${renderAdminTopbar('权限设置')}
        <div class="admin-content">
          <div class="admin-toolbar">
            <div class="admin-filter-row">
              <select class="admin-filter-select" onchange="showMessage('切换角色：' + this.options[this.selectedIndex].text)">
                <option value="admin">管理员</option>
                <option value="operator">操作员</option>
                <option value="user">普通用户</option>
              </select>
              <button class="admin-add-btn" onclick="showMessage('权限设置已保存')">保存设置</button>
            </div>
          </div>
          ${adminPermissionsData.map(module => `
            <div class="permission-module">
              <div class="permission-module-header">${module.module}</div>
              <div class="permission-list">
                ${module.permissions.map(perm => `
                  <div class="permission-item">
                    <span class="permission-name">${perm.name}</span>
                    <div class="toggle-switch ${perm.enabled ? 'active' : ''}" onclick="handleTogglePermission('${perm.id}', '${perm.name}', ${perm.enabled}); this.classList.toggle('active')"></div>
                  </div>
                `).join('')}
              </div>
            </div>
          `).join('')}
        </div>
      </div>
    </div>
  `;
  router.render(html);
}


// ==================== 老人管理模块 ====================

// 4. 老人档案页面
function renderAdminElderListPage() {
  loadAdminStyles();
  const html = `
    <div class="admin-layout">
      ${renderAdminSidebar('admin-elder-list')}
      <div class="admin-main">
        ${renderAdminTopbar('老人档案')}
        <div class="admin-content">
          <div class="admin-toolbar">
            <div class="admin-search-row">
              <input type="text" class="admin-search-input" id="elderSearch" placeholder="搜索姓名/电话">
              <button class="admin-search-btn" onclick="handleAdminSearch('老人', document.getElementById('elderSearch').value)">搜索</button>
            </div>
            <div class="admin-filter-row">
              <select class="admin-filter-select" onchange="handleStatusFilter('健康状态', this.value)">
                <option value="all">全部状态</option>
                <option value="normal">健康</option>
                <option value="warning">亚健康</option>
                <option value="danger">需关注</option>
              </select>
              <button class="admin-add-btn" onclick="handleAdminAdd('elder')">+ 添加档案</button>
            </div>
          </div>
          <div class="admin-table-container">
            <table class="admin-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>姓名</th>
                  <th>年龄</th>
                  <th>性别</th>
                  <th>联系电话</th>
                  <th>紧急联系人</th>
                  <th>健康状态</th>
                  <th>建档时间</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                ${adminEldersData.map(elder => `
                  <tr>
                    <td>${elder.id}</td>
                    <td>${elder.name}</td>
                    <td>${elder.age}</td>
                    <td>${elder.gender}</td>
                    <td>${elder.phone}</td>
                    <td>${elder.emergencyContact}</td>
                    <td><span class="status-tag ${elder.healthStatus}">${elder.healthText}</span></td>
                    <td>${elder.createTime}</td>
                    <td>
                      <div class="admin-actions">
                        <button class="admin-action-btn view" onclick="handleViewHealthFile(${elder.id}, '${elder.name}')">健康档案</button>
                        <button class="admin-action-btn edit" onclick="handleAdminEdit('elder', ${elder.id}, '${elder.name}')">编辑</button>
                        <button class="admin-action-btn delete" onclick="handleAdminDelete('elder', ${elder.id}, '${elder.name}')">删除</button>
                      </div>
                    </td>
                  </tr>
                `).join('')}
              </tbody>
            </table>
          </div>
          <div class="admin-pagination">
            <span>共 ${adminEldersData.length} 条记录</span>
            <span>第 1/1 页</span>
          </div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}

// 5. 健康记录页面
function renderAdminHealthRecordPage() {
  loadAdminStyles();
  const html = `
    <div class="admin-layout">
      ${renderAdminSidebar('admin-health-record')}
      <div class="admin-main">
        ${renderAdminTopbar('健康记录')}
        <div class="admin-content">
          <div class="admin-toolbar">
            <div class="admin-search-row">
              <input type="text" class="admin-search-input" id="healthSearch" placeholder="搜索老人姓名">
              <button class="admin-search-btn" onclick="handleAdminSearch('健康记录', document.getElementById('healthSearch').value)">搜索</button>
            </div>
            <div class="admin-filter-row">
              <select class="admin-filter-select" onchange="handleCategoryFilter('记录类型', this.value)">
                <option value="all">全部类型</option>
                <option value="blood-pressure">血压</option>
                <option value="heart-rate">心率</option>
                <option value="blood-sugar">血糖</option>
                <option value="temperature">体温</option>
              </select>
              <select class="admin-filter-select" onchange="handleStatusFilter('健康状态', this.value)">
                <option value="all">全部状态</option>
                <option value="normal">正常</option>
                <option value="warning">偏高/偏低</option>
                <option value="danger">异常</option>
              </select>
              <button class="admin-add-btn" onclick="handleAdminAdd('health-record')">+ 添加记录</button>
            </div>
          </div>
          <div class="admin-table-container">
            <table class="admin-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>老人姓名</th>
                  <th>记录类型</th>
                  <th>测量值</th>
                  <th>状态</th>
                  <th>记录时间</th>
                  <th>操作人</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                ${adminHealthRecordsData.map(record => `
                  <tr>
                    <td>${record.id}</td>
                    <td>${record.elderName}</td>
                    <td><span class="category-tag health">${record.recordType}</span></td>
                    <td>${record.value}</td>
                    <td><span class="status-tag ${record.status}">${record.statusText}</span></td>
                    <td>${record.recordTime}</td>
                    <td>${record.operator}</td>
                    <td>
                      <div class="admin-actions">
                        <button class="admin-action-btn view" onclick="handleAdminView('health-record', ${record.id}, '${record.elderName}的${record.recordType}记录')">详情</button>
                        <button class="admin-action-btn edit" onclick="handleAdminEdit('health-record', ${record.id}, '${record.elderName}的记录')">编辑</button>
                        <button class="admin-action-btn delete" onclick="handleAdminDelete('health-record', ${record.id}, '该记录')">删除</button>
                      </div>
                    </td>
                  </tr>
                `).join('')}
              </tbody>
            </table>
          </div>
          <div class="admin-pagination">
            <span>共 ${adminHealthRecordsData.length} 条记录</span>
            <span>第 1/1 页</span>
          </div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}

// 6. 家属绑定页面
function renderAdminFamilyBindingPage() {
  loadAdminStyles();
  const html = `
    <div class="admin-layout">
      ${renderAdminSidebar('admin-family-bindng')}
      <div class="admin-main">
        ${renderAdminTopbar('家属绑定')}
        <div class="admin-content">
          <div class="admin-toolbar">
            <div class="admin-search-row">
              <input type="text" class="admin-search-input" id="familySearch" placeholder="搜索老人/家属姓名">
              <button class="admin-search-btn" onclick="handleAdminSearch('家属绑定', document.getElementById('familySearch').value)">搜索</button>
            </div>
            <div class="admin-filter-row">
              <select class="admin-filter-select" onchange="handleStatusFilter('绑定状态', this.value)">
                <option value="all">全部状态</option>
                <option value="bindng">已绑定</option>
                <option value="pending">待确认</option>
              </select>
              <button class="admin-add-btn" onclick="handleAdminAdd('family')">+ 添加绑定</button>
            </div>
          </div>
          <div class="admin-table-container">
            <table class="admin-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>老人姓名</th>
                  <th>家属姓名</th>
                  <th>关系</th>
                  <th>家属电话</th>
                  <th>绑定时间</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                ${adminFamilyBindingsData.map(binding => `
                  <tr>
                    <td>${binding.id}</td>
                    <td>${binding.elderName}</td>
                    <td>${binding.familyName}</td>
                    <td>${binding.relation}</td>
                    <td>${binding.phone}</td>
                    <td>${binding.bindTime}</td>
                    <td><span class="status-tag ${binding.status}">${binding.statusText}</span></td>
                    <td>
                      <div class="admin-actions">
                        ${binding.status === 'pending' ? `<button class="admin-action-btn view" onclick="handleSendBindInvite(${binding.id}, '${binding.elderName}')">发送邀请</button>` : ''}
                        <button class="admin-action-btn edit" onclick="handleAdminEdit('family', ${binding.id}, '${binding.elderName}与${binding.familyName}的绑定')">编辑</button>
                        <button class="admin-action-btn delete" onclick="handleUnbind(${binding.id}, '${binding.elderName}', '${binding.familyName}')">解除绑定</button>
                      </div>
                    </td>
                  </tr>
                `).join('')}
              </tbody>
            </table>
          </div>
          <div class="admin-pagination">
            <span>共 ${adminFamilyBindingsData.length} 条记录</span>
            <span>第 1/1 页</span>
          </div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}


// ==================== 服务管理模块 ====================

// 7. 服务项目页面
function renderAdminServiceListPage() {
  loadAdminStyles();
  const html = `
    <div class="admin-layout">
      ${renderAdminSidebar('admin-service-list')}
      <div class="admin-main">
        ${renderAdminTopbar('服务项目')}
        <div class="admin-content">
          <div class="admin-toolbar">
            <div class="admin-search-row">
              <input type="text" class="admin-search-input" id="serviceSearch" placeholder="搜索服务名称">
              <button class="admin-search-btn" onclick="handleAdminSearch('服务', document.getElementById('serviceSearch').value)">搜索</button>
            </div>
            <div class="admin-filter-row">
              <select class="admin-filter-select" onchange="handleCategoryFilter('服务类型', this.value)">
                <option value="all">全部类型</option>
                <option value="life">生活照料</option>
                <option value="medical">医疗护理</option>
                <option value="mental">精神慰藉</option>
                <option value="other">其他服务</option>
              </select>
              <select class="admin-filter-select" onchange="handleStatusFilter('服务状态', this.value)">
                <option value="all">全部状态</option>
                <option value="online">上架</option>
                <option value="offline">下架</option>
              </select>
              <button class="admin-add-btn" onclick="handleAdminAdd('service')">+ 添加服务</button>
            </div>
          </div>
          <div class="admin-table-container">
            <table class="admin-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>服务名称</th>
                  <th>服务类型</th>
                  <th>价格</th>
                  <th>状态</th>
                  <th>服务描述</th>
                  <th>创建时间</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                ${adminServicesData.map(service => `
                  <tr>
                    <td>${service.id}</td>
                    <td>${service.name}</td>
                    <td><span class="category-tag ${service.category}">${service.categoryText}</span></td>
                    <td>${service.price}</td>
                    <td><span class="status-tag ${service.status}">${service.statusText}</span></td>
                    <td style="max-width:150px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" title="${service.description}">${service.description}</td>
                    <td>${service.createTime}</td>
                    <td>
                      <div class="admin-actions">
                        <button class="admin-action-btn edit" onclick="handleAdminEdit('service', ${service.id}, '${service.name}')">编辑</button>
                        <button class="admin-action-btn toggle" onclick="handleToggleService(${service.id}, '${service.name}', '${service.status}')">${service.status === 'online' ? '下架' : '上架'}</button>
                        <button class="admin-action-btn delete" onclick="handleAdminDelete('service', ${service.id}, '${service.name}')">删除</button>
                      </div>
                    </td>
                  </tr>
                `).join('')}
              </tbody>
            </table>
          </div>
          <div class="admin-pagination">
            <span>共 ${adminServicesData.length} 条记录</span>
            <span>第 1/1 页</span>
          </div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}

// 8. 服务预约页面
function renderAdminServiceOrderPage() {
  loadAdminStyles();
  const html = `
    <div class="admin-layout">
      ${renderAdminSidebar('admin-service-order')}
      <div class="admin-main">
        ${renderAdminTopbar('服务预约')}
        <div class="admin-content">
          <div class="admin-toolbar">
            <div class="admin-search-row">
              <input type="text" class="admin-search-input" id="orderSearch" placeholder="搜索预约单号/老人姓名">
              <button class="admin-search-btn" onclick="handleAdminSearch('预约', document.getElementById('orderSearch').value)">搜索</button>
            </div>
            <div class="admin-filter-row">
              <select class="admin-filter-select" onchange="handleStatusFilter('预约状态', this.value)">
                <option value="all">全部状态</option>
                <option value="pending">待服务</option>
                <option value="confirmed">已确认</option>
                <option value="completed">已完成</option>
                <option value="cancelled">已取消</option>
              </select>
              <button class="admin-add-btn" onclick="handleAdminAdd('service-order')">+ 新建预约</button>
            </div>
          </div>
          <div class="admin-table-container">
            <table class="admin-table">
              <thead>
                <tr>
                  <th>预约单号</th>
                  <th>老人姓名</th>
                  <th>服务项目</th>
                  <th>预约时间</th>
                  <th>服务时间</th>
                  <th>状态</th>
                  <th>服务人员</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                ${adminServiceOrdersData.map(order => `
                  <tr>
                    <td>${order.orderNo}</td>
                    <td>${order.elderName}</td>
                    <td>${order.serviceName}</td>
                    <td>${order.orderTime}</td>
                    <td>${order.serviceTime}</td>
                    <td><span class="status-tag ${order.status}">${order.statusText}</span></td>
                    <td>${order.operator}</td>
                    <td>
                      <div class="admin-actions">
                        ${order.status === 'pending' ? `
                          <button class="admin-action-btn view" onclick="handleConfirmOrder(${order.id}, '${order.orderNo}')">确认</button>
                          <button class="admin-action-btn delete" onclick="handleCancelOrder(${order.id}, '${order.orderNo}')">取消</button>
                        ` : ''}
                        ${order.status === 'confirmed' ? `
                          <button class="admin-action-btn view" onclick="handleCompleteOrder(${order.id}, '${order.orderNo}')">完成</button>
                          <button class="admin-action-btn delete" onclick="handleCancelOrder(${order.id}, '${order.orderNo}')">取消</button>
                        ` : ''}
                        <button class="admin-action-btn edit" onclick="handleAdminView('service-order', ${order.id}, '预约单${order.orderNo}')">详情</button>
                      </div>
                    </td>
                  </tr>
                `).join('')}
              </tbody>
            </table>
          </div>
          <div class="admin-pagination">
            <span>共 ${adminServiceOrdersData.length} 条记录</span>
            <span>第 1/1 页</span>
          </div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}

// ==================== 活动管理模块 ====================

// 9. 活动列表页面
function renderAdminActivityListPage() {
  loadAdminStyles();
  const html = `
    <div class="admin-layout">
      ${renderAdminSidebar('admin-activity-list')}
      <div class="admin-main">
        ${renderAdminTopbar('活动列表')}
        <div class="admin-content">
          <div class="admin-toolbar">
            <div class="admin-search-row">
              <input type="text" class="admin-search-input" id="activitySearch" placeholder="搜索活动名称">
              <button class="admin-search-btn" onclick="handleAdminSearch('活动', document.getElementById('activitySearch').value)">搜索</button>
            </div>
            <div class="admin-filter-row">
              <select class="admin-filter-select" onchange="handleCategoryFilter('活动类型', this.value)">
                <option value="all">全部类型</option>
                <option value="health">健康</option>
                <option value="culture">文娱</option>
                <option value="learning">学习</option>
                <option value="travel">旅游</option>
              </select>
              <select class="admin-filter-select" onchange="handleStatusFilter('活动状态', this.value)">
                <option value="all">全部状态</option>
                <option value="pending">未开始</option>
                <option value="ongoing">进行中</option>
                <option value="ended">已结束</option>
                <option value="cancelled">已取消</option>
              </select>
              <button class="admin-add-btn" onclick="handleAdminAdd('activity')">+ 添加活动</button>
            </div>
          </div>
          <div class="admin-table-container">
            <table class="admin-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>活动名称</th>
                  <th>类型</th>
                  <th>活动时间</th>
                  <th>地点</th>
                  <th>报名人数</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                ${adminActivitiesManageData.map(activity => `
                  <tr>
                    <td>${activity.id}</td>
                    <td>${activity.name}</td>
                    <td><span class="category-tag ${activity.category}">${activity.categoryText}</span></td>
                    <td>${activity.time}</td>
                    <td>${activity.location}</td>
                    <td>${activity.enrolled}/${activity.capacity}</td>
                    <td><span class="status-tag ${activity.status}">${activity.statusText}</span></td>
                    <td>
                      <div class="admin-actions">
                        <button class="admin-action-btn view" onclick="handleViewEnrollments(${activity.id}, '${activity.name}')">报名列表</button>
                        <button class="admin-action-btn edit" onclick="handleAdminEdit('activity', ${activity.id}, '${activity.name}')">编辑</button>
                        ${activity.status === 'pending' ? `<button class="admin-action-btn delete" onclick="handleCancelActivity(${activity.id}, '${activity.name}')">取消</button>` : ''}
                      </div>
                    </td>
                  </tr>
                `).join('')}
              </tbody>
            </table>
          </div>
          <div class="admin-pagination">
            <span>共 ${adminActivitiesManageData.length} 条记录</span>
            <span>第 1/1 页</span>
          </div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}

// 10. 报名管理页面
function renderAdminEnrollmentPage() {
  loadAdminStyles();
  const html = `
    <div class="admin-layout">
      ${renderAdminSidebar('admin-enrollment')}
      <div class="admin-main">
        ${renderAdminTopbar('报名管理')}
        <div class="admin-content">
          <div class="admin-toolbar">
            <div class="admin-search-row">
              <input type="text" class="admin-search-input" id="enrollSearch" placeholder="搜索活动/老人姓名">
              <button class="admin-search-btn" onclick="handleAdminSearch('报名', document.getElementById('enrollSearch').value)">搜索</button>
            </div>
            <div class="admin-filter-row">
              <select class="admin-filter-select" onchange="handleStatusFilter('报名状态', this.value)">
                <option value="all">全部状态</option>
                <option value="pending">待确认</option>
                <option value="confirmed">已确认</option>
                <option value="attended">已签到</option>
                <option value="absent">未参加</option>
              </select>
              <button class="admin-add-btn" onclick="handleAdminAdd('enrollment')">+ 添加报名</button>
            </div>
          </div>
          <div class="admin-table-container">
            <table class="admin-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>活动名称</th>
                  <th>老人姓名</th>
                  <th>联系电话</th>
                  <th>报名时间</th>
                  <th>状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                ${adminEnrollmentsData.map(enrollment => `
                  <tr>
                    <td>${enrollment.id}</td>
                    <td>${enrollment.activityName}</td>
                    <td>${enrollment.elderName}</td>
                    <td>${enrollment.phone}</td>
                    <td>${enrollment.enrollTime}</td>
                    <td><span class="status-tag ${enrollment.status}">${enrollment.statusText}</span></td>
                    <td>
                      <div class="admin-actions">
                        ${enrollment.status === 'pending' ? `
                          <button class="admin-action-btn view" onclick="handleConfirmEnrollment(${enrollment.id}, '${enrollment.elderName}', '${enrollment.activityName}')">确认</button>
                        ` : ''}
                        ${enrollment.status === 'confirmed' ? `
                          <button class="admin-action-btn view" onclick="handleCheckIn(${enrollment.id}, '${enrollment.elderName}', '${enrollment.activityName}')">签到</button>
                        ` : ''}
                        ${enrollment.status !== 'attended' && enrollment.status !== 'absent' ? `
                          <button class="admin-action-btn delete" onclick="handleCancelEnrollment(${enrollment.id}, '${enrollment.elderName}', '${enrollment.activityName}')">取消</button>
                        ` : ''}
                      </div>
                    </td>
                  </tr>
                `).join('')}
              </tbody>
            </table>
          </div>
          <div class="admin-pagination">
            <span>共 ${adminEnrollmentsData.length} 条记录</span>
            <span>第 1/1 页</span>
          </div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}

// ==================== 注册管理页面路由 ====================
function registerAdminRoutes() {
  // 用户管理模块
  router.register('/admin-user-list', () => { router.toggleBottomNav(false); renderAdminUserListPage(); });
  router.register('/admin-role-manage', () => { router.toggleBottomNav(false); renderAdminRoleManagePage(); });
  router.register('/admin-permission', () => { router.toggleBottomNav(false); renderAdminPermissionPage(); });
  
  // 老人管理模块
  router.register('/admin-elder-list', () => { router.toggleBottomNav(false); renderAdminElderListPage(); });
  router.register('/admin-health-record', () => { router.toggleBottomNav(false); renderAdminHealthRecordPage(); });
  router.register('/admin-family-bindng', () => { router.toggleBottomNav(false); renderAdminFamilyBindingPage(); });
  
  // 服务管理模块
  router.register('/admin-service-list', () => { router.toggleBottomNav(false); renderAdminServiceListPage(); });
  router.register('/admin-service-order', () => { router.toggleBottomNav(false); renderAdminServiceOrderPage(); });
  
  // 活动管理模块
  router.register('/admin-activity-list', () => { router.toggleBottomNav(false); renderAdminActivityListPage(); });
  router.register('/admin-enrollment', () => { router.toggleBottomNav(false); renderAdminEnrollmentPage(); });
}

// 页面加载时注册路由
if (typeof router !== 'undefined') {
  registerAdminRoutes();
}
