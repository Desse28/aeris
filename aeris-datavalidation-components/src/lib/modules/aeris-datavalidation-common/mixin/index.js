export default {
    data () {
        return {}
    },
    created: function () {
        console.log("Printing from the Mixin")
    },
    methods: {
        completeNumber : function(number) {
            let result = Math.floor(number / 10) <= 0 ? "0" + number : number
            return result
        },
        getDatePikerDateFormat : function(date, code) {
            let response
            let currentDate = new Date(date)
            let day = this.completeNumber(currentDate.getUTCDate())
            let month = this.completeNumber(currentDate.getUTCMonth()+1)
            let year = currentDate.getUTCFullYear()
            response = code === "en" ? year + "-" + month + "-" + day : day + "-" + month + "-" + year
            return response
        },
        getTimePickerTimeFormat : function(date, isUtcFormat) {
            let currentTime = new Date(date)
            let hours = isUtcFormat ? currentTime.getUTCHours() : currentTime.getHours()
            let minutes = this.completeNumber(currentTime.getMinutes())
            let seconds = this.completeNumber(currentTime.getSeconds())
            hours = this.completeNumber(hours)
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
                            this.getCleanDate(endDate) === shortEndDate)
                            return true
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
        getCleanDate : function (date) {
            let dateFragment = date.split(".")
            return this.takeOfDateMilliseconds(dateFragment[0])
                .replace('T', ' ').replace('Z', ' ').trim()
        },
        takeOfDateMilliseconds : function(date) {
            let dateFragment = date.split(".")
            return dateFragment[0]
        }
    }
}