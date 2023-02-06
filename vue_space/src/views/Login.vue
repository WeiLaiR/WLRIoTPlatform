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
              max-width="850"
              elevation="20"
              outlined
          >
            <v-row>

              <v-col
                  cols="6"
              >
                <div style="height: 100%;width: 100%;">

                  <v-card-title style="color: aliceblue;font-size: 32px;margin: 20px">
                    Welcome back!
                  </v-card-title>

                  <v-card-text style="color: aliceblue;font-size: 18px">
                    <div style="line-height: 30px;margin: 10px">
                      Try to connect your device to our Internet of Things platform and enjoy efficient device management and information statistics.
                    </div>
                  </v-card-text>

                </div>

              </v-col>

              <v-col
                  style="padding-left: 0;padding-right: 0"
                  cols="auto"
              >

                <v-divider
                    style="height: 90%;border: 0.5px solid rgba(150,150,150,0.6)"
                    dark
                    inset
                    vertical
                ></v-divider>

              </v-col>

              <v-col
                  cols="6"
              >

                <v-card-title>
                  <div style="margin: 0 auto;font-size: 35px;color: antiquewhite">
                    Sign in
                  </div>
                </v-card-title>

                <v-card-actions>

                  <v-form
                      ref="loginForm"
                      v-model="valid"
                      style="width: 90%;"
                  >

                    <v-text-field
                        dark
                        v-model="Login.email"
                        :rules="emailRules"
                        type="text"
                        label="E-mail"
                    >

                    </v-text-field>

                    <v-text-field
                        dark
                        v-model="Login.password"
                        :rules="passwordRules"
                        type="password"
                        label="PassWord"
                      >

                    </v-text-field>

                  </v-form>

                </v-card-actions>

                <v-card-actions
                >

                  <v-btn outlined color="#2ebfaf" style="margin: 0 auto" @click="login">Log In</v-btn>

                  <v-btn outlined color="#2ebfaf" style="margin: 0 auto" @click="signup">Sign up</v-btn>

                </v-card-actions>

              </v-col>

            </v-row>

          </v-card>

        </v-col>

      </v-row>

    </div>
  </v-app>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Login",
  data() {
    return {
      valid: false,
      Login: {
        email: "",
        password: ""
      },
      publicKey: "",
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
      ],
      passwordRules: [
        v => !!v || 'PassWord is required',
        v => (v && v.length <= 20) || 'PassWord must be less than 20 characters',
        v => (v && v.length >= 3) || 'PassWord must be greater than 3 characters',
      ],
      pwEncrypt: "",
    }
  },

  methods: {

    login() {
      request.get("/user/ras/getPublicKey").then(res => {
        this.publicKey = res.publicKey;
        console.log(this.publicKey);
      })

      console.log("==============================");

      request.post("/user/login/sign_in", this.Login).then(res => {
        console.log(res);
        if (res.status === 200) {
          this.$router.push("/home");
          localStorage.setItem("token", res.token);
        }
      })

    },

    signup() {
      this.$router.push("/register");
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
