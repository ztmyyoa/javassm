<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import axios from "axios";

const registerForm = ref({
  username: 'A',
  password: '123',
  email: '1@qq.com',
  phone: '12345678901',
  name: 'aaa',
  role: '消费者',
})

const router = useRouter()

const onRegister =async () => {
  try {
    const data={
      username: registerForm.value.username,
      password: registerForm.value.password,
      email: registerForm.value.email,
      phone: registerForm.value.phone,
      name: registerForm.value.name,
      usertype: registerForm.value.role,
      regtime: new Date().toISOString(),
      avatar: '',  // 默认头像字段
      restatus: 0  // 默认注册待审核
    }
    const response = await axios.post('http://localhost:8080/spring_mvc_mysql_vue_war/user/add', data);
    if (response.status === 201){
      ElMessage('注册成功');
      router.push('/login')
    }
  }catch (error){
    ElMessage('注册失败');
    console.error('注册请求失败:', error)
  }
  console.log('注册请求数据:', registerForm.value)
}
</script>

<template>
  <div class="register-container">
    <div class="register-form">
      <h2>农产品销售平台注册</h2>
      <el-form :model="registerForm" class="register-form-inner">
        <div class="flex gap-4 mb-4">
          <span>账号：</span>
          <el-input v-model="registerForm.username" style="width: 80%" placeholder="请输入账号" />
        </div>
        <div class="flex gap-4 mb-4">
          <span>密码：</span>
          <el-input v-model="registerForm.password" style="width: 80%" placeholder="请输入密码" type="password" />
        </div>
        <div class="flex gap-4 mb-4">
          <span>姓名：</span>
          <el-input v-model="registerForm.name" style="width: 80%" placeholder="请输入姓名" />
        </div>
        <div class="flex gap-4 mb-4">
          <span>邮箱：</span>
          <el-input v-model="registerForm.email" style="width: 80%" placeholder="请输入邮箱" />
        </div>
        <div class="flex gap-4 mb-4">
          <span>电话：</span>
          <el-input v-model="registerForm.phone" style="width: 80%" placeholder="请输入电话" />
        </div>
        <el-form-item>
          <el-radio-group v-model="registerForm.role">
            <el-radio value="农户">农户</el-radio>
            <el-radio value="消费者">消费者</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onRegister">注册</el-button>
          <el-button type="text" @click="router.push('/login')">已有账号？登录</el-button>
        </el-form-item>
      </el-form>
      <el-button type="text" @click="router.push('/')">返回</el-button> <!-- 跳转到登录页面 -->
    </div>
  </div>
</template>

<style scoped>
.register-container {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

.register-form {
  background: rgba(255, 255, 255, 0.9);
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  width: 400px;
}

h2 {
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
}

.register-form-inner {
  width: 100%;
  margin: 0 auto;
}
</style>
