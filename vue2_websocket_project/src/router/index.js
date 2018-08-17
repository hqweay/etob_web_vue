import Vue from 'vue'
import Router from 'vue-router'
import App from '@/App'

Vue.use(Router)
//嵌套路由见另一个项目
export default new Router({
  //mode: 'history', // 访问路径不带井号 
  routes: [
    {
      path: '/',
      name: 'index',
      component: r => require.ensure([], () => r(require('../page/index/index')), 'index'), // 使用懒加载 加快访问速度
    },
    {
      path: '/about',
      component: r => require.ensure([], () => r(require('../page/about/about')), 'about')
    },
    {
      path: '/copyright',
      component: r => require.ensure([], () => r(require('../page/copyright/copyright')), 'copyright')
    },
    {
      path: '/member/:name',
      component: r => require.ensure([], () => r(require('../components/member/information')), 'index'), // 使用懒加载 加快访问速度
      props: true
    }
  ]
})
