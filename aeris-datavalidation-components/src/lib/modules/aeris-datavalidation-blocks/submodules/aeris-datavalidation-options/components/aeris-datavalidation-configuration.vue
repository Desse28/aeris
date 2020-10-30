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
              :page="page"
              :sessions="sessions"
              :pageCount="pageCount"
              :itemsPerPage="itemsPerPage"
              :initNewSession="initNewSession"
              :setCurrentView="setCurrentView"
              :continueSession="continueSession"
              :switchTablecurrentPage="switchTablecurrentPage"
          />
          <AerisDatavalidationSessionForm
              v-if="isCreationMode"
              :sessions="sessions"
              :parameters="parameters"
              :instruments="instruments"
              :currentInstrument="currentInstrument"
              :getInstrument="getInstrument"
              :initNewSession="initNewSession"
              :setCurrentView="setCurrentView"
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
const SESSIONS_PAGE_PATH = "/sessions?page="
const CREATE_SESSION_PATH = "/sessions/create"
const INSTRUMENT_NAME_PATH = "/instruments?name="
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
      page: 1,
      pageCount: 0,
      dialog: true,
      sessions : [],
      itemsPerPage: 5,
      callBack : null,
      parameters : [],
      currentUrl : "",
      instruments : [],
      requestData : {},
      typeOfRequest : "",
      currentInstrument : null,
      currentView : 'configuration.label_continueSession',
    }
  },
  computed : {
    getTitle : function() {
      return this.$t(this.currentView)
    },
    isCreationMode : function () {
      return this.currentView === 'configuration.label_newSession'
    }
  },
  methods : {
    setCurrentView : function(view) {
      this.currentView = view
    },
    getInstrument : function (instrumentIdObj) {
      let instrumentId = instrumentIdObj['_id']['$oid']
      this.typeOfRequest = "GET"
      this.callBack = (instrument) => {
        this.currentUrl=""
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
          this.currentUrl=""
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
          this.startNewSession(session, this.currentInstrument)
        }
        this.currentUrl = process.env.VUE_APP_ROOT_API + CREATE_SESSION_PATH
      }
    },
    continueSession : function(session) {
      let {instrumentName} = session

      this.typeOfRequest = "GET"
      this.callBack = (instrument) => {
        if(instrument) {
          this.currentUrl = ""
          this.startNewSession(session, instrument)
        }
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + INSTRUMENT_NAME_PATH + instrumentName
    },
    startNewSession : function(currentSession, instrument) {
      this.getInstrumentInfos(instrument, (infos) => {
        console.log("Test start newSession : ", infos, currentSession)
        //this.dialog = false
        //this.newSession(currentSession, instrument, infos)
      })
    },
    getInstrumentInfos : function ({uuid}, callBack) {
      this.typeOfRequest = "GET"
      this.callBack = callBack
      this.currentUrl = process.env.VUE_APP_ROOT_API + INSTRUMENT_INFOS_PATH + uuid
    },
    switchTablecurrentPage : function() {
      this.typeOfRequest = "GET"
      this.callBack = ((currentPageData) => {
        if(currentPageData) {
          this.page = currentPageData.number
          this.sessions = currentPageData.content
          this.pageCount = currentPageData.totalPages
        }
      })
      this.currentUrl = process.env.VUE_APP_ROOT_API + SESSIONS_PAGE_PATH + this.page + "&size=" + this.itemsPerPage
    },
  }
}
</script>

<style scoped>

</style>