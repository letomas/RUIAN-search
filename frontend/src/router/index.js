import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "",
    name: "home",
    component: Home
  },
  {
    path: "/",
    redirect: { name: "home" }
  },
  {
    path: "/vyhledavani",
    name: "search",
    component: () => import("../views/Search.vue")
  },
  {
    path: "/adresni-mista-v-okoli",
    name: "nearbyAddresses",
    component: () => import("../views/NearbyAddresses.vue")
  },
  {
    path: "/adresni-mista/:id(\\d+)",
    name: "addressDetail",
    component: () => import("../views/AddressDetail.vue")
  },
  {
    path: "*",
    component: () => import("../components/NotFound.vue")
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
