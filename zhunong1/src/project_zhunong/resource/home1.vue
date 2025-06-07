<script setup>
import {onMounted, ref} from 'vue'
import router from "@/router.js";
const adverts = ref([
 ])

const announcements = ref([
])

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
    if (addata && anndata && prodata) {
      adverts.value = addata;
      announcements.value = anndata;
      promotions.value = prodata;
      console.log('广告数据:', adverts);
      console.log('公告数据:', announcements);
      console.log('活动数据:', promotions);
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
  <div class="home-containerone">

    <!-- 广告轮播 -->
    <el-carousel :interval="4000" type="card" height="800px">
      <el-carousel-item v-for="item in adverts" :key="item.advertid">
        <img :src="item.imageUrl" class="carousel-image" />
      </el-carousel-item>
    </el-carousel>

    <!-- 公告栏 -->
    <div class="section">
      <h2 class="section-title">📢 最新公告</h2>
      <el-row :gutter="20" class="carddata">
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
      <h2 class="section-title" >🎉 精彩活动</h2>
      <el-row :gutter="20" class="carddata">
        <el-col :span="8" v-for="item in promotions" :key="item.promotionid">
          <el-card shadow="hover" class="card">
            <h3>{{ item.promotionname }}</h3>
            <p>{{ item.description }}</p>
          </el-card>
        </el-col>
      </el-row>
    </div>

  </div>
</template>

<style scoped>
.home-containerone {
  background-color: transparent;
  padding: 20px;
}

.carousel-image {
  width: 100%;
  height: 800px;
  object-fit: cover;
  border-radius: 10px;
}
.card {
  min-height: 200px;
  padding: 20px;
  margin-top: 10px;
  text-align: center;
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

.carddata{
  justify-content: center
}

</style>
