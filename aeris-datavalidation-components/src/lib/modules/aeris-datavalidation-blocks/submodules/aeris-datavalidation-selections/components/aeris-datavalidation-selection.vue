<template>
  <div class="ma-8">
    <AerisDatavalidationChartSteppers
        :qualityFlags="qualityFlags"
        :selectionStartDate="selectionStartDate"
        :selectionEndDate="selectionEndDate"
        :selectionEndTime="selectionEndTime"
        :selectionStartTime="selectionStartTime"
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
    }
  },
  methods : {
    selectionHandler : function(selection) {
      console.log("Test selection : ", selection)
      let startX, endX//, startY, endY
      let startDate, endDate//, currentDate

      if(selection) {
        startX = selection.range.x[0]
        endX = selection.range.x[1]
        //startY = selection.range.x[0]
        //endY = selection.range.x[1]
        startDate = new Date(startX)
        endDate   = new Date(endX)
        this.setSelectionDateTime(startDate, endDate)
      }
    },
    setSelectionDateTime : function(startDate, endDate) {
      this.selectionStartDate = this.getDateGoodFormat(startDate)
      this.selectionEndDate = this.getDateGoodFormat(endDate)
      this.selectionStartTime = this.getTimeGoodFormat(startDate)
      this.selectionEndTime = this.getTimeGoodFormat(endDate)
    },
    getDateGoodFormat : function(currentDate) {
      let day = this.completNumber(currentDate.getDay())
      let month = this.completNumber(currentDate.getMonth())
      let year = currentDate.getFullYear()
      return day + "/" + month + "/" + year
    },
    getTimeGoodFormat : function(currentTime) {
      let hours = this.completNumber(currentTime.getHours())
      let minutes = this.completNumber(currentTime.getMinutes())
      let seconds = this.completNumber(currentTime.getSeconds())
      return hours + ":" + minutes + ":" + seconds
    },
    completNumber : function(number) {
      let result = Math.floor(number / 10) <= 0 ? "0" + number : number
      return result
    },
  },
}
</script>
<style scoped>
</style>