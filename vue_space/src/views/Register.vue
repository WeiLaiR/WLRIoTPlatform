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
                  ref="registerForm"
                  v-model="valid"
                  style="width: 80%;margin: 0 35px"
              >

                <v-form
                    ref="eMail"
                    v-model="valid1"
                >
                  <v-text-field

                      dark
                      v-model="email"
                      :rules="emailRules"
                      label="E-mail"
                  >

                  </v-text-field>
                </v-form>

                <v-row>
                  <v-col cols="9">

                    <v-text-field
                        dark
                        v-model="code"
                        :rules="codeRules"
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
                    :rules="passwordRules"
                    label="PassWord"
                >

                </v-text-field>


                <v-text-field
                    dark
                    v-model="password2"
                    :rules="passwordRules"
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
      valid: false,
      valid1: false,
      email: '',
      code: '',
      password: '',
      password2: '',
      publicKey: "",
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
        v => (v && v.length <= 50) || 'E-mail must be less than 50 characters',
      ],
      passwordRules: [
        v => !!v || 'PassWord is required',
        v => (v && v.length <= 20) || 'PassWord must be less than 20 characters',
        v => (v && v.length >= 3) || 'PassWord must be greater than 3 characters',
      ],
      codeRules: [
        v => !!v || 'Code is required',
        v => (v && v.length <= 20) || 'Code must be less than 20 characters',
      ],
      pwEncrypt: "",



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

