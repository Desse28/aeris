<template>
  <div>
    <AerisDatavalidationConfiguration
        :sessionsDialog="sessionsDialog"
        :newSession="newSession"
        :setSessionsDialog="setSessionsDialog"
    />

    <AerisDatavalidationToolbar
        :charts="charts"
        :selection="selection"
        :session="currentSession"
        :qualityFlags="qualityFlags"
        :auxParameters="auxParameters"
        :instrumentEndDate="endDate"
        :instrumentStartDate="startDate"
        :linkedParameters ="linkedParameters"
        :currentParalelChart="currentParalelChart"
        :addNewChart="addNewChart"
        :removeChart="removeChart"
        :addParameter="addParameter"
        :removeParameter="removeParameter"
        :notifySelection=" notifySelection"
        :setSessionsDialog="setSessionsDialog"
        :switchParameterChart="switchParameterChart"
        :notifyDeleteSelection="notifyDeleteSelection"
    />
    <div>
      <AerisDatavalidationLandScapeLayaout
          key="mainLandScape"
          justify="center"
          padding="pa-0"
          :cols="getChartsCol"
          :nbrChildElement="2"
      >
        <template v-slot:land1>
          <AerisDatavalidationMainChartTab
              :endDate="endDate"
              :isMainChart="true"
              :selection="selection"
              :startDate="startDate"
              :deleteStep="deleteStep"
              :defaultSelections="selections"
              :currentSession="currentSession"
              :linkedChartData="linkedChartData"
              :instrumentInfos="instrumentInfos"
              :currentInstrument="currentInstrument"
              :isParallelChartsEmpty="getChartsCol[1] === 0"
              :parameters="charts[charts.Chart1.name].parameters"
              :notifySelection="notifySelection"
              :switchLinkedMode="switchLinkedMode"
              :applyLinkedEffect="applyLinkedEffect"
              :notifyDeleteSelection="notifyDeleteSelection"
          />
        </template>
        <template v-slot:land2>
          <AerisDatavalidationChartsTab
              :hideChart="getChartsCol[1] === 0"
              :endDate="endDate"
              :isMainChart="false"
              :startDate="startDate"
              :charts="charts"
              :currentSession="currentSession"
              :instrumentInfos="instrumentInfos"
              :linkedChartData="linkedChartData"
              :isLinkedChartMode="isLinkedChartMode"
              :currentInstrument="currentInstrument"
              :secondChartParameters="secondChartParameters"
              :setCurrentParralelChart="setCurrentParralelChart"
          />
        </template>
      </AerisDatavalidationLandScapeLayaout>
    </div>
  </div>
</template>
<script>
import {
  AerisDatavalidationChartsTab,
  AerisDatavalidationMainChartTab,
  AerisDatavalidationConfiguration,
  AerisDatavalidationToolbar,
  AerisDatavalidationLandScapeLayaout
} from "./../../../../aeris-datavalidation-components"

import {colors, defaultColor} from "./../../../../aeris-datavalidation-common/colors"

export default {
  name: "aeris-datavalidation-homepage",
  components: {
    AerisDatavalidationToolbar,
    AerisDatavalidationChartsTab,
    AerisDatavalidationMainChartTab,
    AerisDatavalidationConfiguration,
    AerisDatavalidationLandScapeLayaout,
  },
  data() {
    return {
      endDate: "",
      startDate: "",
      colorCount: 0,
      deleteStep: 1,
      selections: [],
      selection: null,
      qualityFlags: [],
      auxParameters: [],
      sessionsDialog: false,
      linkedParameters: [],
      currentSession: null,
      instrumentInfos: null,
      currentInstrument: null,
      isLinkedChartMode: false,
      currentParalelChart: "",
      secondChartParameters: [],
      isSecondChartEmpty: false,
      parallelChartsParameters : [],
      linkedChartData: {startXaxis: null, endXaxis: null},
      charts : {Chart1 : {name : "Chart1", parameters : []}, Chart2 : {name : "Chart2", parameters : []}},
    }
  },
  computed : {
    getChartsCol : function() {
      if(this.parallelChartsParameters.length !== 0) {
        return [7, 5]
      } else {
        return [12, 0]
      }
    }
  },
  methods : {
    setSessionsDialog : function (state) {
      this.sessionsDialog = false
      if(state) {
        //Object.assign(this.$data, this.$options.data())
        //this.sessionsDialog = state
        console.log("Reset all")
      }
    },
    notifySelection : function(startDate, endDate) {
      this.selection = {startDate: startDate, endDate: endDate}
    },
    notifyDeleteSelection : function () {
      if(this.deleteStep%2 === 0)
        this.deleteStep = 1
      else
        this.deleteStep = 2
    },
    newSession : function(currentSession, currentInstrument, instrumentInfos) {
      let mainParameter
      if(currentSession && currentInstrument) {
        this.instrumentInfos = instrumentInfos
        this.currentSession = currentSession
        this.currentInstrument = currentInstrument
        this.qualityFlags = currentInstrument.flags
        this.startDate = this.currentSession.startDate
        this.endDate = this.currentSession.endDate
        this.initParameters()
        this.selections = currentSession.sessionSelections
        mainParameter = this.currentSession['mainParameter']
        mainParameter.color = this.getNewColor()
        this.charts[this.charts.Chart1.name].parameters = [mainParameter]
      }
    },
    initParameters : function() {
      let auxParameters = this.currentInstrument['auxParameters']
      let linkedParameters = this.currentSession.linkedParameters

      if(auxParameters) {
        auxParameters.forEach((parameter) => {
          if(parameter !== "") {
            parameter.isOn = true
            parameter.color = this.getNewColor()
            parameter.chartName = this.charts.Chart2.name
          }
        })
        this.auxParameters = auxParameters
        this.parallelChartsParameters = [...auxParameters]
        this.charts.Chart2.parameters = this.auxParameters
      }

      if(linkedParameters) {
        linkedParameters.forEach((parameter) => {
          parameter.isOn = false
          parameter.color = this.getNewColor()
        })
        this.linkedParameters = linkedParameters
      }
    },
    getNewColor: function() {
      if(this.colorCount < colors.length -1) {
        this.colorCount = this.colorCount + 1
        return colors[this.colorCount]
      } else {
        return defaultColor
      }
    },
    addParameter : function(targetParameter) {
      //console.log("Test add : ", targetParameter)
      let targetChart = this.charts[targetParameter.chartName]

      if(targetParameter) {
        if(!targetChart.parameters.includes(targetParameter))
          this.charts[targetParameter.chartName].parameters = [...targetChart.parameters, targetParameter]

        if(targetParameter.chartName !== "Chart1")
          this.parallelChartsParameters = [...this.parallelChartsParameters, targetParameter]
      }
    },
    removeParameter : function(deletedElement) {
      let targetChart = this.charts[deletedElement.chartName]

      if(deletedElement && targetChart) {
        this.charts[deletedElement.chartName].parameters = targetChart.parameters.filter(function(param) {
          return param.name !== deletedElement.name
        })
      }

      if(deletedElement.chartName !== "Chart1")
        this.parallelChartsParameters = this.parallelChartsParameters.filter(function(param) {
          return param.name !== deletedElement.name
        })
    },
    switchParameterChart: function (targetParameter) {
      let oldChart, newChart

      if (targetParameter) {
        oldChart = this.charts[targetParameter.oldChartName]
        newChart = this.charts[targetParameter.chartName]

        oldChart.parameters = oldChart.parameters.filter(function (param) {
          return param.name !== targetParameter.name
        })

        if(!newChart.parameters.includes(targetParameter))
          newChart.parameters = [...newChart.parameters, targetParameter]

        if(targetParameter.oldChartName !== "Chart1")
          this.parallelChartsParameters = this.parallelChartsParameters.filter(function(param) {
            return param.name !== targetParameter.name
          })

        if(targetParameter.chartName !== "Chart1")
          this.parallelChartsParameters = [...this.parallelChartsParameters, targetParameter]
      }
    },
    switchLinkedMode: function () {
      if(this.isLinkedChartMode)
        this.isLinkedChartMode = false
      else
        this.isLinkedChartMode = true
    },
    applyLinkedEffect: function(data) {
      if(this.isLinkedChartMode && data && data['xaxis.range[0]'] && data['xaxis.range[1]']) {
        this.linkedChartData = {
          startXaxis: data['xaxis.range[0]'],
          endXaxis: data['xaxis.range[1]']
        }
      }
    },
    addNewChart: function(chartName) {
      let cloneCharts = JSON.parse(JSON.stringify(this.charts))
      cloneCharts[chartName] = {name: chartName, parameters : []}
      this.charts = cloneCharts
    },
    setCurrentParralelChart : function (chartName) {
      this.currentParalelChart = chartName
    },
    removeChart : function() {
      let charts = Object.values(this.charts)
      const currentChart = this.currentParalelChart
      let cloneCharts = JSON.parse(JSON.stringify(this.charts))
      let mainChatName =  charts[0].name
      let defaultChartName =  charts[1].name

      if(currentChart !== "" &&
          currentChart !== mainChatName &&
          currentChart !== defaultChartName) {
        delete(cloneCharts[currentChart])
        this.charts = cloneCharts
        this.currentParalelChart = ""
      }
    },
  },
}
</script>
<style scoped>
</style>