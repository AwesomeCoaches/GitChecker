import Vue from "vue";
import Vuex from "vuex";
import contributions from "@/contributions.json";
import _ from "lodash";

Vue.use(Vuex);

const stdDate = new Date("2021-01-01");
let startDate, endDate;

export default new Vuex.Store({
  state: {
    contributions: {},
    updatedTime: "",
    filter: {
      region: "",
      classes: "",
      team: "",
      period: "전체",
      dates: ["2021-01-11", "2021-05-28"],
    },
    pjt: 1,
  },
  mutations: {
    getContributions(state) {
      state.contributions = contributions;
      state.updatedTime = contributions.updated_time;
    },
    updateRegion(state, value) {
      state.filter.region = value;
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
      state.filter.dates = value.sort();
    },
  },
  actions: {},
  getters: {
    filteredContributions: (state) => {
      return state.contributions.students.filter(
        (item) =>
          (state.filter.region
            ? item.region === state.filter.region.toLowerCase()
            : true) &
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
      return contributions;
    },
    updatedTime(state) {
      return state.updatedTime;
    },
  },
  modules: {},
});
