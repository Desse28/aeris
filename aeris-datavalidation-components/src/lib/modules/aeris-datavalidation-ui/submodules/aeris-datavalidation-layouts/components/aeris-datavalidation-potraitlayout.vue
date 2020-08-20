<template>
  <div>
    <v-row :justify="justify">
      <v-col
          cols="12"
          :class="padding"
          v-for="i in nbrChildElement" :key="i"
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