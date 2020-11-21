import $ from "jquery"
import { isSameDate, getCleanDate} from "./../mixin/dateUtilityFunctions"
import { isSelectionExist, getTargetSelectionIndex } from "./../mixin/selectionUtilityFunctions"

const SELECTION_BORDER_COLOR = 'rgb(84,217,27)'
const SELECTION_BACKGROUND_COLOR = 'rgb(33, 150, 243)'
const TARGET_SELECTION_BORDER_COLOR = 'rgb(255, 152, 0)'

export default {
    data: () => ({
    }),
    methods : {
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
        drawSelection : function(startDate, endDate, isDefault) {
            if(!this.isSelectionExist(this.selections, startDate, endDate)) {
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
                        x0 : this.getCleanDate(startDate),
                        x1 : this.getCleanDate(endDate),
                        yref : 'paper',
                        y0 : 0,
                        y1 : 1
                    }
                ]
                this.bindSelectionListener(startDate, endDate, isDefault)
            }
        },
        getCleanDate,
        isSelectionExist,
        turnOffCurrentSelection : function() {
            this.selections.forEach((selection) => {
                if(selection.line.color === TARGET_SELECTION_BORDER_COLOR) {
                    selection.line.color = SELECTION_BORDER_COLOR
                }
            })
        },
        turnOnCurrentSelection : function(targetIndex) {
            this.selections.forEach((selection, index) => {
                if(index === Number(targetIndex)) {
                    selection.line.color = TARGET_SELECTION_BORDER_COLOR
                } else if(selection.line.color === TARGET_SELECTION_BORDER_COLOR) {
                    selection.line.color = SELECTION_BORDER_COLOR
                }
            })
            this.addSelectionListener()
        },
        switchSelection : function(event) {
            let startDate, endDate
            let index = $(event.currentTarget).attr('data-index')
            let targetSelection = this.selections[index]
            if(targetSelection) {
                startDate = targetSelection.x0
                endDate = targetSelection.x1
                this.tooltipState = false
                this.currentSelection = targetSelection
                this.notifySelection(startDate, endDate)
                this.turnOnCurrentSelection(index)
            }
        },
        deleteTargetSelection : function() {
            let index
            let mainChart = this.currentSession.charts[0]

            if(this.currentSelection === null) {
                this.deleteAlert = true
                setTimeout(() => {
                    this.deleteAlert = false
                }, 1000)
            } else if(mainChart.selections) {
                index = this.getTargetSelectionIndex(mainChart.selections, this.currentSelection)

                if(index !== -1) {
                    this.deleteDialog = true
                } else {
                    this.deleteCurrentSelection()
                }
            }
        },
        getTargetSelectionIndex,
        deleteCurrentSelection : function() {
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
            }
        },
        submitDeleteSelection : function(startDate, endDate) {
            let mainChart, selectionsBeforeLen
            if(startDate && endDate) {
                mainChart = this.currentSession.charts[0]
                selectionsBeforeLen =  mainChart.selections.length

                mainChart.selections = mainChart.selections.filter((selection) => {
                    return (!this.isSameDate(selection.startDate, startDate) && !this.isSameDate(selection.endDate, endDate))
                })

                if(mainChart.selections.length < selectionsBeforeLen)
                    this.updateSessionState()
            }
        },
        setCurrentSelection : function(index, isDefault) {
            let startDate, endDate

            this.currentSelection = this.selections[index]
            startDate = this.currentSelection.x0
            endDate = this.currentSelection.x1
            this.turnOnCurrentSelection(index)

            if(!isDefault)
                this.notifySelection(startDate, endDate)
        },
        setCurrentSelectionPeriod : function(newStartDate, newEndDate) {
            if(newStartDate && newEndDate) {
                this.currentSelection.x0 = newStartDate
                this.currentSelection.x1 = newEndDate
                this.$Plotly.relayout(this.chartDiv, { shapes : this.selections})
                this.addSelectionListener()
            }
        },
        isSwitchSelection : function(startDate, endDate) {
            if(startDate && endDate && this.currentSelection) {
                return (!this.isSameDate(this.currentSelection.x0, startDate) &&
                    !this.isSameDate(this.currentSelection.x1, endDate))
            }
            return false
        },
        getSelectionIndex: function(startDate, endDate) {
            return this.selections.findIndex((selection) => {
                return (this.isSameDate(selection.x0, startDate) &&
                    this.isSameDate(selection.x1, endDate))
            })
        },
        isSelectionEmpty : function() {
            return !(this.selection && this.currentSelection !== null)
        },
        isSameDate,
    },
}