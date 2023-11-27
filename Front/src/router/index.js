import { createRouter, createWebHistory, createMemoryHistory } from 'vue-router'
import MentionView from '../views/MentionView.vue';
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import SignUpView from '../views/SignUpView.vue';
import PlanView from '../views/PlanView.vue';

const router = createRouter({
  // mode:'history',
  // history: createWebHistory(import.meta.env.BASE_URL),
  history: createMemoryHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/mention',
      name: 'mention',
      component: MentionView,
      redirect: { name:'mention-list'},
      children: [
        {
          path: 'list',
          name: 'mention-list',
          component:() => import("@/components/mention/MentionListView.vue")
        },
      ]
    },
    {
      path: '/user/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/user/signup',
      name: 'signup',
      component: SignUpView
    },
    {
      path: '/user/changepassword',
      name: 'changePassword',
      component:() => import("@/views/ChangePwView.vue")
    },
    {
      path: '/plan',
      name: 'plan',
      component: PlanView,
      redirect: { name:'plan-list'},
      children: [
        {
          path: 'list',
          name: 'plan-list',
          component:() => import("@/components/plan/PlanList.vue")
        },
        {
          path: 'view/:contentid',
          name: 'plan-view',
          component:() => import("@/components/plan/PlanView.vue")
        },
        {
          path: 'regist',
          name: 'plan-write',
          component:() => import("@/components/plan/PlanWriteView.vue")
        },
        {
          path: 'update/:contentid',
          name: 'plan-update',
          component:() => import("@/components/plan/PlanEditView.vue")
        }
      ]
    },
    {
      path: '/todos',
      name: 'todos',
      component: () => import("@/views/TheTodoView.vue"),
    },
    {
      path: '/user/changeuserinfo',
      name: 'changeuserinfo',
      component: () => import("@/views/ChangeUserInfoView.vue"),
    },
    {
      path: '/choice',
      name: 'choice',
      component: () => import("@/views/ChoiceChangeView.vue"),
    },
    // {
    //   path: '/about',
    //   name: 'about',
    //   // route level code-splitting
    //   // this generates a separate chunk (About.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import('../views/AboutView.vue')
    // }
  ]
})

export default router
