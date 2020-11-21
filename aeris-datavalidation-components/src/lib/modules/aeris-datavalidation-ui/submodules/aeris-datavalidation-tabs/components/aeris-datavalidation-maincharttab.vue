<template>
  <div :class="getTabClass">
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
          <AerisDatavalidationHeatMap
              v-if="typeOfData === 'Heatmap'"
              :charts="charts"
              :isMainChart="true"
              :chartName="getMainChartTitle"
              :isLinkedChartMode="isLinkedChartMode"
          />
          <AerisDatavalidationTimeSeries
              v-else-if="typeOfData === 'Time series'"
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
              :isBreackPointChange="isBreackPointChange"
              :secondChartsParameters="secondChartsParameters"
          />
          <AerisDatavalidationDefaultChart
              v-else
              :charts="charts"
              :isMainChart="true"
              chartName="mainChart"
              :selection="selection"
          />
        </v-card>
      </v-tab-item>
    </v-tabs>
  </div>
</template>
<script>
import $ from "jquery";
import AerisDatavalidationHeatMap from "./../../../../aeris-datavalidation-blocks/submodules/aeris-datavalidation-charts/components/aeris-datavalidation-heatmap"
import AerisDatavalidationTimeSeries from "./../../../../aeris-datavalidation-blocks/submodules/aeris-datavalidation-charts/components/aeris-datavalidation-timeseries"
import  AerisDatavalidationDefaultChart from "./../../../../aeris-datavalidation-blocks/submodules/aeris-datavalidation-charts/components/aeris-datavalidation-defaultchart"

export default {
  name: "aeris-datavalidation-chartstabs",
  components : {
    AerisDatavalidationHeatMap,
    AerisDatavalidationTimeSeries,
    AerisDatavalidationDefaultChart
  },
  props: {
    charts : {
      type : Object
    },
    typeOfData : {
      type : String
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
    isBreackPointChange : {
      type : Boolean,
      default : false
    }
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
        mainChartName = values[0].enName
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
    getTabClass : function () {
      switch (this.$vuetify.breakpoint.name) {
          //case 'xs': return "ml-6 rounded-pill"
          //case 'sm': return "ml-6 rounded-pill"
          // case 'md': return 500
          //case 'lg': return 600
          //case 'xl': return 800
        default : return "ml-6"
      }
    }
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