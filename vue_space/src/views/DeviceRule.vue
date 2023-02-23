<template>
  <div style="height: 100%">
    <v-main style="margin: 0;padding: 0;height: 100%">

      <div style="margin: 10px;padding-left: 15px">
        <div class="teal--text" style="font-size: 26px">规则管理</div>
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
                cols="5"
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

            <v-col cols="1">

              <v-tooltip bottom>
                <template  v-slot:activator="{ on, attrs }">
                  <v-icon color="#1ECBEEFF" size="30px" style="margin-top: 35px"  v-bind="attrs" v-on="on">
                    mdi-information-outline
                  </v-icon>
                </template>
                <span>目前仅支持整形、浮点型的数字类型进行规则过滤。</span>
              </v-tooltip>

            </v-col>

            <v-col
                cols="3"
            >
              <v-btn right
                     absolute
                     elevation="5"
                     large rounded
                     color="#2ebfaf"
                     @click="newRule"
                     dark
                     style="display:inline;margin-top: 27px;margin-right: 15px"
              >
                NEW RULE
              </v-btn>

            </v-col>


          </v-row>

        </div>



        <div>

          <v-data-table
              :headers="headers"
              :items="dataRule"
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
                    <span class="headline">修改设备配置信息：</span>
                  </v-card-title>

                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field v-model="editedItem.deviceRuleId" disabled label="Device Rule ID"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field v-model="editedItem.ruleName" label="Rule Name"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-select :items="IS" v-model="editedItem.isMeet" label="Is Meet"></v-select>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-select :items="Sym" v-model="editedItem.symbol" label="Symbol"></v-select>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field v-model="editedItem.value" label="Value"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field v-model="editedItem.createTime" disabled label="Create Time"></v-text-field>
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
                  @click="deleteDialog(item)"
              >
                mdi-delete
              </v-icon>
            </template>


          </v-data-table>

        </div>


        <div>

          <v-dialog v-model="dialogNewDeviceRule" width="600px">
            <v-card>
              <v-card-title >
                <span class="headline" style="margin: 12px">添加新的数据规则：</span>
              </v-card-title>


              <v-row style="width: 580px">

                <v-col cols="1">

                </v-col>

                <v-col cols="10">

                  <v-text-field
                      light
                      color="#2ebfaf"

                      v-model="nRule.ruleName"
                      label="Rule Name"
                  >
                  </v-text-field>

                </v-col>
                <v-col cols="1">

                </v-col>





                <v-col cols="1">

                </v-col>

                <v-col cols="10">

                  <v-select
                      light
                      color="#2ebfaf"
                      :items="IS"

                      v-model="nRule.isMeet"
                      label="Is Meet?"
                  >
                  </v-select>

                </v-col>

                <v-col cols="1">

                </v-col>


                <v-col cols="1">

                </v-col>

                <v-col cols="10">

                  <v-select
                      light
                      color="#2ebfaf"
                      :items="Sym"

                      v-model="nRule.symbol"
                      label="Symbol"
                  >
                  </v-select>

                </v-col>

                <v-col cols="1">

                </v-col>


                <v-col cols="1">

                </v-col>

                <v-col cols="10">

                  <v-text-field
                      light
                      color="#2ebfaf"

                      v-model="nRule.value"
                      label="Rule Value"
                  >
                  </v-text-field>

                </v-col>
                <v-col cols="1">

                </v-col>

              </v-row>


              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" style="min-width: 80px;margin: 20px" @click="dialogNewDeviceRule = false">CLOSE</v-btn>
                <v-btn color="#ff3f6f" style="min-width: 80px;margin: 20px" @click="createRule">NEXT</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

        </div>


        <div>

          <v-dialog v-model="dialogDeleteRule" width="600px">
            <v-card>
              <v-card-title >
                <span class="headline" style="margin: 12px">警告：</span>
              </v-card-title>


              <v-row style="width: 580px">

                <v-col cols="1">

                </v-col>

                <v-col cols="10" style="color: red">

                  你确定要删除当前的设备配置吗？其对应的数据也会随之删除。

                </v-col>
                <v-col cols="1">

                </v-col>

              </v-row>


              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" style="min-width: 80px;margin: 20px" @click="dialogDeleteRule = false">CLOSE</v-btn>
                <v-btn color="#ff3f6f" style="min-width: 80px;margin: 20px" @click="deleteItem">NEXT</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

        </div>



      </v-card>



    </v-main>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "DeviceRule",
  data() {
    return {
      deviceCh: null,
      cfgCh: null,
      cfgS: [],
      items: [
      ],

      IS: [
        {
          text: 'True',
          value: true
        },
        {
          text: 'False',
          value: false
        }
      ],

      Sym: [
        {
          text: '>',
          value: '>'
        },
        {
          text: '<',
          value: '<'
        },
        {
          text: '>=',
          value: '>='
        },
        {
          text: '<=',
          value: '<='
        },
        {
          text: '==',
          value: '=='
        },
        {
          text: '!=',
          value: '!='
        }
      ],

      nRule: {
        ruleName: '',
        isMeet: null,
        symbol: null,
        value: null,
        deviceCfgId: 0
      },
      dialogNewDeviceRule: false,
      dialogDeleteRule: false,
      dDeviceRuleId: 0,


      rowNums: 10,
      pageNum: 1,
      itemNums: 10,


      dialog: false,
      headers: [
        {
          text: '规则ID',
          align: 'start',
          sortable: false,
          value: 'deviceRuleId',
        },
        { text: '规则名', sortable: false, value: 'ruleName' },
        { text: '条件满足', sortable: false, value: 'isMeet' },
        { text: '比较符号', sortable: false, value: 'symbol' },
        { text: '值', sortable: false, value: 'value' },
        { text: '创建时间', sortable: false, value: 'createTime' },
        { text: '操作',  value: 'actions', sortable: false },
      ],
      dataRule: [],
      editedIndex: -1,
      editedItem: {
        deviceRuleId: '',
        ruleName: '',
        value: '',
        symbol: null,
        isMeet: null,
        createTime: '',
      },
      defaultItem: {
        deviceRuleId: '',
        ruleName: '',
        value: '',
        symbol: null,
        isMeet: null,
        createTime: '',
      },


    }
  },

  // 监听dialog的变化，当dialog为false时，关闭对话框
  watch: {
    dialog (val) {
      val || this.close()
    },
    deviceCh() {
      this.loadCfg();
    },
    pageNum () {
      this.load();
    },
    rowNums () {
      this.load();
    },
    cfgCh () {
      this.load()
    }
  },


  // 启动后触发此触发器填充假数据
  async created () {

    await request.get("/platform/deviceInfo/briefList").then(res => {
      console.log(res)
      this.items = res.data;
      this.deviceCh = this.items.at(0).value
    });

    if (this.deviceCh != null && this.deviceCh !== '') {
      await request.get("/platform/deviceCfg/briefListIsNumber/" +  this.deviceCh).then(res => {
        console.log(res)
        this.cfgS = res.data;
        this.cfgCh = this.cfgS.at(0).value
      });
    }

    if (this.cfgCh != null && this.cfgCh !== '') {
      this.load();
    }

  },

  methods: {

    editItem(item) {
      this.editedIndex = this.dataRule.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },

    deleteItem() {
      if (this.dDeviceRuleId !== 0 && this.dDeviceRuleId !== null) {
        request.delete("/platform/deviceRule/delete/" + this.dDeviceRuleId).then(res => {
          if (res.status === 200) {
            this.$message.success(res.message);
            this.dialogDeleteRule = false;
            this.load();
          }
        });
      }
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
        if (this.editedItem.ruleName === '' || this.editedItem.isMeet === null || this.editedItem.symbol === null || this.editedItem.value === '') {
          this.$message.error("请正确输入数据！");
        }else {
          Object.assign(this.dataRule[this.editedIndex], this.editedItem)
          request.post("/platform/deviceRule/update", {
            deviceRuleId: this.dataRule[this.editedIndex].deviceRuleId,
            ruleName: this.editedItem.ruleName,
            isMeet: this.editedItem.isMeet,
            symbol: this.editedItem.symbol,
            value: this.editedItem.value,
          }).then(res => {
            if (res.status === 200) {
              this.$message.success(res.message);
              this.close()
            }
          });
          request.delete("/platform/deviceRule/deleteRedisRule/" + this.cfgCh)

        }
      } else {
        this.dataRule.push(this.editedItem)
        this.close()
      }

    },


    load() {
      if (this.cfgCh != null && this.cfgCh !== '') {
        request.get("/platform/deviceRule/listP/" + this.pageNum + "/" + this.rowNums + "/" + this.cfgCh).then(res => {
          console.log(res)
          this.dataRule = res.data;
          this.itemNums = res.total;
        });
      }
    },

    loadCfg() {
      request.get("/platform/deviceCfg/briefListIsNumber/" +  this.deviceCh).then(res => {
        console.log(res)
        this.cfgS = res.data;
        this.cfgCh = this.cfgS.at(0).value
      });
    },

    newRule() {
      this.nRule.deviceCfgId = 0;
      this.nRule.ruleName = '';
      this.nRule.value = '';
      this.nRule.isMeet = null;
      this.nRule.symbol = null;
      this.dialogNewDeviceRule = true;
    },

    createRule() {
      if (this.nRule.ruleName === '' || this.nRule.value === '' || this.nRule.isMeet === null || this.nRule.symbol === null) {
        this.$message.error("请正确输入数据！");
        return;
      } else {
        this.nRule.deviceCfgId = this.cfgCh;
        request.put("/platform/deviceRule/add", this.nRule).then(res => {
          if (res.status === 200) {
            this.$message.success(res.message);
            this.dialogNewDeviceRule = false;
            this.load();
          }
        });
        request.delete("/platform/deviceRule/deleteRedisRule/" + this.cfgCh)

      }
      console.log('CREATE RULE')
    },

    deleteDialog(item) {
      this.dDeviceRuleId = item.deviceRuleId;
      this.dialogDeleteRule = true;
    },
  }
}
</script>

<style scoped>

</style>
