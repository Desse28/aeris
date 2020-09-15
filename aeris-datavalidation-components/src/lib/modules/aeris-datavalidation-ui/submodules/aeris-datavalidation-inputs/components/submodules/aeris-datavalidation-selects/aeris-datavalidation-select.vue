<template>
  <v-select
      v-model="value"
      :items="items"
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
    qualityFlagsDefaultValue : {
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
    qualityFlagsDefaultValue : function () {
      this.value = this.qualityFlagsDefaultValue
    }
  },
}
</script>

<style scoped>

</style>