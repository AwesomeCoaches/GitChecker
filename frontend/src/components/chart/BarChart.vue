<script>
import { Bar } from "vue-chartjs";

export default {
  extends: Bar,
  props: {
    propData: Array,
    label: String,
    color: String,
  },
  watch: {
    propData() {
      this.rendChart();
    },
  },

  mounted() {
    this.rendChart();
  },
  methods: {
    rendChart() {
      console.log(this.propData);
      this.renderChart(
        {
          labels: this.propData.map((item) => {
            return item.name;
          }),
          datasets: [
            {
              label: this.label,
              backgroundColor: this.color,
              data: [
                ...this.propData.map((item) => {
                  return item.commits.reduce((a, b) => {
                    return a + b;
                  });
                }),
                0,
              ],
            },
          ],
        },
        { responsive: true, maintainAspectRatio: false }
      );
    },
  },
};
</script>
