<template>
  <div class="ma-8">
    <AerisDatavalidationChartSteppers
        :qualityFlags="qualityFlags"
        :selectionDate="selectionDate"
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
      let startX, endX//, startY, endY
      let startDate, endDate//, currentDate

      if(selection) {
        startX = selection.range.x[0]
        endX = selection.range.x[1]
        //startY = selection.range.x[0]
        //endY = selection.range.x[1]
        startDate = new Date(startX)
        endDate   = new Date(endX)

        //console.log("Test paramData (before) : ", this.currentData)
        this.currentData.forEach((paramData) => {
          console.log("Test paramData : ", paramData)
          /*paramData.x = paramData.x.filter((xPoint) => {
            currentDate = new Date(xPoint)
            return startDate <= currentDate && currentDate <= endDate
          })
          paramData.y = paramData.y.filter((yPoint) => {
            return startY <= yPoint && yPoint <= endY
          })*/
        })
        this.selectionDate = startDate
        this.selectionStartTime = startDate
        this.selectionEndTime = endDate
      }
    }
  },
  data() {
    return {
      data: "",
      selectionDate : null,
      selectionEndTime : null,
      selectionStartTime : null,
    }
  },
  methods : {
    selectionHandler : function(selection) {
      console.log("Test selection : ", selection)
    }
  },
}
</script>
<style scoped>
</style>