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
                  :currentDate="getDefaultstartDate"
                  :setCurrentDate="setStartDate"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationDateMounthPicker
                  :dateLabel="$t('session.label_endDate')"
                  :currentDate="getDefaultendDate"
                  :setCurrentDate="setEndDate"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationTimePicker
                  :time_label="$t('session.label_startTime')"
                  :currentTime="getDefaultstartTime"
                  :setCurrentTime="setStartTime"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="6">
              <AerisDatavalidationTimePicker
                  :time_label="$t('session.label_endTime')"
                  :currentTime="getDefaultendTime"
                  :setCurrentTime="setEndTime"
                  :disabled="false"
              />
            </v-col>
            <v-col cols="12">
              <AerisDatavalidationSelect
                  name="flags"
                  itemText="concatName"
                  :qualityFlags="getQualityFlags"
                  :flag_message="$t('session.label_qualityFlags')"
                  :setFlagsSelected="setFlagsSelected"
                  :defaultQualityFlags="getDefaultFlags"
              />
            </v-col>
            <v-col cols="12">
              <v-card-actions>
                <v-btn
                    v-if="this.currentView === this.$t('session.label_selection')"
                    v-on:click="saveSelection"
                    :disabled="isSelectedFlagsEmpty"
                >
                  {{$t('session.label_save')}}
                </v-btn>
                <v-btn
                    v-else
                    v-on:click="editSelection"
                    :disabled="isSelectedFlagsEmpty"
                >
                  {{$t('session.label_save')}}
                </v-btn>
                <v-btn
                    v-if="this.currentView !== this.$t('session.label_selection')"
                    v-on:click="deleteCurrentSelecton"
                >
                  {{$t('session.label_remove')}}
                </v-btn>
                <v-btn
                    v-on:click="cancelCurrentSelection"
                >
                  {{ $t('session.label_cancel') }}
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
    currentView : {
      type : String
    },
    qualityFlags : {
      type : Array,
      default : () => []
    },
    selection : {
      type : Object,
      default : null,
    },
    targetSelection : {
      type : Object,
      default : null,
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
      currentSelection : null,
      isResetSelection: false,
      isEditSelectionExist: false,
    }
  },
  watch : {
    selection : function () {

      if(this.isResetSelection) {
        this.isResetSelection = false
      } else {
        this.currentSelection = null
        this.initForm()
      }
    },
  },
  computed : {
    isSelectedFlagsEmpty : function() {
      return this.selectedFlags.length === 0
    },
    getDefaultstartDate : function () {
      if(this.currentSelection) {
        return this.$root.getDatePikerDateFormat(this.currentSelection.startDate)
      }
      return ""
    },
    getDefaultstartTime : function() {
      if(this.currentSelection)
        return this.$root.getTimePickerTimeFormat(this.currentSelection.startDate)
      return ""
    },
    getDefaultendDate : function () {
      if(this.currentSelection)
        return this.$root.getDatePikerDateFormat(this.currentSelection.endDate)
      return ""
    },
    getDefaultendTime : function () {
      if(this.currentSelection)
        return this.$root.getTimePickerTimeFormat(this.currentSelection.endDate)
      return ""
    },
    getDefaultFlags : function () {
      const mainCharts = this.session.charts[0]
      let startDate = this.selection.startDate
      let endDate = this.selection.endDate
      if(this.$root.isSelectionExist(mainCharts.selections, startDate, endDate)) {
        return this.concatFlagCodeLabel(this.$root.getTargetSelection(mainCharts.selections, startDate, endDate).flags)
      } else if(this.currentSelection && this.currentView !== this.$t('session.label_selection')) {
        return this.concatFlagCodeLabel(this.selectedFlags)
      } else {
        return []
      }
    },
    getQualityFlags : function() {
      return this.concatFlagCodeLabel(this.qualityFlags)
    }
  },
  mounted() {
    this.initForm()
  },
  methods: {
    initForm : function() {
      if(this.currentSelection === null) {
        this.initDefaultDates(this.selection)
        this.currentSelection = this.selection
      }
    },
    initDefaultDates : function(selection) {
      this.startDate = this.$root.getDatePikerDateFormat(selection.startDate)
      this.startTime = this.$root.getTimePickerTimeFormat(selection.startDate)
      this.endDate = this.$root.getDatePikerDateFormat(selection.endDate)
      this.endTime = this.$root.getTimePickerTimeFormat(selection.endDate)
    },
    concatFlagCodeLabel : function(flags) {
      if(flags) {
        flags.forEach((flag)=>{
          flag.concatName = flag.code + ", " + flag.label
        })
      }
      return flags
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
      if(!this.isEmptyDate()) {
        startDate = this.startDate + " " + this.startTime
        endDate = this.endDate + " " + this.endTime
        this.notifySelection(startDate, endDate)
      }
    },
    isEmptyDate : function() {
      return (this.startDate === "" || this.startTime === "" || this.endDate === "" || this.endTime === "")
    },
    saveSelection : function() {
      let mainChart
      let charts = this.session.charts
      let startDate = this.$root.getSpringDateFormat(this.startDate + " " + this.startTime)
      let endDate = this.$root.getSpringDateFormat(this.endDate + " " + this.endTime)

      if(charts) {
        mainChart = charts[0]
        if(!this.$root.isSelectionExist(mainChart.selections,startDate, endDate)) {
          this.createNewSelection(startDate, endDate, mainChart)
          this.submitSelection()
        } else {
          this.alertExistSelection()
        }
      }
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
      let mainChart
      let charts = this.session.charts
      const newStartDate = this.$root.getSpringDateFormat(this.startDate + " " + this.startTime)
      const newEndDate = this.$root.getSpringDateFormat(this.endDate + " " + this.endTime)

      if(charts) {
        mainChart = charts[0]

        mainChart.selections.forEach( (selection) => {
          if(this.$root.isSameDate(selection.startDate, this.targetSelection.startDate) &&
              this.$root.isSameDate(selection.endDate, this.targetSelection.endDate)) {
            selection.startDate = newStartDate
            selection.endDate = newEndDate
            selection.flags = this.selectedFlags
            this.submitSelection()
          }
        })
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
    cancelCurrentSelection : function () {
      const startDate = this.startDate + " " + this.startTime
      const endDate = this.endDate + " " + this.endTime
      let selections = this.session.charts[0].selections

      if(!this.$root.isSelectionExist(selections,startDate, endDate) &&
          this.currentView !== this.$t('session.label_selection')) {
        this.isResetSelection = true
      }

      this.notifyCancelPopUp({startDate : startDate, endDate : endDate})
    },
    submitSelection : function() {
      this.typeOfRequest = "PUT"
      this.requestData = this.session
      this.callBack = (selection) => {
        this.currentUrl=""
        if(selection) {
          //this.alertIsRecorded()
          this.notifyCancelPopUp()
        }
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + UPDATE_SESSION_PATH
    },
    alertIsRecorded : function () {
      this.isRecorded = true
      setTimeout(() => {
        this.isRecorded = false
      }, 1000);
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