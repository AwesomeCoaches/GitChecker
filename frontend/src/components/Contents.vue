<template>
  <div class="contents">
    Contents<br>
    commit wave(grass) present area
    <div 
    class="content"
    v-for="dummy in dummyData" 
    :key="dummy.id">
      <Content @data="dummy"/>
    </div>
    {{dummyData}}
  </div>
</template>

<script>
import Content from "@/components/Content"

const dummies = Array();
for(let i=0; i<10; i++) {
  dummies.push({title: "Team" + i})
}
export default {
  data() {
    return {
      dummyData: dummies 
    }
  },
  components: {
    Content
  }
}
</script>

<style scoped>
.contents {
  display: flex;
  flex-direction: column;
  width: 80vw;
  min-width: 500px;
  min-height: 500px;
  
  border: dashed 4px lightgreen;
}

.content {
  width: 50%;
  height: 200px;
}
</style>