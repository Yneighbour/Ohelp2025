/**
 * 路由系统 - 智慧养老系统
 */

class Router {
  constructor() {
    this.routes = {};
    this.currentRoute = null;
    this.init();
  }

  init() {
    window.addEventListener('hashchange', () => this.handleRouteChange());
    window.addEventListener('load', () => this.handleRouteChange());
  }

  register(path, handler) {
    this.routes[path] = handler;
  }

  handleRouteChange() {
    const hash = window.location.hash.slice(1) || '/login';
    const { path, params, pathParts } = this.parseRoute(hash);
    params.pathParts = pathParts;
    this.currentRoute = { path, params };
    const handler = this.findRoute(path);
    if (handler) {
      handler(params);
    } else {
      this.navigate('/login');
    }
  }

  parseRoute(hash) {
    const [path, queryString] = hash.split('?');
    const params = {};
    const pathParts = path.split('/').filter(Boolean);
    if (queryString) {
      queryString.split('&').forEach(param => {
        const [key, value] = param.split('=');
        params[key] = decodeURIComponent(value);
      });
    }
    return { path, params, pathParts };
  }

  findRoute(path) {
    if (this.routes[path]) return this.routes[path];
    for (const routePath in this.routes) {
      const regex = this.pathToRegex(routePath);
      if (regex.test(path)) return this.routes[routePath];
    }
    return null;
  }

  pathToRegex(path) {
    const pattern = path.replace(/:\w+/g, '([^/]+)');
    return new RegExp(`^${pattern}$`);
  }

  navigate(path) {
    window.location.hash = `#${path}`;
  }

  back() { window.history.back(); }

  toggleBottomNav(show) {
    const nav = document.getElementById('bottom-nav');
    if (nav) nav.style.display = show ? 'flex' : 'none';
  }

  updateBottomNavActive(page) {
    document.querySelectorAll('.bottom-nav .nav-item').forEach(item => {
      item.classList.toggle('active', item.dataset.page === page);
    });
  }

  render(html) {
    const app = document.getElementById('app');
    if (app) app.innerHTML = html;
  }
}

const router = new Router();

// 注册路由
router.register('/login', () => { router.toggleBottomNav(false); renderLoginPage(); });
router.register('/health', () => { router.toggleBottomNav(true); router.updateBottomNavActive('health'); renderHealthPage(); });
router.register('/health/detail/:type', (p) => { router.toggleBottomNav(false); renderHealthDetailPage(p.pathParts[2]); });
router.register('/alerts', () => { router.toggleBottomNav(false); renderAlertsPage(); });
router.register('/alerts/:id', (p) => { router.toggleBottomNav(false); renderAlertDetailPage(p.pathParts[1]); });
router.register('/emergency', () => { router.toggleBottomNav(true); router.updateBottomNavActive('emergency'); renderEmergencyPage(); });
router.register('/social', () => { router.toggleBottomNav(true); router.updateBottomNavActive('social'); renderSocialPage(); });
router.register('/activities', () => { router.toggleBottomNav(true); router.updateBottomNavActive('social'); renderActivitiesPage(); });
router.register('/activities/:id', (p) => { router.toggleBottomNav(false); renderActivityDetailPage(p.pathParts[1]); });
router.register('/profile', () => { router.toggleBottomNav(true); router.updateBottomNavActive('profile'); renderProfilePage(); });
router.register('/profile/info', () => { router.toggleBottomNav(false); renderProfileInfoPage(); });
router.register('/profile/health-records', () => { router.toggleBottomNav(false); renderHealthRecordsPage(); });
router.register('/profile/my-activities', () => { router.toggleBottomNav(false); renderMyActivitiesPage(); });
router.register('/profile/contacts', () => { router.toggleBottomNav(false); renderEmergencyContactsPage(); });
router.register('/profile/settings', () => { router.toggleBottomNav(false); renderSettingsPage(); });


// ========== 登录页面 ==========
function renderLoginPage() {
  if (!document.querySelector('link[href="styles/login.css"]')) {
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = 'styles/login.css';
    document.head.appendChild(link);
  }
  
  const html = `
    <div class="login-page">
      <div class="login-container">
        <div class="login-logo">智</div>
        <h1 class="login-title">智慧养老系统</h1>
        <form class="login-form" onsubmit="handleLogin(event)">
          <div class="form-group">
            <label class="form-label" for="phone">手机号</label>
            <input type="tel" id="phone" class="form-input" placeholder="请输入手机号" maxlength="11" required>
          </div>
          <div class="form-group">
            <label class="form-label" for="password">密码</label>
            <input type="password" id="password" class="form-input" placeholder="请输入密码" required>
          </div>
          <div class="form-group">
            <label class="form-label">登录身份</label>
            <div class="role-selector">
              <label class="role-option">
                <input type="radio" name="role" value="user" checked>
                <span class="role-card">
                  <span class="role-icon">👴</span>
                  <span class="role-name">用户端</span>
                  <span class="role-desc">老人/家属使用</span>
                </span>
              </label>
              <label class="role-option">
                <input type="radio" name="role" value="admin">
                <span class="role-card">
                  <span class="role-icon">👨‍💼</span>
                  <span class="role-name">管理端</span>
                  <span class="role-desc">管理员使用</span>
                </span>
              </label>
            </div>
          </div>
          <div class="form-options">
            <div class="checkbox-group">
              <input type="checkbox" id="remember" name="remember">
              <label for="remember">记住密码</label>
            </div>
            <a class="forgot-link" onclick="showMessage('演示版本，该功能暂不可用')">忘记密码？</a>
          </div>
          <button type="submit" class="login-btn">登录</button>
          <div class="register-link">还没有账号？<a onclick="showMessage('演示版本，无需注册')">立即注册</a></div>
        </form>
      </div>
    </div>
  `;
  router.render(html);
}

function handleLogin(event) {
  event.preventDefault();
  const phone = document.getElementById('phone').value;
  const password = document.getElementById('password').value;
  const role = document.querySelector('input[name="role"]:checked').value;
  
  if (!phone || !password) {
    showMessage('请输入手机号和密码');
    return;
  }
  
  showMessage('登录成功！');
  
  // 根据角色跳转不同页面
  if (role === 'admin') {
    setTimeout(() => router.navigate('/admin-user-list'), 500);
  } else {
    setTimeout(() => router.navigate('/health'), 500);
  }
}


// ========== 健康页面 ==========
function renderHealthPage() {
  if (!document.querySelector('link[href="styles/health.css"]')) {
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = 'styles/health.css';
    document.head.appendChild(link);
  }
  
  const unreadAlerts = alertsData.filter(a => !a.isRead).length;
  
  const html = `
    <div class="health-page">
      <div class="health-header">
        <div class="user-info">
          <div class="user-avatar">${userData.nickname.charAt(0)}</div>
          <div class="user-details">
            <div class="user-name">${userData.nickname}</div>
            <div class="user-greeting">今天身体状况良好</div>
          </div>
          <button class="alert-btn" onclick="router.navigate('/alerts')">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"></path>
              <path d="M13.73 21a2 2 0 0 1-3.46 0"></path>
            </svg>
            ${unreadAlerts > 0 ? '<span class="alert-badge">' + unreadAlerts + '</span>' : ''}
          </button>
        </div>
        <div class="health-score-card">
          <div class="score-label">今日健康评分</div>
          <div class="score-value">85<span class="score-unit">/100</span></div>
          <div class="score-bar"><div class="score-progress" style="width: 85%"></div></div>
          <div class="score-tip">继续保持，您的健康状况良好！</div>
        </div>
      </div>
      <div class="quick-actions">
        <button class="action-item" onclick="showMessage('演示版本，该功能暂不可用')">
          <div class="action-icon">📊</div><div class="action-text">测量数据</div>
        </button>
        <button class="action-item" onclick="router.navigate('/activities')">
          <div class="action-icon">🏃</div><div class="action-text">运动打卡</div>
        </button>
        <button class="action-item" onclick="showMessage('演示版本，该功能暂不可用')">
          <div class="action-icon">💊</div><div class="action-text">用药提醒</div>
        </button>
        <button class="action-item" onclick="showMessage('演示版本，该功能暂不可用')">
          <div class="action-icon">📋</div><div class="action-text">健康报告</div>
        </button>
      </div>
      <div class="health-section">
        <div class="section-header">
          <h2 class="section-title">健康指标</h2>
          <a class="section-more" onclick="showMessage('查看更多健康指标')">更多 →</a>
        </div>
        <div class="health-cards">
          ${renderHealthCard(healthData['blood-pressure'])}
          ${renderHealthCard(healthData['heart-rate'])}
          ${renderHealthCard(healthData['blood-sugar'])}
          ${renderHealthCard(healthData['temperature'])}
        </div>
      </div>
      <div class="health-section">
        <div class="section-header">
          <h2 class="section-title">每日任务</h2>
          <span class="task-progress">3/5 已完成</span>
        </div>
        <div class="daily-tasks">
          <div class="task-item completed"><div class="task-checkbox">✓</div><div class="task-content"><div class="task-name">早晨测量血压</div><div class="task-time">已完成 · 08:30</div></div></div>
          <div class="task-item completed"><div class="task-checkbox">✓</div><div class="task-content"><div class="task-name">饮水 8 杯</div><div class="task-time">已完成 6/8 杯</div></div></div>
          <div class="task-item completed"><div class="task-checkbox">✓</div><div class="task-content"><div class="task-name">步行 30 分钟</div><div class="task-time">已完成 · 10:15</div></div></div>
          <div class="task-item"><div class="task-checkbox"></div><div class="task-content"><div class="task-name">午餐后服药</div><div class="task-time">12:30 提醒</div></div></div>
          <div class="task-item"><div class="task-checkbox"></div><div class="task-content"><div class="task-name">晚间测量血糖</div><div class="task-time">19:00 提醒</div></div></div>
        </div>
      </div>
      <div class="health-section">
        <div class="section-header"><h2 class="section-title">健康建议</h2></div>
        <div class="health-tips">
          <div class="tip-card"><div class="tip-icon">🥗</div><div class="tip-content"><div class="tip-title">饮食建议</div><div class="tip-text">建议多吃新鲜蔬菜水果，少油少盐</div></div></div>
          <div class="tip-card"><div class="tip-icon">💤</div><div class="tip-content"><div class="tip-title">睡眠建议</div><div class="tip-text">保持规律作息，每天睡眠7-8小时</div></div></div>
          <div class="tip-card"><div class="tip-icon">🧘</div><div class="tip-content"><div class="tip-title">运动建议</div><div class="tip-text">每天坚持30分钟有氧运动</div></div></div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}

function renderHealthCard(data) {
  const icons = { 'blood-pressure': '💓', 'heart-rate': '❤️', 'blood-sugar': '🩸', 'temperature': '🌡️' };
  return `
    <div class="health-card" onclick="router.navigate('/health/detail/${data.type}')">
      <div class="card-header">
        <div class="card-icon ${data.type}">${icons[data.type]}</div>
        <span class="card-status ${data.status}">${data.statusText}</span>
      </div>
      <div class="card-title">${data.name}</div>
      <div class="card-value">${data.currentValue}<span class="card-unit">${data.unit}</span></div>
      <div class="card-time">${data.measureTime}</div>
    </div>
  `;
}


// ========== 健康详情页面 ==========
function renderHealthDetailPage(type) {
  if (!document.querySelector('link[href="styles/health-detail.css"]')) {
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = 'styles/health-detail.css';
    document.head.appendChild(link);
  }
  
  const data = healthData[type];
  if (!data) { router.navigate('/health'); return; }
  
  const html = `
    <div class="health-detail-page">
      <div class="top-bar">
        <button class="back-btn" onclick="router.back()"><svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7"/></svg></button>
        <h1 class="title">${data.name}详情</h1>
        <div class="right-action"></div>
      </div>
      <div class="detail-content">
        <div class="current-value-card">
          <div class="value-label">当前数值</div>
          <div class="value-display">${data.currentValue}<span class="value-unit">${data.unit}</span></div>
          <div class="value-status ${data.status}">${data.statusText}</div>
          <div class="value-range">正常范围: ${data.normalRange} ${data.unit}</div>
        </div>
        <div class="chart-card">
          <div class="chart-title">最近7天趋势</div>
          <canvas id="trendChart" class="chart-canvas"></canvas>
        </div>
        <div class="history-card">
          <div class="history-title">历史记录</div>
          <div class="history-list">
            ${data.history.map(item => `
              <div class="history-item">
                <div><span class="history-date">${item.date}</span><span class="history-time">${item.time}</span></div>
                <div class="history-value">${item.value} ${data.unit}</div>
              </div>
            `).join('')}
          </div>
        </div>
        <div class="advice-card">
          <div class="advice-title">健康建议</div>
          <div class="advice-content">${data.advice}</div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
  setTimeout(() => drawTrendChart(data), 100);
}

function drawTrendChart(data) {
  const canvas = document.getElementById('trendChart');
  if (!canvas) return;
  const ctx = canvas.getContext('2d');
  const width = canvas.offsetWidth;
  const height = canvas.offsetHeight;
  canvas.width = width;
  canvas.height = height;
  
  const history = data.history.slice().reverse();
  const values = history.map(item => parseFloat(item.value.split('/')[0]));
  const maxValue = Math.max(...values) * 1.1;
  const minValue = Math.min(...values) * 0.9;
  const range = maxValue - minValue;
  
  ctx.strokeStyle = '#f0f0f0';
  ctx.lineWidth = 1;
  for (let i = 0; i <= 4; i++) {
    const y = (height - 40) * i / 4 + 20;
    ctx.beginPath();
    ctx.moveTo(40, y);
    ctx.lineTo(width - 20, y);
    ctx.stroke();
  }
  
  ctx.strokeStyle = '#7C3AED';
  ctx.lineWidth = 2;
  ctx.beginPath();
  values.forEach((value, index) => {
    const x = 40 + (width - 60) * index / (values.length - 1);
    const y = height - 40 - ((value - minValue) / range) * (height - 60);
    if (index === 0) ctx.moveTo(x, y);
    else ctx.lineTo(x, y);
  });
  ctx.stroke();
  
  ctx.fillStyle = '#7C3AED';
  values.forEach((value, index) => {
    const x = 40 + (width - 60) * index / (values.length - 1);
    const y = height - 40 - ((value - minValue) / range) * (height - 60);
    ctx.beginPath();
    ctx.arc(x, y, 4, 0, Math.PI * 2);
    ctx.fill();
  });
}


// ========== 预警页面 ==========
function renderAlertsPage() {
  if (!document.querySelector('link[href="styles/alerts.css"]')) {
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = 'styles/alerts.css';
    document.head.appendChild(link);
  }
  
  const unreadCount = alertsData.filter(a => !a.isRead).length;
  const html = `
    <div class="alerts-page">
      <div class="top-bar">
        <button class="back-btn" onclick="router.back()"><svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7"/></svg></button>
        <h1 class="title">健康预警</h1>
        <div class="right-action"></div>
      </div>
      <div class="alerts-content">
        <div class="alerts-stats">
          <span class="stats-text">您有</span>
          <span class="stats-number">${unreadCount}</span>
          <span class="stats-text">条未读预警</span>
        </div>
        <div class="alerts-list">
          ${alertsData.map(alert => `
            <div class="alert-item ${alert.level} ${!alert.isRead ? 'unread' : ''}" onclick="router.navigate('/alerts/${alert.id}')">
              <div class="alert-header">
                <div class="alert-icon ${alert.level}">${alert.level === 'urgent' ? '⚠️' : alert.level === 'important' ? '❗' : 'ℹ️'}</div>
                <div class="alert-header-content">
                  <div class="alert-title-row">
                    <div class="alert-title">${alert.title}</div>
                    <span class="alert-level ${alert.level}">${alert.levelText}</span>
                  </div>
                  <div class="alert-time">${alert.time}</div>
                </div>
              </div>
              <div class="alert-summary">${alert.summary}</div>
            </div>
          `).join('')}
        </div>
      </div>
    </div>
  `;
  router.render(html);
}

function renderAlertDetailPage(id) {
  if (!document.querySelector('link[href="styles/alert-detail.css"]')) {
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = 'styles/alert-detail.css';
    document.head.appendChild(link);
  }
  
  const alert = alertsData.find(a => a.id === parseInt(id));
  if (!alert) { router.navigate('/alerts'); return; }
  
  const html = `
    <div class="alert-detail-page">
      <div class="top-bar">
        <button class="back-btn" onclick="router.back()"><svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7"/></svg></button>
        <h1 class="title">预警详情</h1>
        <div class="right-action"></div>
      </div>
      <div class="alert-detail-content">
        <div class="alert-detail-header">
          <span class="alert-detail-level ${alert.level}">${alert.levelText}</span>
          <h2 class="alert-detail-title">${alert.title}</h2>
          <div class="alert-detail-time">${alert.time}</div>
        </div>
        <div class="alert-detail-section">
          <div class="section-title">预警说明</div>
          <div class="section-content">${alert.content}</div>
        </div>
        <div class="alert-detail-section">
          <div class="section-title">健康建议</div>
          <div class="section-content">${alert.advice}</div>
        </div>
      </div>
      <div class="alert-detail-actions">
        <button class="action-btn action-btn-secondary" onclick="router.back()">返回</button>
        <button class="action-btn action-btn-primary" onclick="showMessage('演示版本，该功能暂不可用')">联系医生</button>
      </div>
    </div>
  `;
  router.render(html);
}


// ========== 紧急呼救页面 ==========
function renderEmergencyPage() {
  if (!document.querySelector('link[href="styles/emergency.css"]')) {
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = 'styles/emergency.css';
    document.head.appendChild(link);
  }
  
  const html = `
    <div class="emergency-page">
      <div class="emergency-content">
        <div class="emergency-button-container">
          <button class="emergency-button" onclick="showMessage('紧急呼救已发送！')">
            <span class="emergency-icon">🚨</span>
            <span class="emergency-text">紧急呼救</span>
          </button>
          <div class="emergency-hint">长按3秒自动呼救</div>
        </div>
        <div class="quick-dial-section">
          <div class="quick-dial-title">快速拨号</div>
          <div class="quick-dial-grid">
            <button class="quick-dial-btn" onclick="showMessage('正在拨打 120')"><div class="dial-icon">🚑</div><div class="dial-label">急救中心</div><div class="dial-number">120</div></button>
            <button class="quick-dial-btn" onclick="showMessage('正在拨打 110')"><div class="dial-icon">🚓</div><div class="dial-label">报警电话</div><div class="dial-number">110</div></button>
            <button class="quick-dial-btn" onclick="showMessage('正在拨打 119')"><div class="dial-icon">🚒</div><div class="dial-label">火警电话</div><div class="dial-number">119</div></button>
            <button class="quick-dial-btn" onclick="showMessage('正在拨打社区医院')"><div class="dial-icon">🏥</div><div class="dial-label">社区医院</div><div class="dial-number">社区</div></button>
          </div>
        </div>
        <div class="location-card">
          <div class="location-header">
            <div class="location-title">📍 当前位置</div>
          </div>
          <div class="location-address">${locationData.address}</div>
          <button class="share-location-btn" onclick="showMessage('位置已分享')">分享位置</button>
        </div>
        <div class="contacts-section">
          <div class="contacts-header">
            <div class="contacts-title">紧急联系人</div>
            <button class="add-contact-btn" onclick="showMessage('演示版本，该功能暂不可用')">+ 添加</button>
          </div>
          <div class="contacts-list">
            ${emergencyContacts.map(contact => `
              <div class="contact-card">
                <div class="contact-avatar">${contact.name.charAt(0)}</div>
                <div class="contact-info">
                  <div class="contact-name">${contact.name}</div>
                  <div class="contact-relation">${contact.relation}</div>
                  <div class="contact-phone">${contact.phone}</div>
                </div>
                <button class="contact-call-btn" onclick="showMessage('正在拨打 ${contact.name}')">📞</button>
              </div>
            `).join('')}
          </div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}


// ========== 社交页面 ==========
function renderSocialPage() {
  if (!document.querySelector('link[href="styles/social.css"]')) {
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = 'styles/social.css';
    document.head.appendChild(link);
  }
  
  const html = `
    <div class="social-page">
      <div class="social-header">
        <h1 class="social-title">社交圈</h1>
        <button class="post-btn" onclick="showMessage('演示版本，该功能暂不可用')">发布</button>
      </div>
      <div class="social-tabs">
        <button class="tab-btn active">动态</button>
        <button class="tab-btn" onclick="router.navigate('/activities')">活动</button>
      </div>
      <div class="social-content">
        ${socialPosts.map(post => `
          <div class="post-card">
            <div class="post-header">
              <div class="post-avatar">${post.user.nickname.charAt(0)}</div>
              <div class="post-info">
                <div class="post-author">${post.user.nickname}</div>
                <div class="post-time">${post.time}</div>
              </div>
            </div>
            <div class="post-content">${post.content}</div>
            ${post.tags ? '<div class="post-tags">' + post.tags.map(tag => '<span class="post-tag">#' + tag + '</span>').join('') + '</div>' : ''}
            <div class="post-actions">
              <button class="post-action" onclick="showMessage('已点赞')">👍 ${post.likes}</button>
              <button class="post-action" onclick="showMessage('评论功能演示')">💬 ${post.comments}</button>
            </div>
          </div>
        `).join('')}
      </div>
    </div>
  `;
  router.render(html);
}

// ========== 活动列表页面 ==========
function renderActivitiesPage() {
  if (!document.querySelector('link[href="styles/activities.css"]')) {
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = 'styles/activities.css';
    document.head.appendChild(link);
  }
  
  const html = `
    <div class="activities-page">
      <div class="activities-header">
        <h1 class="activities-title">社区活动</h1>
      </div>
      <div class="activities-tabs">
        <button class="tab-btn" onclick="router.navigate('/social')">动态</button>
        <button class="tab-btn active">活动</button>
      </div>
      <div class="activities-content">
        ${activitiesData.map(activity => `
          <div class="activity-card" onclick="router.navigate('/activities/${activity.id}')">
            <div class="activity-image">${activity.category === 'health' ? '💪' : activity.category === 'culture' ? '🎨' : activity.category === 'learning' ? '📚' : '🌳'}</div>
            <div class="activity-info">
              <div class="activity-title">${activity.name}</div>
              <div class="activity-meta">
                <span>📅 ${activity.time.split(' ')[0]}</span>
                <span>📍 ${activity.location}</span>
              </div>
              <div class="activity-footer">
                <span class="activity-enrolled">${activity.enrolled}/${activity.capacity}人</span>
                <span class="activity-status ${activity.status}">${activity.statusText}</span>
              </div>
            </div>
          </div>
        `).join('')}
      </div>
    </div>
  `;
  router.render(html);
}

function renderActivityDetailPage(id) {
  if (!document.querySelector('link[href="styles/activity-detail.css"]')) {
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = 'styles/activity-detail.css';
    document.head.appendChild(link);
  }
  
  const activity = activitiesData.find(a => a.id === parseInt(id));
  if (!activity) { router.navigate('/activities'); return; }
  
  const categoryIcon = activity.category === 'health' ? '💪' : activity.category === 'culture' ? '🎨' : activity.category === 'learning' ? '📚' : '🌳';
  
  const html = `
    <div class="activity-detail-page">
      <div class="top-bar">
        <button class="back-btn" onclick="router.back()"><svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7"/></svg></button>
        <h1 class="title">活动详情</h1>
        <div class="right-action"></div>
      </div>
      <div class="activity-detail-content">
        <div class="activity-detail-header">
          <div class="activity-image-large">${categoryIcon}</div>
          <h2 class="activity-detail-title">${activity.name}</h2>
          <span class="activity-status ${activity.status}">${activity.statusText}</span>
        </div>
        <div class="activity-detail-info">
          <div class="info-item"><span class="info-label">时间：</span><span>${activity.time}</span></div>
          <div class="info-item"><span class="info-label">地点：</span><span>${activity.location}</span></div>
          <div class="info-item"><span class="info-label">费用：</span><span>${activity.fee}</span></div>
          <div class="info-item"><span class="info-label">名额：</span><span>${activity.enrolled}/${activity.capacity}人</span></div>
          <div class="info-item"><span class="info-label">主办方：</span><span>${activity.organizer}</span></div>
        </div>
        <div class="activity-detail-section">
          <div class="section-title">活动介绍</div>
          <div class="section-content">${activity.description}</div>
        </div>
        <div class="activity-detail-section">
          <div class="section-title">注意事项</div>
          <div class="section-content">${activity.notes}</div>
        </div>
        <div class="activity-detail-section">
          <div class="section-title">已报名 (${activity.participants.length}人)</div>
          <div class="participants-list">
            ${activity.participants.map(p => '<span class="participant-avatar">' + p.name.charAt(0) + '</span>').join('')}
          </div>
        </div>
      </div>
      <div class="activity-detail-actions">
        <button class="action-btn action-btn-secondary" onclick="router.back()">返回</button>
        <button class="action-btn action-btn-primary" onclick="showMessage('${activity.status === 'full' ? '活动已满员' : '报名成功！'}')">${activity.status === 'full' ? '已满员' : '立即报名'}</button>
      </div>
    </div>
  `;
  router.render(html);
}


// ========== 个人中心页面 ==========
function renderProfilePage() {
  if (!document.querySelector('link[href="styles/profile.css"]')) {
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = 'styles/profile.css';
    document.head.appendChild(link);
  }
  
  const html = `
    <div class="profile-page">
      <div class="profile-header">
        <div class="profile-avatar">${userData.nickname.charAt(0)}</div>
        <div class="profile-info">
          <div class="profile-name">${userData.nickname}</div>
          <div class="profile-bio">${userData.bio}</div>
        </div>
      </div>
      <div class="profile-stats">
        <div class="stat-item"><div class="stat-value">${userData.stats.posts}</div><div class="stat-label">动态</div></div>
        <div class="stat-item"><div class="stat-value">${userData.stats.activities}</div><div class="stat-label">活动</div></div>
        <div class="stat-item"><div class="stat-value">${userData.stats.healthDays}</div><div class="stat-label">健康天数</div></div>
      </div>
      <div class="profile-menu">
        <div class="menu-item" onclick="router.navigate('/profile/info')">
          <span class="menu-icon">👤</span><span class="menu-text">个人资料</span><span class="menu-arrow">›</span>
        </div>
        <div class="menu-item" onclick="router.navigate('/profile/health-records')">
          <span class="menu-icon">📋</span><span class="menu-text">健康档案</span><span class="menu-arrow">›</span>
        </div>
        <div class="menu-item" onclick="router.navigate('/profile/my-activities')">
          <span class="menu-icon">🎯</span><span class="menu-text">我的活动</span><span class="menu-arrow">›</span>
        </div>
        <div class="menu-item" onclick="router.navigate('/profile/contacts')">
          <span class="menu-icon">📞</span><span class="menu-text">紧急联系人</span><span class="menu-arrow">›</span>
        </div>
        <div class="menu-item" onclick="router.navigate('/profile/settings')">
          <span class="menu-icon">⚙️</span><span class="menu-text">系统设置</span><span class="menu-arrow">›</span>
        </div>
        <div class="menu-item" onclick="router.navigate('/admin-user-list')" style="background: linear-gradient(135deg, #7C3AED 0%, #A78BFA 100%); color: white; margin-top: 12px; border-radius: 12px;">
          <span class="menu-icon">🔧</span><span class="menu-text">后台管理</span><span class="menu-arrow" style="color: white;">›</span>
        </div>
      </div>
      <button class="logout-btn" onclick="if(confirm('确定要退出登录吗？')){showMessage('已退出登录');router.navigate('/login');}">退出登录</button>
    </div>
  `;
  router.render(html);
}


// ========== 个人资料页面 ==========
function renderProfileInfoPage() {
  const html = `
    <div class="profile-info-page">
      <div class="top-bar">
        <button class="back-btn" onclick="router.back()"><svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7"/></svg></button>
        <h1 class="title">个人资料</h1>
        <button class="edit-btn" onclick="showMessage('演示版本，该功能暂不可用')">编辑</button>
      </div>
      <div class="profile-info-content">
        <div class="avatar-section">
          <div class="large-avatar">${userData.nickname.charAt(0)}</div>
          <button class="change-avatar-btn" onclick="showMessage('演示版本，该功能暂不可用')">更换头像</button>
        </div>
        <div class="info-section">
          <div class="info-row"><div class="info-label">昵称</div><div class="info-value">${userData.nickname}</div></div>
          <div class="info-row"><div class="info-label">性别</div><div class="info-value">${userData.gender || '男'}</div></div>
          <div class="info-row"><div class="info-label">年龄</div><div class="info-value">${userData.age || '68'}岁</div></div>
          <div class="info-row"><div class="info-label">手机号</div><div class="info-value">${userData.phone}</div></div>
          <div class="info-row"><div class="info-label">个人简介</div><div class="info-value">${userData.bio}</div></div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}

// ========== 健康档案页面 ==========
function renderHealthRecordsPage() {
  const html = `
    <div class="health-records-page">
      <div class="top-bar">
        <button class="back-btn" onclick="router.back()"><svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7"/></svg></button>
        <h1 class="title">健康档案</h1>
        <div class="right-action"></div>
      </div>
      <div class="health-records-content">
        <div class="records-section">
          <div class="section-title">基本信息</div>
          <div class="record-row"><div class="record-label">身高</div><div class="record-value">170 cm</div></div>
          <div class="record-row"><div class="record-label">体重</div><div class="record-value">65 kg</div></div>
          <div class="record-row"><div class="record-label">血型</div><div class="record-value">A型</div></div>
        </div>
        <div class="records-section">
          <div class="section-title">慢性病史</div>
          <div class="disease-item">
            <div class="disease-name">高血压</div>
            <div class="disease-time">确诊时间：2020年3月</div>
            <div class="disease-status">已控制</div>
          </div>
          <div class="disease-item">
            <div class="disease-name">糖尿病</div>
            <div class="disease-time">确诊时间：2021年6月</div>
            <div class="disease-status">已控制</div>
          </div>
        </div>
        <div class="records-section">
          <div class="section-title">过敏史</div>
          <div class="allergy-tags">
            <span class="allergy-tag">青霉素</span>
          </div>
        </div>
        <div class="records-section">
          <div class="section-title">用药记录</div>
          <div class="medicine-item">
            <div class="medicine-name">降压药（硝苯地平）</div>
            <div class="medicine-usage">每日1次，每次1片，早餐后服用</div>
          </div>
          <div class="medicine-item">
            <div class="medicine-name">降糖药（二甲双胍）</div>
            <div class="medicine-usage">每日1次，每次1粒，早餐后服用</div>
          </div>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}


// ========== 我的活动页面 ==========
function renderMyActivitiesPage() {
  const myActivities = [
    { id: 1, name: '太极拳晨练', date: '2026-01-10', time: '07:00-08:00', status: 'upcoming', statusText: '即将开始' },
    { id: 2, name: '书法课程', date: '2026-01-12', time: '14:00-16:00', status: 'upcoming', statusText: '即将开始' },
    { id: 3, name: '健康讲座', date: '2026-01-08', time: '09:00-11:00', status: 'completed', statusText: '已完成' },
    { id: 4, name: '社区郊游', date: '2026-01-05', time: '08:00-17:00', status: 'completed', statusText: '已完成' }
  ];
  
  const html = `
    <div class="my-activities-page">
      <div class="top-bar">
        <button class="back-btn" onclick="router.back()"><svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7"/></svg></button>
        <h1 class="title">我的活动</h1>
        <div class="right-action"></div>
      </div>
      <div class="my-activities-content">
        <div class="activities-stats">
          <div class="stat-item"><div class="stat-value">2</div><div class="stat-label">已参加</div></div>
          <div class="stat-item"><div class="stat-value">2</div><div class="stat-label">进行中</div></div>
          <div class="stat-item"><div class="stat-value">0</div><div class="stat-label">已报名</div></div>
        </div>
        <div class="activities-list">
          ${myActivities.map(activity => `
            <div class="activity-item">
              <div class="activity-info">
                <div class="activity-name">${activity.name}</div>
                <div class="activity-time">${activity.date} ${activity.time}</div>
              </div>
              <span class="activity-status ${activity.status}">${activity.statusText}</span>
            </div>
          `).join('')}
        </div>
      </div>
    </div>
  `;
  router.render(html);
}

// ========== 紧急联系人页面 ==========
function renderEmergencyContactsPage() {
  const html = `
    <div class="emergency-contacts-page">
      <div class="top-bar">
        <button class="back-btn" onclick="router.back()"><svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7"/></svg></button>
        <h1 class="title">紧急联系人</h1>
        <button class="add-btn" onclick="showMessage('演示版本，该功能暂不可用')">+</button>
      </div>
      <div class="emergency-contacts-content">
        <div class="contacts-tip">紧急情况下，系统将自动通知以下联系人</div>
        <div class="contacts-list">
          ${emergencyContacts.map((contact, index) => `
            <div class="contact-card">
              <div class="contact-priority">${index + 1}</div>
              <div class="contact-avatar">${contact.name.charAt(0)}</div>
              <div class="contact-details">
                <div class="contact-name">${contact.name}</div>
                <div class="contact-relation">${contact.relation}</div>
                <div class="contact-phone">${contact.phone}</div>
              </div>
              <div class="contact-actions">
                <button onclick="showMessage('正在拨打 ${contact.name}')">📞</button>
                <button onclick="showMessage('演示版本，该功能暂不可用')">✏️</button>
              </div>
            </div>
          `).join('')}
        </div>
        <div class="contacts-note">
          <div class="note-title">💡 温馨提示</div>
          <ul class="note-list">
            <li>建议添加3-5位紧急联系人</li>
            <li>紧急情况下，系统将按优先级顺序通知联系人</li>
            <li>请确保联系人电话号码准确无误</li>
          </ul>
        </div>
      </div>
    </div>
  `;
  router.render(html);
}


// ========== 系统设置页面 ==========
function renderSettingsPage() {
  const html = `
    <div class="settings-page">
      <div class="top-bar">
        <button class="back-btn" onclick="router.back()"><svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7"/></svg></button>
        <h1 class="title">系统设置</h1>
        <div class="right-action"></div>
      </div>
      <div class="settings-content">
        <div class="settings-section">
          <div class="section-title">通知设置</div>
          <div class="setting-item">
            <div class="setting-info"><div class="setting-name">健康提醒</div><div class="setting-desc">体检测量、用药等健康提醒</div></div>
            <label class="switch"><input type="checkbox" checked onchange="showMessage('设置已更新')"><span class="slider"></span></label>
          </div>
          <div class="setting-item">
            <div class="setting-info"><div class="setting-name">活动通知</div><div class="setting-desc">活动报名、开始提醒</div></div>
            <label class="switch"><input type="checkbox" checked onchange="showMessage('设置已更新')"><span class="slider"></span></label>
          </div>
          <div class="setting-item">
            <div class="setting-info"><div class="setting-name">社交消息</div><div class="setting-desc">点赞、评论等消息通知</div></div>
            <label class="switch"><input type="checkbox" checked onchange="showMessage('设置已更新')"><span class="slider"></span></label>
          </div>
        </div>
        <div class="settings-section">
          <div class="section-title">隐私设置</div>
          <div class="setting-item">
            <div class="setting-info"><div class="setting-name">位置服务</div><div class="setting-desc">用于紧急呼救定位</div></div>
            <label class="switch"><input type="checkbox" checked onchange="showMessage('设置已更新')"><span class="slider"></span></label>
          </div>
          <div class="setting-item">
            <div class="setting-info"><div class="setting-name">健康数据共享</div><div class="setting-desc">与医生共享健康数据</div></div>
            <label class="switch"><input type="checkbox" onchange="showMessage('设置已更新')"><span class="slider"></span></label>
          </div>
        </div>
        <div class="settings-section">
          <div class="section-title">显示设置</div>
          <div class="setting-item clickable" onclick="showMessage('字体大小设置')">
            <div class="setting-info"><div class="setting-name">字体大小</div><div class="setting-desc">当前：标准</div></div>
            <span class="setting-arrow">›</span>
          </div>
          <div class="setting-item clickable" onclick="showMessage('主题模式设置')">
            <div class="setting-info"><div class="setting-name">主题模式</div><div class="setting-desc">当前：浅色模式</div></div>
            <span class="setting-arrow">›</span>
          </div>
        </div>
        <div class="settings-section">
          <div class="section-title">账号安全</div>
          <div class="setting-item clickable" onclick="showMessage('演示版本，该功能暂不可用')">
            <div class="setting-info"><div class="setting-name">修改密码</div></div>
            <span class="setting-arrow">›</span>
          </div>
          <div class="setting-item clickable" onclick="showMessage('演示版本，该功能暂不可用')">
            <div class="setting-info"><div class="setting-name">绑定手机</div><div class="setting-desc">138****8888</div></div>
            <span class="setting-arrow">›</span>
          </div>
        </div>
        <div class="settings-section">
          <div class="section-title">关于</div>
          <div class="setting-item clickable" onclick="showMessage('智慧养老系统 v2.0.0')">
            <div class="setting-info"><div class="setting-name">关于我们</div></div>
            <span class="setting-arrow">›</span>
          </div>
          <div class="setting-item clickable" onclick="showMessage('隐私政策')">
            <div class="setting-info"><div class="setting-name">隐私政策</div></div>
            <span class="setting-arrow">›</span>
          </div>
          <div class="setting-item clickable" onclick="showMessage('当前版本：v2.0.0')">
            <div class="setting-info"><div class="setting-name">版本信息</div><div class="setting-desc">v2.0.0</div></div>
            <span class="setting-arrow">›</span>
          </div>
        </div>
        <button class="clear-cache-btn" onclick="if(confirm('确定要清除缓存吗？')){showMessage('缓存已清除');}">清除缓存</button>
      </div>
    </div>
  `;
  router.render(html);
}
