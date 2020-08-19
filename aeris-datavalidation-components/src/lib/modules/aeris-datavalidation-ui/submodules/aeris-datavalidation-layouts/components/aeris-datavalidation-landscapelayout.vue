<template>
    <div>
      <v-row justify="center">
        <v-col
            :cols="colSize"
            class="pa-8"
            v-for="i in nbrChildElement" :key="i">
          <slot :name="'land'+ i"></slot>
        </v-col>
      </v-row>
    </div>
</template>
<script>

export default {
  name: "aeris-datavalidation-landscapelayout",
  props: {
    nbrChildElement : {
      type : Number,
      default : 1
    },
  },
  beforeMount() {
    this.colSize = this.getNumberOfCol();
  },
  data() {
    return {
      colSize : 1,
      gridMaxPoint : 12
    }
  },
  methods: {
    getNumberOfCol : function () {
      return  this.gridMaxPoint/this.nbrChildElement;
    }
  },
}
</script>
<style scoped>
</style>