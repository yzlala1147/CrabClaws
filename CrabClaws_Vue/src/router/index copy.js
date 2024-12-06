import Vue from 'vue'
import VueRouter from 'vue-router'
import main from '../views/Home/mainView.vue'
import home from '../views/Home/HomeView.vue'
import SQL1 from '../views/SQL注入/SQL1.vue'
import SQL2 from '../views/SQL注入/SQL2.vue'
import SQL3 from '../views/SQL注入/SQL3.vue'
import SQL_No from '../views/SQL注入/SQL_No.vue'
import CMD1 from '../views/命令执行/CMD1.vue'
import CMD2 from '../views/命令执行/CMD2.vue'
import upload from '../views/文件上传/upload.vue'
import upload_white from '../views/文件上传/upload_white.vue'
import file_read_no from '../views/文件读取/file_read_no.vue'
import file_read from '../views/文件读取/file_read.vue'
import SSRF from '../views/SSRF/SSRF.vue'
import XXE from '../views/XXE/XXE.vue'
import De_serialize from '../views/反序列化/De_serialize.vue'
import Fast_json from '../views/反序列化/fast_json.vue'


Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'login',
        component: () => import(/* webpackChunkName: "login" */ '../views/Login/LoginView.vue')
    },
    {
        path: '/main',
        name: 'main',
        component: main,
        children: [
            //子路由
            //SQL注入
            {
                path: '/SQL1',
                name: 'SQL1',
                component: SQL1
            },
            {
                path: '/SQL2',
                name: 'SQL2',
                component: SQL2
            },
            {
                path: '/SQL3',
                name: 'SQL3',
                component: SQL3
            },
            {
                path: '/SQL_No',
                name: 'SQL_No',
                component: SQL_No
            },
            //命令执行
            {
                path: '/CMD1',
                name: 'CMD1',
                component: CMD1
            },
            {
                path: '/CMD2',
                name: 'CMD2',
                component: CMD2
            },
            //文件上传
            {
                path: '/upload',
                name: 'upload',
                component: upload
            },
            {
                path: '/upload_white',
                name: 'upload_white',
                component: upload_white
            },
            //文件读取
            {
                path: '/file_read',
                name: 'file_read',
                component: file_read
            },
            {
                path: '/file_read_no',
                name: 'file_read_no',
                component: file_read_no
            },
            //SSRF
            {
                path: '/SSRF',
                name: 'SSRF',
                component: SSRF
            },
            //XXE
            {
                path: '/XXE',
                name: 'XXE',
                component: XXE
            },
            //反序列化
            {
                path: '/Deserialize',
                name: 'Deserialize',
                component: De_serialize
            },
            {
                path: '/fastjson',
                name: 'fastjson',
                component: Fast_json
            },
            //主页面
            {
                path: '/home',
                name: 'home',
                component: home
            }
        ]
    },
]

const router = new VueRouter({
    routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');

  // 判断是否存在token
  if (!token && to.path!== '/login') {
      // 如果没有token且不是访问登录页面，跳转到登录页面
      next('/login');
  } else {
      // 检查路由是否存在
      const matchedRoutes = router.getRoutes().map(route => route.path);
      if (!matchedRoutes.includes(to.path)) {
          // 如果路由不存在，跳转到主页面（home）
          next('/home');
      } else {
          // 路由存在且有token（或正在访问登录页面），放行
          next();
      }
  }
});

export default router