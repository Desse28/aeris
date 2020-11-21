import $ from "jquery";
import {
    linkChartsIconPath,
    unLinkChartsIconPath
} from "./../icons/index"

import {isSelectionExist} from "./../mixin/selectionUtilityFunctions"
import { isSameDate, getSpringDateFormat } from "./../mixin/dateUtilityFunctions"

export default {
    data: () => ({
    }),
    methods : {
        bindChartListener : function () {
            this.addSelectionListener()
            this.$forceNextTick(() => {
                $('document').ready(() => {
                    if(this.getChartId) {
                        if(this.isMainChart) {
                            this.chartDiv.on( 'plotly_hover', this.hoverHandler)
                            this.chartDiv.on( 'plotly_selected', this.addNewSelection)
                        }
                        this.chartDiv.on( 'plotly_relayout', this.reLayoutHandler)
                    }
                })
            })
        },
        hoverHandler : function(eventdata) {
            let points = eventdata.points[0]
            if(points && this.isLinkedChartMode && this.isMainChart)
                this.applyLinkedEffect(eventdata)
        },
        applyLinkedHoverEffect : function (points) {
            const targetDate = new Date(points.x)
            this.$Plotly.Fx.hover(this.chartDiv, {xval: targetDate.getTime()})
        },
        addNewSelection : function(data) {
            let startDate, endDate
            if(data) {
                startDate = data.range.x[0], endDate = data.range.x[1]
                if(!this.isSelectionExist(this.selections, startDate, endDate))
                    this.drawSelection(startDate, endDate, false)
            }
        },
        isSelectionExist,
        reLayoutHandler : function(data) {
            if(data && data['xaxis.range[0]'] !== this.linkedChartData.startXaxis &&
                data['xaxis.range[1]'] !== this.linkedChartData.endXaxis && this.isMainChart && this.isLinkedChartMode) {
                this.applyLinkedEffect(data)
            } else if(data['xaxis.range[0]'] && data['xaxis.range[1]']) {
                this.charts[this.chartName].startXaxis = this.getSpringDateFormat(data['xaxis.range[0]'])
                this.charts[this.chartName].endXaxis = this.getSpringDateFormat(data['xaxis.range[1]'])
                this.currentSession.charts = Object.values(this.charts)
                this.updateSessionState()
                this.bindChartListener()
            }
        },
        bindSelectionListener : function (startDate, endDate, isDefault) {
            this.currentSelection = this.selections[this.selections.length-1]
            $("#" + this.getChartId + " .select-outline").remove()
            this.addSelectionListener()

            if(!isDefault)
                this.notifySelection(startDate, endDate)
        },
        addSelectionListener : function () {
            this.$Plotly.relayout(this.chartDiv, { shapes : this.selections})
            this.$forceNextTick(() => {
                let children
                let layer = $('#' + this.getChartId).find('.shapelayer')[0]
                if (layer) {
                    children = layer.children
                    $('document').ready(() => {
                        if (children) {
                            children.forEach((selection, index) => {
                                if ($(selection).attr('id') === undefined) {
                                    $(selection).css("pointer-events", "bounding-box")
                                    $(selection).attr('id', 'selection' + index)
                                    $(document).on('click', '#' + 'selection' + index, this.switchSelection)
                                    $(document).on('mouseenter', '#' + 'selection' + index, this.tooltipMouseEnter)
                                    $(document).on('mouseleave', '#' + 'selection' + index, this.tooltipMouseLeave)
                                }
                            })
                        }
                    })
                }
            })
        },
        tooltipMouseEnter : function(event) {
            let startDate, endDate
            const mainChart = this.currentSession.charts[0]
            const index = $(event.currentTarget).attr('data-index')
            const targetSelection = mainChart.selections[index]

            if(targetSelection) {
                startDate = this.getCleanDate(targetSelection.startDate, 'fr')
                endDate = this.getCleanDate(targetSelection.endDate, 'fr')

                this.positionX = event.clientX
                this.positionY = event.clientY

                this.tooltipContent = this.$t('session.label_startDate') + ' : ' + startDate + '</br>'
                this.tooltipContent +=this.$t('session.label_endDate') + ' : ' + endDate + '</br>'
                this.tooltipContent += this.$t('session.label_flags') + " : " + '</br>'

                targetSelection.flags.forEach((flag) => {
                    this.tooltipContent += flag.code + ", " + flag.label + '</br>'
                })
                this.tooltipState = true
            }
        },
        tooltipMouseLeave : function () {
            this.tooltipState = false
        },
        enableLinkedMode : function() {
            let icon
            if(this.isMainChart) {
                this.currentSession.linkedChartMode = !this.isLinkedChartMode
                icon = !this.isLinkedChartMode ? linkChartsIconPath : unLinkChartsIconPath
                this.switchLinkedMode()
                this.initChartsConfig(icon)
                this.upDateModeBarIcon()//need two call for update icon
                this.upDateModeBarIcon()//second call
                this.updateSessionState()
                this.bindChartListener()
            }
        },
        upDateModeBarIcon : function () {
            this.config['scrollZoom'] = this.config['scrollZoom'] !== true
            this.config['displaylogo'] =  !this.config['displaylogo']
            this.config['displayModeBar'] = !this.config['displayModeBar']
            this.$Plotly.react(this.chartDiv, this.data, this.layout, this.config)
        },
        resetAxes : function () {
            if(this.getStartXaxis && this.getEndXaxis) {
                this.layout.xaxis.range = [this.getStartXaxis, this.getEndXaxis]
                this.charts[this.chartName].startXaxis = this.getStartXaxis
                this.charts[this.chartName].endXaxis = this.getEndXaxis
                this.currentSession.charts = Object.values(this.charts)
                this.updateSessionState()
                this.$Plotly.relayout(this.chartDiv, this.layout)
                if(this.isMainChart)
                    this.applyLinkedEffect("Reset axes")
            }
        },
        setLayoutAxisRange : function() {
            if(this.linkedChartData.startXaxis && this.linkedChartData.endXaxis) {
                this.layout.xaxis.range = [this.linkedChartData.startXaxis, this.linkedChartData.endXaxis]
                this.layout.xaxis.autorange =  !this.isLinkedChartMode
                this.charts[this.chartName].startXaxis = this.getSpringDateFormat(this.linkedChartData.startXaxis)
                this.charts[this.chartName].endXaxis = this.getSpringDateFormat(this.linkedChartData.endXaxis)
                this.$Plotly.relayout(this.chartDiv, this.layout)
                this.currentSession.charts = Object.values(this.charts)
                this.updateSessionState()
            }
        },
        isSameDate,
        getSpringDateFormat,
    },
}