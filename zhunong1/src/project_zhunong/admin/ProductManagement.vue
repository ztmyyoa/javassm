<script setup>
import {ref, computed, onMounted} from 'vue'
import axios from "axios";
import {ElMessage} from "element-plus";
const products = ref([])

const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const dialogVisible = ref(false)
const dialogTitle = ref('新增农产品')
const form = ref({
  productid: null,
  productname: '',
  image: '',
  farmerId: '',
  type: '',
  price: '',
  stock: '',
  description: ''
})

// 过滤后的列表
const filteredproducts = computed(() => {
  const keyword = searchKeyword.value.trim().toLowerCase()
  return products.value.filter(product =>
      product.productid?.toString().includes(keyword) ||
      product.productname?.toLowerCase().includes(keyword) ||
      product.type?.toLowerCase().includes(keyword)
  )
})

const fetchproduct = async () => {
  try {
    const res = await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/product/all')
    const response = await res.json()
    console.log('用户数据:', response)
    if(response) {
      products.value = response
      console.log('农产品数据:', products)
    }else{
      console.error('返回的数据格式错误:', response)
      products.value = []
    }
  }
  catch (error) {
    console.error('获取农产品列表失败：', error)
    products.value = []
  }
}
onMounted(()=>{
  fetchproduct()
})
// 当前页数据
const pagedproducts = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return filteredproducts.value.slice(start, end)
})

const handlePageChange = (page) => {
  currentPage.value = page
}

// 新增/编辑打开弹窗
const openDialog = (type, row = null) => {
    dialogTitle.value = '编辑农产品'
    form.value = { ...row }
  dialogVisible.value = true
}

// 提交保存
const handleSubmit = async () => {
  try {
    if (dialogTitle.value === '编辑农产品') {
      console.log('编辑农产品:', form.value)
      const res = await axios.put('http://localhost:8080/spring_mvc_mysql_vue_war/product/update', form.value)
      if (res.data) {
        ElMessage.success('更新成功')
        dialogVisible.value = false
        await  fetchproduct()
      } else {
        ElMessage.error(res.data.message || '更新失败')
      }
    }
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败，请检查网络或服务器状态')
  }
}

// 删除农产品
// const handleDelete =async (id) => {
//   try {
//     console.log('删除农产品:', id)
//     const res = await axios.delete(`http://localhost:8080/spring_mvc_mysql_vue_war/product/delete/${id}`)
//     console.log('返回的数据:', res.data)
//     if (id) {
//       ElMessage.success('删除成功')
//       products.value = products.value.filter(f => f.userid !== id)
//     } else {
//       ElMessage.error(res.data.message || '删除失败')
//     }
//   } catch (error) {
//     console.error('删除失败:', error)
//   }
//   // products.value = products.value.filter(f => f.userid !== id)
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
          placeholder="搜索农产品姓名"
          style="width: 1000px;"
          @input="handleSearch"
          clearable
      />
    </div>

    <el-table :data="pagedproducts" border stripe style="width: 100%">
      <el-table-column prop="productid" label="ID" width="80" />
      <el-table-column prop="productname" label="产品名" />
      <el-table-column prop="image" label="图片" >
      <template #default="scope">
        <el-image
            style="width: 60px; height: 60px; border-radius: 50%"
            :src="scope.row.image"
            fit="cover"
            :preview-src-list="[scope.row.image]"
        />
      </template>
      </el-table-column>
      <el-table-column prop="farmerId" label="农户id" />
      <el-table-column prop="type" label="种类" />
      <el-table-column prop="price" label="价格" />
      <el-table-column prop="stock" label="库存" />
      <el-table-column prop="description" label="描述" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openDialog('edit', scope.row)">编辑</el-button>
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
          :total="filteredproducts.length"
          @current-change="handlePageChange"
      />
    </div>

    <!-- 弹窗表单 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="农产品名">
          <el-input v-model="form.productname" />
        </el-form-item>
        <el-form-item label="图片">
          <el-input v-model="form.image" />
        </el-form-item>
        <el-form-item label="种类">
          <el-input v-model="form.type" />
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model="form.price" />
        </el-form-item>
        <el-form-item label="库存">
          <el-input v-model="form.stock" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" />
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
