<script setup>
import {computed, onMounted, ref} from 'vue'
import {useStore} from "vuex";
import axios from "axios";
import {ElMessage} from "element-plus";
const store = useStore()
// 获取用户名和密码
const farmerusername = store.getters.getUsername;
const farmerpassword = store.getters.getPassword;
console.log(farmerusername);
console.log(farmerpassword);
const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(5)
const feedbackList = ref([])
const dialogVisible = ref(false)
const form = ref({
  reviewid: '',
  consumerid: '',
  productid: '',
  farmerid: '',
  reviewcontent: '',
  reply: '',
  rating: '',
  reviewtime: ''
})
const filterreview = computed(() => {
  const keyword = searchKeyword.value.trim().toLowerCase()
  return feedbackList.value.filter(a =>
      a.reviewid?.toString().includes(keyword) ||
      a.consumerid?.toString().includes(keyword) ||
      a.productid?.toString().includes(keyword)
  )
})
const fetchFeedback = async () => {
  try {
    console.log('开始获取农户反馈数据...');
    const res = await axios.get(`http://localhost:8080/spring_mvc_mysql_vue_war/review/getfarmerreview?username=${farmerusername}`);
    const response = await res.data;
    console.log('农户反馈数据:', response);
    if(response){
      feedbackList.value = response;
      console.log('农户反馈数据1:', feedbackList.value);
    }else {
      console.error('返回的数据格式错误:', response);
      feedbackList.value = [];
    }
  }catch (error){
    console.error('请求出错:', error);
    feedbackList.value = [];
  }
};
onMounted(()=>{
  fetchFeedback()
})
const pagedas = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return filterreview.value.slice(start, end)
})
const handlePageChange = (page) => {
  currentPage.value = page
}
const handleSearch = () => {
  currentPage.value = 1
}
const handleReply = (row) => {
    dialogVisible.value = true
  form.value={...row}

}
const submitReply = async () => {
console.log('提交回复数据:', form.value)
  try{
    const res = await axios.put(`http://localhost:8080/spring_mvc_mysql_vue_war/review/update`, form.value)
    if (res.data) {
      ElMessage.success('回复成功')
      dialogVisible.value = false
      await fetchFeedback()
    } else {
      ElMessage.error(res.data.message || '回复失败')
    }
  }catch (error){
    console.error('提交回复出错:', error)
    ElMessage.error('提交回复失败，请检查网络或服务器状态')
  }
}
</script>

<template>
  <div class="feedback-container">

    <el-card>
      <h2>消费者反馈</h2>
      <div class="toolbar">
        <el-input
            v-model="searchKeyword"
            placeholder="搜索"
            style="width: 1000px;"
            @input="handleSearch"
            clearable
        />
      </div>
      <el-table :data="pagedas" style="width: 100%" stripe>
        <el-table-column prop="reviewid" label="活动编号" width="100" />
        <el-table-column prop="consumerid" label="消费者id" width="100" />
        <el-table-column prop="productid" label="商品Id" width="100" />
        <el-table-column prop="farmerid" label="农户id" width="100" />
        <el-table-column prop="reviewcontent" label="评论内容" />
        <el-table-column prop="reply" label="回复" />
        <el-table-column prop="rating" label="评分" width="100">
          <template #default="{ row }">
            <el-rate :model-value="row.rating" disabled />
          </template>
        </el-table-column>

        <el-table-column prop="reviewtime" label="日期" width="160" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button type="primary" @click="handleReply(row)">回复</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <el-pagination
            background
            layout="total, sizes, prev, pager, next, jumper"
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[5, 10, 20]"
            :total="filterreview.length"
            @current-change="handlePageChange"
        />
      </div>
      <el-dialog v-model="dialogVisible" title="回复消费者" width="500px">
        <el-form :model="form" label-width="80px">
          <el-form-item label="评论内容">
            <el-input type="textarea" v-model="form.reviewcontent" disabled />
          </el-form-item>
          <el-form-item label="回复内容">
            <el-input type="textarea" v-model="form.reply" placeholder="请输入回复内容" />
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitReply">提交</el-button>
        </template>
      </el-dialog>
    </el-card>
  </div>
</template>

<style scoped>
.feedback-container {
  margin: 20px;
}
.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
