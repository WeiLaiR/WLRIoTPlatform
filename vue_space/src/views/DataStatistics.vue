<template>
  <div style="height: 100%">
    <v-main style="margin: 0;padding: 0;height: 100%">

      <div style="margin: 10px;padding-left: 15px">
        <div class="teal--text" style="font-size: 26px">数据统计</div>
      </div>
      <v-divider style="margin-left: 7px;margin-right: 7px"></v-divider>


      <v-card
          max-width="90%"
          min-width="96%"
          min-height="88%"
          elevation="3"
          style="margin: 20px auto;background: #f8f9f9"
          outlined
      >

        <v-row>

          <v-col
              cols="4"
              style="padding-right: 0"
          >
            <v-select
                v-model="deviceCh"
                color="#2ebfaf"
                :items="items"
                item-text="text"
                item-value="value"
                filled
                label="选择设备"
                style="margin-left: 30px;margin-top: 22px;"
            ></v-select>

          </v-col>


          <v-col
              cols="3"
          >
            <v-select
                v-model="cfgCh"
                color="#2ebfaf"
                :items="items"
                item-text="text"
                item-value="value"
                filled
                label="选择数据"
                style="margin-left: 5px;margin-right: 5px;margin-top: 22px;"
            ></v-select>

          </v-col>

          <v-col
              cols="2"
              style="padding-left: 0;margin-top: 22px;padding-right: 20px"
          >
            <v-menu
                v-model="menu1"
                :close-on-content-click="false"
                :nudge-right="40"
                transition="scale-transition"
                offset-y
                color="#2ebfaf"
                min-width="290px"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                    v-model="dateStart"
                    label="START DATE"
                    prepend-icon="event"
                    readonly
                    color="#2ebfaf"
                    v-bind="attrs"
                    v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker v-model="dateStart" color="#2ebfaf" @input="menu1 = false"></v-date-picker>
            </v-menu>

          </v-col>

          <v-col
              cols="2"
              style="padding-left: 0;margin-top: 22px;padding-right: 15px"
          >
            <v-menu
                v-model="menu2"
                :close-on-content-click="false"
                :nudge-right="40"
                transition="scale-transition"
                offset-y
                color="#2ebfaf"
                min-width="290px"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                    v-model="dateEnd"
                    label="END DATE"
                    prepend-icon="event"
                    readonly
                    v-bind="attrs"
                    color="#2ebfaf"
                    v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker v-model="dateEnd" color="#2ebfaf" @input="menu2 = false"></v-date-picker>
            </v-menu>

          </v-col>

          <v-col cols="1" style="margin-top: 16px;">
            <v-switch v-model="switch1" color="#2ebfaf"></v-switch>
          </v-col>

        </v-row>






        <template

        >
          <v-sparkline
              v-show="switch1"
              :value="value"
              :gradient="gradient"
              :smooth="radius || false"
              :padding="padding"
              :line-width="width"
              :stroke-linecap="lineCap"
              :gradient-direction="gradientDirection"
              :fill="fill"
              :type="type"
              :auto-line-width="autoLineWidth"
              height="120"
              :labels="value"
              auto-draw
          ></v-sparkline>


          <v-data-table
              v-show="!switch1"
              :items-per-page="rowNums"
              :page="pageNum"
              :server-items-length="itemNums"
              :headers="headers"
              :items="desserts"
              class="elevation-1"
              style="margin-left: 30px;margin-right: 30px"
          ></v-data-table>


        </template>





      </v-card>



    </v-main>
  </div>
</template>

<script>

const gradients = [
  ['#222'],
  ['#42b3f4'],
  ['red', 'orange', 'yellow'],
  ['purple', 'violet'],
  ['#00c6ff', '#F0F', '#FF0'],
  ['#f72047', '#ffd200', '#1feaea'],
]


export default {
  name: "DataStatistics",
  data() {
    return {
      rowNums: 10,
      pageNum: 1,
      itemNums: 10,

      menu1: false,
      menu2: false,
      switch1: false,
      dateStart:new Date().toISOString().substr(0, 10),
      dateEnd:new Date().toISOString().substr(0, 10),
      deviceCh: "",
      cfgCh: "",
      items: [
        { text: '123', value: '1233' },
        { text: '123a', value: '1233a' },
        { text: '123b', value: '1233b' },
        { text: '123c', value: '1233c' },
        { text: '123d', value: '1233d' },
      ],

      width: 3,
      radius: 10,
      padding: 16,
      lineCap: 'round',
      gradient: gradients[5],
      value: [0, 2, 5, 9, 5, 10, 3, 5, 3, 0, 1, 8, 2, 9, 0],
      gradientDirection: 'top',
      gradients,
      fill: false,
      type: 'trend',
      autoLineWidth: false,







      headers: [
        {
          text: '数据ID',
          align: 'start',
          sortable: false,
          value: 'name',
        },
        { text: '数据接收时间', value: 'calories' },
        { text: '数据值', value: 'fat' },
      ],
      desserts: [
        {
          name: 'Frozen Yogurt',
          calories: 159,
          fat: 6.0,
        },
        {
          name: 'Ice cream sandwich',
          calories: 237,
          fat: 9.0,
        },
        {
          name: 'Eclair',
          calories: 262,
          fat: 16.0,
        },
        {
          name: 'Cupcake',
          calories: 305,
          fat: 3.7,
        },
        {
          name: 'Gingerbread',
          calories: 356,
          fat: 16.0,
        },
        {
          name: 'Jelly bean',
          calories: 375,
          fat: 0.0,
        },
        {
          name: 'Lollipop',
          calories: 392,
          fat: 0.2,
        },
        {
          name: 'Honeycomb',
          calories: 408,
          fat: 3.2,
        },
        {
          name: 'Donut',
          calories: 452,
          fat: 25.0,
        },
        {
          name: 'KitKat',
          calories: 518,
          fat: 26.0,
        },
      ],
    }
  },

}
</script>

<style scoped>

</style>
