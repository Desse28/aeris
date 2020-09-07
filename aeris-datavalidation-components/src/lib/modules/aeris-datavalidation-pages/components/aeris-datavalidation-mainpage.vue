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
        :cols="chartsCol"
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
                :requestData="requestData"
                :typeOfRequest="typeOfRequest"
            >
                <AerisDatavalidationChart
                    :uuid="uuid"
                    :dataInfo="dataInfo"
                    :targetShape="targetShape"
                    :setCurrentData="setCurrentData"
                    :setCurrentShape="setCurrentShape"
                    :parameters="firstChartParameters"
                    :selectionHandler="selectionHandler"
                    :selectionPreconfData="selectionPreconfData"
                />
            </AerisDataValidationServices>
          </template>
          <template v-slot:portrait2>
            <AerisDatavalidationSelection
                :selection="selection"
                :currentShape="targetShape"
                :addSelection="addSelection"
                :currentData="firtsChartData"
                :qualityFlags="qualityFlags"
                :sessionSelections="sessionSelections"
                :setCurrentShapeX0="setCurrentShapeX0"
                :setCurrentShapeX1="setCurrentShapeX1"
                :setSelectionPreconfData="setSelectionPreconfData"
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
    <AerisDatavalidationConfiguration
        :setCurrentSessionId="setCurrentSessionId"
    />
  </div>
</template>
<script>
import {
  AerisDatavalidationChart,
  AerisDataValidationServices,
  AerisDatavalidationSelection,
  AerisDatavalidationConfiguration,
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
        AerisDatavalidationConfiguration,
        AerisDatavalidationSimpleToolbar,
        AerisDatavalidationPortraitLayaout,
        AerisDatavalidationLandScapeLayaout,
      },
      data() {
        return {
          url : "",
          uuid : "",
          dataInfo : null,
          requestData : null,
          typeOfRequest : "GET",
          selection : null,
          chartsCol : [12, 0],
          qualityFlags : [],
          firtsChartData : [],
          parallelsLabel: [],
          parametersLabel : [],
          firstChartParameters : [],
          secondChartParameters : [],
          mainChartGridSize : 12,
          currentSession: null,
          currentInstrument : null,
          currentSessionId : "",
          parallelChartGridSize : 0,
          callBack : null,
          currentUrl : "",
          targetShape : null,
          sessionSelections : null,
          selectionPreconfData : [],
        }
      },
      watch: {
        secondChartParameters : function() {
          if(this.secondChartParameters.length === 0)
            this.hideParallelChart()
        },
      },
      methods : {
        setCurrentSessionId : function (sessionId) {
          if(sessionId) {
            this.currentSessionId = sessionId
            this.callBack = this.setCurrentSession
            this.currentUrl = baseUrl + "sessions/" + sessionId
          }
        },
        setCurrentSession : function (session) {
          let linkedParameters = session['linkedParameters']
          this.currentSession = session
          this.sessionSelections = session.sessionSelections
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
          this.currentInstrument = instrument
          this.qualityFlags = instrument.flags
          this.uuid = instrument.uuid;

          this.callBack = this.setDataSetInfo
          this.currentUrl = baseUrl + "instruments/infos/91440f71-9c3e-5d31-befc-2729873ce581"
          console.log("Test currentInstrument : ", instrument)
        },
        setDataSetInfo : function (dataInfo) {
          this.dataInfo = dataInfo
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
          this.chartsCol = [7, 5]
          this.mainChartGridSize = 7
          this.parallelChartGridSize = 12 - this.mainChartGridSize
          console.log("Test hide parallel" )
        },
        hideParallelChart : function () {
          this.chartsCol = [12, 0]
          this.mainChartGridSize = 12
          this.parallelChartGridSize = 12 - this.mainChartGridSize
          console.log("Test hide parallel", this.mainChartGridSize, this.parallelChartGridSize )
        },
        setCurrentData : function (currentData) {
          if(currentData)
            this.firtsChartData = currentData
        },
        selectionHandler : function(selection) {
          if(selection)
            this.selection = selection
        },
        addSelection : function (currentSelection) {
          let sessionSelections = this.currentSession['sessionSelections']
          sessionSelections = sessionSelections.length === 0 ? [currentSelection] : [...this.currentSession, currentSelection]
          this.currentSession['sessionSelections'] = sessionSelections
          this.typeOfRequest = "PUT"
          this.requestData = this.currentSession
          this.callBack = this.updateSessionResponseHandler
          this.currentUrl = baseUrl + "sessions/update"
        },
        updateSessionResponseHandler : function (data) {
          console.log("Test updateSessionResponseHandler : ", data)
        },
        setCurrentShape : function(newCurrentShape) {
          if(newCurrentShape) {
            this.targetShape = newCurrentShape
          }
        },
        setCurrentShapeX0 : function(x0) {
          if(x0) {
            const clone = JSON.parse(JSON.stringify(this.targetShape))
            clone.x0 = x0
            this.targetShape = clone
          }
        },
        setCurrentShapeX1 : function(x1) {
          if(x1) {
            const clone = JSON.parse(JSON.stringify(this.targetShape))
            clone.x1 = x1
            this.targetShape = clone
          }
        },
        setSelectionPreconfData : function(selectionPreconfData) {
          console.log("Test preconfig,", selectionPreconfData)
          //this.selectionPreconfData = selectionPreconfData
        }

  },
}
</script>
<style scoped>
</style>