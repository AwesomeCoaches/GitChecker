<script>
import { Pie } from "vue-chartjs";

export default {
  extends: Pie,
  data() {
    return {
      stdColor: [20, 90, 150],
      teams: {},
      pjt: this.$store.state.pjt,
      cnt: 0,
    };
  },
  computed: {
    contributions() {
      return this.$store.getters.datesContributions;
    },
  },
  mounted() {
    this.rendChart();
  },
  watch: {
    contributions() {
      this.rendChart();
    },
  },
  methods: {
    rendChart() {
      this.teams = {};
      this.contributions.forEach((contrib) => {
        if (this.teams[contrib.class[this.pjt]]) {
          this.teams[contrib.class[this.pjt]] += contrib.commits.reduce(
            (a, b) => {
              return a + b;
            }
          );
        } else {
          this.teams[contrib.class[this.pjt]] = contrib.commits.reduce(
            (a, b) => {
              return a + b;
            }
          );
        }
      });
      const sortedTeam = Object.entries(this.teams).sort((a, b) => b[1] - a[1]);
      this.cnt = sortedTeam.length;
      const diff = this.stdColor.map((color) => {
        return parseInt((255 - color) / this.cnt);
      });
      const pallete = [];

      for (let i = 0; i < this.cnt; i++) {
        pallete.push(
          `#${(this.stdColor[0] + diff[0] * i).toString(16)}${(
            this.stdColor[1] +
            diff[1] * i
          ).toString(16)}${(this.stdColor[2] + diff[2] * i).toString(16)}`
        );
      }

      this.renderChart(
        {
          labels: sortedTeam.map((item) => {
            return item[0];
          }),
          datasets: [
            {
              backgroundColor: pallete,
              data: sortedTeam.map((item) => {
                return item[1];
              }),
            },
          ],
        },
        { responsive: true, maintainAspectRatio: false }
      );
    },
  },
};
</script>