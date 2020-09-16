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
        :linkedParameters ="linkedParameters"
        :auxParameters="auxParameters"
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
                    :dataInfo="dataInfo"
                    :currentSession="currentSession"
                    :parameters="firstChartParameters"
                    :currentInstrument="currentInstrument"
                />
          </template>
        </AerisDatavalidationPortraitLayaout>
      </template>
      <template v-slot:land2>
        <AerisDatavalidationChart
            v-if="!isSecondChartParametersEmpty"
            :dataInfo="dataInfo"
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
        getFirsChartCol : function () {
          if( this.secondChartParameters.length === 0)
            return 12
          else
            return 7
        },
        getSecondChartCol : function () {
          if( this.secondChartParameters.length === 0)
            return 0
          else
            return 5
        }
      },
      watch : {
        firstChartParameters : function () {
          console.log("Test watch firstChartParameters : ", this.firstChartParameters)
        },
        secondChartParameters : function () {
          console.log("Test watch secondChartParameters : ", this.secondChartParameters)
        }
      },
      methods : {
        newSession : function(currentSession, currentInstrument) {
          if(currentSession && currentInstrument) {
            this.currentSession = currentSession
            this.currentInstrument = currentInstrument
            this.initParametersLabel()
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
          console.log("Test addNewParameter")
          if(newParameter) {
            this.firstChartParameters = [...this.firstChartParameters, newParameter]
            this.secondChartParameters = this.secondChartParameters.filter(function(e) { return e !== newParameter })
          }
        },
        removeParameter : function(deletedElement) {
          console.log("Test removeParameter : ", deletedElement)
          if( deletedElement ) {
            this.firstChartParameters = this.firstChartParameters.filter(function(e) { return e !== deletedElement  })
            this.secondChartParameters = this.secondChartParameters.filter(function(e) { return e !== deletedElement })
          }
        },
        addNewParallel : function(targetParameter) {
          console.log("Test addNewParallel")
          if(targetParameter) {
            this.firstChartParameters = this.firstChartParameters.filter(function(e) { return e !== targetParameter })
            this.secondChartParameters = [...this.secondChartParameters, targetParameter]
          }
        },
        removeParallel : function (targetParameter) {
          console.log("Test removeParallel : ", targetParameter)
          if(targetParameter) {
            this.addNewParameter(targetParameter)
          }
        },
  },
}
</script>
<style scoped>
</style>