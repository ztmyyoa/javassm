<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { ElMessage } from 'element-plus'
import {useStore} from "vuex";
const store = useStore()
const route = useRoute()
const router = useRouter()
const product = ref({})
const quantity = ref(1)
const form = ref({
  cartid: '',
  consumerid: '',
  productid: '',
  quantity: 0,
  totalprice: 0,
})
const fetchProductDetail = async () => {
  try {
    const res = await axios.get(
        `http://localhost:8080/spring_mvc_mysql_vue_war/product/${route.params.id}`
    )
    product.value = res.data
    console.log('获取产品详情成功：', res.data)
  } catch (error) {
    console.error('获取产品详情失败：', error)
    ElMessage.error('获取产品详情失败')
    router.push('/') // 出现错误返回首页或商品列表页
  }
}
onMounted(() => {
  fetchProductDetail()
})
</script>
<template>
  <el-card style="margin-top: 60px">
    <el-row>
      <el-col :span="10">
        <el-image
            :src="product.image"
            fit="cover"
            style="width: 100%; height: 500px"
        />
      </el-col>

      <el-col :span="14" style="padding-left: 20px">
        <p><strong>名称：</strong>{{product.productname}}</p>
        <p><strong>价格：</strong>￥{{ product.price }}</p>
        <p><strong>库存：</strong>{{ product.stock }}</p>
        <p><strong>类型：</strong>{{ product.type }}</p>
        <p><strong>农户ID：</strong>{{ product.farmerId }}</p>
        <p><strong>描述：</strong>{{ product.description || '暂无描述' }}</p>

      </el-col>
    </el-row>
  </el-card>
</template>



<style scoped>
.mt-3 {
  margin-top: 20px;
}
</style>


