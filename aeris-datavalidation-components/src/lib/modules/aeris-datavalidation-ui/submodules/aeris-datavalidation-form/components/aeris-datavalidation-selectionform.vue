<template>
  <div class="pa-8">
    <v-row>
      <v-col cols="12" sm="6" md="6">
        <AerisDatavalidationDateMounthPicker
          :date_label="date_label"
        />
        <v-row>
          <v-col cols="6">
            <AerisDatavalidationTimePicker
              :time_label="start_label"
            />
          </v-col>
          <v-col cols="6">
            <AerisDatavalidationTimePicker
                :time_label="end_label"
            />
          </v-col>
          <v-col cols="12">
            <AerisDatavalidationSelect
                :flag_message="flag_message"
            />
          </v-col>
          <v-col cols="12">
            <div class="my-2">
              <v-btn>{{save_label}}</v-btn>
            </div>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import AerisDatavalidationSelect from "./../../aeris-datavalidation-inputs/components/submodules/aeris-datavalidation-selects/aeris-datavalidation-select"
import AerisDatavalidationDateMounthPicker from "./../../aeris-datavalidation-inputs/components/submodules/aeris-datavalidation-pickers/aeris-datavalidation-datemounthpicker"
import AerisDatavalidationTimePicker from "./../../aeris-datavalidation-inputs/components/submodules/aeris-datavalidation-pickers/aeris-datavalidation-timepicker"

export default {
  name: "aeris-datavalidation-selectionform",
  components: {
    AerisDatavalidationDateMounthPicker,
    AerisDatavalidationTimePicker,
    AerisDatavalidationSelect
  },
  data() {
    return {
      start_label : "Start",
      end_label : "End",
      flag_message : "Choose quality flag",
      date_label : "Date",
      save_label : "Save",
    }
  },
}
</script>

<style scoped>

</style>