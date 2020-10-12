<template>
  <div class="pa-8">
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
        :requestData="requestData"
        :typeOfRequest="typeOfRequest"
    >
      <v-alert type="success" v-if="isRecorded">
        {{ $t("session.message_recordedSelection") }}
      </v-alert>
      <v-alert type="error" v-if="existSelection">
        {{ $t("session.message_existSelection") }}
      </v-alert>
      <v-alert type="warning" v-if="isEditSelectionExist">
        {{ $t("session.message_canNotEdit") }}
      </v-alert>
      <v-row>
        <v-col :cols="12" :sm="12" :md="12">
          <v-row>
            <v-col cols="6">
              <AerisDatavalidationDateMounthPicker
                  :dateLabel="$t('session.label_startDate')"
                  :currentDate="startDate"
                  :setCurrentDate="setStartDate"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationDateMounthPicker
                  :dateLabel="$t('session.label_endDate')"
                  :currentDate="endDate"
                  :setCurrentDate="setEndDate"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationTimePicker
                  :time_label="$t('session.label_startTime')"
                  :currentTime="startTime"
                  :setCurrentTime="setStartTime"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationTimePicker
                  :time_label="$t('session.label_endTime')"
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
                  :flag_message="$t('session.label_qualityFlags')"
                  :setFlagsSelected="setFlagsSelected"
                  :defaultQualityFlags="defaultQualityFlags"
              />
            </v-col>
            <v-col cols="12">
              <v-card-actions>
                <v-btn
                    v-if="isSelectionMode"
                    v-on:click="saveSelection"
                    :disabled="false"
                >
                  {{$t('session.label_save')}}
                </v-btn>
                <v-btn
                    v-else
                    v-on:click="editSelection"
                    :disabled="false"
                >
                  {{$t('session.label_save')}}
                </v-btn>
                <v-btn
                    v-on:click="notifyCancelPopUp"
                >
                  {{$t('session.label_cancel')}}
                </v-btn>
              </v-card-actions>
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
      type : Object,
      default : null
    },
    qualityFlags : {
      type : Array,
      default : () => []
    },
    selection : {
      type : Object,
      default : null,
    },
    sessionSelection : {
      type : Object,
    },
    isSelectionMode : {
      type : Boolean,
      default : () =>true,
    },
    notifySelection : {
      type : Function,
      default : () => {}
    },
    notifyCancelPopUp : {
      type : Function,
      default : () => {}
    },
    switchCurrentView : {
      type : Function,
      default : () => {}
    }
  },
  data() {
    return {
      endDate: "",
      endTime: "",
      startDate: "",
      startTime: "",
      callBack: null,
      currentUrl: "",
      requestData: null,
      typeOfRequest: "",
      selectedFlags: [],
      isRecorded: false,
      existSelection : false,
      defaultQualityFlags: null,
      isEditSelectionExist: false,
      currentSessionSelection: null,
    }
  },
  watch : {
    selection: function () {
      let selectionDate
      if(!this.isSelectionEmpty()) {
        selectionDate = this.getSelectionDate(this.selection, false)
        if(selectionDate) {
          if(this.isDateChange(selectionDate)) {
            this.setCurrentDate(selectionDate)
          }
        }
      } else {
        this.resetDate()
      }
      this.initDefaultQualityFlags()
    },
    sessionSelection: {
      immediate: true,
      handler(selection) {
        if(selection !== null ) {
          this.currentSessionSelection = selection
          this.defaultQualityFlags = selection.flags
        }
      },
    },
  },
  mounted() {
    this.initForm()
  },
  methods: {
    initDefaultQualityFlags : function () {
      if(this.isSelectionMode)
        this.defaultQualityFlags = []
      else if(this.currentSessionSelection)
        this.defaultQualityFlags = this.currentSessionSelection.flags
    },
    initForm : function() {
      if(this.isSelectionMode) {
        this.initSelectionForm()
      } else {
        this.initDefaultParameters()
      }
    },
    initSelectionForm : function() {
      let selectionDate = this.getSelectionDate(this.selection, false)
      if(selectionDate !== null) {
        this.defaultQualityFlags = []
        this.setCurrentDate(selectionDate)
      }
    },
    initDefaultParameters : function() {
      let selectionDate
      if(this.currentSessionSelection) {
        this.defaultQualityFlags = this.currentSessionSelection.flags
        selectionDate = this.getSelectionDate(this.currentSessionSelection, true)
        this.setCurrentDate(selectionDate)
        this.notifyDateChange()
      }
    },
    getSelectionDate : function (selection, isUtcFormat) {
      let startDate, startTime, endDate, endTime

      if(selection) {
        endDate= this.$root.getDatePikerDateFormat(selection.endDate)
        startDate= this.$root.getDatePikerDateFormat(selection.startDate)
        startTime= isUtcFormat ? this.$root.getTimeUniverselFormat(selection.startDate) : this.$root.getTimePickerTimeFormat(selection.startDate)
        endTime= isUtcFormat ? this.$root.getTimeUniverselFormat(selection.endDate) : this.$root.getTimePickerTimeFormat(selection.endDate)
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
        this.createNewSelection(selectionStartDate, selectionEndDate)
        this.submitSelection()
      } else {
        this.activeExistSelectionAlert()
      }
    },
    createNewSelection : function(selectionStartDate, selectionEndDate) {
      let selection = {
        startDate : selectionStartDate,
        endDate : selectionEndDate,
        flags : this.selectedFlags,
      }
      this.session.sessionSelections.push(selection)
    },
    editSelection : function() {
      let startDate = this.startDate + " " + this.startTime
      let endDate = this.endDate + " " + this.endTime

      if(this.session) {
        this.setTargetSelection(startDate, endDate)
        this.submitSelection()
      }
    },
    setTargetSelection : function(startDate, endDate) {
      let selectionStartDate, selectionEndDate

      this.session.sessionSelections.forEach((selection) => {
        selectionStartDate = this.$root.getCleanDate(selection.startDate)
        selectionEndDate = this.$root.getCleanDate(selection.endDate)

        if(selectionStartDate === startDate && selectionEndDate === endDate) {
          this.activeNoEditAlert()
          return
        }

        if(this.currentSessionSelection.startDate === selection.startDate &&
            this.currentSessionSelection.endDate === selection.endDate) {
          selection.flags = this.selectedFlags
          selection.startDate = this.$root.getSpringDateFormat(startDate)
          selection.endDate = this.$root.getSpringDateFormat(endDate)
        }
      })
    },
    submitSelection : function() {
      this.typeOfRequest = "PUT"
      this.requestData = this.session
      this.callBack = (selection) => {
        if(selection) {
          this.activeIsRecordedAlert()
          this.switchCurrentView(this.$t('session.label_edit'))
          this.notifyCancelPopUp()
        }
        this.currentUrl=""
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + "/sessions/update"
    },
    isSelectionExist: function(selectionStartDate, selectionEndDate) {
      let selections = this.session.sessionSelections
      for(const index in selections) {
        if(selections[index].startDate === selectionStartDate &&
            selections[index].endDate === selectionEndDate)
          return true
      }
      return false
    },
    activeIsRecordedAlert : function () {
      this.isRecorded = true
      setTimeout(() => {
        this.isRecorded = false
      }, 2000);
    },
    activeExistSelectionAlert : function () {
      this.existSelection=true
      setTimeout(() => {
        this.existSelection = false
      }, 2000);
    },
    activeNoEditAlert : function () {
      this.isEditSelectionExist = true
      setTimeout(() => {
        this.isEditSelectionExist = false
      }, 2000);
    },
    isSelectionEmpty : function () {
      return (this.selection.startDate === "" || this.selection.endDate === "")
    },
    isDateChange : function (selectionDate) {
      return ((this.startDate !== selectionDate.startDate || this.startTime !== selectionDate.startTime) ||
          this.endDate !== selectionDate.endDate || this.endTime !== selectionDate.endTime)
    },
  },
}
</script>