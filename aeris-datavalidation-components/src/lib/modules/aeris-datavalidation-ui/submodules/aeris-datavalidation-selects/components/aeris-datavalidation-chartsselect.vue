<template>
  <div style="max-height: 40px;">
    <v-select
        v-model="select"
        :items="charts"
        name="chart"
        :item-text="$i18n.locale === 'fr'? 'frName' : 'enName'"
        single-line
        return-object
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
    },
    defaultChart : {
      type : Object
    },
    currentParameter : {
      type : Object
    },
    notifySwitchChart : {
      type : Function,
    },
    currentSecondChart : {
      type : String,
      default : () => ""
    },
    disableParameter : {
      type : Function,
      default : () => {}
    }
  },
  data() {
    return {
      select: this.defaultChart,
    }
  },
  watch : {
    select : function (newChart, oldChart) {
      const newChartName = newChart.enName
      const oldChartName = oldChart ? oldChart.enName : undefined
      this.notifySwitchChart(this.currentParameter, newChartName, oldChartName, this.selectIndex)
    },
    charts : {
      immediate: true,
      handler(newCharts) {
        if(this.defaultChart === undefined) {
          this.select = newCharts[1]
          this.disableParameter(this.currentParameter)
        }
      },
    },
  }
}
</script>

<style scoped>

</style>