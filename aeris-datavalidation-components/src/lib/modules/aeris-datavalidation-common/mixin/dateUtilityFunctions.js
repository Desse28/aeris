function isSameDate(startDateStr, endDateStr) {
    let result = false
    let startDate, endDate

    if(startDateStr !== "" && endDateStr !== "") {
        startDate = new Date(getCleanDate(startDateStr))
        endDate = new Date(getCleanDate(endDateStr))
        result = startDate.getTime() === endDate.getTime()
    }

    return result
}

function getCleanDate(date, type) {
    let day, month, year, hours, minutes, seconds
    let dateFragment = date.split(".")
    let cleanDate = takeOfDateMilliseconds(dateFragment[0])
        .replace('T', ' ').replace('Z', ' ').trim()

    cleanDate = new Date(cleanDate)
    day = completeNumber(cleanDate.getDate())
    month = completeNumber(cleanDate.getMonth()+1)
    year = cleanDate.getFullYear()
    hours = completeNumber(cleanDate.getHours())
    minutes = completeNumber(cleanDate.getMinutes())
    seconds =  completeNumber(cleanDate.getSeconds())

    if(type === 'fr')
        return day + '/' + month + '/' + year + ' ' + hours + ':' + minutes + ':' + seconds

    return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds
}

function getTimePickerTimeFormat(date) {
    let currentTime = new Date(this.getCleanDate(date))
    let hours = completeNumber(currentTime.getHours())
    let minutes = completeNumber(currentTime.getMinutes())
    let seconds = completeNumber(currentTime.getSeconds())
    return hours + ":" + minutes + ":" + seconds
}

function getDatePikerDateFormat(date) {
    let currentDate = new Date(date)
    let day = completeNumber(currentDate.getDate())
    let month = completeNumber(currentDate.getMonth()+1)
    let year = currentDate.getFullYear()
    return year + "-" + month + "-" + day
}

function getTimeUniverselFormat(date) {
    let currentTime = new Date(date)
    let hours =  completeNumber(currentTime.getUTCHours())
    let minutes = completeNumber(currentTime.getUTCMinutes())
    let seconds = completeNumber(currentTime.getUTCSeconds())
    return hours + ":" + minutes + ":" + seconds
}

function getSpringDateFormat(date) {
    let currentDate = new Date(date)
    let day = completeNumber(currentDate.getDate())
    let month = completeNumber(currentDate.getMonth()+1)
    let year = currentDate.getFullYear()
    let hours = completeNumber(currentDate.getHours())
    let minutes = completeNumber(currentDate.getMinutes())
    let seconds =  completeNumber(currentDate.getSeconds())
    return year + '-' + month + '-' + day + 'T' + hours + ':'+ minutes + ':' + seconds + 'Z'
}

function completeNumber (number) {
    return Math.floor(number / 10) <= 0 ? "0" + number : number
}

function takeOfDateMilliseconds(date) {
    let dateFragment = date.split(".")
    return dateFragment[0]
}

export { isSameDate, getCleanDate, getTimePickerTimeFormat, getDatePikerDateFormat, getSpringDateFormat, getTimeUniverselFormat};
