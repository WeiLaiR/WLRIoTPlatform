import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Manage from "@/views/Manage.vue";
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
import DeviceInfo from "@/views/DeviceInfo.vue";
import DeviceSetting from "@/views/DeviceSetting.vue";
import DataStatistics from "@/views/DataStatistics.vue";
import UserInfo from "@/views/UserInfo.vue";
import UpdateInfo from "@/views/UpdateInfo.vue";
import Setting from "@/views/Setting.vue";
import DeviceRule from "@/views/DeviceRule.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Manage,
    redirect: "/home",
    children: [
      { path: 'home', name: '主页', component: Home, meta: {title: 'WLRIoTPlatform-主页'}},
      { path: 'deviceInfo', name: '设备管理', component: DeviceInfo, meta: {title: 'WLRIoTPlatform-设备管理'}},
      { path: 'deviceRule', name: '规则管理', component: DeviceRule, meta: {title: 'WLRIoTPlatform-规则管理'}},
      { path: 'deviceSetting', name: '设备配置', component: DeviceSetting, meta: {title: 'WLRIoTPlatform-设备配置'}},
      { path: 'dataStatistics', name: '数据统计', component: DataStatistics, meta: {title: 'WLRIoTPlatform-数据统计'}},
      { path: 'userInfo', name: '用户信息', component: UserInfo, meta: {title: 'WLRIoTPlatform-用户信息'}},
      { path: 'updateInfo', name: '个人信息', component: UpdateInfo, meta: {title: 'WLRIoTPlatform-个人信息'}},
      { path: 'setting', name: '设置', component: Setting, meta: {title: 'WLRIoTPlatform-设置'}},
    ]
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: "/login",
    name: 'Login',
    component: Login, meta: {title: 'WLRIoTPlatform-登录'}
  },
  {
    path: "/register",
    name: "Register",
    component: Register, meta: {title: 'WLRIoTPlatform-注册'}
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.afterEach((to) => {
  document.title = to.meta.title //在全局后置守卫中获取路由元信息设置title
})




export default router
