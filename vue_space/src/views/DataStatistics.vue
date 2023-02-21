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
                :items="cfgS"
                item-text="text"
                item-value="value"
                filled
                label="选择数据"
                style="margin-left: 5px;margin-right: 5px;margin-top: 22px;"
            ></v-select>

          </v-col>

          <v-col
              cols="1"
              style="padding-left: 0;margin-top: 22px;padding-right: 2px"
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
              cols="1"
              style="padding-left: 0;margin-top: 22px;padding-right: 15px"
          >
            <v-menu
                ref="menu3"
                v-model="menu3"
                :close-on-content-click="false"
                :nudge-right="40"
                :return-value.sync="time1"
                transition="scale-transition"
                color="#2ebfaf"
                offset-y
                max-width="290px"
                min-width="290px"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                    v-model="time1"
                    label=" TIME"
                    readonly
                    color="#2ebfaf"
                    v-bind="attrs"
                    v-on="on"
                ></v-text-field>
              </template>
              <v-time-picker
                  v-if="menu3"
                  v-model="time1"
                  color="#2ebfaf"
                  full-width
                  @click:minute="$refs.menu3.save(time1)"
              ></v-time-picker>
            </v-menu>

          </v-col>



          <v-col
              cols="1"
              style="padding-left: 0;margin-top: 22px;padding-right: 2px;"
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

          <v-col
              cols="1"
              style="padding-left: 0;margin-top: 22px;padding-right: 15px"
          >
            <v-menu
                ref="menu4"
                v-model="menu4"
                :close-on-content-click="false"
                :nudge-right="40"
                :return-value.sync="time2"
                transition="scale-transition"
                offset-y
                color="#2ebfaf"
                max-width="290px"
                min-width="290px"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                    v-model="time2"
                    label=" TIME"
                    readonly
                    color="#2ebfaf"
                    v-bind="attrs"
                    v-on="on"
                ></v-text-field>
              </template>
              <v-time-picker
                  v-if="menu4"
                  v-model="time2"
                  color="#2ebfaf"
                  full-width
                  @click:minute="$refs.menu4.save(time2)"
              ></v-time-picker>
            </v-menu>

          </v-col>

          <v-col cols="1" style="margin-top: 6px;">
            <v-switch v-model="switch1" :disabled="!isNumber" messages="数据简要图" color="#2ebfaf"></v-switch>
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
              label-size="3"
              auto-draw
          ></v-sparkline>


          <v-data-table
              v-show="!switch1"
              :items-per-page.sync="rowNums"
              :page.sync="pageNum"
              :server-items-length="itemNums"
              :headers="headers"
              :items="dataValue"
              class="elevation-1"
              style="margin-left: 30px;margin-right: 30px"
          ></v-data-table>


        </template>





      </v-card>



    </v-main>
  </div>
</template>

<script>

import request from "@/utils/request";

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
      isNumber: null,
      menu1: false,
      menu2: false,
      switch1: false,
      dateStart:new Date().toISOString().substr(0, 10),
      dateEnd:new Date().toISOString().substr(0, 10),
      deviceCh: null,
      cfgCh: null,
      items: [

      ],
      cfgS: [

      ],

      time1: '00:00',
      time2: '23:59',
      menu3: false,
      menu4: false,

      width: 3,
      radius: 10,
      padding: 16,
      lineCap: 'round',
      gradient: gradients[5],
      value: [],
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
          value: 'deviceDataId',
        },
        { text: '数据接收时间', sortable: false,value: 'createTime' },
        { text: '数据值', sortable: false,value: 'data' },
        { text: '协议', sortable: false,value: 'protocol' },
        { text: '设备编码',sortable: false, value: 'equipmentNo' },
      ],
      dataValue: [
      ],
    }
  },

  async created() {
    await request.get("/platform/deviceInfo/briefList").then(res => {
      console.log(res)
      this.items = res.data;
      this.deviceCh = this.items.at(0).value
    });

    if (this.deviceCh != null && this.deviceCh !== '') {
      await request.get("/platform/deviceCfg/briefList/" +  this.deviceCh).then(res => {
        console.log(res)
        this.cfgS = res.data;
        this.cfgCh = this.cfgS.at(0).value
      });
    }

    if (this.cfgCh != null && this.cfgCh !== '') {
      await request.get("/platform/deviceData/listP/" + this.pageNum + "/" + this.rowNums + "/" + this.cfgCh + "/"
          + this.dateStart + ' ' + this.time1 + ':00' + "/" + this.dateEnd + ' ' + this.time2 + ':59').then(res => {
        console.log(res)
        this.dataValue = res.data;
        this.itemNums = res.total;
        this.isNumber = res.isNumber;
      });
    }
  },

  watch: {
    deviceCh() {
      this.loadCfg();
    },
    cfgCh() {
      this.load();
    },
    dateStart() {
      this.load();
    },
    dateEnd() {
      this.load();
    },
    pageNum() {
      this.load();
    },
    rowNums() {
      this.load();
    },
    time1() {
      this.load();
    },
    time2() {
      this.load();
    },
    switch1() {
      this.value = [];
      if (this.switch1 === true) {
        if (this.isNumber === true) {
          request.get("/platform/deviceData/value/"  + this.cfgCh + "/"
              + this.dateStart + ' ' + this.time1 + ':00' + "/" + this.dateEnd + ' ' + this.time2 + ':59').then(res => {
            console.log(res)
            this.value = res.data;
          });
        }else
          this.switch1 = false;
      }
    }

  },

  methods: {
    load() {
      if (this.deviceCh != null && this.deviceCh !== '') {
        request.get("/platform/deviceData/listP/" + this.pageNum + "/" + this.rowNums + "/" + this.cfgCh + "/"
            + this.dateStart + ' ' + this.time1 + ':00' + "/" + this.dateEnd + ' ' + this.time2 + ':59').then(res => {
          console.log(res)
          this.dataValue = res.data;
          this.itemNums = res.total;
          this.isNumber = res.isNumber;
        });
      }
    },
    loadCfg() {
      request.get("/platform/deviceCfg/briefList/" +  this.deviceCh).then(res => {
        console.log(res)
        this.cfgS = res.data;
        this.cfgCh = this.cfgS.at(0).value
      });
    },
    formTitle() {

    }
  }

}
</script>

<style scoped>

</style>
