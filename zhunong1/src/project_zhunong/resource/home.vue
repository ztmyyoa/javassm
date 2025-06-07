<script setup>
import {onMounted, ref} from 'vue'
import Login from '/src/project_zhunong/other/login.vue'  // 假设你有 Login 组件
import Register from '/src/project_zhunong/other/register.vue'
import router from "@/router.js";  // 假设你有 Register 组件
const loginSectionView = ref('welcome') // 初始显示的是欢迎页面
const products = ref([])
// 切换按钮方法
const goLogin = () => {
  router.push('/login') // 切换到登录页面
}

const goRegister = () => {
  router.push('/register')// 切换到注册页面
}

const adverts = ref([])


const announcements = ref([])

const promotions = ref([])

const getdata = async () => {
  // 获取数据逻辑
  try {
    const adres = await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/ad/all');
    const addata=await adres.json();
    const annres = await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/a/all');
    const anndata = await annres.json();
    const prores =await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/promotion/all');
    const prodata=await prores.json();
    const productres=await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/product/gettop');
    const productdata=await productres.json();
    if (addata && anndata && prodata) {
      adverts.value = addata;
      announcements.value = anndata;
      promotions.value = prodata;
      products.value = productdata;
      console.log('广告数据:', adverts);
      console.log('公告数据:', announcements);
      console.log('活动数据:', promotions);
      console.log('商品数据:', products);
    } else {
      console.error('返回的数据格式错误:', addata);
      adverts.value = [];
    }
  }catch (error){
    console.error('获取广告列表失败：', error);
  }
}
onMounted(()=>{
  getdata()
})
</script>

<template>
  <div class="home-container">

    <!-- 广告轮播 -->
    <el-carousel :interval="4000" type="card" height="800px">
      <el-carousel-item v-for="item in adverts" :key="item.advertid">
        <a :href="item.linkUrl" target="_blank" rel="noopener noreferrer">
        <img :src="item.imageUrl" class="carousel-image" />
        </a>
      </el-carousel-item>
    </el-carousel>

    <!-- 公告栏 -->
    <div class="section">
      <h2 class="section-title">📢 最新公告</h2>
      <el-row :gutter="20" class="carddata" >
        <el-col :span="8" v-for="item in announcements" :key="item.aid">
          <el-card shadow="hover" class="card">
            <div>{{ item.atitle }}</div>
            <div>{{ item.acontent }}</div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 活动推荐 -->
    <div class="section">
      <h2 class="section-title">🎉 精彩活动</h2>
      <el-row :gutter="20" class="carddata">
        <el-col :span="8" v-for="item in promotions" :key="item.promotionid">
          <el-card shadow="hover" class="card">
            <h3>{{ item.promotionname }}</h3>
            <p>{{ item.description }}</p>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 登录注册区块 (固定在首页下面) -->
    <div class="section login-section">
      <el-card class="card">

        <!-- 欢迎页面 -->
        <div v-if="loginSectionView">
          <h3>欢迎来到扶贫云超市！</h3>
          <p>请先登录或注册，以享受更多服务！</p>
          <div class="login-buttons">
            <el-button type="primary" @click="goLogin">登录</el-button>
            <el-button type="success" @click="goRegister">注册</el-button>
          </div>
        </div>

      </el-card>
    </div>
    <router-view></router-view>
    <!-- 精选农产品（未登录底部展示） -->
    <div class="section" >
      <h2 class="section-title">🍀 精选农产品</h2>
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in products.slice(0, 4)" :key="item.id">
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
  background-image: url('src/assets/jpg1.png'); /* 替换为你的背景图片路径 */
  background-size: cover;
  background-position: center;
}

.carousel-image {
  width: 100%;
  height: 800px;
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
  margin-top: 10px;
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
.carddata{
  justify-content: center
}
.back-buttons {
  margin-top: 20px;
  text-align: center;
}
</style>
