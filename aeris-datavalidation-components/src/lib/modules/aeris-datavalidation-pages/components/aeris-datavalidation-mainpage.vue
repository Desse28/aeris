<template>
  <div>
    <AerisDatavalidationConfiguration
        :newSession="newSession"
    />
    <AerisDatavalidationSimpleToolbar
        :addNewChart="addNewChart"
        :removeChart="removeChart"
        :addNewParallel="addNewParallel"
        :removeParallel="removeParallel"
        :addNewParameter="addNewParameter"
        :removeParameter="removeParameter"
        :notifySelection=" notifySelection"
        :notifyDeleteSelection="notifyDeleteSelection"
        :linkedParameters ="linkedParameters"
        :auxParameters="auxParameters"
        :qualityFlags="qualityFlags"
        :isDeleteMode="isDeleteMode"
        :session="currentSession"
        :selection="selection"
    />

    <AerisDatavalidationLandScapeLayaout
        justify="center"
        padding="pa-3"
        :cols="[getFirsChartCol, getSecondChartCol]"
        :nbrChildElement="2"
    >
      <template v-slot:land1>
        <AerisDatavalidationPortraitLayaout
            padding="pa-0"
            justify="center"
            :nbrChildElement="1"
            :cols="[12]"
        >
          <template v-slot:portrait1>
            <AerisDatavalidationMainChartTab
                :endDate="endDate"
                :isMainChart="true"
                :dataInfo="dataInfo"
                :selection="selection"
                :startDate="startDate"
                :deleteStep="deleteStep"
                :isDeleteMode="isDeleteMode"
                :defaultSelections="selections"
                :currentSession="currentSession"
                :parameters="firstChartParameters"
                :currentInstrument="currentInstrument"
                :notifySelection="notifySelection"
                :notifyDeleteSelection="notifyDeleteSelection"
            />
          </template>
        </AerisDatavalidationPortraitLayaout>
      </template>
      <template v-slot:land2>
        <AerisDatavalidationChartsTab
            :isSecondChartParametersEmpty="!isSecondChartParametersEmpty"
            :endDate="endDate"
            :isMainChart="false"
            :dataInfo="dataInfo"
            :startDate="startDate"
            :currentSession="currentSession"
            :nbrParallelChart="nbrParallelChart"
            :secondChartParameters="secondChartParameters"
            :currentInstrument="currentInstrument"
        />
      </template>
    </AerisDatavalidationLandScapeLayaout>
  </div>
</template>
<script>
import {
  AerisDatavalidationChartsTab,
  AerisDatavalidationMainChartTab,
  AerisDatavalidationConfiguration,
  AerisDatavalidationSimpleToolbar,
  AerisDatavalidationPortraitLayaout,
  AerisDatavalidationLandScapeLayaout
} from "./../../aeris-datavalidation-components"

  const COLORS = [
    'rgb(255, 0, 0)',
    'rgb(0, 255, 1)',
    'rgb(0, 255, 255)',
    'rgb(0, 0, 255)',
    'rgb(170, 170, 0)',
    'rgb(244, 67, 54)',
    'rgb(233, 30, 99)',
    'rgb(156, 39, 176)',
    'rgb(103, 58, 183)',
    'rgb(63, 81, 181)',
    'rgb(33, 150, 243)',
    'rgb(0, 188, 212)',
    'rgb(0, 150, 136)',
    'rgb(76, 175, 180)',
    'rgb(255, 235, 59)',
    'rgb(255, 193, 7)',
    'rgb(255, 193, 7)',
    'rgb(255, 152, 0)',
    'rgb(96, 125, 139)',
  ]
    export default {
      name: "aeris-datavalidation-homepage",
      components: {
        AerisDatavalidationChartsTab,
        AerisDatavalidationMainChartTab,
        AerisDatavalidationConfiguration,
        AerisDatavalidationSimpleToolbar,
        AerisDatavalidationPortraitLayaout,
        AerisDatavalidationLandScapeLayaout,
      },
      data() {
        return {
          endDate: "",
          colors : [],
          startDate: "",
          colorCount : 0,
          deleteStep : 0,
          selections: [],
          dataInfo : null,
          selection : null,
          qualityFlags : [],
          auxParameters: [],
          nbrParallelChart : 2,
          isDeleteMode : false,
          linkedParameters : [],
          currentSession: null,
          currentInstrument : null,
          firstChartParameters : [],
          secondChartParameters : [],
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
          if( this.secondChartParameters.length === 0)
            return 0
          else
            return 5
        }
      },
      methods : {
        notifySelection : function(startDate, endDate) {
          this.selection = {startDate: startDate, endDate: endDate}
        },
        notifyDeleteSelection : function (state) {
          this.isDeleteMode = state

          if(this.deleteStep === 2)
            this.deleteStep = 0

          if(state)
            this.deleteStep = this.deleteStep + 1
        },
        newSession : function(currentSession, currentInstrument) {
          let mainParameter
          if(currentSession && currentInstrument) {
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
        initParametersLabel : function() {
          let auxParameters = this.currentInstrument['auxParameters']
          let linkedParameters = this.currentSession.linkedParameters

          if(auxParameters) {
            auxParameters.forEach((parameter) => {
              parameter.color = this.getNewColor()
              this.secondChartParameters.push(parameter)
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
          }
        },
        addNewParallel : function(targetParameter) {
          if(targetParameter) {
            this.firstChartParameters = this.firstChartParameters.filter(function(param) {
              return param.name !== targetParameter.name
            })
            this.secondChartParameters = [...this.secondChartParameters, targetParameter]
          }
        },
        removeParallel : function(targetParameter) {
          if(targetParameter) {
            this.addNewParameter(targetParameter)
          }
        },
        addNewChart : function() {
          console.log("Test add newChart")
          this.nbrParallelChart++
        },
        removeChart : function() {
          if(1 < this.nbrParallelChart)
            this.nbrParallelChart--
        },
        getNewColor : function() {
          if(this.colorCount <  COLORS.length -1) {
            this.colorCount = this.colorCount + 1
            return COLORS[this.colorCount]
          } else {
            return 'rgb(96, 125, 139)'
          }
        }
  },
}
</script>
<style scoped>
</style>