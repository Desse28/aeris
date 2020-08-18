<template>
  <v-select
      v-model="value"
      :items="items"
      attach
      chips
      label="Flags"
      multiple
  >
  </v-select>
</template>
<script>
export default {
  name: "aeris-datavalidation-select",
  data() {
    return {
      items: ['foo', 'bar', 'fizz', 'buzz', 'buzz1'],
      value: [],
    }
  },
}
</script>

<style scoped>

</style>