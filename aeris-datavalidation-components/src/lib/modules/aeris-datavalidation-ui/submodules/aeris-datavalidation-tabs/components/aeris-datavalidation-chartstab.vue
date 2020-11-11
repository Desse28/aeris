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
          v-for="({enName, frName}, index) in getCharts"
          :key="index"
          :href="'#' + enName"
      >
        {{$i18n.locale === 'fr' ? frName : enName}}
      </v-tab>
      <v-tabs-items v-model="tab">
        <v-tab-item
            v-for="{enName, parameters} in getCharts"
            :key="enName"
            :value="enName"
        >
          <v-card
              flat
              tile
          >
            <AerisDatavalidationChart
                :charts="charts"
                :chartName="enName"
                :parameters="parameters"
                :isMainChart="isMainChart"
                :currentSession="currentSession"
                :instrumentInfos="instrumentInfos"
                :linkedChartData="linkedChartData"
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
    charts : {
      type : Object
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
    hideChart : {
      type: Boolean,
      default : false
    },
    isLinkedChartMode: {
      type: Boolean,
      default : false
    },
    setCurrentSecondChart  : {
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
      return Object.values(this.charts).filter((chart) => {
        return chart.enName !== 'Main chart'
      })
    }
  },
  watch : {
    tab : function(chartName) {
      this.setCurrentSecondChart(chartName)
    }
  }
}
</script>

<style scoped>

</style>