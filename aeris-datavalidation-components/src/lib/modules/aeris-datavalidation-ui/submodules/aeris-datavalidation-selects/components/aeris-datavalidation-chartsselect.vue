<template>
  <div style="max-height: 40px;">
    <v-select
        :items="parallelCharts"
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
    parallelCharts : {
      type : Array,
      default : () => []
    }
  },
  data() {
    return {
      select: this.parallelCharts[1],
    }
  },
}
</script>

<style scoped>

</style>