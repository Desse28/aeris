import {
    blackTrashIconPath,
    linkChartsIconPath,
    unLinkChartsIconPath
} from "./../icons/index"

import {isSameDate} from "./../mixin/dateUtilityFunctions";

const DEFAULT_DATE = "1970-01-01T00:00:00.000+00:00"
const PLOTLY_SERVER_URL = "https://chart-studio.plotly.com"

export default {
    data: () => ({
        data: [],
        layout: {},
        config : {},
        modeBarButtons: [],
    }),
    computed: {
        getChartId : function () {
            if(this.chartName === "")
                return "defaultChart"
            return this.chartName.replace(' ', '')
        },
        getLinkChartIcon : function() {
            if(this.isLinkedChartMode)
                return linkChartsIconPath
            else
                return unLinkChartsIconPath
        },
        getTypeOfChart : function () {
            return this.currentInstrument.typeOfData
        }
    },
    methods : {
        initCurrentChartConf : function(chartDiv) {
            this.initChartsConfig(this.getLinkChartIcon)
            this.initLayout()
            this.$Plotly.newPlot(chartDiv, this.data, this.layout, this.config)
        },
        initChartsConfig : function(linkIcon) {
            if(this.isMainChart) {
                this.modeBarButtons = this.getMainChartModeBar(linkIcon)
            } else {
                this.modeBarButtons = this. getSecondChartsModeBar()
            }
            this.initConfig()
        },
        getMainChartModeBar : function(linkIcon) {
            return (
                [
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
                            icon:  this.$Plotly.Icons.home,
                            click: () => {
                                this.resetAxes()
                            }
                        },
                        'sendDataToCloud',
                    ]
                ]
            )
        },
        getSecondChartsModeBar : function () {
            return (
                [
                    [
                        'zoomIn2d',
                        'zoomOut2d',
                        'pan2d',
                        'toggleHover',
                        {
                            name: "Reset axes",
                            icon:  this.$Plotly.Icons.home,
                            click: () => {
                                this.resetAxes()
                            }
                        },
                        'sendDataToCloud',
                    ],
                ]
            )
        },
        initConfig : function () {
            this.config = {
                modeBarButtons: this.modeBarButtons,
                scrollZoom : true,
                displaylogo : false,
                displayModeBar : true,
                plotlyServerURL : PLOTLY_SERVER_URL
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
        getLayoutXaxis: function(value, type) {
            let startXaxis = "", endXaxis = ""

            if(this.charts[this.chartName]) {
                startXaxis = this.charts[this.chartName].startXaxis
                endXaxis = this.charts[this.chartName].endXaxis
                startXaxis = this.isSameDate(startXaxis, DEFAULT_DATE) ? "" : startXaxis
                endXaxis = this.isSameDate(endXaxis, DEFAULT_DATE) ? "" : endXaxis
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
        isSameDate,
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
        getLayoutModeBarConf : function () {
            return {
                orientation: 'h',
                color: 'rgba(68, 68, 68, 0.3)',
                bgcolor: 'rgba(255, 255, 255, 0.5)',
                activecolor: "rgba(212, 10, 10, 0.7)",
            }
        },
    },
}