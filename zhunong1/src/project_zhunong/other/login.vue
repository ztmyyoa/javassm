<script setup>
import { useStore } from 'vuex'
import { ref, provide} from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import axios from "axios";
import store from "@/store.js";

const form = ref({
  username: 'user',
  password: '123',
  role: '管理员'
})

const router = useRouter()
const isLoggedIn = ref(false)
const onSubmit = async () => {
  console.log('登录请求数据:', form.value)

  try {
    // 向后端发送登录请求，包含角色信息
    const response = await axios.post('http://localhost:8080/spring_mvc_mysql_vue_war/user/login', {
      username: form.value.username,
      password: form.value.password,
      usertype: form.value.role  ,// 传递角色信息

    })

    console.log('登录响应:', response.data)  // 输出后端响应数据

    if (response.status === 200) {
      ElMessage({
        message: '登录成功',
        type: 'success',

      }) // 在登录成功后更新 Vuex store
      store.commit('setUsername', form.value.username);
      store.commit('setPassword', form.value.password);

      isLoggedIn.value = true
      provide('Username',form.value.username)
      provide('Password',form.value.password)
      // 根据角色跳转到不同的页面
      if (form.value.role === '管理员') {
        await router.push('/adminhome')
      } else if (form.value.role === '农户') {
        await router.push('/farmer/home')
      } else {
        await router.push('/consumer/home')
      }
    } else {
      ElMessage({
        message: '登录请求失败',
        type: 'error',

      })
    }
  } catch (error) {
    console.error('登录失败，用户名或密码错误:', error)  // 输出错误信息
    ElMessage({
      message: '登录失败，用户名或密码错误',
      type: 'error',
    })
  }
}
const goToRegister = () => {
  router.push('/register')
}

const goToWelcome = () => {
  router.push('/')  // 跳转到首页或欢迎页
}
</script>

<template>
  <div class="login-container">
    <div class="login-form">
      <h2>农产品销售平台登入</h2>
      <el-form :model="form" class="login-form-inner">
        <div class="flex gap-4 mb-4">
          <span>账号：</span>
          <el-input
              v-model="form.username"
              style="width: 80%"
              placeholder="请输入账号"
          />
        </div>
        <div class="flex gap-4">
          <span>密码：</span>
          <el-input v-model="form.password" style="width: 80%" placeholder="请输入密码" type="password">
          </el-input>
        </div>
        <el-form-item>
          <el-radio-group v-model="form.role">
            <el-radio value="管理员">管理员</el-radio>
            <el-radio value="农户">农户</el-radio>
            <el-radio value="消费者">消费者</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item >
          <el-button type="primary" @click="onSubmit">登录</el-button>
          <el-button type="text" @click="goToRegister">没有账号？注册</el-button>
        </el-form-item>
        <el-button type="text" @click="router.push('/')">返回</el-button>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}
.button{
  justify-content: center;
}
.login-form {
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

.login-form-inner {
  width: 100%;
  margin: 0 auto;
}
</style>
