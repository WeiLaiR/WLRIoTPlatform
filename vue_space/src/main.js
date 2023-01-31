import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import axios from 'axios'
import VueAxios from "vue-axios";
import request from "@/utils/request";


Vue.config.productionTip = false

Vue.use(VueAxios,axios)
Vue.prototype.request=request

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
