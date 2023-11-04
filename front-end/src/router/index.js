import { createRouter, createWebHistory } from "vue-router";

import HomeView from "../views/home/HomeView.vue";

const componentRoutes = [
  {
    path: "/",
    component: HomeView,
    name: "home",
  },
];

const redirectRoutes = [{ path: "/home", redirect: { name: "home" } }];
const routes = [...redirectRoutes, ...componentRoutes];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
