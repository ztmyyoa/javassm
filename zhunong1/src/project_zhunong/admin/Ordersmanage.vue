<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from "axios";
import {ElMessage} from "element-plus";

const orders = ref([])
const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const dialogVisible = ref(false)
const dialogTitle = ref('处理订单')
const form = ref({
  orderid: null,
  consumerid: null,
  productid: null,
  farmerid: null,
  orderstatus: '',
  orderAmount: 0,
  orderTime: '',
  logisticsInfo: '',
  quantity: 0
})

// 从服务器获取订单数据
const fetchorder = async () => {
  try {
    const res = await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/orders/all')
    const response = await res.json()
    console.log('订单数据:', response)
    if (response) {
      orders.value = response
    } else {
      console.error('返回的数据格式错误:', response)
      orders.value = []
    }
  } catch (error) {
    console.error('获取订单列表失败：', error)
    orders.value = []
  }
}

onMounted(() => {
  fetchorder()
})

// 计算属性，基于当前页和搜索关键字返回分页后的订单
const filteredOrders = computed(() => {
  return orders.value.filter(order => {
    return (
        order.orderid.toString().includes(searchKeyword.value) ||
        order.consumerid.toString().includes(searchKeyword.value) ||
        order.productid.toString().includes(searchKeyword.value) ||
        order.farmerid.toString().includes(searchKeyword.value) ||
        order.orderstatus.includes(searchKeyword.value) ||
        order.orderAmount.toString().includes(searchKeyword.value)
    )
  })
})

const paginatedOrders = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return filteredOrders.value.slice(start, end)
})

const openDialog = (row) => {
  dialogTitle.value = '编辑订单'
  form.value = { ...row }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  try {
    console.log('表单数据：', form.value)
    const res = await axios.put(`http://localhost:8080/spring_mvc_mysql_vue_war/orders/update`, form.value)
    console.log('更新结果:', res.data)
    if (res.data) {
      ElMessage.success('更新成功')
      await fetchorder()
      dialogVisible.value = false
    } else {
      ElMessage.error('更新失败')
    }
  }catch (error){
    console.error('提交出错:', error)
    ElMessage.error('提交出错')
  }
}

const handleSearch = () => {
  currentPage.value = 1
}
</script>

<template>
  <div class="page-container">
    <div class="toolbar">
      <el-input
          v-model="searchKeyword"
          placeholder="搜索订单"
          style="width: 1000px;"
          @input="handleSearch"
          clearable
      />
    </div>
    <el-table :data="paginatedOrders" border stripe style="width: 100%">
      <el-table-column prop="orderid" label="订单ID" width="80" />
      <el-table-column prop="consumerid" label="消费者ID" />
      <el-table-column prop="productid" label="产品ID" />
      <el-table-column prop="farmerid" label="农户ID" />
      <el-table-column prop="orderstatus" label="订单状态" />
      <el-table-column prop="quantity" label="数量" />
      <el-table-column prop="orderAmount" label="订单金额" />
      <el-table-column prop="orderTime" label="下单时间" />
      <el-table-column prop="logisticsInfo" label="物流信息" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" type="primary" @click="openDialog(scope.row)">处理</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          :total="filteredOrders.length"
          layout="total, sizes, prev, pager, next, jumper"
      />
    </div>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" label-width="120px">
        <el-form-item label="订单状态">
          <el-select v-model="form.orderstatus" placeholder="请选择状态" style="width: 100%">
            <el-option label="待支付" value="待支付" />
            <el-option label="待发货" value="待发货" />
            <el-option label="待收货" value="待收货" />
            <el-option label="已完成" value="已完成" />
            <el-option label="已取消" value="已取消" />
          </el-select>
        </el-form-item>
        <el-form-item label="物流信息">
          <el-input v-model="form.logisticsInfo" />
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
.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
