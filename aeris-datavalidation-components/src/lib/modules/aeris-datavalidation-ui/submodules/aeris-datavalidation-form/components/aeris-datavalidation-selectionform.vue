<template>
  <div class="pa-8">
    <v-row>
      <v-col cols="12" sm="6" md="6">
        <AerisDatavalidationDateMounthPicker/>
        <v-row>
          <v-col cols="6">
            <AerisDatavalidationTimePicker
              :time_label="start_time"
            />
          </v-col>
          <v-col cols="6">
            <AerisDatavalidationTimePicker
                :time_label="end_time"
            />
          </v-col>
          <v-col cols="12">
            <AerisDatavalidationSelect/>
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
      start_time : "Start",
      end_time : "End",
    }
  },
}
</script>

<style scoped>

</style>