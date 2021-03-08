import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    filter: {
      city: "",
      classes: "",
      team: "",
    },
  },
  mutations: {
    updateCity(state, value) {
      state.filter.city = value;
    },
    updateClass(state, value) {
      state.filter.classes = value;
    },
    updateTeam(state, value) {
      state.filter.team = value;
    },
  },
  actions: {},
  modules: {},
});
