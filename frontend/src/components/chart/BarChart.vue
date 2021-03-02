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
    propData: function (newVal, oldVal) {
      // watch it
      console.log("Prop changed: ", newVal, " | was: ", oldVal);
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
            return item.student.name;
          }),
          datasets: [
            {
              label: this.label,
              backgroundColor: this.color,
              data: [
                ...this.propData.map((item) => {
                  return item.cnt;
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
