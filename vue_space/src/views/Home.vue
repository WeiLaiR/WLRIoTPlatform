<template>

  <div style="height: 100%">
    <v-main
        style="margin: 0;padding: 0;height: 100%"
    >
      <div style="margin: 35px">

        <v-row>

          <v-col cols="3">

            <v-card
              height="365px"
            >
              <div style="font-size: 17px;padding: 15px;font-weight: bold">
                <v-icon color="#55A8D8FF">
                  mdi-star-four-points-outline
                </v-icon>
                平台简介
              </div>

              <div style="padding: 10px 35px 0 30px;color: #616161FF">
                尊敬的用户{{hello}}，欢迎使用WLR物联网平台。
              </div>

              <div style="padding: 10px 35px 0 30px;color: #616161FF">
                本平台前端基于Vue+Vuetify开发，后端基于SpringBoot开发，使用SpringCloud微服务架构。
              </div>

              <div style="padding: 10px 35px 0 30px;color: #616161FF">
                目前支持的物联网协议有：
              </div>

              <div style="padding: 10px 35px 0 40px;color: #616161FF">
                <v-icon size="10">mdi-star-three-points</v-icon>&nbsp;&nbsp;HTTP
              </div>

              <div style="padding: 10px 35px 0 40px;color: #616161FF">
                <v-icon size="10">mdi-star-three-points</v-icon>&nbsp;&nbsp;MQTT
              </div>

              <div style="padding: 10px 35px 0 40px;color: #616161FF">
                <v-icon size="10">mdi-star-three-points</v-icon>&nbsp;&nbsp;CoAP
              </div>






            </v-card>

          </v-col>

          <v-col
              cols="9"
              style="padding-right: 25px"
          >

            <v-card
                height="270px"
            >

              <div style="font-size: 17px;padding: 15px;font-weight: bold">
                <v-icon color="#55A8D8FF">
                  mdi-star-four-points-outline
                </v-icon>
                设备统计
              </div>

              <v-row style="margin-left: 20px;margin-right: 20px;margin-top: 0">

                <v-col
                    cols="4"
                    style="padding-top: 0"
                >
                  <v-card height="180px" elevation="3" style="margin-top: 8px;background-image: linear-gradient(to top left, #FFFFFFFF 0%,#FFFFFFFF 35%, #DAE5FFFF 100%);">
                    <div style="position: absolute; top: 25px;left: 30px;font-size: 17px;color: #747474FF">
                      注册设备数
                    </div>

                    <div style="position: absolute; top: 75px;left: 30px;font-size: 43px;font-weight: 500;color: #4B4B4BFF">
                      28
                    </div>

                    <v-img src="../assets/azs1.svg" height="130px" width="130px" style="position: absolute;right: 20px;bottom: 10px"></v-img>



                  </v-card>

                </v-col>

                <v-col
                    cols="4"
                    style="padding-top: 0"
                >
                  <v-card height="180px" elevation="3" style="margin-top: 8px;background-image: linear-gradient(to top left, #FFFFFFFF 0%,#FFFFFFFF 35%, #DAE5FFFF 100%);">
                    <div style="position: absolute; top: 25px;left: 30px;font-size: 17px;color: #747474FF">
                      激活设备数
                    </div>

                    <div style="position: absolute; top: 75px;left: 30px;font-size: 43px;font-weight: 500;color: #4B4B4BFF">
                      19
                    </div>

                    <v-img src="../assets/azs2.svg" height="130px" width="130px" style="position: absolute;right: 20px;bottom: 10px"></v-img>



                  </v-card>

                </v-col>

                <v-col
                    cols="4"
                    style="padding-top: 0"
                >
                  <v-card height="180px" elevation="3" style="margin-top: 8px;background-image: linear-gradient(to top left, #FFFFFFFF 0%,#FFFFFFFF 35%, #DAE5FFFF 100%);">
                    <div style="position: absolute; top: 25px;left: 30px;font-size: 17px;color: #747474FF">
                      活跃设备数
                    </div>

                    <div style="position: absolute; top: 75px;left: 30px;font-size: 43px;font-weight: 500;color: #4B4B4BFF">
                      16
                    </div>

                    <v-img src="../assets/azs3.svg" height="130px" width="130px" style="position: absolute;right: 20px;bottom: 10px"></v-img>



                  </v-card>

                </v-col>
              </v-row>





            </v-card>

          </v-col>


          <v-col cols="3">

            <v-card
                height="355px"
            >
              <div style="font-size: 17px;padding: 15px;font-weight: bold">
                <v-icon color="#55A8D8FF">
                  mdi-star-four-points-outline
                </v-icon>
                平台引导
              </div>

              <v-card style="margin: 10px 22px 10px 22px;background-image: linear-gradient(to top left, #FFFFFFFF 0%,#FFFFFFFF 43%, #D0E8FDFF 100%);" elevation="3" height="70px" >
                <v-img src="../assets/advi.svg" height="55px" width="55px" style="margin: 5px;position: absolute;top: 5px"></v-img>

                <div style="position: absolute;left: 65px;top: 10px;color: #747474FF">STEP1</div>

                <div style="position: absolute;left: 65px;top: 36px;font-size: 17px">添加设备</div>

                <div style="position: absolute;right: 30px;top: -5px;font-size: 66px;color: #D1E8FDFF">1</div>

              </v-card>

              <v-card style="margin: 18px 22px;background-image: linear-gradient(to top left, #FFFFFFFF 0%,#FFFFFFFF 43%, #D0E8FDFF 100%);" elevation="3" height="70px">
                <v-img src="../assets/acfg.svg" height="55px" width="55px" style="margin: 5px;position: absolute;top: 5px"></v-img>

                <div style="position: absolute;left: 65px;top: 10px;color: #747474FF">STEP2</div>

                <div style="position: absolute;left: 65px;top: 36px;font-size: 17px">创建配置</div>

                <div style="position: absolute;right: 30px;top: -5px;font-size: 66px;color: #D1E8FDFF">2</div>

              </v-card>

              <v-card style="margin: 18px 22px;background-image: linear-gradient(to top left, #FFFFFFFF 0%,#FFFFFFFF 43%, #D0E8FDFF 100%);" elevation="3" height="70px">
                <v-img src="../assets/agz.svg" height="55px" width="55px" style="margin: 5px;position: absolute;top: 5px"></v-img>

                <div style="position: absolute;left: 65px;top: 10px;color: #747474FF">STEP3</div>

                <div style="position: absolute;left: 65px;top: 36px;font-size: 17px">创建规则</div>

                <div style="position: absolute;right: 30px;top: -5px;font-size: 66px;color: #D1E8FDFF">3</div>

              </v-card>


            </v-card>

          </v-col>

          <v-col cols="9" style="padding-left: 25px;padding-right: 47px;position: absolute;top: 317px;right: 1px">

            <v-card
                height="450px"
            >

              <div style="font-size: 17px;padding: 15px;font-weight: bold">
                <v-icon color="#55A8D8FF">
                  mdi-star-four-points-outline
                </v-icon>
                设备接入步骤
              </div>

              <v-row style="margin: 0 20px 0 20px">

                <v-col
                    cols="3"
                >
                  <v-card
                      height="350px"
                  >

                    <div style="width: 100%;height: 66px;background-image: linear-gradient(to top left, #FFFFFFFF 0%,#FFFFFFFF 33%, #D0E8FDFF 100%);">
                      <div style="font-size: 22px;padding: 17px;color: #707070FF">STEP1</div>

                      <v-img src="../assets/lc.svg" height="60px" width="60px" style="position: absolute; top: 12px; right: 12px"></v-img>

                    </div>
                    <v-divider></v-divider>

                    <div style="font-weight: bold;padding-top: 7px;padding-left: 15px" >
                      添加设备
                    </div>

                    <div style="padding-top: 5px;padding-left: 15px;padding-right: 15px;font-size: 15px">
                      1.点击左侧菜单栏“设备管理”进入设备管理页面.点击“添加设备”按钮并填写相应信息。
                    </div>

                    <div style="padding-top: 5px;padding-left: 15px;padding-right: 15px;font-size: 15px">
                      2.添加设备成功后保存相应Token。Token极为重要，请妥善保管不可泄漏！
                    </div>

                    <div style="padding-top: 5px;padding-left: 15px;padding-right: 15px;font-size: 15px;color: #707070FF">
                      此处设备指的是单一设备或具有相同功能的多个设备的集合。
                    </div>

                  </v-card>

                </v-col>


                <v-col
                    cols="3"
                >
                  <v-card
                      height="350px"
                  >

                    <div style="width: 100%;height: 66px;background-image: linear-gradient(to top left, #FFFFFFFF 0%,#FFFFFFFF 33%, #D0E8FDFF 100%);">
                      <div style="font-size: 22px;padding: 17px;color: #707070FF">STEP2</div>

                      <v-img src="../assets/lc.svg" height="60px" width="60px" style="position: absolute; top: 12px; right: 12px"></v-img>

                    </div>
                    <v-divider></v-divider>

                    <div style="font-weight: bold;padding-top: 7px;padding-left: 15px" >
                      创建配置
                    </div>

                    <div style="padding-top: 5px;padding-left: 15px;padding-right: 15px;font-size: 15px">
                      1.点击左侧菜单栏“设备配置”后，选定设备后点击“添加配置”。
                    </div>

                    <div style="padding-top: 5px;padding-left: 15px;padding-right: 15px;font-size: 15px">
                      2.应正确填写数据名称和数据类型字段，若填写错误服务器将不能正确获取相应数据。
                    </div>

                    <div style="padding-top: 5px;padding-left: 15px;padding-right: 15px;font-size: 15px;color: #707070FF">
                      同一设备或设备集合的数据昵称可以重复但数据名称不能重复。
                    </div>

                  </v-card>

                </v-col>


                <v-col
                    cols="3"
                >
                  <v-card
                      height="350px"
                  >

                    <div style="width: 100%;height: 66px;background-image: linear-gradient(to top left, #FFFFFFFF 0%,#FFFFFFFF 33%, #D0E8FDFF 100%);">
                      <div style="font-size: 22px;padding: 17px;color: #707070FF">STEP3</div>

                      <v-img src="../assets/lc.svg" height="60px" width="60px" style="position: absolute; top: 12px; right: 12px"></v-img>

                    </div>
                    <v-divider></v-divider>

                    <div style="font-weight: bold;padding-top: 7px;padding-left: 15px" >
                      创建规则
                    </div>

                    <div style="padding-top: 5px;padding-left: 15px;padding-right: 15px;font-size: 15px">
                      1.点击左侧菜单栏“规则管理”后，选择对应的设备和配置，点击“添加规则”。
                    </div>

                    <div style="padding-top: 5px;padding-left: 15px;padding-right: 15px;font-size: 15px">
                      2.请正确填写规则信息和提示方式。
                    </div>

                    <div style="padding-top: 5px;padding-left: 15px;padding-right: 15px;font-size: 15px;color: #707070FF">
                      当设备数据满足规则设定时，将会以相应的提示方式进行提示。
                    </div>

                  </v-card>

                </v-col>


                <v-col
                    cols="3"
                >
                  <v-card
                      height="350px"
                  >

                    <div style="width: 100%;height: 66px;background-image: linear-gradient(to top left, #FFFFFFFF 0%,#FFFFFFFF 33%, #D0E8FDFF 100%);">
                      <div style="font-size: 22px;padding: 17px;color: #707070FF">STEP4</div>

                      <v-img src="../assets/lc.svg" height="60px" width="60px" style="position: absolute; top: 12px; right: 12px"></v-img>

                    </div>
                    <v-divider></v-divider>

                    <div style="font-weight: bold;padding-top: 7px;padding-left: 15px" >
                      接入设备
                    </div>

                    <div style="padding-top: 5px;padding-left: 15px;padding-right: 15px;font-size: 15px">
                      1.确保需要接入的设备支持相应协议。
                    </div>

                    <div style="padding-top: 5px;padding-left: 15px;padding-right: 15px;font-size: 15px">
                      2.消息体应为合法的字符串，且符合相应协议的格式以便于解析，并携带必须的消息如Token、version等。
                    </div>

                    <div style="padding-top: 5px;padding-left: 15px;padding-right: 15px;font-size: 15px;color: #707070FF">
                      查看更多的接入设备的详细信息请点击此处。
                    </div>

                  </v-card>

                </v-col>

              </v-row>





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
