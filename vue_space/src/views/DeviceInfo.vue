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
                            <v-btn
                                    right
                                    elevation="5"
                                    rounded
                                    color="#2ebfaf"
                                    dark
                                    style="position: absolute; margin-top: 30px;margin-left: 30px"
                                    @click="openTip"
                            >
                                tip
                            </v-btn>

                        </v-col>

                        <v-col
                                cols="4"
                        >
                            <div style="color: #666666FF;font-size: 18px;position: absolute;right: 185px;top: 29px">
                                {{ this.startNumber }} - {{ this.endNumber }} of {{ this.itemNums }}
                                <v-icon size="42px" @click="minusOne" color="#00C3BEFF">
                                    mdi-arrow-left-bold-box-outline
                                </v-icon>
                                <v-icon size="42px" @click="addOne" color="#00C3BEFF">mdi-arrow-right-bold-box-outline
                                </v-icon>
                            </div>


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

                                <v-card height="195px" elevation="5" rounded="xl"
                                        style="padding: 20px 0 12px 12px;width: 100%">

                                    <div style="color: black;font-weight: 600">
                                        <v-icon color="black">mdi-server-network</v-icon>
                                        {{ info.deviceName }}&nbsp;&nbsp;
                                        <v-chip
                                                small
                                                color="#00C98BFF"
                                                outlined
                                        >
                                            <v-icon left>mdi-alpha-v-box-outline</v-icon>
                                            version:{{ info.version }}
                                        </v-chip>
                                    </div>

                                    <v-img src="../assets/router.png" height="98px" width="102px"
                                           style="float: right;position: absolute;right: 5px;top: 20px;margin-right: 10px"></v-img>

                                    <div style="margin-top: 9px;position: relative;">
                                        <v-chip
                                                label
                                                small
                                                :color="info.status === '在线' ? '#00C98BFF' : info.protocol === null? '#9C9C9CFF' : '#FFB300FF'"
                                                outlined
                                                style="margin-left: 3px"
                                        >
                                            {{ info.status === '在线' ? info.status : info.protocol === null ? '未激活' : '离线' }}
                                        </v-chip>

                                        <v-chip
                                                label
                                                small
                                                :color="info.protocol === null ? '#9C9C9CFF' : info.protocol === 'CoAP' ? '#00D0F1FF' : info.protocol === 'MQTT' ? '#FF0081FF' : '#6E00FFFF'"
                                                outlined
                                                style="margin-left: 6px"
                                        >
                                            {{ info.protocol === null ? 'NULL' : info.protocol }}
                                        </v-chip>

                                    </div>

                                    <div style="margin-top: 6px;margin-left: 4px;font-size: 13px">
                                        设备ID: {{ info.deviceId }}
                                    </div>

                                    <div style="margin-top: 0;margin-left: 4px;font-size: 13px">
                                        描述信息: {{ info.description }}
                                    </div>

                                    <div style="margin-top: 0;margin-left: 4px;font-size: 13px">
                                        创建时间: {{ info.createTime }}
                                    </div>

                                    <div style="margin: 3px 0 0 0;" align="center">

                                        <v-btn dark small color="#4602FCFF" @click="editItem(info)" style="margin: 1px">
                                            <v-icon left>mdi-pencil</v-icon>
                                            修改
                                        </v-btn>
                                        <v-btn :dark="info.protocol === 'MQTT'" small color="#0077FFFF"
                                               @click="openSender(info)" style="margin: 1px"
                                               :disabled="info.protocol !== 'MQTT'">
                                            <v-icon left>mdi-message-arrow-right</v-icon>
                                            消息
                                        </v-btn>
                                        <v-btn dark small color="#00CC43FF" @click="openCreateToken(info)"
                                               style="margin: 3px">重置密钥
                                        </v-btn>
                                        <v-btn small color="error" @click="deleteDialog(info)" style="margin: 1px">
                                            <v-icon left>mdi-delete-forever</v-icon>
                                            删除
                                        </v-btn>

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
                                            <v-text-field v-model="editedItem.deviceId" disabled
                                                          label="Device ID"></v-text-field>
                                        </v-col>
                                        <v-col cols="12" sm="6" md="6">
                                            <v-text-field v-model="editedItem.deviceName"
                                                          label="Device Name"></v-text-field>
                                        </v-col>
                                        <v-col cols="12" sm="6" md="6">
                                            <v-text-field v-model="editedItem.createTime" disabled
                                                          label="Create Time"></v-text-field>
                                        </v-col>
                                        <v-col cols="12" sm="6" md="6">
                                            <v-text-field v-model="editedItem.description"
                                                          label="Description"></v-text-field>
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
                            <v-card-title>
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
                                <v-btn color="green darken-1" style="min-width: 80px;margin: 20px"
                                       @click="dialogNewDevice = false">CLOSE
                                </v-btn>
                                <v-btn color="#ff3f6f" style="min-width: 80px;margin: 20px" @click="addDeviceInfo">YES
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>

                </div>

                <div>

                    <v-dialog v-model="dialogToken" width="600px">
                        <v-card>
                            <v-card-title>
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
                                <v-btn color="green darken-1" style="min-width: 80px;margin: 20px"
                                       @click="dialogToken = false">CLOSE
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>

                </div>


                <div>
                    <v-dialog v-model="dialogCreateToken" width="600px">
                        <v-card>
                            <v-card-title>
                                <span class="headline" style="margin: 12px">警告：</span>
                            </v-card-title>


                            <div style="padding: 10px 56px;font-size: 15px;color: red">
                                您正在正在进行高风险操作！该行为会使您当前设备的Token失效，并为您生成新的Token！
                            </div>


                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="green darken-1" style="min-width: 80px;margin: 20px"
                                       @click="dialogCreateToken = false">CLOSE
                                </v-btn>
                                <v-btn color="#ff3f6f" style="min-width: 80px;margin: 20px" @click="CreateToken">NEXT
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                </div>


                <div>

                    <v-dialog v-model="dialogDeleteInfo" width="600px">
                        <v-card>
                            <v-card-title>
                                <span class="headline" style="margin: 12px">警告：</span>
                            </v-card-title>


                            <v-row style="width: 580px">

                                <v-col cols="1">

                                </v-col>

                                <v-col cols="10" style="color: red">

                                    你确定要删除当前的设备信息吗？其相关数据也会随之删除。

                                </v-col>
                                <v-col cols="1">

                                </v-col>

                            </v-row>


                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="green darken-1" style="min-width: 80px;margin: 20px"
                                       @click="dialogDeleteInfo = false">CLOSE
                                </v-btn>
                                <v-btn color="#ff3f6f" style="min-width: 80px;margin: 20px" @click="deleteItem">NEXT
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>

                </div>

                <div>
                    <v-dialog
                            v-model="dltip"
                            width="900px"
                    >

                        <v-card>

                            <v-card-title>
                                Tip:
                            </v-card-title>

                            <div style="padding: 5px 35px">
                                点击右上角的”NEW
                                DEVICE“按钮并输入对应信息即可添加设备，填写并成功提交后请及时保存系统提示的Token，该Token是您设备连接服务器的唯一凭证，请勿泄露！
                                如Token已经泄露，请点击设备卡片上的”重置密钥“按钮，系统将为您生成新的Token，之前的Token将立即作废。
                            </div>

                            <v-divider style="margin: 10px"></v-divider>

                            <div style="padding: 5px 35px">
                                如下是当前平台支持的协议，您可从中任选一种协议将您的设备接入平台
                            </div>


                            <v-icon color="#55A8D8FF" size="20px" style="position: absolute;left: 35px;top: 204px">
                                mdi-star-four-points-outline
                            </v-icon>
                            <div style="padding: 7px 35px 5px 65px">

                                HTTP协议： 请接入
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">
                                    http://iot.futureproton.com:6666
                                </v-card>
                            </div>


                            <v-icon color="#55A8D8FF" size="20px" style="position: absolute;left: 35px;top: 240px">
                                mdi-star-four-points-outline
                            </v-icon>
                            <div style="padding: 7px 35px 5px 65px">

                                CoAP协议： 请接入
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">
                                    coap://iot.futureproton.com:6667/coapTelemetry
                                </v-card>
                            </div>


                            <v-icon color="#55A8D8FF" size="20px" style="position: absolute;left: 35px;top: 280px">
                                mdi-star-four-points-outline
                            </v-icon>
                            <div style="padding: 7px 35px 5px 65px;line-height: 34px">
                                MQTT协议： 请设置broker为
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">
                                    tcp://20.40.51.147:1886
                                </v-card>
                                ，
                                设置topic为
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">
                                    mqtt/wlr
                                </v-card>
                                ，
                                设置username为
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">
                                    wlruser
                                </v-card>
                                ，
                                设置password为
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">wlriot
                                </v-card>
                                ，
                                设置clientid为您的设备Token.
                            </div>

                            <v-divider style="margin: 10px"></v-divider>

                            <div style="padding: 5px 35px">
                                设备发送消息体规则如下：
                            </div>
                            <v-icon color="#55A8D8FF" size="20px" style="position: absolute;left: 35px;top: 415px">
                                mdi-star-four-points-outline
                            </v-icon>
                            <div style="padding: 7px 35px 5px 65px;line-height: 32px">
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">token
                                </v-card>
                                : <p style="display: inline;font-weight: bold">必须</p> ,为平台分配给您的设备Token(使用HTTP协议接入平台时应同时将token放入Header中用以验证)。
                            </div>

                            <v-icon color="#55A8D8FF" size="20px" style="position: absolute;left: 35px;top: 459px">
                                mdi-star-four-points-outline
                            </v-icon>
                            <div style="padding: 7px 35px 5px 65px;line-height: 32px">
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">
                                    version
                                </v-card>
                                : <p style="display: inline;">可选</p> ,默认为1，为设备版本号。
                            </div>


                            <v-icon color="#55A8D8FF" size="20px" style="position: absolute;left: 35px;top: 502px">
                                mdi-star-four-points-outline
                            </v-icon>
                            <div style="padding: 7px 35px 5px 65px;line-height: 32px">
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">
                                    equipment_no
                                </v-card>
                                : <p style="display: inline;">可选</p> ,当接入的设备为一个集群时可用此数据分辨不同设备发送来的消息。
                            </div>

                            <div style="padding: 7px 35px 5px 50px">
                                在消息体中应将数据以key、value键值对的方式存储，中间使用
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">=
                                </v-card>
                                连接，每个键值对之间使用
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">&
                                </v-card>
                                连接。
                            </div>

                            <div style="padding: 5px 35px 5px 55px">
                                示例：
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">
                                    token=xxxxxxxxxxxxxxxxxxxxxx&version=1.x&equipment_no=NO.1
                                </v-card>
                            </div>

                            <v-divider style="margin: 10px"></v-divider>

                            <div style="padding: 7px 35px 5px 35px">
                                目前仅支持使用Mqtt协议向设备下发命令。当准备使用Mqtt协议接收消息时，应配置topic为
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">re/设备Token
                                </v-card>
                                 如
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">re/O2KirsoJqwOQL-bMotqNd5
                                </v-card>
                                 配置username为
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">wlruser
                                </v-card>
                                 配置password为
                                <v-card elevation="3" style="display: inline;padding: 3px 8px;color: #696969FF">wlriot
                                </v-card>
                                 用以接收消息。
                            </div>

                            <v-divider style="margin: 10px"></v-divider>

                            <div style="padding: 5px 35px 25px 35px">
                                设备接入平台后，应至少每十分钟向平台发送一次信息，否则设备将被标记为离线状态。设备进入离线状态后，若设备向平台成功发送数据，设备将被标记为在线。
                            </div>


                        </v-card>


                    </v-dialog>


                    <div>

                        <v-dialog v-model="dialogMqttSender" width="700px">
                            <v-card>
                                <v-card-title>
                                    <span class="headline" style="margin: 12px">{{this.SSSname}}信息发送：</span>
                                </v-card-title>

                                <div style="margin-left: 70px;font-size: 13px;color: #FF5252">当前仅支持Mqtt协议</div>


                                <v-row style="width: 660px">

                                    <v-col cols="11" style="margin: 15px 20px 0 50px">
                                        <v-textarea
                                            v-model="messageMqtt"
                                            filled
                                            outlined
                                            auto-grow
                                            label="输入需要发送的消息"
                                            rows="5"
                                            row-height="30"
                                        ></v-textarea>
                                    </v-col>


                                </v-row>


                                <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn color="green darken-1" style="min-width: 80px;margin: 20px"
                                           @click="dialogMqttSender = false">CLOSE
                                    </v-btn>
                                    <v-btn color="#ff3f6f" style="min-width: 80px;margin: 20px" @click="SendMqttMessage">
                                        SEND
                                    </v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>

                    </div>
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
            dialogDeleteInfo: false,
            dDeviceInfoId: 0,
            dialogMqttSender: false,
            SSSname: '',
            SSSdid: 0,
            messageMqtt: '',


            dltip: false,


            dialog: false,
            headers: [
                {
                    text: '设备ID',
                    align: 'start',
                    sortable: false,
                    value: 'deviceId',
                },
                {text: '设备名称', value: 'deviceName'},
                {text: '创建时间', value: 'createTime'},
                {text: '描述信息', value: 'description'},
                {text: '状态', value: 'status'},
                {text: '操作', value: 'actions', sortable: false},
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
        dialog(val) {
            val || this.close()
        },
        rowNums() {
            this.load();
        },
        dialogToken(val) {
            if (val === false) {
                this.deviceToken = '';
            }
        },
        // deviceSearch (newVal, oldVal) {
        //   console.log('newVal' + newVal + 'OLD' + oldVal);
        // }
    },

    // 启动后触发此触发器填充假数据
    created() {
        this.load();
    },

    methods: {


        editItem(item) {
            this.editedIndex = this.infoData.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialog = true
        },

        deleteItem() {
            if (this.dDeviceInfoId === '' || this.dDeviceInfoId === null || this.dDeviceInfoId === 0) {
                this.$message.error('表单数据异常！');
                return;
            }
            request.delete("platform/deviceInfo/delete/" + this.dDeviceInfoId).then(res => {
                if (res.status === 200) {
                    this.$message.success(res.message);
                    this.load();
                    this.dialogDeleteInfo = false;
                }
            })
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
                if (this.editedItem.deviceName === '' || this.editedItem.deviceName === null) {
                    this.$message.error('设备名称不能为空');
                    return;
                } else if (this.editedItem.description === '' || this.editedItem.description === null) {
                    this.$message.error('设备描述不能为空');
                    return;
                } else {
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
                this.endNumber = this.pageNum * this.rowNums < this.itemNums ? this.pageNum * this.rowNums : this.itemNums;
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

            } else if (this.description === '' || this.description === null) {
                this.$message.error('设备描述不能为空');

            } else {
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
                this.pageNum--;
                this.load()
            }
        },
        addOne() {
            if (this.pageNum < Math.ceil(this.itemNums / this.rowNums)) {
                this.pageNum++;
                this.load()
            }
        },

        deleteDialog(item) {
            this.dDeviceInfoId = item.deviceId;
            this.dialogDeleteInfo = true;
        },

        openTip() {
            this.dltip = true;
        },

        openSender(item) {
            this.dialogMqttSender = true;
            this.SSSname = item.deviceName;
            this.SSSdid = item.deviceId;
        },

        SendMqttMessage() {
            if (this.messageMqtt === '' || this.messageMqtt === null) {
                this.$message.error("请输入需要发送的内容！")
            }else {
                request.post("platform/deviceInfo/sendMqttMessage", {
                    did: this.SSSdid,
                    message: this.messageMqtt
                }).then(res => {
                    if (res.status === 200) {
                        this.$message.success(res.message);
                        this.messageMqtt = '';
                    }
                });
            }
        }


    },


}
</script>

<style scoped>

</style>
