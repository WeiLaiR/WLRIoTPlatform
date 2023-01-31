<template>

  <v-app
      id="inspire"
      :style="{ background: $vuetify.theme.themes.dark.background }"
  >

    <SideBar />

    <v-divider color="white" class="mt-1" dark></v-divider>

    <router-view/>

  </v-app>

</template>

<script>

import SideBar from "../components/SideBar";
export default {
  name: "Manage",


  components: {
    SideBar,
  },
}


</script>

<style scoped>

</style>
