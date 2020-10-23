<template>
  <div
      v-if="!hideChart"
      class="mr-4 rounded-pill"
  >
    <v-tabs
        v-model="tab"
        background-color="light-blue"
        class="elevation-2"
        dark
    >
      <v-tabs-slider></v-tabs-slider>

      <v-tab
          v-for="(chart, index) in getCharts"
          :key="index"
          :href="'#' + chart.name"
      >
        {{ chart.name }}
      </v-tab>
      <v-tabs-items v-model="tab">
        <v-tab-item
            v-for="chart in getCharts"
            :key="chart.name"
            :value="chart.name"
        >
          <v-card
              flat
              tile
          >
            <AerisDatavalidationChart
                :endDate="endDate"
                :startDate="startDate"
                :isMainChart="isMainChart"
                :currentSession="currentSession"
                :instrumentInfos="instrumentInfos"
                :linkedChartData="linkedChartData"
                :parameters="charts[chart.name].parameters"
                :isLinkedChartMode="isLinkedChartMode"
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
    instrumentInfos : {
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
    linkedChartData : {
      type : Object,
      default : () => null
    },
    secondChartParameters : {
      type: Array,
      default : () => []
    },
    hideChart : {
      type: Boolean,
      default : false
    },
    isLinkedChartMode: {
      type: Boolean,
      default : false
    },
    charts : {
      type : Object,
      default : () => null
    },
    setCurrentParralelChart  : {
      type : Function,
      default : () => {}
    }
  },
  data () {
    return {
      tab: "chart-1",
      icons: false,
    }
  },
  computed: {
    getCharts() {
      let result = Object.values(this.charts)
      result.shift()
      return result
    },
  },
  watch : {
    tab : function(chartName) {
      this.setCurrentParralelChart(chartName)
    }
  }
}
</script>

<style scoped>

</style>