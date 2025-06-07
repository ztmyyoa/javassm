<template>
  <div class="page-container">
    <el-row :gutter="20" class="top-cards">
      <!-- 系统状态等其他信息卡片 -->
    </el-row>

    <el-row :gutter="20" class="bottom-chart">
      <el-col :span="24">
        <el-card shadow="always">
          <div id="trafficChart" class="chart"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

const trafficData = ref([
  { date: '2024-04-20', traffic: 1000 },
  { date: '2024-04-21', traffic: 1500 },
  { date: '2024-04-22', traffic: 1800 },
  { date: '2024-04-23', traffic: 2200 },
  { date: '2024-04-24', traffic: 1900 },
  { date: '2024-04-25', traffic: 2100 },
  { date: '2024-04-26', traffic: 2500 },
]);

const initChart = () => {
  const chartElement = document.getElementById('trafficChart');
  if (!chartElement) {
    console.error('找不到 trafficChart DOM元素');
    return;
  }
  const myChart = echarts.init(chartElement);
  const option = {
    title: {
      text: '平台访问量趋势',
      left: 'center',
      top: '5%',
      textStyle: { fontSize: 18 },
    },
    tooltip: {
      trigger: 'axis',
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: trafficData.value.map(item => item.date),
    },
    yAxis: {
      type: 'value',
    },
    series: [
      {
        name: '访问量',
        type: 'line',
        smooth: true,
        data: trafficData.value.map(item => item.traffic),
        areaStyle: {},
        lineStyle: {
          color: '#4a90e2',
        },
      },
    ],
  };
  myChart.setOption(option);

  // 监听 WebSocket 消息更新图表
  const ws = new WebSocket('ws://localhost:8080/spring_mvc_mysql_vue_war/ws/traffic'); // 替换为实际 WebSocket 服务端地址
  ws.onmessage = (event) => {
    const newTrafficData = JSON.parse(event.data);
    trafficData.value.push(newTrafficData); // 新数据推送到数组
    if (trafficData.value.length > 10) trafficData.value.shift(); // 保持数组的最大长度

    // 更新图表
    myChart.setOption({
      xAxis: {
        data: trafficData.value.map(item => item.date),
      },
      series: [
        {
          data: trafficData.value.map(item => item.traffic),
        },
      ],
    });
  };

  ws.onopen = () => {
    console.log('WebSocket连接已建立');
  };

  ws.onerror = (error) => {
    console.error('WebSocket 连接错误', error);
  };

  ws.onclose = () => {
    console.log('WebSocket 连接关闭');
  };
};

onMounted(() => {
  initChart();
});
</script>

<style scoped>
.page-container {
  padding: 30px;
  background: #f5f7fa;
  min-height: 100vh;
}

.top-cards {
  margin-bottom: 30px;
}

.card-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #333;
}

.card-content {
  font-size: 18px;
  color: #666;
}

.chart {
  width: 100%;
  height: 400px;
}
</style>
