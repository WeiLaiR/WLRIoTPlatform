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
              v-model="userName">

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
              v-model="email">

          </v-text-field>

          <v-text-field
              label="电话"
              filled
              color="#2ebfaf"
              v-model="phone">

          </v-text-field>

          <v-text-field
              label="简介"
              filled
              color="#2ebfaf"
              v-model="introduction">

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
export default {
  name: "UpdateInfo",
  data() {
    return {

      sexItem: [
        {text: '男', value: 1},
        {text: '女', value: 0},
      ]


    }

  },
}
</script>

<style scoped>


</style>
