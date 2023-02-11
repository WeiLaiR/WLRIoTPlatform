<template>
  <div style="height: 100%">
    <v-main style="margin: 0;padding: 0;height: 100%">

      <div style="margin: 10px;padding-left: 15px">
        <div class="teal--text" style="font-size: 26px">用户信息</div>
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

        <div>

          <v-row>

            <v-col
                cols="6"
            >
              <v-text-field
                  label="输入用户id、邮箱或手机号码"
                  filled
                  style="margin-left: 30px;margin-top: 22px;"
                  color="#2ebfaf"
                  append-icon="mdi-magnify"
                  @blur="load"
                  v-model="deviceSearch">

              </v-text-field>

            </v-col>

            <v-col
                cols="3"
            >

              <!--       空位置       -->

            </v-col>

            <v-col
                cols="3"
            >
              <v-btn right
                     absolute
                     elevation="5"
                     large rounded
                     color="#2ebfaf"
                     @click="openStatus"
                     dark
                     style="display:inline;margin-top: 27px;margin-right: 15px"
              >
                without permission user
              </v-btn>

            </v-col>

          </v-row>


        </div>


        <div>

          <v-data-table
              :headers="headers"
              :items="userdata"
              :items-per-page.sync="rowNums"
              :page.sync="pageNum"
              :server-items-length="itemNums"
              class="elevation-1"
              style="margin-left: 30px;margin-right: 30px"
          >

            <!--      弹出的修改框      -->
            <template v-slot:top>
              <v-dialog v-model="dialog" max-width="500px">
                <v-card>
                  <v-card-title>
                    <span class="headline">{{ formTitle }}</span>
                  </v-card-title>

                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.name" label="Device ID"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.calories" label="Device Name"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.fat" label="Create Time"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.carbs" label="Description"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6" md="4">
                          <v-text-field v-model="editedItem.protein" label="Status"></v-text-field>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-card-text>

                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                    <v-btn color="blue darken-1" text @click="save">Save</v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </template>

            <!--      两个按键      -->

            <template v-slot:item.actions="{ item }">
              <v-icon
                  small
                  color="#2ebfaf"
                  class="mr-2"
                  @click="editItem(item)"
              >
                mdi-pencil
              </v-icon>

              <v-icon
                  small
                  color="#ff3f6f"
                  @click="deleteItem(item)"
              >
                mdi-delete
              </v-icon>
            </template>


          </v-data-table>

        </div>

        <div>

          <v-dialog v-model="dialogStatus" width="800px">
            <v-card>
              <v-card-title>
                <span class="headline">申请注册用户列表:</span>
              </v-card-title>




              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" text @click="dialogStatus = false">Disagree</v-btn>
                <v-btn color="green darken-1" text @click="dialogStatus = false">Agree</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

        </div>



      </v-card>



    </v-main>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "UserInfo",
  data() {
    return {
      deviceSearch: "",
      rowNums: 10,
      pageNum: 1,
      itemNums: 10,

      dialogStatus: false,

      dialog: false,
      headers: [
        {
          text: '用户ID',
          align: 'start',
          sortable: false,
          value: 'uid',
        },
        { text: '用户名', value: 'uname' , sortable: false},
        { text: '邮箱', value: 'email' , sortable: false},
        { text: '账户创建时间', value: 'createTime' , sortable: false},
        { text: '手机号码', value: 'phoneNumber' , sortable: false},
        { text: '操作', value: 'actions', sortable: false },
      ],
      userdata: [],
      editedIndex: -1,
      editedItem: {
        uid: 0,
        uname: '',
        email: '',
        createTime: '',
        phoneNumber: '',
      },
      defaultItem: {
        uid: 0,
        uname: '',
        email: '',
        createTime: '',
        phoneNumber: '',
      },

    }
  },


  // 监听dialog的变化，当dialog为false时，关闭对话框
  watch: {
    dialog (val) {
      val || this.close()
    },
    pageNum () {
      this.load();
    },
    rowNums () {
      this.load();
    },
    // deviceSearch (newVal, oldVal) {
    //   console.log('newVal' + newVal + 'OLD' + oldVal);
    // }
  },

  created () {
    request.get("user/users/listP/" + this.pageNum + "/" + this.rowNums + "/" + (this.deviceSearch === "" ? "null" : this.deviceSearch)).then(res => {
      console.log(res)
      this.userdata = res.data;
      this.itemNums = res.total;
    });
  },

  methods: {
    editItem (item) {
      this.editedIndex = this.userdata.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },

    deleteItem (item) {
      const index = this.userdata.indexOf(item)
      confirm('Are you sure you want to delete this item?') && this.userdata.splice(index, 1)
    },

    close () {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    save () {
      if (this.editedIndex > -1) {
        Object.assign(this.userdata[this.editedIndex], this.editedItem)
      } else {
        this.userdata.push(this.editedItem)
      }
      this.close()
    },
    openStatus () {
      this.dialogStatus = true;
    },

    load() {
      request.get("user/users/listP/" + this.pageNum + "/" + this.rowNums + "/" + (this.deviceSearch === "" ? "null" : this.deviceSearch)).then(res => {
        console.log(res)
        this.userdata = res.data;
        this.itemNums = res.total;
      });
    },
    formTitle() {

    }



  },
}
</script>

<style scoped>

</style>
