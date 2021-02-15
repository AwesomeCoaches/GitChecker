<template>
  <div class="contents">
    Contents<br />
    commit wave(grass) present area<br /><br />
    Dummies is : {{ dummyData }}
    <div v-for="dummy in dummyData" :key="dummy.id">
      <Content :dummy="dummy" />
    </div>
  </div>
</template>

<script>
import Content from "@/components/Content";

const dummies = Array();
for (let i = 0; i < 10; i++) {
  dummies.push({ title: "Team" + i });
}
export default {
  data() {
    return {
      dummyData: dummies,
    };
  },
  components: {
    Content,
  },
};
</script>

<style scoped>
.contents {
  display: flex;
  flex-wrap: wrap;
  width: 80vw;
  min-width: 500px;
  min-height: 500px;

  border: dashed 4px lightgreen;
}

.contents > div {
  flex: 1 40%;
  margin: 10px;
}
</style>