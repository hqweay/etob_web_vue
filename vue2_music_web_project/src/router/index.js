import Vue from 'vue'
import Router from 'vue-router'
import App from '@/App'
import { getCookie, setCookie, delCookie } from "@/util/util";


Vue.use(Router)
// export default new Router({
const router = new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: r => require.ensure([], () => r(require('../page/index/index')), 'index'), // 使用懒加载 加快访问速度
      children: []
    },
    {
      path: '/login',
      component: r => require.ensure([], () => r(require('../components/user/login')), 'login'),
    },
    {
      path: '/about/:name',
      component: r => require.ensure([], () => r(require('../page/about/index')), 'about'),
    },
    {
      path: '/song/:song_id',
      // path: '/song/',
      component: r => require.ensure([], () => r(require('../components/music/musicInfo')), 'musicInfo'), // 使用懒加载 加快访问速度
    },
    {
      path: '/user',
      component: r => require.ensure([], () => r(require('../page/user/index')), 'user'), // 使用懒加载 加快访问速度
      meta: {
        isUser: true
      },
      children: [
        {
          path: '/user/',
          component: r => require.ensure([], () => r(require('../components/user/info')), 'info'), // 使用懒加载 加快访问速度
        },
        {
          path: '/user/edit',
          component: r => require.ensure([], () => r(require('../components/user/edit')), 'edit'), // 使用懒加载 加快访问速度
        },
        {
          path: '/user/info',
          component: r => require.ensure([], () => r(require('../components/user/info')), 'info'), // 使用懒加载 加快访问速度
        },
        {
          path: '/user/playList',
          component: r => require.ensure([], () => r(require('../components/user/playList')), 'palyList'), // 使用懒加载 加快访问速度
        }
      ]
    },
    {
      path: '/manage',
      component: r => require.ensure([], () => r(require('../page/manage/index')), 'manage'), // 使用懒加载 加快访问速度
      //加一个属性 做过滤
      meta: {
        isManager: true
      },
      children: [
        {
          path: '/manage/',
          component: r => require.ensure([], () => r(require('../components/manager/song/song')), 'main'), // 使用懒加载 加快访问速度
        },
        {
          path: '/manage/song',
          component: r => require.ensure([], () => r(require('../components/manager/song/song')), 'song'), // 使用懒加载 加快访问速度
        },
        {
          path: '/manage/user',
          component: r => require.ensure([], () => r(require('../components/manager/user/Users')), 'user'), // 使用懒加载 加快访问速度
        }
      ]
    }
  ]
})
//manager filter
router.beforeEach((to, from, next) => {
  if (to.matched.some(res => res.meta.isManager)) {// 判断是否需要登录权限
    // if (localStorage.getItem('username')) {// 判断是否登录
    if (getCookie("type") === "1") {// 判断是否登录
      next();
    } else {// 没登录则跳转到首页
      next({
        path: '/',
        // query: { redirect: to.fullPath }
      })
    }
  } else {
    next()
  }
});
//user filter
router.beforeEach((to, from, next) => {
  if (to.matched.some(res => res.meta.isUser)) {// 判断是否需要登录权限
    // if (localStorage.getItem('username')) {// 判断是否登录
    if (getCookie("username") != "" || getCookie("username") != undefined) {// 判断是否登录
      next();
    } else {// 没登录则跳转到首页
      next({
        path: '/',
      })
    }
  } else {
    next()
  }
})

export default router