<script>
import { Pie } from "vue-chartjs";

export default {
  extends: Pie,
  data() {
    return {
      hello: this.$store.getters.filteredContributions.reduce((a, b) => {
        return a + b;
      }),
    };
  },
  computed: {
    contributions() {
      return this.$store.getters.filteredContributions;
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
      // color 생성되게 바꿔줄 것
      // sort, reduce 등 비슷한 함수를 쓰는 경우가 많은데 한 번에 취합하면 좋을듯.
      this.gradient = this.$refs.canvas
        .getContext("2d")
        .createLinearGradient(0, 0, 0, 450);
      this.gradient2 = this.$refs.canvas
        .getContext("2d")
        .createLinearGradient(0, 0, 0, 450);

      this.gradient.addColorStop(0, "rgba(255, 0,0, 0.5)");
      this.gradient.addColorStop(0.5, "rgba(255, 0, 0, 0.25)");
      this.gradient.addColorStop(1, "rgba(255, 0, 0, 0)");

      this.gradient2.addColorStop(0, "rgba(0, 231, 255, 0.9)");
      this.gradient2.addColorStop(0.5, "rgba(0, 231, 255, 0.25)");
      this.gradient2.addColorStop(1, "rgba(0, 231, 255, 0)");
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
              backgroundColor: [this.gradient, this.gradient2, "#00D8FF"],
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