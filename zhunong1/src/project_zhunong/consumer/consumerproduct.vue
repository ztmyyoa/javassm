<template>
  <el-card>
    <h2>浏览农产品</h2>
    <el-input
        v-model="search"
        placeholder="搜索产品名称"
        class="mb-4"
        clearable
    />

    <el-row :gutter="20">
      <el-col
          v-for="(product, index) in filteredProducts"
          :key="index"
          :span="6"
      >
        <el-card
            shadow="hover"
            class="product-card clickable-card"
            @click="viewDetail(product.productid)"
        >
          <el-image
              :src="product.image"
              fit="cover"
              style="width: 100%; height: 150px;"
          />
          <div class="info">
            <div><strong>农产品id：</strong>{{ product.productid }}</div>
            <div><strong>名称：</strong>{{ product.productname }}</div>
            <div><strong>价格：</strong>￥{{ product.price }}</div>
            <div><strong>库存：</strong>{{ product.stock }}</div>
            <div><strong>类型：</strong>{{ product.type }}</div>
            <div><strong>农户ID：</strong>{{ product.farmerId }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import router from '@/router.js'

const search = ref('')
const products = ref([])

const productdata = async () => {
  try {
    const res = await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/product/consumerall')
    const response = await res.json()
    console.log('农产品数据:', response)
    if (response) {
      products.value = response
    } else {
      console.error('返回的数据格式错误:', response)
      products.value = []
    }
  } catch (error) {
    console.error('获取农产品列表失败：', error)
    products.value = []
  }
}

onMounted(() => {
  productdata()
})

const filteredProducts = computed(() =>
    products.value.filter(p =>
        p.productname.includes(search.value)
    )
)

const viewDetail = (id) => {
  router.push(`/consumer/productDetail/${id}`)
}
</script>

<style scoped>
.mb-4 {
  margin-bottom: 20px;
}

.product-card {
  margin-bottom: 20px;
  cursor: pointer;
  transition: transform 0.2s;
}
.product-card:hover {
  transform: scale(1.02);
}

.info {
  margin-top: 10px;
  font-size: 14px;
  line-height: 1.6;
}
</style>
