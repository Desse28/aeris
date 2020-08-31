<template>
  <div>
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
    >
      <v-row justify="center">
        <v-col cols="12">
          <Plotly
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

  const baseUrl = "http://localhost:9001/"

  export default {
    name: "aeris-datavalidation-chart",
    props: {
      uuid : {
        type : String,
        default : ""
      },
      parameters: {
        type : Array,
        default : () => [],
      }
    },
    components: {
      Plotly,
      AerisDataValidationServices
    },
    data() {
      return {
        data:[/*{
          x: [1,2,3,4],
          y: [10,15,13,17],
          type:"scatter"
        }*/],
        currentData: [],
        currentUuid : "",
        currentUrl : "",
        layout: {
          title: "My graph"
        },
        flags: [],
        dataInfo: {},
        chartId : "mainChart",
        modeBarButtons: [],
        currentParameters : [],
        callBack : this.addNewParameterData
      }
    },
    watch: {
      parameters : function (newParameters, oldsParameters) {
        if(oldsParameters.length < newParameters.length) {
          if(oldsParameters.length === 0)
            this.currentParameters = newParameters
          else
            this.addNewParameter(newParameters)
        } else {
          this.removeParameter(newParameters, oldsParameters)
        }
      },
      uuid : function(newUuid) {
        this.currentUuid = newUuid
        if(this.currentUuid !== "" && 0 < this.currentParameters.length) {
          this.currentParameters.forEach( (parameterName) => {
            this.currentUrl = baseUrl + "instruments/" + parameterName + "/" + this.currentUuid
          })
        }
      },
      currentData : function(newData) {
        this.refreshChart(newData)
        this.setLayout()
      },
    },
    mounted() {
      if(this.parameters.length === 1) {
        this.currentUuid = this.uuid
        this.addNewParameter(this.parameters)
      }

      this.initModeBar()
    },
    methods: {
      addNewParameter : function (newParameters) {
        let lastIndex = newParameters.length - 1
        let newParameter = newParameters[lastIndex]

        if(newParameters)
          this.currentUrl = baseUrl + "instruments/" + newParameter + "/" + this.currentUuid
      },
      addNewParameterData : function (parameterData) {
        if(parameterData)
          this.refreshChart(parameterData)
      },
      removeParameter : function (newParameters, oldsParameters) {
        let oldParametersInterNewParameters = oldsParameters.filter(value => !newParameters.includes(value))
        let parameterKey = oldParametersInterNewParameters[0]
        const targetParameterIndex = this.data.findIndex(element => element.name === parameterKey )
        console.log("Test removeParameter (before) : ", this.data)
        if( -1 < targetParameterIndex )
          this.data.splice(targetParameterIndex, 1)
          //this.data = this.data.splice(targetParameterIndex, 1)
        console.log("Test removeParameter (after) : ", this.data)

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
        ]
      },
      isDateKey: function(key) {
        return key === 'Date_Time'
      },
      getDate: function(value) {
        return value.split("/").join("-")
      },
      refreshChart: function(newData) {
        let dataContent = {}
        let currentContent = null
        const newDataKeys = Object.keys(newData[0])

        newData.forEach((data) => {
          newDataKeys.forEach((key) => {
              if( ! (key in dataContent) )
                dataContent[key] = []

              currentContent = this.isDateKey( key ) ? this.getDate(data[key]) : data[key]
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
        this.currentUrl = ""
      },
      setLayout: function() {
        this.layout = {
          height : 900,
          showlegend : true,
          selectdirection : 'any',
          plot_bgcolor : "#ffffff",
          paper_bgcolor :  "#ffffff",
          title: this.getLayoutTitle(),
          legend: this.getLayoutLegend(),
          xaxis : this.getLayoutXaxis("Time", "date"),
          yaxis : this.getLayoutYaxis("Values"),
        }
      },
      getLayoutTitle: function() {
        return {
          text : this.dataInfo.resourceTitle.fr,
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