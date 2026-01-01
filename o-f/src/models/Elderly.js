export class Elderly {
  constructor({
    id = null,
    name = '',
    age = null,
    gender = '',
    phone = '',
    address = '',
    birthDate = null,
    emergencyContactName = '',
    emergencyContactPhone = '',
    healthStatus = '',
    specialMedicalNeeds = '',
    roomNumber = '',
    admissionDate = null,
    isActive = true,
    notes = ''
  } = {}) {
    this.id = id
    this.name = name
    this.age = age
    this.gender = gender
    this.phone = phone
    this.address = address
    this.birthDate = birthDate
    this.emergencyContactName = emergencyContactName
    this.emergencyContactPhone = emergencyContactPhone
    this.healthStatus = healthStatus
    this.specialMedicalNeeds = specialMedicalNeeds
    this.roomNumber = roomNumber
    this.admissionDate = admissionDate
    this.isActive = isActive
    this.notes = notes
  }

  // 静态方法：从API响应创建Elderly实例
  static fromAPI(data) {
    return new Elderly({
      id: data.id,
      name: data.name,
      age: data.age,
      gender: data.gender,
      phone: data.phone,
      address: data.address,
      birthDate: data.birthDate,
      emergencyContactName: data.emergencyContactName,
      emergencyContactPhone: data.emergencyContactPhone,
      healthStatus: data.healthStatus,
      specialMedicalNeeds: data.specialMedicalNeeds,
      roomNumber: data.roomNumber,
      admissionDate: data.admissionDate,
      isActive: data.isActive,
      notes: data.notes
    })
  }

  // 静态方法：从API响应数组创建Elderly实例数组
  static fromAPIList(dataList) {
    return dataList.map(item => Elderly.fromAPI(item))
  }

  // 转换为API请求格式
  toAPI() {
    return {
      id: this.id,
      name: this.name,
      age: this.age,
      gender: this.gender,
      phone: this.phone,
      address: this.address,
      birthDate: this.birthDate,
      emergencyContactName: this.emergencyContactName,
      emergencyContactPhone: this.emergencyContactPhone,
      healthStatus: this.healthStatus,
      specialMedicalNeeds: this.specialMedicalNeeds,
      roomNumber: this.roomNumber,
      admissionDate: this.admissionDate,
      isActive: this.isActive,
      notes: this.notes
    }
  }
}