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
const sub = ref([])
const sub1 = ref([])
const searchKeyword = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const dialogVisible = ref(false)
const dialogpromotionname = ref('新增补贴')
const form = ref({
  subid: null,
  title: '',
  content: '',
  amount: '',
  farmerid: '',
  substatus:''
})

// 过滤后的列表
const filteredsub = computed(() => {
  const keyword = searchKeyword.value.trim().toLowerCase()
  return sub.value.filter(a =>
      a.subid?.toString().includes(keyword) ||
      a.title?.toLowerCase().includes(keyword)||
      a.content?.toLowerCase().includes(keyword)

  )
})

const filteredsub1 = computed(() => {
  const keyword = searchKeyword.value.trim().toLowerCase()
  return sub1.value.filter(a =>
      a.subid?.toString().includes(keyword) ||
      a.title?.toLowerCase().includes(keyword)||
      a.content?.toLowerCase().includes(keyword)

  )
})
const fetcha = async () => {
  try {
    const res = await fetch('http://localhost:8080/spring_mvc_mysql_vue_war/subsidy/all')
    const res1=  await fetch(`http://localhost:8080/spring_mvc_mysql_vue_war/subsidy/getbyfarmer?username=${farmerusername}`)
    const response = await res.json()
    const response1 = await res1.json()
    console.log('补贴数据:', response)
    console.log('补贴数据1:', response1)
    if(response) {
      sub.value = response
      sub1.value= response1
      console.log('补贴数据:', sub)
      console.log('补贴数据1:', sub1)
    }else{
      console.error('返回的数据格式错误:', response)
      console.log('补贴数据1:', response1)
      sub.value = []
    }
  }
  catch (error) {
    console.error('获取补贴列表失败：', error)
    sub.value = []
  }
}
onMounted(()=>{
  fetcha()
})
// 当前页数据
const pagedsub = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return filteredsub.value.slice(start, end)
})
const pagedsub1 = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return filteredsub1.value.slice(start, end)
})


// 新增/编辑打开弹窗
// const openDialog = async (type, row = null) => {
//   try {
//     dialogpromotionname.value = '编辑补贴'
//     form.value = { ...row }
//     const farmerdata= await axios.get(`http://localhost:8080/spring_mvc_mysql_vue_war/farmer/getfamerprofile?username=${farmerusername}`);
//     form.value.farmerId=farmerdata.data.userid
//     console.log('传入:',form.value)
//     const res = await axios.post(`http://localhost:8080/spring_mvc_mysql_vue_war/promotion/addfarmerpro`, form.value)
//     console.log('返回的数据:', res.data)
//     if (res.data) {
//       ElMessage.success('新增成功')
//       dialogVisible.value = false
//       await fetcha()
//     } else {
//       ElMessage.error(res.data.message || '新增失败')
//     }} catch (error){
//     console.error('获取补贴列表失败：', error)
//     ElMessage.error(res.data.message || '新增失败')
//   }
//
// }
const openDialog = (type, row = null) => {
    dialogpromotionname.value = '查看补贴'
    form.value = { ...row }
  dialogVisible.value = true
}
// 提交保存
// const handleSubmit = async () => {
//   try{
//     if(dialogpromotionname.value==='新增补贴'){
//       console.log('新增补贴:', form.value)
//       const res = await axios.post('http://localhost:8080/spring_mvc_mysql_vue_war/subsidy/add', form.value)
//       console.log('返回的数据:', res.data)
//       if (res.data) {
//         ElMessage.success('新增成功')
//         dialogVisible.value = false
//         await fetcha()
//       }else {
//         ElMessage.error(res.data.message || '新增失败')
//       }
//     }else {
//       console.log('编辑补贴:', form.value)
//       const res = await axios.put('http://localhost:8080/spring_mvc_mysql_vue_war/subsidy/update', form.value)
//       console.log('返回的数据:', res.data)
//       if (res.data) {
//         ElMessage.success('更新成功')
//         dialogVisible.value = false
//         await fetcha()
//       }
//     }
//   }catch (error){
//     console.error('获取补贴列表失败：', error)
//
//   }
// }

const handleapply = async (row=null) => {
  try{
    form.value={...row}
    const farmerdata= await axios.get(`http://localhost:8080/spring_mvc_mysql_vue_war/farmer/getfamerprofile?username=${farmerusername}`);
    form.value.farmerid=farmerdata.data.userid
    console.log('farmerdata:',farmerdata.data)
    form.value.substatus='待审核'
    console.log('传入:',form.value)
    const res = await axios.post(`http://localhost:8080/spring_mvc_mysql_vue_war/subsidy/addsign`, form.value)
    console.log('返回的数据:', res.data)
    if (res.data) {
      ElMessage.success('申请成功')
      dialogVisible.value = false
      await fetcha()
    }else {
      ElMessage.error(res.data.message || '申请失败')
    }
  }catch (error){
    console.error('获取补贴列表失败：', error)
  }
}
// 搜索时回到第1页
const handleSearch = () => {
  currentPage.value = 1
}
const handlePageChange = (page) => {
  currentPage.value = page
}
</script>

<template>
  <div class="page-container">
    <div class="toolbar">
      <el-input
          v-model="searchKeyword"
          placeholder="搜索补贴"
          style="width: 1000px;"
          @input="handleSearch"
          clearable
      />

    </div>

    <el-table :data="pagedsub" border stripe style="width: 100%">
      <el-table-column prop="subid" label="补贴ID" width="80" />
      <el-table-column prop="title" label="补贴政策" />
      <el-table-column prop="content" label="内容" />
      <el-table-column prop="amount" label="补贴金额"/>
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" type="primary"  color="green" @click="handleapply(scope.row)">申请</el-button>
          <el-button size="small" type="primary" @click="openDialog('edit', scope.row)">查看</el-button>
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
          :total="filteredsub.length"
          @current-change="handlePageChange"
      />
    </div>

    <el-table :data="pagedsub1" border stripe style="width: 100%">
      <el-table-column prop="subid" label="补贴ID" width="80" />
      <el-table-column prop="title" label="补贴政策" />
      <el-table-column prop="content" label="内容" />
      <el-table-column prop="amount" label="补贴金额"/>
      <el-table-column prop="farmerid" label="申请农户"/>
      <el-table-column prop="substatus" label="申请状态"/>
    </el-table>

    <div class="pagination-container">
      <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          :total="filteredsub1.length"
          @current-change="handlePageChange"
      />
    </div>
    <!-- 弹窗表单 -->
    <el-dialog v-model="dialogVisible" :promotionname="dialogpromotionname" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="补贴政策标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="补贴内容">
          <el-input v-model="form.content" />
        </el-form-item>
        <el-form-item label="补贴金额">
          <el-input v-model="form.amount" />
        </el-form-item>

      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
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
