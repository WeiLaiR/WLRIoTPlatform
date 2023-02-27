<template>
  <div style="height: 100%">
    <v-main style="margin: 0;padding: 0;height: 100%">

      <div style="margin: 10px;padding-left: 15px">
        <div class="teal--text" style="font-size: 26px">修改个人信息</div>
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

        <div
            style="margin: 30px 120px"
        >

          <v-text-field
              label="用户名称"
              filled
              color="#2ebfaf"
              v-model="uname">

          </v-text-field>

          <v-select
              label="性别"
              filled
              color="#2ebfaf"
              :items="sexItem"
              v-model="sex">

          </v-select>


          <v-text-field
              label="年龄"
              filled
              color="#2ebfaf"
              v-model="age">

          </v-text-field>

          <v-text-field
              label="邮箱"
              filled
              color="#2ebfaf"
              readonly
              v-model="email">

          </v-text-field>

          <v-text-field
              label="电话"
              filled
              color="#2ebfaf"
              v-model="phoneNumber">

          </v-text-field>

          <v-text-field
              label="简介"
              filled
              color="#2ebfaf"
              v-model="briefIntroduction">

          </v-text-field>

          <v-text-field
              label="备注"
              filled
              color="#2ebfaf"
              v-model="remarks">

          </v-text-field>

        </div>


        <div>

          <v-row>

            <v-col
              cols="4"
            >


            </v-col>

            <v-col cols="1">
              <v-btn
                  elevation="5"
                  large rounded
                  color="#2ebfaf"
                  dark
                  @click="save"
              >
                 S A V E
              </v-btn>
            </v-col>

            <v-col cols="1">

            </v-col>

            <v-col cols="1">
              <v-btn
                  elevation="5"
                  large rounded
                  color="#2ebfaf"
                  dark
                  @click="restore"
              >
                restore
              </v-btn>
            </v-col>

          </v-row>

        </div>


      </v-card>



    </v-main>
  </div>
</template>

<script>
import request from "@/utils/request";
import store from "@/store";

export default {
  name: "UpdateInfo",
  data() {
    return {
      uname: "",
      sex: null,
      age: null,
      email: "",
      phoneNumber: "",
      briefIntroduction: "",
      remarks: "",

      sexItem: [
        {text: '男', value: true},
        {text: '女', value: false},
      ]
    }
  },

  created() {
    request.get("user/users/userinfo").then( res => {
      if (res.status === 200) {
        this.uname = res.data.uname;
        this.sex = res.data.sex;
        this.age = res.data.age;
        this.email = res.data.email;
        this.phoneNumber = res.data.phoneNumber;
        this.briefIntroduction = res.data.briefIntroduction;
        this.remarks = res.data.remarks;
      }
    })
  },

  methods: {
    restore() {
      request.get("user/users/userinfo").then( res => {
        if (res.status === 200) {
          this.uname = res.data.uname;
          this.sex = res.data.sex;
          this.age = res.data.age;
          this.email = res.data.email;
          this.phoneNumber = res.data.phoneNumber;
          this.briefIntroduction = res.data.briefIntroduction;
          this.remarks = res.data.remarks;
        }
      })
    },
    save() {
      if (this.uname === null || this.uname === '' | this.sex === null | this.sex === '' | this.age === null | this.age === '' | this.phoneNumber === null | this.phoneNumber === '' | this.briefIntroduction === null | this.briefIntroduction === '' | this.remarks === null | this.remarks === '') {
        this.$message.error("请填写完整信息");
        return;
      }else {
        request.put("user/users/update",{
          uname : this.uname,
          sex : this.sex,
          age : this.age,
          phoneNumber : this.phoneNumber,
          briefIntroduction : this.briefIntroduction,
          remarks : this.remarks
        }).then(res => {
          if (res.status === 200) {
            this.$message.success(res.message);
            localStorage.setItem("newNameValue", this.uname);
            store.commit("setPath");
          }
        })
      }
      console.log('save')
    }
  }
}
</script>

<style scoped>


</style>
