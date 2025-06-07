<script setup>
import {computed, onMounted, ref} from 'vue'
import {useStore} from "vuex";
import axios from "axios";
import {ElMessage} from "element-plus";
const store = useStore()
// 获取用户名和密码
const consumerusername = store.getters.getUsername;
const consumerpassword = store.getters.getPassword;
console.log(consumerusername);
console.log(consumerpassword);
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
    const res = await axios.get(`http://localhost:8080/spring_mvc_mysql_vue_war/review/getconsumerreview?username=${consumerusername}`);
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
const addReview = () => {
  dialogVisible.value = true
  // form.value = {
  //   reviewid: '',
  //   consumerid: '',
  //   productid: '',
  //   farmerid: '', // 默认使用登录农户账号
  //   reviewcontent: '',
  //   reply: '',
  //   rating: 0,
  //   reviewtime: ''
  // }
}
const submitReview = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/spring_mvc_mysql_vue_war/consumer/getconsumerprofile?username=${consumerusername}`)
    console.log('获取用户数据成功:', response.data)
    form.value.consumerid = await response.data.userid;
    console.log('提交评论数据:', form.value)
    const res = await axios.post(`http://localhost:8080/spring_mvc_mysql_vue_war/review/add`, form.value)
    if (res) {
      ElMessage.success('评论添加成功')
      dialogVisible.value = false
      fetchFeedback() // 刷新评论列表
    } else {
      ElMessage.error('添加失败')
    }
  } catch (error) {
    console.error('评论添加出错:', error)
    ElMessage.error('评论添加失败')
  }
}

</script>

<template>
  <div class="feedback-container">

    <el-card>
      <h2>消费者反馈</h2>
      <div class="toolbar">
        <el-button type="primary" @click="addReview">新增评论</el-button>
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
      <el-dialog v-model="dialogVisible" title="新增评论" width="500px">
        <el-form :model="form" label-width="100px">
          <el-form-item label="产品ID">
            <el-input v-model="form.productid" placeholder="请输入产品ID" />
          </el-form-item>
          <el-form-item label="农户ID">
            <el-input v-model="form.farmerid" placeholder="请输入农户ID" />
          </el-form-item>
          <el-form-item label="评论内容">
            <el-input type="textarea" v-model="form.reviewcontent" placeholder="请输入评论内容" />
          </el-form-item>
          <el-form-item label="评分">
            <el-rate v-model="form.rating" show-score />
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitReview">提交</el-button>
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
