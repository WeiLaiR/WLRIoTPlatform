import { Line } from 'vue-chartjs'
//const { reactiveProp } = mixins

export default {
    extends: Line,
    mounted() {
      this.renderChart(
        {
          labels: [
            'January',
            'February',
            'March',
            'April',
            'May',
            'June',
            'July'
          ],
          datasets: [
            {
              label: 'Achievement',
              backgroundColor: '#2EBFAF',
              data: [20, 39, 70, 15, 50, 80, 22]
            }
          ]
        },
        { responsive: true, maintainAspectRatio: false }
      )
    }
  }