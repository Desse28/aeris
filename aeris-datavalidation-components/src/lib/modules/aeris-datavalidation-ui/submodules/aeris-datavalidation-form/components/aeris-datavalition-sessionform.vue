<template>
  <div>
    <v-alert type="error" v-if="isSessionExist">
      This session exist
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
                label="Instrument name"
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
                label="Main Parameter"
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
                label="Linked Parameters"
                name="linkedParameters"
                return-object
                multiple
                :disabled="parametersAuthorization"
            ></v-autocomplete>
          </template>
          <template v-slot:portrait4>
              <AerisDatavalidationDateMounthPicker
                  date_label="Start date"
                  :setCurrentDate="setStartDate"
              />
          </template>
          <template v-slot:portrait5>
              <AerisDatavalidationDateMounthPicker
                  date_label="End date"
                  :setCurrentDate="setEndDate"
              />
          </template>
          <template v-slot:portrait6>
            <AerisDatavalidationTimePicker
                time_label="Start time"
                :setCurrentTime="setStartTime"
                :disabled="false"
            />
          </template>
          <template v-slot:portrait7>
            <AerisDatavalidationTimePicker
                time_label="End time"
                :setCurrentTime="setEndTime"
                :disabled="false"
            />
          </template>
        </AerisDatavalidationPortraitLayaout>
      </v-container>
      <v-card-actions>
        <v-btn
            type="submit"
            color="blue darken-1"
            :disabled="createSessionAuthorization"
            text @click="createNewSession"
        >
          Create
        </v-btn>
        <v-btn
            type="submit"
            color="blue darken-1"
            text @click="continueSession"
        >
          Continue session
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
      isSessionExist : false,
      currentInstrument : null,
      linkedParameters : [],
    }
  },
  computed : {
    createSessionAuthorization : function () {
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
      this.callBack = (data) => {
        if(data) {
          this.currentInstrument = data
          this.parameters = this.currentInstrument.parameters
        }
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + "/instruments/" + instrumentId
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
    createNewSession : function () {
      this.initRequestData()
      this.typeOfRequest = "POST"

      this.callBack = (session) => {
        if(session) {
          console.log("Test createNewSession : ", session)
          if(this.isExistSession(session)) {
            this.isSessionExist = true
            setTimeout(() => {
              this.isSessionExist = false
            }, 2000);
          } else {
            this.initNewSession(session, this.currentInstrument)
          }
        }
        this.currentUrl=""
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + "/sessions/create"
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
        console.log("Test session exist : ", isIdEmpty, isSameInstrument, isSameInstrument)
        exist = isIdEmpty && isSameInstrument && isSameMainParameter
      }
      return exist;
    },
  }
}
</script>
<style scoped>

</style>