<template>
  <v-select
      v-model="value"
      :items="items"
      :label="flag_message"
      multiple
  >
  </v-select>
</template>
<script>
export default {
  name: "aeris-datavalidation-select",
  props: {
    flag_message : {
      type : String,
      default : "Flags"
    }
  },
  data() {
    return {
      items: ['foo', 'bar', 'fizz', 'buzz', 'buzz1', 'buzz2', 'buzz3', 'buzz4'],
      value: [],
    }
  },
}
</script>

<style scoped>

</style>