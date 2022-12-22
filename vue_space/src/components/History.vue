<template>
   <v-card height="" flat color="transparent" class="mt-n5">
                <v-toolbar color="rgba(0,0,0,0)" flat class="py-4">
                  <v-toolbar-title class=""> History Survey </v-toolbar-title>
                </v-toolbar>

                <line-chart
                  :chart-id="chartId"
                  :width="width"
                  height="200px"
                  :css-classes="cssClasses"
                  :styles="styles"
                  :plugins="plugins"
                />
              </v-card>
</template>

<script>
import LineChart from "../chart/LineChart.js";
export default {
    data:  () => ({
         props: {
      chartId: {
        type: String,
        default: "line-chart",
      },
      width: {
        type: Number,
        default: 400,
      },
      height: {
        type: Number,
        default: 100,
      },
      cssClasses: {
        default: "",
        type: String,
      },
      styles: {
        type: Object,
        default: () => {},
      },
      plugins: {
        type: Array,
        default: () => {},
      },
    },
   
    }),
     components: {
    LineChart,
   
  },
}
</script>

<style>

</style>