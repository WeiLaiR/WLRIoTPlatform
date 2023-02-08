import axios from 'axios'
import router from "@/router";
import Message from '@/components/message/index.js'

const request = axios.create({
    baseURL: 'http://localhost:8887',
    timeout: 12000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    let token = localStorage.getItem("token") ? localStorage.getItem("token") : null;
    if (token != null){
        config.headers['token'] = token
    }
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;

        // console.log(res)



        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }

        if (res.status === 200) {
            console.log(res.message)
        }else if (res.status === 400) {
            Message.error(res.message);
        }else if (res.status === 401) {
            router.push('/login');
            Message.error(res.message);
        }





        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)


export default request
