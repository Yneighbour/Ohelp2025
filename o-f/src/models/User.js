export class User {
  constructor({
    id = null,
    username = '',
    password = '',
    email = '',
    phone = '',
    realName = '',
    age = null,
    gender = '',
    avatar = '',
    role = '',
    isActive = true,
    createTime = null,
    updateTime = null,
    lastLoginTime = null
  } = {}) {
    this.id = id
    this.username = username
    this.password = password
    this.email = email
    this.phone = phone
    this.realName = realName
    this.age = age
    this.gender = gender
    this.avatar = avatar
    this.role = role
    this.isActive = isActive
    this.createTime = createTime
    this.updateTime = updateTime
    this.lastLoginTime = lastLoginTime
  }

  // 静态方法：从API响应创建User实例
  static fromAPI(data) {
    return new User({
      id: data.id,
      username: data.username,
      password: data.password,
      email: data.email,
      phone: data.phone,
      realName: data.realName,
      age: data.age,
      gender: data.gender,
      avatar: data.avatar,
      role: data.role,
      isActive: data.isActive,
      createTime: data.createTime,
      updateTime: data.updateTime,
      lastLoginTime: data.lastLoginTime
    })
  }

  // 静态方法：从API响应数组创建User实例数组
  static fromAPIList(dataList) {
    return dataList.map(item => User.fromAPI(item))
  }

  // 转换为API请求格式
  toAPI() {
    return {
      id: this.id,
      username: this.username,
      password: this.password,
      email: this.email,
      phone: this.phone,
      realName: this.realName,
      age: this.age,
      gender: this.gender,
      avatar: this.avatar,
      role: this.role,
      isActive: this.isActive,
      createTime: this.createTime,
      updateTime: this.updateTime,
      lastLoginTime: this.lastLoginTime
    }
  }
}