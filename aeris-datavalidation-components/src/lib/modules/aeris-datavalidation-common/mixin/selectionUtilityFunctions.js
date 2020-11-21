function isSelectionExist(selections, startDate, endDate) {
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
}
function getTargetSelection(selections, startDate, endDate) {
    return selections.find((selection) => {
        return (this.isSameDate(selection.startDate, startDate) &&
            this.isSameDate(selection.endDate, endDate))
    })
}

 function getTargetSelectionIndex(selections, targetSelection) {
    return selections.findIndex((selection) => {
        return (this.isSameDate(selection.startDate, targetSelection.x0) &&
            this.isSameDate(selection.endDate, targetSelection.x1))
    })
}

export { isSelectionExist, getTargetSelection, getTargetSelectionIndex }
