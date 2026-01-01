export class Worker {
  constructor({
    id = null,
    laogongUuid = '',
    username = '',
    password = '',
    laogongName = '',
    laogongPhone = '',
    laogongIdNumber = '',
    laogongDelete = '0',
    createTime = null,
    updateTime = null
  } = {}) {
    this.id = id
    this.laogongUuid = laogongUuid
    this.username = username
    this.password = password
    this.laogongName = laogongName
    this.laogongPhone = laogongPhone
    this.laogongIdNumber = laogongIdNumber
    this.laogongDelete = laogongDelete
    this.createTime = createTime
    this.updateTime = updateTime
  }

  // 静态方法：从API响应创建Worker实例
  static fromAPI(data) {
    return new Worker({
      id: data.id,
      laogongUuid: data.laogongUuid,
      username: data.username,
      password: data.password,
      laogongName: data.laogongName,
      laogongPhone: data.laogongPhone,
      laogongIdNumber: data.laogongIdNumber,
      laogongDelete: data.laogongDelete,
      createTime: data.createTime,
      updateTime: data.updateTime
    })
  }

  // 静态方法：从API响应数组创建Worker实例数组
  static fromAPIList(dataList) {
    return dataList.map(item => Worker.fromAPI(item))
  }

  // 转换为API请求格式
  toAPI() {
    return {
      id: this.id,
      laogongUuid: this.laogongUuid,
      username: this.username,
      password: this.password,
      laogongName: this.laogongName,
      laogongPhone: this.laogongPhone,
      laogongIdNumber: this.laogongIdNumber,
      laogongDelete: this.laogongDelete,
      createTime: this.createTime,
      updateTime: this.updateTime
    }
  }
}