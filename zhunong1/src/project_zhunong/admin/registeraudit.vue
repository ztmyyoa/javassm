<script setup>
import {ref, computed, onMounted} from 'vue'
import { ElMessage } from 'element-plus'
import axios from "axios";

// 模拟用户数据
const users = ref([])

const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(10)

// 只显示待审核(restatus = 0)的用户
const fetchauditUser = async () => {
  try {
    const res = await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/user/getregister')
    const response = await res.json()

    // 确保 response.data 是一个数组
    if (response.data && Array.isArray(response.data)) {
      users.value = response.data
      console.log('用户数据:', users.value)
    } else {
      console.error('返回的数据格式错误:', response)
      users.value = []  // 如果返回的不是数组，设置为空数组
    }
  } catch (error) {
    console.error('获取用户数据失败:', error)
    users.value = []  // 错误时清空数据
  }
}
onMounted(() => {
  fetchauditUser()
})
// 分页数据
const pagedUsers = computed(() => {
  // 如果 users.value 不是数组，返回空数组
  if (!Array.isArray(users.value)) {
    return []
  }
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return users.value.slice(start, end)
})

// 分页变化
const handlePageChange = (page) => {
  currentPage.value = page
}

// 审核通过
const approveUser = async (userid) => {
  try {
    console.log("请求的 userid:", userid)  // 打印 user_id，确保它不是 null 或 undefined
    const res = await axios.post(`http://localhost:8080/spring_mvc_mysql_vue_war/user/approve`, { userid })
    console.log(res.data); // 输出返回的数据，便于调试
    console.log(res.data.code);
    if (res.data ) {  // 确保返回的对象有 code 字段
      ElMessage.success('已通过该用户注册。')
      await fetchauditUser()  // 刷新数据
    } else {
      ElMessage.error('审核请求失败：' + (res.data.msg || '未知错误'))
    }
  } catch (error) {
    ElMessage.error('审核请求失败。')
    console.error('审核请求失败:', error)
  }
}


// 审核拒绝
const rejectUser =async (userid) => {
  try {

    console.log("请求的 userid:", userid)  // 打印 user_id，确保它不是 null 或 undefined
    const res = await axios.post(`http://localhost:8080/spring_mvc_mysql_vue_war/user/reject`, { userid })
    console.log(res.data); // 输出返回的数据，便于调试
    console.log(res.data.code);
    if (res.data ) {  // 确保返回的对象有 code 字段
      ElMessage.success('已拒绝该用户注册。')
      await fetchauditUser()  // 刷新数据
    } else {
      ElMessage.error('审核请求失败：' + (res.data.msg || '未知错误'))
    }
  } catch (error) {
    ElMessage.error('审核请求失败。')
    console.error('审核请求失败:', error)
  }
}
</script>

<template>
  <div class="page-container">
    <div class="toolbar">
      <el-input
          v-model="searchKeyword"
          placeholder="搜索用户名"
          style="width: 300px;"
          clearable
      />
    </div>

    <el-table :data="pagedUsers" stripe border style="width: 100%">
      <el-table-column prop="userid" label="用户ID" width="80" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="phone" label="电话" />
      <el-table-column prop="usertype" label="角色" width="100" />
      <el-table-column prop="regtime" label="注册时间" width="150" />
      <el-table-column prop="restatus" label="状态" width="100" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" type="success" @click="approveUser(scope.row.userid)">通过</el-button>
          <el-button size="small" type="danger" @click="rejectUser(scope.row.userid)">拒绝</el-button>
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
          :total="users.length"
          @current-change="handlePageChange"
      />
    </div>
  </div>
</template>

<style scoped>
.page-container {
  padding: 20px;
}

.toolbar {
  margin-bottom: 20px;
  display: flex;
  justify-content: flex-start;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
