<template>
  <div class="contents">
    <!-- Contents<br />
    commit wave(grass) present area<br /><br />
    Dummies is : {{ dummyData }} -->
    <!-- <div v-for="dummy in dummyData" :key="dummy.id">
      <Content :dummy="dummy" />
    </div> -->
    <div class="charts">
      <AreaChart/>
      <PieChart/>
      <BarChart/>
    </div>
  </div>
</template>

<script>
// import Content from "@/components/Content";
import AreaChart from "@/components/chart/AreaChart.vue";
import PieChart from "@/components/chart/PieChart.vue";
import BarChart from "@/components/chart/BarChart.vue";

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
    // Contents,
    AreaChart,
    PieChart,
    BarChart,
  },
};
</script>

<style scoped>
.charts {
  display: flex;
  height: 300px;
}
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