<template>
  <div>
    <!-- {{ this.bestFiltered }} -->
    <v-menu
      v-model="dateMenu"
      :close-on-content-click="false"
      :nudge-right="40"
      transition="scale-transition"
      offset-y
      min-width="auto"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-text-field
          v-model="date"
          label="날짜 선택"
          prepend-icon="mdi-calendar"
          readonly
          v-bind="attrs"
          v-on="on"
        ></v-text-field>
      </template>
      <v-date-picker v-model="date" @input="dateMenu = false"></v-date-picker>
    </v-menu>
    <div class="charts">
      <div class="chart top10">
        <BarChart :propData="bestFiltered" label="Best 10" color="#f87979" />
        <v-simple-table dense>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">순위</th>
                <th class="text-left">이름</th>
                <th class="text-left">커밋 수</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, idx) in bestFiltered" :key="item.id">
                <td>{{ idx + 1 }}</td>
                <td>{{ item.student.name }}</td>
                <td>{{ item.cnt }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </div>
      <div class="chart top10">
        <BarChart :propData="worstFiltered" label="Worst 10" color="#00D8FF" />
        <v-simple-table dense>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">순위</th>
                <th class="text-left">이름</th>
                <th class="text-left">커밋 수</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, idx) in worstFiltered" :key="item.id">
                <td>{{ idx + 1 }}</td>
                <td>{{ item.student.name }}</td>
                <td>{{ item.cnt }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </div>
      <div class="chart"><PieChart /></div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
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
    dateFiltered() {
      return this.contributions.filter((item) => item.date.includes(this.date));
    },
    bestFiltered() {
      return this.contributions
        .filter((item) => item.date.includes(this.date))
        .sort((a, b) => b.cnt - a.cnt)
        .slice(0, 10);
    },
    worstFiltered() {
      return this.contributions
        .filter((item) => item.date.includes(this.date))
        .sort((a, b) => a.cnt - b.cnt)
        .slice(0, 10);
    },
  },
  data() {
    return {
      contributions: [],
      date: new Date().toISOString().substr(0, 10),
      dateMenu: false,
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
    };
  },
  mounted() {
    this.getContributions();
  },
  methods: {
    getContributions() {
      axios
        .get(`http://t4coach44.p.ssafy.io/api/contributions/`)
        .then((res) => {
          console.log(res);
          this.contributions = res.data;
          this.totalDesserts = res.data.length;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
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