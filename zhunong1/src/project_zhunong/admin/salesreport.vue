<template>
  <div class="data-report-container">
    <!-- 农产品销量图表 -->
    <div class="chart-container">
      <div id="salesChart" style="width: 100%; height: 400px;"></div>
    </div>

    <!-- 每季度订单数量图表 -->
    <div class="chart-container">
      <div id="quarterlyOrderChart" style="width: 100%; height: 400px;"></div>
    </div>
    <!-- 用户角色数量图表（柱状图） -->
    <div class="chart-container">
      <div id="userRoleBarChart" style="width: 100%; height: 400px;"></div>
    </div>

    <!-- 用户角色数量图表（饼状图） -->
    <div class="chart-container">
      <div id="userRolePieChart" style="width: 100%; height: 400px;"></div>
    </div>
    <!-- 销售分析 -->
    <div class="analysis-text">
      <h3>销售分析</h3>
      <p>{{ analysisText }}</p>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, nextTick} from 'vue'
import * as echarts from 'echarts'

const analysisText = ref('目前整体销售情况较好，特别是下半年，销售趋势逐渐上升。')

// 请求农产品销量数据
const fetchProductSales = async () => {
  try {
    const response = await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/orders/getquantity')
    const data = await response.json()
    // 假设返回的数据结构是 [{ productid, total_sales }, ...]
    const productIds = data.map(item => item.productid)
    const sales = data.map(item => item.total_sales)
    // 更新 ECharts 配置
    updateProductSalesChart(productIds, sales)
  } catch (error) {
    console.error('获取农产品销量数据失败:', error)
  }
}

// 更新农产品销量图表
const updateProductSalesChart = (productIds, sales) => {
  const chart = echarts.init(document.getElementById('salesChart'))

  const option = {
    title: {
      text: '农产品销量统计',
      left: 'center',
    },
    tooltip: {
      trigger: 'axis',
    },
    xAxis: {
      type: 'category',
      data: productIds,
    },
    yAxis: {
      type: 'value',
    },
    series: [
      {
        data: sales,
        type: 'bar',
        itemStyle: {
          color: '#4caf50',
        },
      },
    ],
  }

  chart.setOption(option)
}
const fetchUserRoleCount = async () => {
  try {
    const response = await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/user/getbytype')
    const data = await response.json()
    const roles = Object.keys(data)
    const counts = Object.values(data)

    updateRoleBarChart(roles, counts)
    updateRolePieChart(roles, counts)
  } catch (error) {
    console.error('获取用户角色数量失败:', error)
  }
}
// 请求季度订单数量数据
const fetchQuarterlyOrderCount = async () => {
  try {
    const response = await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/orders/quarterly')
    const data = await response.json()
    console.log('季度订单数量数据:', data)  // 确保这里的数据是正确的
    const quarters = data.map(item => `Q${item.quarter} ${item.year}`)
    const orderCounts = data.map(item => item.tquantity)
    console.log('季度标签:', quarters)  // 打印季度标签
    console.log('订单数量:', orderCounts)  // 打印订单数量
    updateQuarterlyOrderCountChart(quarters, orderCounts)
  } catch (error) {
    console.error('获取季度订单数量数据失败:', error)
  }
}

// 更新季度订单数量图表
const updateQuarterlyOrderCountChart = (quarters, orderCounts) => {
  const chart = echarts.init(document.getElementById('quarterlyOrderChart'))

  const option = {
    title: {
      text: '季度订单数量统计',
      left: 'center',
    },
    tooltip: {
      trigger: 'axis',
    },
    xAxis: {
      type: 'category',
      data: quarters,
    },
    yAxis: {
      type: 'value',
    },
    series: [
      {
        data: orderCounts,
        type: 'line',
        smooth: true,
        itemStyle: {
          color: '#4caf50',
        },
      },
    ],
  }

  chart.setOption(option)
}
const updateRoleBarChart = (roles, counts) => {
  const chart = echarts.init(document.getElementById('userRoleBarChart'))  // 修改为 'userRoleBarChart'
  const option = {
    title: { text: '用户角色数量（柱状图）', left: 'center' },
    tooltip: { trigger: 'axis' },
    xAxis: { type: 'category', data: roles },
    yAxis: { type: 'value' },
    series: [{
      data: counts,
      type: 'bar',
      itemStyle: { color: '#2196f3' },
    }]
  }
  chart.setOption(option)
}

const updateRolePieChart = (roles, counts) => {
  const chart = echarts.init(document.getElementById('userRolePieChart'))  // 修改为 'userRolePieChart'
  const option = {
    title: { text: '用户角色比例（饼状图）', left: 'center' },
    tooltip: { trigger: 'item' },
    series: [{
      type: 'pie',
      radius: '50%',
      data: roles.map((role, index) => ({ name: role, value: counts[index] })),
      itemStyle: {
        emphasis: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }]
  }
  chart.setOption(option)
}
// 在组件挂载后请求数据
onMounted(() => {
  fetchProductSales()
  fetchQuarterlyOrderCount()
  nextTick(() => {
    fetchUserRoleCount()
  })
})
</script>

<style scoped>
.data-report-container {
  padding: 20px;
}

.chart-container {
  margin-bottom: 20px;
}

.analysis-text {
  background-color: #f9f9f9;
  padding: 15px;
  border-radius: 8px;
}

h3 {
  margin-top: 0;
}
</style>
