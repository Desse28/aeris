<template>
    <div
        v-if="currentView === $t('session.label_addChart')"
        class="pa-4"
    >
      <AerisDataValidationServices
          :url="currentUrl"
          :callBack="callBack"
          :requestData="requestData"
          :typeOfRequest="typeOfRequest"
      >
      <v-alert
          dense
          outlined
          type="error"
          v-if="isEmptyName"
      >
        {{tabAlertMess}}
      </v-alert>
      <v-text-field
          v-model="tabName"
          :label="$t('session.label_chartName')"
          hide-details="auto"
      ></v-text-field>
      <v-btn class="mb-2 mt-2"
             color="blue"
             depressed
             v-on:click="addChart"
      >
        {{$t('session.label_add')}}
      </v-btn>
      </AerisDataValidationServices>
    </div>
    <v-btn class="mb-2 mt-2 blue--text"
           color="rgb(255, 255, 255)"
           depressed
           v-else-if="currentView === $t('session.label_removeChart')"
           v-on:click="deleteCurrentParalelChart"
    >
      <v-icon left>{{removeIcon}}</v-icon>{{  currentView }}
      <AerisDatavalidationDeleteDialog
          :dialog="deleteDialog"
          :hideOkButton="hideOkButton"
          :ok="$t('session.label_yes')"
          :cancel="cancelDeleteLabel"
          :okCallBack="validateDelete"
          :cancelCallBack="cancelDelete"
          :title="$t('session.label_deletion')"
          :message="deleteTabMessage"
      />
      <AerisDataValidationServices
          :url="currentUrl"
          :callBack="callBack"
          :requestData="requestData"
          :typeOfRequest="typeOfRequest"
      >
      </AerisDataValidationServices>
    </v-btn>
</template>

<script>
import AerisDataValidationServices from "./../../../../aeris-datavalidation-services/components/aeris-datavalidation-services"
import AerisDatavalidationDeleteDialog from "./../../aeris-datavalidation-dialogs/components/aeris-datavalidation-deletedialog"

const SESSION_UPDATE_PATH = "/sessions/update"

export default {
  name: "aeris-datavalidation-tabhandler",
  components : {
    AerisDataValidationServices,
    AerisDatavalidationDeleteDialog
  },
  props : {
    session: {
      type: Object,
      default: null
    },
    charts : {
      type : Object,
    },
    currentView : {
      type : String,
      default : () => ""
    },
    removeIcon : {
      type : String,
      default : () => ""
    },
    addNewChart : {
      type: Function,
      default: () => {}
    },
  },
  data() {
    return {
      tabName: "",
      tabNames : [],
      currentUrl : "",
      requestData : {},
      callBack : null,
      tabAlertMess : "",
      typeOfRequest : "",
      hideOkButton:false,
      deleteDialog: false,
      isEmptyName : false,
      deleteTabMessage : "",
      cancelDeleteLabel: this.$t('session.label_no'),
    }
  },
  computed : {
    genNewTabName : function () {
      let nameNumPart, lastName
      let tabsNames = Object.keys(this.charts)
      const defaultName = this.$t('session.label_secondaryChart')

      tabsNames = tabsNames.filter((tabName) => {
        return tabName.toUpperCase().includes(this.$t(defaultName).toUpperCase())
      })

      lastName = tabsNames[tabsNames.length-1]
      lastName = lastName ? lastName.toUpperCase().replace(defaultName.toUpperCase(), "") : ""
      nameNumPart = lastName !== "" ? Number(lastName) : 1

      return defaultName + (nameNumPart + 1)
    },
  },
  mounted() {
    this.tabName = this.genNewTabName
  },
  methods : {
    addChart : function () {
      const tabsNames = Object.keys(this.charts)
      if(this.tabName === "") {
        this.alertTab(true)
      } else if(tabsNames.includes(this.tabName.toUpperCase())) {
        this.alertTab(false)
      } else {
        this.creatNewChart()
      }
    },
    alertTab : function (isEmptyName) {
      this.tabAlertMess = isEmptyName ? this.$t("session.label_emptyName") : this.$t("session.label_chartExist")
      this.isEmptyName = true
      setTimeout(() => {
        this.isEmptyName = false
      }, 1000)
    },
    creatNewChart : function () {
      let newChart = this.getNewChart()
      this.session.charts.push(newChart)
      this.requestData = this.session
      this.typeOfRequest = "PUT"

      this.callBack = (data) => {
        this.currentUrl=""
        if(data) {
          this.addNewChart(newChart)
        }
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + SESSION_UPDATE_PATH
    },
    getNewChart : function() {
      const defaultDate = "1970-01-01T00:00:00.000+00:00"
      return {
        endXaxis : defaultDate,
        startXaxis : defaultDate,
        selections : [],
        frName : this.tabName,
        enName :  this.tabName,
        parameters : []
      }
    },
    deleteCurrentParalelChart : function() {
      if(this.currentParalelChart.toUpperCase() === "CHART2") {
        this.hideOkButton = true
        this.cancelDeleteLabel = this.$t('session.label_close')
        this.deleteTabMessage = this.$t("session.label_nonDeletableChart")
      } else {
        this.deleteTabMessage = this.$t("session.message_deleteChart")
      }
      this.deleteDialog = true
    },
    validateDelete : function () {
      let intersection
      let targetParameters = this.charts[this.currentParalelChart].parameters
      this.removeChart()
      this.deleteDialog = false
      if(targetParameters) {
        intersection = this.parameters.filter(param => {
          return !targetParameters.some(newParam => newParam.name === param.name)
        })
        if(intersection)
          this.parameters = intersection
      }

      this.tabNames =  this.tabNames.filter(tabName => tabName.toUpperCase() !== this.currentParalelChart.toUpperCase())
    },
    cancelDelete : function () {
      this.deleteDialog = false
      this.hideOkButton = false
      this.cancelDeleteLabel = this.$t('session.label_no')
    },
  }
}
</script>

<style scoped>

</style>