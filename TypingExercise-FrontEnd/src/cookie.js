/*
 * @描述: 
 * @版本: 
 * @作者: 冯佳兴
 * @Date: 2019-09-25 08:55:38
 * @最后修改人: 冯佳兴
 * @LastEditTime: 2019-09-25 10:58:06
 */
function setCookie(name, value) {
    var Days = 30
    var exp = new Date()
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 30)
    document.cookie = name + '=' + escape(value) + ';expires=' + exp.toGMTString()
  }
  function getCookie(name) {
    var reg = new RegExp('(^| )' + name + '=([^;]*)(;|$)')
    var arr = document.cookie.match(reg)
  
    if (arr) { return unescape(arr[2]) } else { return null }
  }
  
  function getToken() {
    return getCookie('Authorization')
  }
  
  export { setCookie, getCookie, getToken }
  