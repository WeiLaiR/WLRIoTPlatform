<template>
  <div>
       <v-toolbar color="rgba(0,0,0,0)" flat class="pt-2">
                <v-toolbar-title class=""> Doctors List </v-toolbar-title>
              </v-toolbar>
              <v-simple-table class="transparent mt-n2">
                <template v-slot:default>
                  <thead>
                    <tr>
                      <th class="text-left">#</th>
                      <th class="text-left">First Name</th>
                      <th class="text-left">Last Name</th>
                      <th class="text-left">Expert in</th>
                      <th class="text-left">Appointment</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="item in doctors" :key="item.id">
                      <td>{{ item.id }}</td>
                      <td>{{ item.first }}</td>
                      <td>{{ item.last }}</td>
                      <td>{{ item.expert }}</td>
                      <td>{{ item.date }}</td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
  </div>
</template>

<script>
export default {
data : () => ({
     doctors: [
      {
        id: 1,
        first: "Dipto",
        last: "Rey",
        expert: "Deseases",
        date: "05:30 pm - 11:00 pm",
      },
      {
        id: 2,
        first: "Soidul",
        last: "Hussain",
        expert: "Deseases",
        date: "05:30 pm - 12:00 pm",
      },
    ],
})
}
</script>

<style>

</style>