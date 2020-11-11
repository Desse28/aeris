<template>
  <div>
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
        :requestData="requestData"
        :typeOfRequest="typeOfRequest"
    >
      <div
          v-if="currentView === $t('session.label_addChart')"
          class="pa-4"
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
      </div>
      <AerisDatavalidationDeleteDialog
          :dialog="deleteDialog"
          :hideOkButton="false"
          :ok="$t('session.label_yes')"
          :okCallBack="validateDelete"
          :cancelCallBack="cancelDelete"
          :cancel="$t('session.label_no')"
          :title="$t('session.label_deletion')"
          :message="$t('session.message_deleteChart')"
      />
    </AerisDataValidationServices>
  </div>
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
    menuModelState : {
      type : Boolean,
      default : () => null
    },
    chartTabsHandler : {
      type: Function,
      default: () => {}
    },
    currentSecondChart : {
      type : String,
      default : () => ""
    },
    isDeleteChartModeState : {
      type : Boolean,
      default : () => false
    },
    switchDeleteChartModeState : {
      type : Function,
      default : () => {}
    }
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
      deleteDialog: false,
      isEmptyName : false,
    }
  },
  watch : {
    charts : function () {
      this.tabName = this.genNewTabName
    },
    menuModelState : function () {
      if(this.menuModelState === false)
        this.tabName = this.genNewTabName
    },
    isDeleteChartModeState : function () {
      this.deleteDialog = this.isDeleteChartModeState;
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
      } else if(tabsNames.map(name => name.toUpperCase()).includes(this.tabName.toUpperCase())) {
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
          this.chartTabsHandler(newChart, 'add')
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
    validateDelete : function () {
      this.session.charts = this.session.charts.filter((chart) => {
        return(chart.enName.toUpperCase() !== this.currentSecondChart.toUpperCase())
      })
      this.requestData = this.session
      this.typeOfRequest = "PUT"

      this.callBack = (data) => {
        this.currentUrl=""

        if(data) {
          this.chartTabsHandler(this.currentSecondChart, 'remove')
          this.switchDeleteChartModeState()
        }
      }
      this.currentUrl = process.env.VUE_APP_ROOT_API + SESSION_UPDATE_PATH
    },
    cancelDelete : function () {
      this.switchDeleteChartModeState()
    },
  }
}
</script>

<style scoped>

</style>