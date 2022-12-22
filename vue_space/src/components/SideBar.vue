<template>
<nav>
  <v-navigation-drawer
      app
      dark
      color="#E9EEF4"
    >
       <div class="text-center mt-5">
           <h1 class="teal--text text-lg-h5 font-weight-bold ">WLRIoTPlatform</h1>
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
                  <v-list-item v-for="(item, i) in items" :key="i" v-slot="{ active }">
                      <v-list-item-icon class="ml-6">
                          <v-icon v-text="item.icon"  :color="active ? 'black' : 'grey'" ></v-icon>
                      </v-list-item-icon>
                      <v-list-item-content>
                          <v-list-item-title v-text="item.text" :class="active ? 'black--text' : 'grey--text'" ></v-list-item-title>
                      </v-list-item-content>
                  </v-list-item>

                 <v-list-item class="mt-16" v-slot="{ active }">
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

      <v-btn class="ml-2"  icon  small>
        <v-icon dark>
          mdi-bell
        </v-icon>
      </v-btn>

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

    </v-app-bar>

</nav>
</template>

<script>
export default {
    data: () =>({
        selectedItem: 0,
        items: [
            {icon: 'mdi-finance', text: '首页'},
            {icon: 'mdi-calendar-outline', text: '设备信息'},
            {icon: 'mdi-account-outline', text: '设备配置'},
            {icon: 'mdi-content-paste', text: '用户信息'},
            {icon: 'mdi-home-outline', text: '用户管理'},
            {icon: 'mdi-cogs', text: '设置'},
        ],
    })
}
</script>

<style>
.image {
    border: 2px solid #2EBFAF;
}

</style>
