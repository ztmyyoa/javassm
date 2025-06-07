<script setup>
import {onMounted, ref} from 'vue';
import { ElMessage } from 'element-plus';
import IconDocumentation from "@/components/icons/IconDocumentation.vue";
import router from "@/router.js";
import {useStore} from "vuex";
import axios from "axios";
const adminName = ref('');
const currentModule = ref('审核注册信息');
const store = useStore()
// 获取用户名和密码
const adminusername = store.getters.getUsername;
const adminpassword = store.getters.getPassword;

console.log(adminusername);  // 显示保存的用户名
console.log(adminpassword);
const userdata=async () => {
  try {
    const res = await axios.get(`http://localhost:8080/spring_mvc_mysql_vue_war/user/username?username=${adminusername}`);
    const response = await res.data;
    console.log('用户数据:', response);
    if (response) {
      adminName.value = response.name;
      console.log('用户数据:', adminName);
    } else {
      console.error('返回的数据格式错误:', response);
      adminName.value = '';
    }
  } catch (error) {
    console.error('请求出错:', error);
  }
}
onMounted(() => {
  userdata()
})
const goTo = (path) => {
  router.push(`/adminhome/${path}`);
  currentModule.value = path;
};
// 登出功能
const logout = () => {
  store.commit('clearUserData');  // 清除 Vuex 中的用户数据
  router.push('/login');  // 重定向到登录页面
}
</script>

<template>
  <el-container class="common-layout">
    <el-header class="el-header">
      <h2>平台管理员后台</h2>
      <div class="user-info">
        <span>{{ adminName }}</span>
        <el-button @click="logout" size="small" type="danger">登出</el-button>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px" class="aside">
        <el-menu default-active="1" class="adminmanu">
          <el-sub-menu index="1">
            <template #title>
              用户信息管理
              </template>
            <el-menu-item index="1-1" @click="goTo('registeraudit')">审核注册信息</el-menu-item>
            <el-menu-item index="1-2" @click="goTo('farmermanage')" >农户管理</el-menu-item>
            <el-menu-item index="1-3" @click="goTo('consumermanage')">消费者管理</el-menu-item>
            <el-menu-item index="1-4" @click="goTo('subsidymanage')">补贴政策管理</el-menu-item>
         </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              平台资源管理
            </template>
            <el-menu-item index="2-1" @click="goTo('productmanage')">农产品管理</el-menu-item>
            <el-menu-item index="2-2" @click="goTo('admanage')">广告管理</el-menu-item>
            <el-menu-item index="2-3" @click="goTo('announcementmanage')">公告管理</el-menu-item>
            <el-menu-item index="2-4" @click="goTo('promotionmanage')">活动管理</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              销售处理
            </template>
            <el-menu-item index="3-1" @click="goTo('ordersmanage')">订单处理</el-menu-item>
            <el-menu-item index="3-2" @click="goTo('reviewmanage')">投诉建议处理</el-menu-item>
<!--            <el-menu-item index="3-3">物流配送协调</el-menu-item>-->
          </el-sub-menu>
          <el-menu-item index="4" @click="goTo('salesreport')">
            数据报告与分析
          </el-menu-item>
          <el-menu-item index="5" @click="goTo('platformmonitor')">
            平台监控
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main class="main">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.common-layout {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
}

.el-header {
  background-color: #80F60B;
  color: white;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
}

.user-info img {
  border-radius: 50%;
  margin-right: 10px;
}

.aside {
  background-color: #f5f7fa;
  padding: 20px;
}

.main {
  background-color: #ffffff;
  padding: 0;
  flex: 1;
  overflow: auto;
}

</style>
