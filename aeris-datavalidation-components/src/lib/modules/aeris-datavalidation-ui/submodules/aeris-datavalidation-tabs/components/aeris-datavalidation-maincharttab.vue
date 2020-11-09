<template>
  <div class="ml-6">
    <v-tabs
        background-color="light-blue"
        :class="'elevation-2 ' + tab"
        dark
        :centered="centered"
    >
      <v-tab>
        {{ getMainChartName }}
      </v-tab>
      <v-tab-item>
        <v-card
            flat
            tile
        >
          <AerisDatavalidationChart
              :charts="charts"
              :isMainChart="true"
              :selection="selection"
              :deleteStep="deleteStep"
              :chartName="getMainChartTitle"
              :currentSession="currentSession"
              :instrumentInfos="instrumentInfos"
              :linkedChartData="linkedChartData"
              :notifySelection="notifySelection"
              :parameters="getMainChartParameters"
              :switchLinkedMode="switchLinkedMode"
              :applyLinkedEffect="applyLinkedEffect"
              :isLinkedChartMode="isLinkedChartMode"
              :currentInstrument="currentInstrument"
              :secondChartsParameters="secondChartsParameters"
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
import $ from "jquery";
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
    instrumentInfos: {
      type : Object,
      default : () => null,
    },
    isMainChart: {
      type: Boolean,
      default: () => false
    },
    secondChartsParameters : {
      type: Array
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
    isLinkedChartMode: {
      type: Boolean,
      default : false
    },
  },
  computed : {
    getMainChartName : function () {
      let mainChartName = ""
      let values = Object.values(this.charts)
      if(this.charts && 0 < values.length) {
        mainChartName = this.$i18n.locale === 'fr' ? values[0].frName : values[0].enName
      }
      return mainChartName
    },
    getMainChartTitle : function () {
      let mainChartName = ""
      let values = Object.values(this.charts)
      if(this.charts && 0 < values.length) {
        mainChartName = /*this.$i18n.locale === 'fr' ? values[0].frName :*/ values[0].enName
      }
      return mainChartName
    },
    getMainChartParameters : function () {
      let parameters = []
      let values = Object.values(this.charts)

      if(this.charts && 0 < values.length) {
        parameters = values[0].parameters
      }

      return parameters
    },
  },
  data () {
    return {
      tabs: 1,
      grow: false,
      icons: false,
      right: false,
      centered: true,
      tab: "main-tab",
      vertical: false,
      prevIcon: false,
      nextIcon: false,
    }
  },
  mounted() {
    let tabSlider = $('.' + this.tab).find( '.v-tabs-slider-wrapper' )
    if(tabSlider)
      tabSlider.remove()
  }
}
</script>

<style scoped>

</style>