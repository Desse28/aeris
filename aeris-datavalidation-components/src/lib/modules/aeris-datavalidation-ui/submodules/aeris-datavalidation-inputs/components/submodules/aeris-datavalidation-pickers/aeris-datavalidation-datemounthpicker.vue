<template>
      <v-menu
          ref="menu"
          v-model="menu"
          :close-on-content-click="false"
          :return-value.sync="date"
          transition="scale-transition"
          offset-y
          min-width="290px"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
              v-model="date"
              label="Date"
              prepend-icon="mdi-calendar"
              readonly
              v-bind="attrs"
              v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker v-model="date" no-title scrollable>
          <v-btn text color="primary" @click="menu = false">Cancel</v-btn>
          <v-btn text color="primary" @click="$refs.menu.save(date)">OK</v-btn>
        </v-date-picker>
      </v-menu>
</template>

<script>
export default {
  name: "aeris-datavalidation-datemounthpicker",
  props: {
    date_label : {
      type : String,
      default : "Date"
    },
    setCurrentDate : {
      type : Function
    }
  },
  data() {
    return {
      menu: false,
      date: null,
    }
  },
  watch: {
    date(val) {
      this.setCurrentDate(val)
    }
  }
}
</script>

<style scoped>

</style>