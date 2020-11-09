import {colors, defaultColor} from "./../colors/index"

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
        isSameDate : function(startDateStr, endDateStr) {
            let result = false
            let startDate, endDate

            if(startDateStr !== "" && endDateStr !== "") {
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
        isSelectionExist : function(selections, startDate, endDate) {
            let currentStartDate, currentendDate
            if(selections) {
                return selections.some((selection) => {
                    currentStartDate = selection.startDate ? selection.startDate : selection.x0
                    currentendDate = selection.endDate ? selection.endDate : selection.x1

                    return (this.isSameDate(currentStartDate, startDate) &&
                        this.isSameDate(currentendDate, endDate))
                })
            }
            return false
        },
        getTargetSelection : function(selections, startDate, endDate) {
            return selections.find((selection) => {
                return (this.isSameDate(selection.startDate, startDate) &&
                    this.isSameDate(selection.endDate, endDate))
            })
        },
        getTargetSelectionIndex : function (selections, targetSelection) {
            return selections.findIndex((selection) => {
                return (this.isSameDate(selection.startDate, targetSelection.x0) &&
                    this.isSameDate(selection.endDate, targetSelection.x1))
            })
        },
        getCleanDate : function (date) {
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
        getNewColor : function(colorIndex) {
            if(colorIndex < colors.length -1)
                return colors[colorIndex]
            else
                return defaultColor
        }
    }
}