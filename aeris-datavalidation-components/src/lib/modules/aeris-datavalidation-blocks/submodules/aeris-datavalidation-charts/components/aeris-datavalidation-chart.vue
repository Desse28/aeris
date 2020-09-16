<template>
  <div>
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
    >
      <v-row justify="center">
        <v-col cols="12">
          <Plotly
              :key="componentKey"
              :x="Date"
              :data="data"
              :id="chartId"
              :layout="layout"
              :scrollZoom="true"
              :displaylogo="false"
              :displayModeBar="true"
              :modeBarButtons=" modeBarButtons"
          >
          </Plotly>
        </v-col>
      </v-row>
    </AerisDataValidationServices>
  </div>
</template>

<script>
  import { Plotly } from 'vue-plotly'

  import {
    AerisDataValidationServices,
  } from "./../../../../aeris-datavalidation-components"

  const SELECTION_BORDER_COLOR = 'rgb(84,217,27)'
  const SELECTION_BACKGROUND_COLOR = 'rgb(204, 39, 39)'
  const TARGET_SELECTION_BORDER_COLOR = 'rgb(84,217,27)'

  export default {
    name: "aeris-datavalidation-chart",
    props: {
      startDate : {
        type: String
      },
      endDate : {
        type: String
      },
      parameters: {
        type : Array,
        default : () => [],
      },
      dataInfo : {
        type : Object,
        default : () => null,
      },
      isMainChart : {
        type: Boolean,
        default: () => false
      },
      currentSession : {
        type : Object,
        default : () => null
      },
      currentInstrument : {
        type : Object,
        default : () => null
      },
    },
    components: {
      Plotly,
      AerisDataValidationServices
    },
    data() {
      return {
        data: [],
        flags: [],
        layout: {},
        selections : [],
        currentUrl : "",
        currentData: [],
        componentKey: 0,
        currentUuid : "",
        callBack : null,
        modeBarButtons: [],
        currentSelection : null,
        chartId : "mainChart",
        currentParameters : [],
      }
    },
    watch: {
      parameters : function (newParameters, oldsParameters) {
        let paramName
        console.log("Test watch parameter", newParameters, oldsParameters)
        if(this.data.length === 0) {
          paramName= newParameters[0]
          this.initCurrentChart(paramName)
        } else if(oldsParameters.length < newParameters.length) {
          paramName = newParameters[newParameters.length - 1]
          this.addNewParameter(paramName)
        } else if(newParameters.length < oldsParameters.length) {
          this.removeParameter(newParameters, oldsParameters)
        }
      },
      dataInfo : function() {
        if (this.dataInfo) {
          let title =  {
            text : "Current data set - "+ "test",//this.dataInfo.resourceTitle.fr,
            x : 0.52,
            font : {
              color : 'rgb(13, 13, 13)',
              family : '"Open Sans", verdana, arial, sans-serif',
              size : 18,
            }
          }
          this.layout.title = title
        }
      },
    },
    mounted() {
      this.initCurrentChart(this.parameters[0])
    },
    methods: {
      initCurrentChart : function(parameterName) {
        if(this.currentSession && this.currentInstrument && parameterName) {
          if(0 < this.parameters.length)
            this.addNewParameter(parameterName)

          if(this.isMainChart) {
            this.initModeBar()
            this.addEventsHandler()
          }
          this.setLayout()
        }
      },
      initdefaultParameters : function (parameterName) {
        let currentParameterIndex
        if(parameterName && this.parameters) {
          currentParameterIndex = this.parameters.indexOf(parameterName)
          if(currentParameterIndex + 1 < this.parameters.length)
            this.addNewParameter(this.parameters[currentParameterIndex + 1])
        }
      },
      addNewParameter : function (parameterName) {
        let uri
        this.callBack = (data) => {
          if(data) {
            this.updateChart(data.parameterData, parameterName)
            //this.refresh()
            this.initdefaultParameters(parameterName)
          }
        }

        uri = "/instruments/" + parameterName + "/" + this.startDate + "/" + this.endDate
        this.currentUrl = process.env.VUE_APP_ROOT_API + uri
      },
      removeParameter : function (newParameters, oldsParameters) {
        let intersection = oldsParameters.filter(value => !newParameters.includes(value))
        let parameterName = intersection[0]
        const targetParameterIndex = this.data.findIndex(element => element.name === parameterName)
        if(-1 < targetParameterIndex ) {
          this.data.splice(targetParameterIndex, 1)
          //this.refresh()
        }
      },
      updateChart: function(newData, parameterName) {
        let dataContent = {}
        let currentKey= ""
        let currentContent = null
        let newDataKeys = Object.keys(newData[0])

        newData.forEach((data) => {
          newDataKeys.forEach((key) => {
            currentKey = key === "value" ? parameterName : key
            if(! (currentKey in dataContent))
              dataContent[currentKey] = []

            currentContent = data[key]
            dataContent[currentKey].push(currentContent)
          });
        });
        newDataKeys = [newDataKeys[0], currentKey]
        this.setData( newDataKeys, dataContent)
      },
      setData(newDataKeys, dataContent) {
        const xaxis = newDataKeys[0]
        const yaxis = newDataKeys[1]

        this.data = [...this.data, {
          x: dataContent[xaxis],
          y: dataContent[yaxis],
          name: yaxis,
        }]
      },
      refresh : function() {
        this.currentUrl = ""
        this.componentKey += 1
        this.addEventsHandler()
      },
      addEventsHandler : function () {
        this.$nextTick(() => {
          document.getElementById( this.chartId ).on( 'plotly_click', this.clickHandler)
          document.getElementById( this.chartId ).on( 'plotly_selected', this.addNewSelection)
        });
      },
      clickHandler : function (data) {
        let targetPoint
        if(data) {
          targetPoint = data.points[0].x
          this.setCurrentSelection(targetPoint)
          //this.setEventsHandler()
        }
      },
      setCurrentSelection : function(targetPoint) {
        let targetSelection = this.getTargetSelection(targetPoint)
        if(targetSelection) {
          this.clearCurrentSelection()
          targetSelection.line.color = TARGET_SELECTION_BORDER_COLOR
          this.currentSelection = targetSelection
          //this.componentKey += 1
          //this.selectionHandler(targetSelection)

        }
      },
      getTargetSelection : function(targetPoint) {
        let targetPointDate
        let selectionStartDate, selectionEndDate
        let targetsSelection = []

        this.selections.forEach((selection) => {
          targetPointDate = new Date(targetPoint)
          selectionStartDate = new Date(selection.x0)
          selectionEndDate = new Date(selection.x1)

          if(selectionStartDate <= targetPointDate && targetPointDate <= selectionEndDate) {
            targetsSelection.push(selection)
          }
        })
        return targetsSelection[targetsSelection.length-1]
      },
      addNewSelection : function(data) {
        let startDate, endDate
        if(data) {
          startDate = data.range.x[0]
          endDate = data.range.x[1]

          if(!this.isSelectionExist(startDate, endDate)) {
            this.drawSelection(startDate, endDate)
            //this.selectionHandler(data)
          }
        }
      },
      isSelectionExist : function (startDate, endDate) {
        let currentSelection

        for(let index in this.selections) {
          currentSelection = this.selections[index]
          if(currentSelection && currentSelection.x0 === startDate && currentSelection.x1 === endDate )
            return true
        }

        return false
      },
      drawSelection : function(startDate, endDate) {
        this.clearCurrentSelection()

        this.selections = [
          ...this.selections,
          {
            visible : true,
            type : 'rect',
            editable : false,
            layer : 'above',
            opacity : 0.28,
            fillcolor : SELECTION_BACKGROUND_COLOR,
            fillrule : 'evenodd',
            line : {
              width : 5,
              color : SELECTION_BORDER_COLOR,
              dash : 'dot'
            },
            xsizemode : 'scaled',
            ysizemode : 'scaled',
            xref : 'x',
            x0 : startDate,
            x1 : endDate,
            yref : 'paper',
            y0 : 0,
            y1 : 1
          }
        ]
        //this.layout.shapes = this.shapes
        //this.componentKey += 1
        //this.setEventsHandler()
        //this.currentShape = this.shapes[this.shapes.length-1]
      },
      clearCurrentSelection : function() {
        this.layout.shapes.forEach((shape) => {
          if(shape.line.color === SELECTION_BORDER_COLOR) {
            shape.line.color = SELECTION_BORDER_COLOR
          }
        })
      },
      deleteSelection :function() {
        let startDate, endDate
        if(this.currentShape !== null) {
          startDate = this.currentShape.x0
          endDate = this.currentShape.x1
          if(this.currentShape) {
            this.selections = this.selections.filter((selection) => {
              return selection.x0 !== startDate  && selection.x1 !== endDate
            })
            this.layout.shapes = this.selections
            //this.componentKey += 1
            //this.addEventsHandler()
          }
        }
      },
      initModeBar : function() {
        this.modeBarButtons = [
          [
            'pan2d',
            'select2d',
            'autoScale2d',
            'resetScale2d',
          ],
          [
            'zoom2d',
            'zoomIn2d',
            'zoomOut2d',
          ],
          [
            'toImage',
            'sendDataToCloud',
          ],
          [{
            name: "Delete selection",
            //icon: 'Icons.disk',
              click: () => {
                this.deleteSelection()
              }
          }],
        ]
      },
      setLayout: function() {
        this.layout = {
          height : 900,
          showlegend : true,
          selectdirection : 'h',
          plot_bgcolor : "#ffffff",
          paper_bgcolor :  "#ffffff",
          dragmode : 'select',
          title: this.getLayoutTitle(),
          legend: this.getLayoutLegend(),
          xaxis : this.getLayoutXaxis("Time", "date"),
          yaxis : this.getLayoutYaxis("Values"),
          shapes : this.selections,
        }
      },
      getLayoutTitle: function() {
        return {
          text : this.dataInfo !== null ? "Current data set - "+ this.dataInfo.resourceTitle.fr : "Test graph",
              x : 0.52,
              font : {
            color : 'rgb(13, 13, 13)',
                family : '"Open Sans", verdana, arial, sans-serif',
                size : 18,
          }
        }
      },
      getLayoutXaxis: function(value, type) {
        return {
          type: type,
          title: {
            text: value,
            font: {
              family: 'Courier New, monospace',
              size: 18,
              color: 'rgb(18, 18, 18)'
            }
          },
          margin : this.getXaxisMargin(),
          rangeslider: { visible : true, y : 1},
          rangeselector : this.getXaxisRangeselector()
        }
      },
      getXaxisMargin: function() {
        return {
          t: 40,
          b: 26,
          l: 72,
          r: 80,
          pad: 0,
          autoexpand: true,
        }
      },
      getXaxisRangeselector: function() {
        return {
          buttons: [
            {
              count: 1,
              label: '1m',
              step: 'month',
              stepmode: 'backward'
            },
            {
              count: 6,
              label: '6m',
              step: 'month',
              stepmode: 'backward'
            },
            {step: 'all'}
          ]
        }
      },
      getLayoutYaxis: function(value) {
        return {
          title: {
            text: value,
            font: {
              family: 'Courier New, monospace',
              size: 18,
              color: 'rgb(18, 17, 17)'
            }
          }
        }
      },
      getLayoutLegend: function() {
        return  {
          orientation : "h",
          //x : 0.19999999999999893,
          x : 0.05,//landscape mode
          y : -0.6199999999999999,
          borderwidth : 1,
          bordercolor : 'rgb(13, 12, 12)',
        }
      },
    }
  }
</script>

<style scoped>

</style>