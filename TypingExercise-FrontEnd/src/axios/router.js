
import router from '../router'

// 路由验证
router.beforeEach((to, from, next) => {
    if (to.matched.some(m => m.meta.auth)) {
        // 对路由进行验证
        if (store.state.isLogin == true) { // 已经登录
            next()  // 正常跳转
        } else {
            next({ path: '/login', query: { redirect: to.fullPath }})
        }
    } else {
        next()
    }

    // 如果已登录不允许直接跳转到 登录页面
    let fullPath = to.fullPath.substring(0, 6)
    if (fullPath == '/login') {
        if (store.state.isLogin == true) {
            next({
                path: from.fullPath
            })
        } else {
            next()
        }
    }
})