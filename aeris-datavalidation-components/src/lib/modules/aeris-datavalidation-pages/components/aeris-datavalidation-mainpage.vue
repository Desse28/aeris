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
        :parametersLabel="parametersLabel"
        :parallelsLabel="parallelsLabel"
    />

    <AerisDatavalidationLandScapeLayaout
        justify="center"
        padding="pa-8"
        :cols="chartsCol"
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
            v-if="0 < parallelChartGridSize"
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
          chartsCol : [12, 0],
          firtsChartData : [],
          parallelsLabel: [],
          parametersLabel : [],
          firstChartParameters : [],
          secondChartParameters : [],
          mainChartGridSize : 12,
          currentSession: null,
          currentInstrument : null,
          parallelChartGridSize : 0,
        }
      },
      watch: {
        secondChartParameters : function() {
          if(this.secondChartParameters.length === 0)
            this.hideParallelChart()
        },
      },
      methods : {
        newSession : function(currentSession, currentInstrument) {
          if(currentSession && currentInstrument) {
            this.currentSession = currentSession
            this.currentInstrument = currentInstrument
          }
        },
        //
        addNewParameter : function (newOptions) {
          console.log(newOptions)
          /*let lastIndex = newOptions.length - 1
          let newParameter = newOptions[lastIndex]

          if(newParameter) {
            this.firstChartParameters = [...this.firstChartParameters, newParameter]
            this.secondChartParameters = this.secondChartParameters.filter(function(e) { return e !== newParameter })
          }*/
        },
        removeParameter : function (newOptions, oldOptons) {
          console.log(newOptions, oldOptons)
          /*let oldOptionsInterNewOptions = oldOptons.filter(value => !newOptions.includes(value))
          let deletedElement = oldOptons.length === 0 ? newOptions : oldOptionsInterNewOptions[0]

          if( deletedElement ) {
            this.firstChartParameters = this.firstChartParameters.filter(function(e) { return e !== deletedElement  })
            if(oldOptons.length !== 0)
              this.secondChartParameters = this.secondChartParameters.filter(function(e) { return e !== deletedElement })
          }*/
        },
        addNewParallel : function( newParallels ) {
          console.log(newParallels)
          /*let lastIndex = newParallels.length - 1
          let newParallelIndex = newParallels[lastIndex].split("parallel")[1]
          let targetParameter = this.parametersLabel[newParallelIndex-1]

          if(1 <= newParallels.length)
            this.displayParallelChart()

          if(targetParameter) {
            this.removeParameter(targetParameter, [])
            this.secondChartParameters = [...this.secondChartParameters, targetParameter]
          }*/
        },
        removeParallel : function (newParallels, oldParrales) {
          console.log(newParallels, oldParrales)
          /*let deletedIndex
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
          }*/
        },
        displayParallelChart : function () {
          /*this.chartsCol = [7, 5]
          this.mainChartGridSize = 7
          this.parallelChartGridSize = 12 - this.mainChartGridSize*/
        },
        hideParallelChart : function () {
          /*this.chartsCol = [12, 0]
          this.mainChartGridSize = 12
          this.parallelChartGridSize = 12 - this.mainChartGridSize*/
        },
  },
}
</script>
<style scoped>
</style>