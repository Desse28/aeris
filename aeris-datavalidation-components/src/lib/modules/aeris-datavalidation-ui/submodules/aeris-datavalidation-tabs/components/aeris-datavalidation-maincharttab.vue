<template>
  <div class="ml-6">
    <v-tabs
        v-model="tab"
        background-color="light-blue"
        class="elevation-2"
        dark
        :centered="centered"
    >
      <v-tab
          v-for="i in tabs"
          :key="i"
          :href="`#chart-${i}`"
      >
        {{ $t("session.label_mainChart") }}
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
              :selection="selection"
              :startDate="startDate"
              :deleteStep="deleteStep"
              :parameters="parameters"
              :currentSession="currentSession"
              :instrumentInfos="instrumentInfos"
              :linkedChartData="linkedChartData"
              :notifySelection="notifySelection"
              :switchLinkedMode="switchLinkedMode"
              :applyLinkedEffect="applyLinkedEffect"
              :defaultSelections="defaultSelections"
              :currentInstrument="currentInstrument"
              :isSecondChartEmpty="isSecondChartEmpty"
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
    startDate: {
      type: String
    },
    endDate: {
      type: String
    },
    parameters: {
      type: Array,
      default: () => [],
    },
    instrumentInfos: {
      type : Object,
      default : () => null,
    },
    isMainChart: {
      type: Boolean,
      default: () => false
    },
    isSecondChartEmpty : {
      type: Boolean,
      default: () => false
    },
    deleteStep: {
      type: Number,
      default: () => 0
    },
    selection: {
      type: Object,
      default: () => null
    },
    notifySelection: {
      type: Function,
      default: () => {}
    },
    notifyDeleteSelection: {
      type: Function,
      default: () => {}
    },
    defaultSelections: {
      type: Array,
      default: () => []
    },
    currentSession: {
      type: Object,
      default: () => null
    },
    currentInstrument: {
      type: Object,
      default: () => null
    },
    linkedChartData: {
      type: Object,
      default: () => null
    },
    switchLinkedMode: {
      type: Function,
      default: () => {}
    },
    applyLinkedEffect: {
      type: Function,
      default: ()=> {}
    },
    secondChartParameters: {
      type: Array,
      default: () => []
    },
    isSecondChartParametersEmpty: {
      type: Boolean,
      default: false
    },
  },
  data () {
    return {
      tabs: 1,
      tab: "chart-1",
      grow: false,
      icons: false,
      right: false,
      centered: true,
      vertical: false,
      prevIcon: false,
      nextIcon: false,
    }
  },
}
</script>

<style scoped>

</style>