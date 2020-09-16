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

  export default {
    name: "aeris-datavalidation-chart",
    props: {
      parameters: {
        type : Array,
        default : () => [],
      },
      dataInfo : {
        type : Object,
        default : () => null,
      },
      currentSession : {
        type : Object,
        default : () => null
      },
      currentInstrument : {
        type : Object,
        default : () => null
      }
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
        shapes : [],
        currentUrl : "",
        currentData: [],
        componentKey: 0,
        currentUuid : "",
        callBack : null,
        modeBarButtons: [],
        currentShape : null,
        chartId : "mainChart",
        currentParameters : [],
      }
    },
    watch: {
      currentInstrument : function () {
        if(!this.isCurrentSessionEmpty && !this.isCurrentInstrumentEmpty)
          this.initCurrentChart()
      },
      //parameters : function (newParameters, oldsParameters) {
        /*if(oldsParameters.length < newParameters.length) {
          if(oldsParameters.length === 0)
            this.currentParameters = newParameters
          else
            this.addNewParameter(newParameters)
          this.setLayout()
        } else {
          this.removeParameter(newParameters, oldsParameters)
        }*/
      //},
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
          //this.setEventsHandler()
        }
      },
    },
    computed : {
      isCurrentSessionEmpty : function () {
        console.log("Test isCurrentSessionEmpty")
        return this.currentSession === null;
      },
      isCurrentInstrumentEmpty : function () {
        return this.currentInstrument === null;
      }
    },
    methods: {
      initCurrentChart : function() {
        let targetParameter
        if(this.currentSession && this.currentInstrument) {
          targetParameter = [this.currentSession['mainParameter'].name]
          this.addNewParameter(targetParameter)
          this.initModeBar()
          this.addEventsHandler()
        }
      },
      addNewParameter : function (parameters) {
        let uri
        let startDate = this.currentSession.startDate;
        let endDate = this.currentSession.endDate;
        let parameterName = parameters[parameters.length - 1]

        this.callBack = (data) => {
          console.log("Test add newParameter, ", data)
          if(data) {
            this.refreshChart(data.parameterData)
          }
        }

        uri = "/instruments/" + parameterName + "/" + startDate + "/" + endDate
        this.currentUrl = process.env.VUE_APP_ROOT_API + uri
      },
      refreshChart: function(newData) {
        let dataContent = {}
        let currentContent = null
        const newDataKeys = Object.keys(newData[0])

        newData.forEach((data) => {
          newDataKeys.forEach((key) => {
            if( ! (key in dataContent) )
              dataContent[key] = []

            currentContent = data[key]
            dataContent[key].push(currentContent)
          });
        });

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
        //this.currentUrl = ""
        //this.componentKey += 1
        //this.setEventsHandler()
      },
      addEventsHandler : function () {
        this.$nextTick(() => {
          document.getElementById( this.chartId ).on( 'plotly_click', this.clickHandler )
          //document.getElementById( this.chartId ).on( 'plotly_selected', this.addNewSelection)
        });
      },
      clickHandler : function (data) {
        console.log("Test click : ",data )
        /*let targetPoint
        if(data) {
          targetPoint = data.points[0].x
          this.setCurrentSelection(targetPoint)
          this.setEventsHandler()
        }*/
      },
      //addNewSelection : function(data) {
        /*let x0, x1
        if(data) {
          x0 = data.range.x[0]
          x1 = data.range.x[1]
          if(!this.isSelectionExist(x0, x1)) {
            this.drawSelection(x0, x1)
            this.selectionHandler(data)
          }
        }*/
      //},
      drawSelection : function(x0, x1) {
        console.log(x0, x1)
        //this.clearCurrentSelection()
        this.shapes = [
          ...this.shapes,
          {
            visible : true,
            type : 'rect',
            editable : false,
            layer : 'above',
            opacity : 0.28,
            fillcolor : 'rgb(204, 39, 39)',
            fillrule : 'evenodd',
            line : {
              width : 5,
              color : 'rgb(84,217,27)',
              dash : 'dot'
            },
            xsizemode : 'scaled',
            ysizemode : 'scaled',
            xref : 'x',
            x0 : x0,
            x1 : x1,
            yref : 'paper',
            y0 : 0,
            y1 : 1
          }
        ]
        //this.layout.shapes = this.shapes
        //this.componentKey += 1
        //this.setEventsHandler()
        //this.currentShape = this.shapes[this.shapes.length-1]
        //this.setCurrentShape(this.currentShape)
      },
      isSelectionExist : function (x0, x1) {
        console.log(x0, x1)
        /*for(let index in this.shapes) {
          if(this.layout.shapes[index] && this.layout.shapes[index].x0 === x0 && this.layout.shapes[index].x1 === x1)
            return true
        }
        return false*/
      },
      //setCurrentSelection : function(targetPoint) {
        /*let targetSelection = this.getTargetSelection(targetPoint)
        if(targetSelection) {
          this.clearCurrentSelection()
          targetSelection.line.color = 'rgb(84,217,27)'
          this.currentShape = targetSelection
          this.componentKey += 1
          this.selectionHandler(targetSelection)
          this.setCurrentShape(targetSelection)
        }*/
      //},
      //getTargetSelection : function(targetPoint) {
        /*let targetsSelection = []
        this.layout.shapes.forEach((shape) => {
          if(shape && new Date(shape.x0) <= new Date(targetPoint) &&
              new Date(targetPoint) <= new Date(shape.x1)) {
            targetsSelection.push(shape)
          }
        })
        return targetsSelection[targetsSelection.length-1]*/
      //},
      clearCurrentSelection : function() {
        /*this.layout.shapes.forEach((shape) => {
          if(shape.line.color === 'rgb(84,217,27)') {
            shape.line.color = 'rgb(252, 12, 12)'
          }
        })*/
      },
      removeParameter : function (newParameters, oldsParameters) {
        console.log(newParameters, oldsParameters)
        /*let oldParametersInterNewParameters = oldsParameters.filter(value => !newParameters.includes(value))
        let parameterKey = oldParametersInterNewParameters[0]
        const targetParameterIndex = this.data.findIndex(element => element.name === parameterKey )

        if( -1 < targetParameterIndex ) {
          this.data.splice(targetParameterIndex, 1)
          this.setCurrentData(this.data)
        }*/
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
      deleteSelection : function () {
        let x0, x1
        if( this.currentShape !== null) {
          x0 = this.currentShape.x0
          x1 = this.currentShape.x1
          if( this.currentShape) {
            this.shapes = this.shapes.filter((e) =>{ return e.x0 !==  x0  && e.x1 !== x1})
            this.layout.shapes = this.shapes
            this.componentKey += 1
            this.setEventsHandler()
            this.selectionHandler({type : "clearForm"})
          }
        }
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
          shapes : this.shapes,
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