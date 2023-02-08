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

                    <v-btn color="#2ebfaf" style="margin-top: 28px" @click="sendEmail">send</v-btn>

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
                <v-btn outlined color="#2ebfaf" @click="userRegister">New account</v-btn>

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
import request from "@/utils/request";
import JSEncrypt from "jsencrypt";

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
    },
    async userRegister() {

      if (this.$refs.eMail.validate() && this.$refs.registerForm.validate()) {

        if (this.password !== this.password2) {
          this.$message.error("两次密码输入不一致！");
        }

        await request.get("/user/ras/getPublicKey").then(res => {
          this.publicKey = res.publicKey;
          // console.log(this.publicKey);
        })

        // 进行简单的加盐
        let temp = ""
        for (let i = 0; i < this.password.length; i++) {
          temp += (i + 7) * (this.password.charCodeAt(this.password.length - i - 1) + temp.length) % 103
          temp += String.fromCharCode((((i + 3) * this.password.charCodeAt(i) + temp.length) % this.password.length) % 24 + 97)
          temp += ((this.password.charCodeAt(i) + 16) * temp.length % 107)
          if (i * temp.length % 2 === 0) {
            temp += String.fromCharCode(((this.password.charCodeAt(this.password.length - i - 1) + temp.length) * (i * temp.length % 300 + 6)) % 24 + 65)
          }
          temp += String.fromCharCode( ((this.password.charCodeAt(i) + i) * temp.length) % 24 + 65)
        }

        // console.log('加盐' + temp);

        let sha256 = this.$SHA256(temp);

        // console.log('sha256加密' + sha256);

        let encrypt = new JSEncrypt();
        encrypt.setPublicKey(this.publicKey);
        this.pwEncrypt = encrypt.encrypt(sha256);

        // console.log('RSA加密' + this.pwEncrypt);

        request.post('/user/login/register', {
          email: this.email,
          code: this.code,
          password: this.pwEncrypt
        }).then(res => {
          if (res.status === 200) {
            this.$message.success(res.message);
            this.$router.push('/login')
          }
        })



      }

    },
    sendEmail() {
      if (this.$refs.eMail.validate()) {
        request.post('/mail/email/send', {
          email: this.email
        }).then(res => {
          console.log(res)
        })
      }

    },

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

