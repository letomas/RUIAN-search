import Vue from "vue";

import VueLogger from "vuejs-logger";

const options = {
  showLogLevel: true,
  showMethodName: true,
  showConsoleColors: true
};

Vue.use(VueLogger, options);
