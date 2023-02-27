import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import axios from 'axios'
import VueAxios from "vue-axios";
import request from "@/utils/request";
import Message from "@/components/message/index.js";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import { library } from '@fortawesome/fontawesome-svg-core'
import {faTriangleExclamation} from '@fortawesome/free-solid-svg-icons'
import {faCircleCheck} from '@fortawesome/free-solid-svg-icons'
import JSEncrypt from 'jsencrypt';
import SHA256 from 'js-sha256'
import './router/permission'
// import 'material-design-icons-iconfont/dist/material-design-icons.css'


Vue.config.productionTip = false

Vue.use(VueAxios,axios)
Vue.prototype.request=request
Vue.prototype.$message = Message
Vue.prototype.$jsEncrypt = JSEncrypt
Vue.prototype.$SHA256 = SHA256
library.add(faTriangleExclamation, faCircleCheck)
Vue.component('font-awesome-icon', FontAwesomeIcon)

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
