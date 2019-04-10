import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import setQuestion from '@/pages/setQuestion/setQuestion'
import answer from '@/pages/answerQuestion/answer'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    // 设题
    {
      path: '/setQuestion',
      name: 'setQuestion',
      component: setQuestion
    },
    // 答题
    {
      path: '/answer',
      name: 'answer',
      component: answer
    },
  ]
})
