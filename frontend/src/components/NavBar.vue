<template>
  <div class="navbar">
      <v-app-bar
      color="white"
      dense
      dark
      height="60"
    >

     <img class="logo" src="../assets/logo.png"/>

    </v-app-bar>
  </div>
</template>

<script>
  export default {
  }
</script>

<style scoped>
.navbar {
  display: fixed;
  height: 60px;
  z-index: 101;
  /* border: dashed 4px lightpink */
}
.logo {
 height:100%;
}
</style>