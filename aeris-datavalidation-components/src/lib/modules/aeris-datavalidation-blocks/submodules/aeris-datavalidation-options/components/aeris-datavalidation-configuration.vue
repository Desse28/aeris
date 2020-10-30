<template>
  <v-row justify="center">
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
        :requestData="requestData"
        :typeOfRequest="typeOfRequest"
    >
      <v-dialog v-model="dialog" persistent max-width="1000px" :retain-focus="false">
        <v-card min-height="500" max-height="900">
          <v-row>
            <v-col cols="2" offset="1">
              <AerisDatavalidationLangSwitcher/>
            </v-col>
            <v-col offset="1">
              <v-card-title>
                <AerisDatavalidationTypography
                    :text="getTitle"
                    type="h5"
                />
              </v-card-title>
            </v-col>
            <v-col offset="1">
              <AerisDatavalidationLogoutItem/>
            </v-col>
          </v-row>
          <AerisDatavalidationSessionsTable
              v-if="!isCreationMode"
              :setSessions="setSessions"
              :initNewSession="initNewSession"
              :setCurrentItem="setCurrentItem"
          />
          <AerisDatavalidationSessionForm
              v-if="isCreationMode"
              :sessions="sessions"
              :parameters="parameters"
              :instruments="instruments"
              :currentInstrument="currentInstrument"
              :getInstrument="getInstrument"
              :initNewSession="initNewSession"
              :setCurrentItem="setCurrentItem"
              :createNewSession="createNewSession"
              :getPiInstruments="getPiInstruments"
          />
        </v-card>
      </v-dialog>
    </AerisDataValidationServices>
  </v-row>
</template>
<script>

import AerisDataValidationServices from "../../../../aeris-datavalidation-services/components/aeris-datavalidation-services"
import AerisDatavalidationSessionForm from "../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-form/components/aeris-datavalition-sessionform"
import AerisDatavalidationLogoutItem from "../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-items/components/aeris-datavalidation-logoutitem"
import AerisDatavalidationTypography from "../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-typographies/components/aeris-datavalidation-typography"
import AerisDatavalidationSessionsTable from "../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-tables/components/aeris-datavalidation-sessionstable"
import AerisDatavalidationLangSwitcher from "../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-inputs/submodules/aeris-datavalidation-switchers/components/aeris-datavalidation-langswitcher"

const INSTRUMENT_PATH = "/instruments?id="
const CREATE_SESSION_PATH = "/sessions/create"
const INSTRUMENT_NAMES_PATH = "/instruments/names"
const INSTRUMENT_INFOS_PATH = "/instruments/infos/"

export default {
  name: "aeris-datavalidation-configuration",
  props : {
    newSession : {
      type : Function,
      default: () => {}
    },
    sessionsDialog : {
      type : Boolean
    },
  },
  components : {
    AerisDataValidationServices,
    AerisDatavalidationTypography,
    AerisDatavalidationLogoutItem,
    AerisDatavalidationSessionForm,
    AerisDatavalidationLangSwitcher,
    AerisDatavalidationSessionsTable
  },
  watch : {
    sessionsDialog : function() {
      if(this.sessionsDialog)
        this.dialog = true
    },
  },
  data() {
    return {
      dialog: true,
      sessions : [],
      callBack : null,
      parameters : [],
      currentUrl : "",
      instruments : [],
      requestData : {},
      typeOfRequest : "",
      currentInstrument : null,
      currentItem : 'configuration.label_continueSession',
    }
  },
  computed : {
    getTitle : function() {
      return this.$t(this.currentItem)
    },
    isCreationMode : function () {
      return this.currentItem === 'configuration.label_newSession'
    }
  },
  methods : {
    setCurrentItem : function(item) {
      this.currentItem = item
    },
    setSessions : function(sessions) {
      if(sessions) {
        this.sessions = sessions
      }
    },
    getInstrument : function (instrumentIdObj) {
      let instrumentId = instrumentIdObj['_id']['$oid']
      this.typeOfRequest = "GET"
      this.callBack = (instrument) => {
        if(instrument) {
          this.currentInstrument = instrument
          this.parameters = this.currentInstrument.parameters
        }
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + INSTRUMENT_PATH + instrumentId
    },
    getPiInstruments : function() {
      let newInstruments = []
      this.typeOfRequest = "GET"
      this.callBack = (data) => {
        if(data) {
          data.forEach((instrumentObj) => {
            newInstruments.push(JSON.parse(instrumentObj))
          })
          this.instruments = newInstruments
        }
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + INSTRUMENT_NAMES_PATH
    },
    createNewSession : function (newSession) {
      this.requestData = newSession
      this.typeOfRequest = "POST"
      this.callBack = (session) => {
        this.currentUrl=""
        if(session) {
          this.startNewSession(session)
        }
        this.currentUrl = process.env.VUE_APP_ROOT_API + CREATE_SESSION_PATH
      }
    },
    startNewSession : function(currentSession) {
      this.getInstrumentInfos(this.currentInstrument['uuid'], (infos) => {
        console.log("Test start newSession : ", infos, currentSession)
        //this.initNewSession(session, this.currentInstrument, infos)
      })
    },
    getInstrumentInfos : function (uuid, callBack) {
      this.typeOfRequest = "GET"
      this.callBack = callBack
      this.currentUrl = process.env.VUE_APP_ROOT_API + INSTRUMENT_INFOS_PATH + uuid
    },
    initNewSession : function (currentSession, currentInstrument, infos) {
      if(currentSession && currentInstrument) {
        this.dialog = false
        this.newSession(currentSession, currentInstrument, infos)
      }
    }
  }
}
</script>

<style scoped>

</style>