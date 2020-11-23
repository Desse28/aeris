<template>
  <AerisDatavalidationChart
      :callBack="callBack"
      :chartId="getChartId"
      :positionX="positionX"
      :positionY="positionY"
      :currentUrl="currentUrl"
      :deleteAlert="deleteAlert"
      :requestData="requestData"
      :deleteDialog="deleteDialog"
      :componentKey="componentKey"
      :tooltipState="tooltipState"
      :typeOfRequest="typeOfRequest"
      :tooltipContent="tooltipContent"
      :cancelDelete="cancelDelete"
      :validateDelete="validateDelete"
  />
</template>
<script>
import $ from "jquery";
import ChartConfMixin from "../../../../aeris-datavalidation-common/mixin/chartConfMixin"
import ChartEventsMixin from "../../../../aeris-datavalidation-common/mixin/chartEventsMixin"
import ChartSelectionsMixin from "../../../../aeris-datavalidation-common/mixin/chartSelectionsMixin"
import ChartParametersMixin from "../../../../aeris-datavalidation-common/mixin/chartParametersMixin"
import AerisDatavalidationChart from "../../../../aeris-datavalidation-blocks/submodules/aeris-datavalidation-charts/components/aeris-datavalidation-chart"

export default {
  name: "aeris-datavalidation-heatmap",
  components : {
    AerisDatavalidationChart,
  },
  props : {
    chartName : {
      type : String
    },
    parameters: {
      type : Array,
      default : () => [],
    },
    isMainChart : {
      type : Boolean,
      default : false
    },
    applyLinkedEffect: {
      type: Function,
      default: ()=> {}
    },
    charts : {
      type : Object
    },
    currentSession : {
      type : Object,
      default : () => null
    },
    notifySelection : {
      type : Function,
      default: () => {}
    },
    deleteStep : {
      type: Number,
      default: () => 0
    },
    selection: {
      type: Object,
      default : () => null
    },
    linkedChartData : {
      type : Object,
      default : () => null
    },
    switchLinkedMode: {
      type : Function,
    },
    isLinkedChartMode: {
      type: Boolean,
      default : false
    },
    instrumentInfos : {
      type : Object,
      default : () => null,
    },
    currentInstrument : {
      type : Object,
      default : () => null
    },
    currentSecondChart : {
      type : String,
      default : () => ""
    },
    secondChartsParameters : {
      type: Array
    },
    isBreackPointChange : {
      type : Boolean,
      default : false
    },
  },
  watch : {
    isBreackPointChange : function () {
      console.log("Test isBreackPointChange : ", this.isBreackPointChange)
    },
    deleteStep : function () {
      console.log("Test deleteStep : ", this.deleteStep)
    },
    instrumentInfos : function() {
      console.log("Test instrumentInfos : ", this.instrumentInfos)
    },
    parameters : function (newParameters, oldsParameters) {
      console.log("Test parameters : ", newParameters, oldsParameters)
    },
    selection : function () {
      console.log("Test selection : ", this.selection)
    },
    linkedChartData: function (newLinkedChartData, oldLinkedChartData) {
      console.log("Test linkedChartData : ", newLinkedChartData, oldLinkedChartData)
    },
    isLinkedChartMode : function() {
      console.log("Test isLinkedChartMode : ", this.isLinkedChartMode)
    },
    currentSecondChart : function () {
      console.log("Test selection : ", this.currentSecondChart)
    },
    secondChartsParameters : function (newParameters, oldParameters) {
      console.log("Test secondChartsParameters : ", newParameters, oldParameters)
    }
  },
  mixins: [ChartConfMixin, ChartEventsMixin, ChartParametersMixin, ChartSelectionsMixin],
  data() {
    return {
      positionX : 0,
      positionY : 0,
      chartDiv : null,
      selections : [],
      componentKey: 1,
      deleteAlert: false,
      deleteDialog: false,
      tooltipState: false,
      tooltipContent : "",
      currentSelection : null,
    }
  },
  mounted() {
    this.initCurrentChart(this.parameters[0])
  },
  methods: {
    initCurrentChart : function(parameter) {
      $('document').ready(() => {
        this.chartDiv = document.getElementById( this.getChartId )
        this.initCurrentChartConf(this.chartDiv)
        if(parameter)
          this.initDefaultParameters(parameter, this.getTypeOfChart)
      })
    },
    validateDelete : function () {
      this.deleteDialog = false
      this.deleteCurrentSelection()
    },
    cancelDelete : function () {
      this.deleteDialog = false
    },
  }
}
</script>

<style scoped>

</style>