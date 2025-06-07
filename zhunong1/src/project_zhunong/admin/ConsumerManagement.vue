<script setup>
import {ref, computed, onMounted} from 'vue'
import axios from "axios";
import {ElMessage} from "element-plus";
const counsumers = ref([])

const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const dialogVisible = ref(false)
const dialogTitle = ref('新增消费者')
const form = ref({
  userid: null,
  name: '',
  avatar: '',
  username: '',
  email: '',
  phone: '',
  address: '',
})

// 过滤后的列表
const filteredcounsumers = computed(() => {
  const keyword = searchKeyword.value.trim().toLowerCase()
  return counsumers.value.filter(counsumer =>
      counsumer.userid?.toString().includes(keyword) ||
      counsumer.name?.toLowerCase().includes(keyword) ||
      counsumer.username?.toLowerCase().includes(keyword) ||
      counsumer.phone?.includes(keyword) ||
      counsumer.address?.toLowerCase().includes(keyword)
  )
})

const fetchcounsumer = async () => {
  try {
    const res = await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/consumer/all')
    const response = await res.json()
    console.log('用户数据:', response)
    if(response) {
      counsumers.value = response
      console.log('消费者数据:', counsumers)
    }else{
      console.error('返回的数据格式错误:', response)
      counsumers.value = []
    }
  }
  catch (error) {
    console.error('获取消费者列表失败：', error)
    counsumers.value = []
  }
}
onMounted(()=>{
  fetchcounsumer()
})
// 当前页数据
const pagedcounsumers = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return filteredcounsumers.value.slice(start, end)
})

const handlePageChange = (page) => {
  currentPage.value = page
}

// 新增/编辑打开弹窗
const openDialog = (type, row = null) => {
  if (type === 'create') {
    dialogTitle.value = '新增消费者'
    form.value = { userid: null, name: 'A', avatar: '', username: 'aaba', email: '1', phone: '2', address: 'b' }
  } else if (type === 'edit' && row) {
    dialogTitle.value = '编辑消费者'
    form.value = { ...row }
  }
  dialogVisible.value = true
}

// 提交保存
const handleSubmit = async () => {
  try {
    if (dialogTitle.value === '新增消费者') {
      console.log('新增消费者:', form.value)
      const res = await axios.post('http://localhost:8080/spring_mvc_mysql_vue_war/consumer/add', form.value)
      console.log('返回的数据:', res.data)
      if (res.data) {
        ElMessage.success('新增成功')
        dialogVisible.value = false
        await fetchcounsumer()
      } else {
        ElMessage.error(res.data.message || '新增失败')
      }
    } else {
      console.log('编辑消费者:', form.value)
      const res = await axios.put('http://localhost:8080/spring_mvc_mysql_vue_war/consumer/update', form.value)
      if (res.data) {
        ElMessage.success('更新成功')
        dialogVisible.value = false
        await  fetchcounsumer()
      } else {
        ElMessage.error(res.data.message || '更新失败')
      }
    }
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败，请检查网络或服务器状态')
  }
}

// 删除消费者
const handleDelete =async (id) => {
  try {
    console.log('删除消费者:', id)
    const res = await axios.delete(`http://localhost:8080/spring_mvc_mysql_vue_war/consumer/delete/${id}`)
    console.log('返回的数据:', res.data)
    if (id) {
      ElMessage.success('删除成功')
      counsumers.value = counsumers.value.filter(f => f.userid !== id)
    } else {
      ElMessage.error(res.data.message || '删除失败')
    }
  } catch (error) {
    console.error('删除失败:', error)
  }
  // counsumers.value = counsumers.value.filter(f => f.userid !== id)
}

// 搜索时回到第1页
const handleSearch = () => {
  currentPage.value = 1
}
</script>

<template>
  <div class="page-container">
    <div class="toolbar">
      <el-button type="primary" @click="openDialog('create')">新增消费者</el-button>
      <el-input
          v-model="searchKeyword"
          placeholder="搜索消费者姓名"
          style="width: 1000px;"
          @input="handleSearch"
          clearable
      />
    </div>

    <el-table :data="pagedcounsumers" border stripe style="width: 100%">
      <el-table-column prop="userid" label="ID" width="80" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column label="头像" width="100">
        <template #default="scope">
          <el-image
              style="width: 60px; height: 60px; border-radius: 50%"
              :src="scope.row.avatar"
              fit="cover"
              :preview-src-list="[scope.row.avatar]"
          />
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="phone" label="电话" />
      <el-table-column prop="address" label="地址" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openDialog('edit', scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row.userid)">删除</el-button>
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
          :total="filteredcounsumers.length"
          @current-change="handlePageChange"
      />
    </div>

    <!-- 弹窗表单 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="姓名">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" />
        </el-form-item>

        <el-form-item label="地址">
          <el-input v-model="form.address" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
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
