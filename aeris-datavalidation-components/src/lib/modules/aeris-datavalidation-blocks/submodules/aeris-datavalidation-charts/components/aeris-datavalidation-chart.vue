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
          <div :id="getChartId" :key="componentKey"></div>
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
        linkIcon: linkChartsIconPath,
      }
    },
    computed : {
      getChartId : function () {
        if(this.chartName === "")
          return "defaultChart"
        return this.chartName.replace(' ', '')
      }
    },
    watch: {
      secondChartsParameters : function (newParameters, oldParameters) {
        console.log("Test secondChartsParameters : ", newParameters, " old : ", oldParameters)
        if(this.secondChartsParameters.length === 0 || (newParameters.length === 1 && oldParameters.length === 0)) {
          this.refresh()
          $('document').ready(() => {
            this.initModeBar()
            this.initLayout()
            Plotly.newPlot(document.getElementById(this.getChartId), this.data, this.layout,
                {modeBarButtons: this.modeBarButtons, scrollZoom: true, displaylogo: false, displayModeBar: true})
          })
        }
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
    updated() {
      this.currentUrl = ""
      this.setDefaultLinkIcon()
      this.addEventsHandler()
    },
    mounted() {
      console.log("Test mounted : ", this.chartName === "")
      this.initCurrentChart(this.parameters[0])

    },
    methods: {
      initCurrentChart : function(parameter) {
        $('document').ready(() => {
          this.initModeBar()
          this.initLayout()
          Plotly.newPlot(document.getElementById( this.getChartId ), this.data, this.layout,
              {modeBarButtons: this.modeBarButtons, scrollZoom : true, displaylogo:false, displayModeBar:true})

          if(this.currentSession && this.currentInstrument) {
            if(parameter)
              this.addNewParameter(parameter)

            /*if(this.isMainChart) {
              this.drawDefaultSelections()
            }
            this.addEventsHandler()*/
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
          //this.data.splice(targetParameterIndex, 1)
          console.log("Test index : ", targetParameterIndex)
          Plotly.deleteTraces(document.getElementById( this.getChartId ), targetParameterIndex);
          //this.refresh()
        }
      },
      refresh : function() {
        if(this.componentKey%2 === 0)
          this.componentKey = 1
        else
          this.componentKey = 2
      },
      addEventsHandler : function () {
        //if(this.isMainChart) {
          this.$nextTick(() => {
            if(this.getChartId) {
              console.log("ID : " ,  this.getChartId)
              //document.getElementById( this.getChartId ).on( 'plotly_hover', this.hoverHandler)
              //document.getElementById( this.getChartId ).on( 'plotly_selected', this.addNewSelection)
              //document.getElementById( this.chartName ).on( 'plotly_relayout', this.reLayoutHandler)
              //this.addSelectionEventHandler()
            }
          });
        //}
      },
      hoverHandler : function(data) {
        console.log("Test handler : ", data, ", ID : ", Plotly.Fx.hover)
        if(this.chartName !== 'Secondarychart') {
          $('document').ready(() => {
            $('#Secondarychart').trigger('plotly_hover', data);
            console.log("current : ", $('#Secondarychart'))
            //this.$emit('hover', data.points, this.getChartId);
            //$('#Secondarychart').trigger('mouseenter')
            //console.log("Test hoverHandler : ", $('#Secondarychart'))
          })
        }
        //console.log("Test getData : ", $('#' + this.chartName).find( '.hoverinfo' ))
        //let points = eventdata.points[0], pointNum = points.pointNumber
        //console.log("Test point : ", points, ", pointNum : ", pointNum)
        /*let hoverInfo = document.getElementById('hoverinfo')
        let infotext = data.points.map(function(d){
          return (d.data.name+': x= '+d.x+', y= '+d.y.toPrecision(3));
        });*/
        //hoverInfo.innerHTML = infotext.join('<br/>');
      },
      reLayoutHandler : function(data) {
        if(data && data['xaxis.range[0]'] !== this.linkedChartData.startXaxis &&
            data['xaxis.range[1]'] !== this.linkedChartData.endXaxis) {
          this.addSelectionEventHandler()
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
          this.componentKey +=1;
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
        const cloneLayout = JSON.parse(JSON.stringify(this.layout))

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
        cloneLayout.shapes = this.selections
        this.layout = cloneLayout
        this.currentSelection = this.selections[this.selections.length-1]
        this.addSelectionEventHandler()
        if(!isDefault)
          this.notifySelection(startDate, endDate)
      },
      addSelectionEventHandler : function () {
        let children
        $('document').ready(() => {
          children = $('#' + this.getChartId).find( '.shapelayer' )[0].children
          children.forEach((selection, index) => {
            if($(selection).attr('id') === undefined) {
              $(selection).attr('id', 'selection' + index )
              $(selection).css("pointer-events", "bounding-box")
              $(document).on('click', '#selection' + index, this.switchSelection)
              //$(document).on('hover', '#selection' + index, this.hoverSelectionHandler)
            }
          })
        });
      },
      hoverSelectionHandler : function() {
        console.log("Test selection hover : ")
      },
      switchSelection : function(event) {
        let startDate, endDate
        let child = event.target
        let parent = child.parentNode
        let index = Array.prototype.indexOf.call(parent.children, child)
        let targetSelection = this.selections[index]
        startDate = targetSelection.x0
        endDate = targetSelection.x1
        this.notifySelection(startDate, endDate)
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
        this.refresh()

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
      turnOnCurrentSelection : function() {
        const cloneLayout = JSON.parse(JSON.stringify(this.layout))
        if(this.currentSelection) {
          this.turnOffCurrentSelection()
          this.currentSelection.line.color = TARGET_SELECTION_BORDER_COLOR
          cloneLayout.shapes = this.selections
          this.layout = cloneLayout
          this.refresh()
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
        let path =  $('#' + this.chartName).find( "a[data-title='Link chart']").find("path")
        if(this.isMainChart && this.linkIcon === linkChartsIconPath) {
          this.linkIcon= unLinkChartsIconPath
        } else  if(this.isMainChart && this.linkIcon === unLinkChartsIconPath) {
          this.linkIcon= linkChartsIconPath
        }

        if( this.isMainChart  && path)
          path.attr('d', this.linkIcon)

        this.switchLinkedMode()
      },
      setDefaultLinkIcon : function() {
        /*let path =  $('#' + this.chartName).find( "a[data-title='Link chart']").find("path")

        if( this.isMainChart  && path)
          path.attr('d', this.linkIcon)*/
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