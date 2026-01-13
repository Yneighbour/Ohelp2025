// 个人中心子页面处理函数

// 编辑个人资料
function handleEditProfile() {
  showMessage('演示版本\n\n编辑个人资料功能暂不可用');
}

// 添加紧急联系人
function handleAddEmergencyContact() {
  showMessage('添加紧急联系人\n\n演示版本，该功能暂不可用');
}

// 拨打联系人电话
function handleCallContact(name, phone) {
  showMessage(`拨打 ${name}\n电话：${phone}\n\n演示版本，无法实际拨打电话`);
}

// 编辑联系人
function handleEditContact(name) {
  showMessage(`编辑联系人：${name}\n\n演示版本，该功能暂不可用`);
}

// 删除联系人
function handleDeleteContact(name) {
  if (showConfirm(`确定要删除联系人"${name}"吗？`)) {
    showMessage('删除成功');
  }
}

// 切换设置
function handleToggleSetting(name, enabled) {
  showMessage(`${name}已${enabled ? '开启' : '关闭'}`);
}

// 字体大小设置
function handleFontSize() {
  showMessage('字体大小设置\n\n可选：小、标准、大、特大');
}

// 主题设置
function handleTheme() {
  showMessage('主题模式设置\n\n可选：浅色模式、深色模式、跟随系统');
}

// 修改密码
function handleChangePassword() {
  showMessage('修改密码\n\n演示版本，该功能暂不可用');
}

// 绑定手机
function handleBindPhone() {
  showMessage('绑定手机\n\n演示版本，该功能暂不可用');
}

// 关于我们
function handleAbout() {
  showMessage('智慧养老系统\n\n版本：v2.0.0\n开发时间：2026年1月\n\n为老年人提供健康管理、社交互动和紧急救助服务');
}

// 隐私政策
function handlePrivacyPolicy() {
  showMessage('隐私政策\n\n我们重视您的隐私保护...\n\n演示版本，完整内容请查看实际应用');
}

// 版本信息
function handleVersion() {
  showMessage('版本信息\n\n当前版本：v2.0.0\n更新时间：2026-01-08\n\n已是最新版本');
}

// 清除缓存
function handleClearCache() {
  if (showConfirm('确定要清除缓存吗？\n\n清除后需要重新加载数据')) {
    showMessage('缓存已清除');
  }
}
