import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';



// 创建axios实例
const axiosInstance = axios.create({
  // 可以设置全局的请求超时时间等配置
  timeout: 5000
});


// 请求拦截器
axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (config.url!== '/api/login') {
    config.headers['token'] = token;
}
  return config;
}, (error) => {
  return Promise.reject(error);
});

Vue.prototype.$axios = axiosInstance;
console.log('Axios实例已成功挂载到Vue原型上');



Vue.use(ElementUI);
Vue.config.productionTip = false





new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
