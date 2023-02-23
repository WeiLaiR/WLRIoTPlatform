<template >
<nav>
  <v-navigation-drawer
      app
      dark
      color="#E9EEF4"
      ref="main"

    >
       <div class="text-center mt-5">
           <h1 class="teal--text text-lg-h5 font-weight-bold "> <v-img src="../assets/iot.svg" height="30px" width="30px" style="position: absolute; left: 12px"></v-img> &nbsp;&nbsp;&nbsp;WLRIoTPlatform</h1>
           <v-divider color="white" class="mt-4"></v-divider>
            <v-avatar class="my-5" size="60">
            <v-img src="https://cdn.vuetifyjs.com/images/lists/1.jpg" class="image"></v-img>
            </v-avatar>
            <h1 class="black--text">IoT平台管理员</h1>
            <h6 class="grey--text ">admin@admin.com</h6>
            <v-btn rounded color="#2EBFAF" outlined>个人信息修改</v-btn>
        </div>

        <v-divider class="mx-10 mt-3" ></v-divider>




        <v-list   dense class="mt-5" >
            <v-list-item-group v-model="selectedItem" color="white" >
                  <v-list-item v-for="(item, i) in items" :key="i" v-slot="{active}" :to="item.path">
                      <v-list-item-icon class="ml-6">
                          <v-icon v-text="item.icon"  :color="active ? 'black' : 'grey'" ></v-icon>
                      </v-list-item-icon>
                      <v-list-item-content>
                          <v-list-item-title v-text="item.text" :class="active ? 'black--text' : 'grey--text'" ></v-list-item-title>
                      </v-list-item-content>
                  </v-list-item>

                 <v-list-item class="mt-16" v-slot="{ active }" @click="LogOut">
                  <v-list-item-icon class="ml-6">
                      <v-icon :color="active ? 'black' : 'grey'" >mdi-exit-to-app</v-icon>
                  </v-list-item-icon>
                  <v-list-item-content>
                      <v-list-item-title  :class="active ? 'black--text' : 'grey--text'">注销</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
            </v-list-item-group>
        </v-list>

    </v-navigation-drawer>

    <v-app-bar app  color="#F5F6FA" flat>
      <v-text-field
          dense
          label="Search..."
          color="black"
        ></v-text-field>

      <v-spacer></v-spacer>

      <v-badge  v-model="remindSwitch" :content="remindCount" overlap color="#00C3BEFF">
        <v-btn class="ml-2" icon small @click.stop="openRemind">
          <v-icon color="#6B6C6E" >
            mdi-bell
          </v-icon>
        </v-btn>
      </v-badge>

      <v-btn class="ml-2" icon  small>
        <v-icon dark>
          mdi-email
        </v-icon>
      </v-btn>

      <v-btn class="ml-3" fab  small color="#6B6C6E">
        <v-icon dark large color="white">
          mdi-account
        </v-icon>
      </v-btn>


      <div @click.stop>
        <transition>
          <v-card max-width="460px" style="padding: 10px 10px 10px 10px;position: absolute;top: 55px;right: 55px" elevation="23" v-if="show01 === true">
            <v-card-text style="height: 500px;padding: 0 ;margin: 0;overflow-y: scroll;width: 440px" >

              <transition-group tag="v-alert">

                <template v-for="(value, index) in values">
                  <v-alert :key="index" style="padding: 5px 5px;margin-bottom: 10px" color="#00A2FFFF" colored-border border="left" elevation="2" v-if="value.isRead === false" @click="isRead1(value)">

                    <v-icon color="#00A2FFFF" style="position: absolute;left: 12px;top: 35%">
                      mdi-information
                    </v-icon>

                    <div style="padding: 10px 10px 10px 40px">{{value.message}}</div>

                  </v-alert>

                </template>
              </transition-group>

            </v-card-text>
          </v-card>
        </transition>
      </div>

    </v-app-bar>



</nav>
</template>

<script>
import request from "@/utils/request";

export default {
    data: () =>({
      selectedItem: 0,
      show01: false,
      values: [

      ],

      items: [
          {icon: 'mdi-home-outline', text: '首页', path: '/home'},
          {icon: 'mdi-cellphone-link', text: '设备管理', path: '/deviceInfo'},
          {icon: 'mdi-cellphone-cog', text: '设备配置', path: '/deviceSetting'},
          {icon: 'mdi-atom-variant', text: '规则管理', path: '/deviceRule'},
          {icon: 'mdi-database-search-outline', text: '数据统计', path: '/dataStatistics'},
          {icon: 'mdi-account-search-outline', text: '用户信息', path: '/userInfo'},
          {icon: 'mdi-comment-account-outline', text: '个人信息', path: '/updateInfo'},
          {icon: 'mdi-message-alert-outline', text: '消息通知', path: '/remind'},
          {icon: 'mdi-cogs', text: '设置', path: '/setting'},
      ],
      remindCount: 0,
      remindSwitch: false,
    }),
  methods: {
    LogOut() {
      request.post("user/login/log_out").then( res => {
        if (res.status === 200) {
          this.$message.success(res.message);
        }
        localStorage.removeItem('token');
        this.$router.push('/login');
      })
    },
    openRemind() {


      request.get("/user/getRemindUnreadList").then(res => {
        if (res.status === 200) {
          this.remindCount = 0;
          this.remindSwitch = false;
          this.values = res.data;
        }
      });

      this.show01 = !this.show01
    },

    bodyCloseMenus() {
      let self = this;
      if (self.show01 === true){
        self.show01 = false;
      }
    },

    beforeDestroy() {
      document.removeEventListener("click", this.bodyCloseMenus);
    },

    isRead1(value) {
      value.isRead = true;
      request.post("/user/isRead", value.remindId);
    }






  },

  created() {
    let token = localStorage.getItem("token") ? localStorage.getItem("token") : null;
    if (token === null) {
      this.$router.push("/login");
    }

    request.get("/user/getCount").then(res => {
      if (res.status === 200) {
        this.remindCount = res.data;
        if (this.remindCount > 0) {
          this.remindSwitch = true;
        }
      }
    });
  },

  mounted() {

    document.addEventListener("click", this.bodyCloseMenus);

      const timer = setInterval(() => {
        // 每隔一段时间，向后端发送请求，获取未读消息的数量
        request.get("/user/getCount").then(res => {
          if (res.status === 200) {
            this.remindCount = res.data;
            if (this.remindCount > 0) {
              this.remindSwitch = true;
            }
          }
        });
      }, 15000);

      this.$once('hook:beforeDestroy', () => {
        // 在页面销毁时，销毁定时器
        clearInterval(timer)
      });
  }


}
</script>

<style>
.image {
    border: 2px solid #2EBFAF;
}
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
