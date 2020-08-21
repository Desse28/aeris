<template>
  <v-card
      color="grey lighten-4"
      flat
      tile
  >
    <v-toolbar dense class="d-flex justify-center">
      <AerisDataValidationOptions
          :parametersLabel="parametersLabel"
          :parallelsLabel="parallelsLabel"
          :addNewParallel="addNewParallel"
          :removeParallel="removeParallel"
          :addNewParameter="addNewParameter"
          :removeParameter="removeParameter"
      />
    </v-toolbar>
  </v-card>
</template>
<script>
import {
  AerisDataValidationOptions,
} from "@/lib/modules/aeris-datavalidation-components";

export default {
  name: "aeris-datavalidation-simpletoolbar",
  props: {
    displayParallelChart : {
      type : Function,
    },
    hideParallelChart : {
      type : Function,
    },
  },
  components: {
    AerisDataValidationOptions
  },
  mounted() {
    this.setParallelsArray()
  },
  data() {
    return {
      parametersLabel: ["options1", "options2", "options3"],
      parallelsLabel: [],
    }
  },
  methods : {
    setParallelsArray : function () {
      let len = this.parametersLabel.length
      this.parallelsLabel = Array(len).fill().map((_, i) => 'parallel' + (i + 1));
    },
    addNewParameter : function (newOptions) {
      let lastIndex = newOptions.length - 1;
      let newParameter = newOptions[lastIndex];
      console.log("Added new option, value : ", newParameter)
    },
    removeParameter : function (newOptions, oldOptons) {
      let oldOptionsInterNewOptions = oldOptons.filter(value => !newOptions.includes(value))
      let deletedElement = oldOptionsInterNewOptions[0]
      let elementDeletedIndex = oldOptons.indexOf(deletedElement)
      console.log("Test (removeParameter) elementDeletedIndex : ", deletedElement)
      console.log("Test (removeParameter) elementDeletedIndex : ", elementDeletedIndex)
    },
    addNewParallel : function(newParallels) {
      let lastIndex = newParallels.length - 1
      let newParallelIndex = newParallels[lastIndex].split("parallel")[1]
      let targetParameter = this.parametersLabel[newParallelIndex-1]

      if(1 <= newParallels.length)
        this.displayParallelChart()

      console.log("Test (newParallels.len) : ", newParallels.length)
      console.log("Test (addNewParallel) targetParameter : ", targetParameter)
    },
    removeParallel : function (newParallels, oldParrales) {
      let deletedIndex;
      let targetParameter;
      let oldParallelsInterNewParallels;

      if(newParallels.length === 0) {
        this.hideParallelChart()
      } else if(newParallels && oldParrales) {
        oldParallelsInterNewParallels = oldParrales.filter(value => !newParallels.includes(value))
        deletedIndex = oldParallelsInterNewParallels[0].split("parallel")[1]
        targetParameter = this.parametersLabel[deletedIndex-1]
        console.log("Test (removeParallel) targetParameter : ", targetParameter)
      }
    },
  },
}
</script>

<style scoped>
</style>