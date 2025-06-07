<script setup>
import { ref, computed, onMounted } from 'vue'
import {ElMessage} from "element-plus";
import {useStore} from "vuex";
import axios from "axios";
import router from "@/router.js";
const store = useStore()
// 获取用户名和密码
const consumerusername = store.getters.getUsername;
const  consumerpassword= store.getters.getPassword;
console.log(consumerusername);
console.log(consumerpassword);
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
const orderDetailDialog = ref(false)
const currentOrderDetail = ref({})
const returnExchangeDialogVisible = ref(false)
const returnExchangeForm = ref({
  logisticsInfo: '',
  productid: null
})
const viewOrder = (row) => {
  currentOrderDetail.value = { ...row }
  orderDetailDialog.value = true
}
// 从服务器获取订单数据
const fetchorder = async () => {
  try {
    const res = await axios.get(`http://localhost:8080/spring_mvc_mysql_vue_war/orders/getbyconsumerid?username=${consumerusername}`)
    const response = await res.data
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
const handleReturnExchangeSubmit = async () => {
  console.log('修改订单数据',returnExchangeForm.value)
  form.value.productid=returnExchangeForm.value.productid
  form.value.orderstatus=returnExchangeForm.value.orderstatus
  form.value.logisticsInfo=returnExchangeForm.value.logisticsInfo
  console.log('退还订单数据',form.value)
  try {
    const res = await axios.put(`http://localhost:8080/spring_mvc_mysql_vue_war/orders/update`, form.value)
    if(res.data){
      ElMessage.success('退换货成功')
      returnExchangeDialogVisible.value = false
      await fetchorder()
    }else {
      ElMessage.error('退换货失败')
    }
  }catch (error){
    console.error('提交出错:', error)
    ElMessage.error('提交出错')
  }

}
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
  if (row.orderstatus !== '待发货') {
    ElMessage.warning('只能处理状态为【待发货】的订单')
    return
  }
  dialogTitle.value = '编辑订单'
  form.value = { ...row }
  dialogVisible.value = true
}


const payOrder = (row) => {
  if (row.orderstatus !== '待支付') {
    ElMessage.warning('该订单当前不是待支付状态')
    return
  }
  // 跳转到支付页，带订单信息
  router.push(`/consumer/pay/${row.orderid}`)
}

const requestReturn = (row) => {

}

const openexDialog = (row) => {
  form.value={...row}
  returnExchangeForm.value={...row}
  returnExchangeDialogVisible.value = true
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
      <el-table-column label="操作" width="240">
        <template #default="scope">
          <el-button size="small" @click="viewOrder(scope.row)">查看</el-button>
          <el-button size="small" type="success" @click="payOrder(scope.row)">支付</el-button>
          <el-button size="small" type="danger" @click="openexDialog(scope.row)">退换货</el-button>
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
    <!-- 退换货弹窗 -->
    <el-dialog v-model="returnExchangeDialogVisible" title="退换货" width="600px">
      <el-form :model="returnExchangeForm" label-width="120px">
        <el-form-item label="产品ID">
          <el-input v-model="returnExchangeForm.productid"  />
        </el-form-item>
        <el-form-item label="订单状态">
        <el-select v-model="returnExchangeForm.orderstatus" placeholder="请选择状态" label="订单状态">
          <el-option label="待发货" value="待发货" />
          <el-option label="已完成" value="已完成" />
          <el-option label="已取消" value="已取消" />
        </el-select>
        </el-form-item>
        <el-form-item label="物流信息">
          <el-input v-model="returnExchangeForm.logisticsInfo" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="returnExchangeDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleReturnExchangeSubmit">确认</el-button>
      </template>
    </el-dialog>
    <el-dialog v-model="orderDetailDialog" title="订单详情" width="600px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="订单ID">{{ currentOrderDetail.orderid }}</el-descriptions-item>
        <el-descriptions-item label="消费者ID">{{ currentOrderDetail.consumerid }}</el-descriptions-item>
        <el-descriptions-item label="产品ID">{{ currentOrderDetail.productid }}</el-descriptions-item>
        <el-descriptions-item label="农户ID">{{ currentOrderDetail.farmerid }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ currentOrderDetail.orderstatus }}</el-descriptions-item>
        <el-descriptions-item label="数量">{{ currentOrderDetail.quantity }}</el-descriptions-item>
        <el-descriptions-item label="金额">{{ currentOrderDetail.orderAmount }}</el-descriptions-item>
        <el-descriptions-item label="时间">{{ currentOrderDetail.orderTime }}</el-descriptions-item>
        <el-descriptions-item label="物流信息">{{ currentOrderDetail.logisticsInfo }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <el-button @click="orderDetailDialog = false">关闭</el-button>
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
