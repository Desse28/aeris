<template>
  <div>
    <AerisDatavalidationSimpleToolbar
        :displayParallelChart="displayParallelChart"
        :hideParallelChart="hideParallelChart"
    />
    <AerisDatavalidationLandScapeLayaout
        justify="center"
        padding="pa-8"
        :cols="[mainChartGridSize, parallelChartGridSize]"
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
            <AerisDatavalidationChart
                :url="url"
                :callBack="setCurrentSession"
            />
          </template>
          <template v-slot:portrait2>
            <AerisDatavalidationSelection/>
          </template>
        </AerisDatavalidationPortraitLayaout>
      </template>
      <template v-slot:land2>
        <AerisDatavalidationChart v-if="0 < parallelChartGridSize"/>
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
      currentSession: null,
      mainChartGridSize : 12,
      parallelChartGridSize : 0,
      url: "http://localhost:9001/sessions/5f3d06f1f94fdd631a02655c",
    }
  },
  methods : {
    setCurrentSession : function (session) {
      this.currentSession = session;
      console.log("Test current session : ", session);
    },
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