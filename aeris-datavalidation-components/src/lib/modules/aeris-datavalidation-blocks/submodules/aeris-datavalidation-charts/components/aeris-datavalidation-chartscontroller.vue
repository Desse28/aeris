<template>
  <div>
    <AerisDatavalidationConfiguration
        :sessionsDialog="sessionsDialog"
        :newSession="newSession"
        :setSessionsDialog="setSessionsDialog"
    />
    <AerisDatavalidationSimpleToolbar
        :selection="selection"
        :session="currentSession"
        :qualityFlags="qualityFlags"
        :auxParameters="auxParameters"
        :linkedParameters ="linkedParameters"
        :addNewChart="addNewChart"
        :removeChart="removeChart"
        :addNewParallel="addNewParallel"
        :removeParallel="removeParallel"
        :addNewParameter="addNewParameter"
        :removeParameter="removeParameter"
        :notifySelection=" notifySelection"
        :setSessionsDialog="setSessionsDialog"
        :notifyDeleteSelection="notifyDeleteSelection"
    />
    <div :key="testKey">
      <AerisDatavalidationLandScapeLayaout
          key="mainLandScape"
          justify="center"
          padding="pa-0"
          :cols="[getFirsChartCol, getSecondChartCol]"
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
              :parameters="firstChartParameters"
              :instrumentInfos="instrumentInfos"
              :currentInstrument="currentInstrument"
              :isSecondChartEmpty="isSecondChartEmpty"
              :notifySelection="notifySelection"
              :switchLinkedMode="switchLinkedMode"
              :applyLinkedEffect="applyLinkedEffect"
              :notifyDeleteSelection="notifyDeleteSelection"
          />
        </template>
        <template v-slot:land2>
          <AerisDatavalidationChartsTab
              :isSecondChartParametersEmpty="!isSecondChartParametersEmpty"
              :endDate="endDate"
              :isMainChart="false"
              :startDate="startDate"
              :currentSession="currentSession"
              :instrumentInfos="instrumentInfos"
              :linkedChartData="linkedChartData"
              :nbrParallelChart="nbrParallelChart"
              :isLinkedChartMode="isLinkedChartMode"
              :currentInstrument="currentInstrument"
              :secondChartParameters="secondChartParameters"
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
  AerisDatavalidationSimpleToolbar,
  AerisDatavalidationLandScapeLayaout
} from "./../../../../aeris-datavalidation-components"

import {colors, defaultColor} from "./../../../../aeris-datavalidation-common/colors"

export default {
  name: "aeris-datavalidation-homepage",
  components: {
    AerisDatavalidationChartsTab,
    AerisDatavalidationMainChartTab,
    AerisDatavalidationConfiguration,
    AerisDatavalidationSimpleToolbar,
    AerisDatavalidationLandScapeLayaout,
  },
  data() {
    return {
      endDate: "",
      testKey : 0,
      startDate: "",
      colorCount: 0,
      deleteStep: 1,
      selections: [],
      selection: null,
      qualityFlags: [],
      auxParameters: [],
      nbrParallelChart: 2,
      sessionsDialog: false,
      linkedParameters: [],
      currentSession: null,
      instrumentInfos: null,
      currentInstrument: null,
      isLinkedChartMode: false,
      firstChartParameters: [],
      secondChartParameters: [],
      isSecondChartEmpty: false,
      linkedChartData: {startXaxis: null, endXaxis: null},
    }
  },
  computed : {
    isSecondChartParametersEmpty : function() {
      return this.secondChartParameters.length === 0
    },
    getFirsChartCol : function() {
      if( this.secondChartParameters.length === 0)
        return 12
      else
        return 7
    },
    getSecondChartCol : function() {
      if( this.secondChartParameters.length === 0) {
        return 0
      } else {
        return 5
      }
    }
  },
  methods : {
    setSessionsDialog : function (state) {
      this.sessionsDialog = false
      if(state) {
        console.log("Test setSessionsDialog : ", state, ", before : ", this.sessionsDialog)
        //this.sessionsDialog = state
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
      //console.log("Test newSession")
      //this.restCurrentSession()
      if(currentSession && currentInstrument) {
        this.instrumentInfos = instrumentInfos
        this.currentSession = currentSession
        this.currentInstrument = currentInstrument
        this.qualityFlags = currentInstrument.flags
        this.startDate = this.currentSession.startDate
        this.endDate = this.currentSession.endDate
        this.initParametersLabel()
        this.selections = currentSession.sessionSelections
        mainParameter = this.currentSession['mainParameter']
        mainParameter.color = this.getNewColor()
        this.firstChartParameters = [mainParameter]
      }
    },
    restCurrentSession : function() {
      this.testKey++
    },
    initParametersLabel : function() {
      let auxParameters = this.currentInstrument['auxParameters']
      let linkedParameters = this.currentSession.linkedParameters

      if(auxParameters) {
        auxParameters.forEach((parameter) => {
          if(parameter !== "") {
            parameter.color = this.getNewColor()
            this.secondChartParameters.push(parameter)
          }
        })
        this.auxParameters = auxParameters
      }

      if(linkedParameters) {
        linkedParameters.forEach((parameter)=> {
          parameter.color = this.getNewColor()
        })
        this.linkedParameters = linkedParameters
      }
    },
    addNewParameter : function(newParameter) {
      if(newParameter) {
        if(!this.firstChartParameters.includes(newParameter))
          this.firstChartParameters = [...this.firstChartParameters, newParameter]

        this.secondChartParameters = this.secondChartParameters.filter(function(param) {
          return param.name !== newParameter.name
        })
      }
    },
    removeParameter : function(deletedElement) {
      if(deletedElement) {
        this.firstChartParameters = this.firstChartParameters.filter(function(param) {
          return param.name !== deletedElement.name
        })
        this.secondChartParameters = this.secondChartParameters.filter(function(param) {
          return param.name !== deletedElement.name
        })

        if( this.secondChartParameters.length === 0)
          this.isSecondChartEmpty = true
      }
    },
    addNewParallel : function(targetParameter) {
      if(targetParameter) {
        this.firstChartParameters = this.firstChartParameters.filter(function(param) {
          return param.name !== targetParameter.name
        })
        if(!this.secondChartParameters.includes(targetParameter))
          this.secondChartParameters = [...this.secondChartParameters, targetParameter]
      }
    },
    removeParallel : function(targetParameter) {
      if(targetParameter) {
        this.addNewParameter(targetParameter)
      }
    },
    applyLinkedEffect: function(data) {
      if(this.isLinkedChartMode && data && data['xaxis.range[0]'] && data['xaxis.range[1]']) {
        this.linkedChartData = {
          startXaxis: data['xaxis.range[0]'],
          endXaxis: data['xaxis.range[1]']
        }
      }
    },
    switchLinkedMode: function () {
      if(this.isLinkedChartMode)
        this.isLinkedChartMode = false
      else
        this.isLinkedChartMode = true
    },
    addNewChart: function() {
      console.log("Test add newChart")
      this.nbrParallelChart++
    },
    removeChart: function() {
      if(1 < this.nbrParallelChart)
        this.nbrParallelChart--
    },
    getNewColor: function() {
      if(this.colorCount < colors.length -1) {
        this.colorCount = this.colorCount + 1
        return colors[this.colorCount]
      } else {
        return defaultColor
      }
    },
  },
}
</script>
<style scoped>
</style>