<template>
  <div class="pa-8">
    <v-row>
      <v-col :cols="12" :sm="12" :md="12">
        <v-row>
          <v-col cols="6">
            <AerisDatavalidationDateMounthPicker
                date_label="Start date"
                currentDate=""
                :setCurrentDate="setStartDate"
                :disabled="false"
            />
          </v-col>
          <v-col cols="6">
            <AerisDatavalidationDateMounthPicker
                date_label="End date"
                currentDate=""
                :setCurrentDate="setEndDate"
                :disabled="false"
            />
          </v-col>
          <v-col cols="6">
            <AerisDatavalidationTimePicker
                time_label="Start time"
                currentTime=""
                :setCurrentTime="setStartTime"
                :disabled="false"
            />
          </v-col>
          <v-col cols="6">
            <AerisDatavalidationTimePicker
                time_label="End time"
                :currentTime="''"
                :setCurrentTime="setEndTime"
                :disabled="false"
            />
          </v-col>
          <v-col cols="12">
            <AerisDatavalidationSelect
                name="flags"
                itemText="label"
                :qualityFlags="[]"
                flag_message="Choose quality flag"
                :setFlagsSelected="setFlagsSelected"
            />
          </v-col>
          <v-col cols="12">
            <div class="my-2">
              <v-btn
                  v-on:click="saveSelection"
                  :disabled="false"
              >
                Save
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
  props : {
    session : {
      type: Object,
      default: null
    },
    currentSelection : {
      type: Object,
      default: null
    },
    notifyNewSelection : {
      type: Function,
      default: () => {}
    }
  },
  data() {
    return {
      startDate: "",
      startTime: "",
      endDate: "",
      endTime: "",
      selectedFlags: []
    }
  },
  watch : {
    currentSelection : function () {
      //
    }
  },
  methods: {
    setStartDate : function(startDate) {
      this.startDate = startDate
    },
    setStartTime : function (startTime) {
      this.startTime = startTime
    },
    setEndDate : function(endDate) {
      this.endDate = endDate
    },
    setEndTime : function (endTime) {
      this.endTime = endTime
    },
    setFlagsSelected : function (flags) {
      this.selectedFlags = flags
    },
    saveSelection : function() {
      //Here submit selection
    }
  },
}
</script>

<style scoped>

</style>