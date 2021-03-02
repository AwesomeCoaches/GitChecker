<template>
  <div class="home">
    <Contents/>
  </div>
</template>

<script>
import Contents from '@/components/Contents' 

export default {
  name: 'Home',
  components: {
    Contents
  }
}
</script>

<style scoped>
.home {
  display: flex;
}
</style>