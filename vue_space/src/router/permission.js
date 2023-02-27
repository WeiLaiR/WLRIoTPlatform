import router from "@/router/index";

router.beforeEach((to, from, next) => {
    let token = localStorage.getItem("token");
    let userStatus = localStorage.getItem("userStatus");
    if (to.path === '/login' || to.path === '/register') {
        if (token === null) {
            next()
        }else {
            next({
                path: '/home'
            })
        }
    }else if (to.path === '/home') {
        if (token === null) {
            next({
                path: '/login'
            })
        }else {
            if (userStatus < 1) {
                next({
                    path: '/login'
                })
            }else {
                next()
            }
        }
    }else if (to.path === '/userInfo') {
        if (token === null) {
            next({
                path: '/login'
            })
        }else {
            if (userStatus < 2) {
                next({
                    path: '/home'
                })
            }else {
                next()
            }
        }
    }else {
        if (token === null) {
            next({
                path: '/login'
            })
        }else {
            next()
        }
    }
})



