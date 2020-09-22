<template>
  <div class="pa-8">
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
        :requestData="requestData"
        :typeOfRequest="typeOfRequest"
    >
      <v-alert type="success" v-if="isRecorded">
        {{ $t("session.recorded_selection") }}
      </v-alert>
      <v-alert type="error" v-if="existSelection">
        {{ $t("session.selection_exists") }}
      </v-alert>
      <v-row>
        <v-col :cols="12" :sm="12" :md="12">
          <v-row>
            <v-col cols="6">
              <AerisDatavalidationDateMounthPicker
                  :date_label="$t('session.start_date_input_label')"
                  :currentDate="startDate"
                  :setCurrentDate="setStartDate"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationDateMounthPicker
                  :date_label="$t('session.end_date_input_label')"
                  :currentDate="endDate"
                  :setCurrentDate="setEndDate"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationTimePicker
                  :time_label="$t('session.start_time_input_label')"
                  :currentTime="startTime"
                  :setCurrentTime="setStartTime"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationTimePicker
                  :time_label="$t('session.end_time_input_label')"
                  :currentTime="endTime"
                  :setCurrentTime="setEndTime"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="12">
              <AerisDatavalidationSelect
                  name="flags"
                  itemText="label"
                  :qualityFlags="qualityFlags"
                  :flag_message="$t('session.choose_quality_flag')"
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
    </AerisDataValidationServices>
  </div>
</template>

<script>
import AerisDataValidationServices from "./../../../../aeris-datavalidation-services/components/aeris-datavalidation-services"
import AerisDatavalidationSelect from "./../../aeris-datavalidation-inputs/components/submodules/aeris-datavalidation-selects/aeris-datavalidation-select"
import AerisDatavalidationDateMounthPicker from "./../../aeris-datavalidation-inputs/components/submodules/aeris-datavalidation-pickers/aeris-datavalidation-datemounthpicker"
import AerisDatavalidationTimePicker from "./../../aeris-datavalidation-inputs/components/submodules/aeris-datavalidation-pickers/aeris-datavalidation-timepicker"

export default {
  name: "aeris-datavalidation-selectionform",
  components: {
    AerisDatavalidationDateMounthPicker,
    AerisDatavalidationTimePicker,
    AerisDataValidationServices,
    AerisDatavalidationSelect
  },
  props : {
    session : {
      type: Object,
      default: null
    },
    qualityFlags : {
      type: Array,
      default: () => []
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
      callBack: null,
      currentUrl: "",
      requestData: null,
      typeOfRequest: "",
      selectedFlags: [],
      isRecorded: false,
      existSelection : false,
    }
  },
  watch : {
    selection: function () {
      let selectionDate
      if(this.selection.startDate !== "" && this.selection.endDate !== "") {
        selectionDate = this.getSelectionDate()
        if(selectionDate) {
          if((this.startDate !== selectionDate.startDate || this.startTime !== selectionDate.startTime) ||
              this.endDate !== selectionDate.endDate || this.endTime !== selectionDate.endTime) {
            this.setCurrentDate(selectionDate)
          }
        }
      } else {
        this.resetDate()
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
    resetDate : function () {
      this.startDate = ""
      this.startTime = ""
      this.endDate = ""
      this.endTime = ""
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
      if(this.selectedFlags !== flags) {
        this.selectedFlags = flags
      }
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
      let selectionStartDate = this.$root.getSpringDateFormat(this.startDate + " " + this.startTime)
      let selectionEndDate = this.$root.getSpringDateFormat(this.endDate + " " + this.endTime)

      if(!this.isSelectionExist(selectionStartDate, selectionEndDate)) {
        this.typeOfRequest = "PUT"
        this.initRequestData(selectionStartDate, selectionEndDate)
        this.callBack = (selection) => {
          if(selection) {
            this.isRecorded = true
              setTimeout(() => {
                this.isRecorded = false
              }, 2000);
          }
          this.currentUrl=""
        }
        this.currentUrl = process.env.VUE_APP_ROOT_API + "/sessions/update"
      } else {
        this.existSelection=true
        setTimeout(() => {
          this.existSelection = false
        }, 2000);
      }
    },
    initRequestData : function (selectionStartDate, selectionEndDate) {
      let selection = {
        startDate : selectionStartDate,
        endDate : selectionEndDate,
        flags : this.selectedFlags,
      }
      this.session.sessionSelections.push(selection)
      this.requestData = this.session
    },
    isSelectionExist: function(selectionStartDate, selectionEndDate) {
      let selections = this.session.sessionSelections
      for(const index in selections) {
        if(selections[index].startDate === selectionStartDate &&
            selections[index].endDate === selectionEndDate)
          return true
      }
      return false
    }
  },
}
</script>

<style scoped>

</style>