import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    city: "",
    district: "",
    street: "",
    houseNumber: "",
    admCode: "",
    items: [],
    location: null,
    locationAvailable: false
  },
  mutations: {
    updateCity(state, payload) {
      state.city = payload;
    },
    updateDistrict(state, payload) {
      state.district = payload;
    },
    updateStreet(state, payload) {
      state.street = payload;
    },
    updateHouseNumber(state, payload) {
      state.houseNumber = payload;
    },
    updateAdmCode(state, payload) {
      state.admCode = payload;
    },
    updateItems(state, payload) {
      state.items = payload;
    },
    updateLocation(state, payload) {
      state.location = payload;
      state.locationAvailable = true;
    },
    updateLocationAvailable(state, payload) {
      state.locationAvailable = payload;
    },
    resetQueryState(state) {
      state.city = "";
      state.district = "";
      state.street = "";
      state.houseNumber = "";
      state.admCode = "";
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
