<template>
  <v-select
      v-model="value"
      :items="qualityFlags"
      :label="flag_message"
      :item-text="itemText"
      :name="name"
      return-object
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
    setFlagsSelected : {
      type : Function
    },
    qualityFlags : {
      type : Array,
      default : () => [],
    },
    name : {
      type : String,
      default : ""
    },
    itemText : {
      type : String,
      default : "name"
    },
    defaultQualityFlags : {
      type : Array,
      default : () => [],
    },
  },
  data() {
    return {
      items: [],
      value: [],
    }
  },
  watch: {
    value(flags) {
      this.setFlagsSelected(flags)
    },
    qualityFlags : function(qualityFlags) {
      this.items = qualityFlags
    },
    defaultQualityFlags: {
      immediate: true,
      handler(flags) {
        if(flags)
          this.value = flags
      },
    },
  },
}
</script>

<style scoped>

</style>