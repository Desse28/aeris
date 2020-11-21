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
import $ from "jquery"
import ChartConfMixin from "../../../../aeris-datavalidation-common/mixin/chartConfMixin"
import ChartEventsMixin from "../../../../aeris-datavalidation-common/mixin/chartEventsMixin"
import ChartSelectionsMixin from "../../../../aeris-datavalidation-common/mixin/chartSelectionsMixin"
import ChartParametersMixin from "../../../../aeris-datavalidation-common/mixin/chartParametersMixin"
import AerisDatavalidationChart from "../../../../aeris-datavalidation-blocks/submodules/aeris-datavalidation-charts/components/aeris-datavalidation-chart"

const SESSION_UPDATE_PATH = "/sessions/update"

export default {
  name: "aeris-datavalidation-timeseries",
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
    secondChartsParameters : {
      type: Array
    },
    currentSecondChart : {
      type : String,
      default : () => ""
    },
    isBreackPointChange : {
      type : Boolean,
      default : false
    },
    notifyDeleteSelection: {
      type: Function,
      default : () => {}
    },
  },
  watch: {
    isBreackPointChange : function () {
      this.refreshChart()
    },
    currentSecondChart : function () {
      this.currentSession.currentSecondChartName = this.currentSecondChart
      this.updateSessionState()
    },
    secondChartsParameters : function (newParameters, oldParameters) {
      if(this.secondChartsParameters.length === 0 || (newParameters.length === 1 && oldParameters.length === 0)) {

        if(newParameters.length === 0 && 1 <= oldParameters.length) {
          this.updateSessionState()
        }

        this.refreshChart()

      }
    },
    deleteStep : function() {
      if(this.deleteStep && this.isMainChart) {
        this.deleteCurrentSelection()
        this.bindChartListener()
      }
    },
    parameters : {
      handler: function (newParameters, oldsParameters) {
        let parameter
        if(this.data.length === 0) {
          parameter = newParameters[0]
          this.initCurrentChart(parameter)
        } else if(oldsParameters.length < newParameters.length) {
          parameter = newParameters[newParameters.length - 1]
          this.addNewParameter(parameter)
        } else if(newParameters.length < oldsParameters.length) {
          this.removeParameter(newParameters, oldsParameters)
        }
      },
      deep: true
    },
    selection: function() {
      let index
      if(!this.isSelectionEmpty()) {
        if(this.isDateChange()) {
          if(this.isSwitchSelection(this.selection.startDate, this.selection.endDate)) {
            index = this.getSelectionIndex(this.selection.startDate, this.selection.endDate)
            if(index !== -1) {
              this.setCurrentSelection(index, true)
            }
          } else if(this.currentSelection) {
            this.setCurrentSelectionPeriod(this.selection.startDate, this.selection.endDate)
          }
        }
      }
    },
    linkedChartData: function (newLinkedChartData, oldLinkedChartData) {
      if(this.linkedChartData && !this.isMainChart && this.chartName === this.currentSecondChart) {
        if(newLinkedChartData.type === "Reset axes") {
          this.resetAxes()
        } else if(newLinkedChartData.startXaxis !== oldLinkedChartData.startXaxis ||
            newLinkedChartData.endXaxis !== oldLinkedChartData.endXaxis) {
          this.setLayoutAxisRange()
        } else if(!this.isMainChart && newLinkedChartData.hoverData) {
          this.applyLinkedHoverEffect( newLinkedChartData.hoverData.points[0])
        }
      }
    },
    isLinkedChartMode : function() {
      if(!this.isMainChart && !this.isLinkedChartMode) {
        this.$Plotly.Fx.hover(this.chartDiv, [])
      }
    },
    instrumentInfos : function() {
      const {resourceTitle} = this.instrumentInfos
      const cloneLayout = JSON.parse(JSON.stringify(this.layout))

      if(resourceTitle) {
        let title =  {
          text : "Current data set - "+ resourceTitle.fr,
          x : 0.52,
          font : {
            color : 'rgb(13, 13, 13)',
            family : '"Open Sans", verdana, arial, sans-serif',
            size : 18,
          }
        }
        cloneLayout.title = title
        this.layout = cloneLayout
      }
    },
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
  computed : {
    getStartXaxis : function() {
      let startXaxis = this.data ? this.data[0].x[0] : ""
      return startXaxis
    },
    getEndXaxis : function() {
      let len = this.data[0].x.length
      let endXaxis = this.data ? this.data[0].x[len-1] : ""
      return endXaxis
    },
  },
  mounted() {
    this.initCurrentChart(this.parameters[0])
  },
  methods: {
    initCurrentChart : function(parameter) {
      this.$nextTick(() => {
        $('document').ready(() => {
          if(this.currentSession && this.currentInstrument) {
            this.chartDiv = document.getElementById( this.getChartId )
            this.initCurrentChartConf(this.chartDiv)

            if(parameter)
              this.initDefaultParameters(parameter)

            if(this.isMainChart)
              this.drawDefaultSelections()

            this.bindChartListener()
          }
        })
      })
    },
    updateSessionState : function () {
      this.typeOfRequest = "PUT"
      this.requestData = this.currentSession
      this.currentUrl = ""

      this.callBack = (data) => {
        this.currentUrl = ""
        if(data) {
          console.info(data)
          this.bindChartListener()
        }
      }

      this.currentUrl = process.env.VUE_APP_ROOT_API + SESSION_UPDATE_PATH
    },
    validateDelete : function () {
      this.deleteDialog = false
      this.deleteCurrentSelection()
      this.resetDeleteDefaultState()
    },
    cancelDelete : function () {
      this.deleteDialog = false
      this.notifyDeleteSelection(false)
    },
    isDateChange : function() {
      return (this.selection.startDate !== this.currentSelection.x0 ||
          this.selection.endDate !== this.currentSelection.x1)
    },
    refreshChart : function() {
      if(this.componentKey%2 === 0)
        this.componentKey = 1
      else
        this.componentKey = 2

      this.initCurrentChart(null)
    },
    resetDeleteDefaultState: function () {
      setTimeout(() => {
        this.notifyDeleteSelection(false)
      }, 500)
    },
  }
}
</script>

<style scoped>

</style>