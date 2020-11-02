<template>
  <div>
    <v-alert type="error" v-if="newSessionAlertError">
      {{ newSessionAlertMessage  }}
    </v-alert>
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
            text @click="create"
        >
          {{ $t("configuration.label_create") }}
        </v-btn>
        <v-btn
            type="submit"
            color="blue darken-1"
            text @click="setCurrentView('configuration.label_continueSession')"
        >
          {{ $t("configuration.label_sessions") }}
        </v-btn>
      </v-card-actions>
  </div>
</template>

<script>
import AerisDatavalidationPortraitLayaout from "../../aeris-datavalidation-layouts/components/aeris-datavalidation-potraitlayout"


const MAIN_CHART_EN_NAME = "Main chart"
const SECOND_CHART_EN_NAME = "Secondary chart"
const MAIN_CHART_FR_NAME = "Graphique principal"
const SECOND_CHART_FR_NAME = "Graphique secondaire"

export default {
  name: "aeris-datavalition-sessionform",
  components : {
    AerisDatavalidationPortraitLayaout,
  },
  props : {
    parameters : {
      type : Array
    },
    instruments : {
      type : Array
    },
    sessions : {
      type : Array,
      default : () => []
    },
    getInstrument : {
      type : Function,
    },
    initNewSession : {
      type : Function,
    },
    setCurrentView : {
      type : Function
    },
    getPiInstruments : {
      type : Function,
    },
    createNewSession : {
      type : Function
    },
    currentInstrument : {
      type : Object
    }
  },
  watch : {
    instrument : function(instrumentIdObj) {
      this.getInstrument(instrumentIdObj)
    },
  },
  data() {
    return {
      colorCount: 0,
      instrument : null,
      mainParameter : null,
      linkedParameters : [],
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
      this.getPiInstruments()
    },
    create : function () {
      let charts = [this.getMainChart(), this.getSecondaryChart()]

      let newSession = {
        charts : charts,
        mainParameter : this.mainParameter,
        linkedParameters : this.linkedParameters,
        instrumentName : this.currentInstrument.name,
      }

      if(!this.isExistSession(newSession)) {
        this.createNewSession(newSession)
      } else {
        this.enableAlert(this.$t('configuration.message_existSession'))
      }
    },
    getMainChart : function() {
      let mainChart = {
        frName : MAIN_CHART_FR_NAME,
        enName : MAIN_CHART_EN_NAME,
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
        frName : SECOND_CHART_FR_NAME,
        enName : SECOND_CHART_EN_NAME,
        parameters : auxParameters
      }
    },
    getParametersWithColors : function(parameters) {
      let result = []
      if(parameters) {
        result = parameters.slice()
        result.forEach((parameter) => {
          //parameter.isOn = true
          parameter.color = this.$root.getNewColor(this.colorCount)
          this.colorCount++
        })
      }
      return result
    },
    isExistSession : function (newSession) {
      return this.sessions.some( (session) => {
        return this.isSameMainParameter(session, newSession) || this.isSameLinkedParameters(session, newSession)
      })
    },
    isSameMainParameter : function({mainParameter}, newSession) {
      let exist = false
      if(newSession) {
        exist = mainParameter.name === newSession.mainParameter.name
      }
      return exist
    },
    isSameLinkedParameters : function({linkedParameters}, newSession) {
      let parameters = linkedParameters.filter((parameter) => {
        return newSession.linkedParameters.some((currentParam) => {
              return currentParam.name === parameter.name
            }
        )
      })
      return parameters.length === linkedParameters.length
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