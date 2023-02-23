<template>
  <div style="height: 100%">
    <v-main style="margin: 0;padding: 0;height: 100%">

      <div style="margin: 10px;padding-left: 15px">
        <div class="teal--text" style="font-size: 26px">消息通知</div>
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

        <transition-group tag="v-alert">

          <template v-for="(value, index) in values">
            <v-alert :key="index" min-height="70px" max-height="70px" style="padding: 5px 5px;margin: 15px 20px 15px 20px"
                     :style="{backgroundImage: (value.isRead === true ? 'linear-gradient(to right, #E7E7E6FF, #E7E7E6FF)' : 'linear-gradient(to right, #FFFFFFFF, #FFFFFFFF)')}"
                     color="#00A2FFFF"
                     colored-border
                     border="left"
                     elevation="2"
                     @click="isRead2(value)"
            >

              <v-icon color="#00A2FFFF" style="position: absolute;left: 18px;top: 34%">
                mdi-information
              </v-icon>

              <div style="padding: 17px 10px 10px 50px">{{value.message}}</div>

              <div
                  STYLE="position: absolute;top: 10px;right: 18px;" :style="{color: (value.isRead === true ? '#A3A3A3FF' : '#FFC100FF')}"
              >
                {{value.isRead === true ? '已读' : '未读'}}
              </div>

              <div style="position: absolute;right: 18px;bottom: 6px">
                ——{{value.createTime}}
              </div>

            </v-alert>

          </template>
        </transition-group>






        <div style="position: absolute;right: 50px;bottom: 12px">

          <div style="display: inline;margin-right: 20px;font-size: 13px">{{this.startNum}}-{{this.endNum}} of {{this.total}}</div>

          <v-icon size="39px" style="margin-right: 20px" @click="left1">
            mdi-chevron-left
          </v-icon>

          <v-icon  size="39px" @click="right1">
            mdi-chevron-right
          </v-icon>

        </div>



      </v-card>






    </v-main>
  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name: "Remind",
  data() {
    return{
      values:[],
      page: 1,
      size: 8,
      total: 0,
      startNum: 0,
      endNum: 0,


    }
  },
  created() {
    this.load();
  },

  computed: {

  },

  methods: {
    load() {
      request.get("/user/getRemindListPage/" + this.page + "/" + this.size).then(res => {
        this.values = res.data;
        this.total = res.total;
        this.startNum = (this.page - 1) * this.size + 1;
        this.endNum = this.page * this.size > this.total ? this.total : this.page * this.size;
      })
    },
    isRead2(value) {
      if (value.isRead === false) {
        value.isRead = true;
        request.post("/user/isRead", value.remindId);
      }
    },
    left1() {
      if (this.page > 1) {
        this.page--;
        this.load();
      }
    },
    right1() {
      if (this.page < Math.ceil(this.total / this.size)) {
        this.page++;
        this.load();
      }
    }



  },


}
</script>

<style scoped>

/*设置transition组件管理的内容在显示和隐藏时候的样式*/
.v-enter-active,.v-leave-active{
  transition: all 0.6s;
}
.v-enter,.v-leave-to{
  opacity: 0;
}
.v-enter-to,.v-leave{
  opacity: 1;
}

</style>
