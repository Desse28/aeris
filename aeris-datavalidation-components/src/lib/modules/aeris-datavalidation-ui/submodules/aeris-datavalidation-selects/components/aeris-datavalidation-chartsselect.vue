<template>
  <div style="max-height: 40px;">
    <v-select
        :items="charts"
        item-text="name"
        v-model="select"
    >
    </v-select>
  </div>
</template>

<script>
export default {
  name: "aeris-datavalidation-chartsselect",
  props : {
    selectIndex : {
      type : Number,
    },
    charts : {
      type : Array,
      default : () => []
    },
    currentParameter : {
      type : Object
    },
    switchParameterChart : {
      type : Function,
      default : () => {}
    }
  },
  data() {
    return {
      select: this.charts[1],
    }
  },
  watch : {
    select : function (chartName, oldChart) {

      if(this.currentParameter) {
        this.currentParameter.chartName = chartName
        this.currentParameter.oldChartName = typeof oldChart === "object" ? oldChart.name : oldChart
      }

      if(this.currentParameter.isOn)
        this.switchParameterChart(this.currentParameter)
    }
  }
}
</script>

<style scoped>

</style>