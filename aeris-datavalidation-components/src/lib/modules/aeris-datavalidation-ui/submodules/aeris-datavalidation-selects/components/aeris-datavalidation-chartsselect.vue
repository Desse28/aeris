<template>
  <div style="max-height: 40px;">
    <v-select
        :items="charts"
        item-text="enName"
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
    },
    defaultChart : {
      type : String
    },
    currentParameter : {
      type : Object
    },
    notifySwitchChart : {
      type : Function,
    }
  },
  data() {
    return {
      select: this.defaultChart,
    }
  },
  watch : {
    select : function (newChartName, oldChartName) {
      this.notifySwitchChart(this.currentParameter, newChartName, oldChartName, this.selectIndex)
    }
  }
}
</script>

<style scoped>

</style>