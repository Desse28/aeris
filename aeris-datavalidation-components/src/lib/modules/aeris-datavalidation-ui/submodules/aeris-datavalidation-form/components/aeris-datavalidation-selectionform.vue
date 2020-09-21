<template>
  <div class="pa-8">
    <v-row>
      <v-col :cols="12" :sm="12" :md="12">
        <v-row>
          <v-col cols="6">
            <AerisDatavalidationDateMounthPicker
                date_label="Start date"
                :currentDate="startDate"
                :setCurrentDate="setStartDate"
                :disabled="false"
            />
          </v-col>
          <v-col cols="6">
            <AerisDatavalidationDateMounthPicker
                date_label="End date"
                :currentDate="endDate"
                :setCurrentDate="setEndDate"
                :disabled="false"
            />
          </v-col>
          <v-col cols="6">
            <AerisDatavalidationTimePicker
                time_label="Start time"
                :currentTime="startTime"
                :setCurrentTime="setStartTime"
                :disabled="false"
            />
          </v-col>
          <v-col cols="6">
            <AerisDatavalidationTimePicker
                time_label="End time"
                :currentTime="endTime"
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
    selection: {
      type: Object,
      default: null,
    },
    notifySelection : {
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
      selectedFlags: [],
    }
  },
  watch : {
    selection: function () {
      let selectionDate = this.getSelectionDate()

      if(selectionDate) {
        if((this.startDate !== selectionDate.startDate || this.startTime !== selectionDate.startTime) ||
            this.endDate !== selectionDate.endDate || this.endTime !== selectionDate.endTime) {
          this.setCurrentDate(selectionDate)
        }
      }
    }
  },
  mounted() {
    this.initSelectionForm()
  },

  methods: {
    initSelectionForm : function() {
      let selectionDate = this.getSelectionDate()
      if(selectionDate !== null) {
        this.setCurrentDate(selectionDate)
      }
    },
    getSelectionDate : function () {
      let startDate, startTime, endDate, endTime

      if(this.selection) {
        endDate= this.$root.getDatePikerDateFormat(this.selection.endDate, "en")
        startDate= this.$root.getDatePikerDateFormat(this.selection.startDate, "en")
        startTime= this.$root.getTimePickerTimeFormat(this.selection.startDate)
        endTime= this.$root.getTimePickerTimeFormat(this.selection.endDate)
        return {startDate : startDate, startTime : startTime, endDate: endDate, endTime : endTime}
      }
      return null;
    },
    setCurrentDate : function(currentDate) {
      if(currentDate) {
        this.startDate = currentDate.startDate
        this.startTime = currentDate.startTime
        this.endDate = currentDate.endDate
        this.endTime = currentDate.endTime
      }
    },
    setStartDate : function(startDate) {
      if(this.startDate !== startDate) {
        this.startDate = startDate
        this.notifyDateChange()
      }
    },
    setStartTime : function (startTime) {
      if(this.startTime !== startTime) {
        this.startTime = startTime
        this.notifyDateChange()
      }
    },
    setEndDate : function(endDate) {
      if(this.endDate !== endDate) {
        this.endDate = endDate
        this.notifyDateChange()
      }
    },
    setEndTime : function (endTime) {
      if(this.endTime !== endTime) {
        this.endTime = endTime
        this.notifyDateChange()
      }
    },
    setFlagsSelected : function (flags) {
      this.selectedFlags = flags
    },
    notifyDateChange : function() {
      let startDate, endDate
      if(this.startDate !== "" && this.startTime !== "" &&
          this.endDate !== "" && this.endTime !== "") {
        startDate = this.startDate + " " + this.startTime
        endDate = this.endDate + " " + this.endTime
        this.notifySelection(startDate, endDate)
      }
    },
    saveSelection : function() {
      //Here submit selection
    }
  },
}
</script>

<style scoped>

</style>