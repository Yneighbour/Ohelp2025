# ⚡ 报名管理 - 30秒快速解决

## 问题
报名管理界面空白

## 解决

### Step 1: 打开MySQL
任何MySQL工具（Workbench / 命令行 / Navicat）

### Step 2: 执行SQL
连接到 `ohelp` 数据库，复制下面的SQL执行：

```sql
DELETE FROM enrollment WHERE 1=1;

INSERT INTO enrollment (activity_id, elderly_id, status, enroll_time, check_in_time, notes, is_active) VALUES
(1, 1, 'attended', DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), '已参加太极拳课程', 1),
(1, 2, 'confirmed', DATE_SUB(NOW(), INTERVAL 6 DAY), NULL, '已确认参加太极拳', 1),
(1, 3, 'pending', DATE_SUB(NOW(), INTERVAL 5 DAY), NULL, '刚报名太极拳，待管理员确认', 1),
(1, 4, 'cancelled', DATE_SUB(NOW(), INTERVAL 4 DAY), NULL, '因身体不适取消太极拳报名', 1),
(2, 1, 'attended', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), '参加书法班学习', 1),
(2, 2, 'attended', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), '完成书法班课程', 1),
(2, 3, 'pending', DATE_SUB(NOW(), INTERVAL 9 DAY), NULL, '报名书法班，等待确认', 1),
(2, 4, 'absent', DATE_SUB(NOW(), INTERVAL 11 DAY), NULL, '报名书法班但未参加', 1),
(3, 1, 'confirmed', DATE_SUB(NOW(), INTERVAL 2 DAY), NULL, '已确认参加棋类比赛', 1),
(3, 2, 'pending', NOW(), NULL, '新报名棋类比赛', 1),
(3, 3, 'confirmed', DATE_SUB(NOW(), INTERVAL 1 DAY), NULL, '已确认参加棋类比赛', 1),
(3, 4, 'pending', NOW(), NULL, '报名棋类比赛中', 1);
```

### Step 3: 刷新浏览器
按 `Ctrl+F5` 或 `Cmd+Shift+R` 清除缓存

✅ 完成！应该能看到12条报名记录

---

## 详细信息

- 📄 完整方案：[ENROLLMENT_SOLUTION_SUMMARY.md](ENROLLMENT_SOLUTION_SUMMARY.md)
- 📄 快速指南：[ENROLLMENT_DATA_QUICK_FIX.md](ENROLLMENT_DATA_QUICK_FIX.md)
- 📄 初始化指南：[DATABASE_INIT_GUIDE.md](DATABASE_INIT_GUIDE.md)

## 或使用脚本文件

```bash
# 方式1：快速恢复脚本
mysql -u root -p ohelp < "o-b/QUICK_ENROLLMENT_DATA.sql"

# 方式2：完整初始化
mysql -u root -p ohelp < "o-b/database_schema.sql"
mysql -u root -p ohelp < "o-b/database_init_data.sql"

# 方式3：验证数据库
mysql -u root -p ohelp < "o-b/verify_database.sql"
```

---

## 数据说明
- 12条报名记录
- 3个活动
- 4个老人
- 5种状态（待确认、已确认、已签到、未参加、已取消）
