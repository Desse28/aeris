export default {
    data () {
        return {}
    },
    created: function () {
        console.log("Printing from the Mixin")
    },
    methods: {
        formatDate (date) {
            if (!date) return null
            const [year, month, day] = date.split('-')
            return `${month}/${day}/${year}`
        },
        completeNumber : function(number) {
            let result = Math.floor(number / 10) <= 0 ? "0" + number : number
            return result
        },
        isGreaterThan : function(startDateStr, endDateStr) {
            let result = false
            let startDate, endDate

            if(startDateStr && endDateStr) {
                startDate = new Date(this.getCleanDate(startDateStr))
                endDate = new Date(this.getCleanDate(endDateStr))
                result = startDate.getTime() > endDate.getTime()
            }

            return result
        },
        isSameDate : function(startDateStr, endDateStr) {
            let result = false
            let startDate, endDate

            if(startDateStr && endDateStr) {
                startDate = new Date(this.getCleanDate(startDateStr))
                endDate = new Date(this.getCleanDate(endDateStr))
                result = startDate.getTime() === endDate.getTime()
            }

            return result
        },
        getDatePikerDateFormat : function(date) {
            let currentDate = new Date(date)
            let day = this.completeNumber(currentDate.getUTCDate())
            let month = this.completeNumber(currentDate.getUTCMonth()+1)
            let year = currentDate.getUTCFullYear()
            return year + "-" + month + "-" + day
        },
        getTimePickerTimeFormat : function(date) {
            let currentTime = new Date(this.getCleanDate(date))
            let hours = this.completeNumber(currentTime.getHours())
            let minutes = this.completeNumber(currentTime.getMinutes())
            let seconds = this.completeNumber(currentTime.getSeconds())
            return hours + ":" + minutes + ":" + seconds
        },
        getTimeUniverselFormat : function(date) {
            let currentTime = new Date(date)
            let hours =  this.completeNumber(currentTime.getUTCHours())
            let minutes = this.completeNumber(currentTime.getUTCMinutes())
            let seconds = this.completeNumber(currentTime.getUTCSeconds())
            return hours + ":" + minutes + ":" + seconds
        },
        getSpringDateFormat : function(date) {
            let currentDate = new Date(date)
            let day = this.completeNumber(currentDate.getDate())
            let month = this.completeNumber(currentDate.getMonth()+1)
            let year = currentDate.getFullYear()
            let hours = this.completeNumber(currentDate.getHours())
            let minutes = this.completeNumber(currentDate.getMinutes())
            let seconds =  this.completeNumber(currentDate.getSeconds())
            return year + '-' + month + '-' + day + 'T' + hours + ':'+ minutes + ':' + seconds + 'Z'
        },
        addMinutesToDate : function (dateStr, numberOfMinute, mode) {
            let currentDate, minute, result = ""
            if(dateStr && numberOfMinute) {
                currentDate = new Date(dateStr)
                minute = mode === "subtract" ? - 1000 * (60 * numberOfMinute) : 1000 * (60 * numberOfMinute)
                result =  this.getSpringDateFormat( currentDate.getTime() + minute )
            }
            return result
        },
        isSelectionExist : function(session, startDate, endDate) {
            let selection, selections
            let shortStartDate, shortEndDate

            if(session) {
                selections = session.sessionSelections
                if(selections) {
                    for(let index in selections) {
                        selection = selections[index]
                        shortStartDate = this.getCleanDate(selection.startDate)
                        shortEndDate = this.getCleanDate(selection.endDate)
                        if(this.getCleanDate(startDate) === shortStartDate &&
                            this.getCleanDate(endDate) === shortEndDate) {
                            return true
                        }
                    }
                }
            }
            return false
        },
        getTargetSelection : function(session, startDate, endDate) {
            let selection, selections
            let shortStartDate, shortEndDate
            if(session) {
                selections = session.sessionSelections
                if(selections) {
                    for(let index in selections) {
                        selection = selections[index]
                        shortStartDate = this.getCleanDate(selection.startDate)
                        shortEndDate = this.getCleanDate(selection.endDate)

                        if(this.getCleanDate(startDate) === shortStartDate &&
                            this.getCleanDate(endDate) === shortEndDate)
                            return selection
                    }
                }
            }
            return null
        },
        getTargetSelectionIndex : function (selections, selection) {
            let currentSelection
            if(selection && selections) {
                for(let index in selections) {
                    currentSelection = selections[index]

                    if( this.isSameDate(selection.x0, currentSelection.startDate) &&
                        this.isSameDate(selection.x1, currentSelection.endDate)) {
                        return index
                    }
                }
            }
            return -1
        },
        getCleanDate: function (date) {
            let day, month, year, hours, minutes, seconds
            let dateFragment = date.split(".")
            let cleanDate = this.takeOfDateMilliseconds(dateFragment[0])
                .replace('T', ' ').replace('Z', ' ').trim()

            cleanDate = new Date(cleanDate)
            day = this.completeNumber(cleanDate.getDate())
            month = this.completeNumber(cleanDate.getMonth()+1)
            year = cleanDate.getFullYear()
            hours = this.completeNumber(cleanDate.getHours())
            minutes = this.completeNumber(cleanDate.getMinutes())
            seconds =  this.completeNumber(cleanDate.getSeconds())

            return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds
        },
        takeOfDateMilliseconds : function(date) {
            let dateFragment = date.split(".")
            return dateFragment[0]
        },
        isSinglePoint(point, selections) {
            let selection
            let cptPoint = 0

            if(point && selections) {
                for(let index in selections) {
                    selection = selections[index]
                    if(this.isSameDate(selection.startDate, point) || this.isSameDate(selection.endDate, point))
                        cptPoint++
                }
            }
            return cptPoint < 2;
        },
        getStartDateLim : function(startDate, selections) {
            let lim="", selection

            if(startDate && selections) {
                for(let index in selections) {
                    selection = selections[index]
                    if(this.isGreaterThan(startDate, selection.endDate)) {
                        if(lim === "")
                            lim = selection.endDate
                        else
                            lim = this.isGreaterThan(selection.endDate, lim) ? selection.endDate : lim
                    }
                }
            }

            return lim
        },
        getEndDateLim : function(endDate, selections) {
            let lim="", selection

            if(endDate && selections) {
                for(let index in selections) {
                    selection = selections[index]
                    if(this.isGreaterThan(selection.startDate, endDate)) {
                        if(lim === "")
                            lim = selection.startDate
                        else
                            lim = this.isGreaterThan(lim, selection.startDate) ? lim : selection.startDate
                    }
                }
            }

            return lim
        }
    }
}