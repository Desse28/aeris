<template>
  <div class="ma-8">
    <AerisDatavalidationChartSteppers
        :qualityFlags="qualityFlags"
        :selectionStartDate="selectionStartDate"
        :selectionEndDate="selectionEndDate"
        :selectionEndTime="selectionEndTime"
        :selectionStartTime="selectionStartTime"
        :saveSelection="saveSelection"
        :editSelection="editSelection"
        :setSelectionStartTime="setSelectionStartTime"
        :setSelectionEndTime="setSelectionEndTime"
        :setSelectionStartDate="setSelectionStartDate"
        :setSelectionEndDate="setSelectionEndDate"
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
    }
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
      test : false,
    }
  },
  methods : {
    selectionHandler : function(selection) {
      let startX, endX
      if(selection) {
        startX = selection.range.x[0]
        endX = selection.range.x[1]
        this.setSelectionDateTime(new Date(startX), new Date(endX))
      }
    },
    setSelectionDateTime : function(startDate, endDate) {
      this.selectionStartDate = this.getDateGoodFormat(startDate)
      this.selectionEndDate = this.getDateGoodFormat(endDate)
      this.selectionStartTime = this.getTimeGoodFormat(startDate)
      this.selectionEndTime = this.getTimeGoodFormat(endDate)
    },
    setSelectionStartDate : function(date) {
      this.selectionStartDate = date
    },
    setSelectionEndDate : function(date) {
      this.selectionEndDate = date
    },
    setSelectionStartTime : function(time) {
      this.selectionStartTime = time
    },
    setSelectionEndTime: function(time) {
      this.selectionEndTime = time
    },
    getDateGoodFormat : function(currentDate) {
      let day = this.completeNumber(currentDate.getDay())
      let month = this.completeNumber(currentDate.getMonth())
      let year = currentDate.getFullYear()
      return day + "-" + month + "-" + year
    },
    getTimeGoodFormat : function(currentTime) {
      let hours = this.completeNumber(currentTime.getHours())
      let minutes = this.completeNumber(currentTime.getMinutes())
      let seconds = this.completeNumber(currentTime.getSeconds())
      return hours + ":" + minutes + ":" + seconds
    },
    completeNumber : function(number) {
      let result = Math.floor(number / 10) <= 0 ? "0" + number : number
      return result
    },
    saveSelection : function (str) {
      console.log("Test saveSelection : ", str)
    },
    editSelection : function (str) {
      console.log("Test editSelection : ", str)
    },
  },
}
</script>
<style scoped>
</style>