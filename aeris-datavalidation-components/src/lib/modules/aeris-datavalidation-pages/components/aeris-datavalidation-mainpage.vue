<template>
  <div>
    <AerisDatavalidationSimpleToolbar
        :displayParallelChart="displayParallelChart"
        :hideParallelChart="hideParallelChart"
    />
    <AerisDatavalidationPortraitLayaout
        padding="pa-8"
        justify="center"
        :nbrChildElement="2"
        :cols="[mainChartGridSize, mainChartGridSize]"
    >
      <template v-slot:portrait1>
        <AerisDatavalidationChart/>
      </template>
      <template v-slot:portrait2>
        <AerisDatavalidationSelection/>
      </template>
    </AerisDatavalidationPortraitLayaout>
    <AerisDatavalidationLandScapeLayaout
        justify="center"
        padding="pa-8"
        :cols="[7, 5]"
        :nbrChildElement="2"
    >
      <template v-slot:land1>
        <AerisDatavalidationPortraitLayaout
            padding="pa-8"
            justify="center"
            :nbrChildElement="2"
            :cols="[12, 12]"
        >
          <template v-slot:portrait1>
            <AerisDatavalidationChart/>
          </template>
          <template v-slot:portrait2>
            <AerisDatavalidationSelection/>
          </template>
        </AerisDatavalidationPortraitLayaout>
      </template>
      <template v-slot:land2>
        <AerisDatavalidationChart/>
      </template>
    </AerisDatavalidationLandScapeLayaout>
  </div>
</template>
<script>
import {
  AerisDatavalidationChart,
  AerisDatavalidationSelection,
  AerisDatavalidationSimpleToolbar,
  AerisDatavalidationPortraitLayaout,
  AerisDatavalidationLandScapeLayaout,
} from "./../../aeris-datavalidation-components"

export default {
  name: "aeris-datavalidation-homepage",
  components: {
    AerisDatavalidationChart,
    AerisDatavalidationSelection,
    AerisDatavalidationSimpleToolbar,
    AerisDatavalidationPortraitLayaout,
    AerisDatavalidationLandScapeLayaout,
  },
  data() {
    return {
      mainChartGridSize : 12,
      parallelChartGridSize : 0,
    }
  },
  methods : {
    displayParallelChart : function () {
      this.mainChartGridSize = 7;
      this.parallelChartGridSize = 12 - this.mainChartGridSize;
    },
    hideParallelChart : function () {
      this.mainChartGridSize = 12;
      this.parallelChartGridSize = 12 - this.mainChartGridSize;
    }
  },
}
</script>
<style scoped>
</style>