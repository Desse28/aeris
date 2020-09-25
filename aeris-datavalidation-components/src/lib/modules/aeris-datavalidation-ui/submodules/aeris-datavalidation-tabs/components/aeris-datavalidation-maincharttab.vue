<template>
  <div class="ml-6">
    <v-tabs
        v-model="tab"
        background-color="deep-purple accent-4"
        class="elevation-2"
        dark
        :centered="centered"
    >

      <v-tab
          v-for="i in tabs"
          :key="i"
          :href="`#chart-${i}`"
      >
        Main chart
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
              :isMainChart="true"
              :dataInfo="dataInfo"
              :selection="selection"
              :startDate="startDate"
              :defaultSelections="defaultSelections"
              :currentSession="currentSession"
              :notifySelection="notifySelection"
              :parameters="parameters"
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
          <h3>test2</h3>
          <AerisDatavalidationChart/>
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
      icons: false,
      centered: true,
      grow: false,
      vertical: false,
      prevIcon: false,
      nextIcon: false,
      right: false,
      tabs: 1,
    }
  },
}
</script>

<style scoped>

</style>