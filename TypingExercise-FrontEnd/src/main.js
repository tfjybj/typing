/*
 * @Description: 
 * @Version: 3.0
 * @Author: 谷浩樟
 * @Date: 2020-09-02 21:35:46
 * @LastEditors: 刘延强
 * @LastEditTime: 2021-09-22 15:57:12
 */
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from './axios'
import store from './store'
import global from '../src/components/Global.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'//element-ui的css
import VueI18n from 'vue-i18n';
import { messages } from './components/common/i18n';
import '../src/assets/css/icon.css';
import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
import echarts from 'echarts'
Vue.prototype.$echarts = echarts;
import 'lib-flexible/flexible.js';



import htmlToPdf from '../src/utils/htmlToPdf'
// 使用Vue.use()方法就会调用工具方法中的install方法
Vue.use(htmlToPdf)

import EleImport from '../src/components/importExcel/index'

Vue.component(EleImport.name, EleImport)

Vue.use(VueI18n);
Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.$global = global
// axios.defaults.baseURL = 'http://192.168.26.206/web'//global.BASE_URL
// axios.defaults.baseURL = 'http://localhost:8081/typing-web'//global.BASE_URL
// axios.defaults.baseURL = 'http://t-typing.dmsd.tech:8081/typing-web'//global.BASE_URL
axios.defaults.baseURL = 'http://d-typing.dmsd.tech:8081/typing-web'//global.BASE_URL
// axios.defaults.baseURL = 'http://typing.dmsd.tech:8081/typing-web'//global.BASE_URL
// axios.defaults.baseURL = 'https://dazi.tfjybj.com/web/'//global.BASE_URL


Vue.use(ElementUI, {
  size: 'small'
});
const i18n = new VueI18n({
  locale: 'zh',
  messages
});

// 使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
  document.title = `${to.meta.title}`;
  const role = localStorage.getItem('username');
  if (!role && to.path !== '/login') {
      next('/login');
  } else if (to.meta.permission) {
      // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
      role === 'admin' ? next() : next('/403');
  } else {
      // 简单的判断IE10及以下不进入富文本编辑器，该组件不兼容
      if (navigator.userAgent.indexOf('MSIE') > -1 && to.path === '/editor') {
          Vue.prototype.$alert('vue-quill-editor组件不兼容IE10及以下浏览器，请使用更高版本的浏览器查看', '浏览器不兼容通知', {
              confirmButtonText: '确定'
          });
      } else {
          next();
      }
  }
});



new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount('#app')
