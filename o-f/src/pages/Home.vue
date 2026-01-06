<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <!-- 欢迎区域 -->
      <section class="welcome-section">
        <h1>欢迎使用智慧养老服务管理系统</h1>
        <p class="welcome-text">
          我们致力于为老年人提供高质量、智能化的养老服务管理解决方案，
          帮助机构提升服务质量和管理效率，让老年人享受更舒适、更安全的晚年生活。
        </p>
      </section>
      
      <!-- 功能卡片区域 -->
      <section class="features-section">
        <div class="cards-grid">
          <div class="card feature-card">
            <div class="card-icon">📊</div>
            <h3>数据仪表盘</h3>
            <p>实时监控养老机构运营数据，直观展示各项指标和趋势，助力管理层科学决策。</p>
            <a href="/dashboard.html" class="btn btn-primary btn-sm">查看详情</a>
          </div>
          
          <div class="card feature-card">
            <div class="card-icon">👴</div>
            <h3>老人档案管理</h3>
            <p>全面管理老年人的基本信息、健康状况、护理记录等，实现档案数字化、规范化管理。</p>
            <a href="/Laoren.html" class="btn btn-primary btn-sm">查看详情</a>
          </div>
          
          <div class="card feature-card">
            <div class="card-icon">📋</div>
            <h3>服务请求管理</h3>
            <p>高效处理老年人的服务请求，跟踪服务进度，确保服务质量和及时性。</p>
            <a href="/requests.html" class="btn btn-primary btn-sm">查看详情</a>
          </div>
          
          <div class="card feature-card">
            <div class="card-icon">⚕️</div>
            <h3>健康监测</h3>
            <p>实时监测老年人的健康数据，及时预警异常情况，保障老年人的健康安全。</p>
            <a href="#" class="btn btn-primary btn-sm">查看详情</a>
          </div>
        </div>
      </section>
      
      <!-- 统计数据区域 -->
      <section class="stats-section">
        <h2>系统概览</h2>
        <div class="stats-grid">
          <div class="card stat-card">
            <div class="stat-number">120+</div>
            <div class="stat-label">在住老人</div>
          </div>
          
          <div class="card stat-card">
            <div class="stat-number">85%</div>
            <div class="stat-label">服务满意度</div>
          </div>
          
          <div class="card stat-card">
            <div class="stat-number">24/7</div>
            <div class="stat-label">全天候服务</div>
          </div>
          
          <div class="card stat-card">
            <div class="stat-number">50+</div>
            <div class="stat-label">服务人员</div>
          </div>
        </div>
      </section>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'

export default {
  components: { SiteHeader, SiteFooter }
}
</script>

<style scoped>
.welcome-section {
  text-align: center;
  margin-bottom: 3rem;
  padding: 2rem;
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
  border-radius: 12px;
}

.welcome-text {
  font-size: 1.125rem;
  color: #555;
  max-width: 800px;
  margin: 0 auto;
  line-height: 1.8;
}

.features-section {
  margin-bottom: 3rem;
}

.cards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 1.5rem;
}

.feature-card {
  text-align: center;
  transition: all 0.3s ease;
}

.feature-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.card-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
  display: block;
}

.feature-card h3 {
  margin-bottom: 1rem;
  color: #2c3e50;
}

.feature-card p {
  color: #666;
  margin-bottom: 1.5rem;
  line-height: 1.6;
}

.stats-section {
  margin-bottom: 3rem;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
}

.stat-card {
  text-align: center;
  padding: 2rem 1rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
}

.stat-number {
  font-size: 3rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
}

.stat-label {
  font-size: 1.125rem;
  opacity: 0.9;
}

@media (max-width: 768px) {
  .welcome-section {
    padding: 1rem;
  }
  
  .cards-grid,
  .stats-grid {
    grid-template-columns: 1fr;
  }
  
  .stat-number {
    font-size: 2.5rem;
  }
}
</style>
