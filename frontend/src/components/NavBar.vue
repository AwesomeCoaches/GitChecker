<template>
  <div class="navbar">
    <v-app-bar color="white" dense height="60">
      <div class="nav-flex">
        <img class="logo" src="../assets/logo.png" />
        <div class="filters">
          <v-select
            :items="city"
            label="지역"
            outlined
            dense
            v-model="filterCity"
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
        </div>
      </div>
    </v-app-bar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      city: ["", "a", "b", "c", "d"],
      classes: ["", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11],
      team: ["", 1, 2, 3, 4, 5, 6, 7, 8],
      period: ["전체", "공통", "특화", "자율"],
    };
  },
  computed: {
    filterCity: {
      get() {
        return this.$store.state.filter.city;
      },
      set(value) {
        this.$store.commit("updateCity", value);
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
        this.$store.commit("updatePeriod", value);
      },
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