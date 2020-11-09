<template>
  <div class="text-center ml-4">
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
    <v-menu
        v-for="([text], index) in getBtns"
        :key="text"
        :close-on-content-click="closeMenu"
        :nudge-width="text ===  $t('session.label_addChart') ? 80 : 200"
        :offset-y="text ===  $t('session.label_addChart') && offset"
        :offset-x="text === $t('session.label_addParameters')&& offset"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            class="mb-2 mt-2 blue--text"
            color="rgb(255, 255, 255)"
            v-if="text !== $t('session.label_removeChart')"
            depressed
            v-bind="attrs"
            v-on="on"
        >
          <v-icon left>{{getIcons[index]}}</v-icon> {{ text }}
        </v-btn>
        <v-btn class="mb-2 mt-2 blue--text"
               color="rgb(255, 255, 255)"
               depressed
               v-else
               v-on:click="deleteCurrentParalelChart"
        >
          <v-icon left>{{getIcons[index]}}</v-icon> {{ text }}
        </v-btn>
      </template>
      <v-card>
        <div v-if="text === $t('session.label_addParameters')">
          <v-row justify="center">
            <v-col cols="6">
              <v-list>
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title>{{ $t("session.label_parameter") }}</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
              <v-divider></v-divider>
              <v-list>
                <v-list-item v-for="label in parametersLabel" :key="label.name" class="ma-3">
                  <v-list-item-action>
                    <v-checkbox
                        v-model="parameters"
                        :label="label.name"
                        :color="label.color"
                        :value="label"
                        hide-details
                    ></v-checkbox>

                  </v-list-item-action>
                </v-list-item>
              </v-list>
            </v-col>
            <v-col cols="6">
              <v-list>
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title>{{$t('session.label_chart')}}</v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
              <v-divider></v-divider>
              <v-list>
                <v-list-item v-for="(label, index) in parametersLabel"
                             :key="label.name"
                >
                  <v-list-item-action>
                    <AerisDatavalidationChartsSelect
                        :selectIndex="index"
                        :charts="getCharts"
                        :defaultChart="getTargetChart(chartsSelects[index])"
                        :currentParameter="parametersLabel[index]"
                        :notifySwitchChart="notifySwitchChart"
                    />
                  </v-list-item-action>
                </v-list-item>
              </v-list>
            </v-col>
          </v-row>
        </div>
        <div v-if="text ===  $t('session.label_addChart')" class="pa-4">
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
      </v-card>
    </v-menu>
  </div>
</template>
<script>
import {colors} from "../../../../aeris-datavalidation-common/colors/index"
import AerisDatavalidationChartsSelect from "../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-selects/components/aeris-datavalidation-chartsselect"
import AerisDatavalidationDeleteDialog from "../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-dialogs/components/aeris-datavalidation-deletedialog"

export default {
  name: "aeris-datavalidation-options",
  components : {
    AerisDatavalidationChartsSelect,
    AerisDatavalidationDeleteDialog
  },
  props: {
    secondChartsParameters : {
      type : Array
    },
    addNewChart: {
      type: Function,
      default: () => {}
    },
    removeParameter : {
      type : Function
    },
    addParameter : {
      type : Function
    },
    removeChart: {
      type : Function,
    },
    charts : {
      type : Object,
    },
    switchParameterChart : {
      type : Function,
      default : () => {}
    },
    currentParalelChart : {
      type : String,
      default : () => ""
    }
  },
  computed : {
    getCharts : function () {
      return Object.values(this.charts)
    },
    getIcons : function () {
      return ['mdi-chevron-down', 'mdi-tab-plus', 'mdi-tab-remove']
    },
    getBtns : function() {
      return [
        [this.$t("session.label_addParameters")],
        [this.$t("session.label_addChart")],
        [this.$t("session.label_removeChart")],
      ]
    }
  },
  data() {
    return {
      tabName: "",
      offset: true,
      tabNames : [],
      parameters: [],
      closeMenu : false,
      tabAlertMess : "",
      hideOkButton:false,
      chartsSelects : [],
      isEmptyName: false,
      deleteDialog: false,
      parametersLabel : [],
      parametersColors : [],
      deleteTabMessage : "",
      cancelDeleteLabel: this.$t('session.label_no'),
    }
  },
  watch: {
    parameters : function (newParameters, oldParameters) {
      if(oldParameters.length < newParameters.length) {
        this.flushAddParameter(newParameters)
      } else if (newParameters.length < oldParameters.length) {
        this.flushRemoveParameter(newParameters, oldParameters)
      }
    },
    secondChartsParameters : function() {
      if(this.charts) {
        this.addActiveParameters()
        this.addOffParameters()
      }
    }
  },
  methods : {
    getTargetChart : function (chartName) {
      return this.charts[chartName]
    },
    addActiveParameters : function() {
      let parameters
      const mainChart = Object.values(this.charts)[0]

      this.registreParametersColors(mainChart.parameters)
      Object.keys(this.charts).forEach((chartName) => {
        parameters = this.getParameters(chartName)
        this.registreParametersColors(parameters)
        this.parameters = [...this.parameters, ...parameters]
        this.parametersLabel = [...this.parametersLabel, ...parameters]
        this.chartsSelects = [...this.chartsSelects, ...parameters.slice().fill(chartName)]
      })
    },
    getParameters : function (chartName) {
      const mainChart = Object.values(this.charts)[0]
      let parameters = this.charts[chartName].parameters
      if(chartName === mainChart.enName) {
        parameters = parameters.filter((_, index)=> {
          return index !== 0
        })
      }
      return parameters
    },
    registreParametersColors : function(parameters) {
      parameters.forEach(({color}) => {
        this.parametersColors.push(color)
      })
    },
    addOffParameters : function() {
      let color
      const chartName = Object.values(this.charts)[1].enName
      this.secondChartsParameters.forEach((targetParameter) => {
        if(this.parametersLabel.find(parameter => parameter.name === targetParameter.name) === undefined) {
          color = this.getNewColor()
          targetParameter.color = color
          this.parametersColors.push(color)
          this.chartsSelects.unshift(chartName)
          this.parametersLabel = [targetParameter, ...this.parametersLabel]
        }
      })
    },
    getNewColor : function() {
      return colors.find(color => !this.parametersColors.includes(color))
    },
    flushAddParameter : function (newParameters) {
      let parameter = newParameters[newParameters.length - 1]
      let targetChartName = this.chartsSelects[this.parametersLabel.indexOf(parameter)]
      this.addParameter(parameter, targetChartName)
    },
    flushRemoveParameter : function (newParameters, oldParameters) {
      let intersection = oldParameters.filter(param => {
        return !newParameters.some(newParam => newParam.name === param.name)
      })
      let targetChartName = this.chartsSelects[this.parametersLabel.indexOf(intersection[0])]
      this.removeParameter(intersection[0], targetChartName)
    },
    notifySwitchChart : function(targetParameter, newChartName, oldChartName, selectIndex) {
      this.chartsSelects.splice(selectIndex, 1, newChartName)
      if(this.parameters.includes(targetParameter)) {
        this.switchParameterChart(targetParameter, newChartName, oldChartName)
      }
    },
    addChart : function () {
     if(this.tabName === "") {
       this.turnTabAlert(true)
     } else if(this.tabNames.includes(this.tabName.toUpperCase())) {
       this.turnTabAlert(false)
     } else {
       this.addNewChart(this.tabName)
       this.closeMenu = true
       this.tabNames.push(this.tabName.toUpperCase())
       setTimeout(() => {
         this.closeMenu = false
         this.tabName = ""
       }, 100)
     }
    },
    turnTabAlert : function (isEmptyName) {
      this.tabAlertMess = isEmptyName ? this.$t("session.label_emptyName") : this.$t("session.label_chartExist")
      this.isEmptyName = true
      setTimeout(() => {
        this.isEmptyName = false
      }, 1000)
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
  },

}
</script>

<style scoped>
</style>