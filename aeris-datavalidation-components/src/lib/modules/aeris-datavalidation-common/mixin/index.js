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
        getTimePickerTimeFormat : function(date) {
            console.log("Test getTimePickerTimeFormat : ", date)
            let currentTime = new Date(date)
            let hours = this.completeNumber(currentTime.getUTCHours())
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
        }
    }
}