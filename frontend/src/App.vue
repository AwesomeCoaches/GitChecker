<template>
  <v-app id="app">
    <NavBar />
    <div class="d-flex">
      <SideBar />
      <Contents />
      <!-- <router-view /> -->
    </div>
  </v-app>
</template>

<script>
import NavBar from "@/components/NavBar";
import SideBar from "@/components/SideBar";
import contributions from "@/contributions.json";
import Contents from "@/components/Contents";

export default {
  data() {
    return {
      contributions: contributions,
    };
  },
  components: {
    NavBar,
    SideBar,
    Contents,
  },
  mounted() {
    this.$store.commit("getContributions");
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
}
.d-flex {
  display: flex;
}
</style>
