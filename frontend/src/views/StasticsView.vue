<template>
  <div>
    <div class="individual_modal" v-if="showindiModal" @click="closeIndividual">
      <Individual :student = "clickedStudent" class="modal"/>
    </div>
    <div class="charts">
      <div class="chart top10">
        <BarChart :propData="bestFiltered" label="Best 10" color="#f87979" />
        <v-simple-table dense>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">순위</th>
                <th class="text-left">지역</th>
                <th class="text-left">이름</th>
                <th class="text-left">커밋 수</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, idx) in bestFiltered" :key="item.id">
                <td>{{ idx + 1 }}</td>
                <td>{{ item.region.toUpperCase() }}</td>
                <td>{{ item.name }}</td>
                <td>{{ sum(item.commits) }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </div>
      <div class="chart top10" v-if="contribCnt > 10">
        <BarChart :propData="worstFiltered" label="Worst 10" color="#00D8FF" />
        <v-simple-table dense>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">순위</th>
                <th class="text-left">지역</th>
                <th class="text-left">이름</th>
                <th class="text-left">커밋 수</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, idx) in worstFiltered" :key="item.id" >
                <td>{{ idx + 1 }}</td>
                <td>{{ item.region.toUpperCase() }}</td>
                <td>{{ item.name }}</td>
                <td>{{ sum(item.commits) }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </div>
      <div class="chart">
        <PieChart v-if="contribCnt <= 10" />
      </div>
    </div>
  </div>
</template>

<script>
// import AreaChart from "@/components/chart/AreaChart.vue";
import PieChart from "@/components/chart/PieChart.vue";
import BarChart from "@/components/chart/BarChart.vue";

export default {
  name: "StasticsView",
  components: {
    // AreaChart,
    PieChart,
    BarChart,
  },
  computed: {
    datesContributions() {
      return this.$store.getters.datesContributions;
    },
    contributions() {
      return this.$store.getters.filteredContributions;
    },
    contribCnt() {
      return this.contributions.length;
    },
    bestFiltered() {
      if (this.isEmpty(this.datesContributions)) return [];
      if (this.isEmpty(this.datesContributions[0].commits)) return [];
      return this.$store.getters.datesContributions
        .slice()
        .sort(
          (a, b) =>
            b.commits.reduce((c, d) => {
              return c + d;
            }) -
            a.commits.reduce((c, d) => {
              return c + d;
            })
        )
        .slice(0, 10);
    },
    worstFiltered() {
      if (this.isEmpty(this.datesContributions)) return [];
      if (this.isEmpty(this.$store.getters.datesContributions[0].commits))
        return [];
      return this.$store.getters.datesContributions
        .slice()
        .sort(
          (a, b) =>
            a.commits.reduce((c, d) => {
              return c + d;
            }) -
            b.commits.reduce((c, d) => {
              return c + d;
            })
        )
        .slice(0, 10);
    },
  },
  data() {
    return {
      chart: {
        best10: {
          labels: [],
          datasets: [
            {
              label: "BEST 10",
              data: [],
            },
          ],
        },
        worst10: {
          labels: [],
          datasets: [
            {
              label: "WORST 10",
              data: [],
            },
          ],
        },
      },
      startDate: new Date("2021-01-01"),
      showindiModal : false,
    };
  },

  methods: {
    sum(arr) {
      if (this.isEmpty(arr)) return 0;
      else {
        return arr.reduce((a, b) => {
          return a + b;
        });
      }
    },
    isEmpty(arr) {
      if (!arr.length) return true;
      return false;
    },
     showIndividual(student){
      this.clickedStudent = student;
      this.showindiModal = true;
    },
    closeIndividual(){
      this.showindiModal = false;
    }
  },
};
</script>

<style scoped>
.chart {
  padding: 20px;
}
.top10 {
  display: flex;
  justify-content: space-around;
}
</style>