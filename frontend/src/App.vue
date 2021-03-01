<template>
  <v-app id="app">
    <NavBar/>
    <div class="d-flex">
      <SideBar/>
      <router-view/>
    </div>
  </v-app>
</template>

<script>
import NavBar from "@/components/NavBar"
import SideBar from "@/components/SideBar"

export default {
  components: {
    NavBar,
    SideBar
  },
}
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
