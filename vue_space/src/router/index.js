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

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Manage,
    redirect: "/home",
    children: [
      { path: 'home', name: '主页', component: Home},
      { path: 'deviceInfo', name: '设备信息', component: DeviceInfo},
      { path: 'deviceSetting', name: '设备配置', component: DeviceSetting},
      { path: 'dataStatistics', name: '数据统计', component: DataStatistics},
      { path: 'userInfo', name: '用户信息', component: UserInfo},
      { path: 'updateInfo', name: '个人信息', component: UpdateInfo},
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
    component: Login
  },
  {
    path: "/register",
    name: "Register",
    component: Register
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
