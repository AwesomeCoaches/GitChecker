<template>
  <div class="contents">
    <!-- Contents<br />
    commit wave(grass) present area<br /><br />
    Dummies is : {{ dummyData }} -->
    <!-- <div v-for="dummy in dummyData" :key="dummy.id">
      <Content :dummy="dummy" />
    </div> -->
    <v-tabs color="deep-purple accent-4" right>
      <v-tab>자세히</v-tab>
      <v-tab>간략히</v-tab>
      <v-tab>개인</v-tab>

      <v-tab-item>
        <v-container fluid>
          <div>
            <Table />
          </div>
        </v-container>
      </v-tab-item>
      <v-tab-item>
        <v-container fluid>
          <StasticsView />
        </v-container>
      </v-tab-item>
      <v-tab-item>
        <v-container fluid>
          <div>
            <Individual />
          </div>
        </v-container>
      </v-tab-item>
    </v-tabs>
  </div>
</template>

<script>
import StasticsView from "@/views/StasticsView.vue";
import Table from "@/components/Table.vue";
import Individual from "@/components/Individual.vue"

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
    StasticsView,
    Table,
    Individual
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
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  width: 100vw;
  min-width: 500px;
  min-height: 500px;

  /* border: dashed 4px lightgreen; */
}

.contents > div {
  flex: 1 40%;
  margin: 10px;
}
</style>