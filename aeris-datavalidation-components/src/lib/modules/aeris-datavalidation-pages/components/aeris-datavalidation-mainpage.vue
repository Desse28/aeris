<template>
  <div>
    <AerisDatavalidationConfiguration
        :newSession="newSession"
    />

    <AerisDatavalidationSimpleToolbar
        :addNewParallel="addNewParallel"
        :removeParallel="removeParallel"
        :addNewParameter="addNewParameter"
        :removeParameter="removeParameter"
        :notifySelection=" notifySelection"
        :linkedParameters ="linkedParameters"
        :auxParameters="auxParameters"
        :selection="selection"
    />

    <AerisDatavalidationLandScapeLayaout
        justify="center"
        padding="pa-8"
        :cols="[getFirsChartCol, getSecondChartCol]"
        :nbrChildElement="2"
    >
      <template v-slot:land1>
        <AerisDatavalidationPortraitLayaout
            padding="pa-8"
            justify="center"
            :nbrChildElement="1"
            :cols="[12]"
        >
          <template v-slot:portrait1>
                <AerisDatavalidationChart
                    :endDate="endDate"
                    :isMainChart="true"
                    :dataInfo="dataInfo"
                    :selection="selection"
                    :startDate="startDate"
                    :currentSession="currentSession"
                    :notifySelection="notifySelection"
                    :parameters="firstChartParameters"
                    :currentInstrument="currentInstrument"
                />
          </template>
        </AerisDatavalidationPortraitLayaout>
      </template>
      <template v-slot:land2>
        <AerisDatavalidationChart
            v-if="!isSecondChartParametersEmpty"
            :endDate="endDate"
            :isMainChart="false"
            :dataInfo="dataInfo"
            :startDate="startDate"
            :currentSession="currentSession"
            :parameters="secondChartParameters"
            :currentInstrument="currentInstrument"
        />
      </template>
    </AerisDatavalidationLandScapeLayaout>
  </div>
</template>
<script>
import {
  AerisDatavalidationChart,
  AerisDatavalidationConfiguration,
  AerisDatavalidationSimpleToolbar,
  AerisDatavalidationPortraitLayaout,
  AerisDatavalidationLandScapeLayaout,
} from "./../../aeris-datavalidation-components"

    export default {
      name: "aeris-datavalidation-homepage",
      components: {
        AerisDatavalidationChart,
        AerisDatavalidationConfiguration,
        AerisDatavalidationSimpleToolbar,
        AerisDatavalidationPortraitLayaout,
        AerisDatavalidationLandScapeLayaout,
      },
      data() {
        return {
          endDate: "",
          startDate: "",
          dataInfo : null,
          selection : null,
          auxParameters: [],
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
        newSession : function(currentSession, currentInstrument) {
          if(currentSession && currentInstrument) {
            this.currentSession = currentSession
            this.currentInstrument = currentInstrument
            this.startDate = this.currentSession.startDate;
            this.endDate = this.currentSession.endDate;
            this.initParametersLabel()
            this.firstChartParameters = [this.currentSession['mainParameter'].name]
          }
        },
        initParametersLabel : function() {
          let auxParameters = this.currentInstrument['auxParameters']
          if(auxParameters) {
            auxParameters.forEach((parameter)=> {
              this.secondChartParameters.push(parameter.name)
            })
            this.auxParameters = auxParameters
            this.linkedParameters = this.currentSession.linkedParameters
          }
        },
        addNewParameter : function(newParameter) {
          if(newParameter) {
            this.firstChartParameters = [...this.firstChartParameters, newParameter]
            this.secondChartParameters = this.secondChartParameters.filter(function(e) { return e !== newParameter })
          }
        },
        removeParameter : function(deletedElement) {
          if(deletedElement) {
            this.firstChartParameters = this.firstChartParameters.filter(function(e) { return e !== deletedElement  })
            this.secondChartParameters = this.secondChartParameters.filter(function(e) { return e !== deletedElement })
          }
        },
        addNewParallel : function(targetParameter) {
          if(targetParameter) {
            this.firstChartParameters = this.firstChartParameters.filter(function(e) { return e !== targetParameter })
            this.secondChartParameters = [...this.secondChartParameters, targetParameter]
          }
        },
        removeParallel : function(targetParameter) {
          if(targetParameter) {
            this.addNewParameter(targetParameter)
          }
        },
  },
}
</script>
<style scoped>
</style>