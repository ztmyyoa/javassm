<template>
  <div class="pay-container">
    <el-card class="box-card">
      <div class="header">
        <h2>请在 {{ minutes }}:{{ seconds < 10 ? '0' + seconds : seconds }} 内完成支付</h2>
      </div>
      <img src="@/assets/paymaent.jpg" alt="支付二维码" class="payment-image" />

      <div class="action">
        <el-button type="success" @click="confirmPayment">我已完成支付</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import {ref, onMounted, onUnmounted, computed} from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const form =ref({})

const orderid = route.params.id
const countdown = ref(300) // 5分钟
const timer = ref(null)

const minutes = computed(() => Math.floor(countdown.value / 60))
const seconds = computed(() => countdown.value % 60)

onMounted(() => {
  timer.value = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(timer.value)
      ElMessage.error('支付超时，请重新下单')
      router.back()
    }
  }, 1000)
})

onUnmounted(() => {
  clearInterval(timer.value)
})

const confirmPayment = async () => {
  try {
  console.log('订单号:', orderid)
  const res = await await axios.get(`http://localhost:8080/spring_mvc_mysql_vue_war/orders/${orderid}`)
  console.log('订单数据:', res.data)
  if (res.data.orderstatus === '待支付') {
    res.data.orderstatus="待发货"
    console.log('修改订单状态:', res.data)
    const response = await axios.put(`http://localhost:8080/spring_mvc_mysql_vue_war/orders/update`, res.data)
    if(response.data){
      ElMessage.success('支付成功')
      await router.push('/consumer/orders')
    }else{
      ElMessage.error('支付失败')
    }
  }
  }catch (error){
    console.error('支付失败:', error)
    ElMessage.error('支付失败')
  }

}
</script>

<style scoped>
.pay-container {
  display: flex;
  justify-content: center;
  padding-top: 40px;
}

.box-card {
  width: 400px;
  text-align: center;
}

.header {
  margin-bottom: 20px;
}

.payment-image {
  width: 100%;
  height: auto;
  margin-bottom: 20px;
}

.action {
  margin-top: 20px;
}
</style>
