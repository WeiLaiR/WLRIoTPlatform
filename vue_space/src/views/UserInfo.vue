<template>
  <div style="height: 100%">
    <v-main style="margin: 0;padding: 0;height: 100%">

      <div style="margin: 10px;padding-left: 15px">
        <div class="teal--text" style="font-size: 26px">用户信息</div>
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
              <v-text-field
                  label="输入用户id、邮箱或手机号码"
                  filled
                  style="margin-left: 30px;margin-top: 22px;"
                  color="#2ebfaf"
                  append-icon="mdi-magnify"
                  @blur="load"
                  v-model="deviceSearch">

              </v-text-field>

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
                     @click="openStatus"
                     dark
                     style="display:inline;margin-top: 27px;margin-right: 15px"
              >
                without permission user
              </v-btn>

            </v-col>

          </v-row>


        </div>


        <div>

          <v-data-table
              :headers="headers"
              :items="userdata"
              :items-per-page.sync="rowNums"
              :page.sync="pageNum"
              :server-items-length="itemNums"
              class="elevation-1"
              style="margin-left: 30px;margin-right: 30px"
          >

            <!--      弹出的修改框      -->
            <template v-slot:top>
              <v-dialog v-model="dialog" max-width="800px">
                <v-card>
                  <v-card-title>
                    <span class="headline">修改用户信息：</span>
                  </v-card-title>

                  <v-card-text style="padding: 50px">
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.uid" readonly label="User ID"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.uname" label="User Name"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.createTime" readonly label="Create Time"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.email" readonly label="Email"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.phoneNumber" label="Phone Number"></v-text-field>
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
                  @click="passStatus(item)"
              >
                mdi-lock-check
              </v-icon>
            </template>


          </v-data-table>

        </div>

        <div>

          <v-dialog v-model="dialogStatus" width="800px">
            <v-card>
              <v-card-title>
                <span class="headline" style="margin: 12px">申请注册用户列表:</span>
              </v-card-title>


              <template >
                <v-simple-table height="330px" fixed-header style="margin: 20px 0 20px 20px;">
                  <template v-slot:default>
                    <thead>
                    <tr>
                      <th class="text-left">ID</th>
                      <th class="text-center">Email</th>
                      <th class="text-right">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="item in statusZero" :key="item.name">
                      <td class="text-left">{{ item.uid }}</td>
                      <td class="text-center">{{ item.email }}</td>
                      <td class="text-right">
                        <v-btn color="primary" text @click="pass(item)">
                          PASS
                        </v-btn>
                      </td>
                    </tr>
                    </tbody>
                  </template>
                </v-simple-table>
              </template>


              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="#2ebfaf" @click="dialogStatus = false">CLOSE</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

        </div>

        <div>

          <v-dialog v-model="dialogPassStatus" width="600px">
            <v-card>
              <v-card-title>
                <span class="headline" style="margin: 12px">用户登陆权限变更:</span>
              </v-card-title>

              <div style="padding: 15px 30px">
                {{ passStatusText }}
                <div  style="font-size: 13px;color: red; padding: 10px">
                  您的行为很危险，请勿随意操作！
                </div>
              </div>


              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" style="min-width: 80px" @click="dialogPassStatus = false">CLOSE</v-btn>
                <v-btn color="#ff3f6f" style="min-width: 80px" @click="userReject">YES</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

        </div>


        <div>

          <v-dialog v-model="dialogPassWord" width="600px">
            <v-card>
              <v-card-title >
                <span class="headline" style="margin: 12px">请您输入密码进行用户验证:</span>
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

                        v-model="passWord"
                        :rules="passwordRules"
                        type="password"
                        label="PassWord"
                    >
                    </v-text-field>
                  </v-form>

                </v-col>

              </v-row>




              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" style="min-width: 80px" @click="dialogPassWord = false">CLOSE</v-btn>
                <v-btn color="#ff3f6f" style="min-width: 80px" @click="verification">YES</v-btn>
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
import JSEncrypt from "jsencrypt";

export default {
  name: "UserInfo",
  data() {
    return {
      deviceSearch: "",
      rowNums: 10,
      pageNum: 1,
      itemNums: 10,

      dialogStatus: false,
      dialogPassStatus: false,
      dialogPassWord: false,
      userStatus: 0,
      itemUid: 0,
      passCount: 0,

      dialog: false,
      headers: [
        {
          text: '用户ID',
          align: 'start',
          sortable: false,
          value: 'uid',
        },
        { text: '用户名', value: 'uname' , sortable: false},
        { text: '邮箱', value: 'email' , sortable: false},
        { text: '账户创建时间', value: 'createTime' , sortable: false},
        { text: '手机号码', value: 'phoneNumber' , sortable: false},
        { text: '操作', value: 'actions', sortable: false },
      ],
      userdata: [],
      editedIndex: -1,
      editedItem: {
        uid: 0,
        uname: '',
        email: '',
        createTime: '',
        phoneNumber: '',
      },
      defaultItem: {
        uid: 0,
        uname: '',
        email: '',
        createTime: '',
        phoneNumber: '',
      },
      statusZero: [

      ],
      valid: false,
      passWord: '',
      passwordRules: [
        v => !!v || 'PassWord is required',
        v => (v && v.length <= 20) || 'PassWord must be less than 20 characters',
        v => (v && v.length >= 3) || 'PassWord must be greater than 3 characters',
      ],

    }
  },


  // 监听dialog的变化，当dialog为false时，关闭对话框
  watch: {
    dialog (val) {
      val || this.close()
    },
    pageNum () {
      this.load();
    },
    rowNums () {
      this.load();
    },
    // deviceSearch (newVal, oldVal) {
    //   console.log('newVal' + newVal + 'OLD' + oldVal);
    // }
  },

  created () {
    request.get("user/users/listP/" + this.pageNum + "/" + this.rowNums + "/" + (this.deviceSearch === "" ? "null" : this.deviceSearch)).then(res => {
      console.log(res)
      this.userdata = res.data;
      this.itemNums = res.total;
    });
  },

  computed: {
    passStatusText () {
      return this.userStatus === 0 ? "当前用户已被封禁且禁止登录，您确定要恢复此用户账号状态吗？" : "当前用户功能一切正常，您确定要封禁此账户吗？";
    }
  },

  methods: {
    editItem (item) {
      console.log(item)
      this.editedIndex = this.userdata.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },

    passStatus (item) {
      this.itemUid = item.uid;
      request.get("user/login/get_status/" + item.uid).then(res => {
        if (res.status === 200) {
          this.userStatus = res.data;
        }
      })
      this.dialogPassStatus = true;
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
        if (this.editedItem.uname === "" || this.editedItem.uname === null) {
          this.$message.error("用户名不能为空")

        }else if (this.editedItem.phoneNumber === "" || this.editedItem.phoneNumber === null) {
          this.$message.error("电话号码不能为空")
        }else {
          Object.assign(this.userdata[this.editedIndex], this.editedItem)
          request.put("user/users/update",{
            uid: this.userdata[this.editedIndex].uid,
            uname: this.editedItem.uname,
            phoneNumber: this.editedItem.phoneNumber,
          }).then( res => {
            if (res.status === 200) {
              this.$message.success(res.message)
              this.close()
            }
          })
        }
      } else {
        this.userdata.push(this.editedItem)
        this.close()
      }
    },
    openStatus () {
      request.get("/user/login/get_status_zero_list").then( res => {
        this.statusZero = res.data;
      })

      this.dialogStatus = true;

    },

    load() {
      request.get("user/users/listP/" + this.pageNum + "/" + this.rowNums + "/" + (this.deviceSearch === "" ? "null" : this.deviceSearch)).then(res => {
        console.log(res)
        this.userdata = res.data;
        this.itemNums = res.total;
      });
    },
    pass(item) {
      request.post("/user/login/user_pass", {
        uid: item.uid,
        email: item.email
      }).then(res => {
        console.log(res);
        if (res.status === 200) {
          this.$message.success(res.message)
        }
        this.openStatus();
      })
    },
    userReject() {
      if (this.passCount === 3) {
        this.$message.error("您已经连续三次尝试封禁或解封用户，为了您的账户安全，请稍后再试！");
        this.dialogPassWord = true;
        this.dialogPassStatus = false;
        return;
      }
      this.passCount ++;
      request.post("/user/login/user_reject", this.itemUid ).then(res => {
        if (res.status === 200) {
          this.$message.success(res.message);
          this.dialogPassStatus = false;
        }
      })
    },

    async verification() {
      if (this.$refs.loginForm.validate()) {
        await request.get("/user/ras/getPublicKey").then(res => {
          this.publicKey = res.publicKey;
          // console.log(this.publicKey);
        })

        console.log("==============================");


        // 进行简单的加盐
        let temp = ""
        for (let i = 0; i < this.passWord.length; i++) {
          temp += (i + 7) * (this.passWord.charCodeAt(this.passWord.length - i - 1) + temp.length) % 103
          temp += String.fromCharCode((((i + 3) * this.passWord.charCodeAt(i) + temp.length) % this.passWord.length) % 24 + 97)
          temp += ((this.passWord.charCodeAt(i) + 16) * temp.length % 107)
          if (i * temp.length % 2 === 0) {
            temp += String.fromCharCode(((this.passWord.charCodeAt(this.passWord.length - i - 1) + temp.length) * (i * temp.length % 300 + 6)) % 24 + 65)
          }
          temp += String.fromCharCode(((this.passWord.charCodeAt(i) + i) * temp.length) % 24 + 65)
        }

        // console.log('加盐' + temp);

        let sha256 = this.$SHA256(temp);

        // console.log('sha256加密' + sha256);

        let encrypt = new JSEncrypt();
        encrypt.setPublicKey(this.publicKey);
        this.pwEncrypt = encrypt.encrypt(sha256);

        // console.log('RSA加密' + this.pwEncrypt);

        request.post("/user/login/verification", this.pwEncrypt).then(res => {
          console.log(res);
          if (res.status === 200) {
            this.$message.success(res.message);
            this.passCount += 10;
            this.dialogPassWord = false;
          }else {
            this.$refs.loginForm.validata = false;
          }
        })
      }
    }



  },
}
</script>

<style scoped>

</style>
