<template>
  <div>
    <AerisDataValidationServices
        :url="currentUrl"
        :callBack="callBack"
        :requestData="requestData"
        :typeOfRequest="typeOfRequest"
    >
      <v-alert type="error" v-if="deleteAlert">
        {{$t('session.choose_selection_message')}}
      </v-alert>
      <v-row justify="center">
        <v-col cols="12">
          <Plotly
              :x="Date"
              :data="data"
              :id="getChartId"
              :layout="layout"
              :scrollZoom="true"
              :displaylogo="false"
              :displayModeBar="true"
              :responsive="true"
              :modeBarButtons=" modeBarButtons"
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
    AerisDataValidationServices,
  } from "./../../../../aeris-datavalidation-components"

  const SELECTION_BORDER_COLOR = 'rgb(84,217,27)'
  const SELECTION_BACKGROUND_COLOR = 'rgb(33, 150, 243)'
  const TARGET_SELECTION_BORDER_COLOR = 'rgb(255, 152, 0)'

  const TRASH_ICON = {
    'width': 500,
    'height': 500,
    'path': "M296 432h16a8 8 0 0 0 8-8V152a8 8 0 0 0-8-8h-16a8 8 0 0 0-8 8v272a8 8 0 0 0 8 8zm-160 0h16a8 8 0 0 0 8-8V1" +
            "52a8 8 0 0 0-8-8h-16a8 8 0 0 0-8 8v272a8 8 0 0 0 8 8zM440 64H336l-33.6-44.8A48 48 0 0 0 264 0h-80a48 48 0 0" +
            " 0-38.4 19.2L112 64H8a8 8 0 0 0-8 8v16a8 8 0 0 0 8 8h24v368a48 48 0 0 0 48 48h288a48 48 0 0 0 48-48V96h24a8 " +
            "8 0 0 0 8-8V72a8 8 0 0 0-8-8zM171.2 38.4A16.1 16.1 0 0 1 184 32h80a16.1 16.1 0 0 1 12.8 6.4L296 64H152zM384 " +
            "464a16 16 0 0 1-16 16H80a16 16 0 0 1-16-16V96h320zm-168-32h16a8 8 0 0 0 8-8V152a8 8 0 0 0-8-8h-16a8 8 0 0 0-8 " +
            "8v272a8 8 0 0 0 8 8z",
  }

  const LINK_ICON = {
    'width': 500,
    'height': 500,
    'path':"M314.222 197.78c51.091 51.091 54.377 132.287 9.75 187.16-6.242 7.73-2.784 3.865-84.94 86.02-54.696 54.696-143" +
        ".266 54.745-197.99 0-54.711-54.69-54.734-143.255 0-197.99 32.773-32.773 51.835-51.899 63.409-63.457 7.463-7.452 20" +
        ".331-2.354 20.486 8.192a173.31 173.31 0 0 0 4.746 37.828c.966 4.029-.272 8.269-3.202 11.198L80.632 312.57c-32.755 " +
        "32.775-32.887 85.892 0 118.8 32.775 32.755 85.892 32.887 118.8 0l75.19-75.2c32.718-32.725 32.777-86.013 0-118.79a83" +
        ".722 83.722 0 0 0-22.814-16.229c-4.623-2.233-7.182-7.25-6.561-12.346 1.356-11.122 6.296-21.885 14.815-30.405l4.375-" +
        "4.375c3.625-3.626 9.177-4.594 13.76-2.294 12.999 6.524 25.187 15.211 36.025 26.049zM470.958 41.04c-54.724-54.745-14" +
        "3.294-54.696-197.99 0-82.156 82.156-78.698 78.29-84.94 86.02-44.627 54.873-41.341 136.069 9.75 187.16 10.838 10.838 " +
        "23.026 19.525 36.025 26.049 4.582 2.3 10.134 1.331 13.76-2.294l4.375-4.375c8.52-8.519 13.459-19.283 14.815-30.405.621" +
        "-5.096-1.938-10.113-6.561-12.346a83.706 83.706 0 0 1-22.814-16.229c-32.777-32.777-32.718-86.065 0-118.79l75.19-75.2c3" +
        "2.908-32.887 86.025-32.755 118.8 0 32.887 32.908 32.755 86.025 0 118.8l-45.848 45.84c-2.93 2.929-4.168 7.169-3.202 11.1" +
        "98a17.31 173.31 0 0 1 4.746 37.828c.155 10.546 13.023 15.644 20.486 8.192 11.574-11.558 30.636-30.684 63.409-63.457 54.73" +
        "3-54.735 54.71-143.3-.001-197.991z",
  }

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
      deleteStep: {
        type: Number,
        default: () => 0
      },
      isDeleteMode: {
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
      AerisDataValidationServices
    },
    data() {
      return {
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
        currentSelection : null,
        currentParameters : [],
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
      deleteStep : function() {
        if(this.deleteStep === 2) {
          this.deleteCurrentSelection()
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
            } else {
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
      dataInfo : function() {
        if(this.dataInfo) {
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
        return !(this.selection && this.currentSelection !== null || this.isDeleteMode)
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
        return (this.isDeleteMode || this.selection.startDate !== this.currentSelection.x0 ||
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
        this.$forceUpdate()
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
          this.$forceUpdate()
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
        if(startDate && endDate) {
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
            this.currentSession.sessionSelections.splice(index, 1)
            this.updateSession()
          } else {
            this.deleteCurrentSelection(true)
          }
        }
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
            this.deleteCurrentSelection(true)

          }
          this.currentUrl=""
        }
        this.currentUrl = process.env.VUE_APP_ROOT_API + "/sessions/update"
      },
      deleteCurrentSelection :function(isChartEvent) {
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
          if(this.deleteStep !== 2 && isChartEvent === undefined)
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
                icon: TRASH_ICON,
                click: () => {
                  this.deleteTargetSelection()
                }
              },
              {
                name: "Link chart",
                icon: LINK_ICON,
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
        //
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
          shapes : this.selections
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
    }
  }
</script>

<style scoped>

</style>