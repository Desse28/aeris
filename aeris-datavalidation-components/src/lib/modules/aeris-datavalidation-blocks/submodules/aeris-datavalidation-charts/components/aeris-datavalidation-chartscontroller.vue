<template>
  <div>
    <AerisDatavalidationConfiguration
        :sessionsDialog="sessionsDialog"
        :newSession="newSession"
    />
    <AerisDatavalidationToolbar
        :charts="charts"
        :selection="selection"
        :session="currentSession"
        :qualityFlags="qualityFlags"
        :currentParalelChart="currentParalelChart"
        :secondChartsParameters="secondChartsParameters"
        :addNewChart="addNewChart"
        :removeChart="removeChart"
        :addParameter="addParameter"
        :removeParameter="removeParameter"
        :notifySelection=" notifySelection"
        :switchParameterChart="switchParameterChart"
        :notifyDeleteSelection="notifyDeleteSelection"
    />
    <div>
      <AerisDatavalidationLandScapeLayaout
          padding="pa-0"
          justify="center"
          key="mainLandScape"
          :nbrChildElement="2"
          :cols="secondChartsOnParameters.length !== 0 ? [7, 5] : [12, 0]"
      >
        <template v-slot:land1>
          <AerisDatavalidationMainChartTab
              :charts="charts"
              :isMainChart="true"
              :selection="selection"
              :deleteStep="deleteStep"
              :currentSession="currentSession"
              :linkedChartData="linkedChartData"
              :instrumentInfos="instrumentInfos"
              :isLinkedChartMode="isLinkedChartMode"
              :currentInstrument="currentInstrument"
              :secondChartsParameters="secondChartsOnParameters"
              :notifySelection="notifySelection"
              :switchLinkedMode="switchLinkedMode"
              :applyLinkedEffect="applyLinkedEffect"
              :notifyDeleteSelection="notifyDeleteSelection"
          />
        </template>
        <template v-slot:land2>
          <AerisDatavalidationChartsTab
              :charts="charts"
              :isMainChart="false"
              :currentSession="currentSession"
              :instrumentInfos="instrumentInfos"
              :linkedChartData="linkedChartData"
              :isLinkedChartMode="isLinkedChartMode"
              :currentInstrument="currentInstrument"
              :hideChart="secondChartsOnParameters.length === 0"
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
      charts : {},
      deleteStep: 1,
      selection: null,
      qualityFlags: [],
      sessionsDialog: false,
      currentSession: null,
      instrumentInfos: null,
      currentInstrument: null,
      isLinkedChartMode: false,
      currentParalelChart: "",
      secondChartsParameters : [],
      linkedChartData: {startXaxis: null, endXaxis: null},
    }
  },
  computed : {
    secondChartsOnParameters : function() {
      let parameters = []
      if(this.charts) {
        Object.keys(this.charts).forEach((chartName)=> {
          if(chartName !== 'Main chart')
            parameters = [...parameters, ...this.charts[chartName].parameters]
        })
      }
      return parameters
    }
  },
  methods : {
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
      if(currentSession && currentInstrument && instrumentInfos ) {
        this.instrumentInfos = instrumentInfos
        this.initCharts(currentSession, currentInstrument)
        this.currentSession = currentSession
        this.currentInstrument = currentInstrument
        this.qualityFlags = currentInstrument.flags
      }
    },
    initCharts : function({charts, linkedParameters, linkedChartMode}, {auxParameters}) {
      let newCharts = {}
      if(charts && linkedParameters && auxParameters) {
        charts.forEach((chart) => {
          newCharts[chart.enName] = chart
        })
        this.charts = newCharts

        this.isLinkedChartMode = linkedChartMode
        this.secondChartsParameters = [...auxParameters, ...linkedParameters]
      }
    },
    addParameter : function(newParameter, targetChartName) {
      let targetChart = this.charts[targetChartName]

      if(newParameter && targetChartName) {
        if(!targetChart.parameters.includes(newParameter))
          targetChart.parameters = [...targetChart.parameters, newParameter]
      }
    },
    removeParameter : function(targetParameter, targetChartName) {
      let targetChart = this.charts[targetChartName]

      if(targetParameter && targetChartName)
        targetChart.parameters = targetChart.parameters.filter(function(parameter) {
          return parameter.name !== targetParameter.name
        })
    },
    switchParameterChart: function (targetParameter, newChartName, oldChartName) {
      let oldChart = this.charts[oldChartName]
      let newChart = this.charts[newChartName]

      if (targetParameter && oldChart && newChart) {

        oldChart.parameters = oldChart.parameters.filter(function (parameter) {
          return parameter.name !== targetParameter.name
        })

        if(!newChart.parameters.includes(targetParameter))
          newChart.parameters = [...newChart.parameters, targetParameter]
      }
    },
    switchLinkedMode: function () {
      this.isLinkedChartMode = !this.isLinkedChartMode
    },
    applyLinkedEffect: function(data) {
      if(this.isLinkedChartMode && data && data['xaxis.range[0]'] && data['xaxis.range[1]']) {
        this.linkedChartData = {
          startXaxis: data['xaxis.range[0]'],
          endXaxis: data['xaxis.range[1]'],
          points : this.linkedChartData.points
        }
      } else if(this.linkedChartData && data.points) {
        this.linkedChartData = {
          startXaxis : this.linkedChartData.startXaxis,
          endXaxis :  this.linkedChartData.endXaxis,
          hoverData : data
        }
      } else {
        this.linkedChartData = {type : data}
      }
    },
    addNewChart: function(newChart) {
      let cloneCharts = JSON.parse(JSON.stringify(this.charts))
      cloneCharts[newChart.enName] = newChart
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