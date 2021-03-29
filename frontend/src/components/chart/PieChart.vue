<script>
import { Pie } from "vue-chartjs";

export default {
  extends: Pie,
  data() {
    return {
      stdColor: [23, 93, 137],
    };
  },
  computed: {
    contributions() {
      return this.$store.getters.datesContributions;
    },
    cnt() {
      return this.$store.getters.datesContributions.length;
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

      console.log(pallete);
      this.renderChart(
        {
          labels: this.contributions
            .sort(
              (a, b) =>
                b.commits.reduce((c, d) => {
                  return c + d;
                }) -
                a.commits.reduce((c, d) => {
                  return c + d;
                })
            )
            .map((item) => {
              return item.name;
            }),
          datasets: [
            {
              backgroundColor: pallete,
              data: this.contributions
                .sort(
                  (a, b) =>
                    b.commits.reduce((c, d) => {
                      return c + d;
                    }) -
                    a.commits.reduce((c, d) => {
                      return c + d;
                    })
                )
                .map((item) => {
                  return item.commits.reduce((a, b) => {
                    return a + b;
                  });
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