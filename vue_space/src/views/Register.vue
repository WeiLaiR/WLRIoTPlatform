<template>
  <v-app>
    <div class="wrapper">

      <v-row
          style="height: 100%; width: 100%;"
          justify="center"
          align="center"
      >

        <v-col>

          <v-card
              style="background: rgba(0,0,0,0.39)"
              class="mx-auto"
              max-width="390"
              elevation="20"
              outlined
          >

            <v-card-title >
              <div style="margin: 0 auto;font-size: 35px;color: antiquewhite">
                Sign up
              </div>
            </v-card-title>

            <v-card-actions>

              <v-form
                  ref="loginForm"
                  v-model="userRegister"
                  style="width: 80%;margin: 0 35px"
              >

                <v-text-field
                    dark
                    v-model="email"
                    label="E-mail"
                >

                </v-text-field>

                <v-row>
                  <v-col cols="9">

                    <v-text-field
                        dark
                        v-model="code"
                        label="Verification Code"
                    >

                    </v-text-field>

                  </v-col>

                  <v-col cols="3" style="padding: 0;margin: 0">

                    <v-btn color="#2ebfaf" style="margin-top: 28px">send</v-btn>

                  </v-col>
                </v-row>






                <v-text-field
                    dark
                    v-model="password"
                    label="PassWord"
                >

                </v-text-field>


                <v-text-field
                    dark
                    v-model="password2"
                    label="Enter the password again"
                >

                </v-text-field>

              </v-form>

            </v-card-actions>

            <v-card-actions

            >

              <div style="margin: 0 auto">
                <v-btn outlined color="#2ebfaf" >New account</v-btn>

                <v-btn outlined color="#2ebfaf" style="margin-left: 20px" @click="goBack">Go Back</v-btn>

              </div>



            </v-card-actions>

          </v-card>

        </v-col>

      </v-row>

    </div>
  </v-app>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {

    }
  },
  methods: {
    goBack() {
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>

.wrapper {
  height: 100vh;
  background-image: url("../assets/bg2.jpg");
  background-size: 100%;
  overflow: hidden;
}

</style>

