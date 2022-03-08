/*
 * @Description: 
 * @Version: 3.0
 * @Author: 谷浩樟
 * @Date: 2020-09-02 21:35:46
 * @LastEditors: 谷浩樟
 * @LastEditTime: 2020-09-02 21:39:38
 */
import Vue from 'vue'
import axios from 'axios'

const instance = axios.create({

  // baseURL: 'http://192.168.26.206/web', //501机房206服务器部署时使用
  baseURL: 'https://dazi.tfjybj.com/web/',//生产环境部署时使用，映射到外网
  // baseURL: 'http://d-typing.dmsd.tech:8081/typing-web',//开发服务器部署时使用
  // baseURL: 'http://t-typing.dmsd.tech:8081/typing-web',//测试服务器部署时使用
  // baseURL: 'http://localhost:8090/typing-web',//本地查看开发效果时使用


  // 超时时间
  timeout: 5000
})

Vue.prototype.$axios = axios
// axios.defaults.baseURL = baseUrl

// http请求
// axios request 拦截器
axios.interceptors.request.use(
  config => {
    // 下面会说在什么时候存储 token
    if (localStorage.getItem('token')) {
      let token = JSON.parse(localStorage.getItem("token"));
      // console.log(token.token_type);   
      let headers = new Headers({Authorization:token.token_type+" "+token.access_token});
      
      // config.headers['Authorization'] = localStorage.getItem('token')
      config.headers['Authorization'] = token.token_type+" "+token.access_token
      config.headers['Content-Type'] = 'application/json'     
    }
    return config
  },
  err => {
    return Promise.reject(err)
    
  }
)

// axios response 拦截器
axios.interceptors.response.use(
  response => {
    // 判断一下响应中是否有token，如果有就直接使用此token替换掉本地的token
    var token = response.headers.Authorization
    //console.log(token);
    if (token) {
      // 如果header中存在token，那么触发refreshToken方法，替换本地的token
      axios.defaults.headers.common['Authorization'] = token
    }
    return response
  },
  error => {
    return Promise.reject(error)
  }
)

Vue.http = Vue.prototype.$http = instance
export default axios
