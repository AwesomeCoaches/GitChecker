import Vue from "vue";
import Vuex from "vuex";
import contributions from "@/contributions";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    contributions: {},
    filter: {
      city: "",
      classes: "",
      team: "",
    },
  },
  mutations: {
    getContributions(state) {
      state.contributions = contributions;
    },

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
