<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import router from "@/router.js";
import {useStore} from "vuex";
import axios from "axios";
const store = useStore()
// 获取用户名和密码
const username = store.getters.getUsername;
const password = store.getters.getPassword;
console.log(username);
console.log(password);
const route = useRoute()
const searchQuery = ref(route.query.keyword || '')
const products = ref([])
const userdata=ref([
])
const fetchResults = async () => {
  try {


    const res = await fetch(`http://localhost:8080/spring_mvc_mysql_vue_war/product/search?keyword=${encodeURIComponent(searchQuery.value)}`)
    const data = await res.json()
    if (Array.isArray(data) && data.length > 0) {
      products.value = data
    } else {
      ElMessage.info('未找到相关商品')
    }
  } catch (err) {
    console.error(err)
    ElMessage.error('搜索失败')
  }
}
const viewDetail = async (id) => {
 try{
  const userres= await axios.get(`http://localhost:8080/spring_mvc_mysql_vue_war/user/username?username=${username}`)
  const userdata= await userres.data
  console.log('用户数据',userdata)
   if(userdata.usertype==='消费者') {
     await router.push(`/consumer/productDetail/${id}`)
   }else{

     await router.push(`/farmer/productDetail/${id}`)
   }
 }catch (error){
   console.error(error)
 }


}
onMounted(() => {
  fetchResults()
})
</script>

<template>
  <div class="search-results">
    <h2>🔍 搜索结果：{{ searchQuery }}</h2>
    <el-row :gutter="20" style="justify-content: center">
      <el-col :span="6" v-for="item in products" :key="item.productid">
        <el-card shadow="hover" class="product-card"  @click="viewDetail(item.productid)">
          <el-image :src="item.image" fit="cover" style="width: 100%; height: 150px;" />
          <div class="info">
            <div><strong>ID：</strong>{{ item.productid }}</div>
            <div><strong>名称：</strong>{{ item.productname }}</div>
            <div><strong>价格：</strong>￥{{ item.price }}</div>
            <div><strong>库存：</strong>{{ item.stock }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
.search-results {
  padding: 20px;
}
</style>
