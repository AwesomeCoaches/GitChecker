import Vue from "vue";
import Vuex from "vuex";
import contributions from "@/contributions.json";

Vue.use(Vuex);

// const mapPeriod = {
//   전체: "",
//   공통: 0,
//   특화: 1,
//   자율: 2,
// };

export default new Vuex.Store({
  state: {
    contributions: {},
    filter: {
      city: "",
      classes: "",
      team: "",
      period: "전체",
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
    updatePeriod(state, value) {
      state.filter.period = value;
    },
  },
  actions: {},
  getters: {
    filteredContributions: (state) => {
      return state.contributions.students.filter(
        (item) =>
          (state.filter.city ? item.region === state.filter.city : true) &
          (state.filter.classes
            ? parseInt(item.class[1] / 100) === state.filter.classes
            : true) &
          (state.filter.team ? item.class[1] % 100 == state.filter.team : true)
      );
    },
  },
  modules: {},
});
