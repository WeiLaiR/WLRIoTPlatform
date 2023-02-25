<template>
  <div style="height: 100%">
    <v-main style="margin: 0;padding: 0;height: 100%">

      <div style="margin: 10px;padding-left: 15px">
        <div class="teal--text" style="font-size: 26px">设置</div>
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

        <div style="margin: 30px 30px">

          <v-row>

            <v-col cols="3">
              <div style="margin-top: 18px">是否打开设备警告邮件通知：</div>
            </v-col>

            <v-col cols="2">
              <v-switch
                  :disabled="dsSW"
                  v-model="sendEmail"
                  :label="`邮件通知: ${sendEmail ? 'On' : 'Off'}`"
              ></v-switch>
            </v-col>


          </v-row>


          <v-row>

            <v-col cols="3">
              <div style="margin-top: 18px">设置当前用户保存登录时间(天)：</div>
            </v-col>

            <v-col cols="2">
              <v-select
                  :disabled="dsSL"
                  v-model="keepAlive"
                  :items="items"
                  label="保存登录时间(天)"
              ></v-select>
            </v-col>


          </v-row>



        </div>



      </v-card>


    </v-main>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  name: "Setting",
  data() {
    return {
      sendEmail: null,
      dsSW: false,
      keepAlive: null,
      items: [1, 3, 5, 7, 15, 30],
      dsSL: false,
    }
  },
  created() {
    this.load()
  },
  watch: {
    sendEmail(newVal, oldVal) {
      if (oldVal !== null && newVal !== null) {
        this.dsSW = true;
        request.post("/user/setting/updateSendEmail").then(res => {
          if (res.status === 200) {
            this.$message.success("设置成功!");
          }
          this.load();
          this.dsSW = false;
        })
      }
    },
    keepAlive(newVal, oldVal) {
      if (oldVal !== null && newVal !== null) {
        this.dsSL = true;
        request.post("/user/setting/updateKeepAlive", this.keepAlive).then(res => {
          if (res.status === 200) {
            this.$message.success("设置成功!");
          }
          this.dsSL = false;
        })
      }
    }
  },
  methods: {
    load() {
      request.get("/user/setting/query").then(res => {
        if (res.status === 200) {
          this.sendEmail = res.data.sendEmail;
          this.keepAlive = res.data.keepAlive;
          this.dsSW = false;
          this.dsSL = false;
        }
      })
    }
  },
}
</script>

<style scoped>

</style>
