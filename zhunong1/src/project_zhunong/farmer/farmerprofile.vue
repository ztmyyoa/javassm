<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import {useStore} from "vuex";
import axios from "axios";
const store = useStore()
// 获取用户名和密码
const farmerusername = store.getters.getUsername;
const farmerpassword = store.getters.getPassword;
console.log(farmerusername);
console.log(farmerpassword);
// 表单模型
const form = ref({
  name: '',
  phone: '',
  email: '',
  address: ''
})

// 校验规则
const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
  ],
  address: [{ required: true, message: '请输入地址', trigger: 'blur' }]
}

const formRef = ref(null)
const farmerdata=async () => {
  try {
    const res = await axios.get(`http://localhost:8080/spring_mvc_mysql_vue_war/farmer/getfamerprofile?username=${farmerusername}`);
    const response = await res.data;
    console.log('用户数据:', response);
    if (response) {
      form.value = response;
      console.log('用户数据:', form.value);
    } else {
      console.error('返回的数据格式错误:', response);
      form.value = {};
    }
  }catch (error){
    console.error('请求出错:', error);
  }
};
// 模拟获取当前用户数据
onMounted(() => {
  farmerdata()
})

const submitForm = async () => {
  try {

    console.log('编辑农户:', form.value);
    const res = await axios.put(`http://localhost:8080/spring_mvc_mysql_vue_war/farmer/update`, form.value);
    console.log('提交结果:', res.data);
    if (res.data) {
      ElMessage.success('更新成功')
    } else {
      ElMessage.error('更新失败')
    }
  }catch (error){
    console.error('提交出错:', error);
  }

}


</script>

<template>
  <el-card class="profile-card">
    <div class="profile-header">
      <h2>个人中心</h2>
    </div>

    <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" />
      </el-form-item>

      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" />
      </el-form-item>

      <el-form-item label="密码" prop="username">
        <el-input v-model="form.password" />
      </el-form-item>

      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" />
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" />
      </el-form-item>

      <el-form-item label="地址" prop="address">
        <el-input v-model="form.address" type="textarea" rows="2" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm">保存修改</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<style scoped>
.profile-card {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
}
.profile-header {
  margin-bottom: 20px;
  text-align: center;
}
</style>
