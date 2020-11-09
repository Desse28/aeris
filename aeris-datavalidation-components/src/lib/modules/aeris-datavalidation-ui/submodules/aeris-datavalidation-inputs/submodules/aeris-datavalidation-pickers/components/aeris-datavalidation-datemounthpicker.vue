<template>
      <v-menu
          ref="menu"
          v-model="menu"
          :close-on-content-click="false"
          :return-value.sync="date"
          transition="scale-transition"
          offset-y
          min-width="290px"
          :disabled="disabled"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
              v-model="computedDateFormatted"
              :label="dateLabel"
              prepend-icon="mdi-calendar"
              readonly
              v-bind="attrs"
              v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
            v-model="date"
            no-title scrollable
            :value="currentDate"
            @input="$refs.menu.save(date)"
            :max="maxDate"
            :min="minDate"
        >
        </v-date-picker>
      </v-menu>
</template>

<script>
export default {
  name: "aeris-datavalidation-datemounthpicker",
  props: {
    disabled : {
      type : Boolean,
      default : () => false
    },
    dateLabel : {
      type : String,
      default : "Date"
    },
    setCurrentDate : {
      type : Function
    },
    currentDate : {
      type : String,
    },
    minDate : {
      type : String,
      default : () => ""
    },
    maxDate : {
      type : String,
      default : () => ""
    },
  },
  data() {
    return {
      menu: false,
      date: "",
    }
  },
  computed: {
    computedDateFormatted () {
      return this.$root.formatDate(this.date)
    }
  },
  watch: {
    date(newDate) {
      this.setCurrentDate(newDate)
    },
    currentDate : function(currentDate) {
      this.date = currentDate
    }
  }
}
</script>

<style scoped>

</style>