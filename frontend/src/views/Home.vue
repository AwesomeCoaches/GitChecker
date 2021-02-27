<template>
  <div class="home">
    <SideBar/>
    <Contents/>
  </div>
</template>

<script>
import SideBar from '@/components/SideBar.vue'
import Contents from '@/components/Contents' 

export default {
  name: 'Home',
  components: {
    SideBar,
    Contents
  }
}
</script>

<style scoped>
.home {
  display: flex;
}
</style>