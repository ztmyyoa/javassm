<script setup>
import {ref, computed, onMounted} from 'vue'

const reviews = ref([
])

const currentPage = ref(1)
const pageSize = ref(5)

const fetchReviews = async () => {
  try {

    const response = await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/review/all')
    const data = await response.json()
    console.log('评论数据', data)
    if (data) {
      reviews.value = data
    } else {
      console.log('评论数据格式错误', data)
      reviews.value = []
    }
  }catch (error){
    console.log('获取评论数据失败', error)
    reviews.value = []
  }
}
onMounted(()=>{
  fetchReviews()
})
const paginatedReviews = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return reviews.value.slice(start, end)
})
</script>

<template>
  <div class="page-container">
    <el-table :data="paginatedReviews" border stripe>
      <el-table-column prop="reviewid" label="投诉ID" />
      <el-table-column prop="consumerid" label="消费者ID" />
      <el-table-column prop="productid" label="产品ID" />
      <el-table-column prop="reviewcontent" label="内容" />
      <el-table-column prop="reviewtime" label="投诉时间" />
      <el-table-column prop="rating" label="评分" />
    </el-table>

    <div class="pagination-container">
      <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          :total="reviews.length"
          layout="total, sizes, prev, pager, next, jumper"
      />
    </div>
  </div>
</template>

<style scoped>
.page-container { padding: 20px; }
.pagination-container { margin-top: 20px; text-align: center; }
</style>
