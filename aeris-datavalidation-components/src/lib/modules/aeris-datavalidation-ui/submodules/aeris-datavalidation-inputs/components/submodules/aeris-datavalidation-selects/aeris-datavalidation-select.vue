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
    },
    setQualityFlags : {
      type : Function
    },
    qualityFlags : {
      type : Array,
      default : () => [],
    }
  },
  data() {
    return {
      items: [],
      value: [],
    }
  },
  watch: {
    value(val) {
      this.setQualityFlags(val)
    },
    qualityFlags : function(qualityFlags) {
      console.log("Test setQualityFlag")
      this.items = qualityFlags
    }
  },
}
</script>

<style scoped>

</style>