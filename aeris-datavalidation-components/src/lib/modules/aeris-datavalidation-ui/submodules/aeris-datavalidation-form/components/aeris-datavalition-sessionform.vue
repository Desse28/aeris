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
                :disabled="disabledMainParameter"
            ></v-select>
          </template>
          <template v-slot:portrait3>
            <v-autocomplete
                :items="getLinkedParameters"
                v-model="linkedParameters"
                item-text="name"
                :label="$t('configuration.label_linkedParameters')"
                name="linkedParameters"
                return-object
                multiple
                :disabled="disabledLinkedParameters"
            ></v-autocomplete>
          </template>
        </AerisDatavalidationPortraitLayaout>
      </v-container>
      <v-card-actions>
        <v-btn
            type="submit"
            color="blue darken-1"
            :disabled="disabledCreateButton"
            text @click="createNewSession"
        >
          {{ $t("configuration.label_create") }}
        </v-btn>
        <v-btn
            type="submit"
            color="blue darken-1"
            text @click="setCurrentItem('configuration.label_continueSession')"
        >
          {{ $t("configuration.label_sessions") }}
        </v-btn>
      </v-card-actions>
    </AerisDataValidationServices>
  </div>
</template>

<script>
import AerisDataValidationServices from "../../../../aeris-datavalidation-services/components/aeris-datavalidation-services"
import AerisDatavalidationPortraitLayaout from "../../aeris-datavalidation-layouts/components/aeris-datavalidation-potraitlayout"

export default {
  name: "aeris-datavalition-sessionform",
  components : {
    AerisDataValidationServices,
    AerisDatavalidationPortraitLayaout,
  },
  props : {
    initNewSession : {
      type : Function,
    },
    setCurrentItem : {
      type : Function
    },
    sessions : {
      type : Array,
      default : () => []
    },
  },
  watch : {
    instrument : function(instrumentIdObj) {
      let instrumentId = instrumentIdObj['_id']['$oid']

      this.callBack = (instrument) => {
        if(instrument) {
          this.currentInstrument = instrument
          this.parameters = this.currentInstrument.parameters
        }
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + "/instruments?id=" + instrumentId
    },
  },
  data() {
    return {
      colorCount: 0,
      parameters : [],
      currentUrl : "",
      callBack : null,
      instruments : [],
      requestData : {},
      instrument : null,
      typeOfRequest : "",
      currentSession: null,
      mainParameter : null,
      linkedParameters : [],
      currentInstrument : null,
      newSessionAlertMessage : "",
      newSessionAlertError : false,
    }
  },
  computed : {
    disabledMainParameter : function () {
      return this.parameters.length === 0
    },
    disabledLinkedParameters : function () {
      return this.mainParameter === null || this.parameters.length === 0
    },
    disabledCreateButton : function () {
      let isMainParameterEmpty = this.mainParameter === null
      let isLinkedParameterEmpty = this.linkedParameters.length === 0
      return isMainParameterEmpty || isLinkedParameterEmpty
    },
    getLinkedParameters : function () {
      return this.parameters.filter((parameter) => {
        return this.mainParameter && this.mainParameter.name !== parameter.name
      })
    }
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
    createNewSession : function () {
      this.initSession()

      if(!this.isExistSession()) {
        this.requestData = this.currentSession
        this.typeOfRequest = "POST"
        this.callBack = (session) => {
          this.currentUrl=""
          if(session) {
            this.getInstrumentInfos(this.currentInstrument['uuid'], (infos) => {
              console.log("Test start newSession : ", infos)
              //this.initNewSession(session, this.currentInstrument, infos)
            })
          }
        }
        this.currentUrl = process.env.VUE_APP_ROOT_API + "/sessions/create"
      } else {
        this.enableAlert(this.$t('configuration.message_existSession'))
      }
    },
    initSession : function () {
      let charts = [this.getMainChart(), this.getSecondaryChart()]
      let linkedParameters = this.getParametersWithColors(this.linkedParameters)

      this.currentSession = {
        charts : charts,
        mainParameter : this.mainParameter,
        linkedParameters : linkedParameters,
        instrumentName : this.currentInstrument.name,
      }

    },
    getMainChart : function() {
      let mainChart = {
        name : "Graphique principal",
        startXaxis : 0,
        endXaxis : 0,
        parameters : [
          {
            color : this.$root.getNewColor(this.colorCount),
            name : this.mainParameter.name,
          }
        ],
        selections : []
      }
      this.colorCount++

      return mainChart
    },
    getSecondaryChart : function() {
      let auxParameters = this.getParametersWithColors(this.currentInstrument.auxParameters)

      return {
        endXaxis : 0,
        startXaxis : 0,
        selections : [],
        name : "Graphique secondaire",
        parameters : auxParameters
      }
    },
    getParametersWithColors : function(parameters) {
      let result = []
      if(parameters) {
        result = parameters.slice()
        result.forEach((parameter) => {
          parameter.color = this.$root.getNewColor(this.colorCount)
          this.colorCount++
        })
      }
      return result
    },
    isExistSession : function () {
      return this.sessions.some( (session) => {
        return this.isSameMainParameter(session) || this.isSameLinkedParameters(session)
      })
    },
    isSameMainParameter : function({mainParameter}) {
      let exist = false
      if(this.currentSession) {
        exist = mainParameter.name === this.currentSession.mainParameter.name
      }
      return exist
    },
    isSameLinkedParameters : function({linkedParameters}) {
      let parameters = linkedParameters.filter((parameter) => {
        return this.currentSession.linkedParameters.some((currentParam) => {
              return currentParam.name === parameter.name
            }
        )
      })
      return parameters.length === linkedParameters.length
    },
    getInstrumentInfos : function (uuid, callBack) {
      this.typeOfRequest = "GET"
      this.callBack = callBack
      this.currentUrl = process.env.VUE_APP_ROOT_API + "/instruments/infos/" + uuid
    },
    enableAlert : function(message) {
      this.newSessionAlertMessage = message
      this.newSessionAlertError = true
      this.disableAlert(2000)
    },
    disableAlert : function(timeOut) {
      setTimeout(() => {
        this.newSessionAlertError = false
      }, timeOut);
    }
  }
}
</script>
<style scoped>

</style>