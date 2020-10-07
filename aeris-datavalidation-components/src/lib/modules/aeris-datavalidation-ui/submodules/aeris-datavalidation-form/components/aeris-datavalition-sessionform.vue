<template>
  <div>
    <v-alert type="error" v-if="newSessionAlertError">
      {{ newSessionAlertMessage  }}
    </v-alert>
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
        :requestData="requestData"
        :typeOfRequest="typeOfRequest"
    >
      <v-container>
        <AerisDatavalidationPortraitLayaout
            justify="center"
            padding="pa-6"
            :cols="[12, 12, 12, 6, 6, 6, 6]"
            :nbrChildElement="7"
        >
          <template v-slot:portrait1>
            <v-select
                :items="instruments"
                v-model="instrument"
                name="name"
                item-text="name"
                item-value="name"
                :label="$t('session.instrument_name')"
                return-object
                required
            ></v-select>
          </template>
          <template v-slot:portrait2>
            <v-select
                :items="parameters"
                v-model="mainParameter"
                name="mainParameter"
                item-text="name"
                :label="$t('session.main_parameter')"
                return-object
                required
                :disabled="parametersAuthorization"
            ></v-select>
          </template>
          <template v-slot:portrait3>
            <v-autocomplete
                :items="parameters"
                v-model="linkedParameters"
                item-text="name"
                :label="$t('session.linked_parameters')"
                name="linkedParameters"
                return-object
                multiple
                :disabled="parametersAuthorization"
            ></v-autocomplete>
          </template>
          <template v-slot:portrait4>
              <AerisDatavalidationDateMounthPicker
                  :dateLabel="$t('session.start_date_input_label')"
                  :minDate="minDate"
                  :maxDate="maxDate"
                  :currentDate="minDate"
                  :linkDate="endDate"
                  :setCurrentDate="setStartDate"
                  :disabled="parametersAuthorization"
              />
          </template>
          <template v-slot:portrait5>
              <AerisDatavalidationDateMounthPicker
                  :dateLabel="$t('session.end_date_input_label')"
                  :minDate="minDate"
                  :maxDate="maxDate"
                  :linkDate="startDate"
                  :currentDate="maxDate"
                  :setCurrentDate="setEndDate"
                  :disabled="parametersAuthorization"
              />
          </template>
          <template v-slot:portrait6>
            <AerisDatavalidationTimePicker
                :time_label="$t('session.start_time_input_label')"
                :setCurrentTime="setStartTime"
                :disabled="parametersAuthorization"
            />
          </template>
          <template v-slot:portrait7>
            <AerisDatavalidationTimePicker
                :time_label="$t('session.end_time_input_label')"
                :setCurrentTime="setEndTime"
                :disabled="parametersAuthorization"
            />
          </template>
        </AerisDatavalidationPortraitLayaout>
      </v-container>
      <v-card-actions>
        <v-btn
            type="submit"
            color="blue darken-1"
            :disabled="disabledCreateButton"
            text @click="startNewSession"
        >
          {{ $t("session.create_button") }}
        </v-btn>
        <v-btn
            type="submit"
            color="blue darken-1"
            text @click="continueSession"
        >
          {{ $t("session.continue_session") }}
        </v-btn>
      </v-card-actions>
    </AerisDataValidationServices>
  </div>
</template>

<script>
import {
  AerisDataValidationServices,
} from "@/lib/modules/aeris-datavalidation-components";

import AerisDatavalidationPortraitLayaout from "./../../aeris-datavalidation-layouts/components/aeris-datavalidation-potraitlayout"
import AerisDatavalidationTimePicker from "./../../aeris-datavalidation-inputs/components/submodules/aeris-datavalidation-pickers/aeris-datavalidation-timepicker"
import AerisDatavalidationDateMounthPicker from "./../../aeris-datavalidation-inputs/components/submodules/aeris-datavalidation-pickers/aeris-datavalidation-datemounthpicker"

export default {
  name: "aeris-datavalition-sessionform",
  components : {
    AerisDataValidationServices,
    AerisDatavalidationTimePicker,
    AerisDatavalidationPortraitLayaout,
    AerisDatavalidationDateMounthPicker,
  },
  props : {
    initNewSession : {
      type : Function,
    },
    setCurrentItem : {
      type : Function
    },
  },
  data() {
    return {
      endDate : "",
      endTime : "",
      minDate: "",
      maxDate: "",
      startDate : "",
      startTime : "",
      parameters : [],
      currentUrl : "",
      callBack : null,
      instruments : [],
      requestData : {},
      instrument : null,
      typeOfRequest : "",
      mainParameter : null,
      currentInstrument : null,
      linkedParameters : [],
      newSessionAlertMessage : "",
      newSessionAlertError : false,
    }
  },
  computed : {
    disabledCreateButton : function () {
      let isMainParameterEmpty = this.mainParameter === null
      let isTimeEmpty = this.startTime === "" || this.endTime === ""
      let isDateEmpty = this.startDate === "" || this.endDate === ""
      let isLinkedParameterEmpty = this.linkedParameters.length === 0
      return  isTimeEmpty || isMainParameterEmpty || isDateEmpty || isLinkedParameterEmpty
    },
    parametersAuthorization : function () {
      return this.parameters.length === 0
    },
  },
  watch : {
    instrument : function(instrumentIdObj) {
      let instrumentId = instrumentIdObj['_id']['$oid']

      this.callBack = (instrument) => {
        if(instrument) {
          this.currentInstrument = instrument
          this.parameters = this.currentInstrument.parameters
          this.maxDate = this.$root.getDatePikerDateFormat(instrument.endDate, "en")
          this.minDate = this.$root.getDatePikerDateFormat(instrument.startDate, "en")
        }
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + "/instruments?id=" + instrumentId
    },
  },
  mounted() {
    this.initForm()
  },
  methods : {
    initForm : function() {
      this.typeOfRequest = "GET"
      this.callBack = (data) => {
        if(data) {
          data.forEach((instrumentObj) => {
            this.instruments.push(JSON.parse(instrumentObj))
          })
        }
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + "/instruments/names"
    },
    resetForm : function () {
      this.endDate = ""
      this.startDate = ""
      this.parameters = []
      this.instruments = []
      this.instrument  = null
      this.mainParameter = null
      this.currentInstrument = null
      this.linkedParameters = []
    },
    startNewSession : function() {
      let startDateTime = this.startDate + " " + this.startTime
      let endDateTime = this.endDate + " " + this.endTime

      if(this.$root.isGreaterThan(endDateTime, startDateTime)) {
        this.createNewSession()
      } else {
        this.turnOnErrorAlert(this.$t('session.greaterThan_message'))
        this.turnOffErrorAlert(2000);
      }
    },
    createNewSession : function () {
      this.initRequestData()
      this.typeOfRequest = "POST"
      this.callBack = (session) => {
        if(session) {
          this.currentUrl=""
          if(this.isExistSession(session)) {
            this.turnOnErrorAlert(this.$t('session.session_exist'))
            this.turnOffErrorAlert(2000);
          } else {
            this.getInstrumentInfos(this.currentInstrument['uuid'], (infos) => {
              this.initNewSession(session, this.currentInstrument, infos)
            })
          }
        }
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + "/sessions/create"
    },
    turnOnErrorAlert : function(message) {
      this.newSessionAlertMessage = message
      this.newSessionAlertError = true
    },
    turnOffErrorAlert : function(timeOut) {
      setTimeout(() => {
        this.newSessionAlertError = false
      }, timeOut);
    },
    getInstrumentInfos : function (uuid, callBack) {
      this.typeOfRequest = "GET"
      this.callBack = callBack
      this.currentUrl = process.env.VUE_APP_ROOT_API + "/instruments/infos/" + uuid
    },
    initRequestData : function () {
      let startDateTime = this.$root.getSpringDateFormat(this.startDate + " " + this.startTime)
      let endDateTime = this.$root.getSpringDateFormat(this.endDate + " " + this.endTime)

      this.requestData = {
        endDate : endDateTime,
        startDate : startDateTime,
        mainParameter : this.mainParameter,
        linkedParameters : this.linkedParameters,
        instrumentName : this.currentInstrument.name,
      }
    },
    continueSession : function () {
      this.setCurrentItem("Continue session")
    },
    setStartDate : function (newStartDate) {
      this.startDate = newStartDate;
    },
    setEndDate : function(newEndDate) {
      this.endDate = newEndDate;
    },
    setStartTime : function(newStartTime) {
      this.startTime = newStartTime
    },
    setEndTime : function(newEndTime) {
      this.endTime = newEndTime
    },
    isExistSession : function (session) {
      let exist = false;
      let isIdEmpty, isSameMainParameter, isSameInstrument

      if(session) {
        isIdEmpty = session.id === null
        isSameMainParameter = session.mainParameter.name === this.requestData.mainParameter.name
        isSameInstrument = session.instrumentName === this.requestData.instrumentName
        exist = isIdEmpty && isSameInstrument && isSameMainParameter
      }
      return exist;
    },
  }
}
</script>
<style scoped>

</style>