// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from "axios";
import ElementUI from 'element-ui' //引入js
import 'element-ui/lib/theme-chalk/index.css'//引入css
import Vuex from 'vuex'
import store from './vuex/store'
import { getCookie, setCookie, delCookie } from "@/util/util";
Vue.prototype.$cookieStore = {
  getCookie,
  setCookie,
  delCookie
}

//配置 axios
Vue.prototype.$ajax = axios;
Vue.use(Vuex)
Vue.config.productionTip = false;

Vue.use(ElementUI)



/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
