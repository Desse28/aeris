<template>
  <v-row justify="center">
    <v-col cols="12">
      <div
          :id="chartId"
      >
      </div>
    </v-col>
  </v-row>
</template>

<script>
import $ from "jquery";
import Plotly from 'plotly.js-dist'

export default {
  name: "aeris-datavalidation-heatmap",
  props : {
    chartId : {
      type : String
    }
  },
  data() {
    return {
      data:  [
        {
          z: [[1, 20, 30], [20, 1, 60], [30, 60, 1]],
          type: 'heatmap'
        }
      ],
      layout: {},
      config : {},
      selections : [],
      modeBarButtons: [],
    }
  },
  mounted() {
    this.initCurrentChart()
  },
  methods: {
    initCurrentChart: function () {
      this.initConfig()
      this.initLayout()
      $('document').ready(() => {
        Plotly.newPlot(this.chartId, this.data, this.layout, this.config)
      })
    },
    initConfig : function() {
      this.modeBarButtons = [
        [
          'select2d',
          /*{
            name: "Delete selection",
            icon: {
              'width': 500,
              'height': 500,
              'path': blackTrashIconPath,
              fill: "#ed3131"
            },
            click: () => {
              this.deleteTargetSelection()
            }
          },*/
          /*{
            name: "Link chart",
            icon: {
              'width': 500,
              'height': 500,
              'path': linkIcon,
            },
            click: () => {
              this.enableLinkedMode()
            }
          },*/
          'zoomIn2d',
          'zoomOut2d',
        ],
        [
          'pan2d',
         /* {
            name: "Reset axes",
            icon:  Plotly.Icons.home,
            click: () => {
              this.resetAxes()
            }
          },*/
          'sendDataToCloud',
        ]
      ]

      this.config = {
        modeBarButtons: this.modeBarButtons,
        scrollZoom : true,
        displaylogo : false,
        displayModeBar : true,
        plotlyServerURL : "https://chart-studio.plotly.com"
      }
    },
    initLayout: function() {
      this.layout = {
        height : 900,
        showlegend : true,
        selectdirection : 'h',
        plot_bgcolor : "#ffffff",
        paper_bgcolor :  "#ffffff",
        dragmode : 'select',
        title: "First test",
        legend: this.getLayoutLegend(),
        xaxis : this.getLayoutXaxis("Time", "date"),
        yaxis : this.getLayoutYaxis("Values"),
        shapes : this.selections,
        modebar: this.getLayoutModeBarConf()
      }
    },
    getLayoutLegend: function() {
      return  {
        orientation : "v",
        x : 1.02,
        y : 0.8,
        borderwidth : 1,
        bordercolor : 'rgb(13, 12, 12)',
        xanchor: "auto",
        yanchor: "auto",
      }
    },
    getLayoutXaxis: function(/*type, value*/) {
      return {
        //type: type,
        title: {
          //text: value,
          font: {
            family: 'Courier New, monospace',
            size: 18,
            color: 'rgb(18, 18, 18)'
          }
        },
        //margin : this.getXaxisMargin(),
        rangeslider: { visible : true, y : 1},
        //rangeselector : this.getXaxisRangeselector()
      }
    },
    getLayoutYaxis: function(/*value*/) {
      return {
        title: {
          //text: value,
          font: {
            family: 'Courier New, monospace',
            size: 18,
            color: 'rgb(18, 17, 17)'
          }
        }
      }
    },
    getLayoutModeBarConf : function () {
      return {
        orientation: 'h',
        color: 'rgba(68, 68, 68, 0.3)',
        bgcolor: 'rgba(255, 255, 255, 0.5)',
        activecolor: "rgba(212, 10, 10, 0.7)",
      }
    },
  }
}
</script>

<style scoped>

</style>