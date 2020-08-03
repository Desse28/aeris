<template>
  <div>
    <AerisDataValidationServices
        serverUrl="http://localhost:9001"
        flagUrl="/flags"
        groundDataUrl="/ground-data"
        dataInfoUrl="/data-information"
        @metadata="updateMetadata"
    >
    </AerisDataValidationServices>
    <Plotly
        :data="data"
        :layout="layout"
        :display-mode-bar="false"
    >
    </Plotly>
  </div>
</template>

<script>
  import { Plotly } from 'vue-plotly'

  import { AerisDataValidationServices } from "./../../../../aeris-datavalidation-components"

  export default {
    name: "aeris-datavalidation-chart",
    components: {
      Plotly,
      AerisDataValidationServices
    },
    data() {
      return {
        data:[{
          x: [1,2,3,4],
          y: [10,15,13,17],
          type:"scatter"
        }],
        layout:{
          title: "My graph"
        },
        flags: [],
        dataInfo: [],

      }
    },
    watch: {
      data : function(newData) {
        this.refreshChart(newData);
        this.setLayout();
      }
    },
    methods: {
      updateMetadata: function( metadata, metadataType ) {
        if( metadataType === "flags" )
          this.flags = metadata;
        else if( metadataType === "data" )
          this.data = metadata;
        else if( metadataType === "dataInfo" )
          this.dataInfo = metadata;
      },
      isDateKey: function(key) {
        return key === 'Date_Time'
      },
      getDate: function(value) {
        let date = value.split("/").join("-");
        return date;
      },
      refreshChart: function(newData) {
        let dataContent = {}
        let currentContent = null;
        let newDataKeys = Object.keys(newData[0]);

        newData.forEach((data) => {
          newDataKeys.forEach((key) => {
            if( ! (key in dataContent) )
              dataContent[key] = [];
            currentContent = this.isDateKey( key ) ? this.getDate(data[key]) : data[key];
            dataContent[key].push(currentContent);
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
          xaxis : this.getLayoutXaxis("x", "date"),
          yaxis : this.getLayoutYaxis("y"),
        }
      },
      getLayoutTitle: function() {
        return {
          text : "Default title =",
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
          x : 0.19999999999999893,
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