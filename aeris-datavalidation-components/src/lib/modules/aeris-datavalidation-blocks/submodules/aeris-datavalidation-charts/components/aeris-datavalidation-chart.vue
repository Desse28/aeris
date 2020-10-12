<template>
  <div
      class="v-sheet v-sheet--outlined theme--light grey--text--"
  >
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
        :requestData="requestData"
        :typeOfRequest="typeOfRequest"
    >

      <v-alert type="error" v-if="deleteAlert">
        {{$t('session.message_chooseQualityFlag')}}
      </v-alert>
      <AerisDatavalidationDeleteDialog
          :dialog="deleteDialog"
          :ok="$t('session.label_yes')"
          :cancel="$t('session.label_no')"
          :okCallBack="validateDelete"
          :cancelCallBack="cancelDelete"
          :title="$t('session.label_deletion')"
          :message="$t('session.message_delete')"
      />
      <v-row justify="center">
        <v-col cols="12">
          <Plotly
              :x="Date"
              :key="componentKey"
              :data="data"
              :id="getChartId"
              :layout="layout"
              :scrollZoom="true"
              :displaylogo="false"
              :displayModeBar="true"
              :modeBarButtons="modeBarButtons"
          >
          </Plotly>
        </v-col>
      </v-row>
    </AerisDataValidationServices>
  </div>
</template>

<script>
  import $ from "jquery";
  import { Plotly } from 'vue-plotly'

  import {
    blackTrashIconPath,
    linkChartsIconPath,
    unLinkChartsIconPath
  } from "./../../../../aeris-datavalidation-common/icons/index"

  import {
    AerisDataValidationServices,
  } from "./../../../../aeris-datavalidation-components"

  import AerisDatavalidationDeleteDialog from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-dialogs/components/aeris-datavalidation-deletedialog"

  const SELECTION_BORDER_COLOR = 'rgb(84,217,27)'
  const SELECTION_BACKGROUND_COLOR = 'rgb(33, 150, 243)'
  const TARGET_SELECTION_BORDER_COLOR = 'rgb(255, 152, 0)'

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
      instrumentInfos : {
        type : Object,
        default : () => null,
      },
      isMainChart : {
        type: Boolean,
        default: () => false
      },
      deleteStep: {
        type: Number,
        default: () => 0
      },
      isDeleteMode: {
        type: Boolean,
        default: () => false
      },
      isSecondChartEmpty : {
        type: Boolean,
        default: () => false
      },
      selection: {
        type: Object,
        default : () => null
      },
      linkedChartData : {
        type : Object,
        default : () => null
      },
      notifySelection: {
        type : Function,
        default: () => {}
      },
      notifyDeleteSelection: {
        type: Function,
        default : () => {}
      },
      defaultSelections: {
        type: Array,
        default: () => []
      },
      currentSession : {
        type : Object,
        default : () => null
      },
      currentInstrument : {
        type : Object,
        default : () => null
      },
      applyLinkedEffect: {
        type: Function,
        default: ()=> {}
      },
      isLinkedChartMode: {
        type: Boolean,
        default : false
      },
      switchLinkedMode: {
        type : Function,
        default: () => {}
      },
    },
    components: {
      Plotly,
      AerisDataValidationServices,
      AerisDatavalidationDeleteDialog
    },
    data() {
      return {
        componentKey: 0,
        data: [],
        flags: [],
        layout: {},
        chartId : "",
        selections : [],
        currentUrl : "",
        currentData: [],
        callBack : null,
        currentUuid : "",
        typeOfRequest: "",
        requestData: null,
        deleteAlert: false,
        modeBarButtons: [],
        deleteDialog: false,
        currentSelection : null,
        currentParameters : [],
        linkIcon: linkChartsIconPath,
      }
    },
    computed: {
      getChartId : function () {
        if(this.isMainChart)
          return "mainChart1"
        else
          return "mainChart2"
      },
    },
    watch: {
      isSecondChartEmpty : function () {
        if(this.isSecondChartEmpty)
          this.refresh()
      },
      deleteStep : function() {
        if(this.deleteStep) {
          this.deleteCurrentSelection(false, true)
        }
      },
      parameters : function (newParameters, oldsParameters) {
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
      linkedChartData: function () {
        if(this.linkedChartData && !this.isMainChart) {
          this.setLayoutAxisRange()
        }
      },
      instrumentInfos : function() {
        const cloneLayout = JSON.parse(JSON.stringify(this.layout))

        if(this.instrumentInfos) {
          let title =  {
            text : "Current data set - "+ this.instrumentInfos.resourceTitle.fr,
            x : 0.52,
            font : {
              color : 'rgb(13, 13, 13)',
              family : '"Open Sans", verdana, arial, sans-serif',
              size : 18,
            }
          }
          cloneLayout.title = title
          this.layout = cloneLayout
          //this.refresh()
        }
      },
    },
    mounted() {
      let paraName= this.parameters[0]
      this.chartId = this.getChartId
      this.initCurrentChart(paraName)
    },
    methods: {
      initCurrentChart : function(parameter) {
        if(this.currentSession && this.currentInstrument && parameter) {
          if(0 < this.parameters.length) {
            this.addNewParameter(parameter)

            this.initModeBar()

            if(this.isMainChart) {
              this.addEventsHandler()
            }
            this.setLayout()
            this.initDefaultSelections()
          }
        }
      },
      addNewParameter : function (parameter) {
        let uri, nextIndex
        this.typeOfRequest = "GET"
        this.callBack = (data) => {
          if(data) {
            this.updateChart(data.parameterData, parameter)
            if(this.nextDefaultParameterExist(parameter)) {
              nextIndex = this.getParameterIndex(parameter) + 1
              this.addNewParameter(this.parameters[nextIndex])
            }
          }
        }
        uri = "/instruments/" + parameter.name + "/" + this.startDate + "/" + this.endDate
        this.currentUrl = process.env.VUE_APP_ROOT_API + uri
      },
      updateChart: function(ParamData, parameter) {
        let dataContent = {}
        let currentKey= ""
        let currentContent = null
        let paramDataKeys = Object.keys(ParamData[0])

        ParamData.forEach((data) => {
          paramDataKeys.forEach((key) => {
            currentKey = key === "value" ? parameter.name : key
            if(! (currentKey in dataContent))
              dataContent[currentKey] = []

            currentContent = data[key]
            dataContent[currentKey].push(currentContent)
          });
        });
        paramDataKeys = [paramDataKeys[0], currentKey]
        this.setAxis(paramDataKeys, dataContent, parameter.color)
      },
      setAxis(paramDataKeys, dataContent, color) {
        const xaxis = paramDataKeys[0]
        const yaxis = paramDataKeys[1]

        this.data = [...this.data, {
          x: dataContent[xaxis],
          y: dataContent[yaxis],
          name: yaxis,
          line: {
            color: color,
            width: 2,
            dash: "solid",
            shape: "linear",
            simplify: true
          }
        }]
        this.refresh()
      },
      nextDefaultParameterExist : function(parameter) {
        let isDefault = false
        let parameterIndex
        if(parameter) {
          parameterIndex = this.getParameterIndex(parameter)
          if(parameterIndex !== -1)
            isDefault = parameterIndex + 1 < this.parameters.length
        }
        return isDefault
      },
      getParameterIndex : function(parameter) {
        let index = -1

        if(parameter) {
          index = this.parameters.indexOf(parameter)
        }
        return index
      },
      isSelectionEmpty : function() {
        return !(this.selection && this.currentSelection !== null /*|| this.isDeleteMode*/)
      },
      initDefaultSelections: function() {
        setTimeout(() => {
          if(this.defaultSelections) {
            this.defaultSelections.forEach((selection)=> {
              this.drawSelection(selection.startDate, selection.endDate, true)
            })
          }
        }, 1000)
      },
      isDateChange : function() {
        return (/*this.isDeleteMode || */this.selection.startDate !== this.currentSelection.x0 ||
            this.selection.endDate !== this.currentSelection.x1)
      },
      isDefaultSelection : function(startDate, endDate) {
        let selection
        if(this.defaultSelections) {
          for(let index in this.defaultSelections) {
            selection = this.defaultSelections[index]
            if(this.$root.getCleanDate(selection.startDate) === this.$root.getCleanDate(startDate) &&
                this.$root.getCleanDate(selection.endDate) === this.$root.getCleanDate(endDate)) {
              return true
            }
          }
        }
        return false
      },
      removeParameter : function (newParameters, oldsParameters) {
        let parameterName, targetParameterIndex

        let intersection = oldsParameters.filter(param => {
          return !newParameters.some(newParam => newParam.name === param.name)
        })

        parameterName = intersection[0].name
        targetParameterIndex = this.data.findIndex(param => param.name === parameterName)

        if(-1 < targetParameterIndex ) {
          this.data.splice(targetParameterIndex, 1)
          this.refresh()
        }
      },
      refresh : function() {
        this.currentUrl = ""
        //this.$forceUpdate()
        this.componentKey += 1;
        this.addEventsHandler()
      },
      addEventsHandler : function () {
        if(this.isMainChart) {
          this.$nextTick(() => {
            document.getElementById( this.chartId ).on( 'plotly_selected', this.addNewSelection)
            document.getElementById( this.chartId ).on( 'plotly_relayout', this.reLayoutHandler)
            this.addSelectionEvent()
          });
        }
      },
      reLayoutHandler : function(data) {
        if(data && data['xaxis.range[0]'] !== this.linkedChartData.startXaxis &&
            data['xaxis.range[1]'] !== this.linkedChartData.endXaxis) {
          this.addSelectionEvent()
          this.applyLinkedEffect(data)
        }
      },
      setLayoutAxisRange: function() {
        let cloneLayout
        if(this.linkedChartData.startXaxis && this.linkedChartData.endXaxis) {
          cloneLayout = JSON.parse(JSON.stringify(this.layout))
          cloneLayout.xaxis.range = [this.linkedChartData.startXaxis, this.linkedChartData.endXaxis]

          if(this.isLinkedChartMode)
            cloneLayout.xaxis.autorange = false
          else
            cloneLayout.xaxis.autorange = true

          this.layout = cloneLayout
          //this.$forceUpdate()
          this.componentKey +=1;
        }
      },
      addNewSelection : function(data) {
        //let goodInterval
        let startDate, endDate
        if(data /*&& 0 < data.points.length*/) {
          startDate = data.range.x[0]
          endDate = data.range.x[1]
          //goodInterval = this.getGoodInterval(startDate, endDate)
          //if(goodInterval !== null && !this.isSelectionExist(goodInterval.startDate, goodInterval.endDate)) {
          if(!this.isSelectionExist(startDate, endDate)) {
            this.drawSelection(startDate, endDate, false)
          }
        }
      },
      addSelectionEvent: function () {
        let children =  $('#' + this.chartId).find( '.shapelayer' )[2].children
        if(children) {
          $('document').ready(() => {
            children.forEach((selection, index) => {
              if($(selection).attr('id') === undefined) {
                $(selection).attr('id', 'selection' + index )
                $(selection).css("pointer-events", "bounding-box")
                $(document).on('click', '#selection' + index, this.switchSelection)
              }
            })
          });
        }
      },
      switchSelection : function(event) {
        let startDate, endDate
        let child = event.target
        let parent = child.parentNode
        let index = Array.prototype.indexOf.call(parent.children, child)

        if(index !== -1 && this.currentSelection !== this.selections[index]) {
          this.setCurrentSelection(index, false)
        } else if(index !== -1 && this.currentSelection === this.selections[index]) {
          startDate = this.currentSelection.x0
          endDate = this.currentSelection.x1
          this.notifySelection(startDate, endDate)
        }
      },
      setCurrentSelection : function(index, isDefault) {
        let startDate, endDate
        const cloneLayout = JSON.parse(JSON.stringify(this.layout))

        this.clearCurrentSelection()
        this.currentSelection = this.selections[index]
        this.currentSelection.line.color = TARGET_SELECTION_BORDER_COLOR
        startDate = this.currentSelection.x0
        endDate = this.currentSelection.x1
        cloneLayout.shapes = this.selections
        this.layout = cloneLayout
        this.refresh()

        if(!isDefault)
          this.notifySelection(startDate, endDate)
      },
      isSelectionExist : function (startDate, endDate) {
        let selection
        for(let index in this.selections) {
          selection = this.selections[index]
          if(this.$root.getCleanDate(selection.x0) === this.$root.getCleanDate(startDate) &&
              this.$root.getCleanDate(selection.x1) === this.$root.getCleanDate(endDate))
            return true
        }
        return false
      },
      getGoodInterval: function(startDate, endDate) {
        let intervals
        let result = {startDate : startDate, endDate: endDate}

        if(this.selections) {
          for(let index in this.selections) {
            intervals = this.getIntervalDates(this.selections[index], result)

            if(intervals.x3 <= intervals.x1 && intervals.x1 <= intervals.x4 &&
                intervals.x4 <= intervals.x2) { //LF interval

              if(intervals.x1 === intervals.x3 && intervals.x2 === intervals.x4) {
                result =  {startDate : startDate, endDate: endDate}
              } else {
                result = {startDate : startDate, endDate: this.selections[index].x0}
              }
            } else if(intervals.x1 <= intervals.x3 && intervals.x3 < intervals.x2 &&
                intervals.x2 < intervals.x4) {//RG interval
              result = {startDate : this.selections[index].x1, endDate: endDate}
            }
          }
        }

        return result
      },
      getIntervalDates : function(selection, result) {
        return {
          x1: new Date(selection.x0),
          x2: new Date(selection.x1),
          x3: new Date(result.startDate),
          x4: new Date(result.endDate)
        }
      },
      drawSelection : function(startDate, endDate, isDefault) {
        const cloneLayout = JSON.parse(JSON.stringify(this.layout))
        let newStartDate = this.$root.takeOfDateMilliseconds(startDate)
        let newEndDate = this.$root.takeOfDateMilliseconds(endDate)

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
              color : TARGET_SELECTION_BORDER_COLOR,
              dash : 'dot'
            },
            xsizemode : 'scaled',
            ysizemode : 'scaled',
            xref : 'x',
            x0 : newStartDate,
            x1 : newEndDate,
            yref : 'paper',
            y0 : 0,
            y1 : 1
          }
        ]
        cloneLayout.shapes = this.selections
        this.layout = cloneLayout
        this.currentSelection = this.selections[this.selections.length-1]
        //this.refresh()
        this.addSelectionEvent()
        if(!isDefault)
          this.notifySelection(startDate, endDate)
      },
      setCurrentSelectionPeriod : function(newStartDate, newEndDate) {
        let cloneLayout
        if(newStartDate && newEndDate) {
          this.currentSelection.x0 = newStartDate
          this.currentSelection.x1 = newEndDate
          cloneLayout = JSON.parse(JSON.stringify(this.layout))
          cloneLayout.shapes = this.selections
          this.layout = cloneLayout
          this.refresh()
        }
      },
      isSwitchSelection: function(startDate, endDate) {
        if(startDate && endDate && this.currentSelection) {
          return (this.$root.getCleanDate(this.currentSelection.x0) !== this.$root.getCleanDate(startDate) &&
              this.$root.getCleanDate(this.currentSelection.x1) !== this.$root.getCleanDate(endDate))
        }

        return false
      },
      getSelectionIndex: function(startDate, endDate) {
        let selection
        if(this.selections) {
          for(let index in this.selections) {
            selection = this.selections[index]
            if(this.$root.getCleanDate(selection.x0) === this.$root.getCleanDate(startDate) &&
                this.$root.getCleanDate(selection.x1) === this.$root.getCleanDate(endDate)) {
              return index
            }
          }
        }
        return -1
      },
      clearCurrentSelection : function() {
        this.selections.forEach((selection) => {
          if(selection.line.color === TARGET_SELECTION_BORDER_COLOR) {
            selection.line.color = SELECTION_BORDER_COLOR
          }
        })
      },
      deleteTargetSelection : function() {
        let index
        if(this.currentSelection === null) {
          this.deleteAlert = true
          setTimeout(() => {
            this.deleteAlert = false
          }, 1000)
        } else {
          index = this.getTargetSelectionIndex()
          if(index !== -1) {
            this.deleteDialog = true
            this.notifyDeleteSelection(true)
          } else {
            this.deleteCurrentSelection(true, false)
          }
        }
      },
      validateDelete : function () {
        let targetIndex = this.getTargetSelectionIndex()
        if(targetIndex) {
          this.currentSession.sessionSelections.splice(targetIndex, 1)
          this.updateSession()
          this.deleteDialog = false
          this.resetDeleteDefaultState()
        }
      },
      resetDeleteDefaultState: function () {
        setTimeout(() => {
          this.notifyDeleteSelection(false)
        }, 500)
      },
      cancelDelete : function () {
        this.deleteDialog = false
        this.notifyDeleteSelection(false)
      },
      getTargetSelectionIndex : function () {
        let targetIndex = -1
        let selectionCurs, selectionCursStartDate, selectionCursEndDate
        for(let index in this.currentSession.sessionSelections) {
          selectionCurs = this.currentSession.sessionSelections[index]
          selectionCursStartDate = this.$root.takeOfDateMilliseconds(selectionCurs.startDate)
          selectionCursEndDate = this.$root.takeOfDateMilliseconds(selectionCurs.endDate)

          if(selectionCursStartDate === this.currentSelection.x0 &&
              selectionCursEndDate === this.currentSelection.x1) {
            targetIndex = index
            break
          }
        }
        return targetIndex
      },
      updateSession : function() {
        this.typeOfRequest = "PUT"
        this.requestData = this.currentSession
        this.callBack = (selection) => {
          if(selection) {
            this.deleteCurrentSelection(true, false)
          }
          this.currentUrl=""
        }
        this.currentUrl = process.env.VUE_APP_ROOT_API + "/sessions/update"
      },
      deleteCurrentSelection :function(isChartEvent, isDeleteSignal) {
        let startDate, endDate
        const cloneLayout = JSON.parse(JSON.stringify(this.layout))

        if(this.currentSelection !== null) {
          startDate = this.currentSelection.x0
          endDate = this.currentSelection.x1
          this.selections = this.selections.filter((selection) => {
            return selection.x0 !== startDate  && selection.x1 !== endDate
          })
          cloneLayout.shapes = this.selections
          this.layout = cloneLayout
          startDate = ""
          endDate = ""
          this.refresh()
          this.currentSelection = null
          if(!isDeleteSignal && isChartEvent === undefined)
            this.notifySelection(startDate, endDate)
        }

      },
      initModeBar : function() {
        if(this.isMainChart) {
          this.modeBarButtons = [
            [
              'select2d',
              {
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
              },
              {
                name: "Link chart",
                icon: {
                  'width': 500,
                  'height': 500,
                  'path': this.linkIcon,
                },
                click: () => {
                  this.enableLinkedMode()
                }
              },
              'zoomIn2d',
              'zoomOut2d',
            ],
            [
              'pan2d',
              'resetScale2d',
              'sendDataToCloud',
            ]
          ]
        } else {
          this.modeBarButtons = [
            [
              'zoomIn2d',
              'zoomOut2d',
              'pan2d',
              'resetScale2d',
            ],
          ]
        }
      },
      enableLinkedMode : function() {
        let path =  $('#' + this.chartId).find( "a[data-title='Link chart']").find("path")

        if(this.isMainChart && this.linkIcon === linkChartsIconPath && path) {
          this.linkIcon= unLinkChartsIconPath
        } else  if(this.isMainChart && this.linkIcon === unLinkChartsIconPath) {
          this.linkIcon= linkChartsIconPath
        }

        if(path)
          path.attr('d', this.linkIcon)

        this.switchLinkedMode()
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
          modebar: this.getLayoutModeBarConf()
        }
      },
      getLayoutTitle: function() {
        return {
          text : "Current data set - "+ this.instrumentInfos.resourceTitle.fr,
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
          //rangeselector : this.getXaxisRangeselector()
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
          orientation : "v",
          x : 1.02,
          y : 0.8,
          borderwidth : 1,
          bordercolor : 'rgb(13, 12, 12)',
          xanchor: "auto",
          yanchor: "auto",
        }
      },
      getLayoutModeBarConf : function () {
        return {
          orientation: 'h',
          color: 'rgba(68, 68, 68, 0.3)',
          bgcolor: 'rgba(255, 255, 255, 0.5)',
          activecolor: "rgba(212, 10, 10, 0.7)",
        }
      }
    }
  }
</script>

<style scoped>

</style>