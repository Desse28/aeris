const HEAT_MAP_TYPE = "Heatmap"
const TIME_SERIES_TYPE = "Time series"

export default {
    data: () => ({
        currentUrl : "",
        currentData: [],
        callBack : null,
        requestData: null,
        typeOfRequest: "",
    }),
    methods : {
        initDefaultParameters : function (firstParameter, typeOfChart) {
          if(this.parameters)
              this.addNewParameter(firstParameter, typeOfChart)
        },
        addNewParameter : function (parameter, typeOfChart) {
            let uri
            let {startDate, endDate} = this.currentInstrument

            this.typeOfRequest = "GET"
            this.callBack = (data) => {
                if(data) {
                    if(typeOfChart === TIME_SERIES_TYPE)
                        this.timeSeriesDataHandler(data, parameter, typeOfChart)
                    else if(typeOfChart === HEAT_MAP_TYPE)
                        this.heatMapDataHandler(data, parameter, typeOfChart)
                }
            }
            uri = this.getTargetUri(typeOfChart, parameter.name, startDate, endDate)
            this.currentUrl = process.env.VUE_APP_ROOT_API + uri
        },
        timeSeriesDataHandler : function (data, parameter, typeOfChart) {
            let nextIndex
            if(data) {
                this.updateChart(data.parameterData, parameter)
                if(this.nextDefaultParameterExist(parameter)) {
                    nextIndex = this.getParameterIndex(parameter) + 1
                    this.addNewParameter(this.parameters[nextIndex], typeOfChart)
                }
            }
        },
        heatMapDataHandler : function ({dimensions, variables}, parameter, typeOfChart) {
            let data
            if(dimensions && variables && parameter && typeOfChart) {
                data = [{
                    x : variables.time.data,
                    y  : variables.level.data,
                    z : variables.main.data,
                    type : 'heatmap',
                    showlegend : true
                }]
                this.$Plotly.addTraces(this.chartDiv, data)
            }
        },
        getTargetUri : function (typeOfChart, parameterName, startDate, endDate) {
            let targetUri = ""

            if(typeOfChart === TIME_SERIES_TYPE)
                targetUri =  "/instruments/" + parameterName + "/" + startDate + "/" + endDate
            else if(typeOfChart === HEAT_MAP_TYPE)
                targetUri =  "/instruments/" + parameterName

            return targetUri
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
        updateChart: function(paramData, parameter) {
            let paramDataKeys
            let dataContent = {}
            let currentKey= ""
            let currentContent = null

            if(paramData && parameter) {
                paramDataKeys = Object.keys(paramData[0])

                paramData.forEach((data) => {
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
            }
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
            this.$Plotly.addTraces(this.chartDiv, data)
            this.currentSession.charts = Object.values(this.charts)
            this.updateSessionState()
        },
        removeParameter : function (newParameters, oldsParameters) {
            let intersection
            let parameterName, targetParameterIndex

            intersection = oldsParameters.filter(param => {
                return !newParameters.some(newParam => newParam.name === param.name)
            })

            parameterName = intersection[0].name
            targetParameterIndex = this.data.findIndex(param => param.name === parameterName)

            if(-1 < targetParameterIndex ) {
                this.$Plotly.deleteTraces(this.chartDiv, targetParameterIndex)
            }
            this.currentSession.charts = Object.values(this.charts)
            this.updateSessionState()
        },
    },
}