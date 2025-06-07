<script setup>
import {onMounted, ref} from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import {useStore} from "vuex";
const store = useStore()
// 获取用户名和密码
const farmerusername = store.getters.getUsername;
const farmerpassword = store.getters.getPassword;
console.log(farmerusername);
console.log(farmerpassword);
const router = useRouter()

// 登录控制
const isLoggedIn = ref(true) // 登录状态
const loginSectionView = ref(true) // 控制登录块是否显示
const drawerVisible = ref(false) // 控制抽屉是否显示

// 处理登录（模拟）
// 搜索
const searchQuery = ref('')
const searchProduct = () => {
  if (!searchQuery.value.trim()) {
    ElMessage.warning('请输入关键词')
    return
  }
  router.push({ path: '/farmer/search', query: { keyword: searchQuery.value.trim() } })
}

// 模拟农产品数据
const products = ref([
])
const getdata = async () => {
  // 获取数据逻辑
  try {

    const productres=await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/product/gettop');
    const productdata=await productres.json();
    if (products) {
      products.value = productdata;
      console.log('商品数据:', products);
    } else {
      console.error('返回的数据格式错误:', productdata);
      products.value = [];
    }
  }catch (error){
    console.error('获取广告列表失败：', error);
  }
}
onMounted(()=>{
  getdata()
})
const handleDrawerSelect = (key) => {
  // 关闭抽屉
  drawerVisible.value = false

  // 路由跳转
  switch (key) {
    case 'profile':
        router.push('/farmer/profile')
          break
    case 'product':
      router.push('/farmer/products') // 你自己的路由路径
      break
    case 'orders':
      router.push('/farmer/orders')
      break
    case 'reports':
      router.push('/farmer/reports')
      break
    case 'training':
      router.push('/farmer/training')
      break
    case 'feedback':
      router.push('/farmer/feedback')
      break
    case 'subsidy':
      router.push('/farmer/subsidy')
      break
    case 'back':
      router.push('/farmer/home')
      break
  }
}

</script>

<template>
  <div class="home-container">

    <!-- 登录后显示顶部导航 -->
    <div v-if="isLoggedIn" class="top-navbar">
      <img src="/src/assets/img.png" alt="logo" class="logo">
      <el-input
          v-model="searchQuery"
          placeholder="搜索农产品"
          class="search-box"
          @keyup.enter="searchProduct"
      >
        <template #append>
          <el-button icon="el-icon-search" @click="searchProduct">搜索</el-button>
        </template>
      </el-input>
      <img src="/src/assets/avatar.jpg" alt="avatar" class="avatar" @click="drawerVisible = true">
    </div>

    <!-- 用户抽屉 -->
    <el-drawer
        v-model="drawerVisible"
        title="个人菜单"
        direction="rtl"
        size="300px"
    >
      <el-menu default-active="1" class="el-menu-vertical-demo" @select="handleDrawerSelect">
        <el-menu-item index="profile">个人中心</el-menu-item>
        <el-menu-item index="product">管理农产品</el-menu-item>
        <el-menu-item index="orders">订单处理</el-menu-item>
        <el-menu-item index="reports">销售报表</el-menu-item>
        <el-menu-item index="training">培训与活动</el-menu-item>
        <el-menu-item index="feedback">消费者反馈</el-menu-item>
        <el-menu-item index="subsidy">支持与补贴管理</el-menu-item>
        <el-menu-item index="back">返回</el-menu-item>
      </el-menu>
      <p>用户名：示例用户</p>
      <p>角色：农户</p>
      <el-button type="danger" @click="isLoggedIn = false; loginSectionView = true;router.push('/login')">退出登录</el-button>
    </el-drawer>
<router-view  style="padding:100px"></router-view>
    <!-- 广告轮播 -->
<!--    <el-carousel :interval="4000" type="card" height="300px" v-if="isLoggedIn">-->
<!--      <el-carousel-item v-for="item in adverts" :key="item.advertid">-->
<!--        <img :src="item.imageUrl" class="carousel-image" />-->
<!--      </el-carousel-item>-->
<!--    </el-carousel>-->

<!--    &lt;!&ndash; 公告栏 &ndash;&gt;-->
<!--    <div class="section" v-if="isLoggedIn">-->
<!--      <h2 class="section-title">📢 最新公告</h2>-->
<!--      <el-row :gutter="20">-->
<!--        <el-col :span="8" v-for="item in announcements" :key="item.aid">-->
<!--          <el-card shadow="hover" class="card">-->
<!--            <div>{{ item.atitle }}</div>-->
<!--          </el-card>-->
<!--        </el-col>-->
<!--      </el-row>-->
<!--    </div>-->

<!--    &lt;!&ndash; 活动推荐 &ndash;&gt;-->
<!--    <div class="section" v-if="isLoggedIn">-->
<!--      <h2 class="section-title">🎉 精彩活动</h2>-->
<!--      <el-row :gutter="20">-->
<!--        <el-col :span="8" v-for="item in promotions" :key="item.promotionid">-->
<!--          <el-card shadow="hover" class="card">-->
<!--            <h3>{{ item.promotionname }}</h3>-->
<!--            <p>{{ item.description }}</p>-->
<!--          </el-card>-->
<!--        </el-col>-->
<!--      </el-row>-->
<!--    </div>-->

<!--    &lt;!&ndash; 登录注册区块 &ndash;&gt;-->
    <!-- 登录后农产品展示 -->
    <div class="section" v-if="isLoggedIn">
      <h2 class="section-title">🛒 热销农产品</h2>
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in products" :key="item.id">
          <el-card
              shadow="hover"
              class="product-card clickable-card"
          >
            <el-image
                :src="item.image"
                fit="cover"
                style="width: 100%; height: 150px;"
            />
            <div class="info">
              <div><strong>农产品id：</strong>{{ item.productid }}</div>
              <div><strong>名称：</strong>{{ item.productname }}</div>
              <div><strong>价格：</strong>￥{{ item.price }}</div>
              <div><strong>库存：</strong>{{ item.stock }}</div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

  </div>
</template>

<style scoped>
.home-container {
  padding: 20px;
  background-image: url('src/assets/jpg1.png'); /* 背景图 */
  background-size: cover;
  background-position: center;
  min-height: 100vh;
}
.view{
  background-color: transparent
}
/* 顶部导航栏样式 */
.top-navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  display: flex;
  align-items: center;
  background: white;
  padding: 10px 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}


.logo {
  height: 50px;
}

.search-box {
  flex: 1;
  margin: 0 20px;
}

.avatar {
  height: 40px;
  width: 40px;
  border-radius: 50%;
  cursor: pointer;
}

.carousel-image {
  width: 100%;
  height: 300px;
  object-fit: cover;
  border-radius: 10px;
}

.section {
  margin-top: 40px;
}

.section-title {
  font-size: 24px;
  margin-bottom: 20px;
  font-weight: bold;
  color: #333;
}

.card {
  min-height: 200px;
  padding: 20px;
  text-align: center;
}

.login-section {
  margin-top: 50px;
}

.login-buttons {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  gap: 20px;
}
</style>
