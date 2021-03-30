import { Line } from './BaseCharts'

export default {
  extends: Line,
  props:['chartdata']
  ,
  mounted () {
    let label = [];
    for(let i = 0; i<this.chartdata.length;i++){
      label.push(i);
    }
    this.renderChart({
      labels: label,
      datasets: [
        {
          label: '',
          backgroundColor: '#f87979',
          data: this.chartdata
        }
      ]
    }, {responsive: true, maintainAspectRatio: false})
    
}
};