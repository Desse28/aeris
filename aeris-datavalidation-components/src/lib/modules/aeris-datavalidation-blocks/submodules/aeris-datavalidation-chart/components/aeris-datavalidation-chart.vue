<template>
  <div>
    <AerisDataValidationServices
        serverUrl="http://localhost:9001"
        flagUrl="/flags"
        groundDataUrl="/ground-data"
        dataInfoUrl="/data-information"
        @metadata="updateMetadata"
    >
      <v-row justify="center">
        <v-col cols="12">
          <AerisDataValidationOptions/>
        </v-col>
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
    AerisDataValidationOptions,
    AerisDataValidationServices,
  } from "./../../../../aeris-datavalidation-components"

  export default {
    name: "aeris-datavalidation-chart",
    components: {
      Plotly,
      AerisDataValidationOptions,
      AerisDataValidationServices,
    },
    data() {
      return {
        data:[{
          x: [1,2,3,4],
          y: [10,15,13,17],
          type:"scatter"
        }],
        currentData: [],
        layout: {
          title: "My graph"
        },
        flags: [],
        dataInfo: {},
        chartId : "mainChart",
        modeBarButtons: [],
      }
    },
    watch: {
      currentData : function(newData) {
        console.log(newData)
        this.refreshChart(newData);
        this.setLayout();
      }
    },
    mounted() {
      this.initModeBar();
    },
    methods: {
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
      updateMetadata: function( metadata, metadataType ) {
        if( metadataType === "flags" )
          this.flags = metadata;
        else if( metadataType === "data" )
          this.currentData = metadata;
        else if( metadataType === "dataInfo" )
          this.dataInfo = metadata[0];
      },
      isDateKey: function(key) {
        return key === 'Date_Time'
      },
      getDate: function(value) {
        return value.split("/").join("-");
      },
      refreshChart: function(newData) {
        let dataContent = {}
        let currentContent = null;
        const newDataKeys = Object.keys(newData[0]);

        newData.forEach((data) => {
          newDataKeys.forEach((key) => {
            if (key !== 'id') {

              if( ! (key in dataContent) )
                dataContent[key] = [];

              currentContent = this.isDateKey( key ) ? this.getDate(data[key]) : data[key];
              dataContent[key].push(currentContent);
            }
          });
        });

        this.setData( newDataKeys, dataContent);
      },
      setData(newDataKeys, dataContent) {
        this.data = [];
        const id = newDataKeys[0];
        const xaxis = newDataKeys[1];
        newDataKeys.forEach((yaxis) => {
          if ( yaxis !== xaxis && yaxis !== id )
            this.data.push (
                {
                  x: dataContent[xaxis],
                  y: dataContent[yaxis],
                  name: yaxis,
                }
            );
        });
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