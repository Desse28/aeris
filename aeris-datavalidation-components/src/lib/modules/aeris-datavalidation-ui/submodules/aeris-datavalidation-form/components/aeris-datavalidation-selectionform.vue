<template>
  <div class="pa-8">
    <v-row>
      <v-col
          :cols="selectionFormCol[0]"
          :sm="selectionFormCol[1]"
          :md="selectionFormCol[2]"
      >
        <v-row>
          <v-col cols="6">
            <AerisDatavalidationDateMounthPicker
                 date_label="Start date"
                :currentDate="selectionStartDate"
                :setCurrentDate="setSelectionStartDate"
                :disabled="selectionStartDate === ''"
            />
          </v-col>
          <v-col cols="6">
            <AerisDatavalidationDateMounthPicker
                 date_label="End date"
                :currentDate="selectionEndDate"
                :setCurrentDate="setSelectionEndDate"
                :disabled="selectionEndDate === ''"
            />
          </v-col>
          <v-col cols="6">
            <AerisDatavalidationTimePicker
              :time_label="start_label"
              :currentTime="selectionStartTime"
              :setCurrentTime="setSelectionStartTime"
              :disabled="selectionStartTime === ''"
            />
          </v-col>
          <v-col cols="6">
            <AerisDatavalidationTimePicker
                :time_label="end_label"
                :currentTime="selectionEndTime"
                :setCurrentTime="setSelectionEndTime"
                :disabled="selectionEndTime === ''"
            />
          </v-col>
          <v-col cols="12">
            <AerisDatavalidationSelect
                name="flags"
                itemText="label"
                :qualityFlags="qualityFlags"
                :flag_message="flag_message"
                :setQualityFlags="setQualityFlags"
                :qualityFlagsDefaultValue="qualityFlagsDefaultValue"
            />
          </v-col>
          <v-col cols="12">
            <div class="my-2">
              <v-btn
                  v-on:click="addSelection"
                  :disabled="
                  selectionStartDate === '' ||
                  selectionEndDate === '' ||
                  selectionStartTime === '' ||
                  selectionEndTime === '' ||
                  qualityFlagsSelection.length === 0"
              >
                {{buttonLabel}}
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
    selectionStartDate : {
      type : String,
      default : ""
    },
    selectionEndDate : {
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
    },
    selectionFormCol : {
      type : Array,
      default : () => [12, 12, 12],
    },
    callBack : {
      type : Function
    },
    buttonLabel : {
      type : String,
      default : "Submit"
    },
    setSelectionStartTime : {
      type : Function,
    },
    setSelectionEndTime : {
      type : Function,
    },
    setSelectionStartDate : {
      type : Function,
    },
    setSelectionEndDate : {
      type : Function,
    },
    qualityFlagsDefaultValue : {
      type : Array,
      default : () => [],
    },
  },
  data() {
    return {
      end_label : "End",
      date_label : "Date",
      start_label : "Start",
      flag_message : "Choose quality flag",
      startTime : "",
      endTime : "",
      startDate: "",
      endDate: "",
      qualityFlagsSelection : [],
    }
  },
  methods: {
    setQualityFlags: function(flags) {
      this.qualityFlagsSelection = flags;
    },
    addSelection : function () {
      this.callBack(this.qualityFlagsSelection)
    },
  },
}
</script>

<style scoped>

</style>