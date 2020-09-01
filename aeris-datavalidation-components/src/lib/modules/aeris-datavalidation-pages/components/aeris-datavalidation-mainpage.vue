<template>
  <div>
    <AerisDatavalidationSimpleToolbar
        :addNewParallel="addNewParallel"
        :removeParallel="removeParallel"
        :addNewParameter="addNewParameter"
        :removeParameter="removeParameter"
        :parametersLabel="parametersLabel"
        :parallelsLabel="parallelsLabel"
    />
    <AerisDatavalidationLandScapeLayaout
        justify="center"
        padding="pa-8"
        :cols="[mainChartGridSize, parallelChartGridSize]"
        :nbrChildElement="2"
    >
      <template v-slot:land1>
        <AerisDatavalidationPortraitLayaout
            padding="pa-8"
            justify="center"
            :nbrChildElement="2"
            :cols="[12, 12]"
        >
          <template v-slot:portrait1>
            <AerisDataValidationServices
                :url="currentUrl"
                :callBack="callBack"
            >
                <AerisDatavalidationChart
                    :uuid="uuid"
                    :setCurrentData="setCurrentData"
                    :parameters="firstChartParameters"
                    :selectionHandler="selectionHandler"
                />
            </AerisDataValidationServices>
          </template>
          <template v-slot:portrait2>
            <AerisDatavalidationSelection
                :selection="selection"
                :currentData="firtsChartData"
                :qualityFlags="qualityFlags"
            />
          </template>
        </AerisDatavalidationPortraitLayaout>
      </template>
      <template v-slot:land2>
        <AerisDatavalidationChart
            v-if="0 < parallelChartGridSize"
            :uuid="uuid"
            :parameters="secondChartParameters"
        />
      </template>
    </AerisDatavalidationLandScapeLayaout>
  </div>
</template>
<script>
import {
  AerisDatavalidationChart,
  AerisDataValidationServices,
  AerisDatavalidationSelection,
  AerisDatavalidationSimpleToolbar,
  AerisDatavalidationPortraitLayaout,
  AerisDatavalidationLandScapeLayaout,
} from "./../../aeris-datavalidation-components"

const baseUrl = "http://localhost:9001/";

    export default {
      name: "aeris-datavalidation-homepage",
      components: {
        AerisDatavalidationChart,
        AerisDataValidationServices,
        AerisDatavalidationSelection,
        AerisDatavalidationSimpleToolbar,
        AerisDatavalidationPortraitLayaout,
        AerisDatavalidationLandScapeLayaout,
      },
      data() {
        return {
          url : "",
          uuid : "",
          selection : null,
          qualityFlags : [],
          firtsChartData : [],
          parallelsLabel: [],
          parametersLabel : [],
          firstChartParameters : [],
          secondChartParameters : [],
          mainChartGridSize : 12,
          currentSession: null,
          currentInstrument : null,
          parallelChartGridSize : 0,
          callBack : this.setCurrentSession,
          currentUrl : baseUrl + "sessions/5f3d06f1f94fdd631a02655c",
        }
      },
      watch: {
        secondChartParameters : function() {
          if(this.secondChartParameters.length === 0)
            this.hideParallelChart()
        },
      },
      methods : {
        setCurrentSession : function (session) {
          let linkedParameters = session['linkedParameters']

          this.currentSession = session
          this.parametersLabel = Object.keys(linkedParameters).map((key) => linkedParameters[key].name)
          this.firstChartParameters = [...this.firstChartParameters, session['mainParameter'].name]
          this.callBack = this.setCurrentInstrument
          this.setParallelsArray(this.parametersLabel)
          this.currentUrl = baseUrl + "instruments/" + session['instrumentId']
          console.log("Test currentSession : ", session)
        },
        setParallelsArray : function (parametersLabel) {
          let len = parametersLabel.length
          this.parallelsLabel = Array(len).fill().map((_, i) => 'parallel' + (i + 1))
        },
        setCurrentInstrument : function (instrument) {
          let flags = instrument.flags
          this.currentSession = instrument
          this.qualityFlags =  Object.keys(flags).map((key) => flags[key].label)
          this.uuid = instrument.uuid;
          console.log("Test currentInstrument : ", instrument)
        },
        addNewParameter : function (newOptions) {
          let lastIndex = newOptions.length - 1
          let newParameter = newOptions[lastIndex]

          if(newParameter) {
            this.firstChartParameters = [...this.firstChartParameters, newParameter]
            this.secondChartParameters = this.secondChartParameters.filter(function(e) { return e !== newParameter })
          }
        },
        removeParameter : function (newOptions, oldOptons) {
          let oldOptionsInterNewOptions = oldOptons.filter(value => !newOptions.includes(value))
          let deletedElement = oldOptons.length === 0 ? newOptions : oldOptionsInterNewOptions[0]

          if( deletedElement ) {
            this.firstChartParameters = this.firstChartParameters.filter(function(e) { return e !== deletedElement  })
            if(oldOptons.length !== 0)
              this.secondChartParameters = this.secondChartParameters.filter(function(e) { return e !== deletedElement })
          }
        },
        addNewParallel : function( newParallels ) {
          let lastIndex = newParallels.length - 1
          let newParallelIndex = newParallels[lastIndex].split("parallel")[1]
          let targetParameter = this.parametersLabel[newParallelIndex-1]

          if(1 <= newParallels.length)
            this.displayParallelChart()

          if(targetParameter) {
            this.removeParameter(targetParameter, [])
            this.secondChartParameters = [...this.secondChartParameters, targetParameter]
          }
        },
        removeParallel : function (newParallels, oldParrales) {
          let deletedIndex
          let targetParameter
          let oldParallelsInterNewParallels

          if(newParallels && oldParrales) {
            oldParallelsInterNewParallels = oldParrales.filter(value => !newParallels.includes(value))
            deletedIndex = oldParallelsInterNewParallels[0].split("parallel")[1]
            targetParameter = this.parametersLabel[deletedIndex-1]

            if(targetParameter) {
              this.addNewParameter([targetParameter])
              this.secondChartParameters = this.secondChartParameters.filter(function(e) { return e !== targetParameter })
            }
          }
        },
        displayParallelChart : function () {
          this.mainChartGridSize = 7
          this.parallelChartGridSize = 12 - this.mainChartGridSize
        },
        hideParallelChart : function () {
          this.mainChartGridSize = 12
          this.parallelChartGridSize = 12 - this.mainChartGridSize
        },
        setCurrentData : function (currentData) {
          this.firtsChartData = currentData
        },
        selectionHandler : function(selection) {
          if(selection) {
            this.selection = selection
          }
        }
  },
}
</script>
<style scoped>
</style>