import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './style.css'
import App from './App.vue'
import router from "./router.js";
import store from './store'
const app = createApp(App)
app.use(ElementPlus)
app.use(router)
    .use(store)
app.mount('#app')
import * as ELIcons from '@element-plus/icons-vue'
// 全局导入element plus图标
for (let iconName in ELIcons) {
    app.component(iconName, ELIcons[iconName])
}
