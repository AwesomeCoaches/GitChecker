import Vue from "vue";
import Vuex from "vuex";
import contributions from "@/contributions.json";

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
    // filteredStudents: (state) => {
    //   return state.students
    //     .filter(
    //       (item) =>
    //         (state.filter.city
    //           ? item.region.includes(state.filter.city)
    //           : true) &
    //         (state.filter.classes
    //           ? parseInt(item.team2 / 100) == state.filter.classes
    //           : true) &
    //         (state.filter.team ? item.team2 % 100 == state.filter.team : true)
    //     )
    //     .map((item) => item.name);
    // },
    // filteredContributions: (state, getters) => {
    //   return state.contributions.students.filter((item) =>
    //     getters.filteredStudents.includes(item.name)
    //   );
    // },
  },
  modules: {},
});
