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
                :label="$t('configuration.label_instrumentName')"
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
                :label="$t('configuration.label_mainParameter')"
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
                :label="$t('configuration.label_linkedParameters')"
                name="linkedParameters"
                return-object
                multiple
                :disabled="parametersAuthorization"
            ></v-autocomplete>
          </template>
          <template v-slot:portrait4>
              <AerisDatavalidationDateMounthPicker
                  :dateLabel="$t('configuration.label_startDate')"
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
                  :dateLabel="$t('configuration.label_endDate')"
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
                :time_label="$t('configuration.label_startTime')"
                :setCurrentTime="setStartTime"
                :disabled="parametersAuthorization"
            />
          </template>
          <template v-slot:portrait7>
            <AerisDatavalidationTimePicker
                :time_label="$t('configuration.label_endTime')"
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
          {{ $t("configuration.label_sessions") }}
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
    sessions: {
      type : Array,
      default: () => []
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
      currentSession: null,
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
        this.initSession(startDateTime, endDateTime)

        if(this.mainParameterInLinkedParameter()) {
          this.turnOnErrorAlert(this.$t('configuration.message_mainParameterInLinkedParameters'))
        } else {
          this.createNewSession(startDateTime, endDateTime)
        }
      } else {
        this.turnOnErrorAlert(this.$t('configuration.message_greaterThan'))
      }

      this.turnOffErrorAlert(2000);
    },
    mainParameterInLinkedParameter() {
      let mainParameterName
      if(this.currentSession) {
        mainParameterName = this.currentSession.mainParameter.name
        if(this.currentSession.linkedParameters.some(currentParam => currentParam.name === mainParameterName))
          return true
      }
      return false
    },
    createNewSession : function () {
      if(!this.isExistSession()) {
        this.requestData = this.currentSession
        this.typeOfRequest = "POST"
        this.callBack = (session) => {
          this.currentUrl=""
          if(session) {
            this.getInstrumentInfos(this.currentInstrument['uuid'], (infos) => {
              this.initNewSession(session, this.currentInstrument, infos)
            })
          }
        }
        this.currentUrl = process.env.VUE_APP_ROOT_API + "/sessions/create"
      } else {
        this.turnOnErrorAlert(this.$t('configuration.message_existSession'))
      }
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
    initSession : function (startDateTimeStr, endDateTimeStr) {
      let endDateTime = this.$root.getSpringDateFormat(endDateTimeStr)
      let startDateTime = this.$root.getSpringDateFormat(startDateTimeStr)

      this.currentSession = {
        endDate : endDateTime,
        startDate : startDateTime,
        mainParameter : this.mainParameter,
        linkedParameters : this.linkedParameters,
        instrumentName : this.currentInstrument.name,
      }
    },
    continueSession : function () {
      this.setCurrentItem(this.$t('configuration.label_continueSession'))
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
    isExistSession : function () {
      let session
      if(this.currentSession && this.sessions) {
        for (let key in this.sessions) {
          session = this.sessions[key]
          if(this.isSameMainParameter(session) || this.isSamePeriod(session) || this.isSameLinkedParameters(session))
            return true
        }
      }
      return false
    },
    isSameMainParameter : function(session) {
      let exist = false

      if(session && this.currentSession) {
        exist = session.mainParameter.name === this.currentSession.mainParameter.name
      }

      return exist
    },
    isSamePeriod : function(session) {
      let exist = false
      if(session && this.currentSession) {
        exist = this.$root.isSameDate(session.startDate, this.currentSession.startDate) &&
                this.$root.isSameDate(session.endDate, this.currentSession.endDate)
      }
      return exist
    },
    isSameLinkedParameters : function(session) {
      let parameter, parameters
      if(session && this.currentSession) {
        parameters = session.linkedParameters

        if(parameters.length === 0 && this.currentSession.linkedParameters.length === 0)
          return true

        if(parameters.length === this.currentSession.linkedParameters.length) {
          for (let key in parameters) {
            parameter = parameters[key]
            if(!this.currentSession.linkedParameters.some(currentParam => currentParam.name === parameter.name))
              return false
          }
          return true
        }
      }
      return false
    }
  }
}
</script>
<style scoped>

</style>