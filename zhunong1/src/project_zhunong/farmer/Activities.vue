<script setup>
import {ref, computed, onMounted} from 'vue'
import axios from "axios";
import {ElMessage} from "element-plus";
import {useStore} from "vuex";
const store = useStore()
// 获取用户名和密码
const farmerusername = store.getters.getUsername;
const farmerpassword = store.getters.getPassword;
console.log(farmerusername);
console.log(farmerpassword);
const as = ref([])
const as1 = ref([])
const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const dialogVisible = ref(false)
const dialogpromotionname = ref('新增活动')
const form = ref({
  promotionid: null,
  promotionname: '',
  description: '',
  startTime: '',
  endTime: '',
  farmerid: '',

})

// 过滤后的列表
const filteredas = computed(() => {
  const keyword = searchKeyword.value.trim().toLowerCase()
  return as.value.filter(a =>
      a.promotionid?.toString().includes(keyword) ||
      a.promotionname?.toLowerCase().includes(keyword)||
      a.startTime?.toLowerCase().includes(keyword)||
      a.endTime?.toLowerCase().includes(keyword)

  )
})
const filteredas1 = computed(() => {
  const keyword = searchKeyword.value.trim().toLowerCase()
  return as1.value.filter(a =>
      a.promotionid?.toString().includes(keyword) ||
      a.promotionname?.toLowerCase().includes(keyword)||
      a.startTime?.toLowerCase().includes(keyword)||
      a.endTime?.toLowerCase().includes(keyword)

  )
})
const fetcha = async () => {
  try {
    const res = await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/promotion/all')
    const res1=  await axios.get(`http://localhost:8080/spring_mvc_mysql_vue_war/promotion/getfarmerpro?username=${farmerusername}`)
    const response = await res.json()
    const response1 = await res1.data
    console.log('活动数据:', response)
    console.log('活动数据1:', response1)
    if(response) {
      as.value = response
      as1.value= response1
      console.log('活动数据:', as)
      console.log('活动数据1:', as1)
    }else{
      console.error('返回的数据格式错误:', response)
      console.log('活动数据1:', response1)
      as.value = []
    }
  }
  catch (error) {
    console.error('获取活动列表失败：', error)
    as.value = []
  }
}
onMounted(()=>{
  fetcha()
})
// 当前页数据
const pagedas = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return filteredas.value.slice(start, end)
})
const pagedas1 = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return filteredas1.value.slice(start, end)
})
const handlePageChange = (page) => {
  currentPage.value = page
}

// 新增/编辑打开弹窗
const handapply = async (row = null) => {
  try {
  form.value = { ...row }
  const farmerdata= await axios.get(`http://localhost:8080/spring_mvc_mysql_vue_war/farmer/getfamerprofile?username=${farmerusername}`);
  form.value.farmerid=farmerdata.data.userid
  console.log('传入:',form.value)
  const res = await axios.post(`http://localhost:8080/spring_mvc_mysql_vue_war/promotion/addfarmerpro`, form.value)
  console.log('返回的数据:', res.data)
  if (res.data) {
    ElMessage.success('新增成功')
    dialogVisible.value = false
    await fetcha()
  } else {
    ElMessage.error(res.data.message || '新增失败')
  }} catch (error){
    console.error('获取活动列表失败：', error)
    ElMessage.error(res.data.message || '新增失败')
  }

}

// 提交保存
const handleSubmit = async (row) => {

}

// 删除活动
// const handleDelete =async (id) => {
//   try {
//     console.log('删除活动:', id)
//     const res = await axios.delete(`http://localhost:8080/spring_mvc_mysql_vue_war/promotion/delete/${id}`)
//     console.log('返回的数据:', res.data)
//     if (id) {
//       ElMessage.success('删除成功')
//       as.value = as.value.filter(f => f.promotionid !== id)
//     } else {
//       ElMessage.error(res.data.message || '删除失败')
//     }
//   } catch (error) {
//     console.error('删除失败:', error)
//   }
//   // as.value = as.value.filter(f => f.userid !== id)
// }

// 搜索时回到第1页
const handleSearch = () => {
  currentPage.value = 1
}
</script>

<template>
  <div class="page-container">
    <div class="toolbar">
      <el-input
          v-model="searchKeyword"
          placeholder="搜索活动"
          style="width: 1000px;"
          @input="handleSearch"
          clearable
      />
    </div>

    <el-table :data="pagedas" border stripe style="width: 100%">
      <el-table-column prop="promotionid" label="ID" width="80" />
      <el-table-column prop="promotionname" label="活动标题" />
      <el-table-column prop="description" label="内容" />
      <el-table-column prop="startTime" label="开始时间" />
      <el-table-column prop="endTime" label="结束时间" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" type="primary" @click="handapply( scope.row)">报名</el-button>
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
          :total="filteredas.length"
          @current-change="handlePageChange"
      />
    </div>

    <el-table :data="pagedas1" border stripe style="width: 100%">
      <el-table-column prop="promotionid" label="ID" width="80" />
      <el-table-column prop="promotionname" label="活动标题" />
      <el-table-column prop="description" label="内容" />
      <el-table-column prop="startTime" label="开始时间" />
      <el-table-column prop="endTime" label="结束时间" />
      <el-table-column prop="farmerid" label="农户ID" />
    </el-table>

    <div class="pagination-container">
      <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          :total="filteredas.length"
          @current-change="handlePageChange"
      />
    </div>
    <!-- 弹窗表单 -->
  </div>
</template>

<style scoped>
.page-container {
  padding: 20px;
}
.toolbar {
  margin-bottom: 20px;
  display: flex;
  justify-content: center;
  gap: 10px;
}
.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
