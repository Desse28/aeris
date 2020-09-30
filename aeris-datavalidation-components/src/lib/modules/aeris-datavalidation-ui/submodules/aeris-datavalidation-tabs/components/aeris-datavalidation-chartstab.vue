<template>
  <div  v-if="isSecondChartParametersEmpty" class="mr-1">
    <v-tabs
        v-model="tab"
        background-color="deep-purple accent-4"
        class="elevation-2"
        dark
    >
      <v-tabs-slider></v-tabs-slider>

      <v-tab
          v-for="i in nbrParallelChart"
          :key="i"
          :href="`#chart-${i}`"
      >
        Chart {{ i }}
      </v-tab>
      <v-tabs-items v-model="tab">
        <v-tab-item
            v-for="name in getChartsNames"
            :key="name"
            :value="name"
        >
          <v-card
              flat
              tile
          >
            <AerisDatavalidationChart
                :endDate="endDate"
                :isMainChart="isMainChart"
                :dataInfo="dataInfo"
                :startDate="startDate"
                :currentSession="currentSession"
                :parameters="secondChartParameters"
                :currentInstrument="currentInstrument"
            />
          </v-card>
        </v-tab-item>
      </v-tabs-items>
    </v-tabs>
  </div>
</template>
<script>
import AerisDatavalidationChart from "./../../../../aeris-datavalidation-blocks/submodules/aeris-datavalidation-charts/components/aeris-datavalidation-chart"
export default {
  name: "aeris-datavalidation-chartstabs",
  components : {
    AerisDatavalidationChart,
  },
  props: {
    nbrParallelChart: {
      type: Number,
      default : () => 1
    },
    startDate : {
      type: String
    },
    endDate : {
      type: String
    },
    parameters: {
      type : Array,
      default : () => [],
    },
    dataInfo : {
      type : Object,
      default : () => null,
    },
    isMainChart : {
      type: Boolean,
      default: () => false
    },
    selection: {
      type: Object,
      default : () => null
    },
    notifySelection: {
      type : Function,
      default: () => {}
    },
    defaultSelections: {
      type: Array,
      default: () => []
    },
    currentSession : {
      type : Object,
      default : () => null
    },
    currentInstrument : {
      type : Object,
      default : () => null
    },
    secondChartParameters : {
      type: Array,
      default : () => []
    },
    isSecondChartParametersEmpty : {
      type: Boolean,
      default : false
    }
  },
  data () {
    return {
      tab: "chart-2",
      icons: false,
    }
  },
  watch : {
    tab: function (val) {
      console.log("test tab : ", val)
    }
  },
  computed : {
    getChartsNames : function () {
      let chartsNames = []
      for(let i = 1; i <= this.nbrParallelChart; i++) {
        chartsNames.push("chart-" + i)
      }
      console.log("Test chartsNames : ", chartsNames)
      return chartsNames
    }
  }
}
</script>

<style scoped>

</style>