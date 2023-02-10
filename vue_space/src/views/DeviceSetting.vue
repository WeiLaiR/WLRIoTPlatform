<template>
  <div style="height: 100%">
    <v-main style="margin: 0;padding: 0;height: 100%">

      <div style="margin: 10px;padding-left: 15px">
        <div class="teal--text" style="font-size: 26px">设备配置</div>
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

        <div>

          <v-row>

            <v-col
              cols="6"
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

              <!--       空位置       -->

            </v-col>

            <v-col
                cols="3"
            >
              <v-btn right
                     absolute
                     elevation="5"
                     large rounded
                     color="#2ebfaf"
                     dark
                     style="display:inline;margin-top: 27px;margin-right: 15px"
              >
                NEW CONFIG
              </v-btn>

            </v-col>


          </v-row>

        </div>



        <div>

          <v-data-table
              :headers="headers"
              :items="dataCfg"
              :items-per-page.sync="rowNums"
              :page.sync="pageNum"
              :server-items-length="itemNums"
              class="elevation-1"
              style="margin-left: 30px;margin-right: 30px"
          >

            <!--      弹出的修改框      -->
            <template v-slot:top>
              <v-dialog v-model="dialog" max-width="500px">
                <v-card>
                  <v-card-title>
                    <span class="headline">{{ formTitle }}</span>
                  </v-card-title>

                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.name" label="Device ID"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.calories" label="Device Name"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.fat" label="Create Time"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.carbs" label="Description"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.protein" label="Status"></v-text-field>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-card-text>

                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                    <v-btn color="blue darken-1" text @click="save">Save</v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </template>

            <!--      两个按键      -->

            <template v-slot:item.actions="{ item }">
              <v-icon
                  small
                  color="#2ebfaf"
                  class="mr-2"
                  @click="editItem(item)"
              >
                mdi-pencil
              </v-icon>

              <v-icon
                  small
                  color="#ff3f6f"
                  @click="deleteItem(item)"
              >
                mdi-delete
              </v-icon>
            </template>


          </v-data-table>

        </div>



      </v-card>



    </v-main>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "DeviceSetting",
  data() {
    return {
      deviceCh: null,
      items: [
      ],


      rowNums: 10,
      pageNum: 1,
      itemNums: 10,


      dialog: false,
      headers: [
        {
          text: '配置ID',
          align: 'start',
          sortable: false,
          value: 'deviceCfgId',
        },
        { text: '数据名称', sortable: false, value: 'typeName' },
        { text: '类型是否为数字', sortable: false, value: 'isNumber' },
        { text: '创建时间', sortable: false, value: 'createTime' },
        { text: '操作',  value: 'actions', sortable: false },
      ],
      dataCfg: [],
      editedIndex: -1,
      editedItem: {
        deviceCfgId: '',
        typeName: 0,
        isNumber: 0,
        createTime: 0,
        protein: 0,
      },
      defaultItem: {
        deviceCfgId: '',
        typeName: 0,
        isNumber: 0,
        createTime: 0,
        protein: 0,
      },



    }
  },

  // 监听dialog的变化，当dialog为false时，关闭对话框
  watch: {
    dialog (val) {
      val || this.close()
    },
    deviceCh() {
      this.load();
    },
    pageNum () {
      this.load();
    },
    rowNums () {
      this.load();
    },
  },


  // 启动后触发此触发器填充假数据
  async created () {

    await request.get("/platform/deviceInfo/briefList").then(res => {
      console.log(res)
      this.items = res.data;
      this.deviceCh = this.items.at(0).value
    });


    if (this.deviceCh != null && this.deviceCh !== '') {
      request.get("/platform/deviceCfg/listP/" + this.pageNum + "/" + this.rowNums + "/" + this.deviceCh).then(res => {
        console.log(res)
        this.dataCfg = res.data;
        this.itemNums = res.total;
      });
    }

  },

  methods: {

    editItem(item) {
      this.editedIndex = this.dataCfg.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },

    deleteItem(item) {
      const index = this.dataCfg.indexOf(item)
      confirm('Are you sure you want to delete this item?') && this.dataCfg.splice(index, 1)
    },

    close() {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    save() {
      if (this.editedIndex > -1) {
        Object.assign(this.dataCfg[this.editedIndex], this.editedItem)
      } else {
        this.dataCfg.push(this.editedItem)
      }
      this.close()
    },


    load() {
      if (this.deviceCh != null && this.deviceCh !== '') {
        request.get("/platform/deviceCfg/listP/" + this.pageNum + "/" + this.rowNums + "/" + this.deviceCh).then(res => {
          console.log(res)
          this.dataCfg = res.data;
          this.itemNums = res.total;
        });
      }
    },
    formTitle() {

    }
  }
}
</script>

<style scoped>

</style>
