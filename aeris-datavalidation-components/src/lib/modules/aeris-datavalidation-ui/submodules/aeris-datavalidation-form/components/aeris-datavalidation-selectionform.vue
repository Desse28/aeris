<template>
  <div class="pa-8">
    <v-row>
      <v-col cols="12" sm="6" md="6">
        <AerisDatavalidationDateMounthPicker
            :date_label="date_label"
            :currentDate="selectionDate"
            :setCurrentDate="setCurrentDate"
        />
        <v-row>
          <v-col cols="6">
            <AerisDatavalidationTimePicker
              :time_label="start_label"
              :setCurrentTime="setStartTime"
              :currentTime="selectionStartTime"
            />
          </v-col>
          <v-col cols="6">
            <AerisDatavalidationTimePicker
                :time_label="end_label"
                :setCurrentTime="setEndTime"
                :currentTime="selectionEndTime"
            />
          </v-col>
          <v-col cols="12">
            <AerisDatavalidationSelect
                :qualityFlags="qualityFlags"
                :flag_message="flag_message"
                :setQualityFlags="setQualityFlags"
            />
          </v-col>
          <v-col cols="12">
            <div class="my-2">
              <v-btn v-on:click="saveSelection">
                {{save_label}}
              </v-btn>
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
  props: {
    selectionDate : {
      type : String,
      default : ""
    },
    selectionStartTime : {
      type : String,
      default : ""
    },
    selectionEndTime : {
      type : String,
      default : ""
    },
    qualityFlags : {
      type : Array,
      default : () => [],
    }
  },
  data() {
    return {
      end_label : "End",
      date_label : "Date",
      save_label : "Save",
      start_label : "Start",
      flag_message : "Choose quality flag",
      startTime : null,
      endTime : null,
      currentDate: null,
      qualityFlagsSelection : [],
    }
  },
  methods: {
    setCurrentDate : function(date) {
      this.currentDate = date;
    },
    setStartTime: function(time) {
      this.startTime = time;
    },
    setEndTime: function(time) {
      this.endTime = time;
    },
    setQualityFlags: function(flags) {
      this.qualityFlagsSelection = flags;
      console.log("Test pickerDate : ", this.qualityFlags)
    },
    saveSelection : function () {
      console.log("Test pickerDate : ", this.pickerDate)
    },
  }
}
</script>

<style scoped>

</style>