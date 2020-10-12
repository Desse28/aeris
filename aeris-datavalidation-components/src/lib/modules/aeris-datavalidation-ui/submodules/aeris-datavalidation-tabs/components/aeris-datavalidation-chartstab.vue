<template>
  <div
      v-if="isSecondChartParametersEmpty"
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
          v-for="i in nbrParallelChart"
          :key="i"
          :href="'#'+$t('session.label_chart') + `-${i}`"
      >
        {{ $t("session.label_chart") }} {{ i }}
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
                :startDate="startDate"
                :isMainChart="isMainChart"
                :currentSession="currentSession"
                :instrumentInfos="instrumentInfos"
                :linkedChartData="linkedChartData"
                :parameters="secondChartParameters"
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
    isSecondChartParametersEmpty : {
      type: Boolean,
      default : false
    },
    isLinkedChartMode: {
      type: Boolean,
      default : false
    }
  },
  data () {
    return {
      tab: "chart-1",
      icons: false,
    }
  },
  computed : {
    getChartsNames : function () {
      let chartsNames = []
      for(let i = 1; i <= this.nbrParallelChart; i++) {
        chartsNames.push( this.$t('session.label_chart') + "-" + i)
      }
      return chartsNames
    }
  }
}
</script>

<style scoped>

</style>