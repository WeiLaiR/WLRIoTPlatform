<template>
  <div style="height: 100%">
    <v-main style="margin: 0;padding: 0;height: 100%">

      <div style="margin: 10px;padding-left: 15px">
        <div class="teal--text" style="font-size: 26px">设备信息</div>
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

          <v-row style="margin-bottom: 0;padding-bottom: 0">

            <v-col
                cols="6"
            >
              <v-text-field
                  label="输入设备id或设备名称"
                  filled
                  style="margin-left: 30px;margin-top: 22px;position: absolute;width: 46%"
                  color="#2ebfaf"
                  append-icon="mdi-magnify"
                  @blur="load"
                  v-model="deviceSearch">

              </v-text-field>

            </v-col>

            <v-col
                cols="2"
            >

              <!--       空位置       -->

            </v-col>

            <v-col
                cols="4"
            >
              <div style="color: #666666FF;font-size: 18px;position: absolute;right: 185px;top: 29px"> {{this.startNumber}} - {{this.endNumber}} of {{this.itemNums}}  <v-icon size="42px" @click="minusOne" color="#00CDFFFF">mdi-arrow-left-bold-box-outline</v-icon> <v-icon size="42px" @click="addOne" color="#00CDFFFF">mdi-arrow-right-bold-box-outline</v-icon></div>


              <v-btn right
                     absolute
                     elevation="5"
                     large rounded
                     color="#2ebfaf"
                     @click="openNew"
                     dark
                     style="display:inline;margin-top: 27px;margin-right: 15px"
              >
                NEW DEVICE
              </v-btn>

            </v-col>

          </v-row>


        </div>


        <div style="margin: 100px 45px 0 45px">

          <v-row>

            <template v-for="(info, index) in infoData">


              <v-col cols="4" :key="index" style="padding: 0 18px;margin-bottom: 23px">

                <v-card height="195px" elevation="5" rounded="xl" style="padding: 20px 0 12px 12px">

                  <div style="color: black;font-weight: 600"  >
                    <v-icon color="black">mdi-server-network</v-icon>
                    {{info.deviceName}}
                    <v-chip
                        small
                        color="#00C98BFF"
                        outlined
                    >
                      <v-icon left>mdi-alpha-v-box-outline</v-icon>
                      version:1.1
                    </v-chip>
                  </div>

                  <v-img src="../assets/router.png"  height="98px" width="102px" style="float: right;position: absolute;right: 5px;top: 20px;margin-right: 10px"></v-img>

                  <div style="margin-top: 9px;position: relative;">
                    <v-chip
                        label
                        small
                        color="success"
                        outlined
                        style="margin-left: 3px"
                    >
                      在线
                    </v-chip>

                    <v-chip
                        label
                        small
                        color="#00D0F1FF"
                        outlined
                        style="margin-left: 6px"
                    >
                      MQTT
                    </v-chip>

                  </div>

                  <div style="margin-top: 6px;margin-left: 4px;font-size: 13px">
                    设备ID: {{info.deviceId}}
                  </div>

                  <div style="margin-top: 0;margin-left: 4px;font-size: 13px">
                    描述信息: {{info.description}}
                  </div>

                  <div style="margin-top: 0;margin-left: 4px;font-size: 13px">
                    创建时间: {{info.createTime}}
                  </div>

                  <div style="margin: 3px 70px 0 70px">

                    <v-btn dark small color="#0077FFFF" @click="editItem(info)" style="margin: 1px"><v-icon left>mdi-pencil</v-icon>修改</v-btn>
                    <v-btn dark small color="#00CC43FF" @click="openCreateToken(info)" style="margin: 3px">重置密钥</v-btn>
                    <v-btn small color="error" @click="deleteItem(info)" style="margin: 1px"><v-icon left>mdi-delete-forever</v-icon>删除</v-btn>

                  </div>

                </v-card>

              </v-col>

            </template>



          </v-row>


        </div>

        <div>

          <v-dialog v-model="dialog" max-width="500px">
            <v-card>
              <v-card-title>
                <span class="headline">修改设备基本信息：</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="6" md="6">
                      <v-text-field v-model="editedItem.deviceId" disabled label="Device ID"></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="6">
                      <v-text-field v-model="editedItem.deviceName" label="Device Name"></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="6">
                      <v-text-field v-model="editedItem.createTime" disabled label="Create Time"></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="6">
                      <v-text-field v-model="editedItem.description" label="Description"></v-text-field>
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

        </div>



        <div>

          <v-dialog v-model="dialogNewDevice" width="600px">
            <v-card>
              <v-card-title >
                <span class="headline" style="margin: 12px">添加新的设备：</span>
              </v-card-title>


              <v-row style="width: 580px">
                <v-col cols="1">

                </v-col>

                <v-col cols="10">

                  <v-form
                      ref="loginForm"
                      v-model="valid"
                  >
                    <v-text-field
                        light
                        color="#2ebfaf"

                        v-model="deviceName"
                        label="Device Name"
                    >
                    </v-text-field>
                  </v-form>

                </v-col>
                <v-col cols="1">

                </v-col>

                <v-col cols="1">

                </v-col>

                <v-col cols="10">

                  <v-form
                      ref="loginForm1"
                      v-model="valid"
                  >
                    <v-text-field
                        light
                        color="#2ebfaf"

                        v-model="description"
                        label="Device Description"
                    >
                    </v-text-field>
                  </v-form>

                </v-col>

              </v-row>




              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" style="min-width: 80px;margin: 20px" @click="dialogNewDevice = false">CLOSE</v-btn>
                <v-btn color="#ff3f6f" style="min-width: 80px;margin: 20px" @click="addDeviceInfo">YES</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

        </div>

        <div>

          <v-dialog v-model="dialogToken" width="600px">
            <v-card>
              <v-card-title >
                <span class="headline" style="margin: 12px">Token：</span>
              </v-card-title>




              <v-row style="width: 590px">
                <v-col cols="3">

                </v-col>

                <v-col cols="6">

                  <v-text-field
                      light
                      color="#2ebfaf"
                      readonly
                      v-model="deviceToken"
                      label="Device Token"
                      style="margin-top: 15px"
                  >
                  </v-text-field>

                </v-col>

              </v-row>

              <div style="padding: 10px 56px;font-size: 13px;color: red">
                每个设备Token仅可展示一次，您无法再从服务器中获取当前Token，请您妥善保管，这是您的设备连接服务器的唯一凭证，请勿泄露或丢失！
              </div>


              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" style="min-width: 80px;margin: 20px" @click="dialogToken = false">CLOSE</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

        </div>


        <div>
          <v-dialog v-model="dialogCreateToken" width="600px">
            <v-card>
              <v-card-title >
                <span class="headline" style="margin: 12px">警告：</span>
              </v-card-title>



              <div style="padding: 10px 56px;font-size: 15px;color: red">
                您正在正在进行高风险操作！该行为会使您当前设备的Token失效，并为您生成新的Token！
              </div>


              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" style="min-width: 80px;margin: 20px" @click="dialogCreateToken = false">CLOSE</v-btn>
                <v-btn color="#ff3f6f" style="min-width: 80px;margin: 20px" @click="CreateToken">NEXT</v-btn>
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
  name: "DeviceInfo",
  data() {
    return {
      deviceSearch: "",
      rowNums: 9,
      pageNum: 1,
      itemNums: 10,
      startNumber: 0,
      endNumber: 0,
      dialogNewDevice: false,
      valid: false,
      description: '',
      deviceName: '',
      dialogToken: false,
      deviceToken: '',
      dialogCreateToken: false,
      CDid: 0,




      dialog: false,
      headers: [
        {
          text: '设备ID',
          align: 'start',
          sortable: false,
          value: 'deviceId',
        },
        { text: '设备名称', value: 'deviceName' },
        { text: '创建时间', value: 'createTime' },
        { text: '描述信息', value: 'description' },
        { text: '状态', value: 'status' },
        { text: '操作', value: 'actions', sortable: false },
      ],
      infoData: [],
      editedIndex: -1,
      editedItem: {
        deviceId: '',
        deviceName: 0,
        createTime: 0,
        description: 0,
        status: 0,
      },
      defaultItem: {
        deviceId: '',
        deviceName: 0,
        createTime: 0,
        description: 0,
        status: 0,
      },

    }
  },


  // 监听dialog的变化，当dialog为false时，关闭对话框
  watch: {
    dialog (val) {
      val || this.close()
    },
    rowNums () {
      this.load();
    },
    dialogToken (val) {
      if (val === false) {
        this.deviceToken = '';
      }
    },
    // deviceSearch (newVal, oldVal) {
    //   console.log('newVal' + newVal + 'OLD' + oldVal);
    // }
  },

  // 启动后触发此触发器填充假数据
  created () {
    this.load();
  },

  methods: {


    editItem (item) {
      this.editedIndex = this.infoData.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },

    deleteItem (item) {
      const index = this.infoData.indexOf(item)
      confirm('Are you sure you want to delete this item?') && this.infoData.splice(index, 1)
    },

    close () {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    save () {
      if (this.editedIndex > -1) {
        if (this.editedItem.deviceName === '' || this.editedItem.deviceName === null) {
          this.$message.error('设备名称不能为空');
          return;
        }else if (this.editedItem.description === '' || this.editedItem.description === null) {
          this.$message.error('设备描述不能为空');
          return;
        }else {
          Object.assign(this.infoData[this.editedIndex], this.editedItem)
          request.post("platform/deviceInfo/update", {
            deviceId: this.infoData[this.editedIndex].deviceId,
            deviceName: this.editedItem.deviceName,
            description: this.editedItem.description,
          }).then(res => {
            if (res.status === 200) {
              this.$message.success(res.message)
              this.close()
            }
          });
        }
      } else {
        this.infoData.push(this.editedItem)
        this.close()
      }
      console.log('save')
    },

    load() {
      request.get("platform/deviceInfo/listP/" + this.pageNum + "/" + this.rowNums + "/" + (this.deviceSearch === "" ? "null" : this.deviceSearch)).then(res => {
        console.log(res)
        this.infoData = res.data;
        this.itemNums = res.total;
        this.startNumber = (this.pageNum - 1) * this.rowNums + 1;
        this.endNumber = this.pageNum * this.rowNums;
      });
    },

    openNew() {
      this.deviceName = '';
      this.description = '';
      this.dialogNewDevice = true;
    },

    addDeviceInfo() {
      if (this.deviceName === '' || this.deviceName === null) {
        this.$message.error('设备名称不能为空');

      }else if (this.description === '' || this.description === null) {
        this.$message.error('设备描述不能为空');

      }else {
        request.post("platform/deviceInfo/add", {
          deviceName: this.deviceName,
          description: this.description,
        }).then(res => {
          if (res.status === 200) {
            this.$message.success(res.message)
            this.dialogNewDevice = false;
            this.deviceToken = res.deviceToken;
            this.dialogToken = true;
            this.load();
          }
        });
      }
    },

    openCreateToken(item) {
      this.CDid = item.deviceId;
      this.dialogCreateToken = true;
    },

    CreateToken() {
      if (this.CDid === 0 || this.CDid === null || this.CDid === '') {
        this.$message.error('设备ID不能为空');
      }
      request.post("platform/deviceInfo/createNewToken", this.CDid).then(res => {
        if (res.status === 200) {
          this.$message.success(res.message)
          this.dialogCreateToken = false;
          this.deviceToken = res.deviceToken;
          this.dialogToken = true;
        }
      });

    },
    minusOne() {
      if (this.pageNum > 1) {
        this.pageNum --;
        this.load()
      }
    },
    addOne() {
      if (this.pageNum < Math.ceil(this.itemNums / this.rowNums)) {
        this.pageNum ++;
        this.load()
      }
    },

  },
}
</script>

<style scoped>

</style>
