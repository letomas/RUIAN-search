import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    items: [],
    location: null,
    locationAvailable: false
  },
  mutations: {
    updateItems(state, payload) {
      state.items = payload;
    },
    updateLocation(state, payload) {
      state.location = payload;
      state.locationAvailable = true;
    }
  },
  getters: {
    items: state => {
      return state.items;
    },
    location: state => {
      return state.location;
    },
    locationAvailable: state => {
      return state.locationAvalable;
    }
  },
  actions: {},
  modules: {}
});
