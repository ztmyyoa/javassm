<script setup>
import {ref, computed, onMounted} from 'vue'
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
const cartItems = ref([])

const totalPrice = computed(() =>
    cartItems.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
)
const fetchCartItems = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/spring_mvc_mysql_vue_war/cart/getbyconsumerid?username=${consumerusername}`)
    const res = response.data
    console.log('购物车:', res)
    if (res) {
      cartItems.value = res
    } else {
      console.error('返回的数据格式错误:', res)
      cartItems.value = []
    }

  } catch (error) {
    console.error('获取购物车列表失败：', error)
    cartItems.value = []
  }
}
onMounted(() => {
    fetchCartItems()
  })
const removeItem = async (index) => {
  const item = cartItems.value[index]
  try {
    await axios.delete(`http://localhost:8080/spring_mvc_mysql_vue_war/cart/delete?cartid=${item.cartid}`)
    cartItems.value.splice(index, 1)
    ElMessage.success('移除成功')
  } catch (e) {
    ElMessage.error('移除失败')
  }
}

const clearCart = async () => {
    try {
      await axios.delete(`http://localhost:8080/spring_mvc_mysql_vue_war/cart/clear?username=${consumerusername}`)
      cartItems.value = []
      ElMessage.success('购物车已清空')
    } catch (e) {
      ElMessage.error('清空失败')
    }
  }

const updateQuantity = async (item, value) => {
  item.quantity = Math.max(1, value)

  try {
    console.log('购物车id:', item.cartid)
    console.log('购物车数量:', item.quantity)
    console.log('购物车金额:', item.price* item.quantity)
    await axios.post(`http://localhost:8080/spring_mvc_mysql_vue_war/cart/update`, {
      cartid: item.cartid,
      quantity: item.quantity,
      totalprice: item.price * item.quantity
    })
    ElMessage.success('数量已更新')
  } catch (e) {
    ElMessage.error('数量更新失败')
  }
}

const dialogVisible = ref(false)
const confirmCheckout = async () => {
  dialogVisible.value = false
  try {
    const response = await axios.post(`http://localhost:8080/spring_mvc_mysql_vue_war/cart/submit`, cartItems.value)
    const res = response.data
    if (res){
      ElMessage.success('订单提交成功')
      router.push('/consumer/orders')
    }else{
      ElMessage.error('订单提交失败')
    }
    }catch (error){
    ElMessage.error('服务器错误，订单提交失败')
  }
  }

</script>

<template>
  <el-card>
    <h2>我的购物车</h2>
    <el-table :data="cartItems" stripe style="width: 100%">
      <el-table-column prop="cartid" label="ID" width="80" />
      <el-table-column prop="productid" label="农产品ID" width="80" />
      <el-table-column prop="productname" label="商品名称" width="80"/>
      <el-table-column label="图片" >
        <template #default="{ row }">
          <el-image :src="row.image" style="width: 120px; height: 120px" />
        </template>
      </el-table-column>
      <el-table-column prop="price" label="单价 (元)" width="120" />
      <el-table-column label="数量" width="150">
        <template #default="{ row }">
          <el-input-number
              v-model="row.quantity"
              :min="1"
              @change="val => updateQuantity(row, val)"
          />
        </template>
      </el-table-column>
      <el-table-column label="小计 (元)" width="120">
        <template #default="{ row }">
          {{ (row.price * row.quantity).toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ $index }">
          <el-button type="danger" size="small" @click="removeItem($index)">移除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="cart-actions">
      <div>总金额：<strong>{{ totalPrice.toFixed(2) }} 元</strong></div>
      <div>
        <el-button type="danger" @click="clearCart">清空购物车</el-button>
        <el-button type="primary" @click="dialogVisible = true" :disabled="cartItems.length === 0">
          结算
        </el-button>
      </div>
    </div>

    <!-- 结算确认框 -->
    <el-dialog v-model="dialogVisible" title="确认结算" width="400px">
      <span>总金额为 {{ totalPrice.toFixed(2) }} 元，是否提交订单？</span>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmCheckout">确认</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<style scoped>
h2 {
  margin-bottom: 20px;
}
.cart-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}
</style>
