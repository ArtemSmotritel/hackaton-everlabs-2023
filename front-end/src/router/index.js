import { createRouter, createWebHistory } from "vue-router";

import HomeView from "../views/home/HomeView.vue";
import TinderView from "../views/tinder/TinderView.vue";
import EventDetails from "../views/eventDetails/EventDetails.vue"
import EventBoardView from "../views/event_board/EventBoardView.vue";

const componentRoutes = [
  {
    path: "/",
    component: HomeView,
    name: "home",
  },
  {
    path: "/mate-match",
    component: TinderView,
    name: "match",
  },
  {
    path: "/events",
    component: EventBoardView,
    name: "event-board",
  },
  {
    path: "/event-details",
    component: EventDetails,
    name: "eventDetails",
  },
];

const redirectRoutes = [{ path: "/home", redirect: { name: "home" } }];
const routes = [...redirectRoutes, ...componentRoutes];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
