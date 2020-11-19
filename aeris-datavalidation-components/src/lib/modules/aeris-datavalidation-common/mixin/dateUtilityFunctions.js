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

function completeNumber (number) {
    return Math.floor(number / 10) <= 0 ? "0" + number : number
}

function takeOfDateMilliseconds(date) {
    let dateFragment = date.split(".")
    return dateFragment[0]
}

export { isSameDate };
