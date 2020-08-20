<template>
  <div>
    <v-row :justify="justify">
      <v-col
          :class="padding"
          v-for="i in nbrChildElement" :key="i"
          :cols="cols[i-1]"
      >
        <slot :name="'portrait'+ i"></slot>
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
  data() {
    return {
      gridMaxPoint : 12
    }
  },
}
</script>

<style scoped>
</style>