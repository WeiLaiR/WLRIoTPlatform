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



                    <v-card-actions
                    >

                      <v-btn outlined color="#2ebfaf" style="margin: 0 auto" @click="login">Log In</v-btn>

                      <v-btn outlined color="#2ebfaf" style="margin: 0 auto" @click="signup">Sign up</v-btn>

                    </v-card-actions>

                  </v-form>

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
import JSEncrypt from "jsencrypt";

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

    async login() {

      if (this.$refs.loginForm.validate()) {
        await request.get("/user/ras/getPublicKey").then(res => {
          this.publicKey = res.publicKey;
          // console.log(this.publicKey);
        })

        console.log("==============================");

        // 进行简单的加盐
        let temp = ""
        for (let i = 0; i < this.Login.password.length; i++) {
          temp += (i + 7) * (this.Login.password.charCodeAt(this.Login.password.length - i - 1) + temp.length) % 103
          temp += String.fromCharCode((((i + 3) * this.Login.password.charCodeAt(i) + temp.length) % this.Login.password.length) % 24 + 97)
          temp += ((this.Login.password.charCodeAt(i) + 16) * temp.length % 107)
          if (i * temp.length % 2 === 0) {
            temp += String.fromCharCode(((this.Login.password.charCodeAt(this.Login.password.length - i - 1) + temp.length) * (i * temp.length % 300 + 6)) % 24 + 65)
          }
          temp += String.fromCharCode(((this.Login.password.charCodeAt(i) + i) * temp.length) % 24 + 65)
        }

        // console.log('加盐' + temp);

        let sha256 = this.$SHA256(temp);

        // console.log('sha256加密' + sha256);

        let encrypt = new JSEncrypt();
        encrypt.setPublicKey(this.publicKey);
        this.pwEncrypt = encrypt.encrypt(sha256);

        // console.log('RSA加密' + this.pwEncrypt);

        request.post("/user/login/sign_in", {email: this.Login.email, password: this.pwEncrypt}).then(res => {
          console.log(res);
          if (res.status === 200) {
            this.$message.success(res.message);
            localStorage.setItem("token", res.token);
            localStorage.setItem("userStatus", res.userStatus);
            this.$router.push("/home");
          }
        })
      }
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
  background-size: cover;
  overflow: hidden;
}

</style>
