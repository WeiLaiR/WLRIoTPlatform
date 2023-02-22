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
                     @click="newCfg"
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
                    <span class="headline">修改设备配置信息：</span>
                  </v-card-title>

                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field v-model="editedItem.deviceCfgId" disabled label="Device Cfg ID"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field v-model="editedItem.typeName" label="Type Name"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field v-model="editedItem.typeNickName" label="Type Nick Name"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="6">
                          <v-select :items="IS" v-model="editedItem.isNumber" disabled label="Is Number?"></v-select>
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

          <v-dialog v-model="dialogNewDeviceCfg" width="600px">
            <v-card>
              <v-card-title >
                <span class="headline" style="margin: 12px">添加新的设备配置：</span>
              </v-card-title>


              <v-row style="width: 580px">

                <v-col cols="1">

                </v-col>

                <v-col cols="10">

                  <v-text-field
                      light
                      color="#2ebfaf"

                      v-model="typeNickName"
                      label="Type Nick Name"
                  >
                  </v-text-field>

                </v-col>
                <v-col cols="1">

                </v-col>


                <v-col cols="1">

                </v-col>

                <v-col cols="10">

                  <v-text-field
                      light
                      color="#2ebfaf"

                      v-model="typeName"
                      label="Type Name"
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

                      v-model="isNumber"
                      label="Is Number?"
                  >
                  </v-select>

                </v-col>

              </v-row>


              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" style="min-width: 80px;margin: 20px" @click="dialogNewDeviceCfg = false">CLOSE</v-btn>
                <v-btn color="#ff3f6f" style="min-width: 80px;margin: 20px" @click="createCfg">NEXT</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

        </div>


        <div>

          <v-dialog v-model="dialogDeleteCfg" width="600px">
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
                <v-btn color="green darken-1" style="min-width: 80px;margin: 20px" @click="dialogDeleteCfg = false">CLOSE</v-btn>
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
  name: "DeviceSetting",
  data() {
    return {
      deviceCh: null,
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

      typeNickName: '',
      typeName: '',
      isNumber: null,
      dialogNewDeviceCfg: false,
      dialogDeleteCfg: false,
      dDeviceCfgId: 0,


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
        { text: '数据昵称', sortable: false, value: 'typeNickName' },
        { text: '数据名称', sortable: false, value: 'typeName' },
        { text: '类型是否为数字', sortable: false, value: 'isNumber' },
        { text: '创建时间', sortable: false, value: 'createTime' },
        { text: '操作',  value: 'actions', sortable: false },
      ],
      dataCfg: [],
      editedIndex: -1,
      editedItem: {
        deviceCfgId: '',
        typeNickName: '',
        typeName: '',
        isNumber: false,
        createTime: '',
      },
      defaultItem: {
        deviceCfgId: '',
        typeNickName: '',
        typeName: '',
        isNumber: false,
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

    deleteItem() {
      if (this.dDeviceCfgId !== 0 && this.dDeviceCfgId !== null) {
        request.delete("/platform/deviceCfg/deleteDeviceCfg/" + this.dDeviceCfgId).then(res => {
          if (res.status === 200) {
            this.$message.success(res.message);
            this.dialogDeleteCfg = false;
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
        if (this.editedItem.typeName === '' || this.editedItem.typeName === null) {
          this.$message.error("请输入数据名称");
        }else {
          Object.assign(this.dataCfg[this.editedIndex], this.editedItem)
          request.post("/platform/deviceCfg/updateDeviceCfg", {
            deviceCfgId: this.dataCfg[this.editedIndex].deviceCfgId,
            typeName: this.editedItem.typeName,
            typeNickName: this.editedItem.typeNickName
          }).then(res => {
            if (res.status === 200) {
              this.$message.success(res.message);
              this.close()
            }
          });
          request.delete("/platform/deviceInfo/deleteRedisDeviceCfg/" + this.deviceCh)
        }
      } else {
        this.dataCfg.push(this.editedItem)
        this.close()
      }

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

    newCfg() {
      this.typeName = '';
      this.typeNickName = '';
      this.isNumber = null;
      this.dialogNewDeviceCfg = true;
    },

    createCfg() {
      if (this.typeName === '' || this.typeNickName === '' || this.typeName === null || this.typeNickName === null) {
        this.$message.error("请输入数据名称");
        return;
      } else if (this.isNumber === null) {
        this.$message.error("请选择类型是否为数字");
        return;
      } else if (this.deviceCh === null) {
        this.$message.error("请选择设备");
        return;
      } else {
        request.put("/platform/deviceCfg/newDeviceCfg", {
          deviceId: this.deviceCh,
          typeName: this.typeName,
          isNumber: this.isNumber,
          typeNickName: this.typeNickName,
        }).then(res => {
          if (res.status === 200) {
            this.$message.success(res.message);
            this.dialogNewDeviceCfg = false;
            this.load();
          }
        });
        request.delete("/platform/deviceInfo/deleteRedisDeviceCfg/" + this.deviceCh)
      }
      console.log('CREATE CFG')
    },

    deleteDialog(item) {
      this.dDeviceCfgId = item.deviceCfgId;
      this.dialogDeleteCfg = true;
    },
  }
}
</script>

<style scoped>

</style>
