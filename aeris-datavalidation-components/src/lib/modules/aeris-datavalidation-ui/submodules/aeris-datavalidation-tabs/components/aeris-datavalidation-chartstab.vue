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
          v-for="i in tabs"
          :key="i"
          :href="`#chart-${i}`"
      >
        Chart {{ i }}
      </v-tab>

      <v-tab-item
          :key="1"
          :value="'chart-' + 1"
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
      <v-tab-item
          :key="2"
          :value="'chart-' + 2"
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
      tab: null,
      text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
      icons: false,
      tabs: 2,
    }
  },
}
</script>

<style scoped>

</style>