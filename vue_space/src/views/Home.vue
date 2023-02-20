<template>

  <div style="height: 100%">
    <v-main
        style="margin: 0;padding: 0;height: 100%"
    >
      <div style="margin: 35px">

        <v-row>

          <v-col cols="3">

            <v-card
              height="260px"
            >
              <div style="font-size: 19px;padding: 15px">
                <v-icon color="#55A8D8FF">
                  mdi-star-four-points-outline
                </v-icon>
                物联网平台引导
              </div>

              <v-card style="margin: 0 22px 10px 22px;background-image: linear-gradient(to top left, #FFFFFFFF 0%,#FFFFFFFF 50%, #D0E8FDFF 100%);" elevation="3" height="70px" >
                <v-img src="../assets/advi.svg" height="55px" width="55px" style="margin: 5px;position: absolute;top: 5px"></v-img>

                <div style="position: absolute;left: 65px;top: 10px;color: #747474FF">STEP1</div>

                <div style="position: absolute;left: 65px;top: 36px;font-size: 17px">创建设备</div>

                <div style="position: absolute;left: 210px;top: -5px;font-size: 66px;color: #D1E8FDFF">1</div>

              </v-card>

              <v-card style="margin: 10px 22px;background-image: linear-gradient(to top left, #FFFFFFFF 0%,#FFFFFFFF 50%, #D0E8FDFF 100%);" elevation="3" height="70px">
                <v-img src="../assets/acfg.svg" height="55px" width="55px" style="margin: 5px;position: absolute;top: 5px"></v-img>

                <div style="position: absolute;left: 65px;top: 10px;color: #747474FF">STEP2</div>

                <div style="position: absolute;left: 65px;top: 36px;font-size: 17px">创建配置</div>

                <div style="position: absolute;left: 210px;top: -5px;font-size: 66px;color: #D1E8FDFF">2</div>

              </v-card>


            </v-card>

          </v-col>



        </v-row>

      </div>
    </v-main>
  </div>

</template>

<script>

export default {
  name: "Home",
  data: () => ({

  }),


  computed: {
    hello () {
      let time = new Date();
      if (time.getHours() < 12 && time.getHours() > 6) {
        return '上午好'
      } else if (time.getHours() < 19) {
        return '下午好'
      } else {
        return '晚上好'
      }
    }
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
