
import { PolarArea } from 'vue-chartjs'
export default {
  name: 'PolarAreaChart',
  extends: PolarArea,
  data: () => ({
    chartdata: {
      labels: [
        'Male',
        'Female',
        'Child',
        'Old',
      ],
      datasets: [
        {
          label: 'My First dataset',
          backgroundColor: 'rgba(179,181,198,0.2)',
          pointBackgroundColor: 'rgba(179,181,198,1)',
          pointBorderColor: '#2EBFAF',
          pointHoverBackgroundColor: '#2EBFAF',
          pointHoverBorderColor: 'rgba(179,181,198,1)',
          data: [28, 48, 40, 19]
        },
        {
          label: 'My Second dataset',
          backgroundColor: 'rgba(46,191,175,0.2)',
          pointBackgroundColor: '#2EBFAF',
          pointBorderColor: '#2EBFAF',
          pointHoverBackgroundColor: '#2EBFAF',
          pointHoverBorderColor: '#2EBFAF',
          data: [65, 59, 90, 81]
        }
      ]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false
    }
  }),
  mounted() {
    this.renderChart(this.chartdata, this.options)
  }
}
