<template>
  <div class="pa-8">
    <AerisDatavalidationDeleteDialog
        :dialog="dialog"
        :ok="$t('session.label_yes')"
        :cancel="$t('session.label_no')"
        :okCallBack="validateDelete"
        :cancelCallBack="cancelDelete"
        :title="$t('session.label_deletion')"
        :message="$t('session.message_delete')"
    />
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
                  minDate=""
                  maxDate=""
                  :setCurrentDate="setStartDate"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationDateMounthPicker
                  :dateLabel="$t('session.label_endDate')"
                  :currentDate="endDate"
                  minDate=""
                  maxDate=""
                  :setCurrentDate="setEndDate"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationTimePicker
                  min=""
                  max=""
                  :time_label="$t('session.label_startTime')"
                  :currentTime="startTime"
                  :setCurrentTime="setStartTime"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationTimePicker
                  min=""
                  max=""
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
                    v-if="false"
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
                    v-on:click="deleteCurrentSelecton"
                    v-if="false"
                >
                  {{$t('session.label_delete')}}
                </v-btn>
                <v-btn
                    v-on:click="notifyCancelPopUp"
                >
                  {{ $t('session.label_cancel') }}
                 <!-- {{true? $t('session.label_cancel') : $t('session.label_close')}}-->
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
import AerisDatavalidationDeleteDialog from "./../../aeris-datavalidation-dialogs/components/aeris-datavalidation-deletedialog"
import AerisDatavalidationSelect from "../../aeris-datavalidation-inputs/submodules/aeris-datavalidation-selects/components/aeris-datavalidation-select"
import AerisDatavalidationDateMounthPicker from "../../aeris-datavalidation-inputs/submodules/aeris-datavalidation-pickers/components/aeris-datavalidation-datemounthpicker"
import AerisDatavalidationTimePicker from "../../aeris-datavalidation-inputs/submodules/aeris-datavalidation-pickers/components/aeris-datavalidation-timepicker"

const UPDATE_SESSION_PATH = "/sessions/update"

export default {
  name: "aeris-datavalidation-selectionform",
  components: {
    AerisDatavalidationDateMounthPicker,
    AerisDatavalidationDeleteDialog,
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
    },
    instrumentStartDate : {
      type : String,
      default : () => ""
    },
    instrumentEndDate : {
      type : String,
      default : () => ""
    },
    notifyDeleteSelection : {
      type : Function,
      default : () => {}
    },
  },
  data() {
    return {
      endDate: "",
      endTime: "",
      dialog: false,
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
    selection : function () {
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
          this.defaultQualityFlags = selection ? selection.flags : []
        }
      },
    },
  },
  mounted() {
    this.initDefaultParameters()
  },
  methods: {
    initDefaultQualityFlags : function () {
        this.defaultQualityFlags = this.currentSessionSelection.flags
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
      let mainChart
      let charts = this.session.charts
      let startDate = this.$root.getSpringDateFormat(this.startDate + " " + this.startTime)
      let endDate = this.$root.getSpringDateFormat(this.endDate + " " + this.endTime)
      if(charts) {
        mainChart = charts[0]
        if(!this.isSelectionExist(startDate, endDate, mainChart.selections)) {
          this.createNewSelection(startDate, endDate, mainChart)
          this.submitSelection()
        } else {
          this.alertExistSelection()
        }
      }
    },
    isSelectionExist: function(startDate, endDate, selections) {
      return selections.some((selection) => {
        return (selection.startDate === startDate && selection.endDate === endDate)
      })
    },
    createNewSelection : function(startDate, endDate, chart) {
      let selection = {
          startDate : startDate,
          endDate : endDate,
          flags : this.selectedFlags,
        }
        chart.selections.push(selection)
    },
    editSelection : function() {
      let startDate = this.startDate + " " + this.startTime
      let endDate = this.endDate + " " + this.endTime

      if(this.session) {
        this.setTargetSelection(startDate, endDate)
        this.submitSelection()
      }
    },
    deleteCurrentSelecton : function () {
      this.dialog = true
    },
    cancelDelete : function () {
      this.dialog = false
    },
    validateDelete : function () {
      this.dialog = false
      this.notifyDeleteSelection()
      this.notifyCancelPopUp()
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
          this.alertIsRecorded()
          this.switchCurrentView(this.$t('session.label_edit'))
          this.notifyCancelPopUp()
        }
        this.currentUrl=""
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + UPDATE_SESSION_PATH
    },
    alertIsRecorded : function () {
      this.isRecorded = true
      setTimeout(() => {
        this.isRecorded = false
      }, 2000);
    },
    alertExistSelection : function () {
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