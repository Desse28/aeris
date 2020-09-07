<template>
  <div class="ma-8">
    <AerisDatavalidationChartSteppers
        :qualityFlags="qualityFlags"
        :sessionSelections="sessionSelections"
        :selectionStartDate="selectionStartDate"
        :selectionEndDate="selectionEndDate"
        :selectionEndTime="selectionEndTime"
        :selectionStartTime="selectionStartTime"
        :setSelectionPreconfData="setSelectionPreconfData"
        :qualityFlagsDefaultValue="qualityFlagsDefaultValue"
        :saveSelection="saveSelection"
        :editSelection="editSelection"
        :resetSelection="resetForm"
        :setSelectionEndTime="setSelectionEndTime"
        :setSelectionEndDate="setSelectionEndDate"
        :setSelectionDateTime="setSelectionDateTime"
        :setSelectionStartTime="setSelectionStartTime"
        :setSelectionStartDate="setSelectionStartDate"
        :setQualityFlagsDefaultValue="setQualityFlagsDefaultValue"
    />
  </div>
</template>
<script>

import AerisDatavalidationChartSteppers from "./../../../../aeris-datavalidation-ui/submodules/aeris-datavalidation-steppers/components/aeris-datavalidation-chartsteppers"

export default {
  name: "aeris-datavalidation-selection",
  components: {
    AerisDatavalidationChartSteppers,
  },
  props: {
    selection : {
      type : Object,
      default : null,
    },
    currentData : {
      type : Array,
      default : () => [],
    },
    qualityFlags : {
      type : Array,
      default : () => [],
    },
    addSelection : {
      type : Function,
    },
    currentShape : {
      type : Object,
      default : null,
    },
    setCurrentShapeX0 : {
      type : Function,
    },
    setCurrentShapeX1 : {
      type : Function,
    },
    sessionSelections : {
      type : Array,
      default : () => [],
    },
    setSelectionPreconfData : {
      type : Function,
    },
  },
  watch : {
    selection : function ( selection) {
      this.selectionHandler(selection)
    },
  },
  data() {
    return {
      data: "",
      selectionStartDate : "",
      selectionEndDate : "",
      selectionEndTime : "",
      selectionStartTime : "",
      qualityFlagsDefaultValue : [],
      test : false,
    }
  },
  methods : {
    selectionHandler : function(selection) {
      let startX, endX
      if(selection && selection.range) {
        startX = selection.range.x[0]
        endX = selection.range.x[1]
        this.setSelectionDateTime(new Date(startX), new Date(endX))
      } else if(selection.x0 && selection.x1) {
        startX = selection.x0
        endX = selection.x1
        this.setSelectionDateTime(new Date(startX), new Date(endX))
      } else if (selection.type === 'clearForm') {
        this.resetForm();
      }
    },
    resetForm : function () {
      this.selectionStartDate = ''
      this.selectionEndDate = ''
      this.selectionStartTime = ''
      this.selectionEndTime = ''
      this.qualityFlagsDefaultValue = []
    },
    setSelectionDateTime : function(startDate, endDate) {
      this.selectionStartDate = this.getDateGoodFormat(startDate)
      this.selectionEndDate = this.getDateGoodFormat(endDate)
      this.selectionStartTime = this.getTimeGoodFormat(startDate)
      this.selectionEndTime = this.getTimeGoodFormat(endDate)
    },
    setSelectionStartDate : function(date) {
      let newX0
      let datePortions
      if(this.selectionStartDate !== date) {
        this.selectionStartDate = date
      }
      if(this.currentShape !== null) {
        this.currentShape.x0.split(" ")
        newX0 = date + " " + datePortions[1]
        this.setCurrentShapeX0(newX0)
      }
    },
    setSelectionEndDate : function(date) {
      let newX1
      let datePortions
      if(date !== this.selectionEndDate) {
        this.selectionEndDate = date
      }

      if(this.currentShape !== null) {
        datePortions = this.currentShape.x1.split(" ")
        newX1 = date + " " + datePortions[1]
        this.setCurrentShapeX1(newX1)
      }

    },
    setSelectionStartTime : function(time) {
      let newX0
      let datePortions
      if(this.selectionStartTime !== time) {
        this.selectionStartTime = time
      }
      if(this.currentShape !== null) {
        datePortions = this.currentShape.x0.split(" ")
        newX0 = datePortions[0] + " " + time + ":00"
        //this.setCurrentShapeX0(newX0)
        console.log("Test time : ", newX0, ",", this.currentShape.x0)
      }
    },
    setSelectionEndTime: function(time) {
      let newX1
      let datePortions
      if(this.selectionEndTime !== time) {
        this.selectionEndTime = time
      }
      if( this.currentShape !== null) {
        datePortions = this.currentShape.x1.split(" ")
        newX1 = datePortions[0] + " " + time
        console.log("Test time : ", newX1, ",",this.currentShape.x1)
        //this.setCurrentShapeX1(newX1)
      }
    },
    setQualityFlagsDefaultValue : function (value) {
      this.qualityFlagsDefaultValue = value
    },
    getDateGoodFormat : function(currentDate) {
      let day = this.completeNumber(currentDate.getUTCDate())
      let month = this.completeNumber(currentDate.getUTCMonth()+1)
      let year = currentDate.getUTCFullYear()
      return year + "-" + month + "-" + day
    },
    getTimeGoodFormat : function(currentTime) {
      let hours = this.completeNumber(currentTime.getHours())
      let minutes = this.completeNumber(currentTime.getUTCMinutes())
      let seconds = this.completeNumber(currentTime.getUTCSeconds())
      return hours + ":" + minutes + ":" + seconds
    },
    completeNumber : function(number) {
      let result = Math.floor(number / 10) <= 0 ? "0" + number : number
      return result
    },
    saveSelection : function (qualityFlagsSelection) {
      let currentSelection = {
        startDate : this.getBackEndDateFormat(new Date(this.selectionStartDate)),
        endDate : this.getBackEndDateFormat(new Date(this.selectionEndDate)),
        flags : qualityFlagsSelection
      }
      this.addSelection(currentSelection)
    },
    editSelection : function (str) {
      console.log("Test editSelection : ", str)
    },
    getBackEndDateFormat : function (date) {
      let day = this.completeNumber(date.getUTCDay())
      let month = this.completeNumber(date.getUTCMonth())
      let year = date.getUTCFullYear()
      let hours = this.completeNumber(date.getUTCHours())
      let minutes = this.completeNumber(date.getUTCMinutes())
      let seconds =  this.completeNumber(date.getUTCSeconds())
      return year + '-' + month + '-' + day + 'T' + hours + ':'+ minutes + ':' + seconds + 'Z'
    }

  },
}
</script>
<style scoped>
</style>