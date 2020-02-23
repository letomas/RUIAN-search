import Vue from "vue";

import {
  BootstrapVue,
  BIconSearch,
  BIconAlertTriangleFill
} from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

Vue.use(BootstrapVue);
Vue.component("BIconSearch", BIconSearch);
Vue.component("BIconAlertTriangleFill", BIconAlertTriangleFill);
