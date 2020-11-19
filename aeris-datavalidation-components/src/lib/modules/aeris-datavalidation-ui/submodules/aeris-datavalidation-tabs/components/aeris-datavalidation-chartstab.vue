<template>
  <div
      v-if="!hideChart"
      :class="getTabClass"
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
            <AerisDatavalidationHeatMap
                v-if="typeOfData === 'Heatmap'"
                :charts="charts"
                :chartName="enName"
                :isMainChart="isMainChart"
                :isLinkedChartMode="isLinkedChartMode"
            />
            <AerisDatavalidationChart
                v-else
                :charts="charts"
                :chartName="enName"
                :parameters="parameters"
                :isMainChart="isMainChart"
                :currentSession="currentSession"
                :instrumentInfos="instrumentInfos"
                :linkedChartData="linkedChartData"
                :isLinkedChartMode="isLinkedChartMode"
                :currentInstrument="currentInstrument"
                :currentSecondChart="currentSecondChart"
                :isBreackPointChange="isBreackPointChange"
            />
          </v-card>
        </v-tab-item>
      </v-tabs-items>
    </v-tabs>
  </div>
</template>
<script>
import AerisDatavalidationChart from "./../../../../aeris-datavalidation-blocks/submodules/aeris-datavalidation-charts/components/aeris-datavalidation-chart"
import AerisDatavalidationHeatMap from "./../../../../aeris-datavalidation-blocks/submodules/aeris-datavalidation-charts/components/aeris-datavalidation-heatmap"

export default {
  name: "aeris-datavalidation-chartstabs",
  components : {
    AerisDatavalidationChart,
    AerisDatavalidationHeatMap
  },
  props: {
    charts : {
      type : Object
    },
    typeOfData : {
      type : String
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
    currentSecondChart : {
      type : String,
      default : () => ""
    },
    isLinkedChartMode: {
      type: Boolean,
      default : false
    },
    setCurrentSecondChart  : {
      type : Function,
      default : () => {}
    },
    isBreackPointChange : {
      type : Boolean,
      default : false
    }
  },
  data () {
    return {
      tab: "",
      icons: false,
    }
  },
  computed : {
    getCharts() {
      return Object.values(this.charts).filter((chart) => {
        return chart.enName !== 'Main chart'
      })
    },
    getTabClass : function () {
      switch (this.$vuetify.breakpoint.name) {
        case 'xs': return "ml-6 rounded-pill"
        case 'sm': return "ml-6 rounded-pill"
          //case 'md': return 500
          //case 'lg': return 600
          //case 'xl': return 800
        default : return "mr-4 rounded-pill"
      }
    }
  },
  watch : {
    tab : function(chartName) {
      this.setCurrentSecondChart(chartName)
    },
    currentSecondChart : function() {
      if(this.tab === "")
        this.tab = this.currentSecondChart
    }
  }
}
</script>

<style scoped>

</style>