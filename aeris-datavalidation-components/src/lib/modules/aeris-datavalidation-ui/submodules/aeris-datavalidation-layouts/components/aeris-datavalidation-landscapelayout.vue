<template>
    <div :class="cols[0] === 12 && 'mr-4 rounded-pill'">
      <v-row :justify="justify">
        <v-col
            :class="padding"
            v-for="i in nbrChildElement" :key="i"
            :cols="cols[i-1]"
        >
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
    justify : {
      type : String,
      default : "center"
    },
    padding : {
      type : String,
      default : "pa-8"
    },
    cols : {
      type : Array,
      default : () => [12]
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