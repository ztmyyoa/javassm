import {createRouter, createWebHashHistory} from 'vue-router'
import Login from "@/project_zhunong/other/login.vue";
import Register from "@/project_zhunong/other/register.vue";
import store from './store.js' // 💡 引入 Vuex 状态管理
//(2)配置组件访问的路径
const routes=[
    {
        path: '/',component:()=>import("./project_zhunong/resource/home.vue"),
        children: [
            {
                path: '/login',
                name: 'Login',
                component: Login,
            },
            {
                path: '/register',
                name: 'Register',
                component: Register,
            },
        ]
    },
    {
        path: '/adminhome',component:()=>import("./project_zhunong/admin/adminhome.vue"),
        children:[
            {
                path: 'farmermanage',component:()=>import("./project_zhunong/admin/FarmerManagement.vue")
            },
            {
                path: 'consumermanage', component: () => import("./project_zhunong/admin/ConsumerManagement.vue")
            },
            {
                path: 'productmanage', component: () => import("./project_zhunong/admin/ProductManagement.vue")
            },
            {
                path: 'ordersmanage', component: () => import("./project_zhunong/admin/Ordersmanage.vue")
            },
            {
                path: 'announcementmanage', component: () => import("./project_zhunong/admin/Announcement.vue")
            },
            {
                path: 'promotionmanage', component: () => import("./project_zhunong/admin/Promotion.vue")
            },
            {
                path: 'reviewmanage', component: () => import("./project_zhunong/admin/Reviewmanage.vue")
            },
            {
                path: 'registeraudit', component: () => import("./project_zhunong/admin/registeraudit.vue")
            },
            {
                path: 'admanage', component: () => import("./project_zhunong/admin/advertisementmanage.vue")
            },
            {
                path: 'salesreport', component: () => import("./project_zhunong/admin/salesreport.vue")

            },
            {
                path: 'platformmonitor', component: () => import("./project_zhunong/admin/platformmonitor.vue")
            },
            {
                path: 'subsidymanage', component: () => import("./project_zhunong/admin/subsidymanage.vue")
            }
        ]
    },
    {
        path: '/farmer', component: () => import("./project_zhunong/farmer/farmerhome.vue")
        ,
        children: [
            {
                path: 'home', component: () => import("./project_zhunong/resource/home1.vue")
            },
            {
                path: 'profile', component: () => import("./project_zhunong/farmer/farmerprofile.vue")
            },
            {
                path: 'products', component: () => import("./project_zhunong/farmer/farmerproduct.vue")
            },
            {
                path: 'orders', component: () => import("./project_zhunong/farmer/farmerorder.vue")
            },
            {
                path: 'reports', component: () => import("./project_zhunong/farmer/salesreport.vue")
            },
            {
                path: 'training', component: () => import("./project_zhunong/farmer/Activities.vue")
            },
            {
                path: 'feedback', component: () => import("./project_zhunong/farmer/farmerFeedback.vue")
            },
            {
                path: 'subsidy', component: () => import("./project_zhunong/farmer/farmersubsidy.vue")
            },
            {
                path: '/farmer/search',
                name: 'farmerSearch',
                component: () => import('./project_zhunong/resource/SearchResult.vue')
            },
            {
                path: '/farmer/productDetail/:id',
                name: 'ProductDetailf',
                component: () => import('./project_zhunong/resource/ProductDetailf.vue')
            },

        ]
    },
    {
        path: '/consumer', component: () => import("./project_zhunong/consumer/consumerhome.vue")
        ,
        children: [
            {
                path: 'home', component: () => import("./project_zhunong/resource/home1.vue")
            },
            {
                path: 'profile', component: () => import("./project_zhunong/consumer/consumerprofile.vue")
            },
            {
                path: 'products', component: () => import("./project_zhunong/consumer/consumerproduct.vue")
            },
            {
                path: 'orders', component: () => import("./project_zhunong/consumer/consumerorder.vue")
            },
            {
                path: 'shopcar', component: () => import("./project_zhunong/consumer/consumercar.vue")
            },
            {
                path: 'feedback', component: () => import("./project_zhunong/consumer/consumerFeedback.vue")
            },
            {
                path: '/consumer/productDetail/:id',
                name: 'ProductDetail',
                component: () => import('./project_zhunong/resource/ProductDetail.vue')
            },
            {
                path: '/consumer/pay/:id',
                name: 'Pay'
                ,  component: () => import("./project_zhunong/resource/pay.vue")
            },
            {
                path: '/consumer/search',
                name: 'ConsumerSearch',
                component: () => import('./project_zhunong/resource/SearchResult.vue')
            }

        ]
    }
]

//(3)创建路由实例
const router = createRouter({
    history: createWebHashHistory(),
    routes
})
//(4)设置登录拦截路由守卫
router.beforeEach((to, from, next) => {
    const publicPaths = ['/login', '/register', '/','SearchResult']; // 不需要登录的路径
    const isPublic = publicPaths.includes(to.path);
    const isLoggedIn = store.getters.isLoggedIn;

    if (!isLoggedIn && !isPublic) {
        // 用户未登录，且访问的页面非公开页 → 重定向到登录页
        next('/login');
    } else {
        // 已登录 或 是公开页面 → 放行
        next();
    }
});
export default router;
