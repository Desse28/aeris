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
          <div :id="getChartId" :key="componentKey" ref="graph"></div>
        </v-col>
      </v-row>
    </AerisDataValidationServices>
  </div>
</template>

<script>
  import $ from "jquery";
  import Plotly from 'plotly.js-dist'
  import {
    blackTrashIconPath,
    linkChartsIconPath,
    unLinkChartsIconPath
  } from "./../../../../aeris-datavalidation-common/icons/index"

  import {
    AerisDataValidationServices,
  } from "./../../../../aeris-datavalidation-components"

  import AerisDatavalidationDeleteDialog from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-dialogs/components/aeris-datavalidation-deletedialog"

  const SESSION_UPDATE_PATH = "/sessions/update"

  const SELECTION_BORDER_COLOR = 'rgb(84,217,27)'
  const SELECTION_BACKGROUND_COLOR = 'rgb(33, 150, 243)'
  const TARGET_SELECTION_BORDER_COLOR = 'rgb(255, 152, 0)'

  export default {
    name: "aeris-datavalidation-chart",
    props: {
      charts : {
        type : Object
      },
      chartName : {
        type : String
      },
      isMainChart : {
        type : Boolean,
        default : false
      },
      parameters: {
        type : Array,
        default : () => [],
      },
      instrumentInfos : {
        type : Object,
        default : () => null,
      },
      deleteStep: {
        type: Number,
        default: () => 0
      },
      secondChartsParameters : {
        type: Array
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
      }
    },
    components: {
      AerisDataValidationServices,
      AerisDatavalidationDeleteDialog
    },
    data() {
      return {
        data: [],
        flags: [],
        layout: {},
        config : {},
        pointNum : 0,
        componentKey: 1,
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
      }
    },
    computed : {
      getChartId : function () {
        if(this.chartName === "")
          return "defaultChart"
        return this.chartName.replace(' ', '')
      },
      getPointNumber : function() {
        let pointNum = []
        if(this.parameters && this.pointNum) {
          pointNum = Array.from(Array(this.parameters.length).keys(),
              i => {return { curveNumber:i, pointNumber : this.pointNum }})
        }
        return pointNum
      },
      getStartXaxis : function() {
        let startXaxis = this.data ? this.data[0].x[0] : ""
        return startXaxis
      },
      getEndXaxis : function() {
        let len = this.data[0].x.length
        let endXaxis = this.data ? this.data[0].x[len-1] : ""
        return endXaxis
      },
      getLinkChartIcon : function() {
        if(this.isLinkedChartMode)
          return linkChartsIconPath
        else
          return unLinkChartsIconPath
      }
    },
    watch: {
      secondChartsParameters : function (newParameters, oldParameters) {
        if(this.secondChartsParameters.length === 0 || (newParameters.length === 1 && oldParameters.length === 0)) {
          this.refresh()
          this.initCurrentChart(null)
        }
      },
      deleteStep : function() {
        if(this.deleteStep && this.isMainChart) {
          this.deleteCurrentSelection(false, true)
          this.addSelectionEventHandler()
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
      linkedChartData: function (newLinkedChartData, oldLinkedChartData) {
        if(this.linkedChartData && !this.isMainChart) {
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
          Plotly.Fx.hover(document.getElementById( this.getChartId ), [])
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
    mounted() {
      this.initCurrentChart(this.parameters[0])
    },
    updated() {
      this.addSelectionEventHandler()
    },
    methods: {
      initCurrentChart : function(parameter) {
        $('document').ready(() => {
          this.initConfig(this.getLinkChartIcon)
          this.initLayout()
          Plotly.newPlot(document.getElementById( this.getChartId ), this.data, this.layout, this.config)

          if(this.currentSession && this.currentInstrument) {
            if(parameter)
              this.addNewParameter(parameter)

            if(this.isMainChart)
              this.drawDefaultSelections()

            this.addEventsHandler()
          }
        })
      },
      addNewParameter : function (parameter) {
        let uri, nextIndex
        let {startDate, endDate} = this.currentInstrument

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
        uri = "/instruments/" + parameter.name + "/" + startDate + "/" + endDate
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
        const data = [{
          x: dataContent[xaxis],
          y: dataContent[yaxis],
          name: yaxis,
          line: {
            color: color,
            width: 2,
            dash: "solid",
            shape: "linear",
            simplify: true
          },
        }]
        Plotly.addTraces(document.getElementById( this.getChartId ), data)
        this.currentSession.charts = Object.values(this.charts)
        this.updateSessionState()
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
        return !(this.selection && this.currentSelection !== null)
      },
      drawDefaultSelections: function() {
        let currentChart = this.charts[this.chartName]
        if(currentChart) {
          $('document').ready(() => {
            currentChart.selections.forEach((selection)=> {
              this.drawSelection(selection.startDate, selection.endDate, true)
            })
          })
        }
      },
      isDateChange : function() {
        return (this.selection.startDate !== this.currentSelection.x0 ||
            this.selection.endDate !== this.currentSelection.x1)
      },
      removeParameter : function (newParameters, oldsParameters) {
        let parameterName, targetParameterIndex

        let intersection = oldsParameters.filter(param => {
          return !newParameters.some(newParam => newParam.name === param.name)
        })

        parameterName = intersection[0].name
        targetParameterIndex = this.data.findIndex(param => param.name === parameterName)

        if(-1 < targetParameterIndex ) {
          Plotly.deleteTraces(document.getElementById( this.getChartId ), targetParameterIndex)
        }
        this.currentSession.charts = Object.values(this.charts)
        this.updateSessionState()
      },
      refresh : function() {
        if(this.componentKey%2 === 0)
          this.componentKey = 1
        else
          this.componentKey = 2
      },
      addEventsHandler : function () {
        this.$nextTick(() => {
          $('document').ready(() => {
            if(this.getChartId) {
              if(this.isMainChart) {
                document.getElementById( this.getChartId ).on( 'plotly_hover', this.hoverHandler)
                document.getElementById( this.getChartId ).on( 'plotly_selected', this.addNewSelection)
              }
              document.getElementById( this.getChartId ).on( 'plotly_relayout', this.reLayoutHandler)
              //this.addSelectionEventHandler()
            }
          })
        });
      },
      hoverHandler : function(eventdata) {
        let points = eventdata.points[0]
        if(points && this.isLinkedChartMode && this.isMainChart)
          this.applyLinkedEffect(eventdata)
      },
      applyLinkedHoverEffect : function (points) {
        this.pointNum = points.pointNumber;
        Plotly.Fx.hover(document.getElementById( this.getChartId ), this.getPointNumber)
      },
      reLayoutHandler : function(data) {
        if(data && data['xaxis.range[0]'] !== this.linkedChartData.startXaxis &&
            data['xaxis.range[1]'] !== this.linkedChartData.endXaxis && this.isMainChart && this.isLinkedChartMode) {
          this.applyLinkedEffect(data)
        } else if(data['xaxis.range[0]'] && data['xaxis.range[1]']) {
          this.charts[this.chartName].startXaxis = this.$root.getSpringDateFormat(data['xaxis.range[0]'])
          this.charts[this.chartName].endXaxis = this.$root.getSpringDateFormat(data['xaxis.range[1]'])
          this.currentSession.charts = Object.values(this.charts)
          this.updateSessionState()
        }
      },
      updateSessionState : function () {
        this.typeOfRequest = "PUT"
        this.requestData = this.currentSession
        this.callBack = (data) => {
          this.currentUrl = ""
          if(data) {
            console.info(data)
          }
        }
        this.currentUrl = process.env.VUE_APP_ROOT_API + SESSION_UPDATE_PATH
      },
      setLayoutAxisRange : function() {
        if(this.linkedChartData.startXaxis && this.linkedChartData.endXaxis) {
          this.layout.xaxis.range = [this.linkedChartData.startXaxis, this.linkedChartData.endXaxis]
          this.layout.xaxis.autorange =  !this.isLinkedChartMode
          this.charts[this.chartName].startXaxis = this.$root.getSpringDateFormat(this.linkedChartData.startXaxis)
          this.charts[this.chartName].endXaxis = this.$root.getSpringDateFormat(this.linkedChartData.endXaxis)
          Plotly.relayout(document.getElementById( this.getChartId ), this.layout)
          this.currentSession.charts = Object.values(this.charts)
          this.updateSessionState()
        }
      },
      addNewSelection : function(data) {
        let startDate, endDate
        if(data) {
          startDate = data.range.x[0], endDate = data.range.x[1]
          if(!this.isSelectionExist(startDate, endDate))
            this.drawSelection(startDate, endDate, false)
        }
      },
      isSelectionExist : function (startDate, endDate) {
        return this.selections.some((selection) => {
          return (this.$root.isSameDate(selection.x0, startDate) &&
              this.$root.isSameDate(selection.x1, endDate))
        })
      },
      drawSelection : function(startDate, endDate, isDefault) {
        this.turnOffCurrentSelection()
        this.selections = [
          ...this.selections,
          {
            visible : true,
            type : 'rect',
            editable : false,
            layer : 'below',
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
            x0 : this.$root.getCleanDate(startDate),
            x1 : this.$root.getCleanDate(endDate),
            yref : 'paper',
            y0 : 0,
            y1 : 1
          }
        ]

        this.currentSelection = this.selections[this.selections.length-1]
        Plotly.relayout(document.getElementById( this.getChartId ), { shapes : this.selections})
        this.addSelectionEventHandler()
        if(!isDefault)
          this.notifySelection(startDate, endDate)
      },
      addSelectionEventHandler : function () {
        this.$nextTick(() => {
          let children = $('#' + this.getChartId).find( '.shapelayer' )[0].children

          $('document').ready(() => {
            if(children) {
              children.forEach((selection, index) => {
                if($(selection).attr('id') === undefined) {
                  $(selection).css("pointer-events", "bounding-box")
                  $(selection).attr('id', 'selection' + index )
                  $(document).on('click', '#' + 'selection' + index,this.switchSelection)
                }
              })
            }
          })
        });

      },
      switchSelection : function(event) {
        let startDate, endDate
        let index = $(event.currentTarget).attr('data-index')
        let targetSelection = this.selections[index]
        startDate = targetSelection.x0
        endDate = targetSelection.x1
        this.currentSelection = targetSelection
        this.notifySelection(startDate, endDate)
        this.turnOnCurrentSelection(index)
        Plotly.relayout(document.getElementById( this.getChartId ), { shapes : this.selections})
        this.addSelectionEventHandler()
      },
      setCurrentSelection : function(index, isDefault) {
        let startDate, endDate
        const cloneLayout = JSON.parse(JSON.stringify(this.layout))
        this.turnOffCurrentSelection()
        this.currentSelection = this.selections[index]
        this.currentSelection.line.color = TARGET_SELECTION_BORDER_COLOR
        startDate = this.currentSelection.x0
        endDate = this.currentSelection.x1
        cloneLayout.shapes = this.selections
        this.layout = cloneLayout

        if(!isDefault)
          this.notifySelection(startDate, endDate)
      },
      setCurrentSelectionPeriod : function(newStartDate, newEndDate) {
        if(newStartDate && newEndDate) {
          this.currentSelection.x0 = newStartDate
          this.currentSelection.x1 = newEndDate
          Plotly.relayout(document.getElementById( this.getChartId ), { shapes : this.selections})
          this.addSelectionEventHandler()
        }
      },
      isSwitchSelection: function(startDate, endDate) {
        if(startDate && endDate && this.currentSelection) {
          return (!this.$root.isSameDate(this.currentSelection.x0, startDate) &&
                  !this.$root.isSameDate(this.currentSelection.x1, endDate))
        }
        return false
      },
      getSelectionIndex: function(startDate, endDate) {
        return this.selections.findIndex((selection) => {
          return (this.$root.isSameDate(selection.x0, startDate) &&
              this.$root.isSameDate(selection.x1, endDate))
        })
      },
      turnOnCurrentSelection : function(targetIndex) {
        if(this.selections && targetIndex) {
          this.turnOffCurrentSelection()
          this.selections.forEach((selection, index) => {
            if(index === Number(targetIndex)) {
              selection.line.color = TARGET_SELECTION_BORDER_COLOR
            }
          })
        }
      },
      turnOffCurrentSelection : function() {
        this.selections.forEach((selection) => {
          if(selection.line.color === TARGET_SELECTION_BORDER_COLOR) {
            selection.line.color = SELECTION_BORDER_COLOR
          }
        })
      },
      deleteTargetSelection : function() {
        let index
        let selections = this.currentSession.sessionSelections
        if(this.currentSelection === null) {
          this.deleteAlert = true
          setTimeout(() => {
            this.deleteAlert = false
          }, 1000)
        } else {
          index = this.$root.getTargetSelectionIndex(selections, this.currentSelection)
          if(index !== -1) {
            this.deleteDialog = true
            this.notifyDeleteSelection(true)
          } else {
            this.deleteCurrentSelection(true, false)
          }
        }
      },
      validateDelete : function () {
        let selections = this.currentSession.sessionSelections
        let targetIndex = this.$root.getTargetSelectionIndex(selections, this.currentSelection)
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
      deleteCurrentSelection :function(isChartEvent, isDeleteSignal) {
        let startDate, endDate

        if(this.currentSelection !== null) {
          startDate = this.currentSelection.x0
          endDate = this.currentSelection.x1
          this.selections = this.selections.filter((selection) => {
            return selection.x0 !== startDate  && selection.x1 !== endDate
          })
          this.submitDeleteSelection(startDate, endDate)
          startDate = ""
          endDate = ""
          this.currentSelection = null
          $("#" + this.getChartId + " .select-outline").remove()
          Plotly.relayout(document.getElementById( this.getChartId ), { shapes : this.selections})
          if(!isDeleteSignal && isChartEvent === undefined)
            this.notifySelection(startDate, endDate)
        }
      },
      submitDeleteSelection : function(startDate, endDate) {
        let mainChart, selectionsBeforeLen
        if(startDate && endDate) {
          mainChart = this.currentSession.charts[0]
          selectionsBeforeLen =  mainChart.selections.length

          mainChart.selections = mainChart.selections.filter((selection) => {
            return (!this.$root.isSameDate(selection.startDate, startDate) && !this.$root.isSameDate(selection.endDate, endDate))
          })

          if(mainChart.selections.length < selectionsBeforeLen)
            this.updateSessionState()
        }
      },
      initConfig : function(linkIcon) {
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
                  'path': linkIcon,
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
              {
                name: "Reset axes",
                icon:  Plotly.Icons.home,
                click: () => {
                  this.resetAxes()
                }
              },
              'sendDataToCloud',
            ]
          ]
        } else {
          this.modeBarButtons = [
            [
              'zoomIn2d',
              'zoomOut2d',
              'pan2d',
              'toggleHover',
              {
                name: "Reset axes",
                icon:  Plotly.Icons.home,
                click: () => {
                  this.resetAxes()
                }
              },
            ],
          ]
        }
        this.config = {
          modeBarButtons: this.modeBarButtons,
          scrollZoom : true,
          displaylogo : false,
          displayModeBar : true,
          plotlyServerURL : "https://chart-studio.plotly.com"
        }
      },
      enableLinkedMode : function() {
        let icon
        if(this.isMainChart) {
          this.currentSession.linkedChartMode = !this.isLinkedChartMode
          icon = !this.isLinkedChartMode ? linkChartsIconPath : unLinkChartsIconPath
          this.switchLinkedMode()
          this.initConfig(icon)
          this.upDateModeBarIcon()//need two call for update icon
          this.upDateModeBarIcon()//second call
          this.updateSessionState()
        }
      },
      upDateModeBarIcon : function () {
        this.config['scrollZoom'] = this.config['scrollZoom'] !== true
        this.config['displaylogo'] =  !this.config['displaylogo']
        this.config['displayModeBar'] = !this.config['displayModeBar']
        Plotly.react(document.getElementById( this.getChartId ), this.data, this.layout, this.config)
      },
      initLayout: function() {
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
      resetAxes : function () {
        if(this.getStartXaxis && this.getEndXaxis) {
          this.layout.xaxis.range = [this.getStartXaxis, this.getEndXaxis]
          this.charts[this.chartName].startXaxis = this.getStartXaxis
          this.charts[this.chartName].endXaxis = this.getEndXaxis
          this.currentSession.charts = Object.values(this.charts)
          this.updateSessionState()
          Plotly.relayout(document.getElementById( this.getChartId ), this.layout)
          if(this.isMainChart)
            this.applyLinkedEffect("Reset axes")
        }
      },
      getLayoutTitle: function() {
        const {resourceTitle} =  this.instrumentInfos ? this.instrumentInfos : ""
        let title = resourceTitle ? "Current data set - " + resourceTitle.fr : ""
        return {
          text : title,
          x : 0.52,
          font : {
            color : 'rgb(13, 13, 13)',
            family : '"Open Sans", verdana, arial, sans-serif',
            size : 18,
          }
        }
      },
      getLayoutXaxis: function(value, type) {
        const defaultDate = "1970-01-01T00:00:00.000+00:00"
        let startXaxis = "", endXaxis = ""

        if(this.charts[this.chartName]) {
          startXaxis = this.charts[this.chartName].startXaxis
          endXaxis = this.charts[this.chartName].endXaxis
          startXaxis = this.$root.isSameDate(startXaxis, defaultDate) ? "" : startXaxis
          endXaxis = this.$root.isSameDate(endXaxis, defaultDate) ? "" : endXaxis
        }

        return {
          type: type,
          range : [startXaxis, endXaxis],
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
    }
  }
</script>

<style scoped>

</style>