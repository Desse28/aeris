<template>
  <div class="text-center ml-4">
    <AerisDatavalidationTabHandler
        :charts="charts"
        :session="session"
        :chartTabsHandler="chartTabsHandler"
        :currentSecondChart="currentSecondChart"
        :isDeleteChartModeState="isDeleteChartModeState"
        :switchDeleteChartModeState="switchDeleteChartModeState"
    />
    <v-menu
        v-for="([text], index) in getBtns"
        :key="text"
        :close-on-content-click="closeMenu"
        :nudge-width="text ===  $t('session.label_addChart') ? 80 : 200"
        :offset-y="text ===  $t('session.label_addChart') && offset"
        :offset-x="text === $t('session.label_displayManagement')&& offset"
        v-model="menuModel[index].active"
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
               :disabled="isSecondChart"
               v-if="text === $t('session.label_removeChart')"
               v-on:click="switchDeleteChartModeState"
        >
          <v-icon left>{{getIcons[index]}}</v-icon>{{ text }}
        </v-btn>
      </template>
      <v-card>
        <div v-if="text === $t('session.label_displayManagement')">
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
                        :currentSecondChart="currentSecondChart"
                        :defaultChart="getTargetChart(chartsSelects[index])"
                        :currentParameter="parametersLabel[index]"
                        :notifySwitchChart="notifySwitchChart"
                        :disableParameter="disableParameter"
                    />
                  </v-list-item-action>
                </v-list-item>
              </v-list>
            </v-col>
          </v-row>
        </div>
        <AerisDatavalidationTabHandler
            v-if="text === $t('session.label_addChart')"
            :charts="charts"
            :session="session"
            :currentView="text"
            :chartTabsHandler="chartTabsHandler"
            :menuModelState="menuModel[1].active"
            :currentSecondChart="currentSecondChart"
        />
      </v-card>
    </v-menu>
  </div>
</template>
<script>
import {colors} from "../../../../aeris-datavalidation-common/colors/index"
import AerisDatavalidationTabHandler from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-tabs/components/aeris-datavalidation-tabhandler"
import AerisDatavalidationChartsSelect from "../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-selects/components/aeris-datavalidation-chartsselect"
export default {
  name: "aeris-datavalidation-options",
  components : {
    AerisDatavalidationTabHandler,
    AerisDatavalidationChartsSelect,
  },
  props: {
    session : {
      type: Object,
      default: null
    },
    currentSecondChart : {
      type : String,
      default : () => ""
    },
    secondChartsParameters : {
      type : Array
    },
    chartTabsHandler : {
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
  },
  computed : {
    isSecondChart : function() {
      let tabsNames = Object.keys(this.charts)
      if(tabsNames && this.currentSecondChart)
        return tabsNames[1].toUpperCase() === this.currentSecondChart.toUpperCase()
      return false
    },
    getCharts : function () {
      return Object.values(this.charts)
    },
    getIcons : function () {
      return ['mdi-chevron-down', 'mdi-tab-plus', 'mdi-tab-remove']
    },
    getBtns : function() {
      return [
        [this.$t("session.label_displayManagement")],
        [this.$t("session.label_addChart")],
        [this.$t("session.label_removeChart")],
      ]
    }
  },
  data() {
    return {
      offset: true,
      menuModel: [
        {active : false},
        {active : false},
        {active : false},
      ],
      parameters: [],
      closeMenu : false,
      chartsSelects : [],
      parametersLabel : [],
      parametersColors : [],
      isDeleteChartModeState : false,
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
    switchDeleteChartModeState : function() {
      this.isDeleteChartModeState = !this.isDeleteChartModeState
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
    disableParameter : function (targetParameter) {
      this.parameters = this.parameters.filter((parameter)=>{
        return parameter.name !== targetParameter.name
      })
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
  },

}
</script>

<style scoped>
</style>