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
            text @click="startNewSession"
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
import {
  AerisDataValidationServices,
  AerisDatavalidationPortraitLayaout,
} from "@/lib/modules/aeris-datavalidation-components";

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
    startNewSession : function() {
      this.currentSession = {
        mainParameter : this.mainParameter,
        linkedParameters : this.linkedParameters,
        instrumentName : this.currentInstrument.name,
      }
      this.createNewSession()
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
    isExistSession : function () {
      let session
      if(this.currentSession && this.sessions) {
        for (let key in this.sessions) {
          session = this.sessions[key]
          if(this.isSameMainParameter(session) || this.isSameLinkedParameters(session))
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