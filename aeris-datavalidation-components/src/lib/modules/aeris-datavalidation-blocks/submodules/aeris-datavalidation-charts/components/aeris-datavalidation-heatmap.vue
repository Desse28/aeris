<template>
  <v-row justify="center">
    <v-col cols="12">
      <div
          :id="getChartId"
      >
      </div>
    </v-col>
  </v-row>
</template>
<script>
import $ from "jquery";
import ChartMixin from "../../../../aeris-datavalidation-common/mixin/chartConfMixin"

export default {
  name: "aeris-datavalidation-heatmap",
  props : {
    chartName : {
      type : String
    },
    isMainChart : {
      type : Boolean,
      default : false
    },
    charts : {
      type : Object
    },
    isLinkedChartMode: {
      type: Boolean,
      default : false
    },
  },
  mixins: [ChartMixin],
  data() {
    return {
      chartDiv : null
    }
  },
  mounted() {
    $('document').ready(() => {
      this.chartDiv = document.getElementById( this.getChartId )

      this.data = [
        {
          z: [[1, 20, 30], [20, 1, 60], [30, 60, 1]],
          type: 'heatmap'
        }
      ]
      this.initCurrentChart(this.chartDiv)
    })
  },
  methods: {

  }
}
</script>

<style scoped>

</style>