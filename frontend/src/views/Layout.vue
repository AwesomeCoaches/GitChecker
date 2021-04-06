<template>
  <div class="wrapper">
    <div class="simple">
      <div class="totalCommits card">
        <div class="title">Total Commits</div>
        <div class="content">
          <div class="strong">{{ totalCommits }}</div>
          <div class="weak">{{ $store.getters.updatedTime }}</div>
        </div>
      </div>
      <div class="students card">
        <div class="title">Students</div>
        <div class="content">
          <v-simple-table dense>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">idx</th>
                  <th class="text-left">지역</th>
                  <th class="text-left">이름</th>
                  <th class="text-left">커밋 수</th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(item, idx) in $store.getters.datesContributions"
                  :key="item.id"
                >
                  <td>{{ idx + 1 }}</td>
                  <td>{{ item.region.toUpperCase() }}</td>
                  <td>{{ item.name }}</td>
                  <td>{{ sum(item.commits) }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </div>
      </div>
    </div>
    <div class="detail">
      <div class="rows">
        <div class="best10 card w-50">
          <div class="title">BEST 10</div>
          <div class="content">
            <div class="chart">
              <BarChart
                :propData="bestFiltered"
                label="Best 10"
                color="#f87979"
              />
            </div>
            <div class="table">
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
          </div>
        </div>
        <div class="worst10 card w-50">
          <div class="title">WORST 10</div>
          <div class="content">
            <div class="chart">
              <BarChart
                :propData="worstFiltered"
                label="Worst 10"
                color="#00D8FF"
              />
            </div>
          </div>
          <div class="table">
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
                  <tr v-for="(item, idx) in worstFiltered" :key="item.id">
                    <td>{{ idx + 1 }}</td>
                    <td>{{ item.region.toUpperCase() }}</td>
                    <td>{{ item.name }}</td>
                    <td>{{ sum(item.commits) }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </div>
        </div>
      </div>
      <div class="rows">
        <div v-if="contribCnt <= 70" class="pie card w-50">
          <div class="title">Pie Chart</div>
          <div class="content">
            <PieChart v-if="contribCnt <= 10" />
            <PieChartClass v-if="(contribCnt >= 10) & (contribCnt <= 70)" />
          </div>
        </div>
        <div class="recentCommits card w-50">
          <div class="title">No Commit Days</div>
          <div class="content"><NoCommits /></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import AreaChart from "@/components/chart/AreaChart.vue";
import PieChart from "@/components/chart/PieChart";
import PieChartClass from "@/components/chart/PieChartClass";
import BarChart from "@/components/chart/BarChart";
import NoCommits from "@/components/chart/NoCommits";

export default {
  name: "StasticsView",
  components: {
    // AreaChart,
    PieChart,
    PieChartClass,
    BarChart,
    NoCommits,
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
    totalCommits() {
      if (this.isEmpty(this.datesContributions)) return 0;
      let cnt = 0;
      this.$store.getters.datesContributions.forEach((ele) => {
        cnt += ele.commits.reduce((a, b) => {
          return a + b;
        });
      });
      return cnt;
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
  },
};
</script>


<style lang="scss" scoped>
.wrapper {
  display: flex;

  .card {
    padding: 20px 30px;
    margin: 15px;
    background-color: #f4f4f4;
    border-radius: 16px;
    box-shadow: rgba(50, 50, 93, 0.25) 0px 2px 5px -1px,
      rgba(0, 0, 0, 0.3) 0px 1px 3px -1px;

    .title {
      font-weight: 1000;
      padding-bottom: 10px;
    }
  }

  .simple {
    width: 30vw;
    min-width: 400px;
  }

  .detail {
    width: 70vw;
  }

  .rows {
    display: flex;
  }
  .w-50 {
    width: 50%;
  }

  .strong {
    font-size: 2rem;
    font-weight: 600;
    text-align: center;
    margin: 20px;
  }
  .weak {
    color: #5f5f5f;
    font-size: 0.8rem;
    text-align: right;
  }
}
</style>