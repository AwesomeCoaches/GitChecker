<template>
  <div class="navbar">
    <v-app-bar color="white" dense height="60">
      <div class="nav-flex">
        <img class="logo" src="../assets/logo.png" />
        <div class="filters">
          <v-select
            :items="region"
            label="지역"
            outlined
            dense
            v-model="filterRegion"
            hegiht="60"
            hide-details
            class="filter"
          ></v-select>
          <v-select
            :items="classes"
            label="반"
            outlined
            dense
            v-model="filterClass"
            hegiht="60"
            hide-details
            class="filter"
          ></v-select>
          <v-select
            :items="team"
            label="팀"
            outlined
            dense
            v-model="filterTeam"
            hegiht="60"
            hide-details
            class="filter"
          ></v-select>
          <v-select
            :items="Object.values(this.period)"
            label="기간"
            outlined
            dense
            v-model="filterPeriod"
            hegiht="60"
            hide-details
            class="filter"
          ></v-select>
          <div class="filter" style="margin-top: 15px !important">
            <v-menu
              ref="menu"
              v-model="menu"
              :close-on-content-click="false"
              :return-value.sync="filterDates"
              transition="scale-transition"
              offset-y
              nudge-left="150"
              min-width="auto"
              hegiht="60"
              class="filter"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  v-model="datesStr"
                  label="Date Picker"
                  readonly
                  v-bind="attrs"
                  v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker v-model="filterDates" no-title scrollable range>
                <v-spacer></v-spacer>
                <v-btn text color="primary" @click="menu = false">
                  Cancel
                </v-btn>
                <v-btn
                  text
                  color="primary"
                  @click="$refs.menu.save(filterDates)"
                >
                  OK
                </v-btn>
              </v-date-picker>
            </v-menu>
          </div>
        </div>
      </div>
    </v-app-bar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      region: ["", "A", "B", "C", "D"],
      classes: ["", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
      team: ["", 1, 2, 3, 4, 5, 6, 7, 8],
      period: ["전체", "공통", "특화", "자율"],
      menu: false,
    };
  },
  computed: {
    filterRegion: {
      get() {
        return this.$store.state.filter.region;
      },
      set(value) {
        this.$store.commit("updateRegion", value);
      },
    },
    filterClass: {
      get() {
        return this.$store.state.filter.classes;
      },
      set(value) {
        this.$store.commit("updateClass", value);
      },
    },
    filterTeam: {
      get() {
        return this.$store.state.filter.team;
      },
      set(value) {
        this.$store.commit("updateTeam", value);
      },
    },
    filterPeriod: {
      get() {
        return this.$store.state.filter.period;
      },
      set(value) {
        let dates = [];
        if (value === "전체") dates = ["2021-01-11", "2021-05-28"];
        else if (value === "공통") dates = ["2021-01-11", "2021-02-19"];
        else if (value == "특화") dates = ["2021-03-02", "2021-04-09"];
        else if (value == "자율") dates = ["2021-04-12", "2021-05-28"];
        this.$store.commit("updatePeriod", value);
        this.$store.commit("updateDates", dates);
      },
    },
    filterDates: {
      get() {
        return this.$store.state.filter.dates;
      },
      set(value) {
        this.$store.commit("updateDates", value);
      },
    },
    datesStr() {
      return this.filterDates
        .map((item) => {
          return item.slice(5, 10);
        })
        .join(" ~ ");
    },
  },
};
</script>

<style scoped>
.navbar {
  display: fixed;
  height: 60px;
  z-index: 101;
  /* border: dashed 4px lightpink */
}
.nav-flex {
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 60px;
}
.logo {
  height: 60px;
}

.filters {
  display: flex;
  /* padding: 20px 20px 20px 20px; */

  font-size: 0.75rem;
}
.filter {
  margin-top: 10px !important;
  width: 150px;
  padding: 0px 10px !important;
}
</style>