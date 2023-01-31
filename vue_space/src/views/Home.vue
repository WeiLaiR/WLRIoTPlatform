<template>

  <div style="height: 100%">
    <v-main
        style="padding: 0px;"
    >
      <v-container>
        <v-row>
          <v-col cols="12" sm="12">
            <v-toolbar flat color="rgba(0,0,0,0)" class="mt-n5">
              <v-toolbar-title>仪表盘</v-toolbar-title>
              <v-spacer></v-spacer>
            </v-toolbar>

            <v-row class="px-5 mt-n6 ml-5" style="padding-top: 20px">
              <v-col cols="12" sm="3" v-for="list in lists" :key="list">
                <v-card
                    align="center"
                    color="#F9FAFC"
                    class="rounded-circle border pt-10"
                    width="200"
                    height="200"
                    flat
                >
                  <v-icon size="60" color="#2EBFAF">
                    {{ list.icon }}
                  </v-icon>

                  <v-card-text class="grey--text text-lg-h6">
                    {{ list.title }}
                  </v-card-text>

                  <v-btn
                      absolute
                      color="#2EBFAF"
                      class="white--text"
                      fab
                      left
                      top
                  >
                    <h2>{{ list.count }}</h2>
                  </v-btn>
                </v-card>
              </v-col>
            </v-row>


            <v-row class="mt-n5" style="padding-top: 50px">
              <v-col cols="12" sm="7">
                <History />
              </v-col>

              <v-col cols="12" sm="5">
                <Appointment />
              </v-col>

            </v-row>

            <v-row class="mt-n12" style="padding-top: 50px">
              <v-col cols="12" sm="12">
                <Table />
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </div>

</template>

<script>
import Table from "../components/Table";
import History from "../components/History";
import Appointment from "../components/Appointment";
export default {
  name: "Home",
  data: () => ({

    lists: [
      {
        icon: "fas fa-user-md",
        title: "注册设备",
        count: 21,
      },
      {
        icon: "fas fa-procedures",
        title: "活跃设备",
        count: 41,
      },
      {
        icon: "fas fa-user-nurse",
        title: "Active Nurses",
        count: 35,
      },
      {
        icon: "fas fa-hospital",
        title: "Representative",
        count: 15,
      },
    ],
  }),

  components: {
    History,
    Appointment,
    Table
  },
};
</script>
<style scoped>
.border {
  border: 2px solid #2ebfaf !important;
}
.v-btn--fab.v-size--default.v-btn--absolute.v-btn--top {
  top: 65px !important;
}
.v-btn--absolute.v-btn--left,
.v-btn--fixed.v-btn--left {
  left: -26px !important;
}
</style>
