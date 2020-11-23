<template>
  <div>
    <AerisDatavalidationChart
        :chartId="getChartId"
    />
  </div>
</template>
<script>

import $ from "jquery"
import ChartConfMixin from "../../../../aeris-datavalidation-common/mixin/chartConfMixin"
import AerisDatavalidationChart from "../../../../aeris-datavalidation-blocks/submodules/aeris-datavalidation-charts/components/aeris-datavalidation-chart"

export default {
  name: "aeris-datavalidation-defaultchart",
  components : {
    AerisDatavalidationChart,
  },
  props : {
    charts : {
      type : Object
    },
    chartName : {
      type : String
    },
    isLinkedChartMode: {
      type: Boolean,
      default : false
    },
  },
  mixins: [ChartConfMixin],
  data() {
    return {
      chartDiv : null,
      attrs: {
        class: 'mb-6',
        boilerplate: true,
        elevation: 2,
      },
    }
  },
  mounted() {
    this.initCurrentChart()
  },
  methods: {
    initCurrentChart : function() {
      this.$nextTick(() => {
        $('document').ready(() => {
          this.chartDiv = document.getElementById( this.getChartId )
          this.initCurrentChartConf(this.chartDiv)
        })
      })
    },
  }
}
</script>