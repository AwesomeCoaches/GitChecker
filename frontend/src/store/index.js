import Vue from "vue";
import Vuex from "vuex";
import contributions from "@/contributions.json";
import _ from "lodash";

Vue.use(Vuex);

// const mapPeriod = {
//   전체: "",
//   공통: 0,
//   특화: 1,
//   자율: 2,
// };
const stdDate = new Date("2021-01-01");
let startDate, endDate;

export default new Vuex.Store({
  state: {
    contributions: {},
    filter: {
      city: "",
      classes: "",
      team: "",
      period: "전체",
      dates: ["2021-01-11", "2021-05-28"],
    },
  },
  mutations: {
    getContributions(state) {
      state.contributions = contributions;
    },
    updateCity(state, value) {
      state.filter.city = value.toLowerCase();
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
    updateDates(state, value) {
      state.filter.dates = value;
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
    datesContributions: (state, getters) => {
      let contributions = _.cloneDeep(getters.filteredContributions);

      startDate = new Date(state.filter.dates[0]);
      if (state.filter.dates.length == 1) endDate = startDate;
      else endDate = new Date(state.filter.dates[1]);
      if (startDate > endDate) {
        let temp;
        startDate = temp;
        startDate = endDate;
        endDate = temp;
      }
      const diffDays1 = Math.ceil(
        Math.abs(stdDate - startDate) / (1000 * 60 * 60 * 24)
      );
      const diffDays2 = Math.ceil(
        Math.abs(stdDate - endDate) / (1000 * 60 * 60 * 24)
      );
      contributions.map((item) => {
        item.commits = item.commits.slice(diffDays1, diffDays2 + 1);
        return item;
      });
      console.log(diffDays1, diffDays2 + 1, contributions);
      return contributions;
    },
  },
  modules: {},
});
